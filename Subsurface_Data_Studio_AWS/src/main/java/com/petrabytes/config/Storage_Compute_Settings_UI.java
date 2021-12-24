package com.petrabytes.config;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrabytes.databricks.DatabricksClusterPopupInfo;
import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.keyvault.AWS_Key_Vault;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;

import net.minidev.json.JSONObject;

public class Storage_Compute_Settings_UI extends PetrabyteUI_Dialog {

	private VerticalLayout mainLayout = new VerticalLayout();
	private HorizontalLayout toolbarLayout = new HorizontalLayout();
	private VerticalLayout centerLayout = new VerticalLayout();
	private Cluster_view clusterLayout = new Cluster_view();
	private Storage_view storageLayout = new Storage_view();
	private Tab clusterTab;
	private Tab storageTab;
	private Tabs tabs;
	Map<Tab, Component> tabsToPages = new HashMap<>();
	
	public Storage_Compute_Settings_UI() {
		
		SetUI();
	}
	
	
	private void SetUI() {
		// TODO Auto-generated method stub
		this.setButtonName("OK");
		this.setTitle("Cluster & Storage Configuration");
		this.setImage("icons" + File.separator + "purple-24x" + File.separator + "cluster-24x.png");
		
		clusterTab = new Tab();
		clusterTab.getStyle().set("width", "650px");
		clusterTab.add();
		Image wellCompletionimage = new Image("icons" + File.separator + "16x" + File.separator + "cluster_set_up16.png", "Cluster Setup");
		wellCompletionimage.getStyle().set("margin-right", "5px");
		Label wellCompletionlabel = new Label("Cluster Setup");
		wellCompletionlabel.setClassName("tab_label_spacing");
		clusterTab.add(wellCompletionimage);
		clusterTab.add("Cluster Setup");

	
		storageTab = new Tab();
		storageTab.getStyle().set("width", "790px");
		storageTab.add();
		Image storageTabimage = new Image("icons" + File.separator + "16x" + File.separator + "storage16.png", "Storage");
		storageTabimage.getStyle().set("margin-right", "5px");
		Label storagelabel = new Label("Storage");
		wellCompletionlabel.setClassName("tab_label_spacing");
		storageTab.add(storageTabimage);
		storageTab.add("Storage");
		
		tabsToPages.put(clusterTab, clusterLayout);
		tabsToPages.put(storageTab, storageLayout);

		tabs = new Tabs(clusterTab, storageTab);

//		tabs.setId("Tabs");
		tabs.setWidthFull();
		tabs.setFlexGrowForEnclosedTabs(1);
		tabs.setHeightFull();
		tabs.addThemeVariants(TabsVariant.LUMO_SMALL);
		toolbarLayout.add(tabs);
		mainLayout.add( toolbarLayout, centerLayout);
		centerLayout.expand(centerLayout);
		mainLayout.setWidth("1500px");
		mainLayout.setHeight("600px");
		this.content.add(mainLayout);
		createTabsWithPages();
		
		saveButtonEvent();
	}


	private void createTabsWithPages() {
		// TODO Auto-generated method stub
		centerLayout.add(clusterLayout);
		Set<Component> pagesShown = Stream.of(clusterLayout).collect(Collectors.toSet());
		tabs.addSelectedChangeListener(event -> {
			pagesShown.clear();
			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
			selectedPage.setVisible(true);
			Tab selectedTagName = tabs.getSelectedTab();
			if (selectedTagName.equals(clusterTab)) {
				System.out.println(selectedTagName);
				centerLayout.removeAll();
				centerLayout.add(clusterLayout);

			} else if (selectedTagName.equals(storageTab)) {
				System.out.println(selectedTagName);
				centerLayout.removeAll();
				centerLayout.add(storageLayout);
			}

		});

	}
	
	private void saveButtonEvent() {
		// TODO Auto-generated method stub
		this.saveButton.addClickListener(event -> {

			
			Cluster_Info_view selectedConfig = clusterLayout.severlessLayout.getSelectedConfig();
			DatabricksClusterPopupInfo selectedEndpoint = clusterLayout.severlessLayout.getSelectedEndpoint();
			List<Cluster_Info_view> existingConfigList = new <Cluster_Info_view>ArrayList();
			existingConfigList = AWS_Key_Vault.getExistingConfigsList();
			// check if the selectedConfig is present in existing configs.
			
			boolean newConfigFlag = true;
			for (Cluster_Info_view config : existingConfigList) {
				if (config.getToken().equalsIgnoreCase(selectedConfig.getToken())) {
					newConfigFlag = false;
					break;
				}
			}
			String url = selectedConfig.getUrl();
			String username = selectedConfig.getUserName();
			String password = selectedConfig.getToken();
			String endpointID = selectedEndpoint.getCluster();
			url = url.replace("https://", "");
			
			url = "jdbc:spark://" + url + ":443/default;transportMode=http;ssl=1;"
					+ "AuthMech=3;httpPath=/sql/1.0/endpoints/" + endpointID;

			String driver = "com.simba.spark.jdbc.Driver";
			Connection con = null;
			if(newConfigFlag) {
				try {
					Class.forName(driver);
					System.out.println("Trying to Connect to Databricks Cluster . . . ");
					String count = AWS_Key_Vault.getSecret("databricks-serverless-config-count");
					con = DriverManager.getConnection(url, username, password);
					System.out.println("Connection Successful");

					Statement stmt = con.createStatement();
					// if connection is successful, add json to secrets manager.
					
					JSONObject json = new JSONObject();
					json.put("servername", selectedConfig.getServername());
					json.put("url", selectedConfig.getUrl());
					json.put("userName", selectedConfig.getUserName());
					json.put("token", selectedConfig.getToken());
					json.put("type", selectedConfig.getType());
					
					String jsonString = json.toString();
					
					AWS_Key_Vault.updateSecret("databricks-serverless-config-count", count + 1);
					AWS_Key_Vault.createNewSecret("databricks-serverless-config-" + (count + 1).toString() , jsonString);
					AWS_Key_Vault.updateSecret("databricks-serverless-endpoint" , endpointID);
					AWS_Key_Vault.updateSecret("databricks-serverless-current-config" , jsonString);
					System.out.println(AWS_Key_Vault.getSecret("databricks-serverless-config-") + (count + 1).toString());
					System.out.println(AWS_Key_Vault.getSecret("databricks-serverless-endpoint"));
					this.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				
				try {
					Class.forName(driver);
					System.out.println("Trying to Connect to Databricks Cluster . . . ");
					String count = AWS_Key_Vault.getSecret("databricks-serverless-config-count");
					con = DriverManager.getConnection(url, username, password);
					System.out.println("Connection Successful");

					Statement stmt = con.createStatement();
					
					// for successful connection, updating the selected endpoint and config
					
					JSONObject json = new JSONObject();
					json.put("servername", selectedConfig.getServername());
					json.put("url", selectedConfig.getUrl());
					json.put("userName", selectedConfig.getUserName());
					json.put("token", selectedConfig.getToken());
					json.put("type", selectedConfig.getType());
					
					String jsonString = json.toString();
					AWS_Key_Vault.updateSecret("databricks-serverless-endpoint" , endpointID);
					AWS_Key_Vault.updateSecret("databricks-serverless-current-config" , jsonString);
					
					
					System.out.println(AWS_Key_Vault.getSecret("databricks-serverless-endpoint"));
					this.close();
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			
			}
			
		});
	}
	
	
	
	
}

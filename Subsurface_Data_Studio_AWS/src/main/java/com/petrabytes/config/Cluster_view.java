package com.petrabytes.config;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrabytes.databricks.DatabricksClusterPopupInfo;
import com.petrabytes.toptoolUI.Blgz_OpenProject_UI;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

public class Cluster_view extends VerticalLayout{
	
	private VerticalLayout mainLayout = new VerticalLayout();
	private VerticalLayout centerLayout = new VerticalLayout();
	private HorizontalLayout toolbarLayout = new HorizontalLayout();
	
	private Grid<Cluster_Info_view> clusterGrid = new Grid<>();
	private Grid<DatabricksClusterPopupInfo> popupGrid = new Grid<>();
	private Button clusteraddbutton = new Button();
	private Button clustereditButton = new Button();
	private Button clusterdeleteButton = new Button();
	private Label clusterlabel = new Label("Cluster List");
	
	public Severless_Tab_View severlessLayout = new Severless_Tab_View();
	private Classic_Tab_View ClassicLayout = new Classic_Tab_View();
	
	private Tab computeTab;
	private Tab serverlessTab;
	private Tabs tabs;
	

	Map<Tab, Component> tabsToPages = new HashMap<>();
	public Cluster_view() {
		
		setui();
		clusteraddbutton.addClickListener(event -> {
			try {

				addbuttonpopup();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		clustereditButton.addClickListener(event -> {
			try {

				editbuttonpopup();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		 
		 computeTab = new Tab();
		 computeTab.getStyle().set("width", "650px");
		 computeTab.add();
		 Image computeTabimage = new Image("icons" + File.separator + "16x" + File.separator + "compute_2_16.png", "Compute");
		 computeTabimage.getStyle().set("margin-right", "5px");
			computeTab.add(computeTabimage);
			computeTab.add("Compute");


		serverlessTab = new Tab();
		serverlessTab.getStyle().set("width", "690px");
		
		serverlessTab.add("");
		 Image serverlessTabimage = new Image("icons" + File.separator + "16x" + File.separator + "serverless_SQL16.png", "Serverless SQL");
		 serverlessTabimage.getStyle().set("margin-right", "5px");
			serverlessTab.add(serverlessTabimage);
			serverlessTab.add("Serverless SQL");
		
		tabsToPages.put(computeTab, ClassicLayout);
		tabsToPages.put(serverlessTab, severlessLayout);

		tabs = new Tabs(computeTab, serverlessTab);
		
//		tabs.setId("Tabs");
		tabs.setWidthFull();
		tabs.setFlexGrowForEnclosedTabs(1);
		tabs.setHeightFull();
		tabs.addThemeVariants(TabsVariant.LUMO_SMALL);
		toolbarLayout.add(tabs);
		
		mainLayout.add(toolbarLayout,centerLayout);
		mainLayout.getStyle().set("margin-top", "-30px");
		
		centerLayout.expand(centerLayout);
         
		createTabsWithPages();
	
	}

	

	private void createTabsWithPages() {
		// TODO Auto-generated method stub
		centerLayout.add(ClassicLayout);
		Set<Component> pagesShown = Stream.of(ClassicLayout).collect(Collectors.toSet());
		tabs.addSelectedChangeListener(event -> {
			pagesShown.clear();
			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
			selectedPage.setVisible(true);
			Tab selectedTagName = tabs.getSelectedTab();
			if (selectedTagName.equals(computeTab)) {
				System.out.println(selectedTagName);
				centerLayout.removeAll();
				centerLayout.add(ClassicLayout);

			} else if (selectedTagName.equals(serverlessTab)) {
				System.out.println(selectedTagName);
				centerLayout.removeAll();
				centerLayout.add(severlessLayout);
			}

		});
	}



	private void setui() {
		// TODO Auto-generated method stub

			add(mainLayout);
			
	}
	
	
	private void addbuttonpopup()  throws Exception {
		Tab selectedTagName = tabs.getSelectedTab();
		if (selectedTagName.equals(computeTab)) {
			
		}
		Cluster_Dialog_View equationWindow = new Cluster_Dialog_View(false);

		equationWindow.open();

		}
	
	private void editbuttonpopup()  throws Exception {

		Cluster_Dialog_View equationWindow = new Cluster_Dialog_View(true);

			equationWindow.open();

		}
	
	}

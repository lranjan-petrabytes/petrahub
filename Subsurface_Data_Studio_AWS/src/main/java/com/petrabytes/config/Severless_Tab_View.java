package com.petrabytes.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.amazonaws.services.budgets.model.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrabytes.databricks.DatabricksClusterPopupInfo;
import com.petrabytes.databricks.Serverless_SQL_Endpoints_List;
import com.petrabytes.keyvault.AWS_Key_Vault;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Severless_Tab_View extends VerticalLayout{

	private VerticalLayout mainLayout = new VerticalLayout();
	public Grid<Cluster_Info_view> configGrid = new Grid<>();
	public Grid<DatabricksClusterPopupInfo> endpointsGrid = new Grid<>();
	private Button clusteraddbutton = new Button();
	private Button clustereditButton = new Button();
	private Button clusterdeleteButton = new Button();
	private Label clusterlabel = new Label("Endpoints List");
	List<Cluster_Info_view> serverList = new <Cluster_Info_view>ArrayList();
	public Cluster_Info_view selectedConfig;
	public DatabricksClusterPopupInfo selectedEndpoint;
	
	public Severless_Tab_View() {
		
		SetUI();
		clusteraddbutton.addClickListener(event -> {
			try {

				savebuttonpopup();
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
		initConfigGrid();
		configGridSelectionEvent();
		endpointsGridSelectionEvent();
	
}


	private void initConfigGrid() {
		// TODO Auto-generated method stub
		serverList = AWS_Key_Vault.getExistingConfigsList();
		configGrid.setItems(serverList);
	}


	private void endpointsGridSelectionEvent() {
		// TODO Auto-generated method stub
		endpointsGrid.addSelectionListener(event -> {
			selectedEndpoint = endpointsGrid.asSingleSelect().getValue();
		});
		
	}


	private void configGridSelectionEvent() {
		// TODO Auto-generated method stub
		configGrid.addSelectionListener(event -> {
			Cluster_Info_view configInfo = configGrid.asSingleSelect().getValue();
			String url_for_list = configInfo.getUrl() + "/api/2.0/sql/endpoints/";
			String token_value = configInfo.getToken();
			String username_value = configInfo.getUserName();
			ArrayList<DatabricksClusterPopupInfo> popupDatabricksList;
			try {
				popupDatabricksList = new Serverless_SQL_Endpoints_List().getDatabricks_Cluster_List(url_for_list,username_value,
						token_value);
				endpointsGrid.setItems(popupDatabricksList);
				selectedConfig = configInfo;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}


	private void SetUI() {
		// TODO Auto-generated method stub
		clusterlabel.getStyle().set("font-weight", "bold");
		HorizontalLayout ButtonLayout = new HorizontalLayout();
		Image clusteraddImage = new Image("icons" + File.separator + "16x" + File.separator + "add-16x.png",
				"Add Server");
		clusteraddbutton.getElement().setAttribute("title", "Add Server");
		clusteraddbutton.setIcon(clusteraddImage);

		Image clustereditImage = new Image("icons" + File.separator + "16x" + File.separator + "edit_16.png",
				"Edit Server");
		clustereditButton.getElement().setAttribute("title", "Edit Server");
		clustereditButton.setIcon(clustereditImage);

		Image clusterdeleteImage1 = new Image("icons" + File.separator + "16x" + File.separator + "delete16.png",
				"Delete Server");
		clusterdeleteButton.getElement().setAttribute("title", "Delete Server");
		clusterdeleteButton.setIcon(clusterdeleteImage1);
	 ButtonLayout.add(clusteraddbutton,clustereditButton,clusterdeleteButton);
	 ButtonLayout.getStyle().set("margin-top", "-30px");
	 
	 configGrid.setSelectionMode(SelectionMode.SINGLE);
	 configGrid.addColumn(Cluster_Info_view::getServername).setHeader("Server Name").setAutoWidth(true);
	 configGrid.addColumn(Cluster_Info_view::getUrl).setHeader("URL").setAutoWidth(true);
	 configGrid.addColumn(Cluster_Info_view::getUserName).setHeader("User Name").setAutoWidth(true);
	 
	 
	 configGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
	 configGrid.setHeightByRows(true);
	 configGrid.setWidth("1390px");
	 
	 
	 endpointsGrid.setSelectionMode(SelectionMode.SINGLE);

		endpointsGrid.addColumn(DatabricksClusterPopupInfo::getName).setHeader("Endpoint Name").setAutoWidth(true);
		endpointsGrid.addColumn(DatabricksClusterPopupInfo::getCluster).setHeader("Endpoint Id").setAutoWidth(true);
		endpointsGrid.addColumn(DatabricksClusterPopupInfo::getCreatedBy).setHeader("Created by").setAutoWidth(true);
		endpointsGrid.addColumn(DatabricksClusterPopupInfo::getNodeType).setHeader("Node Type").setAutoWidth(true);
		
		endpointsGrid.addColumn(DatabricksClusterPopupInfo::getWorker_min).setHeader("Worker(min)").setAutoWidth(true);
		endpointsGrid.addColumn(DatabricksClusterPopupInfo::getWorker_max).setHeader("Worker(max)").setAutoWidth(true);
		endpointsGrid.addColumn(DatabricksClusterPopupInfo::getStatus).setHeader("Status").setAutoWidth(true);

		endpointsGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		endpointsGrid.setHeightByRows(true);
		endpointsGrid.setWidth("1390px");
		
		mainLayout.add(ButtonLayout,configGrid,clusterlabel,endpointsGrid);
		mainLayout.getStyle().set("margin-left", "-60px");
		add(mainLayout);
		
		
	}
	private void savebuttonpopup()  throws Exception {

		Cluster_Dialog_View equationWindow = new Cluster_Dialog_View(false);

			equationWindow.open();
			equationWindow.addOpenedChangeListener(event-> {
				if(!event.isOpened()) {
					JSONObject json = equationWindow.getJson();
					ObjectMapper objectMapper = new ObjectMapper();
					Cluster_Info_view serverInfo;
					try {
						serverInfo = objectMapper.readValue(json.toString(), Cluster_Info_view.class);
						serverList.add(serverInfo);
						configGrid.setItems(serverList);
					} catch (JsonMappingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JsonProcessingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});

		}
	
	private void editbuttonpopup()  throws Exception {

		Cluster_Dialog_View equationWindow = new Cluster_Dialog_View(true);

			equationWindow.open();

		}


	public Cluster_Info_view getSelectedConfig() {
		return selectedConfig;
	}


	public void setSelectedConfig(Cluster_Info_view selectedConfig) {
		this.selectedConfig = selectedConfig;
	}


	public DatabricksClusterPopupInfo getSelectedEndpoint() {
		return selectedEndpoint;
	}


	public void setSelectedEndpoint(DatabricksClusterPopupInfo selectedEndpoint) {
		this.selectedEndpoint = selectedEndpoint;
	}
	
	
	}


package com.petrabytes.databricks;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.views.MainLayout;





public class DatabricksClusterPopup extends PetrabyteUI_Dialog {
	private VerticalLayout body = new VerticalLayout();
	
	private Grid<DatabricksClusterPopupInfo> popupGrid = new Grid<>();
    
	private Button select = new Button("OK");
	private Button close = new Button();
	private Button refreshButton = new Button();
	private Button settingshButton = new Button();
	
	
	private boolean _editFlag;

	public   DatabricksClusterPopup(boolean editFlag) throws Exception {
		this._editFlag = editFlag;
	
		//this.getElement().getStyle().set("padding", "0 0");
		
		mainLayout.getStyle().set("resize", "both");
		mainLayout.getStyle().set("overflow", "auto");
//		mainLayout.getElement().getStyle().set("padding-left", "20px");
//	
		_setUI();
	
		populateGrid();
		selectevent();
		_refreshevent();
	}
 
	

	private void _setUI() {
		// TODO Auto-generated method stub
		Image refreshButtonimage = new Image(
				"images" +  File.separator + "refresh16.png", "Refresh");
		refreshButton.getElement().setAttribute("title", "Refresh");
		refreshButton.setIcon(refreshButtonimage);
		
		Image settingsButtonimage = new Image(
				"icons" + File.separator + "purple-16x" + File.separator + "settings16.png", "Settings");
		settingshButton.getElement().setAttribute("title", "Settings");
		settingshButton.setIcon(settingsButtonimage);
		settingshButton.addClickListener(event -> {
			try {
				updateSelectedDialog();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					});

		
		close.addThemeVariants(ButtonVariant.LUMO_SMALL,ButtonVariant.LUMO_TERTIARY);
		
		this.customLayout.add(refreshButton,settingshButton);
		body.setWidth("1230px");
		body.setHeight("235px");
		this.setImage("icons" + File.separator + "purple-24x" + File.separator + "jobs-list-24x.png");
		 this.content.add(body);
		this.setTitle("Databricks Cluster List");
		this.setButtonName("OK");
		this.setCloseButtonName("Cancel");
	//	this.subfooterLayout.add(refresh);
//		this.setCloseButtonName("Refresh");
		
		popupGrid.addColumn(DatabricksClusterPopupInfo::getName).setHeader("Cluster Name").setAutoWidth(true);
		popupGrid.addColumn(DatabricksClusterPopupInfo::getCluster).setHeader("Cluster Id").setAutoWidth(true);
		popupGrid.addColumn(DatabricksClusterPopupInfo::getCreatedBy).setHeader("Created by").setAutoWidth(true);
		popupGrid.addColumn(DatabricksClusterPopupInfo::getNodeType).setHeader("Node Type").setAutoWidth(true);
		
		popupGrid.addColumn(DatabricksClusterPopupInfo::getWorker_min).setHeader("Worker(min)").setAutoWidth(true);
		popupGrid.addColumn(DatabricksClusterPopupInfo::getWorker_max).setHeader("Worker(max)").setAutoWidth(true);
		popupGrid.addColumn(DatabricksClusterPopupInfo::getStatus).setHeader("Status").setAutoWidth(true);
		// wellInformationGrid.addThemeVariants(GridVariant.LUMO_NO_BORDER);

		popupGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		popupGrid.setHeightByRows(true);
		body.add(popupGrid);
		
		
//		HorizontalLayout buttonLayout = new HorizontalLayout();
//		buttonLayout.setClassName("w-full flex-wrap py-s px-l");
//		buttonLayout.getStyle().set("padding-left", "30px");
//		buttonLayout.setSpacing(true);
//		select.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//		refresh.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//	//	close.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//		buttonLayout.add(select, refresh);
//		buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
//		mainLayout.add(buttonLayout);
		
	}
	


		// TODO Auto-generated method stub
		private void updateSelectedDialog() throws Exception {

			Settings_UI equationWindow = new Settings_UI();

			equationWindow.open();
	}



	private void populateGrid() throws Exception {
//	ArrayList<DatabricksClusterPopupInfo> popupDatabricksList = new  Databricks_Cluster_List().getDatabricks_Cluster_List();
//		popupGrid.setItems(popupDatabricksList);
//		popupGrid.setSelectionMode(SelectionMode.SINGLE);
	}
	
	
	private String gridselectionId() {
		
		String clusterID = null;
		for (DatabricksClusterPopupInfo row : popupGrid.getSelectedItems()) {

			clusterID = row.getCluster();
			System.out.println("Cluster id" + clusterID);
		}
		
		return clusterID;
		
	}
	
	private void selectevent() {
		select.addClickListener(event ->{
			
			gridselectionId();
			
		});
	}
	private void _refreshevent() {

		refreshButton.addClickListener(event -> {
			try {
				//popupGrid.removeAllColumns();
				//_setUI();
//				ArrayList<DatabricksClusterPopupInfo> popupDatabricksList = new  Databricks_Cluster_List().getDatabricks_Cluster_List();
//				popupGrid.setItems(popupDatabricksList);
//				popupGrid.getDataProvider().refreshAll();
//				System.out.println("Refresh");
		    	

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

}





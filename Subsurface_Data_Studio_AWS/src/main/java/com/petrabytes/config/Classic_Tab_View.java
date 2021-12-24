package com.petrabytes.config;

import java.io.File;

import com.petrabytes.databricks.DatabricksClusterPopupInfo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Classic_Tab_View extends VerticalLayout {
	private VerticalLayout mainLayout = new VerticalLayout();
	private Grid<Cluster_Info_view> clusterGrid = new Grid<>();
	private Grid<DatabricksClusterPopupInfo> popupGrid = new Grid<>();
	private Button clusteraddbutton = new Button();
	private Button clustereditButton = new Button();
	private Button clusterdeleteButton = new Button();
	private Label clusterlabel = new Label("Cluster List");
	
	
	
	public Classic_Tab_View() {
		
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
	 
	 clusterGrid.setSelectionMode(SelectionMode.SINGLE);
	 clusterGrid.addColumn(Cluster_Info_view::getServername).setHeader("Server Name").setAutoWidth(true);
	 clusterGrid.addColumn(Cluster_Info_view::getUrl).setHeader("URL").setAutoWidth(true);
	 clusterGrid.addColumn(Cluster_Info_view::getUserName).setHeader("User Name").setAutoWidth(true);
	 
	 
	 clusterGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
	 clusterGrid.setHeightByRows(true);
	 clusterGrid.setWidth("1390px");
	 
	 
	 popupGrid.setSelectionMode(SelectionMode.SINGLE);

		popupGrid.addColumn(DatabricksClusterPopupInfo::getName).setHeader("Cluster Name").setAutoWidth(true);
		popupGrid.addColumn(DatabricksClusterPopupInfo::getCluster).setHeader("Cluster Id").setAutoWidth(true);
		popupGrid.addColumn(DatabricksClusterPopupInfo::getCreatedBy).setHeader("Created by").setAutoWidth(true);
		popupGrid.addColumn(DatabricksClusterPopupInfo::getNodeType).setHeader("Node Type").setAutoWidth(true);
		
		popupGrid.addColumn(DatabricksClusterPopupInfo::getWorker_min).setHeader("Worker(min)").setAutoWidth(true);
		popupGrid.addColumn(DatabricksClusterPopupInfo::getWorker_max).setHeader("Worker(max)").setAutoWidth(true);
		popupGrid.addColumn(DatabricksClusterPopupInfo::getStatus).setHeader("Status").setAutoWidth(true);

		popupGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		popupGrid.setHeightByRows(true);
		popupGrid.setWidth("1390px");
		
		mainLayout.add(ButtonLayout,clusterGrid,clusterlabel,popupGrid);
		mainLayout.getStyle().set("margin-left", "-60px");
		add(mainLayout);
		
		
	}
	private void savebuttonpopup()  throws Exception {

		Cluster_Dialog_View equationWindow = new Cluster_Dialog_View(false);

			equationWindow.open();

		}
	
	private void editbuttonpopup()  throws Exception {

		Cluster_Dialog_View equationWindow = new Cluster_Dialog_View(true);

			equationWindow.open();

		}
	
	}

package com.petrabytes.views.wells;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.petrabytes.databricks.DatabricksClusterPopupInfo;
import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.project.util.BasinViewProjectSettingInfo;
import com.petrabytes.project.util.ProjectSettingsInfo;
import com.petrabytes.project.util.WellsMainViewSettingsInfo;
import com.petrabytes.toptoolUI.Blgz_CreateProject_UI;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.UI_Update;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.basin.BasinView;
import com.petrabytes.views.basin.BasinViewGridInfo;
import com.petrabytes.views.basin_registry_workset.deleteBasinprojectUI;
import com.petrabytes.views.wellRegistryQuery.WellRegistryQuries;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.server.VaadinService;

public class WellList_view extends VerticalLayout {
	public ListDataProvider<BasinViewGridInfo> basinProvider;
	public ListDataProvider<WellListInfo> wellDataProvider;
	private ValueChangeListener<ValueChangeEvent<BasinViewGridInfo>> basinComboBoxListerner = null;
	private ComboBox<BasinViewGridInfo> basincombobox = new ComboBox();
	
	public BasinViewGridInfo selectedBasin = null;
	private VerticalLayout mainLayout = new VerticalLayout();
	private Grid<WellListInfo> WellListGrid = new Grid<>();
	private SortDirection aSSENDINGDirection = SortDirection.ASCENDING;
	private Button Add = new Button("Add");

	private Button Edit = new Button("Edit");
	private Button Delete = new Button("Delete");
	private ProjectSettingsInfo projectSettings = null;
	private WellsMainViewSettingsInfo wellSettings = null;
	private final UI ui = UI.getCurrent();
  
	public WellList_view() throws SQLException {
		mainLayout.setSizeFull();
		// this.getElement().getStyle().set("padding", "0 0");
		add(mainLayout);
		mainLayout.setSizeFull();
		String projectname = (String) VaadinService.getCurrentRequest().getWrappedSession()
				.getAttribute("project_name");
		if (projectname == null) {
//			 Notification.show("Please first create the project", 3000,
//			 Position.BOTTOM_START).addThemeVariants(NotificationVariant.LUMO_CONTRAST);

		} else {

			UI_Update.updateEnable_topBar();
			getProjectSetting();
			listBainFromDB();
	
				

					setUI();			
					addButton();

				gridchangelistner();

      		} 
		}

	private void getProjectSetting() {
		// TODO Auto-generated method stub
		projectSettings = View_Update.getProjectsettingInfo();
		if (projectSettings != null) {
			wellSettings = projectSettings.getViews().getWellSetting();
			if (wellSettings == null)
				wellSettings = new WellsMainViewSettingsInfo();
		}
	}

	private void setUI() throws SQLException {
		// TODO Auto-generated method stub

		WellListGrid.addColumn(WellListInfo::getApi).setHeader("API").setAutoWidth(true);
		WellListGrid.addColumn(WellListInfo::getWellName).setHeader("Well Name").setAutoWidth(true);
		WellListGrid.addColumn(WellListInfo::getWellType).setHeader("Well Type").setAutoWidth(true);
	//	WellListGrid.addColumn(WellListInfo::getStatus).setHeader("Status").setAutoWidth(true);
		WellListGrid.addColumn(WellListInfo::getState).setHeader("State").setAutoWidth(true);
		WellListGrid.addColumn(WellListInfo::getBasin).setHeader("Basin").setAutoWidth(true);
		WellListGrid.addColumn(WellListInfo::getCompanyName).setHeader("Company Name").setAutoWidth(true);
		WellListGrid.addColumn(WellListInfo::getTimezone).setHeader("Timezone").setAutoWidth(true);
		WellListGrid.addColumn(WellListInfo::getWellType).setHeader("Location").setAutoWidth(true);
		WellListGrid.addColumn(WellListInfo::getAirGap).setHeader("Air Gap").setAutoWidth(true);
		WellListGrid.addColumn(WellListInfo::getWaterDensity).setHeader("Water Density").setAutoWidth(true);
		WellListGrid.addColumn(WellListInfo::getWaterDepth).setHeader("Water Depth").setAutoWidth(true);
		WellListGrid.addColumn(WellListInfo::getCountryarea).setHeader("Area").setAutoWidth(true);
		WellListGrid.addColumn(WellListInfo::getGlElevation).setHeader("GL Elevation").setAutoWidth(true);
		WellListGrid.addColumn(WellListInfo::getCountry).setHeader("Country").setAutoWidth(true);
		WellListGrid.addColumn(WellListInfo::getLatitude).setHeader("Latutude").setAutoWidth(true);
		WellListGrid.addColumn(WellListInfo::getLongtide).setHeader("Longtide").setAutoWidth(true);
		WellListGrid.setSelectionMode(SelectionMode.SINGLE);

//		WellListGrid.setWidthFull();
		 WellListGrid.setHeight("600px");

		WellListGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		// WellListGrid.setHeightByRows(true);
		basinChangeListner();


	}

	private void addButton() {
		// TODO Auto-generated method stub
		HorizontalLayout buttonLayout = new HorizontalLayout();
		Label basinlabel = new Label("Basin");
		basinlabel.getStyle().set("margin-top", "10px");
	
	//	basincombobox.getStyle().set("margin-top", "-20px");
	//	basincombobox.setWidth("255px");
		Add = new Button();
		Image removeBasinImage = new Image("icons" + File.separator + "16x" + File.separator + "add-16x.png",
				"Create Well");
		Add.getElement().setAttribute("title", "Create Well");
		Add.setIcon(removeBasinImage);

		Edit = new Button();
		Image editBasinImage = new Image("icons" + File.separator + "16x" + File.separator + "edit_16.png",
				"Edit Well");
		Edit.getElement().setAttribute("title", "Edit Well");
		Edit.setIcon(editBasinImage);

		Delete = new Button();
		Image removeBasinImage1 = new Image("icons" + File.separator + "16x" + File.separator + "delete16.png",
				"Delete Well");
		Delete.getElement().setAttribute("title", "Delete Well");
		Delete.setIcon(removeBasinImage1);

		buttonLayout.setClassName("w-full flex-wrap py-s px-l");
		buttonLayout.getStyle().set("margin-top", "-25px");
		buttonLayout.setSpacing(true);

		Edit.addClickListener(event -> {

				try {
					editbuttonpopup();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			
		});
		buttonLayout.add(basinlabel,basincombobox,Add, Edit, Delete);

		Add.addClickListener(event -> {

				try {

					addbuttonpopup();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			
		});

		Delete.addClickListener(event -> {
			WellListInfo wellinfo = View_Update.getWellInfo();
			if (wellinfo != null) {
				try {

					deletebuttonpopup();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				 PB_Progress_Notification notification = new PB_Progress_Notification();
				 String selectWell = PetrahubNotification_Utilities.getInstance().wellListSelection();
		  			notification.setText(selectWell);
		  			notification.open();
		  			notification.setDuration(3000);
			}
		});
		// UI.getCurrent().navigate("WellInfo");

		buttonLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.START);
		mainLayout.add(buttonLayout, WellListGrid);
		mainLayout.expand(WellListGrid);
	}

	private void addbuttonpopup() throws Exception {
		BasinViewGridInfo basinfo	= basincombobox.getValue();
		if (basinfo != null) {
			WellInfoView WellInfoView = new WellInfoView(false, WellListGrid, basinfo);

			WellInfoView.open();
		} else {
			 PB_Progress_Notification notification = new PB_Progress_Notification();
			 String selectBasin = PetrahubNotification_Utilities.getInstance().selectBasinInWellView();
  			notification.setText(selectBasin);
  			notification.open();
  			notification.setDuration(3000);
		}

	}

	private void editbuttonpopup() throws Exception {
		BasinViewGridInfo basinfo	= basincombobox.getValue();
		WellListInfo wellgridinfo     =    WellListGrid.asSingleSelect().getValue();
		if (basinfo != null) {
			if(wellgridinfo !=null) {
			WellInfoView WellInfoView = new WellInfoView(true, WellListGrid, basinfo);

			WellInfoView.open();
		}else {
			 PB_Progress_Notification notification = new PB_Progress_Notification();
			 String selectWell = PetrahubNotification_Utilities.getInstance().wellListSelection();
	  			notification.setText(selectWell);
	  			notification.open();
	  			notification.setDuration(3000);
			}

			
		} else {
			 PB_Progress_Notification notification = new PB_Progress_Notification();
			 String selectBasin = PetrahubNotification_Utilities.getInstance().selectBasinInWellView();
	  			notification.setText(selectBasin);
	  			notification.open();
	  			notification.setDuration(3000);
		}

	}

	private void deletebuttonpopup() throws Exception {
		WellDeleteUI equationWindow = new WellDeleteUI( WellListGrid.asSingleSelect().getValue(),WellListGrid);

		equationWindow.open();

	}

	


	private void basinChangeListner() {

		basinComboBoxListerner = new ValueChangeListener<HasValue.ValueChangeEvent<BasinViewGridInfo>>() {

			@Override
			public void valueChanged(ValueChangeEvent<BasinViewGridInfo> event) {
				// TODO Auto-generated method stub
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				 String queryWellList = PetrahubNotification_Utilities.getInstance().queringWellListInWellView();
				executor.submit(() -> {

					ui.access(() -> {

						notificatiion.setImage("info");
						notificatiion.setText(queryWellList);
						notificatiion.open();
					});
			
				selectedBasin = event.getValue();
				if (selectedBasin != null) {
					try {
						wellDataProvider = new WellRegistryQuries().convertToListWell(selectedBasin.getBasinID());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ui.access(() -> {
					wellDataProvider.setSortOrder(WellListInfo::getWellName, aSSENDINGDirection);
					WellListGrid.setDataProvider(wellDataProvider);
					notificatiion.close();
					});
				}
				});
			}
		};
		basincombobox.addValueChangeListener(basinComboBoxListerner);
}

	private void gridchangelistner() {
		WellListGrid.addSelectionListener(listener -> {
			WellListInfo row = WellListGrid.asSingleSelect().getValue();

			View_Update.setWellInfo(row);

			wellSettings.setSelectedWellGrid(row);

			projectSettings.getViews().setWellSetting(wellSettings);
			View_Update.setProjectsettingInfo(projectSettings);
		
		});
	}
	
	private void listBainFromDB() throws SQLException {
		ExecutorService executor = Executors.newCachedThreadPool();
		PB_Progress_Notification notificatiion = new PB_Progress_Notification();
		 String queryBasinList = PetrahubNotification_Utilities.getInstance().queringBasinListInWellView();

		executor.submit(() -> {

			ui.access(() -> {

				notificatiion.setImage("info");
				notificatiion.setText(queryBasinList);
				notificatiion.open();
			});
	
		ListDataProvider<BasinViewGridInfo> basin;
		try {
			basin = new WellRegistryQuries().convertToListBasin();
			basincombobox.setItemLabelGenerator(BasinViewGridInfo::getBasinName);
			basin.setSortOrder(BasinViewGridInfo::getBasinName, aSSENDINGDirection);
			basincombobox.setDataProvider(basin);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ui.access(() -> {

			notificatiion.close();
		});
	
		});
	}



}

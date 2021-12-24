package com.petrabytes.views.wellbore;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.project.util.ProjectSettingsInfo;
import com.petrabytes.project.util.WellboreViewProjectSettingInfo;
import com.petrabytes.project.util.WellsMainViewSettingsInfo;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.MainLayout;
import com.petrabytes.views.UI_Update;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.basin.BasinViewGridInfo;
import com.petrabytes.views.wellRegistryQuery.WellRegistryQuries;
import com.petrabytes.views.wellRegistryQuery.WellboreListInfo;
import com.petrabytes.views.wells.WellListInfo;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;

@Route(value = "wellboreView", layout = MainLayout.class)
@PageTitle("Wellbores")
public class WellboreView extends VerticalLayout {
	private VerticalLayout mainLayot = new VerticalLayout();
	public ListDataProvider<BasinViewGridInfo> basinDataProvider;
	public ListDataProvider<WellListInfo> wellDataProvider;
	public ListDataProvider<WellboreListInfo> wellboreDataProvider;
	private SortDirection aSSENDINGDirection = SortDirection.ASCENDING;
	private ValueChangeListener<ValueChangeEvent<BasinViewGridInfo>> basinComboBoxListerner = null;
	private ValueChangeListener<ValueChangeEvent<WellListInfo>> wellComboBoxListerner = null;
	private final UI ui = UI.getCurrent();
	private Grid<WellboreListInfo> wellboreGrid = new Grid<>();

	private ProjectSettingsInfo projectSettings = null;
	private WellboreViewProjectSettingInfo wellboreSettings = null;

	public BasinViewGridInfo selectedBasin = null;
	public WellListInfo selectedWell = null;
	
	public  WellboreListInfo selectedWellbore = null;
	private ComboBox<BasinViewGridInfo> basincombobox = new ComboBox<BasinViewGridInfo>();
	private ComboBox<WellListInfo> wellcombobox = new ComboBox<WellListInfo>();

	public WellboreView() throws SQLException {

		mainLayot.setSizeFull();
		add(mainLayot);
		String projectname = (String) VaadinService.getCurrentRequest().getWrappedSession()
				.getAttribute("project_name");
		if (projectname == null) {
PB_Progress_Notification notification = new PB_Progress_Notification();
String createProject = PetrahubNotification_Utilities.getInstance().createProject();
			notification.setImage("info");
			notification.setText(createProject);
			notification.open();
			notification.setDuration(3000);

		} else {

			UI_Update.updateEnable_topBar();

			getProjectSetting();
		
				Analysis_setUI();

				gridchangelistner();

		}
	}

	private void getProjectSetting() {
		// TODO Auto-generated method stub
		projectSettings = View_Update.getProjectsettingInfo();
		if (projectSettings != null) {
			wellboreSettings = projectSettings.getViews().getWellboreViewSettings();
			if (wellboreSettings == null)
				wellboreSettings = new WellboreViewProjectSettingInfo();
		}
	}

	private void Analysis_setUI() throws SQLException {

		HorizontalLayout headerLayout = new HorizontalLayout();

		HorizontalLayout buttonTabLayout = new HorizontalLayout();
		Label basinlabel = new Label("Basin");
		basinlabel.getStyle().set("margin-top", "10px");
		
		
		Label welllabel = new Label("Well");
		welllabel.getStyle().set("margin-top", "10px");
	
		
		Button addWellbore = new Button();
		Image removeBasinImage = new Image("icons" + File.separator + "16x" + File.separator + "add-16x.png",
				"Create Wellbore");
		addWellbore.getElement().setAttribute("title", "Create Wellbore");
		addWellbore.setIcon(removeBasinImage);

		Button editWellbore = new Button();
		Image editBasinImage = new Image("icons" + File.separator + "16x" + File.separator + "edit_16.png",
				"Edit Wellbore");
		editWellbore.getElement().setAttribute("title", "Edit Wellbore");
		editWellbore.setIcon(editBasinImage);

		Button deleteWellbore = new Button();
		Image removeBasinImage1 = new Image("icons" + File.separator + "16x" + File.separator + "delete16.png",
				"Delete Wellbore");
		deleteWellbore.getElement().setAttribute("title", "Delete Wellbore");
		deleteWellbore.setIcon(removeBasinImage1);

		Button configWellbore = new Button();
		Image configWellboreImage = new Image("icons" + File.separator + "16x" + File.separator + "wb-config-16.png",
				"Config Wellbore");
		configWellbore.setIcon(configWellboreImage);
		configWellbore.getElement().setAttribute("title", "Wellbore Configuration");

		HorizontalLayout config = new HorizontalLayout();
		config.setWidth("350px");
	
		
		config.add(configWellbore);
		config.getElement().getStyle().set("margin-left", "504px");
		buttonTabLayout.add(basinlabel,basincombobox,welllabel,wellcombobox,addWellbore, editWellbore, deleteWellbore, config );

		wellboreGrid.addColumn(WellboreListInfo::getBasinName).setHeader("Basin").setAutoWidth(true);
		wellboreGrid.addColumn(WellboreListInfo::getWellNmae).setHeader("Well").setAutoWidth(true);

		wellboreGrid.addColumn(WellboreListInfo::getWellboreID).setHeader("Wellbore ID").setAutoWidth(true);
		wellboreGrid.addColumn(WellboreListInfo::getWellboreName).setHeader("Wellbore Name").setAutoWidth(true);
		wellboreGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		wellboreGrid.setSelectionMode(SelectionMode.SINGLE);
		wellboreGrid.setHeight("600px");
		mainLayot.add(headerLayout, buttonTabLayout, wellboreGrid);
		 String selectWellbore =     PetrahubNotification_Utilities.getInstance().selectWellboreView();
		addWellbore.addClickListener(event -> {

			BasinViewGridInfo basin = basincombobox.getValue();
			     WellListInfo  well = wellcombobox.getValue();
			if (basin != null) {
				
			if(  well!= null) {
				try {

					addWellborebuttonpopup();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
//				Notification.show("Please first select  Well from the WellView", 5000, Position.BOTTOM_START)
//						.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
				 PB_Progress_Notification notification = new PB_Progress_Notification();
				   String selectWell =     PetrahubNotification_Utilities.getInstance().selectWellinWellboreView();
		  			notification.setText(selectWell);
		  			notification.open();
		  			notification.setDuration(3000);
			}
			} else {
				 PB_Progress_Notification notification = new PB_Progress_Notification();
				 String selectBasin =     PetrahubNotification_Utilities.getInstance().selectBasinWellboreView();
		  			notification.setText(selectBasin);
		  			notification.open();
		  			notification.setDuration(3000);
			}
		});

		editWellbore.addClickListener(event -> {

			WellboreListInfo wellboreInfo = wellboreGrid.asSingleSelect().getValue();
			if (wellboreInfo != null) {
				try {

					editWellborebuttonpopup();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {

				//Notification.show("Please first select  Wellbore from the Grid", 5000, Position.BOTTOM_START)
					//	.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
				 PB_Progress_Notification notification = new PB_Progress_Notification();
		
		  			notification.setText(selectWellbore);
		  			notification.open();
		  			notification.setDuration(3000);
			}

		});

		deleteWellbore.addClickListener(event -> {
			try {
				WellboreListInfo wellboreInfo = View_Update.getWellboreInfo();
				if (wellboreInfo != null) {
					delete_basin_buttonpopup();
				} else {
					 PB_Progress_Notification notification = new PB_Progress_Notification();
			  			
			  			notification.setText(selectWellbore);
			  			notification.open();
			  			notification.setDuration(3000);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		configWellbore.addClickListener(event -> {
		
			PB_Progress_Notification notificatiion = new PB_Progress_Notification();

		
			WellboreListInfo wellboreInfo = View_Update.getWellboreInfo();
			
			if (wellboreInfo != null) {
				
			
				UI.getCurrent().navigate("ConfigwellboreView");
			
				
			} else {

//				 PB_Progress_Notification notification = new PB_Progress_Notification();
				ui.access(() -> {	
					notificatiion.setImage("info");
				notificatiion .setText(selectWellbore);
				notificatiion .open();
				notificatiion .setDuration(3000);
				});
			}
		
			
			
		});


		listBainFromDB();
		basinChangeListner();
		wellComboBoxListener();
	}

	private void delete_basin_buttonpopup() {
		// TODO Auto-generated method stub

		WellListInfo wellInfo = wellcombobox.getValue();
		BasinViewGridInfo basinfo = basincombobox.getValue();
		if (wellInfo != null && basinfo != null) {
			Delete_Wellbore_popup_UI equationWindow = new Delete_Wellbore_popup_UI(wellboreGrid);

			equationWindow.open();

		} else {

PB_Progress_Notification notification = new PB_Progress_Notification();
String selectWell =     PetrahubNotification_Utilities.getInstance().selectWellinWellboreView();
  			notification.setText(selectWell);
  			notification.open();
  			notification.setDuration(3000);

		}

	}

	private void addWellborebuttonpopup() throws Exception {
		WellListInfo wellInfo = wellcombobox.getValue();
		BasinViewGridInfo basinfo = basincombobox.getValue();
		if (wellInfo != null && basinfo != null) {
			Add_wellbore_popup_up_UI equationWindow = new Add_wellbore_popup_up_UI(false, wellboreGrid, wellInfo);

			equationWindow.open();

		} else {

PB_Progress_Notification notification = new PB_Progress_Notification();
String selectWell =     PetrahubNotification_Utilities.getInstance().selectWellinWellboreView();
  			notification.setText(selectWell);
  			notification.open();
  			notification.setDuration(3000);

		}

	}

	private void editWellborebuttonpopup() throws Exception {
		WellListInfo wellInfo = wellcombobox.getValue();
	

		Add_wellbore_popup_up_UI equationWindow = new Add_wellbore_popup_up_UI(true, wellboreGrid, wellInfo);
		equationWindow.open();

	}

	
	private void listBainFromDB() throws SQLException {
		ExecutorService executor = Executors.newCachedThreadPool();
		PB_Progress_Notification notificatiion = new PB_Progress_Notification();
		String basinInformation =     PetrahubNotification_Utilities.getInstance().queryBasin();
		executor.submit(() -> {

			ui.access(() -> {

				notificatiion.setImage("info");
				notificatiion.setText(basinInformation);
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


	private void basinChangeListner() {

		basinComboBoxListerner = new ValueChangeListener<HasValue.ValueChangeEvent<BasinViewGridInfo>>() {

			@Override
			public void valueChanged(ValueChangeEvent<BasinViewGridInfo> event) {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				String queryWellList = PetrahubNotification_Utilities.getInstance().queryWellList();
				executor.submit(() -> {

					ui.access(() -> {

						notificatiion.setImage("info");
						notificatiion.setText(queryWellList);
						notificatiion.open();
					});
				// TODO Auto-generated method stub
				selectedBasin = event.getValue();
				if (selectedBasin != null) {
					try {
						wellDataProvider = new WellRegistryQuries().convertToListWell(selectedBasin.getBasinID());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ui.access(()-> {
					wellcombobox.setItemLabelGenerator(WellListInfo::getWellName);
					   wellDataProvider.setSortOrder(WellListInfo::getWellName, aSSENDINGDirection);
					wellcombobox.setDataProvider(wellDataProvider);
					notificatiion.close();
					});
				}
				});
			}
		};
		basincombobox.addValueChangeListener(basinComboBoxListerner);
		
		 View_Update.setBasinInfo(selectedBasin);
}
	
	private void wellComboBoxListener() {
		// TODO Auto-generated method stub
		wellComboBoxListerner = new ValueChangeListener<HasValue.ValueChangeEvent<WellListInfo>>() {

			@Override
			public void valueChanged(ValueChangeEvent<WellListInfo> event) {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				String queryWellboreList = PetrahubNotification_Utilities.getInstance().queryWellboreList();
				executor.submit(() -> {

					ui.access(() -> {

						notificatiion.setImage("info");
						notificatiion.setText(queryWellboreList);
						notificatiion.open();
					});
				// TODO Auto-generated method stub
				selectedWell = event.getValue();
				if (selectedWell != null) {

					try {
						wellboreDataProvider = new WellRegistryQuries().convertToListWellbore(selectedWell.getWellID(),selectedWell.getBasinID() );
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ui.access(() -> {
					wellboreDataProvider.setSortOrder(WellboreListInfo::getWellboreName,aSSENDINGDirection);
					wellboreGrid.setDataProvider(	wellboreDataProvider);
					notificatiion.close();
					});
				}
				});
			}
		};
		wellcombobox.addValueChangeListener(wellComboBoxListerner);
		View_Update.setWellInfo(selectedWell);
	}
	private void gridchangelistner() {
		wellboreGrid.addSelectionListener(listener -> {

			WellboreListInfo row = wellboreGrid.asSingleSelect().getValue();

			View_Update.setWellboreInfo(row);
			wellboreSettings.setSellectedWellboreGrid(row);

			projectSettings.getViews().setWellboreViewSettings(wellboreSettings);
			View_Update.setProjectsettingInfo(projectSettings);
			// VaadinService.getCurrentRequest().getWrappedSession().setAttribute("project_settings",
			// projectSettings);

		});
	}

}

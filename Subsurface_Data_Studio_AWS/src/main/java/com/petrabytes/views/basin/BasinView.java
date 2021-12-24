package com.petrabytes.views.basin;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.project.util.BasinViewProjectSettingInfo;
import com.petrabytes.project.util.ProjectSettingsInfo;
import com.petrabytes.project.util.WellLogsProjectViewsSettingsInfo;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.MainLayout;
import com.petrabytes.views.UI_Update;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.basin_registry_workset.basinCreateProjectUI;

import com.petrabytes.views.basin_registry_workset.deleteBasinprojectUI;

import com.petrabytes.views.wellRegistryQuery.WellRegistryQuries;
import com.petrabytes.views.wellbore.Blgz_Deviation_Info;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.ItemClickEvent;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;

@Route(value = "basin", layout = MainLayout.class)
@PageTitle("Basin")

public class BasinView extends VerticalLayout {

	private Button createBasinButton = null;
	private Button editBasinButton = null;
	private Button removeBasinButton = null;
	private SortDirection aSSENDINGDirection = SortDirection.ASCENDING;
	private ProjectSettingsInfo projectSettings = null;
	private BasinViewProjectSettingInfo basinSettings = null;
	private BasinViewGridInfo selectedBasin;
	public Grid<BasinViewGridInfo> basinGrid = new Grid<>();
	private final UI ui = UI.getCurrent();
	public BasinView() throws SQLException {
		setSizeFull();

		String projectname = (String) VaadinService.getCurrentRequest().getWrappedSession()
				.getAttribute("project_name");
		if (projectname == null) {
			String createProject = PetrahubNotification_Utilities.getInstance().createProject();
			PB_Progress_Notification notification = new PB_Progress_Notification();
			notification.setImage("info");
			notification.setText(createProject);
			notification.open();
			notification.setDuration(3000);

		} else {

			UI_Update.updateEnable_topBar();
			
			getProjectSetting();
			_setUI();

			gridchangelistner();

		}
	}

	private void _setUI() {
		String selectBasin = PetrahubNotification_Utilities.getInstance().selectBasin();
		HorizontalLayout basinButtonsLayout = new HorizontalLayout();

		createBasinButton = new Button();
		Image createBasinImage = new Image("icons" + File.separator + "16x" + File.separator + "add-16x.png",
				"Add basin");
		createBasinButton.getElement().setAttribute("title", "Create Basin");
		createBasinButton.setIcon(createBasinImage);

		editBasinButton = new Button();
		Image editBasinImage = new Image("icons" + File.separator + "16x" + File.separator + "edit_16.png",
				"Edit basin");
		editBasinButton.getElement().setAttribute("title", "Edit Basin");
		editBasinButton.setIcon(editBasinImage);

		removeBasinButton = new Button();
		Image removeBasinImage = new Image("icons" + File.separator + "16x" + File.separator + "delete16.png",
				"Remove basin");
		removeBasinButton.getElement().setAttribute("title", "Remove Basin");
		removeBasinButton.setIcon(removeBasinImage);

		basinButtonsLayout.add(createBasinButton, editBasinButton, removeBasinButton);
		basinButtonsLayout.setAlignSelf(Alignment.CENTER);
		// add(basinButtonsLayout);

		basinGrid.addColumn(BasinViewGridInfo::getBasinID).setHeader("Basin ID").setAutoWidth(true);
		basinGrid.addColumn(BasinViewGridInfo::getBasinName).setHeader("Basin Name").setAutoWidth(true);
		basinGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		basinGrid.setWidth("500px");
		basinGrid.setHeight("600px");
		basinGrid.setSelectionMode(SelectionMode.SINGLE);

		add(basinButtonsLayout, basinGrid);

		createBasinButton.addClickListener(event -> {
			try {

				add_basin_buttonpopup();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		editBasinButton.addClickListener(event -> {
			BasinViewGridInfo basinfo = View_Update.getBasinInfo();
			if (basinfo != null) {
				try {

					edit_basin_buttonpopup();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				PB_Progress_Notification notification = new PB_Progress_Notification();
				
				notification.setText(selectBasin);
				notification.open();
				notification.setDuration(3000);
			}

		});

		removeBasinButton.addClickListener(event -> {
			BasinViewGridInfo basinfo = View_Update.getBasinInfo();
			if (basinfo != null) {
				try {

					delete_basin_buttonpopup();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
PB_Progress_Notification notification = new PB_Progress_Notification();
				
				notification.setText(selectBasin);
				notification.open();
				notification.setDuration(3000);
			}
		});

		populateBasinGrid();

	}

//	}

	private void getProjectSetting() {
		// TODO Auto-generated method stub
		projectSettings = View_Update.getProjectsettingInfo();
		if (projectSettings != null) {
			basinSettings = projectSettings.getViews().getBasinSetting();
			if (basinSettings == null)
				basinSettings = new BasinViewProjectSettingInfo();
		}
	}

	private void populateBasinGrid() {
		ExecutorService executor = Executors.newCachedThreadPool();
		PB_Progress_Notification notificatiion = new PB_Progress_Notification();
		String queryBasin = PetrahubNotification_Utilities.getInstance().queryBasin();
		executor.submit(() -> {

			ui.access(() -> {

				notificatiion.setImage("info");
				notificatiion.setText(queryBasin);
				notificatiion.open();
			});
	
		try {

			ListDataProvider<BasinViewGridInfo> poplateDataBasin = new WellRegistryQuries().convertToListBasin();

			// basinGrid.setItems((Collection<BasinViewGridInfo>) poplateDataBasin);
			poplateDataBasin.setSortOrder(BasinViewGridInfo::getBasinName, aSSENDINGDirection);
			basinGrid.setDataProvider(poplateDataBasin);
			System.out.println("Refresh");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ui.access(() -> {

			
			notificatiion.close();
		});

		});
	}

	private void gridchangelistner() {

		basinGrid.addSelectionListener(listener -> {
			BasinViewGridInfo row = basinGrid.asSingleSelect().getValue();

			View_Update.setBasinInfo(row);
			basinSettings.setSelectedBasinGrid(row);

			projectSettings.getViews().setBasinSetting(basinSettings);
			View_Update.setProjectsettingInfo(projectSettings);
			// VaadinService.getCurrentRequest().getWrappedSession().setAttribute("project_settings",
			// projectSettings);

		});
	}

	private void edit_basin_buttonpopup() {
		// TODO Auto-generated method stub

		basinCreateProjectUI equationWindow = new basinCreateProjectUI(true, basinGrid);
		equationWindow.open();

	}

	private void add_basin_buttonpopup() throws Exception {

		basinCreateProjectUI equationWindow = new basinCreateProjectUI(false, basinGrid);

		equationWindow.open();

	}

	private void delete_basin_buttonpopup() throws Exception {
		deleteBasinprojectUI equationWindow = new deleteBasinprojectUI(basinGrid);

		equationWindow.open();

	}

}

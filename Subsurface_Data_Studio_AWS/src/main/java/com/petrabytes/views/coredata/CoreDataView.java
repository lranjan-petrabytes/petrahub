package com.petrabytes.views.coredata;

import java.io.ByteArrayInputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.project.util.BasinViewProjectSettingInfo;
import com.petrabytes.project.util.CoreDataSettingInfo;
import com.petrabytes.project.util.ProjectSettingsInfo;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.MainLayout;
import com.petrabytes.views.UI_Update;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.welllogs.WellLogQueries;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.router.PageTitle;

@Route(value = "core-data", layout = MainLayout.class)
@PageTitle("Core Data")
public class CoreDataView extends VerticalLayout {

//	private Button renderButton = new Button("Show");
	private Image image = new Image();
	private final UI ui = UI.getCurrent();
	private ComboBox<String> imageComboBox = new ComboBox<>();
	private ProjectSettingsInfo projectSettings = null;
	private CoreDataSettingInfo basinSettings = null;
	public CoreDataView() {
		
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
		_SetUI();

		
		}
	}
	
	private void getProjectSetting() {
		// TODO Auto-generated method stub
		projectSettings = View_Update.getProjectsettingInfo();
		if (projectSettings != null) {
			basinSettings = projectSettings.getViews().getCoreDataSettings();
			if (basinSettings == null)
				basinSettings = new CoreDataSettingInfo();
		}
	}

	private void _SetUI() {

		this.setSizeFull();

		Label label = new Label("WELL DEPTH");
		FlexLayout actionLayout = new FlexLayout();
		actionLayout.setWidth("300px");
		actionLayout.add(label, imageComboBox);
		actionLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.BETWEEN);
		actionLayout.setAlignItems(FlexComponent.Alignment.CENTER);

		VerticalLayout imageLayout = new VerticalLayout();
		imageLayout.setSizeFull();
		image.setSizeFull();
		imageLayout.add(image);

		this.add(actionLayout, imageLayout);
		this.expand(imageLayout);
		
		populateComboBox();

		
		
		String selectedBasin  = basinSettings.getSelectedBasin();
		if (selectedBasin != null) {
			imageComboBox.setValue(selectedBasin);
			
			buttonEvent(false);
		}
		
		buttonEvent(true);

	}

	private void buttonEvent(Boolean userClick) {
		// TODO Auto-generated method stub
        if(userClick) {
		imageComboBox.addValueChangeListener(listener-> {
			
			ExecutorService executor = Executors.newCachedThreadPool();
			PB_Progress_Notification notificatiion = new PB_Progress_Notification();
			String coreDataImage = PetrahubNotification_Utilities.getInstance().coreDataView();
			executor.submit(() -> {

				ui.access(() -> {

					notificatiion.setImage("info");
					notificatiion.setText(coreDataImage);
					notificatiion.open();
				});

		

		
			String imageIndexQuery = imageComboBox.getValue();
			byte[] imageBytes = PB_Image_DB.imageQueryDB(imageIndexQuery);
		
			if (imageBytes != null) {
				try {
					StreamResource resource = new StreamResource("sample", () -> new ByteArrayInputStream(imageBytes));
					ui.access(() -> {
						image.setSrc(resource);
						notificatiion.close();
					});
				
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
			basinSettings.setSelectedBasin(imageIndexQuery);

			projectSettings.getViews().setCoreDataSettings(basinSettings);
			ui.access(() -> {
			View_Update.setProjectsettingInfo(projectSettings);
			});

			});
		});
        } else {
        	String imageIndexQuery = imageComboBox.getValue();
			byte[] imageBytes = PB_Image_DB.imageQueryDB(imageIndexQuery);
			if (imageBytes != null) {
				try {
					StreamResource resource = new StreamResource("sample", () -> new ByteArrayInputStream(imageBytes));
					image.setSrc(resource);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
        }
	}

	private void populateComboBox() {
		String query = "select depth from pb_core_data.core_data_image";
		// WellLogQueries logsQuery = new WellLogQueries();
		List<String> imageDepths = PB_Image_DB.readImageDepths(query);
		  Collections.sort(imageDepths);
		imageComboBox.setItems(imageDepths);

	}

}

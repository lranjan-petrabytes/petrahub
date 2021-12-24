package com.petrabytes.toptoolUI;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import org.apache.commons.lang3.exception.ExceptionUtils;

//import com.example.application.views.main.MainView;
import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.project.util.ProjectQueries;
import com.petrabytes.project.util.ProjectSettingsInfo;
import com.petrabytes.project.util.ViewsSettingsInfo;
import com.petrabytes.project.util.WellLogsProjectViewsSettingsInfo;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.MainLayout;
import com.petrabytes.views.View_Update;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.server.VaadinService;

public class Blgz_CreateProject_UI extends PetrabyteUI_Dialog {

	private Label text = new Label("Enter Project Name");
	protected TextField projectNameField = new TextField();
	private Random uniqueID = new Random();
	private String disableKeys = "addProject,openProject,deleteProject";
	private String enableKeys = "saveProject,closeProject";

	public Blgz_CreateProject_UI() {
		;
		//projectNameField.setValue("Project Name");
		 setDefaultProjectName();
		setUI();
		createProjectEvent();

	}

	private void setUI() {
		projectNameField.setWidth("100%");
		this.setImage("icons" + File.separator + "purple-24x" + File.separator + "add-24x.png");
		VerticalLayout layout = new VerticalLayout(text, projectNameField);
		layout.getStyle().set("padding-bottom", "0px");
		layout.setWidth("100%");
		mainLayout.setWidth("360px");
		mainLayout.setHeight("190px");
		text.getStyle().set("margin-bottom", "-15px");
		this.content.add(layout);
		this.setTitle("New Project");
		this.setButtonName("OK");

	}
	private void setDefaultProjectName() {
		// TODO Auto-generated method stub

		try {
			LocalDateTime datetime = LocalDateTime.now();
			DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yy_hh:mm:ss");
			String formatDateTime = datetime.format(format);
			String projectName = "Project" + "_" + formatDateTime;
			projectNameField.setValue(projectName);
		} catch (Exception e) {
			// TODO: handle exception
			
		}
	}

	private void createProjectEvent() {

		this.saveButton.addClickListener(event -> {

			String projectID = String.valueOf(10000000 + uniqueID.nextInt(90000000));
			String projectName = projectNameField.getValue();
			int isExist = new ProjectQueries().projectExist_ByName(projectName);
			if (isExist == 0) {

				ProjectSettingsInfo projectSettings = new ProjectSettingsInfo();
				VaadinService.getCurrentRequest().getWrappedSession().setAttribute("project_name", projectName);
				VaadinService.getCurrentRequest().getWrappedSession().setAttribute("project_id", projectID);

				ViewsSettingsInfo viewSettings = new ViewsSettingsInfo();

				projectSettings.setViews(viewSettings);

				//VaadinService.getCurrentRequest().getWrappedSession().setAttribute("project_settings", projectSettings);
				 View_Update.setProjectsettingInfo(projectSettings);
				MainLayout mainViewLayout = getInstance();
				mainViewLayout.updateProjectName(projectName);

				/*
				 * disable
				 */
				UI.getCurrent().getPage().executeJavaScript("disableComponents($0)", disableKeys);
				/*
				 * enable
				 */
				UI.getCurrent().getPage().executeJavaScript("enableComponents($0)", enableKeys);
				this.close();
			} else {
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				notificatiion.setImage("info");
				notificatiion.setText("Project name already exists");
				notificatiion.setDuration(3000);
				notificatiion.open();

			}
		});
	}

	public TextField getProjectNameField() {
		return projectNameField;
	}

	public void setProjectNameField(TextField projectNameField) {
		this.projectNameField = projectNameField;
	}

	public Random getUniqueID() {
		return uniqueID;
	}

	public void setUniqueID(Random uniqueID) {
		this.uniqueID = uniqueID;
	}

	/**
	 * Returns the current AppLayout
	 * 
	 * @return
	 */
	public static MainLayout getInstance() {
		return (MainLayout) UI.getCurrent().getChildren().filter(component -> component.getClass() == MainLayout.class)
				.findFirst().orElse(null);
	}
}

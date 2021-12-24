package com.petrabytes.toptoolUI;



import java.io.File;
import java.sql.SQLException;

import org.json.JSONObject;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;

import com.petrabytes.project.util.WellLogsProjectViewsSettingsInfo;
import com.petrabytes.views.View_Update;
import com.petrabytes.project.util.ProjectInfo;
import com.petrabytes.project.util.ProjectQueries;
import com.petrabytes.project.util.ProjectSettingsInfo;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinService;

public class Blgz_SaveProject_UI extends PetrabyteUI_Dialog {

	private VerticalLayout mainLayout = new VerticalLayout();
	private Label messageLabel1 = new Label();

	public Blgz_SaveProject_UI() {
		// TODO Auto-generated constructor stub
		this.setTitle("Save Project");
		this.setImage("icons"+File.separator+"purple-24x"+File.separator+"save-24x.png");
		this.setButtonName("OK");
		messageLabel1.setText("Do you want to save the project?");
	//	this.SetMessage("Do you want to save the project?");
		mainLayout.setWidth("300px");
		mainLayout.setHeight("100px");
		this.content.add(mainLayout);
		SaveDialogUI();
		saveProjectClickAction();

	}

	private void SaveDialogUI() {
		// TODO Auto-generated method stub

		HorizontalLayout headerlayout = new HorizontalLayout();
		headerlayout.add(messageLabel1);
		messageLabel1.setWidth("100%");
	
		add(headerlayout);
		
		mainLayout.add(headerlayout);
	}

	private void saveProjectClickAction() {
		this.saveButton.addClickListener(event -> {
			ProjectInfo project = new ProjectInfo();
		//	ProjectSettingsInfo projectSettings = (ProjectSettingsInfo) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("project_settings");
			ProjectSettingsInfo projectSettings = View_Update.getProjectsettingInfo();
			String  projectname = (String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("project_name");
			
			String projectid =(String)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("project_id");
			project.setProjectName(projectname);
			project.setProjectID(projectid);
			project.setProjectSettings(projectSettings);
			JSONObject jsonObject = new JSONObject(project);
			System.out.println(jsonObject);
			if(project == null) {
				Notification.show("Please create a Project, then save", 3000, Position.BOTTOM_START).addThemeVariants(NotificationVariant.LUMO_CONTRAST);
			}else {
				
				Notification.show("Project is saved", 3000, Position.BOTTOM_START).addThemeVariants(NotificationVariant.LUMO_CONTRAST);

			}
			
			try {
				new ProjectQueries().updateDataInDeltalake(projectname, projectid, jsonObject);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.close();
			
		});

	}

}

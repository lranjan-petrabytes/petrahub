package com.petrabytes.toptoolUI;

import java.io.File;
import java.sql.SQLException;


import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.project.util.OpenProjectInfo;
import com.petrabytes.project.util.ProjectQueries;
import com.petrabytes.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinService;

public class User_Confermation_Delete_Project_UI extends PetrabyteUI_Dialog {

	private VerticalLayout mainLayout = new VerticalLayout();
	private Label messageLabel1 =   new Label();


	public User_Confermation_Delete_Project_UI() {

		this.setTitle("Delete Project");
		this.setImage("icons" + File.separator + "purple-24x" + File.separator + "open-24x.png");
		this.setButtonName("OK");
		String projectName =	(String) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("project_name");
		messageLabel1.setText("Are you sure, you want to delete the project: "+projectName+"");
	//	this.SetMessage("Are you sure, you want to delete the project: "+projectName+"");
		mainLayout.setWidth("300px");
		mainLayout.setHeight("100px");
		this.content.add(mainLayout);
		SaveDialogUI();
		//deleteProjectClickAction();
		deleteprojectInformation();
	//	 projectInformation(selectedProject);
	}

	private void SaveDialogUI() {
		// TODO Auto-generated method stub

		HorizontalLayout headerlayout = new HorizontalLayout();
		headerlayout.add(messageLabel1);
		messageLabel1.setWidth("100%");
		// messageLabel.getStyle().set("padding-left", "20px");

		add(headerlayout);

		mainLayout.add(headerlayout);
	}


	private void deleteprojectInformation() {
		
		this.saveButton.addClickListener(event -> {
			

		
		String projectid =	(String) VaadinService.getCurrentRequest().getWrappedSession().getAttribute("project_id");
		
		

			try {
				new ProjectQueries().deleteDataInDeltalake(projectid);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.close();
			VaadinService.getCurrentRequest().getWrappedSession().removeAttribute("project_name");
			VaadinService.getCurrentRequest().getWrappedSession().removeAttribute("project_id");
			VaadinService.getCurrentRequest().getWrappedSession().removeAttribute("project_settings");
			
			MainLayout mainViewLayout = getInstance();
			mainViewLayout.updateProjectName("");

		});
		
	}
	
	public static MainLayout getInstance() {
		return (MainLayout) UI.getCurrent().getChildren().filter(component -> component.getClass() == MainLayout.class)
				.findFirst().orElse(null);
	}
}

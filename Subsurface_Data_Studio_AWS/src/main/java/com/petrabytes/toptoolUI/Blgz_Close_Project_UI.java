package com.petrabytes.toptoolUI;

import java.io.File;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.petrabytes.dashboard.Dashboard_Update;
import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.project.util.ProjectQueries;
import com.petrabytes.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;

public class Blgz_Close_Project_UI extends PetrabyteUI_Dialog {

	// private Bluegridz_Logger logger =
	// Bluegridz_Logger_Factory.getCurrentSessionLogger();
	private VerticalLayout mainLayout = new VerticalLayout();
	private Label messageLabel1 = new Label();

	private String enableKeys = "addProject,openProject,deleteProject";
	private String disableKeys = "saveProject,closeProject";

	public Blgz_Close_Project_UI() {
		// TODO Auto-generated constructor stub

		this.setTitle("Close Project");
		this.setImage("icons" + File.separator + "purple-24x" + File.separator + "close-24x.png");
		this.setButtonName("OK");
		messageLabel1.setText("Do you want to close the project?");
		// this.SetMessage("Are you sure, you want to close the project without
		// saving?");
		mainLayout.setWidth("300px");
		mainLayout.setHeight("100px");
		this.content.add(mainLayout);
		SaveDialogUI();
		closeButtonClickAction();

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

	public void closeButtonClickAction() {
		this.saveButton.addClickListener(event -> {

			String projectid = (String) VaadinService.getCurrentRequest().getWrappedSession()
					.getAttribute("project_id");

			int isExist = new ProjectQueries().projectExist_ByID(projectid);
			if (isExist == 0) {
				Blgz_Save_N_CloseProject_Window save_N_CloseProject_Window = new Blgz_Save_N_CloseProject_Window();
				save_N_CloseProject_Window.open();
				save_N_CloseProject_Window.addOpenedChangeListener(e -> {
					if (!e.isOpened()) {
						clearActiveProject();
					}
				});
			} else {
				clearActiveProject();
			}

		});

	}

	private void clearActiveProject() {
		VaadinService.getCurrentRequest().getWrappedSession().removeAttribute("project_name");
		VaadinService.getCurrentRequest().getWrappedSession().removeAttribute("project_id");
		VaadinService.getCurrentRequest().getWrappedSession().removeAttribute("project_settings");

		MainLayout mainViewLayout = getInstance();
		mainViewLayout.updateProjectName("");

		// UI.getCurrent().getPage().reload();
		this.close();

		/*
		 * disable
		 */
		UI.getCurrent().getPage().executeJavaScript("disableComponents($0)", disableKeys);
		/*
		 * enable
		 */
		UI.getCurrent().getPage().executeJavaScript("enableComponents($0)", enableKeys);

		Dashboard_Update.removeCenter();

	}

	public static MainLayout getInstance() {
		return (MainLayout) UI.getCurrent().getChildren().filter(component -> component.getClass() == MainLayout.class)
				.findFirst().orElse(null);
	}
}

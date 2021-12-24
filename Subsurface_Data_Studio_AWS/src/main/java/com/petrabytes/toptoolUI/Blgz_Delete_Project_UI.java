package com.petrabytes.toptoolUI;

import java.util.List;
import java.util.Set;

import com.vaadin.flow.component.listbox.ListBox;

import java.io.File;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.json.JSONObject;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.project.util.OpenProjectInfo;
import com.petrabytes.project.util.ProjectInfo;
import com.petrabytes.project.util.ProjectQueries;
import com.petrabytes.project.util.ProjectSettingsInfo;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.MainLayout;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinSession;

public class Blgz_Delete_Project_UI extends PetrabyteUI_Dialog {

	private Grid<OpenProjectInfo> projectGrid = new Grid<>();
	private FormLayout summaryLayout = new FormLayout();

	private Label projectIDLabel = new Label();
	private SortDirection aSDENDINGDirection = SortDirection.DESCENDING;
	private Label projectINameLabel = new Label();

	private Label userNameLabel = new Label();

	private Label userID = new Label();

	private Label projectStatus = new Label();

	private Label projectType = new Label();

	private Label lastmodifiedLabel = new Label();

	Label projectLabel = new Label();
	private final UI ui = UI.getCurrent();
	public Blgz_Delete_Project_UI() {

		SetDialogUI();
		this.setHeight("750px");
		this.setWidth("1100px");
		this.setButtonName("OK");

	}

	private void SetDialogUI() {
		// TODO Auto-generated method stub
		VerticalLayout centerLayout = new VerticalLayout();
		centerLayout.setSizeUndefined();
		centerLayout.setPadding(false);
		centerLayout.getElement().getStyle().set("padding-left", "13px");
		HorizontalLayout projectLayout = new HorizontalLayout(projectGrid, summaryLayout);
		projectLayout.getStyle().set("padding-top", "20px");
		projectLayout.setPadding(false);
		projectGrid.addColumn(OpenProjectInfo::getProjectName).setHeader("Project Name").setAutoWidth(true);
		projectGrid.addColumn(OpenProjectInfo::getUserName).setHeader("User Name").setAutoWidth(true);
		projectGrid.addColumn(OpenProjectInfo::getLastModifiedDate).setHeader("Last Modified").setAutoWidth(true);

		// projectGrid.setSelectionMode(SelectionMode.MULTI);

		projectGrid.setWidth("760px");
		projectGrid.setHeight("570px");
		summaryLayout.setWidth("260px");

		summaryLayout.getElement().getStyle().set("font-weight", "bold");
		summaryLayout.setClassName("geo_prjctGrid");
		centerLayout.add(projectLayout);
		centerLayout.expand(projectLayout);

		Label summaryLAbel = new Label("Summary");
		summaryLAbel.getStyle().set("font-weight", "bold");
		summaryLAbel.getStyle().set("margin-top", "15px");
		summaryLAbel.getStyle().set("pading-left", "10px");
		summaryLayout.getStyle().set("padding-left", "10px");
		summaryLayout.add(summaryLAbel);
		summaryLayout.addFormItem(projectIDLabel, "Project ID: ");
		projectIDLabel.getStyle().set("font-weight", "bold");
		summaryLayout.addFormItem(projectINameLabel, "Project Name: ");
//		summaryLayout.addFormItem(userID, "User ID:");
		projectINameLabel.getStyle().set("font-weight", "bold");
		summaryLayout.addFormItem(userNameLabel, "User Name:");

		summaryLayout.addFormItem(lastmodifiedLabel, "Last Modified:");
		summaryLayout.addFormItem(projectType, "Project Type:");
//		summaryLayout.addFormItem(projectStatus, "Project Status:");

		this.content.add(centerLayout);
		this.setTitle("Delete Project");
		this.setButtonName("OK");

		this.setImage("icons" + File.separator + "purple-24x" + File.separator + "delete24.png");

		displayProjectList();
		saveProjectClickAction();

		updateProjectSummary();
	}

	private void updateProjectSummary() {
		// TODO Auto-generated method stub
		projectGrid.addItemClickListener(event -> {
			OpenProjectInfo selectedProject = projectGrid.asSingleSelect().getValue();
			System.out.println(selectedProject);
			projectIDLabel.setText(selectedProject.getProjectID());
			projectINameLabel.setText(selectedProject.getProjectName());
			userNameLabel.setText(selectedProject.getUserName());
			lastmodifiedLabel.setText(selectedProject.getLastModifiedDate());
			userID.setText(selectedProject.getUserID());
			projectType.setText(selectedProject.getProjectType());
			projectStatus.setText(selectedProject.getProjectStatus());
		});
	}

	private void displayProjectList() {
		// TODO Auto-generated method stub
		ExecutorService executor = Executors.newCachedThreadPool();
		PB_Progress_Notification notificatiion = new PB_Progress_Notification();

		executor.submit(() -> {

			ui.access(() -> {

				notificatiion.setImage("info");
				notificatiion.setText("Querying Deltalake: Fetching Project List from Petrahub");
				notificatiion.open();
			});
			try {
				List<OpenProjectInfo> projectList = new ProjectQueries().fetchProjectsFromDeltalake();
				ListDataProvider<OpenProjectInfo> project = (ListDataProvider<OpenProjectInfo>) DataProvider.ofCollection((Collection) projectList);
				ui.access(() -> {
				//	basin.setSortOrder(BasinViewGridInfo::getBasinName, aSSENDINGDirection);
				project.setSortOrder(OpenProjectInfo::getLastModifiedDate,aSDENDINGDirection);
					//projectGrid.setSortableColumns();
					
					projectGrid.setDataProvider(project);
				});
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				ui.access(() -> {
					notificatiion.close();
				});
			}

		});
	}

	private void saveProjectClickAction() {
		this.saveButton.addClickListener(event -> {
			try {
				String status = Blgz_OpenProject_UI.getCurrentClusterStatusJava();
				if (status.equalsIgnoreCase("running")) {
					OpenProjectInfo selectedProject = projectGrid.asSingleSelect().getValue();
//					OpenProjectInfo resultdata = new ProjectQueries().getProjectBYID(selectedProject.getProjectID(),
//							selectedProject.getUserName());
//					if (resultdata != null) {
//			String projectid = selectedProject.getProjectID();
						VaadinService.getCurrentRequest().getWrappedSession().setAttribute("project_id",
								selectedProject.getProjectID());

						VaadinService.getCurrentRequest().getWrappedSession().setAttribute("project_name",
								selectedProject.getProjectName());

						try {
							deletebuttonpopup();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

//					} else {
//						System.out.println("project setting data doest not exist");
//					}

					this.close();

				} else {
					PB_Progress_Notification notificatiion = new PB_Progress_Notification();

					notificatiion.setText("Cluster Not Started");
					notificatiion.open();
                   notificatiion.setDuration(3000);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});

	}

	private void deletebuttonpopup() throws Exception {

		User_Confermation_Delete_Project_UI equationWindow = new User_Confermation_Delete_Project_UI();
		equationWindow.open();

	}

	public static MainLayout getInstance() {
		return (MainLayout) UI.getCurrent().getChildren().filter(component -> component.getClass() == MainLayout.class)
				.findFirst().orElse(null);
	}

}

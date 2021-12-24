package com.petrabytes.toptoolUI;

import java.io.File;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.petrabytes.dashboard.Dashboard_Update;
import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.keyvault.AWS_Key_Vault;
import com.petrabytes.keyvault.PH_KeyVault;
import com.petrabytes.project.util.OpenProjectInfo;
import com.petrabytes.project.util.ProjectQueries;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.MainLayout;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.gis.GISView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.charts.model.TimeUnit;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
//import com.vaadin.ui.themes.ValoTheme;

public class Blgz_OpenProject_UI extends PetrabyteUI_Dialog {

	private Grid<OpenProjectInfo> projectGrid = new Grid<>();
	private FormLayout summaryLayout = new FormLayout();

	private Label projectIDLabel = new Label();

	private Label projectINameLabel = new Label();

	private Label userNameLabel = new Label();

	private Label userID = new Label();

	private Label projectStatus = new Label();

	private Label projectType = new Label();
	private SortDirection aSDENDINGDirection = SortDirection.DESCENDING;
	private Label lastmodifiedLabel = new Label();
	private final UI ui = UI.getCurrent();
	private ListDataProvider<OpenProjectInfo> dataProvider;
	private StringBuffer _selectedProjectInfo = new StringBuffer();

	Label projectLabel = new Label();

	private String disableKeys = "addProject,openProject,deleteProject";
	private String enableKeys = "saveProject,closeProject";

	public Blgz_OpenProject_UI() {
		// TODO Auto-generated constructor stub
		SetDialogUI();
		this.setButtonName("OK");
		this.setHeight("750px");
		this.setWidth("1050px");
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

		projectGrid.setWidth("660px");
		projectGrid.setHeight("570px");
		summaryLayout.setWidth("320px");

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
		this.setTitle("Open Project");
		// this.setButtonName("Open");
		closeButton.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);

		this.setImage("icons" + File.separator + "purple-24x" + File.separator + "open-24x.png");

		displayProjectList();
		openProjectEvent();
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

	private void openProjectEvent() {
		// TODO Auto-generated method stub
		this.saveButton.addClickListener(event -> {

			try {
				String status = getCurrentClusterStatusJava();
				if (status.equalsIgnoreCase("running")) {
					OpenProjectInfo selectedProject = projectGrid.asSingleSelect().getValue();

					OpenProjectInfo resultdata = new ProjectQueries().getProjectBYID(selectedProject.getProjectID(),
							selectedProject.getUserName());
					if (resultdata != null) {
						String projectName = selectedProject.getProjectName();
						VaadinService.getCurrentRequest().getWrappedSession().setAttribute("project_name", projectName);
						VaadinService.getCurrentRequest().getWrappedSession().setAttribute("project_id",
								selectedProject.getProjectID());
					//	VaadinService.getCurrentRequest().getWrappedSession().setAttribute("project_settings",
						//		selectedProject.getProjectSettings().getProjectSettings());
                         View_Update.setProjectsettingInfo(selectedProject.getProjectSettings().getProjectSettings());
						MainLayout mainViewLayout = getInstance();
						mainViewLayout.updateProjectName(projectName);

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

						System.out.println(UI.getCurrent().getInternals().getTitle());

						Dashboard_Update.updateCenter(UI.getCurrent().getInternals().getTitle());

					} else {
						System.out.println("project setting data doest not exist");
					}
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

	public static String getCurrentClusterStatusJava() throws Exception {

		// Sending get request
//		String url_value = PH_KeyVault.getSecretKey("Databricks-URL");
//		String token_value = PH_KeyVault.getSecretKey("Databricks-Token");
//		String cluster_id = PH_KeyVault.getSecretKey("Databricks-Cluster");
//		
//		url_value = url_value + "/api/2.0/clusters/get?cluster_id=" + cluster_id;
		
		String url_value;
		String token_value;
		String cluster_id;
		String databricks_status = PH_KeyVault.getSecretKey("Databricks-Status");
		if (databricks_status.equalsIgnoreCase("azure")) {
			url_value = PH_KeyVault.getSecretKey("Databricks-URL");
			token_value = PH_KeyVault.getSecretKey("Databricks-Token");
			cluster_id = PH_KeyVault.getSecretKey("Databricks-Cluster");
		} else {
			url_value = AWS_Key_Vault.getSecret("Databricks-URL");
			token_value = AWS_Key_Vault.getSecret("Databricks-Token");
			cluster_id = AWS_Key_Vault.getSecret("Databricks-Cluster");
		}
		
		int indexOfCloud = url_value.indexOf("cloud");
        if (indexOfCloud == -1) {
        
        	url_value = url_value + "/api/2.0/clusters/get?cluster_id=" + cluster_id;
        } else {
        	// aws
        	int indexOfCom = url_value.indexOf("com") + 3;
        	url_value = url_value.substring(0, indexOfCom);
        	url_value = url_value + "/api/2.0/clusters/get?cluster_id=" + cluster_id;
        }
		URL url = new URL(url_value);
//				"https://adb-3305711601758284.4.azuredatabricks.net/api/2.0/clusters/get?cluster_id=0304-081914-quits100");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		conn.setRequestProperty("Authorization", "Bearer " + token_value);

		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestMethod("GET");

		JSONObject jsonObject = new JSONObject(IOUtils.toString(conn.getInputStream(), Charset.forName("UTF-8")));
		// printing result from response
		System.out.println("Cluster " + jsonObject.getString("state"));
		return jsonObject.getString("state");

	}

	public static MainLayout getInstance() {
		return (MainLayout) UI.getCurrent().getChildren().filter(component -> component.getClass() == MainLayout.class)
				.findFirst().orElse(null);
	}
}

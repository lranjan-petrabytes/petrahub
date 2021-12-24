package com.petrabytes.views.osdudata;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


import com.petrabytes.databricks.invokeDatabricksJob;
import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.keyvault.AWS_Key_Vault;
import com.petrabytes.keyvault.PH_KeyVault;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.MainLayout;
import com.petrabytes.views.UI_Update;
import com.petrabytes.views.osdu.OSDUQueries;
import com.petrabytes.views.osdu.OSDUWellboreTrajectoryInfo;
import com.petrabytes.views.osduWellLogs.OsduWellLogs.PB_FlexLayout;
import com.petrabytes.views.wellRegistryQuery.WellboreQueries;
import com.petrabytes.views.wellbore.Blgz_Deviation_Info;
import com.petrabytes.views.welllogs.WellLogQueries;
import com.petrabytes.views.welllogs.WellLogsInfo;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;

@Route(value = "OSDUDATA", layout = MainLayout.class)
@PageTitle("OSDUDATA")
public class OsduDataView extends HorizontalLayout{
	private VerticalLayout mainLayot = new VerticalLayout();
	private VerticalLayout vLayout = new VerticalLayout();
	private FlexLayout mLayout = new FlexLayout();
	private PasswordField apiToken = new PasswordField();
	private FlexLayout importLayot = new FlexLayout();
	private Button button = new Button(VaadinIcon.ANGLE_DOUBLE_LEFT.create());
	private Grid<OsduDataInfo> datagrid = new Grid<>();
	private Button searchButton = new Button();
	private SortDirection aSSENDINGDirection = SortDirection.ASCENDING;
	private Button importButton;
//	private FlexLayout mainLayout = new FlexLayout();
//	private Div plotLayout = new Div();
	public OsduDataView() {
		
		this.setSizeFull();
		this.setSpacing(false);
		this.setPadding(false);
		vLayout.setSpacing(false);
		vLayout.setPadding(false);
		vLayout.setSizeUndefined();
		mainLayot.setSizeFull();

		mLayout.setSizeFull();
		Label label = new Label("Settings");

		label.addClassName("label-rotate");
		FlexLayout labellayout = new FlexLayout(label);
		labellayout.setWidth("41px");
		HorizontalLayout layout = new HorizontalLayout(labellayout, mLayout);
		layout.expand(mLayout);
		layout.setHeightFull();
		layout.setSpacing(false);
		layout.setPadding(false);

		Label label2 = new Label("Settings");
		label2.addClassName("settings_label2");
		HorizontalLayout headLayout = new HorizontalLayout(button, label2);
		headLayout.setSpacing(true);
		headLayout.setPadding(false);
		label2.setVisible(false);
		headLayout.getElement().getStyle().set("font-weight", "bold");
		
		vLayout.add(headLayout,layout);
		vLayout.getStyle().set("border", "1px solid");
		vLayout.getStyle().set("border-color"," #D1D1D1");
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
		setui();
		createEditorLayout();
		this.add(mainLayot, vLayout);
		this.expand(mainLayot);
//		mLayout.focus();
		mLayout.setVisible(false);
//		createPlotLayout();
		apiToken.setValue(
				"Bearer eyJraWQiOiJ3c0tYMlp1K2NXS0FsMklWVHhaXC82NGJUYzJKXC93cjJ5dWpWa0FLNGFOQVU9IiwiYWxnIjoiUlMyNTYifQ.eyJvcmlnaW5fanRpIjoiZjUyOTAwMDUtZTYyNS00MTk5LWJjYTYtM2MxMWY4N2NhMTc3Iiwic3ViIjoiNDQ2ODg3ZTQtMGM2Yy00NGNjLTlmNjYtZWZmOGQ1NWVhNWUyIiwiZXZlbnRfaWQiOiJiNzIyNGJlOC01MTc4LTQyYmUtOTk4NS04MTJkMjQxNWVhMzUiLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6ImF3cy5jb2duaXRvLnNpZ25pbi51c2VyLmFkbWluIiwiYXV0aF90aW1lIjoxNjM0MDIzODEyLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9HSDY2ejVnVnAiLCJleHAiOjE2MzQxMzk3NzcsImlhdCI6MTYzNDEyNTM3NywianRpIjoiNWM2NTQ1YWUtMzQ3ZS00N2UwLWE5NTAtY2M4YmZhNDJkNGMwIiwiY2xpZW50X2lkIjoiZWVnNmtxZ3JzNDkwZXQ4M2gxZThnbmVyaCIsInVzZXJuYW1lIjoic2FzaGlndW50dXJ1QHBldHJhYnl0ZXMuY29tIn0.ZuixJlE81Yk9ZrIdWBSHYDO4IBJ63Ssp2smlDfocAVxDzraxJ6gfxZjzIp-IIEkBbDayYq234t987C5wCnGiBlaOPobC17X9bKOxZ1eSgVzhlASFtmWpOU_VGyD9qMckT-GlPubiFkDA1-Dd7lS8CshvHv80XGQ03OV3VQhF6PZdX1Fwq32UdMgPf-HSVggcaC0SPiG90nNg2HuMd3Gnn5sAcHK494LTK_nc1TmVHzyf0TKmpnI-Xf4I1jXcHSb6rzcTY1QGKwWlkDkLSpKYj3isaOGhggzL5QoiIICpx0OK0ezy4s7ILutRUumNCwb7jZoh1L-7UBMetZ5cNUPTMQ");
		mLayout.setVisible(false);
		add(mainLayot, vLayout);
	
			button.addClickListener(event -> {
				if (mLayout.isVisible()) {
					mLayout.setVisible(false);
					labellayout.setVisible(true);
					label2.setVisible(false);
					button.setIcon(VaadinIcon.ANGLE_DOUBLE_LEFT.create());
				} else {
					mLayout.setVisible(true);
					labellayout.setVisible(false);
					label2.setVisible(true);
//					mLayout.focus();
					button.setIcon(VaadinIcon.ANGLE_DOUBLE_RIGHT.create());
				}

			});
			
		}	
	}
	private void createEditorLayout() {
		// TODO Auto-generated method stub
		

		Div editorLayoutDiv = new Div();
		editorLayoutDiv.setHeightFull();
		editorLayoutDiv.setClassName("flex flex-col");
		editorLayoutDiv.setWidth("250px");

		FormLayout layout1 = new FormLayout();
		layout1.setWidth("200px");

		FormLayout searchFormLayout = new FormLayout();

		searchFormLayout.addFormItem(apiToken, "API Token");
		searchFormLayout.getStyle().set("margin-left", "15px");
		apiToken.setRevealButtonVisible(false);

		Image searchImage = new Image("images" + File.separator + "search24.png", "Search");
		searchButton.getStyle().set("margin-left", "15px");
		searchButton.setIcon(searchImage);

		VerticalLayout dtsVerticalLayout = new VerticalLayout();
		dtsVerticalLayout.add(searchFormLayout,searchButton);
		dtsVerticalLayout.setClassName("p-l flex-grow");
		editorLayoutDiv.add(dtsVerticalLayout);

		mLayout.add(editorLayoutDiv);
		try {
			searchButtonEvent();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		
	}
	private void setui() {
		
		
		HorizontalLayout gridLayout = new HorizontalLayout();
		datagrid.addColumn(OsduDataInfo::getBasinname).setHeader("Basin Name").setAutoWidth(true);
		datagrid.addColumn(OsduDataInfo::getWellname).setHeader("Well Name").setAutoWidth(true);
		datagrid.addColumn(OsduDataInfo::getWellid).setHeader("Well ID").setAutoWidth(true);

		datagrid.setSelectionMode(SelectionMode.MULTI);
		datagrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		datagrid.setHeightByRows(true);
		datagrid.setHeight("600px");
		datagrid.setWidth("900px");
		gridLayout.add(datagrid);
	
		VerticalLayout mainActionLayout = new VerticalLayout();
		importButton = new Button("Import");
		importButton.getElement().setAttribute("title", "Import to Deltalake");
		importButton.setId("bgz_well_saveButton");
		importButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
	
		mainActionLayout.add(importButton);
		mainActionLayout.getStyle().set("margin-left", "-15px");
		mainLayot.add(gridLayout,mainActionLayout);
		importButtonAction();
	}
	private void searchButtonEvent() throws SQLException {
		// TODO Auto-generated method stub
		searchButton.addClickListener(event -> {
			

			//executor.submit(() -> {

			

				
			
			String tokenID = apiToken.getValue();
			ArrayList<String> deviationObject = null;
			try {
				deviationObject = new OSDUQueries().queryWellInformation(tokenID);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				ArrayList<OsduDataInfo> dataList = new ArrayList<>();
				for (String data : deviationObject) {
					OsduDataInfo info=new OsduDataInfo();
					info.setWellid(data);
					info.setWellname(data+"_Well");
					info.setBasinname("OSDU_Basin");		
					dataList.add(info);
					
				}
				
			ListDataProvider<OsduDataInfo>	welllist = (ListDataProvider<OsduDataInfo>) DataProvider.ofCollection((Collection)	 dataList);
				welllist.setSortOrder(OsduDataInfo::getWellid, aSSENDINGDirection);
			
		
				datagrid.setDataProvider(welllist);
                
//			try {
//				ArrayList<OSDUWellLogsInfo> wellLogs = new OSDUQueries().queryWellLogs(selectedWellboreId, tokenID);
//				wellLogsGrid.setItems(wellLogs);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

		});
	}
	private void importButtonAction() {
		importButton.addClickListener(event -> {
			ExecutorService executor = Executors.newCachedThreadPool();
			PB_Progress_Notification notificatiion = new PB_Progress_Notification();
			UI ui = getUI().get();
			executor.submit(() -> {

				ui.access(() -> {

					notificatiion.setImage("info");
					notificatiion.setText("Fetching data from OSDU");
					notificatiion.open();

				});
			String wellnames = "";
			for (OsduDataInfo row : datagrid.getSelectedItems()) {
				String wellName = row.getWellid();

				wellnames += wellName ;

			}
//			if (wellnames.endsWith(",")) {
//				wellnames = wellnames.substring(0, wellnames.length() - 1);
//			}

			String[] myArray = wellnames.split(",");
			List<String> myList = Arrays.asList(myArray);
			String databricks_status = PH_KeyVault.getSecretKey("Databricks-Status");
			String jobID2;
			if (databricks_status.equalsIgnoreCase("azure")) {
				jobID2="13565";
			
			} else {
				
				jobID2 = "1148";
			}
			
			String apitoken=apiToken.getValue();
			try {
				invokeDatabricksJob.osduDataJob(jobID2,apitoken,wellnames);
				ui.access(() -> {
				try {
					TimeUnit.SECONDS.sleep(20);
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
	           
				
				});
				ui.access(() -> {

					notificatiion.setImage("info");
					notificatiion.setText("Fetching Deviation data for selected Wells");
					notificatiion.open();

				});
				
				ui.access(() -> {
					try {
						TimeUnit.SECONDS.sleep(15);
					
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				});
				ui.access(() -> {

					notificatiion.setImage("info");
					notificatiion.setText("Fetching Well logs for selected Wells");
					notificatiion.open();

				});
				
				ui.access(() -> {
					try {
						TimeUnit.SECONDS.sleep(40);
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				});
				ui.access(() -> {

					notificatiion.setImage("info");
					notificatiion.setText("Well Data stored successfully");
					notificatiion.open();

				});
				
				ui.access(() -> {
					try {
						TimeUnit.SECONDS.sleep(5);
						notificatiion.close();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				});
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			});
		

//			try {
//				ArrayList<OSDUWellLogsInfo> wellLogs = new OSDUQueries().queryWellLogs(selectedWellboreId, tokenID);
//				wellLogsGrid.setItems(wellLogs);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

		});
		
	}
	
}

package com.petrabytes.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import com.petrabytes.connections.DatabricksConnection;
import com.petrabytes.databricks.DatabricksClusterPopupInfo;
import com.petrabytes.databricks.Databricks_Cluster_List;
import com.petrabytes.databricks.Serverless_SQL_Endpoints_List;
import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.keyvault.AWS_Key_Vault;
import com.petrabytes.keyvault.PH_KeyVault;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridSelectionModel;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.StartedEvent;

public class Config_view_UI extends PetrabyteUI_Dialog {
	
	private VerticalLayout mainLayout = new VerticalLayout();
	 
	private Label userNameLabel = new Label();
	private Label urlLabel = new Label();
	private Label TokenLabel = new Label();
	private Label databricksLabel = new Label();
	private TextField account = new TextField();
	private TextField url = new TextField();
	private PasswordField Token = new PasswordField();
	private TextField databricksid = new TextField();
	private Label cloudproviderlabel = new Label();
	private ComboBox<String> cloudprovider = new ComboBox();
	
	private Label accessleyLabel = new Label();
	private Label scrcetkeyLabel = new Label();
	private Label bucketnameLabel = new Label();
	private PasswordField accesskey = new PasswordField();
	private PasswordField scerectkey = new PasswordField();
	private TextField awsid = new TextField();
	private Grid<DatabricksClusterPopupInfo> popupGrid = new Grid<>();
	private Label accountnameLabel = new Label();
	private Label accountkeyLabel = new Label();
	private Label containernameLabel = new Label();
	private TextField accountname = new TextField();
	private PasswordField accountkey = new PasswordField();
	private TextField containername = new TextField();
	private Button databricksConfigSaveButton = new Button("Save");
	private Button databricksConfigClearButton = new Button("Clear");
	private Button awsConfigSaveButton = new Button("Save");
	private Button awsConfigClearButton = new Button("Clear");
	private Button azureConfigSaveButton = new Button("Save");
	private Button azureConfigClearButton = new Button("Clear");
	private HorizontalLayout databricksConfigButtonLayout = new HorizontalLayout();
	private HorizontalLayout awsConfigButtonLayout = new HorizontalLayout();
	private HorizontalLayout azureConfigButtonLayout = new HorizontalLayout();
	private VerticalLayout databricksLayoutForm = new VerticalLayout();
	private VerticalLayout awsLayoutForm = new VerticalLayout();
	private VerticalLayout azureLayoutForm = new VerticalLayout();
	private String url_value;
	private String url_for_list;
	private String username_value;
	private String token_value;
	private String selected_cluster_status;
	private Boolean isConnectionSuccessful;
	private Boolean awsFlag = false;
	private String selected_cluster_id = null;
	private final UI ui = UI.getCurrent();
	
	public Config_view_UI() {
		
		setUI();
		
		String url_value;
		String username_value;
		String token_value;

		String databricks_status = PH_KeyVault.getSecretKey("Databricks-Status");
		if (databricks_status.equalsIgnoreCase("azure")) {
			url_value = PH_KeyVault.getSecretKey("Databricks-URL");
			username_value = PH_KeyVault.getSecretKey("Databricks-Username");
			token_value = PH_KeyVault.getSecretKey("Databricks-Token");

		} else {
			url_value = AWS_Key_Vault.getSecret("Databricks-URL");
			username_value = AWS_Key_Vault.getSecret("Databricks-Username");
			token_value = AWS_Key_Vault.getSecret("Databricks-Token");
			
		}
		url.setValue(url_value);
		account.setValue(username_value);
		Token.setValue(token_value);
		init_Grid();

		popupGrid.setSelectionMode(SelectionMode.SINGLE);
		
		databricksConfigSaveButton.addClickListener(event-> {

			populateClusterGrid();

		});
		
		databricksConfigClearButton.addClickListener(event -> {
			url.clear();
			account.clear();
			Token.clear();
		});
		
		saveButtonEvent();
			
		GridSelectionEvent();

	
	}
	
	private void saveButtonEvent() {
		// TODO Auto-generated method stub
		this.saveButton.addClickListener(event -> {

			ExecutorService executor = Executors.newCachedThreadPool();
			PB_Progress_Notification notification = new PB_Progress_Notification();
			selected_cluster_id = PH_KeyVault.getSecretKey("Databricks-Cluster");
			executor.submit(() -> {
			try {
				if (selected_cluster_status.equalsIgnoreCase("TERMINATED")) {
					ui.access(() -> {
						notification.setText("Starting cluster..");
						notification.open();
					});
					startCluster();
					ui.access(() -> {
						notification.close();
					});
				
				}
				
//				ui.access(() -> {
//					notification.setText("Testing connection..");
//					notification.open();
//				});
				isConnectionSuccessful = testConnection();
//				ui.access(() -> {
//					notification.close();
//				});
//				
				if (isConnectionSuccessful) {
					ui.access(() -> {
						notification.setText("Connection is successful");
						notification.open();
						notification.setDuration(5000);
						this.close();
					});	
					
				} else {
					ui.access(() -> {
					notification.setText("Wrong configuration details");
					notification.open();
					notification.setDuration(5000);
					
					});	
				}
					
			} catch(Exception e) {
				ui.access(() -> {
					notification.setText("Wrong configuration details");
					notification.open();
					notification.setDuration(5000);
					});	
			}
		});
			
		});
	}

	private void setUI() {
		this.setTitle(" Cluster and Data Configuration ");
		this.setImage("icons" + File.separator + "purple-24x" + File.separator + "cluster-24x.png");
		this.setButtonName("OK");
		this.setCloseOnOutsideClick(false);
		HorizontalLayout Layout = new HorizontalLayout();
		HorizontalLayout captionLayout = new HorizontalLayout();
	    Label databricksconfigLabel = new Label("Databricks Configuration");
	    Label awsconfigLabel = new Label("AWS S3 Configuration");
	    Label azuresconfigLabel = new Label("Azure Blob Configuration");
	    databricksconfigLabel.getStyle().set("margin-left", "40px");
	    awsconfigLabel.getStyle().set("margin-left", "275px");
	    azuresconfigLabel.getStyle().set("margin-left", "290px");
	    captionLayout.getStyle().set("font-weight", "bold");
	    captionLayout.add(databricksconfigLabel,awsconfigLabel,azuresconfigLabel);
	    VerticalLayout headerLayout = new VerticalLayout();
		HorizontalLayout cloudLayout = new HorizontalLayout();
		
		urlLabel.setText("URL");
//		url.setPlaceholder("https//dbc-59ae521b-6354.cloud.databricks.com/");

		cloudLayout.add(urlLabel,url);
		
		HorizontalLayout userNameLayout = new HorizontalLayout();
		userNameLabel.setText("Username");

//		account.setPlaceholder("xxxxxxxxxxxx");
		userNameLayout.add(userNameLabel,account);
		
		HorizontalLayout TokenLayout = new HorizontalLayout();
		TokenLabel.setText("Token");

//		Token.setPlaceholder("dapi1234567890ab1cdef6");
		Token.setRevealButtonVisible(false);
		TokenLayout.add(TokenLabel,Token);
		databricksLabel.setText("Databricks ID");
		databricksConfigSaveButton.addThemeVariants(ButtonVariant.LUMO_SMALL ,ButtonVariant.LUMO_PRIMARY);
		databricksConfigClearButton.addThemeVariants(ButtonVariant.LUMO_SMALL,ButtonVariant.LUMO_TERTIARY);
		databricksConfigButtonLayout.add(databricksConfigClearButton,databricksConfigSaveButton);
		
		databricksConfigButtonLayout.setJustifyContentMode(JustifyContentMode.END);
		databricksConfigButtonLayout.setSpacing(false);
		
		databricksConfigButtonLayout.setWidthFull();
		headerLayout.setHorizontalComponentAlignment(Alignment.END, databricksConfigButtonLayout);
		headerLayout.setPadding(false);
		databricksLayoutForm.add(cloudLayout,userNameLayout,TokenLayout);
		headerLayout.add(databricksLayoutForm,databricksConfigButtonLayout);
		headerLayout.setHorizontalComponentAlignment(Alignment.END, databricksConfigButtonLayout);
		
		account.getStyle().set("width", "310px");
		url.getStyle().set("margin-left", "15%");
		url.getStyle().set("width", "310px");
		Token.getStyle().set("margin-left", "12%");
		Token.getStyle().set("width", "360px");

		VerticalLayout awsLayout = new VerticalLayout();
		HorizontalLayout tokenLayout = new HorizontalLayout();
	
		accessleyLabel.setText("Access Key");
		PasswordField accesskey = new PasswordField();
//		accesskey.setPlaceholder("AKIARZVLKHBS6T5YEOBS");
		accesskey.setRevealButtonVisible(false);
		tokenLayout.add(accessleyLabel,accesskey);
		
		HorizontalLayout scrcetLayout = new HorizontalLayout();
		scrcetkeyLabel.setText("Secret Key");
		PasswordField scerectkey = new PasswordField();
//		scerectkey.setPlaceholder("5zxfG+ZJ9h1iQFI9mxg4n7dLefy4jaIg/Jdv7eXQ");
		scerectkey.setRevealButtonVisible(false);
		scrcetLayout.add(scrcetkeyLabel,scerectkey);
		
		HorizontalLayout bucketLayout = new HorizontalLayout();
		bucketnameLabel.setText("AWS Bucket Name");
		TextField awsid = new TextField();
//		awsid.setPlaceholder("xxxxx");
		bucketLayout.add(bucketnameLabel,awsid);
		awsConfigSaveButton.addThemeVariants(ButtonVariant.LUMO_SMALL ,ButtonVariant.LUMO_PRIMARY);
		awsConfigClearButton.addThemeVariants(ButtonVariant.LUMO_SMALL,ButtonVariant.LUMO_TERTIARY);
		awsConfigButtonLayout.add(awsConfigClearButton,awsConfigSaveButton);
		
		awsConfigButtonLayout.setJustifyContentMode(JustifyContentMode.END);
		awsConfigButtonLayout.setSpacing(false);
		awsLayoutForm.add(tokenLayout,scrcetLayout,bucketLayout);
		awsLayout.add(awsLayoutForm,awsConfigButtonLayout);
		
		awsConfigButtonLayout.setWidthFull();
		awsLayout.setHorizontalComponentAlignment(Alignment.END, awsConfigButtonLayout);
		awsLayout.setPadding(false);
		
		
		awsid.getStyle().set("width", "270px");
		bucketnameLabel.getStyle().set("width", "40%");
		accessleyLabel.getStyle().set("width", "25%");
		scrcetkeyLabel.getStyle().set("width", "25%");
		scerectkey.getStyle().set("width", "270px");
		scerectkey.getStyle().set("margin-left", "72px");
		accesskey.getStyle().set("margin-left", "63px");
		accesskey.getStyle().set("width", "250px");
		
		VerticalLayout awsconfigLayout = new VerticalLayout();
	
		
		VerticalLayout azureblobLayout = new VerticalLayout();
		HorizontalLayout databrickLayout = new HorizontalLayout();
		accountnameLabel.setText("Storage Account Name");
		TextField accountname = new TextField();
//		accountname.setPlaceholder("blgzsa1");
		databrickLayout.add(accountnameLabel,accountname);
		
		HorizontalLayout accountkeyLayout = new HorizontalLayout();
		accountkeyLabel.setText("Storage Account Key");
		PasswordField accountkey = new PasswordField();
//		accountkey.setPlaceholder("xxxxxx");
		accountkey.setRevealButtonVisible(false);
		accountkeyLayout.add(accountkeyLabel,accountkey);
		
		HorizontalLayout containernameLayout = new HorizontalLayout();
		containernameLabel.setText("Container Name");
		TextField containername = new TextField();
//		containername.setPlaceholder("xxxxxx");
		containernameLayout.add(containernameLabel,containername);
		
		accountname.getStyle().set("width", "280px");
		bucketnameLabel.getStyle().set("width", "40%");
		
		accountkey.getStyle().set("width", "280px");
		accountkey.getStyle().set("margin-left", "30px");
		containername.getStyle().set("margin-left", "63px");
		containername.getStyle().set("width", "280px");
		
		azureConfigSaveButton.addThemeVariants(ButtonVariant.LUMO_SMALL ,ButtonVariant.LUMO_PRIMARY);
		azureConfigClearButton.addThemeVariants(ButtonVariant.LUMO_SMALL,ButtonVariant.LUMO_TERTIARY);
		azureConfigButtonLayout.add(azureConfigClearButton,azureConfigSaveButton);
		
		azureConfigButtonLayout.setJustifyContentMode(JustifyContentMode.END);
		azureConfigButtonLayout.setSpacing(false);
		
		azureConfigButtonLayout.setWidthFull();
		azureblobLayout.setHorizontalComponentAlignment(Alignment.END, azureConfigButtonLayout);
		azureblobLayout.setPadding(false);
		azureLayoutForm.add(databrickLayout,accountkeyLayout,containernameLayout);
		azureblobLayout.add(azureLayoutForm,azureConfigButtonLayout);
		azureblobLayout.setHorizontalComponentAlignment(Alignment.END, azureConfigButtonLayout);
		
		VerticalLayout azureblobstorageLayout = new VerticalLayout();

		cloudLayout.setWidth("400px");
		tokenLayout.setWidth("400px");
		databrickLayout.setWidth("470px");
		Layout.add(headerLayout,awsLayout,azureblobLayout);
		popupGrid.addColumn(DatabricksClusterPopupInfo::getName).setHeader("Cluster Name").setAutoWidth(true);
		popupGrid.addColumn(DatabricksClusterPopupInfo::getCluster).setHeader("Cluster Id").setAutoWidth(true);
		popupGrid.addColumn(DatabricksClusterPopupInfo::getCreatedBy).setHeader("Created by").setAutoWidth(true);
		popupGrid.addColumn(DatabricksClusterPopupInfo::getNodeType).setHeader("Node Type").setAutoWidth(true);
		
		popupGrid.addColumn(DatabricksClusterPopupInfo::getWorker_min).setHeader("Worker(min)").setAutoWidth(true);
		popupGrid.addColumn(DatabricksClusterPopupInfo::getWorker_max).setHeader("Worker(max)").setAutoWidth(true);
		popupGrid.addColumn(DatabricksClusterPopupInfo::getStatus).setHeader("Status").setAutoWidth(true);

		popupGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		popupGrid.setHeightByRows(true);
		popupGrid.setWidth("1420px");
		popupGrid.getStyle().set("margin-left", "2%");
		Layout.setWidth("1450px");
		mainLayout.setWidth("1500px");
		mainLayout.setHeight("500px");
		mainLayout.add(captionLayout,Layout,popupGrid);
		
		headerLayout.getElement().getStyle().set("border", "solid");
		headerLayout.getElement().getStyle().set("border-color", "#D1D1D1");
		headerLayout.getStyle().set("margin-left", "3%");
	
		awsLayout.getElement().getStyle().set("border", "solid");
		awsLayout.getElement().getStyle().set("border-color", "#D1D1D1");

		azureblobLayout.getElement().getStyle().set("border-color", "#D1D1D1");
		azureblobLayout.getStyle().set("border-style", "solid");
		this.content.add(mainLayout);
	}
	private void GridSelectionEvent() {
		// TODO Auto-generated method stub
		popupGrid.addSelectionListener(event -> {
			DatabricksClusterPopupInfo selected_cluster = popupGrid.asSingleSelect().getValue();
			
			Select_Cluster_DialogUI selectClusterPopUp = new Select_Cluster_DialogUI(selected_cluster, awsFlag);
			selectClusterPopUp.open();
			selectClusterPopUp.SelectClusterEvent();
			selectClusterPopUp.addOpenedChangeListener(event1 ->
			{
				if (!event1.isOpened()) {
					selected_cluster_status = selectClusterPopUp.getCluster_status();
				}
			});
		
		});
	}

	private boolean testConnection() {
		// TODO Auto-generated method stub
		String url_value_test;
		String username_value_test;
		String token_value_test;
		String cluster_id_test;

		if(!awsFlag) {

			url_value_test = PH_KeyVault.getSecretKey("Databricks-URL");
			username_value_test = PH_KeyVault.getSecretKey("Databricks-Username");
			token_value_test = PH_KeyVault.getSecretKey("Databricks-Token");
			cluster_id_test = PH_KeyVault.getSecretKey("Databricks-Cluster");
		}
		else {
			url_value_test = AWS_Key_Vault.getSecret("Databricks-URL");
			username_value_test = AWS_Key_Vault.getSecret("Databricks-Username");
			token_value_test = AWS_Key_Vault.getSecret("Databricks-Token");
			cluster_id_test = AWS_Key_Vault.getSecret("Databricks-Cluster");
		}
			String driver = "com.simba.spark.jdbc.Driver";
			String url_test = "";
			url_value_test = url_value_test.replace("https://", "");
			
			int indexOfCloud = url_value_test.indexOf("cloud");
			
	        if (indexOfCloud == -1) {
	        	
	        	int beginIndex = url_value_test.indexOf("-") + 1;
	    		int endIndex = url_value_test.indexOf(".");
	    		String number = url_value_test.substring(beginIndex, endIndex);

	    		url_test = "jdbc:spark://" + url_value_test + 
	    				":443/default;transportMode=http;ssl=1;httpPath=sql/protocolv1/o/" + number 
	    				+ "/" + cluster_id_test;
	        } else {
	        	// parsing aws url
	        	int beginIndex = url_value_test.indexOf("=") + 1;
	    		int endIndex = url_value_test.indexOf("#");
	    		String number = url_value_test.substring(beginIndex, endIndex);
	        	int indexOfCom = url_value_test.indexOf("com") + 3;
	        	url_value_test = url_value_test.substring(0, indexOfCom);
	        	url_test = "jdbc:spark://" + url_value_test + 
	        			":443/default;transportMode=http;ssl=1;httpPath=sql/protocolv1/o/" + number 
	        			+ "/" + cluster_id_test;
	        	
	        }
//			String url_test = "jdbc:spark://" + url_value_test + ":443/default;transportMode=http;ssl=1;httpPath=sql/protocolv1/o/" + number + "/" + cluster_id_test;

			String username_test = username_value_test;

			String password_test = token_value_test;
			Connection con = null;
			
			PB_Progress_Notification notification = new PB_Progress_Notification();
			try {
				Class.forName(driver);
				System.out.println("Trying to Connect to Databricks Cluster . . . ");
				ui.access(() -> {
					notification.setText("Trying to Connect to Databricks Cluster . . . ");
					notification.open();
				});
				con = DriverManager.getConnection(url_test, username_test, password_test);
				System.out.println("Connection Successful");
				ui.access(() -> {

					notification.close();
				});
				Statement stmt = con.createStatement();
				return true;

			} catch (Exception e) {
				e.printStackTrace();
				ui.access(() -> {

					notification.close();
				});
				return false;
			}

		}
	
	private void init_Grid() {
		// TODO Auto-generated method stub
		PB_Progress_Notification notification = new PB_Progress_Notification();
		String url_value;
		String username_value;
		String token_value;
		
		String databricks_status = PH_KeyVault.getSecretKey("Databricks-Status");
		if (databricks_status.equalsIgnoreCase("azure")) {
			url_value = PH_KeyVault.getSecretKey("Databricks-URL");
			username_value = PH_KeyVault.getSecretKey("Databricks-Username");
			token_value = PH_KeyVault.getSecretKey("Databricks-Token");
			selected_cluster_id = PH_KeyVault.getSecretKey("Databricks-Cluster");
		} else {
			url_value = AWS_Key_Vault.getSecret("Databricks-URL");
			username_value = AWS_Key_Vault.getSecret("Databricks-Username");
			token_value = AWS_Key_Vault.getSecret("Databricks-Token");
			selected_cluster_id =  AWS_Key_Vault.getSecret("Databricks-Cluster");
		
		}
				
		int indexOfCloud = url_value.indexOf("cloud");
        if (indexOfCloud == -1) {
        	// aws
        	awsFlag = false;
        	url_for_list = url_value + "/api/2.0/clusters/list";
        } else {
        	awsFlag = true;
        	int indexOfCom = url_value.indexOf("com") + 3;
        	String url_value_aws = url_value.substring(0, indexOfCom);
        	url_for_list = url_value_aws + "/api/2.0/clusters/list";
        }
        url_for_list = "https://dbc-59ae521b-6354.cloud.databricks.com/api/2.0/sql/endpoints/";
		token_value = "dapib6a8aa1c67e61027e8dad070405caa2f";
		username_value = "sashigunturu@petrabytes.com";
		ArrayList<DatabricksClusterPopupInfo> popupDatabricksList;
		try {
//			popupDatabricksList = new  Databricks_Cluster_List().getDatabricks_Cluster_List(url_for_list,username_value,
//					token_value);
			popupDatabricksList = new Serverless_SQL_Endpoints_List().getDatabricks_Cluster_List(url_for_list,username_value,
					token_value);
			
			popupGrid.setItems(popupDatabricksList);
			
			for (DatabricksClusterPopupInfo data : popupDatabricksList) {
				if (data.getCluster().equalsIgnoreCase(selected_cluster_id)) {
//						popupGrid.select(data);
					GridSelectionModel<DatabricksClusterPopupInfo> selectedModel = popupGrid.getSelectionModel();
					selectedModel.select(data);
					selected_cluster_status = data.getStatus();

				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ui.access(() -> {
				notification.setText("Wrong configuration details");
				notification.open();
				notification.setDuration(5000);
			});
		}
	}
	
	private void populateClusterGrid() {
		// TODO Auto-generated method stub

		PB_Progress_Notification notification = new PB_Progress_Notification();
		url_value = url.getValue();
		username_value = account.getValue();
		token_value = Token.getValue();
		
		url_for_list = "https://dbc-59ae521b-6354.cloud.databricks.com/api/2.0/sql/endpoints/";
		token_value = "dapib6a8aa1c67e61027e8dad070405caa2f";
		username_value = "sashigunturu@petrabytes.com";
		// Checking if the url entered is for aws or azure
		ArrayList<DatabricksClusterPopupInfo> popupDatabricksList = null;
		int indexOfCloud = url_value.indexOf("cloud");
        if (indexOfCloud == -1) {
        	// azure
        	awsFlag = false;
//        	url_for_list = url_value + "/api/2.0/clusters/list";

        	try {
        		popupDatabricksList = new  Serverless_SQL_Endpoints_List().getDatabricks_Cluster_List(url_for_list,username_value,
						token_value);
//				popupDatabricksList = new  Databricks_Cluster_List().getDatabricks_Cluster_List(url_for_list,username_value,
//						token_value);

//				PH_KeyVault.update_OR_ADD_Secret("Databricks-URL",url_value);
//				PH_KeyVault.update_OR_ADD_Secret("Databricks-Username", username_value);
//				PH_KeyVault.update_OR_ADD_Secret("Databricks-Token", token_value);
//				PH_KeyVault.update_OR_ADD_Secret("Databricks-Status", "azure");
//				AWS_Key_Vault.updateSecret("Databricks-Status", "azure");
			
				popupGrid.setItems(popupDatabricksList);
				
				selected_cluster_id =  PH_KeyVault.getSecretKey("Databricks-Cluster");
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ui.access(() -> {
					notification.setText("Wrong configuration details");
					notification.open();
					notification.setDuration(5000);
				});
			}
        	
        } else {

        	awsFlag = true;
        	int indexOfCom = url_value.indexOf("com") + 3;
        	String url_value_aws = url_value.substring(0, indexOfCom);
        	url_for_list = url_value_aws + "/api/2.0/clusters/list";
        	
        	
        	try {
				popupDatabricksList = new  Databricks_Cluster_List().getDatabricks_Cluster_List(url_for_list,username_value,
						token_value);

				AWS_Key_Vault.updateSecret("Databricks-URL",url_value);
	        	AWS_Key_Vault.updateSecret("Databricks-Username", username_value);
	        	AWS_Key_Vault.updateSecret("Databricks-Token", token_value);
	        	PH_KeyVault.update_OR_ADD_Secret("Databricks-Status", "aws");
				AWS_Key_Vault.updateSecret("Databricks-Status", "aws");
	        	popupGrid.setItems(popupDatabricksList);
			
	        	selected_cluster_id =  AWS_Key_Vault.getSecret("Databricks-Cluster");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				ui.access(() -> {
					notification.setText("Wrong configuration details");
					notification.open();
					notification.setDuration(5000);
				});
			}
        }
	
		for (DatabricksClusterPopupInfo data : popupDatabricksList) {
			if (data.getCluster().equalsIgnoreCase(selected_cluster_id)) {
//					popupGrid.select(data);
				GridSelectionModel<DatabricksClusterPopupInfo> selectedModel = popupGrid.getSelectionModel();
				selectedModel.select(data);

			}
		} 
	}
	
	private void startCluster() {

		String url_value;
		String username_value;
		String token_value;
		String clusterID;
		if(!awsFlag) {
			url_value = PH_KeyVault.getSecretKey("Databricks-URL");
			username_value = PH_KeyVault.getSecretKey("Databricks-Username");
			token_value = PH_KeyVault.getSecretKey("Databricks-Token");
			clusterID = PH_KeyVault.getSecretKey("Databricks-Cluster");
		}
		else {
			url_value = AWS_Key_Vault.getSecret("Databricks-URL");
			username_value = AWS_Key_Vault.getSecret("Databricks-Username");
			token_value = AWS_Key_Vault.getSecret("Databricks-Token");
			clusterID = AWS_Key_Vault.getSecret("Databricks-Cluster");
		}
		
		
		int indexOfCloud = url_value.indexOf("cloud");
        if (indexOfCloud == -1) {
        	awsFlag = false;
        	url_value = url_value + "/api/2.0/clusters/start";
        } else {
        	awsFlag = true;
        	int indexOfCom = url_value.indexOf("com") + 3;
        	url_value = url_value.substring(0, indexOfCom);
        	url_value = url_value + "/api/2.0/clusters/start";
        }
        
		
		/// Getting JSON String of person Object
		String cluster_jsonString = "{ \"cluster_id\" : \"" + clusterID + "\"}";
		String requestType = "POST";
		try {

			URL obj = new URL(url_value);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod(requestType);
			con.setRequestProperty("Content-Type", "application/json; utf-8");

			con.setRequestProperty("Authorization", "Bearer " + token_value);
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(cluster_jsonString.getBytes());
			os.flush();
			os.close();
			int responseCode = con.getResponseCode();

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			System.out.println("Response: Cluster Started !");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Response: Cluster Already Started !");
		}

	}
}


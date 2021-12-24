package com.petrabytes.views;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Optional;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;

import org.json.JSONObject;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.contextmenu.SubMenu;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.router.PageTitle;
import com.petrabytes.config.Cloud_Util;
import com.petrabytes.config.Config_view_UI;
import com.petrabytes.config.Storage_Compute_Settings_UI;
import com.petrabytes.databricks.DatabricksClusterPopup;
import com.petrabytes.keyvault.AWS_Key_Vault;
import com.petrabytes.keyvault.PH_KeyVault;
import com.petrabytes.login.About_UI_Dialog;
import com.petrabytes.login.GS_Javascripts_Library;
import com.petrabytes.login.Signout_UI_Dialog;
import com.petrabytes.toptoolUI.Blgz_Close_Project_UI;
import com.petrabytes.toptoolUI.Blgz_CreateProject_UI;
import com.petrabytes.toptoolUI.Blgz_Delete_Project_UI;
import com.petrabytes.toptoolUI.Blgz_OpenProject_UI;
import com.petrabytes.toptoolUI.Blgz_SaveProject_UI;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.MainLayout;
import com.petrabytes.views.completions.CompletionsView;
import com.petrabytes.views.coredata.CoreDataView;
import com.petrabytes.views.das.DasView;
import com.petrabytes.views.dts.DTSView;
import com.petrabytes.views.geobodies.GeobodiesView;
import com.petrabytes.views.gis.GISView;
import com.petrabytes.views.governance.Governance;
import com.petrabytes.views.seismic.SeismicView;
import com.petrabytes.views.welllogs.WellLogsView;
import com.petrabytes.views.wells.WellsMapView;

import com.sristy.unitsystem.UOMRegistry;
import com.sristy.unitsystem.util.PoscUnitsXMLParser;

/**
 * The main view is a top-level placeholder for other views.
 */
@Push()
//@PWA(name = "PetraHub", shortName = "PetraHub", iconPath = "images/pb_fav_icon2.png", enableInstallPrompt = false)
@PWA(name = "PetraHub", shortName = "PetraHub", iconPath = "images/pb_fav_icon2.png")
@Theme(themeFolder = "sdsapp")
@JsModule("./src/update_css.js")
public class MainLayout extends AppLayout {

	private final Tabs menu;
	private H1 viewTitle;
	private Label clusterStatus;
	private Label projectNameH1;
	private HorizontalLayout footerLayout = new HorizontalLayout();
	private FlexLayout centerContent = new FlexLayout();
	private String status = null;
	private Image clusterStatusImage = new Image("icons" + File.separator + "16x" + File.separator + "success-16x.png",
			"Cluster Status");

	private Button startClusterButton = new Button();
	private Button settingsButton = new Button();
	private Button projectAddButton = null;
	private Button projectOpenButton = null;
	private Button projectSaveButton = null;
	private Button projectCloseeButton = null;
	private Button projectDeleteButton = null;
	private Button dashboardButton = null;
	private Button bugReportButton = null;
	private Properties globalProps = new Properties();

	private Sds_Util sdsUtil;

	public MainLayout() {
		new GS_Javascripts_Library().addJS_Library();
		setPrimarySection(Section.DRAWER);
		addToNavbar(true, createHeaderContent());
		menu = createMenu();
		addToDrawer(createDrawerContent(menu));
		sdsUtil = new Sds_Util();
	}

	/**
	 * setting up header ui
	 * 
	 * @return
	 */
	private Component createHeaderContent() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.setClassName("sidemenu-header");
		layout.getThemeList().set("dark", true);
		layout.setWidthFull();
		layout.setSpacing(false);
		layout.setAlignItems(FlexComponent.Alignment.CENTER);

		DrawerToggle toggle = new DrawerToggle();
		toggle.setIcon(new Image("icons" + File.separator + "top-bar" + File.separator + "toggle-16x.png", "toggle"));

		layout.add(toggle);
		viewTitle = new H1();
		viewTitle.getStyle().set("font-weight", "normal");
		layout.add(viewTitle);

		HorizontalLayout buttonlayout = new HorizontalLayout();
		buttonlayout.setSpacing(false);
		HorizontalLayout statuslayout = new HorizontalLayout();
		statuslayout.setSpacing(false);

		clusterStatus = new Label();
		clusterStatus.getStyle().set("margin-top", "07px");
		clusterStatusImage.getStyle().set("object-fit", "contain");
		clusterStatusImage.getStyle().set("padding-right", "3px");
		clusterStatusImage.getStyle().set("margin-top", "-5px");

		projectNameH1 = new Label();
		projectNameH1.getStyle().set("margin-top", "07px");

		/*
		 * add button : - button to add/ create new project for paticuler view
		 */
		projectAddButton = new Button();
		Image addButtonimage = new Image("icons" + File.separator + "top-bar" + File.separator + "add-16x.png", "Add");
		// buttonlayout.getStyle().set("padding-left", "53%");
		projectAddButton.getElement().setAttribute("title", "Add Project");
		projectAddButton.setIcon(addButtonimage);
		projectAddButton.setClassName("selector");
		projectAddButton.setId("addProject");

		/*
		 * open button :- open project for selected view
		 */
		projectOpenButton = new Button();
		projectOpenButton.setClassName("selector");
		Image openButtonimage = new Image("icons" + File.separator + "top-bar" + File.separator + "open-16x.png",
				"Open");
		projectOpenButton.getElement().setAttribute("title", "Open Project");
		projectOpenButton.setIcon(openButtonimage);
		projectOpenButton.setId("openProject");
		/*
		 * save button : - save the paticular view project in storage
		 */
		projectSaveButton = new Button();
		projectSaveButton.setClassName("selector");
		Image saveButtonimage = new Image("icons" + File.separator + "top-bar" + File.separator + "save-16x.png",
				"Save");
		projectSaveButton.getElement().setAttribute("title", "Save Project");
		projectSaveButton.setIcon(saveButtonimage);
		projectSaveButton.setId("saveProject");
		/**
		 * close button :- close the selected view project
		 */
		projectCloseeButton = new Button();
		projectCloseeButton.setClassName("selector");
		Image closeeButtonimage = new Image("icons" + File.separator + "top-bar" + File.separator + "close-16x.png",
				"Close");
		projectCloseeButton.getElement().setAttribute("title", "Close Project");
		projectCloseeButton.setIcon(closeeButtonimage);
		projectCloseeButton.setId("closeProject");

		projectDeleteButton = new Button();
		projectDeleteButton.setClassName("selector");
		Image deleteButtonimage = new Image("icons" + File.separator + "top-bar" + File.separator + "delete-16x.png",
				"Delete");
		projectDeleteButton.getElement().setAttribute("title", "Delete Project");
		projectDeleteButton.setIcon(deleteButtonimage);
		projectDeleteButton.setId("deleteProject");

		dashboardButton = new Button();
		dashboardButton.setClassName("selector");
		Image dashboardButtonimage = new Image(
				"icons" + File.separator + "top-bar" + File.separator + "dashboard-16x.png", "Dashboard");
		dashboardButton.getElement().setAttribute("title", "Dashboard");
		dashboardButton.setIcon(dashboardButtonimage);

		dashboardButton.addClickListener(event -> {
//			this.setTopToolbarComponent_visible(false);
			UI.getCurrent().navigate("dashboard/main");

		});

		bugReportButton = new Button();
		Anchor bugReportUrl = new Anchor("https://pb-boards.azurewebsites.net/bugreport/add");
		bugReportUrl.setTarget("_blank");
		bugReportUrl.add(bugReportButton);
		bugReportButton.setClassName("selector");
		bugReportButton.getStyle().set("margin-top", "2px");
		Image bugReportButtonimage = new Image(
				"icons" + File.separator + "top-bar" + File.separator + "bug_report16_0.png", "Bug Report");
		bugReportButton.getElement().setAttribute("title", "Bug Report");
		bugReportButton.setIcon(bugReportButtonimage);

		Button settingsButton = new Button();
		Image SettingsbButtonimage = new Image("icons" + File.separator + "top-bar" + File.separator + "cluster-16x.png", "Settings");
		settingsButton.getElement().setAttribute("title", "Databricks Cluster List");
		settingsButton.setIcon(SettingsbButtonimage);
		
		Button governanceButton = new Button();
		Image GovernancebButtonimage = new Image("icons" + File.separator + "24x" + File.separator + "data_governance24_blue.png", "Settings");
		governanceButton.getElement().setAttribute("title", "Databricks Cluster List");
		governanceButton.setIcon(GovernancebButtonimage);
		
		governanceButton.addClickListener(event -> {

			try {
				PB_Progress_Notification notification = new PB_Progress_Notification();
				notification.setImage("info");
				notification.setText("Fetching Admin View");
				notification.setDuration(2000);
				notification.open();
				
				//Governance equationWindow = new Governance();
				//centerContent.add(equationWindow);
				UI.getCurrent().navigate("governance");
				//equationWindow.open();
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		
		settingsButton.addClickListener(event -> {

			try {
				PB_Progress_Notification notification = new PB_Progress_Notification();
				notification.setImage("info");
				notification.setText("Fetching Databricks Cluster list");
				notification.setDuration(3000);
				notification.open();
				
				updateSelectedDialog();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		Button dataBaseJobsStatus = new Button();
		Image startDatabricksJobButtonimage = new Image(
				"icons" + File.separator + "top-bar" + File.separator + "cluster-16x.png", "Cluster List");
		dataBaseJobsStatus.getElement().setAttribute("title", "Databricks Cluster List");
		dataBaseJobsStatus.setIcon(startDatabricksJobButtonimage);
		// dataBaseJobsStatus.setEnabled(false);

		dataBaseJobsStatus.addClickListener(event -> {

			try {
				configDialog();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		projectAddButton.addClickListener(event -> {
			try {

				addbuttonpopup();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		projectOpenButton.addClickListener(event -> {
			try {

				openbuttonpopup();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		projectSaveButton.addClickListener(event -> {
			try {

				savebuttonpopup();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		projectCloseeButton.addClickListener(event -> {
			try {

				closebuttonpopup();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		
		projectDeleteButton.addClickListener(event -> {
			try {

				deletebuttonpopup();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		///////////////////////

		// Button userButton = new Button();
		// Image userButtonimage = new Image("images" + File.separator + "user.png",
		// "User");
//		//userButton.getElement().setAttribute("title", "User Icon");
		// userButton.setIcon(userButtonimage);

		Image startClusterButtonimage = new Image(
				"icons" + File.separator + "top-bar" + File.separator + "start-cluster-16x.png", "Start");
		startClusterButton.getElement().setAttribute("title", "Start Cluster");
		startClusterButton.getStyle().set("margin-left", "-10px");
		startClusterButton.setIcon(startClusterButtonimage);
		startClusterButton.setEnabled(false);

		buttonlayout.add(projectAddButton, projectOpenButton, projectSaveButton, projectCloseeButton,
				projectDeleteButton, dashboardButton, bugReportUrl);
		buttonlayout.getElement().getStyle().set("margin", "auto");
		projectAddButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
		projectOpenButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
		projectSaveButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
		projectCloseeButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
		projectDeleteButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
		dashboardButton.addThemeVariants(ButtonVariant.LUMO_SMALL);
		projectNameH1.setText("");
		statuslayout.add(projectNameH1, governanceButton, settingsButton, startClusterButton, clusterStatusImage, clusterStatus,
				buildUserMenu());
//        statuslayout.add(clusterStatusImage, clusterStatus,buildUserMenu());
		// clusterStatus.getStyle().set("margin", "auto");

		// layout.add(clusterStatus);

		Avatar avatar = new Avatar();
		avatar.addClassNames("ms-auto", "me-m");
		// layout.add(avatar);
		layout.add(buttonlayout, statuslayout);

		startCluster();
		setTopToolbarComponent_Enable(false);
		_initializeUnits();
		return layout;

	}

	private void configDialog() throws Exception {

		DatabricksClusterPopup equationWindow = new DatabricksClusterPopup(true);
//		Dialog equationWindow = new Dialog();
//		equationWindow.add(new Text("Close me with the esc-key or an outside click"));
//
//		equationWindow.setWidth("400px");
//		equationWindow.setHeight("150px");
		equationWindow.open();
		// equationWindow.add(userDefinedLayout);
	}

	private void updateSelectedDialog() throws Exception {
		String cloud_provider = Cloud_Util.getInstance().getCloud_provider();
		if (cloud_provider.equalsIgnoreCase("aws")) {
			
			Storage_Compute_Settings_UI equationWindow = new Storage_Compute_Settings_UI();
			equationWindow.open();
			
		} else {
			
			Config_view_UI equationWindow = new Config_view_UI();
			equationWindow.open();
			
		}
		
		

	}
	
	private void addbuttonpopup() throws Exception {

		Blgz_CreateProject_UI equationWindow = new Blgz_CreateProject_UI();

		equationWindow.open();

	}

	private void savebuttonpopup() throws Exception {

		Blgz_SaveProject_UI equationWindow = new Blgz_SaveProject_UI();

		equationWindow.open();

	}

	private void closebuttonpopup() throws Exception {

		Blgz_Close_Project_UI equationWindow = new Blgz_Close_Project_UI();

		equationWindow.open();

	}

	private void deletebuttonpopup() throws Exception {

		Blgz_Delete_Project_UI equationWindow = new Blgz_Delete_Project_UI();

		equationWindow.open();

	}

	private void openbuttonpopup() throws Exception {

		Blgz_OpenProject_UI equationWindow = new Blgz_OpenProject_UI();

		equationWindow.open();

	}
	
	private void _initializeUnits() {
		PoscUnitsXMLParser.getInstance().parse();
		UOMRegistry.init();
		UOMRegistry.initializeOtherCategories();
	}

	private Component buildUserMenu() {
		MenuBar settings = new MenuBar();
		settings.addClassName("user-menu");
		Image userButtonimage = new Image("icons" + File.separator + "top-bar" + File.separator + "user-16x.png",
				"User");
		MenuItem settingsItem = settings.addItem(userButtonimage);
		SubMenu userMenu = settingsItem.getSubMenu();
		userMenu.addItem("Edit Profile", e -> {
			PB_Progress_Notification notification = new PB_Progress_Notification();
			
			notification.setText("Edit Profile");
			notification.open();
			notification.setDuration(3000);
		
		});
		userMenu.addItem("About", e -> {
			PB_Progress_Notification notification = new PB_Progress_Notification();
			
//			notification.setText("Edit Profile");
//			notification.open();
//			notification.setDuration(3000);
			About_UI_Dialog aboutWindow = new About_UI_Dialog();
			aboutWindow.open();
		});

		userMenu.addItem("Signout", e -> {
			
			
			Signout_UI_Dialog signoutWindow = new Signout_UI_Dialog();
			signoutWindow.open();
		});
		return settings;
	}

	public void updateProjectName(String projectName) {
		projectNameH1.setText(projectName);
	}

	private Component createDrawerContent(Tabs menu) {
	
		VerticalLayout layout = new VerticalLayout();
		layout.setClassName("sidemenu-menu");
		layout.setSizeFull();

		layout.setPadding(false);
		layout.setSpacing(false);
		// layout1.getThemeList().set("spacing-s", true);
		layout.setAlignItems(FlexComponent.Alignment.STRETCH);
		HorizontalLayout logoLayout = new HorizontalLayout();
		logoLayout.setId("logo");
		// logoLayout.setAlignItems(FlexComponent.Alignment.CENTER);
		logoLayout.add(new Image("images" + File.separator + "ph_databricks_logo_485x102.png", "Petrahub logo"));
		logoLayout.addClickListener(event -> {

			UI.getCurrent().navigate("dashboard/main");
		});

		// logoLayout.add(new H1("SDS_ App"));
	
		layout.add(logoLayout, menu);
		return layout;
	}

	private Tabs createMenu() {
		final Tabs tabs = new Tabs();
		tabs.setOrientation(Tabs.Orientation.VERTICAL);
		tabs.addThemeVariants(TabsVariant.LUMO_MINIMAL);
		tabs.setId("tabs");
//		tabs.add(createMenuItems());
		
		return tabs;
	}

	public void updateMenuItems(Tab[] tabs) {
		if (tabs == null) {
			menu.removeAll();
		} else {
			menu.removeAll();
			menu.add(tabs);
		}
	}

	private Component[] createMenuItems() {
		return new Tab[] {
				createTab("Completions", CompletionsView.class, "images" + File.separator + "well_completions48.png"),
				createTab("Core Data", CoreDataView.class, "images" + File.separator + "core_data48.png"),
				createTab("DAS", DasView.class, "images" + File.separator + "DAS48.png"),
				createTab("DTS", DTSView.class, "images" + File.separator + "DTS48.png"),
				createTab("Geobodies", GeobodiesView.class, "images" + File.separator + "geobodies48.png"),
				createTab("GIS", GISView.class, "images" + File.separator + "GIS_data48.png"),
				createTab("Seismic", SeismicView.class, "images" + File.separator + "seismic48.png"),
				createTab("WellsMap", WellsMapView.class, "images" + File.separator + "wells48.png"), createTab("Well Logs",
						WellLogsView.class, "images" + File.separator + "integrated_subsurface_model48.png"), };
	}

	private static Tab createTab(String text, Class<? extends Component> navigationTarget, String imagepath) {
		final Tab tab = new Tab();
		Image image = new Image(imagepath, "add");
		image.getElement().getStyle().set("margin-right", "20px");
		image.getElement().getStyle().set("width", "35px");
		tab.add(image, new RouterLink(text, navigationTarget));
		ComponentUtil.setData(tab, Class.class, navigationTarget);
		return tab;
	}

	@Override
	protected void afterNavigation() {
		String pb = null;
		super.afterNavigation();
		getTabForComponent(getContent()).ifPresent(menu::setSelectedTab);
		viewTitle.setText(getCurrentPageTitle());

		HashMap<String, Object> userDataList = (HashMap<String, Object>) VaadinService.getCurrentRequest()
				.getWrappedSession().getAttribute("userdata");
		if (userDataList == null) {
			UI.getCurrent().getPage().setLocation("login");
		}

		try {
			getCurrentClusterStatusJava();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (status.equalsIgnoreCase("running")) {
			clusterStatusImage.setSrc("icons" + File.separator + "16x" + File.separator + "success-16x.png");
			startClusterButton.setEnabled(false);
		} else if (status.equalsIgnoreCase("terminated")) {
			clusterStatusImage.setSrc("icons" + File.separator + "16x" + File.separator + "error-16x.png");
			startClusterButton.setEnabled(true);
		} else if (status.equalsIgnoreCase("pending")) {
			clusterStatusImage.setSrc("icons" + File.separator + "16x" + File.separator + "info-16x.png");
			startClusterButton.setEnabled(false);
		}
		clusterStatus.setText("Cluster " + status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase());
	}

	public void updateClusterStatus(String status) {
		if (status.equalsIgnoreCase("running")) {
			clusterStatusImage.setSrc("icons" + File.separator + "16x" + File.separator + "success-16x.png");
			startClusterButton.setEnabled(false);
		} else if (status.equalsIgnoreCase("terminated")) {
			clusterStatusImage.setSrc("icons" + File.separator + "16x" + File.separator + "error-16x.png");
			startClusterButton.setEnabled(true);
		} else if (status.equalsIgnoreCase("pending")) {
			clusterStatusImage.setSrc("icons" + File.separator + "16x" + File.separator + "info-16x.png");
			startClusterButton.setEnabled(false);
		}
		clusterStatus.setText("Cluster " + status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase());
	}

	public void startCluster() {

		startClusterButton.addClickListener((event) -> {

			String url_value;
			String token_value;
			String clusterID;
			String databricks_status = PH_KeyVault.getSecretKey("Databricks-Status");
			if (databricks_status.equalsIgnoreCase("azure")) {
				url_value = PH_KeyVault.getSecretKey("Databricks-URL");
				token_value = PH_KeyVault.getSecretKey("Databricks-Token");
				clusterID = PH_KeyVault.getSecretKey("Databricks-Cluster");
			} else {
				url_value = AWS_Key_Vault.getSecret("Databricks-URL");
				token_value = AWS_Key_Vault.getSecret("Databricks-Token");
				clusterID = AWS_Key_Vault.getSecret("Databricks-Cluster");
			}
			
			// parsing url for starting the cluster
			int indexOfCloud = url_value.indexOf("cloud");
	        if (indexOfCloud == -1) {
	        	
	        	url_value = url_value + "/api/2.0/clusters/start";
	        } else {
	        	// aws
	        	int indexOfCom = url_value.indexOf("com") + 3;
	        	url_value = url_value.substring(0, indexOfCom);
	        	url_value = url_value + "/api/2.0/clusters/start";
	        }
			/// Getting JSON String of person Object
			String cluster_jsonString = "{ \"cluster_id\" : \"" + clusterID + "\"}";
			String requestType = "POST";
			try {
//				URL obj = new URL("https://adb-3305711601758284.4.azuredatabricks.net/api/2.0/clusters/start");
				URL obj = new URL(url_value);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod(requestType);
				con.setRequestProperty("Content-Type", "application/json; utf-8");
//				con.setRequestProperty("Authorization", "Bearer " + "dapi76e6ef0c81459fca303f219c61b4fa9c");
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
		});
	}

	public void getCurrentClusterStatusJava() throws Exception {
		String url_value;
		String token_value;
		String clusterID;
		String databricks_status = PH_KeyVault.getSecretKey("Databricks-Status");
		if (databricks_status.equalsIgnoreCase("azure")) {
			url_value = PH_KeyVault.getSecretKey("Databricks-URL");
			token_value = PH_KeyVault.getSecretKey("Databricks-Token");
			clusterID = PH_KeyVault.getSecretKey("Databricks-Cluster");
		} else {
			url_value = AWS_Key_Vault.getSecret("Databricks-URL");
			token_value = AWS_Key_Vault.getSecret("Databricks-Token");
			clusterID = AWS_Key_Vault.getSecret("Databricks-Cluster");
		}
		
//		url_value = url_value + "/api/2.0/clusters/get?cluster_id=" + cluster_id;
		int indexOfCloud = url_value.indexOf("cloud");
        if (indexOfCloud == -1) {
        
        	url_value = url_value + "/api/2.0/clusters/get?cluster_id=" + clusterID;
        } else {
        	// aws
        	int indexOfCom = url_value.indexOf("com") + 3;
        	url_value = url_value.substring(0, indexOfCom);
        	url_value = url_value + "/api/2.0/clusters/get?cluster_id=" + clusterID;
        }
		// Sending get request
//		URL url = new URL(
//				"https://adb-3305711601758284.4.azuredatabricks.net/api/2.0/clusters/get?cluster_id=0304-081914-quits100");
		URL url = new URL(url_value);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

//		conn.setRequestProperty("Authorization", "Bearer " + "dapi76e6ef0c81459fca303f219c61b4fa9c");
		conn.setRequestProperty("Authorization", "Bearer " + token_value);
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestMethod("GET");

		JSONObject jsonObject = new JSONObject(IOUtils.toString(conn.getInputStream(), Charset.forName("UTF-8")));
		// printing result from response
		System.out.println("Cluster " + jsonObject.getString("state"));
		status = jsonObject.getString("state");

	}

	private void getCurrentClusterStatusTest(String path) {

		try {
			final String commandString = path;
			System.out.println("commandString = " + commandString);
			Thread processThread = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Runtime runtime = Runtime.getRuntime();
						Process process = runtime.exec(commandString);
						BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
						BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
						String line = "";
						System.out.println("starting thread");
						while ((line = input.readLine()) != null) {
							System.out.println("from output: " + line);
							status = line;
							return;
						}
						while ((line = error.readLine()) != null) {
							System.out.println("from error: " + line);
						}
					} catch (IOException ex) {
						System.out.println(ex);
					}
				}
			});
			processThread.start();
			processThread.join();
			System.out.println("thread done");
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
	}

	private String getCurrentClusterStatus(String path) {
		String ret = null;
		try {

			ProcessBuilder pb = new ProcessBuilder(path);
//			ProcessBuilder pb = new ProcessBuilder("D:"+File.separator+"home"+File.separator+"python364x64"+File.separator+"python.exe", basepath + "/python_scripts/Cluster_State.py");
			Process p = pb.start();

			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			ret = in.readLine();
			System.out.println("value is : " + ret);
		} catch (Exception e) {
			System.out.println(e);
		}
		return ret;
	}

	private Optional<Tab> getTabForComponent(Component component) {
		return menu.getChildren().filter(tab -> ComponentUtil.getData(tab, Class.class).equals(component.getClass()))
				.findFirst().map(Tab.class::cast);
	}

	private String getCurrentPageTitle() {
		PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
		return title == null ? "" : title.value();
	}

	public void setTopToolbarComponent_Enable(boolean flag) {

		projectAddButton.setVisible(flag);
		projectOpenButton.setVisible(flag);
		projectSaveButton.setVisible(flag);
		projectCloseeButton.setVisible(flag);
		projectDeleteButton.setVisible(flag);
		dashboardButton.setVisible(flag);
		bugReportButton.setVisible(flag);
	}

	public void updateView(Component component) {
		if (component != null)
			setContent(component);
	}

	public void removeview() {

		setContent(null);
	}

	public Sds_Util getAppUtil() {
		if (sdsUtil != null) {
			return sdsUtil;
		}
		return null;
	}

	public void setApputil(Sds_Util util) {
		sdsUtil = util;
	}
}

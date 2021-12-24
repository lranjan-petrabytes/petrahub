package com.petrabytes.views.wells;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.project.util.ProjectSettingsInfo;
import com.petrabytes.project.util.WellsMainViewSettingsInfo;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.MainLayout;
import com.petrabytes.views.UI_Update;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.basin.BasinViewGridInfo;
import com.petrabytes.views.welllogs.WellLogsView.PB_FlexLayout;
import com.vaadin.addon.leaflet4vaadin.LeafletMap;
import com.vaadin.addon.leaflet4vaadin.layer.groups.LayerGroup;
import com.vaadin.addon.leaflet4vaadin.layer.map.options.DefaultMapOptions;
import com.vaadin.addon.leaflet4vaadin.layer.map.options.MapOptions;
import com.vaadin.addon.leaflet4vaadin.layer.ui.marker.Marker;
import com.vaadin.addon.leaflet4vaadin.plugins.markercluster.MarkerClusterGroup;
import com.vaadin.addon.leaflet4vaadin.plugins.markercluster.MarkerClusterOptions;
import com.vaadin.addon.leaflet4vaadin.types.LatLng;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.FormItem;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;

//@Push
@PageTitle("WellsMap")
public class WellsMapView extends HorizontalLayout {

	private FlexLayout mainLayot = new FlexLayout();
	private final UI ui = UI.getCurrent();
	private VerticalLayout vLayout = new VerticalLayout();
	private PB_FlexLayout mLayout = new PB_FlexLayout();
	private Button button = new Button(VaadinIcon.ANGLE_DOUBLE_LEFT.create());

	private ComboBox<String> companyCombobox = new ComboBox<String>();
	Checkbox companyCheckBox = new Checkbox();
	private ComboBox<String> statusCombobox = new ComboBox<String>();
	Checkbox statusCheckBox = new Checkbox();
	private ComboBox<String> stateCombobox = new ComboBox<String>();
	Checkbox stateCheckBox = new Checkbox();
	private ComboBox<String> wellTypeCombobox = new ComboBox<String>();
	Checkbox wellTypeCheckBox = new Checkbox();

	private TextField wellNameField = new TextField();
	private TextField apiField = new TextField();
	private TextField pageSizeField = new TextField();

	Label wellNumber = new Label();
	private Button searchButton = new Button();

	private FlexLayout mapLayot = new FlexLayout();
	private LeafletMap leafletMap = null;
	private MarkerClusterGroup markerClusterGroup = null;

//	private LeafletMap _leafletMap = null;
	private HashMap<String, Boolean> sets = null;
	private ProjectSettingsInfo projectSettings = null;
	private WellsMainViewSettingsInfo wellMapSettings = null;




	public WellsMapView() {
		// TODO Auto-generated constructor stub
	
		_setUI();
		
		

	}
	
	private void getProjectSetting() {
		// TODO Auto-generated method stub
		  projectSettings = View_Update.getProjectsettingInfo();
		if (projectSettings != null) {
			wellMapSettings = projectSettings.getViews().getWellSetting();
			if (wellMapSettings == null)
				wellMapSettings = new WellsMainViewSettingsInfo();
			
			sets = wellMapSettings.getSets();
			if (sets == null)
				sets = wellMapSettings.setDefaults();
		}
	}
	
	private void loadSetting() {
		// TODO Auto-generated method stub
	   //  companyCheckBox.setValue(true);
		companyCheckBox.setValue(sets.get("Company Name"));
      
		statusCheckBox.setValue(sets.get("Status"));
		stateCheckBox.setValue(sets.get("State"));
		wellTypeCheckBox.setValue(sets.get("well Type"));


	}


	private void _setUI() {
		// TODO Auto-generated method stub
		this.setSizeFull();
		this.setSpacing(false);
		this.setPadding(false);
		vLayout.setSpacing(false);
		vLayout.setPadding(false);
		vLayout.setSizeUndefined();
		mainLayot.setSizeFull();


		mLayout.setSizeFull();
		Label label = new Label("Settings");
//		label.setWidth("26px");
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
		vLayout.add(headLayout, layout);
		String projectname = (String) VaadinService.getCurrentRequest().getWrappedSession()
				.getAttribute("project_name");
		if (projectname == null) {
		//	Notification.show("Please first create the project", 3000, Position.BOTTOM_START).addThemeVariants(NotificationVariant.LUMO_CONTRAST);

		} else {
			
			UI_Update.updateEnable_topBar();
		
		
	
	
		this.add(mapLayot, vLayout);
		this.expand(mapLayot);
		createMapLayout();
	
		createEditorLayout();
		mLayout.focus();

		mLayout.setVisible(true);
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
				mLayout.focus();
				button.setIcon(VaadinIcon.ANGLE_DOUBLE_RIGHT.create());
			}

		});
		 getProjectSetting();
		 
			_companyCheckEvent2WithNotification();
			_companyComboboxEvent();
			
			_stateCheckEvent2WithNotification();
			_stateComboboxEvent();
			statusCheckEvent2WithNotification();
			_statusComboboxEvent();
			wellCheckEvent2WithNotification();
			
			
		
			
	
			
			_wellTypeComboboxEvent();
			
			
//			_searchEvent();
			_searchEvent2(true);
		
			// _statusCheckEventwithNotification();
		 loadSetting();
		}
	}

	private void createEditorLayout() {
		// TODO Auto-generated method stub
		Div editorLayoutDiv = new Div();
		editorLayoutDiv.setHeightFull();
		editorLayoutDiv.setClassName("flex flex-col");
		editorLayoutDiv.setWidth("300px");

		FormLayout searchFormLayout = new FormLayout();
		searchFormLayout.getStyle().set("padding-left", "15px");
		// FormItem basinItem = searchFormLayout.addFormItem(basinCombobox, "Basin");

		//// searchFormLayout.add(basinButtonsLayout);

		FormItem companyItem = searchFormLayout.addFormItem(companyCombobox, "Company Name");
		companyItem.add(companyCheckBox);

		FormItem statusItem = searchFormLayout.addFormItem(statusCombobox, "Status");
		statusItem.add(statusCheckBox);

		FormItem stateItem = searchFormLayout.addFormItem(stateCombobox, "State");
		stateItem.add(stateCheckBox);

		FormItem wellTypeItem = searchFormLayout.addFormItem(wellTypeCombobox, "Well Type");
		wellTypeItem.add(wellTypeCheckBox);

		searchFormLayout.addFormItem(wellNameField, "Well Name");
		searchFormLayout.addFormItem(apiField, "API");
		searchFormLayout.addFormItem(pageSizeField, "Page size");
		editorLayoutDiv.add(searchFormLayout);

		createButtonLayout(editorLayoutDiv);
		mLayout.add(editorLayoutDiv);



	
		
	}

	private void createButtonLayout(Div editorLayoutDiv) {
		// TODO Auto-generated method stub
		HorizontalLayout buttonLayout = new HorizontalLayout();
//		buttonLayout.setClassName("w-full flex-wrap bg-contrast-5 py-s px-l");
//		buttonLayout.setClassName("flex flex-col");
		buttonLayout.setClassName("w-full flex-wrap py-s px-l");
		buttonLayout.getStyle().set("padding-left", "17px");
//		buttonLayout.setSpacing(true);
		Image searchImage = new Image("images" + File.separator + "search24.png", "Search");
		searchButton.setIcon(searchImage);
		searchButton.getElement().setAttribute("title", "Search");

		buttonLayout.add(searchButton);
		editorLayoutDiv.add(buttonLayout);
	}

	private void createMapLayout() {
		// TODO Auto-generated method stub
		MapOptions options = new DefaultMapOptions();
		options.setCenter(new LatLng(29.7154233, -95.505180));
		options.setZoom(3);
		leafletMap = new LeafletMap(options);
//		leafletMap.setHeightFull();
		leafletMap.setSizeFull();
		leafletMap.addClassName("gis-map");
		leafletMap.setBaseUrl("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png");
		mapLayot.add(leafletMap);
		
		leafletMap.whenReady((e) -> {
			leafletMap.getBounds().thenAccept((bounds) -> {
				System.out.println("Current bounds: " + bounds);
				leafletMap.setMaxBounds(bounds);
			});
		});
	}


	private void _companyCheckEvent2WithNotification() {
		WellsQueries wellQuery = new WellsQueries();
		companyCheckBox.addValueChangeListener(event -> {
			if (companyCheckBox.getValue()) {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				String queryCompany = PetrahubNotification_Utilities.getInstance().queringCompanyInfoInWellMapsView();
			//	UI ui = getUI().get();
				executor.submit((Runnable) () -> {
					ui.access(() -> {
						// WellsQueries wellQuery = new WellsQueries();
						//notificatiion.setImage(queryCompany);
						notificatiion.setText(queryCompany);
						notificatiion.open();
					});
					String query = "select distinct(companyname)  from wells_db.wells where companyname is not Null order by companyname asc";
					List companyList;
					try {
						companyList = wellQuery.queryListData(query);
						ui.access(() -> {
							companyCombobox.setItems(companyList);
							String selectedCompanyName = wellMapSettings.getCompanyName();
							if (selectedCompanyName != null) {
								
								companyCombobox.setValue(selectedCompanyName);
							}
							String queryDone = PetrahubNotification_Utilities.getInstance().queryDoneInWellMapsView();
							notificatiion.setImage("success");
							notificatiion.setText(queryDone);
							notificatiion.close();
						});
					} catch (SQLException e) {
						String queryError = PetrahubNotification_Utilities.getInstance().queryErrorInWellMapsView();
						notificatiion.setImage("error");
						notificatiion.setText(queryError);
						e.printStackTrace();
					}
				});
			} else {
				List companyList = new ArrayList<String>();
				companyCombobox.setItems(companyList);
			}
			
			sets.replace("Company Name", companyCheckBox.getValue());
			updateSetting();
		});
	}

	private void _stateCheckEvent2WithNotification() {
		final WellsQueries wellQuery = new WellsQueries();
		stateCheckBox.addValueChangeListener(event -> {
			// Define notification
			if (stateCheckBox.getValue()) {
			//	ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
			//	UI ui = getUI().get();
			//	executor.submit(() -> {
				//	ui.access(() -> {
				String queryState = PetrahubNotification_Utilities.getInstance().queryStateInfoInWellMapsView();
						notificatiion.setImage("info");
						notificatiion.setText(queryState);
						notificatiion.open();
				//	});
					// do query
					String query = "select distinct(state)  from wells_db.wells order by state asc";
					List stateList;
					try {
						stateList = wellQuery.queryListData(query);

						// update notifcation in ui thread
					//	ui.access(() -> {
							stateCombobox.setItems(stateList);
							String selectedStateName = wellMapSettings.getState();
							if (selectedStateName != null) {
								stateCombobox.setValue(selectedStateName);
							}
							String queryDone = PetrahubNotification_Utilities.getInstance().queryDoneInWellMapsView();
							notificatiion.setImage("success");
							notificatiion.setText(queryDone);
							notificatiion.close();
					//	});
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						String queryError = PetrahubNotification_Utilities.getInstance().queryErrorInWellMapsView();
						notificatiion.setImage("error");
						notificatiion.setText(queryError);
						e.printStackTrace();
					}
//				});
			} else {
				List stateList = new ArrayList<String>();
				stateCombobox.setItems(stateList);
			}
			
			sets.replace("State", stateCheckBox.getValue());
			updateSetting();
		});
	}

	private void statusCheckEvent2WithNotification() {
		WellsQueries wellQuery = new WellsQueries();
		statusCheckBox.addValueChangeListener(event -> {
			if (statusCheckBox.getValue()) {
				//ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				String queryStatus = PetrahubNotification_Utilities.getInstance().queryStatusInfoInWellMapsView();
				//UI ui = getUI().get();
				//executor.submit((Runnable) () -> {
					//ui.access(() -> {
						// WellsQueries wellQuery = new WellsQueries();
						notificatiion.setImage("info");
						notificatiion.setText(queryStatus);
						notificatiion.open();
					//});
					String query = "select distinct(status)  from wells_db.wells where status is not Null order by status asc";
					List statusList;
					try {
						statusList = wellQuery.queryListData(query);
					//	ui.access(() -> {
							statusCombobox.setItems(statusList);
							String selectedStatus = wellMapSettings.getStatus();
							if (selectedStatus != null) {
								statusCombobox.setValue(selectedStatus);
							}
							String queryDone = PetrahubNotification_Utilities.getInstance().queryDoneInWellMapsView();
							notificatiion.setImage("success");
							notificatiion.setText(queryDone);
							notificatiion.close();
						//});
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						String queryError = PetrahubNotification_Utilities.getInstance().queryErrorInWellMapsView();
						notificatiion.setImage("error");
						notificatiion.setText(queryError);
						e.printStackTrace();
					}
				//});
			} else {
				List statusList = new ArrayList<String>();
				statusCombobox.setItems(statusList);
			}
			
			sets.replace("Status", statusCheckBox.getValue());
			updateSetting();
		});
	}

	private void wellCheckEvent2WithNotification() {
		WellsQueries wellQuery = new WellsQueries();
		wellTypeCheckBox.addValueChangeListener(event -> {
			if (wellTypeCheckBox.getValue()) {
				//ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				String wellType = PetrahubNotification_Utilities.getInstance().queryWelltypeInfolistInWellMapsView();
				//UI ui = getUI().get();
				//executor.submit((Runnable) () -> {
					//ui.access(() -> {
						// WellsQueries wellQuery = new WellsQueries();
						notificatiion.setImage("info");
						notificatiion.setText(wellType);
						notificatiion.open();
					//});
					String query = "select distinct(welltype)  from wells_db.wells where welltype is not Null order by welltype asc";
					List wellTypeList;
					try {
						wellTypeList = wellQuery.queryListData(query);
						//ui.access(() -> {
							wellTypeCombobox.setItems(wellTypeList);
							String selectedWelltype = wellMapSettings.getWelltype();
							if (selectedWelltype != null) {
								_searchEvent2(false);
								wellTypeCombobox.setValue(selectedWelltype);
							}
							String queryDone = PetrahubNotification_Utilities.getInstance().queryDoneInWellMapsView();
							notificatiion.setImage("success");
							notificatiion.setText(queryDone);
							notificatiion.close();
						//});
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						String queryError = PetrahubNotification_Utilities.getInstance().queryErrorInWellMapsView();
						notificatiion.setImage("error");
						notificatiion.setText(queryError);
						e.printStackTrace();
					}
				//});
			} else {
				List wellTypeList = new ArrayList<String>();
				wellTypeCombobox.setItems(wellTypeList);
			}
			
			sets.replace("well Type", wellTypeCheckBox.getValue());
			updateSetting();
		});
	}

	private void _companyComboboxEvent() {
		companyCombobox.addValueChangeListener(event -> {
			WellsQueries wellQuery = new WellsQueries();

			String wellNumbers = null;
			try {

				String selectedValue = companyCombobox.getValue();
				wellMapSettings.setCompanyName(selectedValue);
				projectSettings.getViews().setWellSetting(wellMapSettings);
				
				
				wellMapSettings.setCompanyName(selectedValue);
				String query = "select count(*)  from wells_db.wells where companyname = '" + selectedValue + "'";
				wellNumbers = wellQuery.querySingleValue(query);
				wellNumber.setText("Well Count: " + wellNumbers);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
	}

	private void _statusComboboxEvent() {
		// TODO Auto-generated method stub
		statusCombobox.addValueChangeListener(event -> {
			WellsQueries wellQuery = new WellsQueries();

			String wellNumbers = null;
			try {

				String selectedValue = statusCombobox.getValue();
				wellMapSettings.setStatus(selectedValue);
				projectSettings.getViews().setWellSetting(wellMapSettings);
			
				
				
				wellMapSettings.setStatus(selectedValue);
				String query = "select count(*)  from wells_db.wells where status = '" + selectedValue + "'";
				wellNumbers = wellQuery.querySingleValue(query);
				wellNumber.setText("Well Count: " + wellNumbers);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

	}

	private void _stateComboboxEvent() {
		// TODO Auto-generated method stub
		stateCombobox.addValueChangeListener(event -> {
			WellsQueries wellQuery = new WellsQueries();

			String wellNumbers = null;
			try {

				String selectedValue = stateCombobox.getValue();
				wellMapSettings.setState(selectedValue);
				projectSettings.getViews().setWellSetting(wellMapSettings);
				
				
				wellMapSettings.setState(selectedValue);
				String query = "select count(*)  from wells_db.wells where state = '" + selectedValue + "'";
				wellNumbers = wellQuery.querySingleValue(query);
				wellNumber.setText("Well Count: " + wellNumbers);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	private void _wellTypeComboboxEvent() {
		// TODO Auto-generated method stub
		wellTypeCombobox.addValueChangeListener(event -> {
			WellsQueries wellQuery = new WellsQueries();

			String wellNumbers = null;
			try {

				String selectedValue = wellTypeCombobox.getValue();
				wellMapSettings.setWelltype(selectedValue);
				projectSettings.getViews().setWellSetting(wellMapSettings);
			
				wellMapSettings.setWelltype(selectedValue);
				String query = "select count(*)  from wells_db.wells where welltype = '" + selectedValue + "'";
				wellNumbers = wellQuery.querySingleValue(query);
				wellNumber.setText("Well Count: " + wellNumbers);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	

	private void _searchEvent2(Boolean userClick) {
		// TODO Auto-generated method stub
		if (userClick) {
		searchButton.addClickListener(event -> {
			queryGisWelldata();
		
			});
		//});
		} else {
		//	JSONArray array = wellMapSettings.getMaparrayData();
			queryGisWelldata();
			}
		}
	
	
	private void queryGisWelldata() {
		//ExecutorService executor = Executors.newCachedThreadPool();
		PB_Progress_Notification notificatiion = new PB_Progress_Notification();
		//UI ui = getUI().get();
		//executor.submit(() -> {
			String selectedValue = stateCombobox.getValue();

			WellsQueries wellQuery = new WellsQueries();
//				Notification.show("Querying Deltalake: Retrieving Well Data. Please Wait . . .  ");

			//ui.access(() -> {
				notificatiion.setImage("info");
				notificatiion.setText("Querying Delta: Fetching wells information");
				notificatiion.open();
			//});

			String query = "select *  from wells_db.wells where state = '" + selectedValue + "'";

//			JSONArray array = null;
			try {
				JSONArray array = wellQuery.querySearchData(query);
//				wellMapSettings.setMaparrayData(array);
			//	projectSettings.getViews().setWellSetting(wellMapSettings);
			//	View_Update.setProjectsettingInfo(projectSettings);
	//			VaadinService.getCurrentRequest().getWrappedSession().setAttribute("project_settings", projectSettings);
			//	updateSetting();
				System.out.println("zwerre");
//				updateMap(array);
			//	ui.access(() -> {
					updateMap(array);
					notificatiion.setImage("info");
					notificatiion.setText("Updating Map. Displaying 1000 of " + array.length() + " wells.");
				//});
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//			Notification.show("Retrieved   " + array.length() + " Wells from " + selectedValue);

			//ui.access(() -> {
				notificatiion.setImage("success");
				notificatiion.setText("Querying Deltalake Done.");
		//	});

		//	ui.access(() -> {
				notificatiion.close();
		//	});
	}

	private void updateMap(JSONArray array) {
		// TODO Auto-generated method stub
//		markers.remove();

		if (markerClusterGroup == null) {
			MarkerClusterOptions options = new MarkerClusterOptions();
			options.setShowCoverageOnHover(false);
			markerClusterGroup = new MarkerClusterGroup(options);
			markerClusterGroup.onAdd(e -> {
				updatedata_map(array);
			});
		
			 markerClusterGroup.addTo(leafletMap);

		} else {
			markerClusterGroup.clearLayers();

			updatedata_map(array);
		}
		JSONObject object = array.getJSONObject(0);
		double latitude = object.getDouble("latitude");
		double longitude = object.getDouble("longitude");
		// mapsViewLayout.addMaker(latitude, longitude);
		LatLng point = new LatLng(latitude, longitude);
		
		 leafletMap.flyTo(point);

	}

	private void updatedata_map(JSONArray array) {
		LayerGroup markers = new LayerGroup();
		com.vaadin.addon.leaflet4vaadin.types.Icon icon = new com.vaadin.addon.leaflet4vaadin.types.Icon(
				"images/well_grey2_16.png", 41);
		for (int i = 0; i < array.length(); i++) {
			if (i < 1000) {
				JSONObject object = array.getJSONObject(i);
				double latitude = object.getDouble("latitude");
				double longitude = object.getDouble("longitude");
				String company = object.getString("companyname");
				String state = object.getString("state");
				String status = object.getString("status");
				String wellname = object.getString("wellname");
				String welltype = object.getString("welltype");
				String api = object.getString("api");
				Marker marker = new Marker(new LatLng(latitude, longitude));
				marker.setIcon(icon);
				/**
				 * setting popup view for each marker
				 * 
				 */
				marker.bindPopup("Comapany Name :" + company + "<br>" + "State :" + state + "<br>" + "status :" + status
						+ "<br>" + "Well Name :" + wellname + "<br>" + "Well Type :" + welltype + "<br>" + "API :" + api
						+ "<br>" + "Latitude :" + latitude + "<br>" + "Longitude :" + longitude);
				marker.addTo(markers);
			}
		}
		markers.addTo(markerClusterGroup);
	}
	
	private void updateSetting() {

		wellMapSettings.setSets(sets);
		projectSettings.getViews().setWellSetting(wellMapSettings);
		//VaadinService.getCurrentRequest().getWrappedSession().setAttribute("project_settings", projectSettings);
	}

	public class PB_FlexLayout extends FlexLayout implements Focusable {

		public PB_FlexLayout() {
			this.setTabIndex(0);
		}
	}

}

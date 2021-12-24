package com.petrabytes.views.gis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;
//import org.geojson.Polygon;

import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.project.util.GisInfo;
import com.petrabytes.project.util.ProjectSettingsInfo;
import com.petrabytes.project.util.WellLogsProjectViewsSettingsInfo;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.MainLayout;
import com.petrabytes.views.UI_Update;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.welllogs.WellLogsInfo;
import com.vaadin.addon.leaflet4vaadin.LeafletMap;
import com.vaadin.addon.leaflet4vaadin.layer.Layer;
import com.vaadin.addon.leaflet4vaadin.layer.groups.LayerGroup;
import com.vaadin.addon.leaflet4vaadin.layer.map.options.DefaultMapOptions;
import com.vaadin.addon.leaflet4vaadin.layer.map.options.MapOptions;
import com.vaadin.addon.leaflet4vaadin.layer.ui.marker.Marker;
import com.vaadin.addon.leaflet4vaadin.layer.vectors.Polygon;
import com.vaadin.addon.leaflet4vaadin.layer.vectors.Polyline;
import com.vaadin.addon.leaflet4vaadin.types.Icon;
import com.vaadin.addon.leaflet4vaadin.types.LatLng;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;

@Route(value = "gis", layout = MainLayout.class)
//@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("GIS")
public class GISView extends HorizontalLayout {

	private UI ui = UI.getCurrent();
	Checkbox biodieselCheckBox = new Checkbox("Bio Diesel");
	Checkbox bordercrossing_electricCheckBox = new Checkbox("Border Crossing Electric");
	Checkbox bcliquidCheckBox = new Checkbox("Border Crossing Liquids");
	Checkbox ethanolplantsCheckBox = new Checkbox("Ethanol Plants");
	Checkbox coalminesCheckBox = new Checkbox("Coal Mines");
	Checkbox crudeOilRailTCheckBox = new Checkbox("Crude Oil Rail Terminals");
	Checkbox productlinesCheckBox = new Checkbox("Product Lines");
	Checkbox naturalgaslinesCheckBox = new Checkbox("Natural Gas Lines");
	Checkbox hglinesCheckBox = new Checkbox("HG Lines");
	Checkbox naturalgasmarkethubsCheckBox = new Checkbox("Natural Gas Market Hubs");
	Checkbox wellschematicCheckBox = new Checkbox("Well Schematic");
	Checkbox tieghtoilshaleplayCheckBox = new Checkbox("Tight Oil Shale Play");
	Checkbox naturalgasstorageCheckBox = new Checkbox("Natural Gas Storage");
	Checkbox ethylene_crackersCheckBox = new Checkbox("Ethylene Crackers");
	Checkbox geothermalCheckBox = new Checkbox("Geothermal");
	Checkbox lngimpexpterminalsCheckBox = new Checkbox("LNG Impexp Terminals");
	Checkbox crudeoilpipelineCheckBox = new Checkbox("Crude Oil Pipeline");
	Checkbox shaleplayshxCheckBox = new Checkbox("Shale Play Shx");

	private FlexLayout mainLayot = new FlexLayout();
	private FlexLayout mapLayot = new FlexLayout();
	private LeafletMap leafletMap = null;
	private String info = "";
	private LayerGroup layer = new LayerGroup();

	private VerticalLayout vLayout = new VerticalLayout();
	private PB_FlexLayout mLayout = new PB_FlexLayout();
	private Button button = new Button(VaadinIcon.ANGLE_DOUBLE_LEFT.create());

	private Icon biodiesel_Icon = new Icon("images/bio_diesel_16.png", 16);
	private Icon bcelectric_Icon = new Icon("images/border_crossing_electric16.png", 16);
	private Icon bcliquid_Icon = new Icon("images/border_crossing_liquids16.png", 16);
	private Icon coalmines_Icon = new Icon("images/coal_mines_16.png", 16);
	private Icon ethanol_Icon = new Icon("images/ethanol_plants16.png", 16);
	private Icon ethylene_Icon = new Icon("images/ethylene_crackers16.png", 16);
	private Icon fault_Icon = new Icon("images/fault_16.png", 16);
	private Icon lng_Icon = new Icon("images/LNG_terminal16.png", 16);
	private Icon ng_pipeline_Icon = new Icon("images/natural_gas_pipeline16.png", 16);
	private Icon ng_hub_Icon = new Icon("images/natural_gas_hub16.png", 16);
	private Icon productlines_Icon = new Icon("images/product_lines_16.png", 16);
	private Icon refineries_Icon = new Icon("images/refineries_16.png", 16);
	private Icon shape_Icon = new Icon("images/shape_16.png", 16);
	private Icon shale_Icon = new Icon("images/shape_16.png", 16);
	private Icon crudeoil_Icon = new Icon("images/crude_oil_terminal16.png", 16);

	private HashMap<String, Boolean> sets = null;
	private ProjectSettingsInfo projectSettings = null;
	private GisInfo gisviewSettings = null;

	public GISView() {
		// TODO Auto-generated constructor stub

		_setUI();
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
		////////////////////////////////////////
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
			///////////////////////////////////////
			this.add(mapLayot, vLayout);
			this.expand(mapLayot);

			createMapLayout();
			createEditorLayout();
			mLayout.focus();

			mLayout.setVisible(false);
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

//		mapRender();
			// updateSetting();
			getProjectSetting();
			shapeButtonEvent();
			border_crossing_electricEvent();
			ethylene_crackersEvent();
			crudeButtonEvent();
			biodieselButtonEvent();
			coalButtonEvent();
			ethanolButtonEvent();
			ngMarketButtonEvent();
			lng_impexp_terminalsEvent();
			bcliquidButtonEvent();
			crudeOilRailTButtonEvent();
			hgLinesButtonEvent();
			loadSetting();
		}
	}

	private void getProjectSetting() {
		// TODO Auto-generated method stub
		// projectSettings = (ProjectSettingsInfo)
		// VaadinService.getCurrentRequest().getWrappedSession().getAttribute("project_settings");
		projectSettings = View_Update.getProjectsettingInfo();
		if (projectSettings != null) {
			gisviewSettings = projectSettings.getViews().getGisviewSettings();
			if (gisviewSettings == null)
				gisviewSettings = new GisInfo();
			sets = gisviewSettings.getSets();
			if (sets == null)
				sets = gisviewSettings.setDefaults();
		}
	}

	private void loadSetting() {
		// TODO Auto-generated method stub
		biodieselCheckBox.setValue(sets.get("Bio Diesel"));

		bordercrossing_electricCheckBox.setValue(sets.get("Border Crossing Electric"));
		bcliquidCheckBox.setValue(sets.get("Border Crossing Liquids"));
		ethanolplantsCheckBox.setValue(sets.get("Ethanol Plants"));
		coalminesCheckBox.setValue(sets.get("Coal Mines"));
		crudeOilRailTCheckBox.setValue(sets.get("Crude Oil Rail Terminals"));
		productlinesCheckBox.setValue(sets.get("Product Lines"));
		naturalgaslinesCheckBox.setValue(sets.get("Natural Gas Lines"));
		hglinesCheckBox.setValue(sets.get("HG Lines"));
		naturalgasmarkethubsCheckBox.setValue(sets.get("Natural Gas Market Hubs"));
		wellschematicCheckBox.setValue(sets.get("Well Schematic"));
		tieghtoilshaleplayCheckBox.setValue(sets.get("Tight Oil Shale Play"));
		naturalgasstorageCheckBox.setValue(sets.get("Natural Gas Storage"));
		ethylene_crackersCheckBox.setValue(sets.get("Ethylene Crackers"));
		geothermalCheckBox.setValue(sets.get("Geothermal"));
		lngimpexpterminalsCheckBox.setValue(sets.get("LNG Impexp Terminals"));
		crudeoilpipelineCheckBox.setValue(sets.get("Crude Oil Pipeline"));
		shaleplayshxCheckBox.setValue(sets.get("Shale Play Shx"));

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

	private void createEditorLayout() {
		// TODO Auto-generated method stub
		Div editorLayoutDiv = new Div();
		editorLayoutDiv.setHeightFull();
		editorLayoutDiv.setClassName("flex flex-col");
		editorLayoutDiv.setWidth("300px");

		FormLayout searchFormLayout = new FormLayout();
		searchFormLayout.getStyle().set("padding-left", "15px");

		searchFormLayout.add(biodieselCheckBox);
		searchFormLayout.add(bordercrossing_electricCheckBox);
		searchFormLayout.add(bcliquidCheckBox);
		searchFormLayout.add(coalminesCheckBox);
		searchFormLayout.add(crudeoilpipelineCheckBox);
		searchFormLayout.add(crudeOilRailTCheckBox);

		searchFormLayout.add(ethanolplantsCheckBox);
		searchFormLayout.add(ethylene_crackersCheckBox);
		searchFormLayout.add(hglinesCheckBox);
		searchFormLayout.add(lngimpexpterminalsCheckBox);
		searchFormLayout.add(naturalgasmarkethubsCheckBox);
		searchFormLayout.add(tieghtoilshaleplayCheckBox);
		editorLayoutDiv.add(searchFormLayout);

		mLayout.add(editorLayoutDiv);
	}

	private void removeComponentFromLayer(String attribute) {
		// LayerGroup layer = null;
		List<Layer> layList = layer.getLayers();
		for (int i = 0; i < layList.size(); i++) {
			Layer la = layList.get(i);
			if (la.getAttribution().equals(attribute)) {
				layer.removeLayer(la);
				i--;
			}
		}
	}

	private void hgLinesButtonEvent() {

		hglinesCheckBox.addValueChangeListener(event -> {

			if (!hglinesCheckBox.getValue()) {
				removeComponentFromLayer("HG Lines");
			} else {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				executor.submit((Runnable) () -> {
					ui.access(() -> {
						notificatiion.setImage("info");
						notificatiion.setText("Querying Deltalake: HG Line Information");
						notificatiion.open();
						removeComponentFromLayer("HG Lines");
					});
					String query = "select * from gis_db.hgl_pipelines_us";
					JSONArray array = new GISQuery().getGISQuery(query);

					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						ArrayList<String> keyList = new ArrayList<String>(obj.keySet());
						info = "";
						for (String key : keyList) {
							if (!key.equals("wkt"))
								info = info + key + " : " + obj.get(key).toString() + "<br>";
						}
						String wktStg = obj.getString("wkt");
						wktStg = wktStg.replace(")", "");
						String type = wktStg.substring(0, wktStg.indexOf("(")).trim();
						String data = wktStg.substring(wktStg.indexOf("(") + 1, wktStg.length());
						if (type.equalsIgnoreCase("linestring")) {
							String[] datas = data.split(",");
							List<LatLng> latLng = new ArrayList<>();
							for (String xydata : datas) {
								xydata = xydata.trim();
								String[] xy = xydata.split(" ");
								latLng.add(new LatLng(Double.valueOf(xy[1].trim()), Double.valueOf(xy[0].trim())));
							}
							ui.access(() -> {
								Polyline polyline = new Polyline(latLng);
								polyline.setFill(false);
								polyline.setColor("blue");
								polyline.bindPopup(info);
								polyline.addTo(layer);
								polyline.setAttribution("HG Lines");
								notificatiion.setImage("success");
								notificatiion.setText("Querying Deltalake Done");
								notificatiion.setDuration(20000);
								notificatiion.close();
							});
						}
					}

				});
				layer.addTo(leafletMap);

			}
			sets.replace("HG Lines", hglinesCheckBox.getValue());
			updateSetting();
		});

	}

	private void crudeOilRailTButtonEvent() {

		crudeOilRailTCheckBox.addValueChangeListener(event -> {
			if (!crudeOilRailTCheckBox.getValue()) {
				removeComponentFromLayer("Crude Oil Rail Terminals");
			} else {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				executor.submit((Runnable) () -> {
					ui.access(() -> {
						PB_Progress_Notification notification = new PB_Progress_Notification();
						notification.setImage("info");
						notification.setText("Querying Deltalake: Crude Oil terminals Information");
						notification.open();
						notification.setDuration(3000);
					});
					String query = "select * from gis_db.crudeoil_railterminals";
					JSONArray array = new GISQuery().getGISQuery(query);

					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						ArrayList<String> keyList = new ArrayList<String>(obj.keySet());
						info = "";
						for (String key : keyList) {
							if (!key.equals("wkt"))
								info = info + key + " : " + obj.get(key).toString() + "<br>";
						}
						String wktStg = obj.getString("wkt");
						wktStg = wktStg.replace(")", "");
						String type = wktStg.substring(0, wktStg.indexOf("(")).trim();
						String data = wktStg.substring(wktStg.indexOf("(") + 1, wktStg.length());
						if (type.equalsIgnoreCase("point")) {
							String[] datas = data.split(",");
							for (String xydata : datas) {
								xydata = xydata.trim();
								String[] xy = xydata.split(" ");
								LatLng latlng = new LatLng(Double.valueOf(xy[1].trim()), Double.valueOf(xy[0].trim()));
								ui.access(() -> {
									Marker marker = new Marker(latlng);
									marker.bindPopup(info);
									marker.setIcon(crudeoil_Icon);
									marker.addTo(layer);
									marker.setAttribution("Crude Oil Rail Terminals");
									notificatiion.setImage("success");
									notificatiion.setText("Querying Deltalake Done");
									notificatiion.setDuration(20000);
									notificatiion.close();
								});
							}

						}
					}
				});
				layer.addTo(leafletMap);

			}
			sets.replace("Crude Oil Rail Terminals", crudeOilRailTCheckBox.getValue());
			updateSetting();
		});

	}

	private void bcliquidButtonEvent() {

		bcliquidCheckBox.addValueChangeListener(event -> {
			if (!bcliquidCheckBox.getValue()) {
				removeComponentFromLayer("Border Crossing Liquids");
			} else {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();

				executor.submit((Runnable) () -> {
					ui.access(() -> {
						PB_Progress_Notification notification = new PB_Progress_Notification();
						notification.setImage("info");
						notification.setText("Querying Deltalake: Border Crossing Liquids Information");
						notification.open();
						notification.setDuration(3000);
								
					});
					String query = "select * from gis_db.bordercrossing_liquids_us";
					JSONArray array = new GISQuery().getGISQuery(query);

					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						ArrayList<String> keyList = new ArrayList<String>(obj.keySet());
						info = "";
						for (String key : keyList) {
							if (!key.equals("wkt"))
								info = info + key + " : " + obj.get(key).toString() + "<br>";
						}
						String wktStg = obj.getString("wkt");
						wktStg = wktStg.replace(")", "");
						String type = wktStg.substring(0, wktStg.indexOf("(")).trim();
						String data = wktStg.substring(wktStg.indexOf("(") + 1, wktStg.length());
						if (type.equalsIgnoreCase("point")) {
							String[] datas = data.split(",");
							for (String xydata : datas) {
								xydata = xydata.trim();
								String[] xy = xydata.split(" ");
								LatLng latlng = new LatLng(Double.valueOf(xy[1].trim()), Double.valueOf(xy[0].trim()));
								ui.access(() -> {
									Marker marker = new Marker(latlng);
									marker.bindPopup(info);
									marker.setIcon(bcliquid_Icon);
									marker.addTo(layer);
									marker.setAttribution("Border Crossing Liquids");
									notificatiion.setImage("success");
									notificatiion.setText("Querying Deltalake Done");
									notificatiion.setDuration(20000);
									notificatiion.close();
								});
							}

						}
					}
				});
				layer.addTo(leafletMap);

			}
			sets.replace("Border Crossing Liquids", bcliquidCheckBox.getValue());
			updateSetting();
		});

	}

	private void ethylene_crackersEvent() {

		ethylene_crackersCheckBox.addValueChangeListener(event -> {
			if (!ethylene_crackersCheckBox.getValue()) {
				removeComponentFromLayer("Ethylene Crackers");
			} else {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();

				executor.submit((Runnable) () -> {
					ui.access(() -> {
						PB_Progress_Notification notification = new PB_Progress_Notification();
						notification.setImage("info");
						notification.setText("Querying Deltalake: Ethylene Crackers Information");
						notification.open();
						notification.setDuration(3000);
						
					});
					String query = "select * from gis_db.ethylene_crackers_us";
					JSONArray array = new GISQuery().getGISQuery(query);

					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						ArrayList<String> keyList = new ArrayList<String>(obj.keySet());
						info = "";
						for (String key : keyList) {
							if (!key.equals("wkt"))
								info = info + key + " : " + obj.get(key).toString() + "<br>";
						}
						String wktStg = obj.getString("wkt");
						wktStg = wktStg.replace(")", "");
						String type = wktStg.substring(0, wktStg.indexOf("(")).trim();
						String data = wktStg.substring(wktStg.indexOf("(") + 1, wktStg.length());
						if (type.equalsIgnoreCase("point")) {
							String[] datas = data.split(",");
							for (String xydata : datas) {
								xydata = xydata.trim();
								String[] xy = xydata.split(" ");
								LatLng latlng = new LatLng(Double.valueOf(xy[1].trim()), Double.valueOf(xy[0].trim()));
								ui.access(() -> {
									Marker marker = new Marker(latlng);
									marker.bindPopup(info);
									marker.setIcon(ethylene_Icon);
									marker.addTo(layer);
									marker.setAttribution("Ethylene Crackers");
									notificatiion.setImage("success");
									notificatiion.setText("Querying Deltalake Done");
									notificatiion.setDuration(20000);
									notificatiion.close();
								});
							}

						}
					}
				});
				layer.addTo(leafletMap);

			}
			sets.replace("Ethylene Crackers", ethylene_crackersCheckBox.getValue());
			updateSetting();
		});

	}

	private void lng_impexp_terminalsEvent() {

		lngimpexpterminalsCheckBox.addValueChangeListener(event -> {
			if (!lngimpexpterminalsCheckBox.getValue()) {
				removeComponentFromLayer("LNG Impexp Terminals");
			} else {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				executor.submit((Runnable) () -> {
					ui.access(() -> {
						PB_Progress_Notification notification = new PB_Progress_Notification();
						notification.setImage("info");
						notification.setText("Querying Deltalake: LNG Terminal Information");
						notification.open();
						notification.setDuration(3000);
						
					});
					String query = "select * from gis_db.lng_impexp_terminals_us";
					JSONArray array = new GISQuery().getGISQuery(query);

					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						ArrayList<String> keyList = new ArrayList<String>(obj.keySet());
						info = "";
						for (String key : keyList) {
							if (!key.equals("wkt"))
								info = info + key + " : " + obj.get(key).toString() + "<br>";
						}
						String wktStg = obj.getString("wkt");
						wktStg = wktStg.replace(")", "");
						String type = wktStg.substring(0, wktStg.indexOf("(")).trim();
						String data = wktStg.substring(wktStg.indexOf("(") + 1, wktStg.length());
						if (type.equalsIgnoreCase("point")) {
							String[] datas = data.split(",");
							for (String xydata : datas) {
								xydata = xydata.trim();
								String[] xy = xydata.split(" ");
								LatLng latlng = new LatLng(Double.valueOf(xy[1].trim()), Double.valueOf(xy[0].trim()));
								ui.access(() -> {
									Marker marker = new Marker(latlng);
									marker.bindPopup(info);
									marker.setIcon(lng_Icon);
									marker.addTo(layer);
									marker.setAttribution("LNG Impexp Terminals");
									notificatiion.setImage("success");
									notificatiion.setText("Querying Deltalake Done");
									notificatiion.setDuration(20000);
									notificatiion.close();
								});
							}

						}
					}
				});
				layer.addTo(leafletMap);

			}
			sets.replace("LNG Impexp Terminals", lngimpexpterminalsCheckBox.getValue());
			updateSetting();
		});

	}

	private void border_crossing_electricEvent() {

		bordercrossing_electricCheckBox.addValueChangeListener(event -> {
			if (!bordercrossing_electricCheckBox.getValue()) {
				removeComponentFromLayer("Border Crossing Electric");
			} else {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				executor.submit((Runnable) () -> {
					ui.access(() -> {
						PB_Progress_Notification notification = new PB_Progress_Notification();
						notification.setImage("info");
						notification.setText("Querying Deltalake: Border Crossing Electric Information");
						notification.open();
						notification.setDuration(3000);
													});
					String query = "select * from gis_db.bordercrossing_electric_us";
					JSONArray array = new GISQuery().getGISQuery(query);

					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						ArrayList<String> keyList = new ArrayList<String>(obj.keySet());
						info = "";
						for (String key : keyList) {
							if (!key.equals("wkt"))
								info = info + key + " : " + obj.get(key).toString() + "<br>";
						}
						String wktStg = obj.getString("wkt");
						wktStg = wktStg.replace(")", "");
						String type = wktStg.substring(0, wktStg.indexOf("(")).trim();
						String data = wktStg.substring(wktStg.indexOf("(") + 1, wktStg.length());
						if (type.equalsIgnoreCase("point")) {
							String[] datas = data.split(",");
							for (String xydata : datas) {
								xydata = xydata.trim();
								String[] xy = xydata.split(" ");
								LatLng latlng = new LatLng(Double.valueOf(xy[1].trim()), Double.valueOf(xy[0].trim()));
								ui.access(() -> {
									Marker marker = new Marker(latlng);
									marker.bindPopup(info);
									marker.setIcon(bcelectric_Icon);
									marker.addTo(layer);
									marker.setAttribution("Border Crossing Electric");
									notificatiion.setImage("success");
									notificatiion.setText("Querying Deltalake Done");
									notificatiion.setDuration(20000);
									notificatiion.close();
								});
							}

						}
					}
				});
				layer.addTo(leafletMap);

			}
			sets.replace("Border Crossing Electric", bordercrossing_electricCheckBox.getValue());
			updateSetting();
		});

	}

	private void crudeButtonEvent() {

		crudeoilpipelineCheckBox.addValueChangeListener(event -> {
			if (!crudeoilpipelineCheckBox.getValue()) {
				removeComponentFromLayer("Crude Oil Pipeline");
			} else {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				executor.submit((Runnable) () -> {
					ui.access(() -> {
						PB_Progress_Notification notification = new PB_Progress_Notification();
						notification.setImage("info");
						notification.setText("Querying Deltalake: Crude Oil pipeline Information");
						notification.open();
						notification.setDuration(3000);
						
					});
					String query = "select * from gis_db.crudeoil_pipelines_us";
					JSONArray array = new GISQuery().getGISQuery(query);

					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						ArrayList<String> keyList = new ArrayList<String>(obj.keySet());
						info = "";
						for (String key : keyList) {
							if (!key.equals("wkt"))
								info = info + key + " : " + obj.get(key).toString() + "<br>";
						}
						String wktStg = obj.getString("wkt");
						wktStg = wktStg.replace(")", "");
						String type = wktStg.substring(0, wktStg.indexOf("(")).trim();
						String data = wktStg.substring(wktStg.indexOf("(") + 1, wktStg.length());
						if (type.equalsIgnoreCase("linestring")) {
							String[] datas = data.split(",");
							List<LatLng> latLng = new ArrayList<>();
							for (String xydata : datas) {
								xydata = xydata.trim();
								String[] xy = xydata.split(" ");
								latLng.add(new LatLng(Double.valueOf(xy[1].trim()), Double.valueOf(xy[0].trim())));
							}
							ui.access(() -> {
								Polyline polyline = new Polyline(latLng);
								polyline.setFill(false);
								polyline.setColor("black");
								polyline.bindPopup(info);
								polyline.addTo(layer);
								polyline.setAttribution("Crude Oil Pipeline");
								notificatiion.setImage("success");
								notificatiion.setText("Querying Deltalake Done");
								notificatiion.setDuration(20000);
								notificatiion.close();
							});
						}
					}

				});
				layer.addTo(leafletMap);

			}
			sets.replace("Crude Oil Pipeline", crudeoilpipelineCheckBox.getValue());
			updateSetting();
		});

	}

	private void biodieselButtonEvent() {

		biodieselCheckBox.addValueChangeListener(event -> {
			if (!biodieselCheckBox.getValue()) {
				removeComponentFromLayer("Bio Diesel");
			} else {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				executor.submit((Runnable) () -> {
					ui.access(() -> {
						PB_Progress_Notification notification = new PB_Progress_Notification();
						notification.setImage("info");
						notification.setText("Querying Deltalake: Biodiesel Information");
						notification.open();
						notification.setDuration(3000);
						
					});
					String query = "select * from gis_db.biodiesel_plants_us";
					JSONArray array = new GISQuery().getGISQuery(query);

					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						ArrayList<String> keyList = new ArrayList<String>(obj.keySet());
						info = "";
						for (String key : keyList) {
							if (!key.equals("wkt"))
								info = info + key + " : " + obj.get(key).toString() + "<br>";
						}
						String wktStg = obj.getString("wkt");
						wktStg = wktStg.replace(")", "");
						String type = wktStg.substring(0, wktStg.indexOf("(")).trim();
						String data = wktStg.substring(wktStg.indexOf("(") + 1, wktStg.length());
						if (type.equalsIgnoreCase("point")) {
							String[] datas = data.split(",");
							for (String xydata : datas) {
								xydata = xydata.trim();
								String[] xy = xydata.split(" ");
								LatLng latlng = new LatLng(Double.valueOf(xy[1].trim()), Double.valueOf(xy[0].trim()));
								ui.access(() -> {
									Marker marker = new Marker(latlng);
									marker.bindPopup(info);
									marker.setIcon(biodiesel_Icon);
									marker.addTo(layer);
									marker.setAttribution("Bio Diesel");
									notificatiion.setImage("success");
									notificatiion.setText("Querying Deltalake Done");
									notificatiion.setDuration(20000);
									notificatiion.close();
								});
							}

						}
					}
				});
				layer.addTo(leafletMap);

			}
			sets.replace("Bio Diesel", biodieselCheckBox.getValue());
			updateSetting();
		});

	}

	private void coalButtonEvent() {

		coalminesCheckBox.addValueChangeListener(event -> {
			if (!coalminesCheckBox.getValue()) {
				removeComponentFromLayer("Coal Mines");
			} else {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				executor.submit((Runnable) () -> {
					ui.access(() -> {
						PB_Progress_Notification notification = new PB_Progress_Notification();
						notification.setImage("info");
						notification.setText("Querying Deltalake: Coal Mine Information");
						notification.open();
						notification.setDuration(3000);
						
					});
					String query = "select * from gis_db.coalmines_us";
					JSONArray array = new GISQuery().getGISQuery(query);

					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						ArrayList<String> keyList = new ArrayList<String>(obj.keySet());
						info = "";
						for (String key : keyList) {
							if (!key.equals("wkt"))
								info = info + key + " : " + obj.get(key).toString() + "<br>";
						}
						String wktStg = obj.getString("wkt");
						wktStg = wktStg.replace(")", "");
						String type = wktStg.substring(0, wktStg.indexOf("(")).trim();
						String data = wktStg.substring(wktStg.indexOf("(") + 1, wktStg.length());
						if (type.equalsIgnoreCase("point")) {
							String[] datas = data.split(",");
							for (String xydata : datas) {
								xydata = xydata.trim();
								String[] xy = xydata.split(" ");
								LatLng latlng = new LatLng(Double.valueOf(xy[1].trim()), Double.valueOf(xy[0].trim()));
								ui.access(() -> {
									Marker marker = new Marker(latlng);
									marker.bindPopup(info);
									marker.setIcon(coalmines_Icon);
									marker.addTo(layer);
									marker.setAttribution("Coal Mines");
									notificatiion.setImage("success");
									notificatiion.setText("Querying Deltalake Done");
									notificatiion.setDuration(20000);
									notificatiion.close();
								});
							}

						}
					}
				});
				layer.addTo(leafletMap);

			}
			sets.replace("Coal Mines", coalminesCheckBox.getValue());
			updateSetting();
		});

	}

	private void ethanolButtonEvent() {

		ethanolplantsCheckBox.addValueChangeListener(event -> {
			if (!ethanolplantsCheckBox.getValue()) {
				removeComponentFromLayer("Ethanol Plants");
			} else {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				executor.submit((Runnable) () -> {
					ui.access(() -> {
						PB_Progress_Notification notification = new PB_Progress_Notification();
						notification.setImage("info");
						notification.setText("Querying Deltalake: Ethanol Information");
						notification.open();
						notification.setDuration(3000);
					
					});
					String query = "select * from gis_db.ethanol_plants_us";
					JSONArray array = new GISQuery().getGISQuery(query);

					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						ArrayList<String> keyList = new ArrayList<String>(obj.keySet());
						info = "";
						for (String key : keyList) {
							if (!key.equals("wkt"))
								info = info + key + " : " + obj.get(key).toString() + "<br>";
						}
						String wktStg = obj.getString("wkt");
						wktStg = wktStg.replace(")", "");
						String type = wktStg.substring(0, wktStg.indexOf("(")).trim();
						String data = wktStg.substring(wktStg.indexOf("(") + 1, wktStg.length());
						if (type.equalsIgnoreCase("point")) {
							String[] datas = data.split(",");
							for (String xydata : datas) {
								xydata = xydata.trim();
								String[] xy = xydata.split(" ");
								LatLng latlng = new LatLng(Double.valueOf(xy[1].trim()), Double.valueOf(xy[0].trim()));
								ui.access(() -> {
									Marker marker = new Marker(latlng);
									marker.bindPopup(info);
									marker.setIcon(ethanol_Icon);
									marker.addTo(layer);
									marker.setAttribution("Ethanol Plants");
									notificatiion.setImage("success");
									notificatiion.setText("Querying Deltalake Done");
									notificatiion.setDuration(20000);
									notificatiion.close();
								});
							}

						}
					}
				});
				layer.addTo(leafletMap);

			}
			sets.replace("Ethanol Plants", ethanolplantsCheckBox.getValue());
			updateSetting();
		});

	}

	private void ngMarketButtonEvent() {

		naturalgasmarkethubsCheckBox.addValueChangeListener(event -> {
			if (!naturalgasmarkethubsCheckBox.getValue()) {
				removeComponentFromLayer("Natural Gas Market Hubs");
			} else {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				executor.submit((Runnable) () -> {
					ui.access(() -> {
						PB_Progress_Notification notification = new PB_Progress_Notification();
						notification.setImage("info");
						notification.setText("Querying Deltalake: Natural Gas Market Information");
						notification.open();
						notification.setDuration(3000);
					
					});
					String query = "select * from gis_db.naturalgas_markethubs_us";
					JSONArray array = new GISQuery().getGISQuery(query);

					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						ArrayList<String> keyList = new ArrayList<String>(obj.keySet());
						info = "";
						for (String key : keyList) {
							if (!key.equals("wkt"))
								info = info + key + " : " + obj.get(key).toString() + "<br>";
						}
						String wktStg = obj.getString("wkt");
						wktStg = wktStg.replace(")", "");
						String type = wktStg.substring(0, wktStg.indexOf("(")).trim();
						String data = wktStg.substring(wktStg.indexOf("(") + 1, wktStg.length());
						if (type.equalsIgnoreCase("point")) {
							String[] datas = data.split(",");
							for (String xydata : datas) {
								xydata = xydata.trim();
								String[] xy = xydata.split(" ");
								LatLng latlng = new LatLng(Double.valueOf(xy[1].trim()), Double.valueOf(xy[0].trim()));
								ui.access(() -> {
									Marker marker = new Marker(latlng);
									marker.bindPopup(info);
									marker.setIcon(ng_hub_Icon);
									marker.addTo(layer);
									marker.setAttribution("Natural Gas Market Hubs");
									notificatiion.setImage("success");
									notificatiion.setText("Querying Deltalake Done");
									notificatiion.setDuration(20000);
									notificatiion.close();
								});
							}

						}
					}
				});
				layer.addTo(leafletMap);

			}
			sets.replace("Natural Gas Market Hubs", naturalgasmarkethubsCheckBox.getValue());
			updateSetting();
		});

	}

	private void shapeButtonEvent() {

		tieghtoilshaleplayCheckBox.addValueChangeListener(event -> {
			if (!tieghtoilshaleplayCheckBox.getValue()) {
				removeComponentFromLayer("Tight Oil Shale Play");
			} else {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				executor.submit((Runnable) () -> {
					ui.access(() -> {
						PB_Progress_Notification notification = new PB_Progress_Notification();
						notification.setImage("info");
						notification.setText("Querying Deltalake: Tight Shale Information");
						notification.open();
						notification.setDuration(3000);
						
						// removeComponentFromLayer("Tight Oil Shale Play");
					});
					String query = "select * from gis_db.tight_shale_plays";
					JSONArray array = new GISQuery().getGISQuery(query);

					for (int i = 0; i < array.length(); i++) {
						JSONObject obj = array.getJSONObject(i);
						ArrayList<String> keyList = new ArrayList<String>(obj.keySet());
						info = "";
						for (String key : keyList) {
							if (!key.equals("wkt"))
								info = info + key + " : " + obj.get(key).toString() + "<br>";
						}
						String wktStg = obj.getString("wkt");
						wktStg = wktStg.replace("((", ":").replace(")", "").replace("(", "");
						String[] wktStgs = wktStg.split(":");
						String type = wktStgs[0].trim();
						String data = wktStgs[1];
						if (type.equalsIgnoreCase("polygon")) {
							String[] datas = data.split(",");
							List<LatLng> latLng = new ArrayList<>();
							for (String xydata : datas) {
								xydata = xydata.trim();
								String[] xy = xydata.split(" ");
								latLng.add(new LatLng(Double.valueOf(xy[1].trim()), Double.valueOf(xy[0].trim())));
							}
							ui.access(() -> {
								Polygon polygon = new Polygon(latLng);
								polygon.onClick(e -> leafletMap.flyToBounds(polygon.getBounds()));
								polygon.bindPopup(info);
								polygon.setFillColor("orange");
								polygon.setColor("yellow");
								polygon.addTo(layer);
								polygon.setAttribution("Tight Oil Shale Play");
								notificatiion.setImage("success");
								notificatiion.setText("Querying Deltalake Done");
								notificatiion.setDuration(20000);
								notificatiion.close();
							});
						}
					}

				});
				layer.addTo(leafletMap);

			}
			sets.replace("Tight Oil Shale Play", tieghtoilshaleplayCheckBox.getValue());
			updateSetting();
		});

	}

	public class PB_FlexLayout extends FlexLayout implements Focusable {

		public PB_FlexLayout() {
			this.setTabIndex(0);
		}
	}

	private void updateSetting() {

		gisviewSettings.setSets(sets);

		projectSettings.getViews().setGisviewSettings(gisviewSettings);
		View_Update.setProjectsettingInfo(projectSettings);
		// VaadinService.getCurrentRequest().getWrappedSession().setAttribute("project_settings",
		// projectSettings);
	}

}

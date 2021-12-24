package com.petrabytes.views.gis;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;
//import org.geojson.Polygon;

import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.MainLayout;
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
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "pipelinegis", layout = MainLayout.class)
//@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Pipeline GIS")
public class PipelineGIS extends HorizontalLayout {

	private UI ui = UI.getCurrent();
	//Checkbox biodieselCheckBox = new Checkbox("Bio Diesel");
	//Checkbox bordercrossing_electricCheckBox = new Checkbox("Border Crossing Electric");
	//Checkbox bcliquidCheckBox = new Checkbox("Border Crossing Liquids");
	//Checkbox ethanolplantsCheckBox = new Checkbox("Ethanol Plants");
	//Checkbox coalminesCheckBox = new Checkbox("Coal Mines");
	Checkbox crudeOilRailTCheckBox = new Checkbox("Crude Oil Rail Terminals");
	//Checkbox productlinesCheckBox = new Checkbox("Product Lines");
	//Checkbox naturalgaslinesCheckBox = new Checkbox("Natural Gas Lines");
	Checkbox hglinesCheckBox = new Checkbox("HG Lines");
	Checkbox naturalgasmarkethubsCheckBox = new Checkbox("Natural Gas Trading Hubs");
	//Checkbox wellschematicCheckBox = new Checkbox("Well Schematic");
	//Checkbox tieghtoilshaleplayCheckBox = new Checkbox("Tight Oil Shale Play");
	//Checkbox naturalgasstorageCheckBox = new Checkbox("Natural Gas Storage");
	Checkbox ethylene_crackersCheckBox = new Checkbox("Ethylene Crackers");
	//Checkbox geothermalCheckBox = new Checkbox("Geothermal");
	Checkbox lngimpexpterminalsCheckBox = new Checkbox("LNG Terminals");
	Checkbox crudeoilpipelineCheckBox = new Checkbox("Crude Oil Pipelines");
	//Checkbox shaleplayshxCheckBox = new Checkbox("Shale Play Shx");
	Checkbox nginterintrastatelineCheckBox = new Checkbox("Natural Gas Pipelines");
	Checkbox naturalgasprocessingplantsCheckBox = new Checkbox("Natural Gas Processing Plants");
	Checkbox naturalgasundergroundstorageCheckBox = new Checkbox("Natural Gas Underground Storage");
	Checkbox petroleumproductpipelineCheckbox = new Checkbox("Petroleum Product Pipelines");
	Checkbox petroleumproductterminalsCheckbox = new Checkbox("Petroleum Product Terminals");
	Checkbox petroleumrefineriesCheckbox = new Checkbox("Petroleum Refineries");
	Checkbox powerplantsCheckbox = new Checkbox("Powerplants");
	Checkbox naturalGasCompressorCheckbox = new Checkbox("Natural Gas Compressor Stations");


	private FlexLayout mainLayot = new FlexLayout();
	private FlexLayout mapLayot = new FlexLayout();
	private LeafletMap leafletMap = null;
	private String info = "";
	private LayerGroup layer = new LayerGroup();
	
	private VerticalLayout vLayout = new VerticalLayout();
	private PB_FlexLayout mLayout = new PB_FlexLayout();
	private Button button = new Button(VaadinIcon.ANGLE_DOUBLE_LEFT.create());

	/*
	private Icon biodiesel_Icon = new Icon("images/bio_diesel_16.png", 16);
	private Icon bcelectric_Icon = new Icon("images/border_crossing_electric16.png", 16);
	private Icon bcliquid_Icon = new Icon("images/border_crossing_liquids16.png", 16);
	private Icon coalmines_Icon = new Icon("images/coal_mines_16.png", 16);
	private Icon ethanol_Icon = new Icon("images/ethanol_plants16.png", 16);
	private Icon ethylene_Icon = new Icon("images/ethylene_crackers16.png", 16);
	private Icon fault_Icon = new Icon("images/fault_16.png", 16);
	private Icon lng_Icon = new Icon("images/lng_terminal16.png", 16);
	private Icon ng_pipeline_Icon = new Icon("images/natural_gas_pipeline16.png", 16);
	private Icon ng_hub_Icon = new Icon("images/natural_gas_hub16.png", 16);
	private Icon productlines_Icon = new Icon("images/product_lines_16.png", 16);
	private Icon refineries_Icon = new Icon("images/refineries_16.png", 16);
	private Icon shape_Icon = new Icon("images/shape_16.png", 16);
	private Icon shale_Icon = new Icon("images/shape_16.png", 16);
	*/

	public PipelineGIS() {
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
		HorizontalLayout headLayout = new HorizontalLayout(button,label2);
		headLayout.setSpacing(true);
		headLayout.setPadding(false);
		label2.setVisible(false);
		vLayout.add(headLayout, layout);

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
		
		//shapeButtonEvent();
		//border_crossing_electricEvent();
		ethylene_crackersEvent();
		crudeButtonEvent();
		//biodieselButtonEvent();
		//coalButtonEvent();
		//ethanolButtonEvent();
		ngMarketButtonEvent();
		lng_impexp_terminalsEvent();
		//bcliquidButtonEvent();
		crudeOilRailTButtonEvent();
		hgLinesButtonEvent();
		ngInterIntraStateButtonEvent();
		ngUndergroundStorageButtonEvent();
		ngProcessingPlantsButtonEvent();
		petroleumProductLinesButtonEvent();
		powerplantsButtonEvent();
		petroleumRefineriesButtonEvent();
		petroleumProductTerminalsButtonEvent();
		naturalGasCompressorButton();
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

	
		searchFormLayout.add(crudeoilpipelineCheckBox);
		searchFormLayout.add(crudeOilRailTCheckBox);

		//searchFormLayout.add(ethanolplantsCheckBox);
		searchFormLayout.add(ethylene_crackersCheckBox);
		searchFormLayout.add(hglinesCheckBox);
		searchFormLayout.add(lngimpexpterminalsCheckBox);
		searchFormLayout.add(naturalgasmarkethubsCheckBox);
		searchFormLayout.add(nginterintrastatelineCheckBox);
		searchFormLayout.add(naturalgasprocessingplantsCheckBox);
		searchFormLayout.add(naturalgasundergroundstorageCheckBox);
		searchFormLayout.add(petroleumproductpipelineCheckbox);
		searchFormLayout.add(petroleumproductterminalsCheckbox);
		searchFormLayout.add(petroleumrefineriesCheckbox);
		searchFormLayout.add(powerplantsCheckbox);
		searchFormLayout.add(naturalGasCompressorCheckbox);
		//searchFormLayout.add(tieghtoilshaleplayCheckBox);
		editorLayoutDiv.add(searchFormLayout);

		mLayout.add(editorLayoutDiv);
	}



	
	private void removeComponentFromLayer(String attribute)
	{
		//LayerGroup layer = null;
		List<Layer> layList = layer.getLayers();
		for(int i=0; i<layList.size(); i++)
		{
			Layer la = layList.get(i);
			if(la.getAttribution().equals(attribute))
			{
				layer.removeLayer(la);
				i--;
			}
		}
	}
	
	private void petroleumProductLinesButtonEvent()
	{
		//Data Info
		String notification = "Querying Deltalake: Petroleum Pipelines";
		String query = "select * from gis_db_1.petroleumproduct_pipelines_us_202001";
		Boolean fill = false;
		String lineColor = "purple";
		String attribution = "Petroleum Lines";
		
		petroleumproductpipelineCheckbox.addClickListener(event -> {
			if (!petroleumproductpipelineCheckbox.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				lineDataEvent(notification, query, fill, lineColor, attribution);
			}

		});
	}

	private void hgLinesButtonEvent()
	{
		//Data Info
		String notification = "Querying Deltalake: HG Lines";
		String query = "select * from gis_db_1.hgl_pipelines_us_202001";
		Boolean fill = false;
		String lineColor = "blue";
		String attribution = "HG Lines";
				
		hglinesCheckBox.addClickListener(event -> {
			if (!hglinesCheckBox.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				lineDataEvent(notification, query, fill, lineColor, attribution);
			}
		});
	}


	private void crudeOilRailTButtonEvent()
	{
		//Data Info
		String notification = "Querying Deltalake: Crude Oil Rail Terminals";
		String query = "select * from gis_db_1.crudeoil_railterminals_2017_2018";
		String attribution = "Crude Oil Rail Terminals";
		Icon icon = new Icon("images/well_completions16.png", 16);
		
		crudeOilRailTCheckBox.addClickListener(event -> {
			if (!crudeOilRailTCheckBox.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				pointDataEvent(notification, query, attribution, icon);
			}
		});
	}



	private void ethylene_crackersEvent()
	{
		//Data Info
		String notification = "Querying Deltalake: Ethylene Crackers";
		String query = "select * from gis_db_1.ethylene_crackers_201803_v2";
		String attribution = "Ethylene Crackers";
		Icon icon = new Icon("images/ethylene_crackers16.png", 16);
		
		ethylene_crackersCheckBox.addClickListener(event -> {
			if (!ethylene_crackersCheckBox.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				pointDataEvent(notification, query, attribution, icon);
			}
		});
	}


	private void lng_impexp_terminalsEvent()
	{
		//Data Info
		String notification = "Querying Deltalake: LNG Terminals";
		String query = "select * from gis_db_1.lng_impexp_terminals_us_202004";
		String attribution = "LNG Impexp Terminals";
		Icon icon = new Icon("images/lng_terminal16_2.png", 16);
		
		lngimpexpterminalsCheckBox.addClickListener(event -> {
			if (!lngimpexpterminalsCheckBox.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				pointDataEvent(notification, query, attribution, icon);
			}
		});
	}


	private void crudeButtonEvent()
	{
		//Data Info
		String notification = "Querying Deltalake: Crude Oil Pipelines";
		String query = "select * from gis_db_1.crudeoil_pipelines_us_202001";
		Boolean fill = false;
		String lineColor = "black";
		String attribution = "Crude Oil Pipeline";
		
		crudeoilpipelineCheckBox.addClickListener(event -> {
			if (!crudeoilpipelineCheckBox.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				lineDataEvent(notification, query, fill, lineColor, attribution);
			}
		});
	}


	
	private void ngMarketButtonEvent()
	{
		//Data Info
		String notification = "Querying Deltalake: Natural Gas Trading Hubs";
		String query = "select * from gis_db_1.naturalgas_tradinghubs_us_202002";
		String attribution = "Natural Gas Trading Hubs";
		Icon icon = new Icon("images/natural_gas_storage16.png", 16);
		
		naturalgasmarkethubsCheckBox.addClickListener(event -> {
			if (!naturalgasmarkethubsCheckBox.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				pointDataEvent(notification, query, attribution, icon);
			}
		});
	}

	private void ngInterIntraStateButtonEvent()
	{
		//Data Info
		String notification = "Querying Deltalake: Natural Gas Pipelines";
		String query = "select * from gis_db_1.naturalgas_pipelines_us_202001";
		Boolean fill = false;
		String lineColor = "red";
		String attribution = "Natural Gas Pipeline";
		
		nginterintrastatelineCheckBox.addClickListener(event -> {
			if (!nginterintrastatelineCheckBox.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				lineDataEvent(notification, query, fill, lineColor, attribution);
			}
		});
	}
	
	
	private void ngProcessingPlantsButtonEvent()
	{
		//Data Info
		String notification = "Querying Deltalake: Natural Gas Processing Plants";
		String query = "select * from gis_db_1.naturalgas_processingplants_us_2017_v2";
		String attribution = "NG Processing Plants";
		Icon icon = new Icon("images/processing_plant16.png", 16);
		
		naturalgasprocessingplantsCheckBox.addClickListener(event -> {
			if (!naturalgasprocessingplantsCheckBox.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				pointDataEvent(notification, query, attribution, icon);
			}
		});
	}
	
	
	private void ngUndergroundStorageButtonEvent()
	{
		//Data Info
		String notification = "Querying Deltalake: Natural Gas Underground Storage";
		String query = "select * from gis_db_1.naturalgas_undergroundstorage_us_201812";
		String attribution = "NG Underground Storage";
		Icon icon = new Icon("images/underground_gas16.png", 16);
		
		naturalgasundergroundstorageCheckBox.addClickListener(event -> {
			if (!naturalgasundergroundstorageCheckBox.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				pointDataEvent(notification, query, attribution, icon);
			}
		});
	}
		
	
	private void powerplantsButtonEvent()
	{
		//Data Info
		String notification = "Querying Deltalake: Powerplants";
		String query = "select * from gis_db_1.powerplants_us_202004";
		String attribution = "Powerplants";
		Icon icon = new Icon("images/power_plant16.png", 16);
		
		powerplantsCheckbox.addClickListener(event -> {
			if (!powerplantsCheckbox.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				pointDataEvent(notification, query, attribution, icon);
			}
		});
	}
	
	private void petroleumRefineriesButtonEvent()
	{
		//Data Info
		String notification = "Querying Deltalake: Petroleum Refineries";
		String query = "select * from gis_db_1.petroleum_refineries_us_2020";
		String attribution = "Petroleum Refineries";
		Icon icon = new Icon("images/refineries16.png", 16);
		
		petroleumrefineriesCheckbox.addClickListener(event -> {
			if (!petroleumrefineriesCheckbox.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				pointDataEvent(notification, query, attribution, icon);
			}
		});
	}
	
	
	private void petroleumProductTerminalsButtonEvent()
	{
		//Data Info
		String notification = "Querying Deltalake: Petroleum Product Terminals";
		String query = "select * from gis_db_1.petroleumproduct_terminals_us_202001";
		String attribution = "Petroleum Product Terminals";
		Icon icon = new Icon("images/refineries_16.png", 16);
		
		petroleumproductterminalsCheckbox.addClickListener(event -> {
			if (!petroleumproductterminalsCheckbox.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				pointDataEvent(notification, query, attribution, icon);
				
			}
		});
	}
	
	private void naturalGasCompressorButton()
	{
		//Point Info
		String notification = "Querying Deltalake: Natural Gas Compressor Stations";
		String query = "select * from gis_db_1.natural_gas_compressor_stations";
		String attribution = "NG Compressor Stations";
		Icon icon = new Icon("images/biodiesel.png", 16);
				
		naturalGasCompressorCheckbox.addClickListener(event -> {
			if (!naturalGasCompressorCheckbox.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				pointDataEvent(notification, query, attribution, icon);
			}
		});
	}
	
	
	private void shapeDataEvent(String notification, String attribution, String query, String outlineColor, String fillColor)
	{
		ExecutorService executor = Executors.newCachedThreadPool();
		PB_Progress_Notification notificatiion = new PB_Progress_Notification();
		UI ui = getUI().get();
		executor.submit((Runnable) () -> {
			ui.access(() -> {
				notificatiion.setImage("info");
				notificatiion.setText(notification);
				notificatiion.open();
				removeComponentFromLayer(attribution);
			});
			//String query = "select * from gis_db.tight_shale_plays";
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
						polygon.setFillColor(fillColor);
						polygon.setColor(outlineColor);
						polygon.addTo(layer);
						polygon.setAttribution(attribution);
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
	
	
	private void lineDataEvent(String notification, String query, Boolean fill, String color, String attribution)
	{
		//Line Data
		ExecutorService executor = Executors.newCachedThreadPool();
		PB_Progress_Notification notificatiion = new PB_Progress_Notification();
		UI ui = getUI().get();
		executor.submit((Runnable) () -> {
			ui.access(() -> {
				notificatiion.setImage("info");
				notificatiion.setText(notification);
				notificatiion.open();
				removeComponentFromLayer(attribution);
			});
			//String query = "select * from gis_db_1.petroleumproduct_pipelines_us_202001";
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
						polyline.setFill(fill);
						polyline.setColor(color);
						polyline.bindPopup(info);
						polyline.addTo(layer);
						polyline.setAttribution(attribution);
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
	
	
	private void pointDataEvent(String notification, String query, String attribution, Icon icon)
	{
		//Point Data
		ExecutorService executor = Executors.newCachedThreadPool();
		PB_Progress_Notification notificatiion = new PB_Progress_Notification();
		UI ui = getUI().get();
		executor.submit((Runnable) () -> {
			ui.access(() -> {
				notificatiion.setImage("info");
				notificatiion.setText(notification);
				notificatiion.open();
				removeComponentFromLayer(attribution);
			});
			//String query = "select * from gis_db_1.petroleumproduct_terminals_us_202001";
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
							marker.setIcon(icon);
							marker.addTo(layer);
							marker.setAttribution(attribution);
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
	
	
	public class PB_FlexLayout extends FlexLayout implements Focusable {

		public PB_FlexLayout() {
			this.setTabIndex(0);
		}
	}
	
}

package com.petrabytes.views.gis;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.vaadin.addon.leaflet4vaadin.layer.vectors.MultiPolygon;
import com.vaadin.addon.leaflet4vaadin.layer.vectors.MultiPolygon.MultiPolygonStructure;
import com.vaadin.addon.leaflet4vaadin.layer.vectors.MultiPolyline;
import com.vaadin.addon.leaflet4vaadin.layer.vectors.Polygon;
import com.vaadin.addon.leaflet4vaadin.layer.vectors.Polyline;
import com.vaadin.addon.leaflet4vaadin.layer.vectors.structure.LatLngArray;
import com.vaadin.addon.leaflet4vaadin.layer.vectors.structure.MultiLatLngArray;
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

@Route(value = "testgis", layout = MainLayout.class)
//@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Test GIS")
public class TestGIS extends HorizontalLayout {

	private UI ui = UI.getCurrent();
	
	Checkbox air = new Checkbox("air");
	
	Checkbox air1 = new Checkbox("air1");
	Checkbox ceml1 = new Checkbox("ceml1");
	Checkbox cemp1 = new Checkbox("cemp1");
	Checkbox cit1 = new Checkbox("cit1");
	Checkbox ctyg1 = new Checkbox("ctyg1");
	Checkbox ctyk1 = new Checkbox("ctyk1");
	Checkbox govl1 = new Checkbox("govl1");
	Checkbox rail1 = new Checkbox("rail1");
	Checkbox road1 = new Checkbox("road1");
	Checkbox subd1 = new Checkbox("subd1");
	Checkbox subdabpt1 = new Checkbox("subdabpt1");
	Checkbox watra1 = new Checkbox("watra1");
	Checkbox watrl1 = new Checkbox("watrl1");
	
	


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

	public TestGIS() {
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
		//biodieselButtonEvent();
		//coalButtonEvent();
		//ethanolButtonEvent();
		//bcliquidButtonEvent();
		
		//Commented methods need to be fixed (probably has to do with mismatch data types)
		
		airButtonEvent();
		
		
		air1ButtonEvent();
		ceml1ButtonEvent();
		cemp1ButtonEvent();
		cit1ButtonEvent();
		ctyg1ButtonEvent();
		ctyk1ButtonEvent();
		govl1ButtonEvent();
		rail1ButtonEvent();
		road1ButtonEvent();
		subd1ButtonEvent();
		subdabpt1ButtonEvent();
		watra1ButtonEvent();
		watrl1ButtonEvent();
	}

	
	
//	public GISView() {
//		// TODO Auto-generated constructor stub
//		SplitLayout splitLayout = new SplitLayout();
//		splitLayout.setSizeFull();
//		createEditorLayout(splitLayout);
//		createMapLayout(splitLayout);
//
//		mapRender();
//
//		add(splitLayout);
//		this.setHeightFull();
//		shapeButtonEvent();
//		border_crossing_electricEvent();
//		ethylene_crackersEvent();
//		crudeButtonEvent();
//		biodieselButtonEvent();
//		coalButtonEvent();
//		ethanolButtonEvent();
//		ngMarketButtonEvent();
//		lng_impexp_terminalsEvent();
//		bcliquidButtonEvent();
//		crudeOilRailTButtonEvent();
//		hgLinesButtonEvent();
//	}

//	private void mapRender() {
//		// TODO Auto-generated method stub
//		MapOptions options = new DefaultMapOptions();
//		options.setCenter(new LatLng(29.7154233, -95.505180));
//		options.setZoom(3);
//		leafletMap = new LeafletMap(options);
//		leafletMap.addClassName("gis-map");
//		leafletMap.setBaseUrl("https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png");
//		mapLayot.add(leafletMap);
//
//	}

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

		//searchFormLayout.add(biodieselCheckBox);
		//searchFormLayout.add(bordercrossing_electricCheckBox);
		//searchFormLayout.add(bcliquidCheckBox);
		//searchFormLayout.add(coalminesCheckBox);
		
		searchFormLayout.add(air);
		
		searchFormLayout.add(air1);
		searchFormLayout.add(ceml1);
		searchFormLayout.add(cemp1);
		searchFormLayout.add(cit1);
		searchFormLayout.add(ctyg1);
		searchFormLayout.add(ctyk1);
		searchFormLayout.add(govl1);
		searchFormLayout.add(rail1);
		searchFormLayout.add(road1);
		searchFormLayout.add(subd1);
		searchFormLayout.add(subdabpt1);
		searchFormLayout.add(watra1);
		searchFormLayout.add(watrl1);

		//searchFormLayout.add(ethanolplantsCheckBox);
		//searchFormLayout.add(tieghtoilshaleplayCheckBox);
		editorLayoutDiv.add(searchFormLayout);

		mLayout.add(editorLayoutDiv);
	}

//	private void createMapLayout(SplitLayout splitLayout) {
//		// TODO Auto-generated method stub
//		Div wrapper = new Div();
//		wrapper.setId("grid-wrapper");
//		wrapper.setWidthFull();
//		splitLayout.addToSecondary(wrapper);
//		mapLayot.setSizeFull();
//		wrapper.add(mapLayot);
//
//	}

//	private void createEditorLayout(SplitLayout splitLayout) {
//		// TODO Auto-generated method stub
//		Div editorLayoutDiv = new Div();
//		editorLayoutDiv.setHeightFull();
//		editorLayoutDiv.setClassName("flex flex-col");
//		editorLayoutDiv.setWidth("400px");
//
//		FormLayout searchFormLayout = new FormLayout();
//
//
//		searchFormLayout.add(biodieselCheckBox);
//		searchFormLayout.add(bordercrossing_electricCheckBox);
//		searchFormLayout.add(bcliquidCheckBox);
//		searchFormLayout.add(coalminesCheckBox);
//		searchFormLayout.add(crudeoilpipelineCheckBox);
//		searchFormLayout.add(air);
//
//		searchFormLayout.add(ethanolplantsCheckBox);
//		searchFormLayout.add(ethylene_crackersCheckBox);
//
//		searchFormLayout.add(hglinesCheckBox);
//		searchFormLayout.add(lngimpexpterminalsCheckBox);
//
//		searchFormLayout.add(naturalgasmarkethubsCheckBox);
//
//		searchFormLayout.add(tieghtoilshaleplayCheckBox);
//
//		editorLayoutDiv.add(searchFormLayout);
//
//		splitLayout.addToPrimary(editorLayoutDiv);
//
//	}

	
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

	private void airButtonEvent()
	{
		//Line Data
		String notification = "Querying Deltalake for Air Information! Please Wait!";
		String query = "select top 4000 * from gis_db_2.air";
		String attribution = "air";
		String color = "black";
		Icon icon = null;
		
		air.addClickListener(event -> {
			if (!air.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				parser(query, attribution, notification, color, icon);
			}
		});
	}
	
	private void air1ButtonEvent()
	{
		//Line Data
		String notification = "Querying Deltalake for air Information! Please Wait!";
		String query = "select * from gis_db_2.air001l";
		String attribution = "air1";
		String color = "black";
		Icon icon = null;
		
		air1.addClickListener(event -> {
			if (!air1.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				parser(query, attribution, notification, color, icon);
			}
		});
	}
	
	
	private void ceml1ButtonEvent()
	{
		//Line Data
		String notification = "Querying Deltalake for ceml Information! Please Wait!";
		String query = "select * from gis_db_2.cem001l";
		String attribution = "ceml1";
		String color = "black";
		Icon icon = null;
		
		
		ceml1.addClickListener(event -> {
			if (!ceml1.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				parser(query, attribution, notification, color, icon);
			}
		});
	}
	
	private void cemp1ButtonEvent()
	{
		//Point Data
		String notification = "Querying Deltalake for cemp Information! Please Wait!";
		String query = "select * from gis_db_2.cem001p";
		String attribution = "cemp1";
		String color = null;
		Icon icon = new Icon("images/lng_terminal16_2.png", 16);;
		
		
		cemp1.addClickListener(event -> {
			if (!cemp1.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				parser(query, attribution, notification, color, icon);
			}
		});
	}
	
	
	private void cit1ButtonEvent()
	{
		//Multipolygon Data
		String notification = "Querying Deltalake for cit Information! Please Wait!";
		String query = "select * from gis_db_2.cit001p";
		String attribution = "cit1";
		String color = "teal";
		Icon icon = null;
		
		
		cit1.addClickListener(event -> {
			if (!cit1.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				parser(query, attribution, notification, color, icon);
			}
		});
	}
	
	
	private void ctyg1ButtonEvent()
	{
		//Data Info
		String notification = "Querying Deltalake for ctyg Information! Please Wait!";
		String query = "select * from gis_db_2.cty001g";
		String attribution = "ctyg1";
		String color = "teal";
		Icon icon = null;
		
		
		ctyg1.addClickListener(event -> {
			if (!ctyg1.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				parser(query, attribution, notification, color, icon);
			}
		});
	}
	
	
	private void ctyk1ButtonEvent()
	{
		//Data Info
		String notification = "Querying Deltalake for ctyk Information! Please Wait!";
		String query = "select * from gis_db_2.cty001k";
		String attribution = "ctyk1";
		String color = "pink";
		Icon icon = null;
		
		
		ctyk1.addClickListener(event -> {
			if (!ctyk1.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				parser(query, attribution, notification, color, icon);
			}
		});
	}
	
	
	
	private void govl1ButtonEvent()
	{
		//Line Data
		String notification = "Querying Deltalake for govl Information! Please Wait!";
		String query = "select * from gis_db_2.gov001l";
		String attribution = "govl1";
		String color = "red";
		Icon icon = null;
		
		
		govl1.addClickListener(event -> {
			if (!govl1.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				parser(query, attribution, notification, color, icon);
			}
		});
	}
	
	
	
	private void rail1ButtonEvent()
	{
		//Line Data
		String notification = "Querying Deltalake for rail Information! Please Wait!";
		String query = "select * from gis_db_2.rail001l";
		String attribution = "rail1";
		String color = "blue";
		Icon icon = null;
		
		rail1.addClickListener(event -> {
			if (!rail1.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				parser(query, attribution, notification, color, icon);
			}
		});
	}
	
	
	
	private void road1ButtonEvent()
	{
		//Line Data
		String notification = "Querying Deltalake for road Information! Please Wait!";
		String query = "select * from gis_db_2.road001l";
		String attribution = "road1";
		String color = "purple";
		Icon icon = null;
		
		
		road1.addClickListener(event -> {
			if (!road1.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				parser(query, attribution, notification, color, icon);
			}
		});
	}
	
	
	
	
	private void subd1ButtonEvent()
	{
		//Line Data
		String notification = "Querying Deltalake for subd Information! Please Wait!";
		String query = "select * from gis_db_2.subd001l";
		String attribution = "subd1";
		String color = "green";
		Icon icon = null;
		
		subd1.addClickListener(event -> {
			if (!subd1.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				parser(query, attribution, notification, color, icon);
			}
		});
	}
	
	
	
	private void subdabpt1ButtonEvent()
	{
		//Point Data
		String notification = "Querying Deltalake for subdabpt Information! Please Wait!";
		String query = "select * from gis_db_2.subd001labpt";
		String attribution = "subdabpt1";
		String color = null;
		Icon icon = new Icon("images/lng_terminal16_2.png", 16);
		
		
		subdabpt1.addClickListener(event -> {
			if (!subdabpt1.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				parser(query, attribution, notification, color, icon);
			}
		});
	}
	
	
	
	
	private void watra1ButtonEvent()
	{
		//Polygon Data
		String notification = "Querying Deltalake for watra Information! Please Wait!";
		String query = "select * from gis_db_2.watr001a";
		String attribution = "watra1";
		String color = "orange";
		Icon icon = null;
		
		
		watra1.addClickListener(event -> {
			if (!watra1.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				parser(query, attribution, notification, color, icon);
			}
		});
	}
	
	
	
	private void watrl1ButtonEvent()
	{
		//Line Data
		String notification = "Querying Deltalake for watrl Information! Please Wait!";
		String query = "select * from gis_db_2.watr001l";
		String attribution = "watrl1";
		String color = "brown";
		Icon icon = null;
		
		
		watrl1.addClickListener(event -> {
			if (!watrl1.getValue()) {
				removeComponentFromLayer(attribution);
			} else {
				parser(query, attribution, notification, color, icon);
			}
		});
	}
	
	

	/*
	private void polygonDataEvent(String notification, String attribution, String query, String outlineColor, String fillColor)
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
	
	private void multilineDataEvent(String notification, String query, Boolean fill, String color, String attribution)
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
				//wktStg = wktStg.replace(")", "");
				String type = wktStg.substring(0, wktStg.indexOf("(")).trim();
				//System.out.println(wktStg);
				String data = wktStg.substring(wktStg.indexOf("((") + 1, wktStg.length());
				data = data.replace("(", "");
				//System.out.println(type);
				//System.out.println(data);
				//System.out.println("test");
				
				//System.out.println(type.equalsIgnoreCase("MULTILINESTRING"));
				
				if(type.equalsIgnoreCase("multilinestring"))
				{
					//System.out.println("test2");
					
					String delimiter = "\\), ";
					String[] polydata = data.split(delimiter);
					
					MultiLatLngArray multiLatLngArr = new MultiLatLngArray();
					
					for(int j=0; j<polydata.length; j++)
					{
						polydata[j] = polydata[j].replace(")", "");
						
						String[] datas = polydata[j].split(",");
						List<LatLng> latLng = new ArrayList<>();
						for (String xydata : datas) {
							xydata = xydata.trim();
							String[] xy = xydata.split(" ");
							latLng.add(new LatLng(Double.valueOf(xy[1].trim()), Double.valueOf(xy[0].trim())));
						}
						
						multiLatLngArr.add(new LatLngArray(latLng));
					}
					
					ui.access(() -> {
						MultiPolyline multiPolyline = new MultiPolyline(multiLatLngArr);
						multiPolyline.setFill(fill);
						multiPolyline.setColor(color);
						multiPolyline.bindPopup(info);
						multiPolyline.addTo(layer);
						multiPolyline.setAttribution(attribution);
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
	
	*/
	
	private void parser(String query, String attribution, String notification, String color, Icon icon)
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
			
			//String query = "select * from gis_db_1.petroleumproduct_pipelines_us_202001";
			JSONArray array = new GISQuery().getGISQuery(query);

			for (int i = 0; i < array.length(); i++)
			{
				JSONObject obj = array.getJSONObject(i);
				ArrayList<String> keyList = new ArrayList<String>(obj.keySet());
				info = "";
				
				for (String key : keyList)
				{
					if (!key.equals("wkt"))
						info = info + key + " : " + obj.get(key).toString() + "<br>";
				}
				
				String wktStg = obj.getString("wkt");
				String type = wktStg.substring(0, wktStg.indexOf("(")).trim();
				
				if (type.equalsIgnoreCase("multipolygon"))
				{
					System.out.println(type);
					multiPolygonDataEvent2(wktStg, query, attribution, notification, color);
				}
				else if (type.equalsIgnoreCase("polygon"))
				{
					System.out.println(type);
					polygonDataEvent2(wktStg, query, attribution, notification, color);
				}
				else if (type.equalsIgnoreCase("multilinestring"))
				{
					System.out.println(type);
					multiLineDataEvent2(wktStg, query, attribution, notification, color);
				}
				else if (type.equalsIgnoreCase("linestring"))
				{
					System.out.println(type);
					lineDataEvent2(wktStg, query, attribution, notification, color);
				}
				else if (type.equalsIgnoreCase("multipoint"))
				{
					//System.out.println(type);
					//The multipoint data seems to follow the same format as the point data, so i will just use that for now
					pointDataEvent2(wktStg, query, attribution, notification, icon);
				}
				else if (type.equalsIgnoreCase("point"))
				{
					//System.out.println(type);
					pointDataEvent2(wktStg, query, attribution, notification, icon);
				}
				
				
			}
			
			ui.access(() -> {
				notificatiion.setImage("success");
				notificatiion.setText("Querying Deltalake Done");
				notificatiion.setDuration(20000);
				notificatiion.close();
			});
		});
		
		layer.addTo(leafletMap);
	}
	
	private void multiPolygonDataEvent2(String wktStg, String query, String attribution, String notification, String color)
	{
		String data = wktStg.substring(wktStg.indexOf("(") + 3, wktStg.length()-3);
		
		String delimiter = "\\)\\), ";
		String[] polydata = data.split(delimiter);
		//System.out.println(Arrays.toString(polydata));
		
		MultiLatLngArray multiLatLngArr = new MultiLatLngArray();
		MultiPolygonStructure multiPolygonStruc = new MultiPolygonStructure();
		
		for(int j=0; j<polydata.length; j++)
		{
			
			polydata[j] = polydata[j].replace(")", "");
			polydata[j] = polydata[j].replace("(", "");
			
			String[] datas = polydata[j].split(",");
			List<LatLng> latLng = new ArrayList<>();
			for (String xydata : datas)
			{
				xydata = xydata.trim();
				//System.out.println(xydata);
				String[] xy = xydata.split(" ");
				latLng.add(new LatLng(Double.valueOf(xy[1].trim()), Double.valueOf(xy[0].trim())));
			}
			multiLatLngArr.add(new LatLngArray(latLng));
		}
		
		ui.access(() -> {
			multiPolygonStruc.add(multiLatLngArr);
			MultiPolygon polygon = new MultiPolygon(multiPolygonStruc);
			polygon.onClick(e -> leafletMap.flyToBounds(polygon.getBounds()));
			polygon.bindPopup(info);
			polygon.setFillColor(color);
			polygon.setColor(color);
			polygon.addTo(layer);
			polygon.setAttribution(attribution);
		});
		
	}
	
	private void polygonDataEvent2(String wktStg, String query, String attribution, String notification, String color)
	{
		String data = wktStg.substring(wktStg.indexOf("(") + 2, wktStg.length()-2);
		String[] latLngStr = data.split(",");
		//System.out.println(data);
		List<LatLng> latLng = new ArrayList<>();
		
		for (String xydata : latLngStr)
		{
			xydata = xydata.trim();
			String[] xy = xydata.split(" ");
			latLng.add(new LatLng(Double.valueOf(xy[1].trim()), Double.valueOf(xy[0].trim())));
		}
		
		ui.access(() -> {
			Polygon polygon = new Polygon(latLng);
			polygon.onClick(e -> leafletMap.flyToBounds(polygon.getBounds()));
			polygon.bindPopup(info);
			polygon.setFillColor(color);
			polygon.setColor(color);
			polygon.addTo(layer);
			polygon.setAttribution(attribution);
		});
	}
	
	private void multiLineDataEvent2(String wktStg, String query, String attribution, String notification, String color)
	{
		String data = wktStg.substring(wktStg.indexOf("(") + 2, wktStg.length()-2);
		//System.out.println(data);
		String delimiter = "\\), ";
		String[] polydata = data.split(delimiter);
		
		
		MultiLatLngArray multiLatLngArr = new MultiLatLngArray();
		
		for(int j=0; j<polydata.length; j++)
		{
			
			polydata[j] = polydata[j].replace(")", "");
			polydata[j] = polydata[j].replace("(", "");
			
			String[] datas = polydata[j].split(",");
			List<LatLng> latLng = new ArrayList<>();
			for (String xydata : datas)
			{
				xydata = xydata.trim();
				System.out.println(xydata);
				String[] xy = xydata.split(" ");
				latLng.add(new LatLng(Double.valueOf(xy[1].trim()), Double.valueOf(xy[0].trim())));
			}
			multiLatLngArr.add(new LatLngArray(latLng));
		}
		
		ui.access(() -> {
			MultiPolyline multiPolyline = new MultiPolyline(multiLatLngArr);
			multiPolyline.setFill(false);
			multiPolyline.setColor(color);
			multiPolyline.bindPopup(info);
			multiPolyline.addTo(layer);
			multiPolyline.setAttribution(attribution);
		});
	}
	
	private void lineDataEvent2(String wktStg, String query, String attribution, String notification, String color)
	{
		String data = wktStg.substring(wktStg.indexOf("(") + 1, wktStg.length()-1);
		//System.out.println(data);
		String[] latLngStr = data.split(",");
		
		List<LatLng> latLng = new ArrayList<>();
		
		for (String xydata : latLngStr)
		{
			xydata = xydata.trim();
			System.out.println(xydata);
			String[] xy = xydata.split(" ");
			latLng.add(new LatLng(Double.valueOf(xy[1].trim()), Double.valueOf(xy[0].trim())));
		
			ui.access(() -> {
				Polyline polyline = new Polyline(latLng);
				polyline.setFill(false);
				polyline.setColor(color);
				polyline.onClick(e -> leafletMap.flyToBounds(polyline.getBounds()));
				polyline.bindPopup(info);
				polyline.setAttribution(attribution);
				polyline.addTo(layer);
			});
		}
	}
	
	private void multiPointDataEvent2()
	{
		/* TODO */
	}
	
	private void pointDataEvent2(String wktStg, String query, String attribution, String notification, Icon icon)
	{
		String data = wktStg.substring(wktStg.indexOf("(") + 1, wktStg.length()-1);
		String[] latLngStr = data.split(",");
		
		for (String xydata : latLngStr)
		{
			xydata = xydata.trim();
			String[] xy = xydata.split(" ");
			LatLng latlng = new LatLng(Double.valueOf(xy[1].trim()), Double.valueOf(xy[0].trim()));
			ui.access(() -> {
				Marker marker = new Marker(latlng);
				marker.bindPopup(info);
				marker.setIcon(icon);
				marker.addTo(layer);
				marker.setAttribution(attribution);
			});
		}
	}
	
	public class PB_FlexLayout extends FlexLayout implements Focusable {

		public PB_FlexLayout() {
			this.setTabIndex(0);
		}
	}
	
}

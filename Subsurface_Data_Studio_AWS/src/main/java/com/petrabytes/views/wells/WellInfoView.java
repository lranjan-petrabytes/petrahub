package com.petrabytes.views.wells;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.MainLayout;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.basin.BasinViewGridInfo;

import com.petrabytes.views.wellRegistryQuery.WellRegistryQuries;
import com.sristy.common.utils.PB_Joda_Time_Utility;
import com.sristy.timezone.util.TimezoneUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.dialog.Dialog;

public class WellInfoView extends PetrabyteUI_Dialog {

	private VerticalLayout body = new VerticalLayout();
	private SortDirection aSSENDINGDirection = SortDirection.ASCENDING;
	private TextField WellNameTextField;
	private TextField APINumberTextField;
	private TextField CompanyTextField;
	private String location;
	private TextField StateTextField;
	private final UI ui = UI.getCurrent();
	private ComboBox<String> locationComboBox;
	private NumberField GLElevationTextField;
	private ComboBox<String> GlEvationUnitComboBox;
	private NumberField WaterDensityTextField;
	private ComboBox<String> WaterdensityunitComboBox;
	private NumberField WaterDepthTextField;
	private ComboBox<String> WaterdepthunitComboBox;
	private ComboBox<String> TimeZoneComboBox;
	private ComboBox<String> countryComboBox;
	private NumberField airGapTextField;
	private ComboBox<String> AirGapUnitComboBox;
	private TextField CountyTextField;
	
	private NumberField LongitudeTextField;
	private NumberField LatitudeTextField;
	private TextField basin;
	// private Button saveButton;
//	private Blgz_Well_Registry _selectedWell;
	private Boolean _editFlag;
	private Random uniqueID = new Random();

	private Grid<WellListInfo> WellListGrid = null;

	private BasinViewGridInfo basinfo;
	private WellListInfo wellInfo;
    private 	WellListInfo wellinfo = null;
	public WellInfoView(boolean editFlag, Grid<WellListInfo> wellListGrid, BasinViewGridInfo basinfo) {
		// TODO Auto-generated constructor stub
		this.basinfo = basinfo;
		this._editFlag = editFlag;
		this.WellListGrid = wellListGrid;
		wellinfo = View_Update.getWellInfo();
		if (_editFlag == true) {
			this.setImage("icons" + File.separator + "16x" + File.separator + "edit_16.png");
			this.setTitle("Edit Well");

		} else {
			this.setImage("icons" + File.separator + "purple-24x" + File.separator + "add-24x.png");
			this.setTitle("Add Well");

		}
		// setEditUI();
		// setBasinfield();
		setUI();

		saveWellButtonClickAction();
	}

	private void setUI() {
		// TODO Auto-generated method stub

		this.setWidth("830px");
		this.setHeight("575px");
		this.content.add(body);
		this.setButtonName("OK");

		VerticalLayout mainLayout = new VerticalLayout();
		HorizontalLayout HeaderLayout = new HorizontalLayout();

		WellNameTextField = new TextField("Well Name");
		WellNameTextField.setId("bgz_well_WellName");
		WellNameTextField.setWidth("250px");

		countryComboBox = new ComboBox<String>("Country");
		countryComboBox.setId("bgz_well_country");
		countryComboBox.setWidth("250px");

		locationComboBox = new ComboBox<String>("Location");
		locationComboBox.setId("bgz_well_location");
		locationComboBox.setWidth("265px");
		HeaderLayout.add(WellNameTextField, countryComboBox, locationComboBox);

		HorizontalLayout AirGapLayout = new HorizontalLayout();
		APINumberTextField = new TextField("API Number");
		APINumberTextField.setId("bgz_well_APINumber");
		APINumberTextField.setWidth("250px");

		StateTextField = new TextField("State/Province");
		StateTextField.setId("bgz_well_State");
		StateTextField.setWidth("250px");

		airGapTextField = new NumberField("Air Gap");
		airGapTextField.setId("bgz_well_airGap");
		airGapTextField.setWidth("130px");
		AirGapUnitComboBox = new ComboBox<String>(" Unit");
		AirGapUnitComboBox.setWidth("120px");
		AirGapUnitComboBox.setId("bgz_well_AirGapUnit");
		AirGapLayout.add(APINumberTextField, StateTextField, airGapTextField, AirGapUnitComboBox);
		AirGapLayout.expand(airGapTextField);
		AirGapLayout.setAlignItems(Alignment.CENTER);
		AirGapLayout.setMargin(false);
		AirGapLayout.setPadding(false);
		AirGapLayout.getStyle().set("align-items", "baseline");

		CompanyTextField = new TextField("Company");
		CompanyTextField.setId("bgz_well_Company");
		CompanyTextField.setWidth("250px");

		HorizontalLayout WaterLayout = new HorizontalLayout();
		WaterLayout.setPadding(false);

		CountyTextField = new TextField("County/Area");
		CountyTextField.setId("bgz_well_County");
		CountyTextField.setWidth("250px");

		WaterDepthTextField = new NumberField("Water Depth");
		WaterDepthTextField.setId("bgz_well_WaterDepth");
		WaterDepthTextField.setWidth("130px");
		WaterdepthunitComboBox = new ComboBox<String>("Units ");
		WaterdepthunitComboBox.setWidth("120px");
		WaterdepthunitComboBox.setId("bgz_well_WaterdepthunitComboBox");
		WaterLayout.add(CompanyTextField, CountyTextField, WaterDepthTextField, WaterdepthunitComboBox);
		WaterLayout.expand(WaterDepthTextField);
		WaterLayout.setAlignItems(Alignment.CENTER);
		WaterLayout.getStyle().set("align-items", "baseline");

		HorizontalLayout GLElevationLayout = new HorizontalLayout();

		basin = new TextField("Basin/Field");
		basin.setId("basin_id");
		basin.setWidth("250px");
		basin.setReadOnly(true);
		TimeZoneComboBox = new ComboBox<String>("Time Zone");
		TimeZoneComboBox.setId("bgz_well_TimeZone");
		TimeZoneComboBox.setWidth("250px");

		GLElevationTextField = new NumberField("GL Elevation");
		GLElevationTextField.setId("bgz_well_GLElevation");
		GLElevationTextField.setWidth("130px");
		GlEvationUnitComboBox = new ComboBox<String>("Unit ");
		GlEvationUnitComboBox.setWidth("120px");
		GLElevationLayout.add(basin, TimeZoneComboBox, GLElevationTextField, GlEvationUnitComboBox);
		GLElevationLayout.expand(GLElevationTextField);
		GLElevationLayout.setAlignItems(Alignment.CENTER);
		GLElevationLayout.getStyle().set("align-items", "baseline");

		HorizontalLayout DensityLayout = new HorizontalLayout();
		DensityLayout.setPadding(false);

		HorizontalLayout LatitudeLayout = new HorizontalLayout();
		LatitudeTextField = new NumberField("Latitude");
		LatitudeTextField.setId("bgz_well_State");
		LatitudeTextField.setWidth("250px");

		LongitudeTextField = new NumberField("Longitude");
		LongitudeTextField.setId("bgz_well_State");
		LongitudeTextField.setWidth("250px");

		WaterDensityTextField = new NumberField("Water Density");
		WaterDensityTextField.setId("bgz_well_WaterDensity");
		WaterDensityTextField.setWidth("130px");
		WaterdensityunitComboBox = new ComboBox<String>(" Unit");
		WaterdensityunitComboBox.setId("bgz_well_WaterDensity");
		WaterdensityunitComboBox.setWidth("120px");
		DensityLayout.add(LatitudeTextField, LongitudeTextField, WaterDensityTextField, WaterdensityunitComboBox);
		DensityLayout.expand(WaterDensityTextField);
		DensityLayout.setAlignItems(Alignment.CENTER);
		DensityLayout.getStyle().set("align-items", "baseline");

		body.add(HeaderLayout, AirGapLayout, WaterLayout, GLElevationLayout, DensityLayout);
		basin.setValue(basinfo.getBasinName());
		updateLocationComboBox();
		upDateTimeZoneCombox();

		upDateCountryComboBox();
		addAllUnits();
		if (_editFlag) {

			for (WellListInfo row : WellListGrid.getSelectedItems()) {
				// WellNameTextField.setValue(row.getWellName());

				WellNameTextField.setValue(row.getWellName());
				locationComboBox.setValue(row.getWellType());
				APINumberTextField.setValue(row.getApi());
				CompanyTextField.setValue(row.getCompanyName());
				StateTextField.setValue(row.getState());
				GLElevationTextField.setValue(row.getGlElevation());
				GlEvationUnitComboBox.setValue(row.getGlelEvationUnit());
				WaterDensityTextField.setValue(row.getWaterDensity());
				WaterdensityunitComboBox.setValue(row.getWaterdensityunit());
				WaterDepthTextField.setValue(row.getWaterDepth());
				WaterdepthunitComboBox.setValue(row.getWaterdepthunit());
				TimeZoneComboBox.setValue(row.getTimezone());
				countryComboBox.setValue(row.getCountry());
				airGapTextField.setValue(row.getAirGap());
				AirGapUnitComboBox.setValue(row.getAirGapUnit());
				CountyTextField.setValue(row.getCountryarea());
				LatitudeTextField.setValue(row.getLatitude());
				LongitudeTextField.setValue(row.getLongtide());
				basin.setValue(row.getBasin());

			}
		}

		// formBinder();
		// saveWellButtonClickAction();
	}

	private void saveWellButtonClickAction() {
		// TODO Auto-generated method stub

		this.saveButton.addClickListener(event -> {

			if (_editFlag == false) {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				 String addWell = PetrahubNotification_Utilities.getInstance().addingWellInDaltalakeInWellInfoView();

				executor.submit(() -> {

					ui.access(() -> {

						notificatiion.setImage("info");
						notificatiion.setText(addWell);
						notificatiion.open();
					});	
		
				Long wellID = (long) (10000000 + uniqueID.nextInt(90000000));
				String WellName = WellNameTextField.getValue();

				int isExist = new WellRegistryQuries().wellExist_ByName(WellName,basinfo.getBasinName());
				if (isExist == 0) {
					// String location = location;
					String api_number = APINumberTextField.getValue();
					Double air_Gap = airGapTextField.getValue();

					String air_gap_unit = AirGapUnitComboBox.getValue();
					String company = CompanyTextField.getValue();
					Double waterDepth = WaterDepthTextField.getValue();
					String waterDepthUnit = WaterdepthunitComboBox.getValue();

					String country = countryComboBox.getValue();
					Double glElevation = GLElevationTextField.getValue();

					String glElevation_unit = GlEvationUnitComboBox.getValue();
					String countryArea = CountyTextField.getValue();
					Double waterDensity = WaterDensityTextField.getValue();

					String waterDensityUnit = WaterdensityunitComboBox.getValue();
					String stateProvinence = StateTextField.getValue();
					String timezone = TimeZoneComboBox.getValue();
					Double latitude = LatitudeTextField.getValue();
					String location = locationComboBox.getValue();
					Double longitude = LongitudeTextField.getValue();
					if (WellName.equals("") == false) {
					try {

						new WellRegistryQuries().insertDataInWell(wellID, WellName, location, api_number, air_Gap,
								air_gap_unit, company, waterDepth, waterDepthUnit, basinfo.getBasinID(),
								basinfo.getBasinName(), country, glElevation, glElevation_unit, countryArea,
								waterDensity, waterDensityUnit, stateProvinence, timezone, latitude, longitude);
					
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 String createWell = PetrahubNotification_Utilities.getInstance().addingWellInDaltalakeInWellInfoView();
					} else {
						 PB_Progress_Notification notification = new PB_Progress_Notification();
						 String createWell = PetrahubNotification_Utilities.getInstance().createWellInWellInfoView();
				  			notification.setText(createWell);
				  			notification.open();
				  			notification.setDuration(3000);
//						Notification.show("Please create a Well", 5000, Position.BOTTOM_START)
//								.addThemeVariants(NotificationVariant.LUMO_CONTRAST);

					}
		
						ui.access(()->{
						try {
							populateWellGrid();
							notificatiion.close();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						});
				
				} else {
					//Notification.show("Well name already exist.").addThemeVariants(NotificationVariant.LUMO_CONTRAST);
					 PB_Progress_Notification notification = new PB_Progress_Notification();
					 String wellExist = PetrahubNotification_Utilities.getInstance().wellnameAlreadyExistInWellInfoView();

			  			notification.setText(wellExist);
			  			notification.open();
			  			notification.setDuration(3000);
				}
			
				});
			}

			if (_editFlag == true) {
		
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				 String editWell = PetrahubNotification_Utilities.getInstance().editingWellInDeltaLakeInWellInfoView();
				executor.submit(() -> {

					ui.access(() -> {

						notificatiion.setImage("info");
						notificatiion.setText(editWell);
						notificatiion.open();
					});	

			

				String WellName = WellNameTextField.getValue();
				String basinName = basin.getValue();
			//	int isExist = new WellRegistryQuries().wellExist_ByName(WellName,basinName);
              //  if(isExist ==1) {
				if (WellName.equals("") == false) {
					// String location = location;
					// Long wellID = (long) (10000000 + uniqueID.nextInt(90000000));
					String api_number = APINumberTextField.getValue();
					Double air_Gap = airGapTextField.getValue();

					String air_gap_unit = AirGapUnitComboBox.getValue();
					String company = CompanyTextField.getValue();
					Double waterDepth = WaterDepthTextField.getValue();
					String waterDepthUnit = WaterdepthunitComboBox.getValue();

					String country = countryComboBox.getValue();
					Double glElevation = GLElevationTextField.getValue();

					String glElevation_unit = GlEvationUnitComboBox.getValue();
					String countryArea = CountyTextField.getValue();
					Double waterDensity = WaterDensityTextField.getValue();

					String waterDensityUnit = WaterdensityunitComboBox.getValue();
					String stateProvinence = StateTextField.getValue();
					String timezone = TimeZoneComboBox.getValue();
					Double latitude = LatitudeTextField.getValue();
					String location = locationComboBox.getValue();
					Double longitude = LongitudeTextField.getValue();
				
					Long wellID = wellinfo.getWellID();
					try {

						new WellRegistryQuries().updateDataInWell(wellID, WellName, location, api_number, air_Gap,
								air_gap_unit, company, waterDepth, waterDepthUnit, country, glElevation,
								glElevation_unit, countryArea, waterDensity, waterDensityUnit, stateProvinence,
								timezone, latitude, longitude, basinfo.getBasinID());
				
						new WellRegistryQuries().updateWellInWellboreView(wellID, WellName);
				
						
						populateWellGrid();
						ui.access(()->{
							notificatiion.close();
						});
					} catch (SQLException e) {

						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					//Notification.show("Please first insert  the Wellname.").addThemeVariants(NotificationVariant.LUMO_CONTRAST);
					 PB_Progress_Notification notification = new PB_Progress_Notification();
					 String fillWellname = PetrahubNotification_Utilities.getInstance().plsFillWellnameInWellInfoView();
	     	  			notification.setText(fillWellname);
	     	  			notification.open();
	     	  			notification.setDuration(3000);
				}
				
				});
//                } else {
//                	 PB_Progress_Notification notification = new PB_Progress_Notification();
//     	  			
//     	  			notification.setText("Well name already exist.");
//     	  			notification.open();
//     	  			notification.setDuration(3000);
//                }
			}

			this.close();

		});

	}

	private void updateLocationComboBox() {
		// TODO Auto-generated method stub
		String[] locations = { "Onshore", "Offshore" };
		locationComboBox.setItems(locations);
		locationComboBox.setValue(locations[0]);
		location = locationComboBox.getValue();
		if (locationComboBox.getValue().toString().trim().equalsIgnoreCase("Onshore")) {
			GLElevationTextField.setEnabled(true);
			GlEvationUnitComboBox.setEnabled(true);

			WaterDepthTextField.setEnabled(false);
			WaterdepthunitComboBox.setEnabled(false);
		} else {
			WaterDepthTextField.setEnabled(true);
			WaterdepthunitComboBox.setEnabled(true);

			GLElevationTextField.setEnabled(false);
			GlEvationUnitComboBox.setEnabled(false);
		}

		locationComboBox.addValueChangeListener(event -> {
			location = locationComboBox.getValue().toString().trim();
			if (location.equalsIgnoreCase("Onshore")) {
				GLElevationTextField.setEnabled(true);
				GlEvationUnitComboBox.setEnabled(true);

				WaterDepthTextField.setEnabled(false);
				WaterdepthunitComboBox.setEnabled(false);
			} else {

				WaterDepthTextField.setEnabled(true);
				WaterdepthunitComboBox.setEnabled(true);

				GLElevationTextField.setEnabled(false);
				GlEvationUnitComboBox.setEnabled(false);

			}
		});
	}

	private void upDateTimeZoneCombox() {
		// TODO Auto-generated method stub
		String[] timezones = { " +11:00 : Etc/GMT-11", "+11:00 : Etc/GMT-12 " };
		// TimeZone.setValue("+11:00 : Etc/GMT-11");

		List<String> timeZonesList = new PB_Joda_Time_Utility().getAllTimeZones();
		TimeZoneComboBox.setItems(timeZonesList);
		TimeZoneComboBox.setValue(timeZonesList.get(436));
		TimeZoneComboBox.setValue("-06:00 : America/Chicago");

	}

	private void upDateCountryComboBox() {
		Enumeration<String> _getCountries = TimezoneUtil._getCountries();
		List countryList = Collections.list(_getCountries);
		countryList.sort(Comparator.naturalOrder());
		countryComboBox.setItems(countryList);
		countryComboBox.setValue(countryList.get(0).toString());
		countryComboBox.setValue("United_States");

	}

	private void addAllUnits() {
		String[] airgapunits = { "m", "ft" };
		AirGapUnitComboBox.setItems(airgapunits);
		AirGapUnitComboBox.setValue(airgapunits[0]);

		String[] waterdepthunits = { "m", "ft" };
		WaterdepthunitComboBox.setItems(waterdepthunits);
		WaterdepthunitComboBox.setValue(waterdepthunits[0]);

		String[] waterDensityunits = { "kg/m3", "g/cm3", "lbm/galUS" };
		WaterdensityunitComboBox.setItems(waterDensityunits);
		WaterdensityunitComboBox.setValue(waterDensityunits[0]);

		String[] glelevationUnits = { "m", "ft" };
		GlEvationUnitComboBox.setItems(glelevationUnits);
		GlEvationUnitComboBox.setValue(glelevationUnits[0]);
	}

	private void populateWellGrid() throws SQLException {
	

		if (basinfo.getBasinName() != null) {
			ListDataProvider<WellListInfo> poplateDataWell = new WellRegistryQuries().convertToListWell(basinfo.getBasinID());
           poplateDataWell.setSortOrder(WellListInfo::getWellName, aSSENDINGDirection);
			//UI ui = getUI().get();
			ui.access(() -> {
				WellListGrid.setDataProvider(poplateDataWell);
			});
		} else {
			 PB_Progress_Notification notification = new PB_Progress_Notification();
			 String selectBasin = PetrahubNotification_Utilities.getInstance().plsSelectBasinInWellInfoView();
			 notification.setText(selectBasin);
	  		 notification.open();
	  		 notification.setDuration(3000);
//			Notification.show("Please select a Basin", 5000, Position.BOTTOM_START)
//					.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
		}
	}
}
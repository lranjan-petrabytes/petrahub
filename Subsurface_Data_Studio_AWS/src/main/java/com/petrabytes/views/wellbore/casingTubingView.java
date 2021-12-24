package com.petrabytes.views.wellbore;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.witsml.schemas.x1Series.ObjTubular;

import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.wellRegistryQuery.WellboreListInfo;
import com.petrabytes.views.wellRegistryQuery.WellboreQueries;
import com.sristy.common.utils.DecimalFormatManager;
import com.sristy.unitsystem.UOM;
import com.sristy.unitsystem.UOMRegistry;
import com.sristy.unitsystem.UnitCategory;
import com.sristy.witsml.extras.Tubing_Component_Ext_Info;
import com.sristy.witsml.extras.Tubing_Ext_Info;
import com.sristy.xkriaextensions.unitsutility.util.XKria_Unit_Conversion_Utility;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.petrabytes.views.wells.WellListInfo;

public class casingTubingView extends VerticalLayout {

	private ComboBox<String> casingUnitComboBox;
	private ComboBox<String> depthUnitComboBox;
	private ComboBox<String> holeSizeUnitComboBox;
	private Button casingAddButton;
	private Button casingEditButton;
	private Button saveButton;
	private Button clearButton;
	private Button casingDeleteButton;

	private ObjTubular _tubular;
	private Tubing_Ext_Info _tubular_EXT;
	
	private Grid<Blgz_Casing_Info> casingGrid = new Grid<>();

	private UnitCategory lenghtUnitCategory;
	private UnitCategory holeSizeUnitCategory;
	private UnitCategory casingSizeUnitCategory;

	private Grid<Blgz_Tubing_Info> tubingGrid = new Grid<>();
	private Button tubingAddButton;
	private Button tubingEditButton;
	private Button tubingCloseButton;

	private String _currentDepthUnit;
	private String _currentCasingUnit;
	private String _currentHoleSizeUnit;
	
	private String holeMdkey = "holeMdId";
	private String casingTopKey = "casingTopId";
	private String casingBottomKey = "casingBottomId";
	private String holeSizeKey = "holeSizeId";
	private String casingOdKey = "casingOdId";
	private String casingIdkey = "casingId";
	private String tubingOdKey = "tubingOdId";
	private String tubingIdkey = "tubingId";

	private String tubingTopIdkey = "tubingTopId";
	private String tubingBottomIdkey = "tubingBottomId";
	
	
	public casingTubingView() {
		_tubular = ObjTubular.Factory.newInstance();
		_tubular_EXT = new Tubing_Ext_Info();
		_tubular.setName("Tubing");
		setSizeFull();

		setUI();
		setUnitSToComboBox();
		comboboxSelectionAction();
		addCasingButton();
		editCasingButton();
		deleteCasingRowClickAction();
		clearCasingData();

		addTubingButton();
		editTubingButton();
		deleteTubingRowClickAction();
		clearTubingData();
		setCasingDataToUI();
//		setTubingDataToUI();
		
		
		
		
//		casingGirdEvent();
		
		saveCasingAndTubingDataEvent();
	
		
		
		_currentDepthUnit = depthUnitComboBox.getValue().toString().trim();
		_currentCasingUnit = casingUnitComboBox.getValue().toString().trim();
		_currentHoleSizeUnit = holeSizeUnitComboBox.getValue().toString().trim();
	}

	private void setUI() {

		lenghtUnitCategory = UOMRegistry.getUnitCategoryForName("Measured Depth");
		holeSizeUnitCategory = UOMRegistry.getUnitCategoryForName("Hole Size");
		casingSizeUnitCategory = UOMRegistry.getUnitCategoryForName("Casing Size");
		
		HorizontalLayout unitsLayout = new HorizontalLayout();

		Label casingUnitLabel = new Label("Casing Unit");
		casingUnitLabel.setId("bgz_well_casingUnitLabel");
		casingUnitLabel.getElement().getStyle().set("padding-top", "10px");
		casingUnitComboBox = new ComboBox<String>();
		casingUnitComboBox.setId("bgz_well_casingUnit");
		Label holeSizeUnitLabel = new Label("Hole Unit");
		holeSizeUnitLabel.setId("bgz_well_HoleUnitLabel");
		holeSizeUnitLabel.getElement().getStyle().set("padding-top", "10px");

		Label depthUnitLabel = new Label("Depth Unit");
		depthUnitLabel.setId("bgz_well_DepthUnitLabel");
		depthUnitLabel.getElement().getStyle().set("padding-top", "20px");
		holeSizeUnitComboBox = new ComboBox<String>();
		holeSizeUnitComboBox.setId("bgz_well_holeSize");

		depthUnitComboBox = new ComboBox<String>();
		depthUnitComboBox.setId("bgz_well_depthUnit");

		unitsLayout.add(casingUnitLabel, casingUnitComboBox, holeSizeUnitLabel, holeSizeUnitComboBox, depthUnitLabel,
				depthUnitComboBox);

		casingGrid.addColumn(Blgz_Casing_Info::getTubularsCombobox).setHeader("Tubulars").setAutoWidth(true);
		casingGrid.addColumn(Blgz_Casing_Info::getCompletionsTypeCombobox).setHeader("Completion Type")
				.setAutoWidth(true);
		casingGrid.addColumn(Blgz_Casing_Info::getHoleSizeCombobox).setHeader("Hole Size(m)").setAutoWidth(true);
		casingGrid.addColumn(Blgz_Casing_Info::getHoleMDTextField).setHeader("Hole MD(m)").setAutoWidth(true);
		casingGrid.addColumn(Blgz_Casing_Info::getCasingODCombobox).setHeader("Casing OD(mm)").setAutoWidth(true);
		casingGrid.addColumn(Blgz_Casing_Info::getCasingTopMDTextField).setHeader("Casing Top MD(m)")
				.setAutoWidth(true);
		casingGrid.addColumn(Blgz_Casing_Info::getCasingBottomMDTextfield).setHeader("Casing Bottom MD(m)")
				.setAutoWidth(true);
		casingGrid.addColumn(Blgz_Casing_Info::getGradeCombobox).setHeader("Grade").setAutoWidth(true);
		casingGrid.addColumn(Blgz_Casing_Info::getWeightCombobox).setHeader("Weight").setAutoWidth(true);
		casingGrid.addColumn(Blgz_Casing_Info::getCasingIDTextfield).setHeader("Casing ID(mm)").setAutoWidth(true);
		casingGrid.addColumn(Blgz_Casing_Info::getEhdMultiplierTextfield).setHeader("EHD Multiplier")
				.setAutoWidth(true);
		casingGrid.setHeight("200px");

		casingGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);

		HorizontalLayout casingActionLayout = new HorizontalLayout();

		casingAddButton = new Button();
		casingAddButton.setId("bgz_well_casingadd");
		Image casinAddimage = new Image("icons" + File.separator + "16x" + File.separator + "add-16x.png", "add");
		casingAddButton.getElement().setAttribute("title", "Add");
		casingAddButton.setIcon(casinAddimage);

		casingEditButton = new Button();
		casingEditButton.setId("bgz_well_casingEdit");
		Image casingEditimage = new Image("icons" + File.separator + "16x" + File.separator + "edit_16.png","Edit");
		casingEditButton.getElement().setAttribute("title", "Edit ");
		casingEditButton.setIcon(casingEditimage);

		casingDeleteButton = new Button();
		casingDeleteButton.setId("bgz_well_casingDelete");
		Image casingCloseimage = new Image("icons" + File.separator + "16x" + File.separator + "delete16.png", "delete");
		casingDeleteButton.getElement().setAttribute("title", "Delete");
		casingDeleteButton.setIcon(casingCloseimage);

		casingActionLayout.add(casingAddButton, casingEditButton, casingDeleteButton);

		tubingGrid.addColumn(Blgz_Tubing_Info::getTubularsCotubingType).setHeader("Type").setAutoWidth(true);
		tubingGrid.addColumn(Blgz_Tubing_Info::getTubingOd).setHeader("Tubing OD(mm)").setAutoWidth(true);

		tubingGrid.addColumn(Blgz_Tubing_Info::getWeight).setHeader("Weight").setAutoWidth(true);
		tubingGrid.addColumn(Blgz_Tubing_Info::getGrade).setHeader("Grade").setAutoWidth(true);
		tubingGrid.addColumn(Blgz_Tubing_Info::getTubingTopMD).setHeader("Top(m)").setAutoWidth(true);

		tubingGrid.addColumn(Blgz_Tubing_Info::getTubingBottomMD).setHeader("Bottom(m)").setAutoWidth(true);

		tubingGrid.addColumn(Blgz_Tubing_Info::getTubingID).setHeader("Tubing ID(mm)").setAutoWidth(true);

		tubingGrid.addColumn(Blgz_Tubing_Info::getRoughness).setHeader("Roughness").setAutoWidth(true);
		tubingGrid.setHeight("200px");

		tubingGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);

		HorizontalLayout tubingActionLayout = new HorizontalLayout();
		tubingAddButton = new Button();
		tubingAddButton.setId("bgz_well_TubingAddWell");
		Image tubingAddimage = new Image("icons" + File.separator + "16x" + File.separator + "add-16x.png", "add");
		tubingAddButton.setIcon(tubingAddimage);

		tubingEditButton = new Button();
		tubingEditButton.setId("bgz_well_TubingEditWell");
		Image tubingEditimage = new Image("icons" + File.separator + "16x" + File.separator + "edit_16.png","Edit");
		tubingEditButton.setIcon(tubingEditimage);

		tubingCloseButton = new Button();
		tubingCloseButton.setId("bgz_well_TubingCloseWell");
		Image tubingCloseimage = new Image("icons" + File.separator + "16x" + File.separator + "delete16.png", "delete");
		tubingCloseButton.setIcon(tubingCloseimage);

		tubingActionLayout.add(tubingAddButton, tubingEditButton, tubingCloseButton);

		HorizontalLayout mainActionLayout = new HorizontalLayout();

		saveButton = new Button("Save");
		saveButton.addThemeVariants(ButtonVariant.LUMO_SMALL,ButtonVariant.LUMO_PRIMARY);
		saveButton.setId("bgz_well_SaveWell");

		clearButton = new Button("Clear");
		clearButton.setId("bgz_well_ClearWell");

		mainActionLayout.add(saveButton, clearButton);

		add( unitsLayout, casingGrid, casingActionLayout, tubingGrid, tubingActionLayout,
				mainActionLayout);
		setAlignSelf(Alignment.CENTER, casingActionLayout);
		setAlignSelf(Alignment.CENTER, tubingActionLayout);
		setAlignSelf(Alignment.BASELINE, mainActionLayout);

	}
	
	

	private void setUnitSToComboBox() {
		// TODO Auto-generated method stub

		UOM casingSizeUnitUom = casingSizeUnitCategory.getUom();
		String[] casingSizeUnits = UOMRegistry.getPossibleUnitsForCategory(casingSizeUnitCategory);

		casingUnitComboBox.setItems(casingSizeUnits);
		casingUnitComboBox.setValue(casingSizeUnits[1]);

		UOM holeSizeUnitUom = holeSizeUnitCategory.getUom();
		String[] holeSizeUnits = UOMRegistry.getPossibleUnitsForCategory(holeSizeUnitCategory);
		holeSizeUnitComboBox.setItems(holeSizeUnits);

		holeSizeUnitComboBox.setValue(holeSizeUnits[1]);

		UOM lengthUnitUom = lenghtUnitCategory.getUom();
		String[] lengthUnits = UOMRegistry.getPossibleUnitsForCategory(lenghtUnitCategory);

		depthUnitComboBox.setItems(lengthUnits);
		depthUnitComboBox.setValue(lengthUnits[0]);

	}

	private void comboboxSelectionAction() {

		this.holeSizeUnitComboBox.addValueChangeListener(event -> {
			String previousHoleSizeUnit = _currentHoleSizeUnit;
			String previousCasingUnit = _currentCasingUnit;
			System.out.println(holeSizeUnitComboBox.getValue().toString());
			String holeSizeUnit = holeSizeUnitComboBox.getValue().toString();
			// casingUnitComboBox.setId("bgz_well_casingUnit");
			casingUnitComboBox.setValue(holeSizeUnit);
			_currentHoleSizeUnit = holeSizeUnit;
			_currentCasingUnit = casingUnitComboBox.getValue().toString();
			updateDataIntoGridByUnit_Casing(previousHoleSizeUnit, previousCasingUnit);
			updateTubingDataIntoGridByUnit_Casing(previousHoleSizeUnit, previousCasingUnit);

		});

		this.casingUnitComboBox.addValueChangeListener(event -> {
			if (casingUnitComboBox.getValue() != holeSizeUnitComboBox.getValue()) {
				holeSizeUnitComboBox.setValue(casingUnitComboBox.getValue());
			}
			String holeSizeUnit = casingUnitComboBox.getValue().toString();
			holeSizeUnitComboBox.setValue(holeSizeUnit);

		});
		this.depthUnitComboBox.addValueChangeListener(event -> {
			String previousDepthunit = _currentDepthUnit;
			_currentDepthUnit = depthUnitComboBox.getValue().toString();

			casingGrid.getColumnByKey(holeMdkey).setHeader("Hole MD(" + _currentDepthUnit + ")");
			casingGrid.getColumnByKey(casingTopKey).setHeader("Casing Top MD(" + _currentDepthUnit + ")");
			casingGrid.getColumnByKey(casingBottomKey).setHeader("Casing Bottom MD(" + _currentDepthUnit + ")");
			tubingGrid.getColumnByKey(tubingTopIdkey).setHeader("Top(" + _currentDepthUnit + ")");

			tubingGrid.getColumnByKey(tubingBottomIdkey).setHeader("Bottom(" + _currentDepthUnit + ")");

			UOM _lengthUnitUom = lenghtUnitCategory.getUom();
			XKria_Unit_Conversion_Utility utility = XKria_Unit_Conversion_Utility.getInstance();
			ListDataProvider<Blgz_Casing_Info> casingAllDatalist = (ListDataProvider<Blgz_Casing_Info>) casingGrid
					.getDataProvider();
			List<Blgz_Casing_Info> rowList = null;

			if (!casingAllDatalist.getItems().isEmpty()) {

				rowList = (List<Blgz_Casing_Info>) casingAllDatalist.getItems();
			} else {
				rowList = new ArrayList<>();
			}

			List<Blgz_Casing_Info> updatedList = new ArrayList<>();

			for (int i = 0; i < rowList.size(); i++) {
				Blgz_Casing_Info casingInfo = rowList.get(i);
				double holeMD = Double.valueOf(casingInfo.getHoleMDTextField());
				double convertedValue = utility.getConvertedValue(_lengthUnitUom, holeMD, previousDepthunit,
						_currentDepthUnit);
				convertedValue = DecimalFormatManager.getDoubleValueInEnUSLocale(convertedValue, 3);
				casingInfo.setHoleMDTextField(convertedValue);
				double top = Double.valueOf(casingInfo.getCasingTopMDTextField().toString());
				convertedValue = utility.getConvertedValue(_lengthUnitUom, top, previousDepthunit, _currentDepthUnit);
				convertedValue = DecimalFormatManager.getDoubleValueInEnUSLocale(convertedValue, 3);
				casingInfo.setCasingTopMDTextField(String.valueOf(convertedValue));
				double bottom = Double.valueOf(casingInfo.getCasingBottomMDTextfield());
				convertedValue = utility.getConvertedValue(_lengthUnitUom, bottom, previousDepthunit,
						_currentDepthUnit);
				convertedValue = DecimalFormatManager.getDoubleValueInEnUSLocale(convertedValue, 3);
				casingInfo.setCasingBottomMDTextfield(convertedValue);
				updatedList.add(casingInfo);

			}

			casingGrid.setItems(updatedList);
		});

	}

	private void updateDataIntoGridByUnit_Casing(String previousHoleSizeUnit, String previousCasingUnit) {

		casingGrid.getColumnByKey(holeSizeKey).setHeader("Hole size(" + _currentHoleSizeUnit + ")");
		casingGrid.getColumnByKey(casingOdKey).setHeader("Casing OD(" + _currentCasingUnit + ")");
		casingGrid.getColumnByKey(casingIdkey).setHeader("Casing ID(" + _currentCasingUnit + ")");

		UOM lengthUnitUom = lenghtUnitCategory.getUom();
		XKria_Unit_Conversion_Utility utility = XKria_Unit_Conversion_Utility.getInstance();
		ListDataProvider<Blgz_Casing_Info> casingAllDatalist = (ListDataProvider<Blgz_Casing_Info>) casingGrid
				.getDataProvider();
		List<Blgz_Casing_Info> rowList = null;

		if (!casingAllDatalist.getItems().isEmpty()) {

			rowList = (List<Blgz_Casing_Info>) casingAllDatalist.getItems();
		} else {
			rowList = new ArrayList<>();
		}

		List<Blgz_Casing_Info> updatedList = new ArrayList<>();

		for (int i = 0; i < rowList.size(); i++) {
			Blgz_Casing_Info casingInfo = rowList.get(i);

			double holeSize = Double.valueOf(casingInfo.getHoleSizeCombobox().toString());
			double convertedValue = utility.getConvertedValue(lengthUnitUom, holeSize, previousHoleSizeUnit,
					_currentHoleSizeUnit);
			convertedValue = DecimalFormatManager.getDoubleValueInEnUSLocale(convertedValue, 3);
			casingInfo.setHoleSizeCombobox(String.valueOf(convertedValue));
			double casingOD = Double.valueOf(casingInfo.getCasingODCombobox().toString());
			convertedValue = utility.getConvertedValue(lengthUnitUom, casingOD, previousCasingUnit, _currentCasingUnit);
			convertedValue = DecimalFormatManager.getDoubleValueInEnUSLocale(convertedValue, 3);
			casingInfo.setCasingODCombobox(String.valueOf(convertedValue));
			double casingID = Double.valueOf(casingInfo.getCasingIDTextfield());
			convertedValue = utility.getConvertedValue(lengthUnitUom, casingID, previousCasingUnit, _currentCasingUnit);
			convertedValue = DecimalFormatManager.getDoubleValueInEnUSLocale(convertedValue, 3);
			casingInfo.setCasingIDTextfield((convertedValue));
			updatedList.add(casingInfo);
		}
		casingGrid.setItems(updatedList);

	}

	private void updateTubingDataIntoGridByUnit_Casing(String previousHoleSizeUnit, String previousCasingUnit) {

		tubingGrid.getColumnByKey(tubingOdKey).setHeader("Tubing OD(" + _currentCasingUnit + ")");
		tubingGrid.getColumnByKey(tubingIdkey).setHeader("Tubing ID(" + _currentCasingUnit + ")");

		UOM lengthUnitUom = lenghtUnitCategory.getUom();
		XKria_Unit_Conversion_Utility utility = XKria_Unit_Conversion_Utility.getInstance();
		ListDataProvider<Blgz_Tubing_Info> tubingAllDatalist = (ListDataProvider<Blgz_Tubing_Info>) tubingGrid
				.getDataProvider();
		List<Blgz_Tubing_Info> rowList = null;

		if (!tubingAllDatalist.getItems().isEmpty()) {

			rowList = (List<Blgz_Tubing_Info>) tubingAllDatalist.getItems();
		} else {
			rowList = new ArrayList<>();
		}

		List<Blgz_Tubing_Info> updatedList = new ArrayList<>();

		for (int i = 0; i < rowList.size(); i++) {
			Blgz_Tubing_Info tubingInfo = rowList.get(i);

			double todMD = Double.valueOf(tubingInfo.getTubingTopMD());
			double convertedValue = utility.getConvertedValue(lengthUnitUom, todMD, previousHoleSizeUnit,
					_currentHoleSizeUnit);
			convertedValue = DecimalFormatManager.getDoubleValueInEnUSLocale(convertedValue, 3);
			tubingInfo.setTubingTopMD(Double.valueOf(convertedValue));

			double bottomMD = Double.valueOf(tubingInfo.getTubingBottomMD());
			double convertedBottomValue = utility.getConvertedValue(lengthUnitUom, bottomMD, previousHoleSizeUnit,
					_currentHoleSizeUnit);
			convertedBottomValue = DecimalFormatManager.getDoubleValueInEnUSLocale(convertedBottomValue, 3);
			tubingInfo.setTubingBottomMD(Double.valueOf(convertedBottomValue));
			double casingOD = Double.valueOf(tubingInfo.getTubingOd().toString());
			convertedValue = utility.getConvertedValue(lengthUnitUom, casingOD, previousCasingUnit, _currentCasingUnit);
			convertedValue = DecimalFormatManager.getDoubleValueInEnUSLocale(convertedValue, 3);
			tubingInfo.setTubingOd(String.valueOf(convertedValue));
			double casingID = Double.valueOf(tubingInfo.getTubingID());
			convertedValue = utility.getConvertedValue(lengthUnitUom, casingID, previousCasingUnit, _currentCasingUnit);
			convertedValue = DecimalFormatManager.getDoubleValueInEnUSLocale(convertedValue, 3);
			tubingInfo.setTubingID(String.valueOf(convertedValue));
			updatedList.add(tubingInfo);
		}
		tubingGrid.setItems(updatedList);

	}

	/**
	 * add casing row data
	 */
	private void addCasingButton() {
		// TODO Auto-generated method stub
		casingAddButton.addClickListener(event -> {
			updateSelectedDialog(false);
		});
	}

	private void editCasingButton() {
		// TODO Auto-generated method stub
		casingEditButton.addClickListener(event -> {
			if (casingGrid.asSingleSelect().getValue() == null) {
				PB_Progress_Notification notification = new PB_Progress_Notification();
				String selectaRow = PetrahubNotification_Utilities.getInstance().selectArowFromgrid();
								notification.setText(selectaRow);
								notification.open();
								notification.setDuration(3000);
			} else {
				updateSelectedDialog(true);
			}

		});
	}

	/*
	 * Add Tubing
	 */

	private void addTubingButton() {
		tubingAddButton.addClickListener(event -> {
			updateSelectedTubingDialog(false);
		});

	}

	private void editTubingButton() {
		// TODO Auto-generated method stub
		tubingEditButton.addClickListener(event -> {
			if (casingGrid.asSingleSelect().getValue() == null) {
				PB_Progress_Notification notification = new PB_Progress_Notification();
				String selectaRow = PetrahubNotification_Utilities.getInstance().selectArowFromgrid();
								notification.setText(selectaRow);
								notification.open();
								notification.setDuration(3000);
			} else {
				updateSelectedTubingDialog(true);
			}

		});
	}

	private void updateSelectedTubingDialog(boolean editFlag) {

		Blgz_Casing_Info lastRow = null;
		ListDataProvider<Blgz_Casing_Info> dataProvider = (ListDataProvider<Blgz_Casing_Info>) casingGrid
				.getDataProvider();
		List<Blgz_Casing_Info> ItemsList = (List<Blgz_Casing_Info>) dataProvider.getItems();
		if (!ItemsList.isEmpty()) {

			lastRow = ItemsList.get(ItemsList.size() - 1);

			String lastCasingID = (String.valueOf(lastRow.getCasingIDTextfield()));
			String[] units = new String[2];
			if (casingUnitComboBox.getValue().toString().equals(holeSizeUnitComboBox.getValue().toString())) {
				units[0] = casingUnitComboBox.getValue().toString();
			}
			units[1] = depthUnitComboBox.getValue().toString();

			Blgz_Tubing_Window_UI tubingWindow = new Blgz_Tubing_Window_UI(tubingGrid, casingGrid, editFlag, units,
					lastCasingID, _tubular, _tubular_EXT);
			tubingWindow.open();

		} else {

			PB_Progress_Notification notification = new PB_Progress_Notification();
			String selectaRow = PetrahubNotification_Utilities.getInstance().fillCasinInformation();
							notification.setText(selectaRow);
							notification.open();
							notification.setDuration(3000);

		}

		if (ItemsList.size() != 0) {
			tubingAddButton.setEnabled(false);
		}

	}

	private void updateSelectedDialog(boolean editFlag) {
		String[] units = new String[2];
		if (casingUnitComboBox.getValue().toString().equals(holeSizeUnitComboBox.getValue().toString())) {
			units[0] = casingUnitComboBox.getValue().toString();
		}
		units[1] = depthUnitComboBox.getValue().toString();

		Blgz_Casing_Window casingWindow = new Blgz_Casing_Window(casingGrid, editFlag, units);
		casingWindow.open();
	}

	private void saveCasingAndTubingDataEvent() {
		// TODO Auto-generated method stub
		saveButton.addClickListener(event -> {
			ListDataProvider<Blgz_Casing_Info> dataProvider = (ListDataProvider<Blgz_Casing_Info>) casingGrid.getDataProvider();
			List<Blgz_Casing_Info> inputs = (List<Blgz_Casing_Info>) dataProvider.getItems();
			ArrayList<ArrayList<String>> casingdata = new ArrayList<>();
			for (Blgz_Casing_Info userInput : inputs) {
				ArrayList<String> casing = new ArrayList<>();
				casing.add(userInput.getTubularsCombobox());
				casing.add(userInput.getCompletionsTypeCombobox());
				casing.add(userInput.getHoleSizeCombobox());
				casing.add(String.valueOf(userInput.getHoleMDTextField()));
				casing.add(userInput.getCasingODCombobox());
				casing.add(userInput.getCasingTopMDTextField());
				casing.add(String.valueOf(userInput.getCasingBottomMDTextfield()));
				casing.add(userInput.getGradeCombobox());
				casing.add(userInput.getWeightCombobox());
				casing.add(userInput.getCasingId());;
				casing.add(String.valueOf(userInput.getEhdMultiplierTextfield()));
				
				casingdata.add(casing);
			}
			ListDataProvider<Blgz_Tubing_Info> dataProvider1 = (ListDataProvider<Blgz_Tubing_Info>) tubingGrid.getDataProvider();
			List<Blgz_Tubing_Info> inputs1 = (List<Blgz_Tubing_Info>) dataProvider1.getItems();
			ArrayList<ArrayList<String>> tubingdata = new ArrayList<>();
			for (Blgz_Tubing_Info userInput : inputs1) {
				ArrayList<String> tubing = new ArrayList<>();
				tubing.add(userInput.getTubularsCotubingType());
				tubing.add(userInput.getTubingOd());
				tubing.add(userInput.getWeight());
				tubing.add(userInput.getGrade());
				tubing.add(String.valueOf(userInput.getTubingTopMD()));
				tubing.add(String.valueOf(userInput.getTubingBottomMD()));
				tubing.add(userInput.getTubingID());
				tubing.add(userInput.getRoughness());
					
				tubingdata.add(tubing);
			}
			WellboreListInfo wellboreInfo = View_Update.getWellboreInfo();
			Long wellboreID = wellboreInfo.getWellboreID();
			
			CasingPojoClass casingvalue = new CasingPojoClass();
			casingvalue.setData(casingdata);
			TubingPojaClass tubingvalue = new TubingPojaClass();
			tubingvalue.setData(tubingdata);
			
			
			CasingMetaDataInfo casingmeta_data = new CasingMetaDataInfo();
			String[] columns = {"Tubulars","Completion_Type","HoleSize","Hole_MD","Casing_OD","Casing_Top_MD","Casing_Bottom_MD","Grade","Weight","Casing_ID","EHD_Multiplier"};
			String[] units = {"NA","NA","m","m","mm","m","m","NA","NA","mm","NA"};
			casingmeta_data.setColumns(columns);
			casingmeta_data.setUnits(units);

			casingvalue.setMeta_data(casingmeta_data);

			JSONObject CasingObject = new JSONObject(casingvalue);
			
			TubingMetaDataInfo tubingmeta_data= new TubingMetaDataInfo();
			String [] colums1= {"Type","Tubing_OD","Weight","Grade","Top","Bottom","Tubing_ID","Roughness"};
			String [] units1= {"NA","mm","NA","NA","m","m","mm","NA"};
			tubingmeta_data.setColumns(colums1);
			tubingmeta_data.setUnits(units1);
			
			tubingvalue.setMeta_data(tubingmeta_data);
			
			JSONObject TubingObject = new JSONObject(tubingvalue);
			String query = "select count(*) from well_registry_db.casing_tubing where wellbore_id=" + wellboreID +"";
			int ifexist = new WellboreQueries(). wellboreExistByID(query);
			
			if (ifexist == 0) {
			try {
				new WellboreQueries().insertCasingTubingData(wellboreID, CasingObject, TubingObject);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			} else {
				System.out.println("wellbore exist already, plese update the wellbore");
				try {
					new WellboreQueries().updateDataInCasingTubingView(wellboreID, CasingObject, TubingObject);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
	}

	public void setCasingDataToUI() {
		CasingPojoClass casingObject = null;
		WellboreListInfo wellboreInfo = View_Update.getWellboreInfo();
		Long wellboreID = wellboreInfo.getWellboreID();
//		Long wellboreID= (long) 10450864;
		try {
			casingObject = new WellboreQueries().getCasingData(wellboreID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (casingObject!=null) {
			ArrayList<ArrayList<String>> data = casingObject.getData();
			ArrayList<Blgz_Casing_Info> casingDataList = new ArrayList<>();
			for (ArrayList<String> casingData : data) {
				
				Blgz_Casing_Info blgzCasingObject = new Blgz_Casing_Info();
				
				blgzCasingObject.setTubularsCombobox(casingData.get(0));
				blgzCasingObject.setCompletionsTypeCombobox(casingData.get(1));
				blgzCasingObject.setHoleSizeCombobox(casingData.get(2));
				blgzCasingObject.setHoleMDTextField(Double.valueOf(casingData.get(3)));
				blgzCasingObject.setCasingODCombobox(casingData.get(4));
				blgzCasingObject.setCasingTopMDTextField(casingData.get(5));
				blgzCasingObject.setCasingBottomMDTextfield(Double.valueOf(casingData.get(6)));
				blgzCasingObject.setGradeCombobox(casingData.get(7));
				blgzCasingObject.setWeightCombobox(casingData.get(8));
				blgzCasingObject.setCasingId(casingData.get(9));
				blgzCasingObject.setEhdMultiplierTextfield(Integer.parseInt(casingData.get(10)));
				casingDataList.add(blgzCasingObject);
				
				
			}
			
			casingGrid.setItems(casingDataList);
			setTubingDataToUI();
		}
		
		
	}
	

	public void setTubingDataToUI() {
		TubingPojaClass tubingObject = null;
		WellboreListInfo wellboreInfo = View_Update.getWellboreInfo();
		Long wellboreID = wellboreInfo.getWellboreID();
//		Long wellboreID= (long) 10450864;
		try {
			tubingObject = new WellboreQueries().getTubingData(wellboreID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (tubingObject!=null) {
			ArrayList<ArrayList<String>> data = tubingObject.getData();
			ArrayList<Blgz_Tubing_Info> tubingDataList = new ArrayList<>();
			for (ArrayList<String> tubingData : data) {
				
				Blgz_Tubing_Info blgzTubingObject = new Blgz_Tubing_Info();
				blgzTubingObject.setTubularsCotubingType(tubingData.get(0));
				blgzTubingObject.setTubingOd(tubingData.get(1));
				blgzTubingObject.setWeight(tubingData.get(2));
				blgzTubingObject.setGrade(tubingData.get(3));
				blgzTubingObject.setTubingTopMD(Double.valueOf(tubingData.get(4)));
				blgzTubingObject.setTubingBottomMD(Double.valueOf(tubingData.get(5)));
				blgzTubingObject.setTubingID(tubingData.get(6));
				blgzTubingObject.setRoughness(tubingData.get(7));
				tubingDataList.add(blgzTubingObject);
					
				
			}
			
			tubingGrid.setItems(tubingDataList);
		}
		
		
	}

	private void deleteCasingRowClickAction() {
//		// TODO Auto-generated method stub
		casingDeleteButton.addClickListener(event -> {

			Blgz_Casing_Info selectedGridRow = casingGrid.asSingleSelect().getValue();
			if (casingGrid.asSingleSelect().getValue() == null) {
				PB_Progress_Notification notification = new PB_Progress_Notification();
				String selectaRow = PetrahubNotification_Utilities.getInstance().selectArowFromgrid();
								notification.setText(selectaRow);
								notification.open();
								notification.setDuration(3000);
			} else {
				ListDataProvider<Blgz_Casing_Info> dataProvider = (ListDataProvider<Blgz_Casing_Info>) casingGrid
						.getDataProvider();
				dataProvider.getItems().remove(selectedGridRow);
				dataProvider.refreshAll();
				casingGrid.getDataProvider().refreshAll();
				casingGrid.deselect(selectedGridRow);

			}

		});
	}

	private Tubing_Component_Ext_Info getTubularCompExtInfo(Tubing_Ext_Info tubular_EXT, String tcID) {
		Tubing_Component_Ext_Info comp_ext_Info = null;
		List<Tubing_Component_Ext_Info> componentList = tubular_EXT.getComponentList();
		for (int i = 0; i < componentList.size(); i++) {
			String id = componentList.get(i).getId();
			if (id.equalsIgnoreCase(tcID)) {
				comp_ext_Info = componentList.get(i);
				break;
			}
		}
		return comp_ext_Info;
	}

	private void clearCasingData() {
		clearButton.addClickListener(event -> {
			/**
			 * Remove all the columns and add again with updated units
			 */

//			List<Blgz_Casing_Info> casingInfoList = new Blgz_Casing_Provider().getCasingData(selectedWellbore);
//			Blgz_Casing_Info ifno = new Blgz_Casing_Info();
//			ListDataProvider<Blgz_Casing_Info> dataProvider = (ListDataProvider<Blgz_Casing_Info>) casingGrid
//					.getDataProvider();
//			dataProvider.getItems().removeAll(casingInfoList);
//			dataProvider.refreshAll();
//			casingGrid.getDataProvider().refreshAll();
//			casingGrid.setItems();

		});
	}

	private void deleteTubingRowClickAction() {
		// TODO Auto-generated method stub

	}

	private void clearTubingData() {
		// TODO Auto-generated method stub
		tubingCloseButton.addClickListener(event -> {
			/**
			 * Remove all the columns and add again with updated units
			 */

//			List<Blgz_Tubing_Info> tubingInfoList = new Blgz_Tubing_Provider().getCasingData(selectedWellbore);
//			Blgz_Tubing_Info ifno = new Blgz_Tubing_Info();
//			ListDataProvider<Blgz_Tubing_Info> dataProvider = (ListDataProvider<Blgz_Tubing_Info>) tubingGrid.getDataProvider();
//            dataProvider.getItems().removeAll(tubingInfoList);
//            dataProvider.refreshAll();
//            tubingGrid.getDataProvider().refreshAll();
//            tubingGrid.setItems();

		});

	}
	
}

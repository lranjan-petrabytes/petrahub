package com.petrabytes.views.wellbore;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.wellRegistryQuery.WellboreListInfo;
import com.petrabytes.views.wellRegistryQuery.WellboreQueries;
import com.sristy.common.utils.DecimalFormatManager;
import com.sristy.unitsystem.UOM;
import com.sristy.unitsystem.UOMRegistry;
import com.sristy.unitsystem.UnitCategory;
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

public class formationView extends VerticalLayout {

	private UnitCategory lenghtUnitCategory = null;
//	String formationDataset = (String) VaadinService.getCurrentRequest().getWrappedSession()
//			.getAttribute("wellboreinfo_formation");
	private ComboBox<String> formationUnitComboBox;;
	private Button formationAddButton;
	private String _currentDepthUnit;
	private Button formationEditButton;
	private Button formationDeleteButton;
	private Button clearButton;
	private Button closeButton;
	private Button saveButton;
	private String topMDkey = "topMD";
	private String bottomMDKey = "bottomMD";
	private Grid<Blgz_formation_Info> formationGrid = new Grid<>();

	// private Blgz_Wellbore_Registry selectedWellbore;

	public formationView() {

		setSizeFull();
		setUI();
		formationGrid.asSingleSelect();
		addFormationButton();
		editFormationButtonClickAction();
		setUnitSToComboBox();
		deleteFormationRowClickAction();
		saveFormationDataEvent();
		clearButtonAction();
		 setDataToUI();
		_currentDepthUnit = formationUnitComboBox.getValue().toString().trim();

	}

	private void setUI() {
		// TODO Auto-generated method stub
		lenghtUnitCategory = UOMRegistry.getUnitCategoryForName("Measured Depth");
		HorizontalLayout unitsLayout = new HorizontalLayout();
		Label depthUnitLabel = new Label("Depth Unit");
		depthUnitLabel.setId("bgz_well_depthUnitLabel");
		depthUnitLabel.getElement().getStyle().set("padding-top", "10px");

		formationUnitComboBox = new ComboBox<String>();
		formationUnitComboBox.setId("bgz_well_formationUnit");

		unitsLayout.add(depthUnitLabel, formationUnitComboBox);

		formationGrid.addColumn(Blgz_formation_Info::getFormationName).setHeader("Formation Name").setAutoWidth(true);
		formationGrid.addColumn(Blgz_formation_Info::getRockType).setHeader("Rock Type").setAutoWidth(true);
		formationGrid.addColumn(Blgz_formation_Info::getTopMD).setHeader("Top MD(m)").setAutoWidth(true);
		formationGrid.addColumn(Blgz_formation_Info::getBottomMD).setHeader("Bottom MD(m)").setAutoWidth(true);
		formationGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		HorizontalLayout formationLayout = new HorizontalLayout();

		formationAddButton = new Button();
		formationAddButton.setId("bgz_well_formationAdd");
		Image formationAddimage = new Image("icons" + File.separator + "16x" + File.separator + "add-16x.png","add");
		formationAddButton.getElement().setAttribute("title", "Add");
		formationAddButton.setIcon(formationAddimage);

		formationEditButton = new Button();
		formationEditButton.setId("bgz_well_formationEdit");
		Image formationEditimage = new Image("icons" + File.separator + "16x" + File.separator + "edit_16.png","Edit");
		formationEditButton.getElement().setAttribute("title", "Edit");
		formationEditButton.setIcon(formationEditimage);

		formationDeleteButton = new Button();
		formationDeleteButton.setId("bgz_well_formationDelete");
		Image formationCloseimage = new Image("icons" + File.separator + "16x" + File.separator + "delete16.png", "delete");
		formationDeleteButton.getElement().setAttribute("title", "Delete");
		formationDeleteButton.setIcon(formationCloseimage);

		formationLayout.add(formationAddButton, formationEditButton, formationDeleteButton);

		HorizontalLayout mainActionLayout = new HorizontalLayout();

		saveButton = new Button("Save");
		saveButton.setId("bgz_well_formationsaveButton");
		saveButton.addThemeVariants(ButtonVariant.LUMO_SMALL,ButtonVariant.LUMO_PRIMARY);

		clearButton = new Button("Clear");
		clearButton.setId("bgz_well_clearButton");

		closeButton = new Button("Close");
		closeButton.setId("bgz_well_closeButton");

		mainActionLayout.add(saveButton, clearButton);

		add( unitsLayout, formationGrid, formationLayout, mainActionLayout);
		setAlignSelf(Alignment.CENTER, formationLayout);
		setAlignSelf(Alignment.CENTER, formationLayout);
		setAlignSelf(Alignment.BASELINE, mainActionLayout);
	}
	private void setUnitSToComboBox() {
		// TODO Auto-generated method stub

		String[] formationSizeUnits = UOMRegistry.getPossibleUnitsForCategory(lenghtUnitCategory);
		formationUnitComboBox.setItems(formationSizeUnits);
		formationUnitComboBox.setValue(formationSizeUnits[0]);

		for (String depthUnit : formationSizeUnits) {
			if (depthUnit.equals(depthUnit)) {
				formationUnitComboBox.setValue("m");
			}
		}

		this.formationUnitComboBox.addValueChangeListener(event -> {
			String previousDepthunit = _currentDepthUnit;
			_currentDepthUnit = formationUnitComboBox.getValue().toString().trim();
			formationGrid.getColumnByKey(topMDkey).setHeader("Top MD(" + _currentDepthUnit + ")");
			formationGrid.getColumnByKey(bottomMDKey).setHeader("Bottom MD(" + _currentDepthUnit + ")");

			UOM lengthUnitUom = lenghtUnitCategory.getUom();
			XKria_Unit_Conversion_Utility utility = XKria_Unit_Conversion_Utility.getInstance();
			ListDataProvider<Blgz_formation_Info> formationAllDatalist = (ListDataProvider<Blgz_formation_Info>) formationGrid
					.getDataProvider();

			List<Blgz_formation_Info> rowList =  new ArrayList<>();
			if (!formationAllDatalist.getItems().isEmpty()) {

				rowList = (List<Blgz_formation_Info>) formationAllDatalist.getItems();
			} else {
				rowList = new ArrayList<>();
			}

			List<Blgz_formation_Info> updatedList = new ArrayList<>();

			for (int i = 0; i < rowList.size(); i++) {
				Blgz_formation_Info formationInfo = rowList.get(i);
				double top = Double.valueOf(formationInfo.getTopMD());
				double convertedValue = utility.getConvertedValue(lengthUnitUom, top, previousDepthunit,
						_currentDepthUnit);
				convertedValue = DecimalFormatManager.getDoubleValueInEnUSLocale(convertedValue, 3);

				formationInfo.setTopMD(String.valueOf(convertedValue));

				double bottom = Double.valueOf(formationInfo.getBottomMD());

				convertedValue = utility.getConvertedValue(lengthUnitUom, bottom, previousDepthunit, _currentDepthUnit);
				convertedValue = DecimalFormatManager.getDoubleValueInEnUSLocale(convertedValue, 3);
				formationInfo.setBottomMD(String.valueOf(convertedValue));
				updatedList.add(formationInfo);

			}

			formationGrid.setItems(updatedList);

		});

	}

	private void addFormationButton() {
		// TODO Auto-generated method stub
		formationAddButton.addClickListener(event -> {

			updateSelectedDialog(false);
		});
	}

	public void editFormationButtonClickAction() {
		formationEditButton.addClickListener(event -> {
			if (formationGrid.asSingleSelect().getValue() == null) {
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

	private void updateSelectedDialog(boolean editFlag) {
		String[] units = new String[1];
		units[0] = formationUnitComboBox.getValue().toString();
		Blgz_Formation_Window formationWindow = new Blgz_Formation_Window(formationGrid, editFlag, units);
		formationWindow.open();

	}

	private void saveFormationDataEvent() {
		// TODO Auto-generated method stub
		saveButton.addClickListener(event -> {
			ListDataProvider<Blgz_formation_Info> dataProvider = (ListDataProvider<Blgz_formation_Info>) formationGrid
					.getDataProvider();
			List<Blgz_formation_Info> inputs = (List<Blgz_formation_Info>) dataProvider.getItems();
			ArrayList<ArrayList<String>> allformationData = new ArrayList<>();
			for (Blgz_formation_Info userInput : inputs) {
				ArrayList<String> formationData = new ArrayList<>();
				formationData.add(userInput.getFormationName());
				formationData.add(userInput.getRockType());
				formationData.add(userInput.getTopMD());
				formationData.add(userInput.getBottomMD());
				
				allformationData.add(formationData);
			}
			WellboreListInfo wellboreInfo = View_Update.getWellboreInfo();
			Long wellboreID = wellboreInfo.getWellboreID();
			
			FormationPojoClass allData = new FormationPojoClass();
			allData.setData(allformationData);

			FormationMetaDataInfo meta_data = new FormationMetaDataInfo();
			String[] columns = { "Formation_Name","Rock_Type","Top_MD","Bottom_MD"};
			String[] units = { "NA","NA","m" ,"m"};
			meta_data.setColumns(columns);
			meta_data.setUnits(units);

			allData.setMeta_data(meta_data);

			JSONObject formationObject = new JSONObject(allData);
			String query = "select count(*) from well_registry_db.formation where wellbore_id=" + wellboreID +"";
			int ifexist = new WellboreQueries(). wellboreExistByID(query);
			
			if (ifexist == 0) {
			try {
				new WellboreQueries().insertFormationData(wellboreID, formationObject);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			} else {
				System.out.println("wellbore exist already, plese update the wellbore");
				try {
					new WellboreQueries().updateDataInFormationView(wellboreID, formationObject);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}

	public void setDataToUI() {
		FormationPojoClass formationObject = null;
		WellboreListInfo wellboreInfo = View_Update.getWellboreInfo();
		Long wellboreID = wellboreInfo.getWellboreID();
//		Long wellboreID= (long) 10450864;
		try {
			formationObject = new WellboreQueries().getFormationData(wellboreID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (formationObject!=null) {
			ArrayList<ArrayList<String>> data = formationObject.getData();
			ArrayList<Blgz_formation_Info> formationDataList = new ArrayList<>();
			for (ArrayList<String> formationData : data) {
				
				Blgz_formation_Info blgzformationObject = new Blgz_formation_Info();
				
				blgzformationObject.setFormationName(formationData.get(0));
				blgzformationObject.setRockType(formationData.get(1));
				blgzformationObject.setTopMD(formationData.get(2));
				blgzformationObject.setBottomMD(formationData.get(3));
				formationDataList.add(blgzformationObject);
				
				
			}
			
			formationGrid.setItems(formationDataList);
		
		}
		
	}

	private void deleteFormationRowClickAction() {
//		// TODO Auto-generated method stub
		formationDeleteButton.addClickListener(event -> {

			Blgz_formation_Info selectedGridRow = formationGrid.asSingleSelect().getValue();
			if (formationGrid.asSingleSelect().getValue() == null) {
				PB_Progress_Notification notification = new PB_Progress_Notification();
				String selectaRow = PetrahubNotification_Utilities.getInstance().selectArowFromgrid();
								notification.setText(selectaRow);
								notification.open();
								notification.setDuration(3000);
			} else {
				ListDataProvider<Blgz_formation_Info> dataProvider = (ListDataProvider<Blgz_formation_Info>) formationGrid
						.getDataProvider();
				dataProvider.getItems().remove(selectedGridRow);
				dataProvider.refreshAll();
				formationGrid.getDataProvider().refreshAll();
				formationGrid.deselect(selectedGridRow);

			}

		});
	}

	private void clearButtonAction() {
		clearButton.addClickListener(event ->{
			
			ListDataProvider<Blgz_formation_Info> dataProvider = (ListDataProvider<Blgz_formation_Info>) formationGrid
					.getDataProvider();
//			List<Blgz_formation_Info> formationInfoList = new Blgz_Formation_Provider().getFormationData(selectedWellbore);
//
//			          dataProvider.getItems().removeAll();
//			          dataProvider.refreshAll();
//			          formationGrid.setItems();;
					
			
		});
		
	}
	

}

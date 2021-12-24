package com.petrabytes.views.well_Information;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.TreeSet;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.wellRegistryQuery.WellboreListInfo;
import com.petrabytes.views.wellbore.Blgz_Deviation_Info;
import com.sristy.unitsystem.UOM;
import com.sristy.unitsystem.UOMRegistry;
import com.sristy.unitsystem.UnitCategory;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;

public class Edit_Unit_UI extends PetrabyteUI_Dialog {
	
	private VerticalLayout mainlayout = new VerticalLayout();
	private List<UnitCategory> unitCategories;
	private static List<UnitCategory> defaultCategories;
	private boolean displayInvereUnitOptions = false;
	private ListBox<String> unitCatagoryList;
	private ListBox<String> unitList;
	private Label unitcategoryLabel = new Label();
	private Label unitLabel = new Label();
    

	private String selectedUnitCategory = "";
	private String selectedUnit = "";
	private static TreeMap<String, UnitCategory> _unitCategoryMap;
	private static TreeSet<String> _categoryNameSet;
	private Quality_Check_Info _logDetails;
	private String hdfFile = null;
	private String _drivePrefix = File.separator + "datadrive1";
	protected String osName = System.getProperty("os.name");
	Quality_Check_UI _ldwView ;
	Grid<Quality_Check_Info> qualitygrid;
	Label _unitcategoryLabel;
	Label _unitlabel;
	String wellboreID;
	public Edit_Unit_UI(Quality_Check_Info logDetails, Grid<Quality_Check_Info> qualitygrid, Label unitcategoryLabel2, Label unitLabel2) {
		this._logDetails = logDetails;
		this.qualitygrid = qualitygrid;
		unitCategories = UOMRegistry.getUnitCategyList();
        this._unitcategoryLabel = unitcategoryLabel2;
        this._unitlabel = unitLabel2;
		this.mainlayout.setWidth("680px");
		this.mainlayout.setHeight("410px");
		this.content.add(mainlayout);
		SetupUI();
		init();
		oKUnitActionEvent();
	}

	private void SetupUI() {
		// TODO Auto-generated method stub
		this.setTitle("Edit  Unit :");

		Div header = new Div();
		header.getStyle().set("width", "100%");
		Label text1 = new Label("Unit category");
		text1.getStyle().set("width", "40%");
		text1.getStyle().set("float", "left");
		text1.getStyle().set("padding-left", "15px");

		Label text2 = new Label("Unit");
		text2.getStyle().set("width", "40%");
		text2.getStyle().set("float", "left");
		text2.getStyle().set("padding-left", "50px");

		header.add(text1, text2);
		add(header);

		Div header1 = new Div();
		header1.getStyle().set("width", "100%");

		unitCatagoryList = new ListBox<>();
		unitCatagoryList.setId("bgz_logs_unitCatagoryList");
		unitCatagoryList.getElement().getStyle().set("margin-left", "16px");

		// listBox.getElement().getStyle().set("background-color", "lightgray");
		unitCatagoryList.getElement().getStyle().set("border-style", "solid");
		unitCatagoryList.getElement().getStyle().set("border-width", "thin");
		unitCatagoryList.getElement().getStyle().set("width", "46%");
		unitCatagoryList.getElement().getStyle().set("float", "left");
		unitCatagoryList.getElement().getStyle().set("height", "200px");

		unitList = new ListBox<>();
		unitList.setId("bgz_logs_unitList");
		unitList.getElement().getStyle().set("border-style", "solid");
		unitList.getElement().getStyle().set("border-width", "thin");
		unitList.getElement().getStyle().set("width", "46%");
		unitList.getElement().getStyle().set("float", "left");
		unitList.getElement().getStyle().set("margin-left", "10px");
		unitList.getElement().getStyle().set("height", "200px");

		header1.add(unitCatagoryList, unitList);
		add(header1);

//		HorizontalLayout footer = new HorizontalLayout();
//		footer.add(saveButton, closeButton);
//		footer.getStyle().set("margin-left", "72%");

//		add(footer);

		mainlayout.add(header, header1);

	}

	private void init() {
		// TODO Auto-generated method stub
		creatDefaultUnitCategories();
		populateUnitCategoriesList();

	}

	private void creatDefaultUnitCategories() {
		// TODO Auto-generated method stub
		unitCategories = new ArrayList<UnitCategory>();
		defaultCategories = new ArrayList<UnitCategory>();
		_unitCategoryMap = new TreeMap<String, UnitCategory>();
		_categoryNameSet = new TreeSet<String>();
		String MASTER_UNIT_CATEGORIES_PATH = "com/sristy/unitsystem/resources/masterUnitCategories";
		ResourceBundle rb = ResourceBundle.getBundle(MASTER_UNIT_CATEGORIES_PATH);

		Enumeration<String> e = rb.getKeys();

		List keyList = Collections.list(e);
		Collections.sort(keyList);
		Enumeration keys = Collections.enumeration(keyList);

		while (keys.hasMoreElements()) {
			String key = keys.nextElement().toString();
			String value = rb.getString(key);
			if (value != null) {
				String[] strs = value.split(",");
				boolean applicable = strs[3].equalsIgnoreCase("true");
				UnitCategory category = new UnitCategory(key, strs[0], strs[1], strs[2], applicable);
				if (category == null) {
					return;
				}
				category.setOther(false);
				unitCategories.add(category);
				defaultCategories.add(category);
				_categoryNameSet.add(category.getCategoryName());
				_unitCategoryMap.put(category.getCategoryName(), category);
			}
		}
	}

	private void populateUnitCategoriesList() {
		// TODO Auto-generated method stub
		ArrayList<String> _unitsList = new ArrayList<String>();
		ArrayList<String> _unitsCatgoryList = new ArrayList<String>();
		UnitCategory uc;
		for (int i = 0; i < unitCategories.size(); i++) {
			int j = i;
			uc = unitCategories.get(i);
			String categoryName = uc.getCategoryName();
			_unitsCatgoryList.add(categoryName);
			unitCatagoryList.setItems(_unitsCatgoryList);
			UOM uom = uc.getUom();
			if (uom == null) {
				continue;
			}
			if (displayInvereUnitOptions) {
				String[] possibleInverseUnitOptions = UOMRegistry.getPossibleInverseUnitOptions(uc.getCategoryName());
				if (possibleInverseUnitOptions != null) {
					for (String possibleInverseUnitOption : possibleInverseUnitOptions) {
						unitList.setItems(possibleInverseUnitOption);
					}
				}
			}

		}

		this.unitCatagoryList.addValueChangeListener(event -> {

			unitCatagoryList.getValue();
			for (String unitsList1 : _unitsList) {
				if (!unitsList1.equalsIgnoreCase("unknown")) {
					unitList.setItems(unitsList1);
					// unitTree.setParent(unitsList1, categoryName);

				}
			}

			UnitCategory _uc = UOMRegistry.getUnitCategoryForName(unitCatagoryList.getValue().toString());
			String[] unitStrs = UOMRegistry.getPossibleUnitsForCategory(_uc);
			ArrayList<String> unitsDataList = new ArrayList<String>();
			ArrayList<String> unitDataListed = new ArrayList<String>();

			for (String unit : unitStrs) {
				unitDataListed.add(unit);
				unitList.setItems(unitDataListed);
			}
			for (String unitsListData : unitsDataList) {
				unitDataListed.add(unitsListData);
				unitList.setItems(unitDataListed);
				// unitTree.setParent(unitsList1, categoryName);
			}

		});

	}

	private void oKUnitActionEvent() {
		// TODO Auto-generated method stub
		this.saveButton.addClickListener(event -> {

			if (unitCatagoryList.getValue() != null) {
				selectedUnitCategory = unitCatagoryList.getValue().toString();

				if (unitList.getValue() != null) {
				
					   _logDetails.setMappedUnit(unitList.getValue());
					   _logDetails.setMappedUnitCategory(selectedUnitCategory);
						ListDataProvider<Quality_Check_Info> dataProvider = (ListDataProvider<Quality_Check_Info>) qualitygrid
								.getDataProvider();
						List<Quality_Check_Info> qualitygridinputs = (List<Quality_Check_Info>) dataProvider.getItems();
					   
					   for (Quality_Check_Info row:qualitygridinputs) {
						   
						   if(row.getMnemonic().equals(_logDetails.getMnemonic())){
							   
							   row.setUnit(unitList.getValue());
							   row.setUnitCategory(selectedUnitCategory);
							   wellboreID = Quality_Check_UI.sellectedWellboreID;
							      Long logid = _logDetails.getLog_id();
							      try {
									new WellLogsDataMappingQuery().updateUnitAndUnitCategory(wellboreID, logid, unitList.getValue(), selectedUnitCategory);
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							   
						   }
						   
						   qualitygrid.setItems(qualitygridinputs);
						   _unitcategoryLabel.setText(selectedUnitCategory);
						   _unitlabel.setText(unitList.getValue());
					   }
					  // _ldwView.updateQualityGrid(_logDetails);
					   this.close();
				} else {
					PB_Progress_Notification notificatiion = new PB_Progress_Notification();
					String selectunit = PetrahubNotification_Utilities.getInstance().selectUnit();
					notificatiion.setText(selectunit);
					notificatiion.open();
					notificatiion.setDuration(3000);
				}

			} else {
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				String unitcategory = PetrahubNotification_Utilities.getInstance().selectUnitCategory();
				notificatiion.setText(unitcategory);
				notificatiion.open();
				notificatiion.setDuration(3000);
			}

		});

	}

	

	public Label getUnitcategoryLabel() {
		return unitcategoryLabel;
	}

	public void setUnitcategoryLabel(Label unitcategoryLabel) {
		this.unitcategoryLabel = unitcategoryLabel;
	}

	public Label getUnitLabel() {
		return unitLabel;
	}

	public void setUnitLabel(Label unitLabel) {
		this.unitLabel = unitLabel;
	}

}

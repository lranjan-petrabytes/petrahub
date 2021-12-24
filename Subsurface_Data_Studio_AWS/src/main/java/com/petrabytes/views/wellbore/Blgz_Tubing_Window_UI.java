package com.petrabytes.views.wellbore;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.witsml.schemas.x1Series.CsTubularComponent;
import org.witsml.schemas.x1Series.ObjTubular;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.views.wellRegistryQuery.WellboreQueries;
import com.sristy.casinginfopack.CasingGradeReference;
import com.sristy.common.utils.DecimalFormatManager;
import com.sristy.unitsystem.UOM;
import com.sristy.unitsystem.UOMRegistry;
import com.sristy.unitsystem.UnitCategory;
import com.sristy.witsml.extras.Tubing_Component_Ext_Info;
import com.sristy.witsml.extras.Tubing_Ext_Info;
import com.sristy.xkriaextensions.unitsutility.util.XKria_Unit_Conversion_Utility;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.VaadinSession;



public class Blgz_Tubing_Window_UI extends PetrabyteUI_Dialog {
	private VerticalLayout mainLayout = new VerticalLayout();
	FormLayout formLayout = new FormLayout();

	private ComboBox<String> tubularsTypeComboboxmboBox;
	private ComboBox<String> weightCombobox;
	private ComboBox<String> tubingODCombobox;
	private ComboBox<String> gradeCombobox;
	private TextField topTextfield;
	private TextField tubingIDTextfield;
	private TextField bottomTextfield;
	private TextField roughtnessTextfield;

	private Binder<Blgz_Tubing_Info> binder = new Binder<>();
	private Grid<Blgz_Tubing_Info> _tubingGrid;
	private Grid<Blgz_Casing_Info> _casingGrid;
	private boolean _editFlag;
	private String[] currentUnits;
	private Tubing_Ext_Info tubular_EXT;
	private ObjTubular _tubular;
	private Tubing_Ext_Info _tubular_EXT;
	private String casingID;
	private List<CasingReferenceInfo> casingReferenceMMList = new ArrayList<>();
	private List<CasingReferenceInfo> casingReferenceINList = new ArrayList<>();
	private String db_type_prop = null;

	private boolean editFlag = false;

//	private IBlgz_SQL_Core_Interface coreSqlService = new Blgz_SQL_Core_Service();
	private CasingGradeReference gradeReference = CasingGradeReference.getInstance();

	ValueChangeListener<ValueChangeEvent<?>> weightValueChangeListener = null;
	ValueChangeListener<ValueChangeEvent<?>> topMDValueChangeListener = null;
	ValueChangeListener<ValueChangeEvent<?>> bottonMDValueChangeListener = null;
	String sessionID = VaadinSession.getCurrent().getSession().getId();
	XKria_Unit_Conversion_Utility utility = XKria_Unit_Conversion_Utility.getInstance();
	private UnitCategory lenghtUnitCategory = UOMRegistry.getUnitCategoryForName("Measured Depth");

	public Blgz_Tubing_Window_UI(Grid<Blgz_Tubing_Info> tubingGrid, Grid<Blgz_Casing_Info> casingGrid, boolean editFlag,
			String[] units, String lastCasingID, ObjTubular tubular, Tubing_Ext_Info tubular_EXT) {
		// TODO Auto-generated constructor stub

//		coreSqlService.setSesstionID(sessionID);
		this._tubingGrid = tubingGrid;
		this._casingGrid = casingGrid;
		this._editFlag = editFlag;
		casingID = lastCasingID;
		_tubular = tubular;
		_tubular_EXT = tubular_EXT;
		currentUnits = units;

		if (_editFlag == true) {
			this.setTitle("Edit Tubing");
		} else {
			this.setTitle("New Tubing");
		}

		this.getElement().getStyle().set("padding", "0 0");
		this.setImage("icons"+File.separator+"casing_tubing24.png");

		this.content.add(mainLayout);
		mainLayout.setWidth("750px");


		_setUI();

	}

	private void _setUI() {
		// TODO Auto-generated method stub
		tubularsTypeComboboxmboBox = new ComboBox<String>();
		tubularsTypeComboboxmboBox.getElement().getStyle().set("margin-left", "25px");
		formLayout.addFormItem(tubularsTypeComboboxmboBox, "Tubing Type");

		tubingODCombobox = new ComboBox<String>();
		tubingODCombobox.getElement().getStyle().set("margin-left", "25px");
		formLayout.addFormItem(tubingODCombobox, "Tubing OD (" + currentUnits[0] + ")");

		gradeCombobox = new ComboBox<String>();
		gradeCombobox.getElement().getStyle().set("margin-left", "25px");
		formLayout.addFormItem(gradeCombobox, "Grade");

		weightCombobox = new ComboBox<String>();
		weightCombobox.getElement().getStyle().set("margin-left", "25px");
		formLayout.addFormItem(weightCombobox, "Weight");

		topTextfield = new TextField();
		topTextfield.getElement().getStyle().set("margin-left", "25px");
		formLayout.addFormItem(topTextfield, "Top (" + currentUnits[0] + ")");

		bottomTextfield = new TextField();
		bottomTextfield.getElement().getStyle().set("margin-left", "25px");
		formLayout.addFormItem(bottomTextfield, "Bottom (" + currentUnits[0] + ")");

		tubingIDTextfield = new TextField();
		tubingIDTextfield.getElement().getStyle().set("margin-left", "25px");
		formLayout.addFormItem(tubingIDTextfield, "Tubing ID (" + currentUnits[0] + ")");

		roughtnessTextfield = new TextField();
		roughtnessTextfield.getElement().getStyle().set("margin-left", "25px");
		formLayout.addFormItem(roughtnessTextfield, "Roughness");

		formLayout.setResponsiveSteps(new ResponsiveStep("10em", 1), new ResponsiveStep("30em", 2));
		mainLayout.add(formLayout);
		mainLayout.expand(formLayout);

		newtubingBinding();
		tubingODComboboxAction();
		weightComboboxAction();
		tubingComboBoxesSetup();
		topTextfieldActionEvent();
		bottomTextFieldActionEvent();
		okButtonClickAction();

		setDataToForm();
		
		topTextfield.addValueChangeListener(topMDValueChangeListener);
		bottomTextfield.addValueChangeListener(bottonMDValueChangeListener);

	}

	

	

	private void newtubingBinding() {
		// TODO Auto-generated method stub

		binder.forField(tubularsTypeComboboxmboBox).bind(Blgz_Tubing_Info::getTubularsCotubingType,
				Blgz_Tubing_Info::setTubularsCotubingType);
		binder.forField(tubingODCombobox).bind(Blgz_Tubing_Info::getTubingOd, Blgz_Tubing_Info::setTubingOd);
		binder.forField(gradeCombobox).bind(Blgz_Tubing_Info::getGrade, Blgz_Tubing_Info::setGrade);
		binder.forField(weightCombobox).bind(Blgz_Tubing_Info::getWeight, Blgz_Tubing_Info::setWeight);
		binder.forField(topTextfield).withConverter(new StringToDoubleConverter("Must enter a number"))
				.bind(Blgz_Tubing_Info::getTubingTopMD, Blgz_Tubing_Info::setTubingTopMD);
		binder.forField(bottomTextfield).withConverter(new StringToDoubleConverter("Must enter a number"))
				.bind(Blgz_Tubing_Info::getTubingBottomMD, Blgz_Tubing_Info::setTubingBottomMD);

		binder.forField(tubingIDTextfield).bind(Blgz_Tubing_Info::getTubingID, Blgz_Tubing_Info::setTubingID);
		binder.forField(roughtnessTextfield).bind(Blgz_Tubing_Info::getRoughness, Blgz_Tubing_Info::setRoughness);

	}

	/**
	 * code to tubing initialize combo boxes
	 */
	private void tubingComboBoxesSetup() {
		// TODO Auto-generated method stub

		// setting tubing types to tubing type combo box
		// ComboBox tubingTypeComboBox = new ComboBox();
		String[] completionTypes = new String[] { "Tubing", "Monobore" };
		tubularsTypeComboboxmboBox.setItems(completionTypes);
		// setting the deafault tubing type to the tubingTypeComboBox
		tubularsTypeComboboxmboBox.setValue("Tubing");
		// setting values of tubing od coloumn
		float casingIDDouble = Float.valueOf(casingID);
		List<String> tubesizelist = new ArrayList<>();
		if (currentUnits[0].equalsIgnoreCase("mm")) {
			tubesizelist = getTubingListForCasingID_MM(casingIDDouble);
		} else {
			tubesizelist = getTubingListForCasingID_IN(casingIDDouble);
		}
		tubingODCombobox.setItems(tubesizelist);
		tubingODCombobox.setValue(tubesizelist.get(0));

		// setting values for gradeCombobox
		String[] grades = gradeReference.getCasingGrades();
		gradeCombobox.setItems(grades);
		gradeCombobox.setValue("L80");
		// setting default values to roughtnessTextfield
		roughtnessTextfield.setValue("0.006");
	}

	/**
	 * method to get tubingODsList for mm unit
	 * 
	 * @param casingIDDouble
	 * @param b
	 * @return
	 */
	private List<String> getTubingListForCasingID_MM(float casingIDDouble) {
		// TODO Auto-generated method stub
		List<String> tubesizelist = new ArrayList<>();
		List<Float> _tubesizelist = new ArrayList<Float>();
		if (casingReferenceMMList.isEmpty()) {
			
			try {
				casingReferenceMMList =new WellboreQueries().getAllCasingReference("mm");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}	
		

//
		for (CasingReferenceInfo casingReference : casingReferenceMMList) {
			String casing_OD = casingReference.getCasing_od();
			if (Float.valueOf(casing_OD) < casingIDDouble) {
				String str = casing_OD;
				if (!_tubesizelist.contains(casing_OD)) {
					_tubesizelist.add(Float.valueOf(casing_OD));
				}
			}
		}

		Collections.sort(_tubesizelist, Collections.reverseOrder());
		for (float tubingOD : _tubesizelist) {
			String str = String.valueOf(tubingOD);
			double doubleValueInEnUSLocale = DecimalFormatManager.getDoubleValueInEnUSLocale(str, 3);
			if (Double.isNaN(doubleValueInEnUSLocale)) {
				tubesizelist.add("");
			} else {
				tubesizelist.add(String.valueOf(doubleValueInEnUSLocale));
			}

		}

		return tubesizelist;

	}

	/**
	 * method to get tubingODsList for IN unit
	 * 
	 * @param casingIDDouble
	 * @param b
	 * @return
	 */
	private List<String> getTubingListForCasingID_IN(float casingIDDouble) {
		// TODO Auto-generated method stub
		List<String> tubesizelist = new ArrayList<>();
		List<Float> _tubesizelist = new ArrayList<Float>();
		if (casingReferenceINList.isEmpty()) {

		
		try {
			casingReferenceINList =new WellboreQueries().getAllCasingReference("in");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
		for (CasingReferenceInfo casingReference : casingReferenceINList) {
			String casing_OD = casingReference.getCasing_od();
			if (Float.valueOf(casing_OD) < casingIDDouble) {
				String str = casing_OD;
				if (!_tubesizelist.contains(casing_OD)) {
					_tubesizelist.add(Float.valueOf(casing_OD));
				}
			}
		}
		Collections.sort(_tubesizelist, Collections.reverseOrder());
		for (float tubingOD : _tubesizelist) {
			String str = String.valueOf(tubingOD);
			double doubleValueInEnUSLocale = DecimalFormatManager.getDoubleValueInEnUSLocale(str, 3);
			if (Double.isNaN(doubleValueInEnUSLocale)) {
				tubesizelist.add("");
			} else {
				tubesizelist.add(String.valueOf(doubleValueInEnUSLocale));
			}

		}

		return tubesizelist;

	}

	private void weightComboboxAction() {
		// TODO Auto-generated method stub
		weightValueChangeListener = new ValueChangeListener<ValueChangeEvent<?>>() {

			@Override
			public void valueChanged(ValueChangeEvent<?> event) {
				// TODO Auto-generated method stub

				float selectTubingOD_value = Float.valueOf(tubingODCombobox.getValue().toString());
				float selectedWeight_value = Float.valueOf(weightCombobox.getValue().toString());
				if (currentUnits[0].equalsIgnoreCase("mm")) {
					getTubingIDforMM(selectTubingOD_value, selectedWeight_value);
				} else {
					getTubingIDforIN(selectTubingOD_value, selectedWeight_value);
				}

			}
		};

	}

	/**
	 * getting casing Id by using unit mm
	 * 
	 * @param selectedHoleSize_value
	 */
	private void getTubingIDforMM(float selectTubingOD_value, float selectedWeight_value) {
		if (casingReferenceMMList.isEmpty()) {

		try {
			casingReferenceMMList =new WellboreQueries().getAllCasingReference("mm");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
		List<String> casgingIDList = null;

		for (CasingReferenceInfo casingRefernce : casingReferenceMMList) {
			float casingOD = Float.valueOf(casingRefernce.getCasing_od());
			float weight = Float.valueOf(casingRefernce.getWeight());
			if (casingOD == selectTubingOD_value) {
				if (weight == selectedWeight_value) {
					tubingIDTextfield.setValue(casingRefernce.getCasing_id());
					break;
				}
			}
		}
	}

	/**
	 * getting casing Id by using unit in
	 * 
	 * @param selectedHoleSize_value
	 */
	private void getTubingIDforIN(float selectTubingOD_value, float selectedWeight_value) {
		if (casingReferenceINList.isEmpty()) {

			try {
				casingReferenceINList =new WellboreQueries().getAllCasingReference("in");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		List<String> casgingIDList = null;
		
		for (CasingReferenceInfo casingRefernce : casingReferenceINList) {
			float casingOD = Float.valueOf(casingRefernce.getCasing_od());
			float weight = Float.valueOf(casingRefernce.getWeight());
			if (casingOD == selectTubingOD_value) {
				if (weight == selectedWeight_value) {
					tubingIDTextfield.setValue(casingRefernce.getCasing_id());
					break;
				}
			}
		}
	}

	private void topTextfieldActionEvent() {
		// TODO Auto-generated method stub
		topMDValueChangeListener = new ValueChangeListener<ValueChangeEvent<?>>() {

			@Override
			public void valueChanged(ValueChangeEvent<?> event) {
				// TODO Auto-generated method stub
				Blgz_Casing_Info lastRow = null;
				double bottom = 0;
				double selectRowBottom = 0;


				try {

					UOM lengthUnitUom = lenghtUnitCategory.getUom();

					ListDataProvider<Blgz_Casing_Info> dataProvider = (ListDataProvider<Blgz_Casing_Info>) _casingGrid
							.getDataProvider();
					List<Blgz_Casing_Info> ItemsList = (List<Blgz_Casing_Info>) dataProvider.getItems();
					if (!ItemsList.isEmpty()) {

						lastRow = ItemsList.get(ItemsList.size() - 1);

						CsTubularComponent[] tubularComponent = _tubular.getTubularComponentArray();
						for (int i = 0; i < tubularComponent.length; i++) {
//								if (tubularComponent[i].getSequence() == index) {
							for (Tubing_Component_Ext_Info component_Ext_Info : _tubular_EXT.getComponentList()) {
								int selectedIndex = Integer.valueOf(component_Ext_Info.getId().split("-")[1].trim());
//										if (selectedIndex == index) {
								bottom = utility.getConvertedValue(lengthUnitUom, component_Ext_Info.getBottom(),
										component_Ext_Info.getDepthUnit(), currentUnits[1]);
								bottom = DecimalFormatManager.getDoubleValueInEnUSLocale(bottom, 3);
//										}
							}
//								}
						}
						double topValue = Double.valueOf(topTextfield.getValue().toString());
						if (topValue < bottom) {
							Notification.show("Value should be greater than " + String.valueOf(bottom));

							topTextfield.clear();
							topTextfield.addValueChangeListener(topMDValueChangeListener);
						}
					}
					if (!ItemsList.isEmpty()) {

						lastRow = ItemsList.get(ItemsList.size() - 1);
						Object basintBottomObj = lastRow.getCasingBottomMDTextfield();
						if (basintBottomObj != null) {
							selectRowBottom = Double.valueOf(basintBottomObj.toString());
						}
					}
					try {
						double topValue = Double.valueOf(topTextfield.getValue().toString());
						if (topValue > selectRowBottom) {
							Notification.show("Value should be Less than " + String.valueOf(selectRowBottom));

							topTextfield.clear();
							topTextfield.addValueChangeListener(topMDValueChangeListener);
						}

					} catch (Exception e) {
						// telemetry.trackException(e);
						e.printStackTrace();
					}

				} catch (Exception e) {
					// telemetry.trackException(e);
					e.printStackTrace();
					Notification.show("Please Enter Numeric Value.");

					topTextfield.clear();
					topTextfield.addValueChangeListener(topMDValueChangeListener);
				}
			}

		};

	}
	
	private void bottomTextFieldActionEvent() {
		// TODO Auto-generated method stub
		bottonMDValueChangeListener = new ValueChangeListener<ValueChangeEvent<?>>() {

			@Override
			public void valueChanged(ValueChangeEvent<?> event) {
				// TODO Auto-generated method stub
				Blgz_Casing_Info lastRow = null;

				try {
					ListDataProvider<Blgz_Casing_Info> dataProvider = (ListDataProvider<Blgz_Casing_Info>) _casingGrid
							.getDataProvider();
					List<Blgz_Casing_Info> ItemsList = (List<Blgz_Casing_Info>) dataProvider.getItems();
					if (!ItemsList.isEmpty()) {

						lastRow = ItemsList.get(ItemsList.size() - 1);
							Object basintBottomObj = lastRow.getCasingBottomMDTextfield();
							if (basintBottomObj != null) {
								float basinBottom = Float.valueOf(basintBottomObj.toString());
								float bottomValue = Float.valueOf(bottomTextfield.getValue().toString());
								if (bottomValue > basinBottom) {
									Notification.show("Value should be less than " + basintBottomObj.toString());
									bottomTextfield.clear();
									bottomTextfield.addValueChangeListener(bottonMDValueChangeListener);
								}
							}
							double topValue = Double.valueOf(topTextfield.getValue().toString());
							double bottomValue = Double.valueOf(bottomTextfield.getValue().toString());
							if (topValue > bottomValue) {
								Notification.show("Value should be greater than " + String.valueOf(topValue));								
								bottomTextfield.clear();
								bottomTextfield.addValueChangeListener(bottonMDValueChangeListener);
							}
					}
				} catch (Exception e) {
					// TODO: handle exception
					// telemetry.trackException(e);
					e.printStackTrace();
					String bottomstg = bottomTextfield.getValue().toString();
					if (!bottomstg.isEmpty()) {
						Notification.show("Please Enter Numeric Value.");
						bottomTextfield.clear();
						bottomTextfield.addValueChangeListener(bottonMDValueChangeListener);
					}

				}
				
			}
			
		};
		
		
	}
	
	/**
	 * setting data to components
	 */
	private void setDataToForm() {
		// TODO Auto-generated method stub
		Blgz_Tubing_Info tubingIfo = _tubingGrid.asSingleSelect().getValue();
		if (tubingIfo != null) {

		double lenthMsId = Double
				.valueOf(tubingIfo.getTubingID().toString());

		// LengthMeasure od = component.addNewOd();

		double lenthMsOd = Double
				.valueOf(tubingIfo.getTubingOd().toString());

		String grade =tubingIfo.getGrade();

		double bottom = Double.valueOf(tubingIfo.getTubingBottomMD());

		double top = Double.valueOf(tubingIfo.getTubingTopMD());

		String tubularType = tubingIfo.getTubularsCotubingType();

		double weight = Double.valueOf(tubingIfo.getWeight());

		double roughness = Double
				.valueOf(tubingIfo.getWeight());

		tubularsTypeComboboxmboBox.setValue(tubularType);
		tubingODCombobox.setValue(String.valueOf(lenthMsOd));
		weightCombobox.setValue(String.valueOf(weight));
		gradeCombobox.setValue(String.valueOf(grade));
		topTextfield.setValue(String.valueOf(top));
		bottomTextfield.setValue(String.valueOf(bottom));
		tubingIDTextfield.setValue(String.valueOf(lenthMsId));
		}

	}
	
	private void tubingODComboboxAction() {
		// TODO Auto-generated method stub
		tubingODCombobox.addValueChangeListener(event -> {
			weightCombobox.clear();
			float selectTubingOD_value = Float.valueOf(tubingODCombobox.getValue().toString());
			List<String> weightList = new ArrayList<>();
			if (currentUnits[0].equalsIgnoreCase("mm")) {
				weightList = getWeightListForMM(selectTubingOD_value);
			} else {
				weightList = getWeightListForIN(selectTubingOD_value);
			}
			weightCombobox.addValueChangeListener(weightValueChangeListener);
			weightCombobox.setItems(weightList);
			weightCombobox.setValue(weightList.get(0));
			
		});
		
	}
	
	/**
	 * getting weight List by using unit MM
	 * 
	 * @param selectedHoleSize_value
	 * @return
	 */
	private List<String> getWeightListForMM(float selectTubingOD_value) {
		List<String> weightList = new ArrayList<>();
		if (casingReferenceMMList.isEmpty()) {

			try {
				casingReferenceMMList =new WellboreQueries().getAllCasingReference("mm");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

		for (CasingReferenceInfo casingRefernce : casingReferenceMMList) {
			float casingOD = Float.valueOf(casingRefernce.getCasing_od());
			if (casingOD == selectTubingOD_value) {
				String str = casingRefernce.getWeight();
				if (!weightList.contains(str)) {
					weightList.add(str);
				}
			}
		}

		Collections.sort(weightList);
		return weightList;
	}

	/**
	 * getting weight List by using unit in
	 * 
	 * @param selectedHoleSize_value
	 * @return
	 */
	private List<String> getWeightListForIN(float selectTubingOD_value) {
		List<String> weightList = new ArrayList<>();
		if (casingReferenceINList.isEmpty()) {

			try {
				casingReferenceINList =new WellboreQueries().getAllCasingReference("in");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}

		for (CasingReferenceInfo casingRefernce : casingReferenceINList) {
			float casingOD = Float.valueOf(casingRefernce.getCasing_od());
			if (casingOD == selectTubingOD_value){
				String str = casingRefernce.getWeight();
				if (!weightList.contains(str)) {
					weightList.add(str);
				}
			}
		}

		Collections.sort(weightList);
		return weightList;
	}
	

	private void okButtonClickAction() {
		// TODO Auto-generated method stub
		saveButton.addClickListener(event -> {
			/* Validate with not null value */
			Blgz_Tubing_Info tubingInfo = new Blgz_Tubing_Info();

			if(tubularsTypeComboboxmboBox.isEmpty() || tubingODCombobox.isEmpty() ||
					gradeCombobox.isEmpty() ||weightCombobox.isEmpty() ||topTextfield.isEmpty() ||bottomTextfield.isEmpty() 
					|| tubingIDTextfield.isEmpty()||roughtnessTextfield.isEmpty()) {
	        	  Notification.show("Please fill all the fields");
	        	  
	          } else {
	        	  /**
	  			 * Casing Window Saving
	  			 */
	  			if (_editFlag == false) {

	  				try {
	  					binder.writeBean(tubingInfo);
	  					addAllTubingSave(tubingInfo);
	  					close();

	  				} catch (ValidationException e) {
						
	  					e.printStackTrace();
	  				}
	  				/**
	  				 * Casing Window editing
	  				 */
	  			} else {

	  				try {
	  					binder.writeBean(tubingInfo);
	  				} catch (ValidationException e) {
						
	  					e.printStackTrace();
	  				}
//	  					
	  					ListDataProvider<Blgz_Tubing_Info> dataProviderList = (ListDataProvider<Blgz_Tubing_Info>) _tubingGrid
	  							.getDataProvider();
	  					
	  					
	  					List<Blgz_Tubing_Info> rowList = null;
	  					if (!dataProviderList.getItems().isEmpty()) {
	  						rowList = (List<Blgz_Tubing_Info>) dataProviderList.getItems();
	  					} else {
	  						rowList = new ArrayList<>();
	  					}

	  					Blgz_Tubing_Info selectedTubingValue = _tubingGrid.asSingleSelect().getValue();

	  					List<Blgz_Tubing_Info> updatedList = new ArrayList<>();
	  					for (Blgz_Tubing_Info eachFormation : rowList) {

	  						if (eachFormation.getTubingOd() == selectedTubingValue.getTubingOd()) {
	  							updatedList.add(tubingInfo);
	  						} else {
	  							updatedList.add(eachFormation);
	  						}

	  					}
	  					
	  					
	  					_tubingGrid.setItems(updatedList);

	  					close();

	  			}
	        	  
	          }
			
			
		});
		
	}
	public void addAllTubingSave(Blgz_Tubing_Info casinginfo) {
		ListDataProvider<Blgz_Tubing_Info> list = (ListDataProvider<Blgz_Tubing_Info>) _tubingGrid.getDataProvider();
		List<Blgz_Tubing_Info> rowList = null;
		if (!list.getItems().isEmpty()) {
			rowList = (List<Blgz_Tubing_Info>) list.getItems();
		} else {
			rowList = new ArrayList<>();
		}
		rowList.add(casinginfo);
		_tubingGrid.setItems(rowList);

	}



}

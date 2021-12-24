package com.petrabytes.views.wellbore;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.jsoup.select.Evaluator.IsEmpty;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.views.wellRegistryQuery.WellboreQueries;
import com.sristy.casinginfopack.CasingGradeReference;
import com.sristy.common.utils.DecimalFormatManager;
import com.sristy.unitsystem.UOM;
import com.sristy.unitsystem.UOMRegistry;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.FooterRow;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.VaadinSession;

public class Blgz_Casing_Window extends PetrabyteUI_Dialog {

	private ComboBox<String> tubularsComboBox;
	private ComboBox<String> completionsTypeComboBox;
	private ComboBox<String> holeSizeComboBox;
	private ComboBox<String> casingODComboBox;
	private ComboBox<String> gradeComboBox;
	private ComboBox<String> weightComboBox;
	private TextField holeMDTextField;
	private ComboBox<String> casingTopComboBox;
	private TextField casingIDTextField;
	private TextField ehdMultiplierTextField;
	private TextField casingBottomMDComboBox;

	private String type;
	private TextField textField = null;


	private VerticalLayout mainLayout = new VerticalLayout();
	FormLayout formLayout = new FormLayout();
	private Binder<Blgz_Casing_Info> binder = new Binder<>();
	private Grid<Blgz_Casing_Info> _casingGrid;
	private boolean _editFlag;

	private static UOM lengthUnit = UOMRegistry.createUnit(UOMRegistry.LENGTH);
	private CasingGradeReference gradeReference = CasingGradeReference.getInstance();

	private String[] holeIDs = null;
//	private String[] currentUnits = new String[2];
	private String[] currentUnits ;
	
	private String db_type_prop = null;

	private boolean editFlag = false;

//	private IBlgz_Core_Interface coreService = new Blgz_Core_Service();
//	private IBlgz_SQL_Core_Interface coreSqlService = new Blgz_SQL_Core_Service();
	private List<CasingReferenceInfo> casingReferenceMMList = new ArrayList<>();
	private List<CasingReferenceInfo> casingReferenceINList = null;

	ValueChangeListener<ValueChangeEvent<?>> casingValueChangeListener = null;
	ValueChangeListener<ValueChangeEvent<?>> weightValueChangeListener = null;
	ValueChangeListener<ValueChangeEvent<?>> holeMDValueChangeListener = null;
	ValueChangeListener<ValueChangeEvent<?>> CasingButtomMDValueChangeListener = null;
	
//	String sessionID = VaadinSession.getCurrent().getSession().getId();
	public Blgz_Casing_Window(Grid<Blgz_Casing_Info> casingGrid, boolean editFlag,String[] units) {
		
//		coreSqlService.setSesstionID(sessionID);
		_casingGrid = casingGrid;
		this._editFlag = editFlag;
		currentUnits = units;
		if (_editFlag == true) {
			this.setTitle("Edit Casing");
			this.setImage("icons" + File.separator + "purple-24x" + File.separator + "casing_tubing24_2.png");
		} else {
			this.setTitle("New Casing");
			this.setImage("icons" + File.separator + "purple-24x" + File.separator + "casing_tubing24_2.png");
		}

		this.getElement().getStyle().set("padding", "0 0");
		

		this.content.add(mainLayout);

		// TODO Auto-generated constructor stub
		mainLayout.setWidth("750px");
	//	mainLayout.getStyle().set("resize", "both");
		mainLayout.getStyle().set("overflow", "auto");
		mainLayout.getElement().getStyle().set("padding-left", "20px");
		holeIDs = new String[] { "36", "30", "26", "24", "20", "17.5", "16", "15", "14.75", "12.25", "11", "10.625",
				"10.375", "9.875", "8.75", "8.625", "8.5", "8.375", "7.875", "7.5", "7.0", "6.5", "6.0", "5.0", "4.5" };
//		currentUnits[0] = "mm";
		setUI();
		newCasingBinding();
		casingComboboxesSetup();
		upCasingDataUI();

		holeSizeComboboxAction();
		holeMDTextFieldAction();
		weightComboboxAction();
		casingODComboboxAction();
		casingBottomMDTextfieldAction();
		saveNewCasingButtonClickAction();
		holeMDTextField.addValueChangeListener(holeMDValueChangeListener);
	}

	private void setUI() {
		// TODO Auto-generated method stub
		
		
		tubularsComboBox = new ComboBox<String>();
		tubularsComboBox.setId("bgz_well_tubulars");
		tubularsComboBox.getElement().getStyle().set("margin-left", "25px");

		formLayout.addFormItem(tubularsComboBox, "Tubulars");
	//	formLayout.getStyle().set("border-style","groove");

		
		completionsTypeComboBox = new ComboBox<String>();
		completionsTypeComboBox.setId("bgz_well_completionsType");
		completionsTypeComboBox.getElement().getStyle().set("margin-left", "25px");

		formLayout.addFormItem(completionsTypeComboBox, "Completions Type");

		
		holeSizeComboBox = new ComboBox<String>();
		holeSizeComboBox.setId("bgz_well_holeSize");
		holeSizeComboBox.getElement().getStyle().set("margin-left", "25px");

		formLayout.addFormItem(holeSizeComboBox, "Hole Size (" + currentUnits[0] + ")");
		

	
		casingODComboBox = new ComboBox<String>();
		casingODComboBox.setId("bgz_well_casingOD");
		casingODComboBox.getElement().getStyle().set("margin-left", "25px");

		formLayout.addFormItem(casingODComboBox, "Casing OD(" + currentUnits[0] + ")");

		
		gradeComboBox = new ComboBox<String>();
		gradeComboBox.setId("bgz_well_grade");
		gradeComboBox.getElement().getStyle().set("margin-left", "25px");

		formLayout.addFormItem(gradeComboBox, "Grade");

		
		weightComboBox = new ComboBox<String>();
		weightComboBox.setId("bgz_well_weight");
		weightComboBox.getElement().getStyle().set("margin-left", "25px");

		formLayout.addFormItem(weightComboBox, "Weight");

		
		holeMDTextField = new TextField();
		holeMDTextField.setId("bgz_well_holeMD");
		holeMDTextField.getElement().getStyle().set("margin-left", "25px");

		formLayout.addFormItem(holeMDTextField, "Hole MD (" + currentUnits[1] + ")");
		
	
		casingBottomMDComboBox = new TextField();
		casingBottomMDComboBox.setId("bgz_well_casingBottomMD");
		casingBottomMDComboBox.getElement().getStyle().set("margin-left", "25px");
//		casingBottomMDComboBox.setEnabled(false);
		formLayout.addFormItem(casingBottomMDComboBox, "Casing Bottom MD (" + currentUnits[1] + ")");
		
		
		casingIDTextField = new TextField();
		casingIDTextField.setId("bgz_well_casingID");
		casingIDTextField.getElement().getStyle().set("margin-left", "25px");

		formLayout.addFormItem(casingIDTextField, "Casing ID (" + currentUnits[0] + ")");
          
		
		ehdMultiplierTextField = new TextField();
		ehdMultiplierTextField.setId("bgz_well_ehdMultiplier");
		ehdMultiplierTextField.getElement().getStyle().set("margin-left", "25px");

		formLayout.addFormItem(ehdMultiplierTextField, "EHD Multiplier");
       
		
		casingTopComboBox = new ComboBox<String>();
		casingTopComboBox.setId("bgz_well_casingTop");
		casingTopComboBox.getElement().getStyle().set("margin-left", "25px");

		formLayout.addFormItem(casingTopComboBox, "Casing Top MD (" + currentUnits[1] + ")");

		formLayout.setResponsiveSteps(new ResponsiveStep("10em", 1), new ResponsiveStep("30em", 2));

		mainLayout.add(formLayout);

		mainLayout.expand(formLayout);

	}

	private void newCasingBinding() {
		// TODO Auto-generated method stub

		binder.forField(tubularsComboBox).bind(Blgz_Casing_Info::getTubularsCombobox,
				Blgz_Casing_Info::setTubularsCombobox);
		binder.forField(completionsTypeComboBox).bind(Blgz_Casing_Info::getCompletionsTypeCombobox,
				Blgz_Casing_Info::setCompletionsTypeCombobox);
		binder.forField(holeSizeComboBox).bind(Blgz_Casing_Info::getHoleSizeCombobox,
				Blgz_Casing_Info::setHoleSizeCombobox);
		binder.forField(casingODComboBox).bind(Blgz_Casing_Info::getCasingODCombobox,
				Blgz_Casing_Info::setCasingODCombobox);
		binder.forField(gradeComboBox).bind(Blgz_Casing_Info::getGradeCombobox, Blgz_Casing_Info::setGradeCombobox);
		binder.forField(weightComboBox).bind(Blgz_Casing_Info::getWeightCombobox, Blgz_Casing_Info::setWeightCombobox);
		binder.forField(holeMDTextField).withConverter(new StringToDoubleConverter("Must enter a number")).bind(Blgz_Casing_Info::getHoleMDTextField, Blgz_Casing_Info::setHoleMDTextField);
		binder.forField(casingTopComboBox).bind(Blgz_Casing_Info::getCasingTopMDTextField,
				Blgz_Casing_Info::setCasingTopMDTextField);
		binder.forField(casingIDTextField).withConverter(new StringToDoubleConverter("Must enter a number")).bind(Blgz_Casing_Info::getCasingIDTextfield,
				Blgz_Casing_Info::setCasingIDTextfield);
		binder.forField(ehdMultiplierTextField).withConverter(new StringToIntegerConverter("Must enter a number")).bind(Blgz_Casing_Info::getEhdMultiplierTextfield,
				Blgz_Casing_Info::setEhdMultiplierTextfield);
		binder.forField(casingBottomMDComboBox).withConverter(new StringToDoubleConverter("Must enter a number")).bind(Blgz_Casing_Info::getCasingBottomMDTextfield,
				Blgz_Casing_Info::setCasingBottomMDTextfield);

	}

	private void saveNewCasingButtonClickAction() {
		// TODO Auto-generated method stub
		this.saveButton.addClickListener(event -> {
			Blgz_Casing_Info casinginfo = new Blgz_Casing_Info();
			
			
			/* Validate with not null value */
			if(tubularsComboBox.isEmpty() || completionsTypeComboBox.isEmpty() || holeSizeComboBox.isEmpty() || casingODComboBox.isEmpty()
					|| gradeComboBox.isEmpty() ||weightComboBox.isEmpty() ||holeMDTextField.isEmpty() ||casingTopComboBox.isEmpty() || casingIDTextField.isEmpty()||
					ehdMultiplierTextField.isEmpty() || casingBottomMDComboBox.isEmpty()) {
	        	  Notification.show("Please fill all the fields");
	        	  
	          } else {
			
			/**
			 * Casing Window Saving
			 */
			if (_editFlag == false) {

				try {
					binder.writeBean(casinginfo);
					addAllCasing(casinginfo);
					close();

				} catch (ValidationException e) {
					e.printStackTrace();
				}
				/**
				 * Casing Window editing
				 */
			} else {

				try {
					binder.writeBean(casinginfo);
				} catch (ValidationException e) {
					e.printStackTrace();
				}
//					
					ListDataProvider<Blgz_Casing_Info> dataProviderList = (ListDataProvider<Blgz_Casing_Info>) _casingGrid
							.getDataProvider();
					
					
					List<Blgz_Casing_Info> rowList = null;
					if (!dataProviderList.getItems().isEmpty()) {
						rowList = (List<Blgz_Casing_Info>) dataProviderList.getItems();
					} else {
						rowList = new ArrayList<>();
					}

					Blgz_Casing_Info selectedCAsingValue = _casingGrid.asSingleSelect().getValue();

					List<Blgz_Casing_Info> updatedList = new ArrayList<>();
					for (Blgz_Casing_Info eachFormation : rowList) {

						if (eachFormation.getHoleMDTextField() == selectedCAsingValue.getHoleMDTextField()) {
							updatedList.add(casinginfo);
						} else {
							updatedList.add(eachFormation);
						}

					}
					
					
					_casingGrid.setItems(updatedList);

					close();

			}
	          }
		});

	}

	private void casingComboboxesSetup() {
		// TODO Auto-generated method stub
		/**
		 * Setting items to tubular combo box
		 */

		String[] tubularItems = { "Casing", "Liner", "Open Hole" };
		tubularsComboBox.setItems(tubularItems);
		tubularsComboBox.setValue("Casing");

		/**
		 * Setting items to completionComboBox
		 */
		String[] completionTypeItems = { "Cased & Perforated", "Cased Gravel Pack", "Open Hole Gravel Pack",
				"Open Hole", "Cased" };
		completionsTypeComboBox.setItems(completionTypeItems);
		completionsTypeComboBox.setValue("Cased");

		/**
		 * Setting items to holeIDCombobox
		 */

		String[] convertpossibleHoleIDs = convertPossibleHoleids();
		List<String> holeSizeList = getArrayInCurrentLocale(convertpossibleHoleIDs);
		holeSizeComboBox.setItems(holeSizeList);
//		holeSizeComboBox.setItems(convertpossibleHoleIDs);
//

		/**
		 * Setting items to casingODComboBox
		 */

		
		List<String> casindODsForMM = new ArrayList<>();
			if (currentUnits[0].equalsIgnoreCase("mm")) {
				
				try {
					casindODsForMM =new WellboreQueries().getCasingOdList("mm");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
			} else if (currentUnits[0].equalsIgnoreCase("in")) {
				try {
					casindODsForMM =new WellboreQueries().getCasingOdList("in");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
						
			
		}
		
		
		
//		casindODsForMM.add();
		casingODComboBox.setItems(casindODsForMM);

		/**
		 * Setting items to gradeComboBox
		 */
		String[] grades = gradeReference.getCasingGrades();
		gradeComboBox.setItems(grades);
		gradeComboBox.setValue("L80");

		/**
		 * Setting items to weightComboBox
		 */
		List<String> weights = new ArrayList<>();
		if (currentUnits[0].equalsIgnoreCase("mm")) {
			try {
				weights =new WellboreQueries().getWeightsList("mm");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (currentUnits[0].equalsIgnoreCase("in")) {
			try {
				weights =new WellboreQueries().getWeightsList("in");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		weightComboBox.setItems(weights);
		casingTopComboBox.setItems("0.0");
		casingTopComboBox.setValue("0.0");
		ehdMultiplierTextField.setValue("1");

	}

	/**
	 * method to get the converted possible holeIDs
	 * 
	 * @return
	 */
	private String[] convertPossibleHoleids() {
		String convertedPossibleHoleids[] = new String[holeIDs.length];
		for (int i = 0; i < holeIDs.length; i++) {
			double val = 0;

			val = lengthUnit.convert(DecimalFormatManager.getDoubleValueInEnUSLocale(holeIDs[i]), "in",
					currentUnits[0]);

			if (Double.isNaN(val)) {
				val = 0;
			}
			double doubleValueInEnUSLocale = DecimalFormatManager.getDoubleValueInEnUSLocale(val, 3);
			if (Double.isNaN(doubleValueInEnUSLocale)) {
				convertedPossibleHoleids[i] = "";
			} else {
				convertedPossibleHoleids[i] = String.valueOf(doubleValueInEnUSLocale);
			}
		}
		return convertedPossibleHoleids;
	}

	public void holeSizeComboboxAction() {
		holeSizeComboBox.addValueChangeListener(event -> {
			
			ListDataProvider<Blgz_Casing_Info> dataProvider=(ListDataProvider<Blgz_Casing_Info>) _casingGrid.getDataProvider();
			List<Blgz_Casing_Info> ItemsList=(List<Blgz_Casing_Info>) dataProvider.getItems();

			casingODComboBox.clear();
			float selectedValue = Float.valueOf(holeSizeComboBox.getValue().toString());
			List<String> casgingODList = new ArrayList<>();
			if (currentUnits[0].equalsIgnoreCase("mm")) {
				
				casgingODList = getCasingODListByMM(selectedValue);
			} else {
				casgingODList = getCasingODListByIN(selectedValue);
			}
			casingODComboBox.addValueChangeListener(casingValueChangeListener);
			casingODComboBox.setItems(casgingODList);
//			casingODComboBox.setValue(casgingODList.get(0));
			
			
			Blgz_Casing_Info lastRow= null;

			if (!ItemsList.isEmpty()) { 
				lastRow  =ItemsList.get(ItemsList.size()-1);
				// if ((int) lastRow != 0) {
				float lastCasingOD = 0f;
				if (lastRow.getCasingODCombobox() != null) {
					lastCasingOD = Float
							.valueOf(lastRow.getCasingODCombobox().toString());
				}
				for (String currentcasingOD : casgingODList) {
					if (Float.valueOf(currentcasingOD) < lastCasingOD) {
						casingODComboBox.setValue(currentcasingOD);
						break;
					}
				}

			} else {
				casingODComboBox.setValue(casgingODList.get(0));
			}
			

		});

	}
     
	/**
	 * 
	 * getting CasingOD List by using unit MM
	 * 
	 * @param selectedHoleSize_value
	 * @return
	 */
	private List<String> getCasingODListByMM(float selectedHoleSize_value) {
		List<String> casgingODList = new ArrayList<>();
		try {
			casingReferenceMMList =new WellboreQueries().getAllCasingReference("mm");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Float> _casingODList = new ArrayList<>();
		for (int i = 0; i < casingReferenceMMList.size(); i++) {
			float od = Float.valueOf(casingReferenceMMList.get(i).getCasing_od());
			if (od < selectedHoleSize_value) {
				_casingODList.add(od);
			}
		}
		Collections.sort(_casingODList, Collections.reverseOrder());

		for (float od : _casingODList) {
			casgingODList.add(String.valueOf(od));
		}
		return casgingODList;
	}

	private List<String> getCasingODListByIN(float selectedHoleSize_value) {
		List<String> casgingODList = new ArrayList<>();
		try {
			casingReferenceINList =new WellboreQueries().getAllCasingReference("in");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		List<Float> _casingODList = new ArrayList<>();
		for (int i = 0; i < casingReferenceINList.size(); i++) {
			float od = Float.valueOf(casingReferenceINList.get(i).getCasing_od());
			if (od < selectedHoleSize_value) {
				_casingODList.add(od);
			}
		}
		Collections.sort(_casingODList, Collections.reverseOrder());

		for (float od : _casingODList) {
			casgingODList.add(String.valueOf(od));
		}
		return casgingODList;
	}

	public void holeMDTextFieldAction() {

		holeMDTextField.addKeyPressListener(Key.ENTER, e -> {

			try {
				if (!holeMDTextField.getValue().isEmpty()) {
					String value = String.valueOf(holeMDTextField.getValue());
					casingBottomMDComboBox.setValue(value);
				} else {
					casingBottomMDComboBox.clear();
				}
			} catch (Exception ex) {
				// TODO: handle exception
				// telemetry.trackException(e);
				Notification.show("Please Enter Numeric Value.");
				ex.printStackTrace();
				
				holeMDTextField.clear();
				holeMDTextField.addValueChangeListener(holeMDValueChangeListener);
				casingBottomMDComboBox.clear();

			}
		});
	}

	public void weightComboboxAction() {

		weightValueChangeListener = new ValueChangeListener<ValueChangeEvent<?>>() {

			@Override
			public void valueChanged(ValueChangeEvent<?> event) {
				// TODO Auto-generated method stub

				float selectCasginOD_value = Float.valueOf(casingODComboBox.getValue());
				System.out.println(weightComboBox.getValue());
				float selectedWeight_value = Float.valueOf(weightComboBox.getValue());
				if (currentUnits[0].equalsIgnoreCase("mm")) {
					getCasigIDforMM(selectCasginOD_value, selectedWeight_value);
				} else {
					getCasigIDforIN(selectCasginOD_value, selectedWeight_value);
				}

			}

		};
	}

	private void getCasigIDforMM(float selectCasginOD_value, float selectedWeight_value) {
		if (casingReferenceMMList.isEmpty()) {
			
			try {
				casingReferenceMMList =new WellboreQueries().getAllCasingReference("mm");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		}
		List<String> casgingIDList = new ArrayList<>();

		try {
			casingReferenceMMList =new WellboreQueries().getAllCasingReference("mm");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (CasingReferenceInfo casingRefernce : casingReferenceMMList) {
			float casingOD = Float.valueOf(casingRefernce.getCasing_od());
			float weight = Float.valueOf(casingRefernce.getWeight());
			if (casingOD == selectCasginOD_value) {
				if (weight == selectedWeight_value) {
					casingIDTextField.setValue(casingRefernce.getCasing_id());
					break;
				}
			}
		}
	}

	private void getCasigIDforIN(float selectCasginOD_value, float selectedWeight_value) {
		if (casingReferenceINList.isEmpty()) {

			
			try {
				casingReferenceINList =new WellboreQueries().getAllCasingReference("in");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		List<String> casgingIDList = new ArrayList<>();

		
		try {
			casingReferenceINList =new WellboreQueries().getAllCasingReference("in");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		for (CasingReferenceInfo casingRefernce : casingReferenceINList) {
			float casingOD = Float.valueOf(casingRefernce.getCasing_od());
			float weight = Float.valueOf(casingRefernce.getWeight());
			if (casingOD == selectCasginOD_value) {
				if (weight == selectedWeight_value) {
					casingIDTextField.setValue(casingRefernce.getCasing_id());
					break;
				}
			}
		}
	}

	public void casingODComboboxAction() {
		casingValueChangeListener = new ValueChangeListener<ValueChangeEvent<?>>() {

			@Override
			public void valueChanged(ValueChangeEvent<?> event) {
				// TODO Auto-generated method stub

				weightComboBox.clear();

				float selectCasginOD_value = Float.valueOf(casingODComboBox.getValue().toString());
				List<String> weightList = new ArrayList<>();
				if (currentUnits[0].equalsIgnoreCase("mm")) {
					weightList = getWeightListForMM(selectCasginOD_value);
				} else {
					weightList = getWeightListForIN(selectCasginOD_value);
				}
				weightComboBox.addValueChangeListener(weightValueChangeListener);
				weightComboBox.setItems(weightList);
				weightComboBox.setValue(weightList.get(0));

			}

		};
	}

	public void casingBottomMDTextfieldAction() {
		casingBottomMDComboBox.addKeyPressListener(Key.ESCAPE, e -> {
			holeMDTextField.clear();
			holeMDTextField.addValueChangeListener(holeMDValueChangeListener);
			casingBottomMDComboBox.clear();
		});
	}

	private List<String> getWeightListForMM(float selectCasginOD_value) {
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
			if (casingOD == selectCasginOD_value) {
				String str = casingRefernce.getWeight();
				if (!weightList.contains(str)) {
					weightList.add(str);
				}
			}
		}
		Collections.sort(weightList);
		return weightList;
	}

	private List<String> getWeightListForIN(float selectCasginOD_value) {
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
			if (casingOD == selectCasginOD_value) {
				String str = casingRefernce.getWeight();
				if (!weightList.contains(str)) {
					weightList.add(str);
				}
			}
		}
		Collections.sort(weightList);
		return weightList;
	}

	private void upCasingDataUI() {
		// TODO Auto-generated method stub
		if (_editFlag) {

			Blgz_Casing_Info casingIfo = _casingGrid.asSingleSelect().getValue();

			if (casingIfo != null) {
				tubularsComboBox.setValue(casingIfo.getTubularsCombobox());
				completionsTypeComboBox.setValue(casingIfo.getCompletionsTypeCombobox());
				holeSizeComboBox.setValue(casingIfo.getHoleSizeCombobox());
				casingODComboBox.setValue(casingIfo.getCasingODCombobox());
				casingBottomMDComboBox.setValue(String.valueOf(casingIfo.getCasingBottomMDTextfield()));
				gradeComboBox.setValue(casingIfo.getGradeCombobox());
				weightComboBox.setValue(casingIfo.getWeightCombobox());
				holeMDTextField.setValue(String.valueOf(casingIfo.getHoleMDTextField()));
				casingBottomMDComboBox.setValue(String.valueOf(casingIfo.getCasingBottomMDTextfield()));
				casingIDTextField.setValue(String.valueOf(casingIfo.getCasingIDTextfield()));
				ehdMultiplierTextField.setValue(String.valueOf(casingIfo.getEhdMultiplierTextfield()));
				casingTopComboBox.setValue(casingIfo.getCasingTopMDTextField());

			}
		}
	}
	
	private List<String> getArrayInCurrentLocale(String[] strArray) {
		List<String> holeSizeDataList = new ArrayList<>();

		
		double holeSize = 0.0;
		Blgz_Casing_Info lastRow= null;
		ListDataProvider<Blgz_Casing_Info> dataProvider=(ListDataProvider<Blgz_Casing_Info>) _casingGrid.getDataProvider();
		List<Blgz_Casing_Info> ItemsList=(List<Blgz_Casing_Info>) dataProvider.getItems();
		if (!ItemsList.isEmpty()) {
			
               lastRow  =ItemsList.get(ItemsList.size()-1);

               String holesizeObj = lastRow.getHoleSizeCombobox().toString();
				if (holesizeObj != null) {
					holeSize = Double.valueOf(holesizeObj.toString());

				}
		}
				
				for (int i = 0; i < strArray.length; i++) {
					strArray[i] = DecimalFormatManager.getValueForDisplayInCurrentLocale(strArray[i]);
					if (holeSize != 0.0) {
						String holeSizeStg = DecimalFormatManager.getValueForDisplayInCurrentLocale(strArray[i]);
						double holeSizeDle = Double.valueOf(holeSizeStg);
						if (holeSizeDle < holeSize) {
							holeSizeDataList.add(holeSizeStg);
						}
					} else {
						holeSizeDataList.add(strArray[i]);
					}

				}
		
		return holeSizeDataList;
	}
	public void addAllCasing(Blgz_Casing_Info casinginfo) {
		ListDataProvider<Blgz_Casing_Info> list = (ListDataProvider<Blgz_Casing_Info>) _casingGrid.getDataProvider();
		List<Blgz_Casing_Info> rowList = null;
		if (!list.getItems().isEmpty()) {
			rowList = (List<Blgz_Casing_Info>) list.getItems();
		} else {
			rowList = new ArrayList<>();
		}
		rowList.add(casinginfo);
		_casingGrid.setItems(rowList);

	}


}

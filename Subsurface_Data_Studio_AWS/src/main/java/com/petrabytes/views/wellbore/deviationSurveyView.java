package com.petrabytes.views.wellbore;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONObject;

import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.wellRegistryQuery.WellboreListInfo;
import com.petrabytes.views.wellRegistryQuery.WellboreQueries;
import com.petrabytes.views.wells.WellListInfo;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;
import elemental.json.JsonValue;
import com.vaadin.flow.internal.JsonSerializer;

@JsModule("./src/plotting/deviation_plotly.js")
public class deviationSurveyView extends VerticalLayout {
	private ComboBox<String> wellpathComboBox;
	private Button importbutton;
	private Button deviationAddButton;
	private Label surveyLabel;
	private Button calculateButton;
	private Button preview2DButton;
	private Button preview3DButton;
	public TextField surveyTextField;

	private Button deviationEditButton;
	private Button deviationRemoveButton;
	private Button saveButton;
	private Button clearButton;
	private String wellPathComboBoxValue;
	VerticalLayout nonPlotLayout;
	protected boolean saveFlag = true;
	private String wellTypePath = "";
	private Grid<Blgz_Deviation_Info> deviationgrid = new Grid<>();
	private VerticalLayout addlay = new VerticalLayout();
	private WellboreListInfo wellboreInfoG;
	private boolean select2d;
	private boolean select3d;

	public deviationSurveyView() {

		setSizeFull();
		setUI();
		deviationValidationwithSurvayFiles();
		wellPathComboBoxValue = wellpathComboBox.getValue();
		addDeviationButton();
		editDeviationButtonClickAction();
		deleteDeviationRowClickAction();
		calculateButtonClickAction();
		saveDeviationDataEvent();
		clearButtonClickAction();
		setDataToUI();
		preview2DClickAction();
		preview3DClickAction();
		
		select2d = false;
		select3d = false;
		
		add(addlay);

		wellboreInfoG = View_Update.getWellboreInfo();
		
		//DeviationQuery queryTest = new DeviationQuery();
		//String queryResult = queryTest.getDeviationQuery("select deviation_data from well_registry_db.deviation_survey where wellbore_id=\"14681532\"");
		
	}

	private void setUI() {
		// TODO Auto-generated method stub

		// this.setSizeFull();

		HorizontalLayout deviationLayout = new HorizontalLayout();
		Label wellPathLabel = new Label("Well Type");
		wellPathLabel.setId("bgz_well_wellType");
		wellPathLabel.getElement().getStyle().set("padding-top", "10px");

		wellpathComboBox = new ComboBox<String>();
		wellpathComboBox.setId("bgz_well_wellpath");
		// wellpathComboBox.setClassName("wellpathComboBox");
		wellpathComboBox.setItems("Vertical", "Deviated");
		wellpathComboBox.setValue("");

		calculateButton = new Button();
		calculateButton.setId("bgz_well_calculateButton");
		Image calculateimage = new Image("images" + File.separator + "calculator_24.png", "add");
		calculateButton.setIcon(calculateimage);
		calculateButton.setEnabled(false);
		
		preview2DButton = new Button();
		//preview2DButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
		preview2DButton.setId("bgz_well_previewButton");
		Image preview2d = new Image("icons" + File.separator + "24x" + File.separator + "preview_2D_24.png", "preview 2D");
		preview2DButton.setIcon(preview2d);
		
		preview3DButton = new Button();
		//preview3DButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
		preview3DButton.setId("bgz_well_previewButton");
		Image preview3d = new Image("icons" + File.separator + "24x" + File.separator + "preview_3D_24.png", "preview 3D");
		preview3DButton.setIcon(preview3d);

		deviationLayout.add(wellPathLabel, wellpathComboBox, calculateButton, preview2DButton, preview3DButton);

		HorizontalLayout surveyLayout = new HorizontalLayout();
		surveyLabel = new Label("Survey File");
		surveyLabel.setWidth("110px");
		surveyLabel.getElement().getStyle().set("margin-top", "20px");

		surveyTextField = new TextField();

		surveyTextField.setWidthFull();
		surveyTextField.getStyle().set("margin-top", "10px");

		importbutton = new Button();
		importbutton.getStyle().set("margin-top", "15px");
		Image surveyimage = new Image("images" + File.separator + "import24.png", "add");
		importbutton.setIcon(surveyimage);
		surveyimage.setWidth("20px");
		surveyLayout.add(surveyLabel, surveyTextField, importbutton);
		surveyLayout.setWidthFull();
		surveyLayout.expand(surveyTextField);

		surveyLabel.setEnabled(false);
		surveyTextField.setEnabled(false);
		// importbutton.setEnabled(false);
		surveyLayout.add(surveyLabel, surveyTextField, importbutton);

		deviationgrid.addColumn(Blgz_Deviation_Info::getmD).setHeader("MD(m)").setAutoWidth(true);
		deviationgrid.addColumn(Blgz_Deviation_Info::getiNc).setHeader("INC(dega)").setAutoWidth(true);
		deviationgrid.addColumn(Blgz_Deviation_Info::getaZM).setHeader("AZM(dega)").setAutoWidth(true);
		deviationgrid.addColumn(Blgz_Deviation_Info::gettVD).setHeader("TVD(m)").setAutoWidth(true);
		deviationgrid.addColumn(Blgz_Deviation_Info::getnS).setHeader("N-S(m)").setAutoWidth(true);
		deviationgrid.addColumn(Blgz_Deviation_Info::geteW).setHeader("E-W(m)").setAutoWidth(true);
		deviationgrid.setSizeFull();
		// deviationgrid.setClassName("deviation-grid");
		deviationgrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		deviationgrid.setHeight("300px");

		HorizontalLayout deviationActionLayout = new HorizontalLayout();

		// VerticalLayout basinLayout = new VerticalLayout();
		// HorizontalLayout basinButtonsLayout = new HorizontalLayout();

		deviationAddButton = new Button();
		deviationAddButton.setId("bgz_well_deviationAdd");
		Image deviationAddimage = new Image("icons" + File.separator + "16x" + File.separator + "add-16x.png", "add");
		deviationAddButton.getElement().setAttribute("title", "Add");
		deviationAddButton.setIcon(deviationAddimage);

		deviationEditButton = new Button();
		deviationEditButton.setId("bgz_well_deviationEdit");
		Image deviationEditimage = new Image("icons" + File.separator + "16x" + File.separator + "edit_16.png", "Edit");
		deviationEditButton.getElement().setAttribute("title", "Edit ");

		deviationEditButton.setIcon(deviationEditimage);

		deviationRemoveButton = new Button();
		deviationRemoveButton.setId("bgz_well_deviationRemove");
		Image deviationRemoveimage = new Image("icons" + File.separator + "16x" + File.separator + "delete16.png",
				"delete");
		deviationRemoveButton.getElement().setAttribute("title", "delete ");

		deviationRemoveButton.setIcon(deviationRemoveimage);

		deviationAddButton.setEnabled(false);
		deviationEditButton.setEnabled(false);
		deviationRemoveButton.setEnabled(false);

		deviationActionLayout.add(deviationAddButton, deviationEditButton, deviationRemoveButton);

		HorizontalLayout mainActionLayout = new HorizontalLayout();

		saveButton = new Button("Save");
		saveButton.setId("bgz_well_saveButton");
		saveButton.setClassName("GS_Color_code");
		saveButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);

		clearButton = new Button("Clear");
		clearButton.setId("bgz_well_clearButton");

		mainActionLayout.add(saveButton, clearButton);
		mainActionLayout.getStyle().set("padding-bottom", "15px");

		nonPlotLayout = new VerticalLayout();
		nonPlotLayout.add(deviationLayout, surveyLayout, deviationgrid, deviationActionLayout, mainActionLayout);
		nonPlotLayout.setSizeFull();
		add(nonPlotLayout);

		setAlignSelf(Alignment.CENTER, deviationActionLayout);
		setAlignSelf(Alignment.BASELINE, mainActionLayout);

		importbutton.addClickListener(event -> {
			try {
				importbuttonpopup();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		});
		
		//test();
	}
	
	private void plot2D(JsonObject data)
	{
		if (data == null)
		{
			return;
		}
		
		select2d = true;
		select3d = false;
		
		UI.getCurrent().getElement().executeJs("deviationPlotly($0, $1)", data, "deviation_survey");
	}

	private void plot3D(JsonObject data)
	{
		if (data == null)
		{
			return;
		}
		
		select2d = false;
		select3d = true;
		
		UI.getCurrent().getElement().executeJs("deviationPlotly3D($0, $1)", data, "deviation_survey");
	}
	
	protected void plot()
	{
		if (select2d)
		{
			plot2D(getGridData());
		}
		else if (select3d)
		{
			plot3D(getGridData());
		}
		
	}
	
	private void importbuttonpopup() throws Exception {

		directionSurveyImportPopup equationWindow = new directionSurveyImportPopup(deviationgrid);

		equationWindow.open();

	}

	private void deviationValidationwithSurvayFiles() {
		/**
		 * SG - 03-28-2020 Handle null pointer for wellpath combo box since the combobox
		 * does not set the default value.
		 */
//		String wellPathTemp = wellpathComboBox.getValue().toString() == null ?
//		"Vertical":wellpathComboBox.getValue().toString();
		wellpathComboBox.addValueChangeListener(event -> {
//			wellPathComboBoxValue = wellPathTemp;
			wellPathComboBoxValue = wellpathComboBox.getValue();

			if (wellPathComboBoxValue.equalsIgnoreCase("Vertical")) {
				importbutton.setEnabled(false);
				surveyLabel.setEnabled(false);

				calculateButton.setEnabled(false);
				surveyTextField.setEnabled(false);
				surveyTextField.setValue("");
				deviationAddButton.setEnabled(false);
				deviationEditButton.setEnabled(false);
				deviationRemoveButton.setEnabled(false);
				deviationgrid.setEnabled(false);
			} else if (wellPathComboBoxValue.equalsIgnoreCase("Deviated")) {
				surveyLabel.setEnabled(true);
				surveyTextField.setEnabled(true);
				deviationgrid.setEnabled(true);
				calculateButton.setEnabled(true);
				if (!wellTypePath.equalsIgnoreCase("")) {
					surveyTextField.setValue(wellTypePath);
				}
				surveyTextField.setEnabled(true);
				// surveyTextBox.setValue("");
				deviationAddButton.setEnabled(true);
				deviationEditButton.setEnabled(true);
				deviationRemoveButton.setEnabled(true);
				deviationgrid.setEnabled(true);
				importbutton.setEnabled(true);
				;

			}
		});

	}

	private void addDeviationButton() {
		// TODO Auto-generated method stub
		deviationAddButton.addClickListener(event -> {

			updateSelectedDialog(false);
		});
	}

	public void editDeviationButtonClickAction() {
		deviationEditButton.addClickListener(event -> {
			if (deviationgrid.asSingleSelect().getValue() == null) {
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

		Blgz_Deviation_Window deviationWindow = new Blgz_Deviation_Window(deviationgrid, editFlag);
		deviationWindow.open();

	}

	private JsonObject getGridData()
	{
		ListDataProvider<Blgz_Deviation_Info> dataProvider = (ListDataProvider<Blgz_Deviation_Info>) deviationgrid
				.getDataProvider();
		List<Blgz_Deviation_Info> inputs = (List<Blgz_Deviation_Info>) dataProvider.getItems();
		ArrayList<ArrayList<String>> allDeviationData = new ArrayList<>();
		for (Blgz_Deviation_Info userInput : inputs) {
			ArrayList<String> deviationData = new ArrayList<>();
			deviationData.add(userInput.getmD());
			deviationData.add(userInput.getiNc());
			deviationData.add(userInput.getaZM());
			deviationData.add(userInput.gettVD());
			deviationData.add(userInput.getnS());
			deviationData.add(userInput.geteW());
			allDeviationData.add(deviationData);
		}

		if (allDeviationData.size()==0)
		{
			return null;
		}
		
		Deviation_MetaDataInfo meta_data = new Deviation_MetaDataInfo();
		String[] columns_str = { "Measured_Depth", "Inclination", "Azimuth", "TVD", "Easting", "Northing" };
		String[] units_str = { "m", "dega", "dega", "m", "m" };

		
		//allData.setMeta_data(meta_data);
		
		//JsonObject main = Json.parse();
		
		
		//Need to get wellbore info somehow
		//System.out.println(wellboreInfo.getWellboreID());
		
		JsonArray md = Json.createArray();
		JsonArray incl = Json.createArray();
		JsonArray azm = Json.createArray();
		JsonArray tvd = Json.createArray();
		JsonArray east = Json.createArray();
		JsonArray north = Json.createArray();
		for (int i = 0; i < allDeviationData.size(); i++) {
			md.set(i, allDeviationData.get(i).get(0));
			incl.set(i, allDeviationData.get(i).get(1));
			azm.set(i, allDeviationData.get(i).get(2));
			if(allDeviationData.get(i).get(3) != null) {
			tvd.set(i, allDeviationData.get(i).get(3));
			east.set(i, allDeviationData.get(i).get(4));
			north.set(i, allDeviationData.get(i).get(5));
			} else {
				return null;
			}
		}
//		for (int i=0; i<allDeviationData.size(); i++)
//		{
//			md.set(i, allDeviationData.get(i).get(0));
//			incl.set(i, allDeviationData.get(i).get(1));
//			azm.set(i, allDeviationData.get(i).get(2));
//			tvd.set(i, allDeviationData.get(i).get(3));
//			east.set(i, allDeviationData.get(i).get(4));
//			north.set(i, allDeviationData.get(i).get(5));
//		}
//		
		JsonObject logs = Json.createObject();
		logs.put("MD", md);
		logs.put("Inclination", incl);
		logs.put("Azimuth", azm);
		logs.put("TVD", tvd);
		logs.put("Easting", east);
		logs.put("Northing", north);
		
		JsonArray units = Json.createArray();
		units.set(0, "m");
		units.set(1, "dega");
		units.set(2, "dega");
		units.set(3, "m");
		units.set(4, "m");
		units.set(5, "m");
		
		logs.put("Units", units);
		
		return logs;
	}
	
	private void saveDeviationDataEvent() {
		// TODO Auto-generated method stub
		saveButton.addClickListener(event -> {
			ListDataProvider<Blgz_Deviation_Info> dataProvider = (ListDataProvider<Blgz_Deviation_Info>) deviationgrid
					.getDataProvider();
			List<Blgz_Deviation_Info> inputs = (List<Blgz_Deviation_Info>) dataProvider.getItems();
			ArrayList<ArrayList<String>> allDeviationData = new ArrayList<>();
			for (Blgz_Deviation_Info userInput : inputs) {
				ArrayList<String> deviationData = new ArrayList<>();
				deviationData.add(userInput.getmD());
				deviationData.add(userInput.getiNc());
				deviationData.add(userInput.getaZM());
				deviationData.add(userInput.gettVD());
				deviationData.add(userInput.getnS());
				deviationData.add(userInput.geteW());
				allDeviationData.add(deviationData);
			}

			WellboreListInfo wellboreInfo = View_Update.getWellboreInfo();
			Long wellboreID = wellboreInfo.getWellboreID();

			Deviation_PojoClass allData = new Deviation_PojoClass();
			allData.setData(allDeviationData);

			Deviation_MetaDataInfo meta_data = new Deviation_MetaDataInfo();
			String[] columns = { "Measured_Depth", "Inclination", "Azimuth", "TVD", "Easting", "Northing" };
			String[] units = { "m", "dega", "dega", "m", "m" };
			meta_data.setColumns(columns);
			meta_data.setUnits(units);

			allData.setMeta_data(meta_data);

			JSONObject jsonObject = new JSONObject(allData);
			int ifexist = new WellboreQueries().wellboreExist_ByIDDeviationView(wellboreID);
			
			if (ifexist == 0) {
			try {
				new WellboreQueries().insertDeviationData(wellboreID, jsonObject);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			} else {
	
				try {
					new WellboreQueries().updateDataInDeviationView(wellboreID, jsonObject);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});
	}

	private void deleteDeviationRowClickAction() {
//		// TODO Auto-generated method stub
		deviationRemoveButton.addClickListener(event -> {

			Blgz_Deviation_Info selectedGridRow = deviationgrid.asSingleSelect().getValue();
			if (deviationgrid.asSingleSelect().getValue() == null) {
	PB_Progress_Notification notification = new PB_Progress_Notification();
	String selectaRow = PetrahubNotification_Utilities.getInstance().selectArowFromgrid();	
				notification.setText(selectaRow);
				notification.open();
				notification.setDuration(3000);
			} else {
				ListDataProvider<Blgz_Deviation_Info> dataProvider = (ListDataProvider<Blgz_Deviation_Info>) deviationgrid
						.getDataProvider();
				dataProvider.getItems().remove(selectedGridRow);
				dataProvider.refreshAll();
				deviationgrid.getDataProvider().refreshAll();
				deviationgrid.deselect(selectedGridRow);

			}

		});
	}
	public void setDataToUI() {
		
		Deviation_PojoClass deviationObject = null;
		WellboreListInfo wellboreInfo = View_Update.getWellboreInfo();
		Long wellboreID = wellboreInfo.getWellboreID();
//		Long wellboreID= (long) 10450864;
		
		try {
			deviationObject = new WellboreQueries().getDeviationData(wellboreID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (deviationObject!=null) {
			ArrayList<ArrayList<String>> data = deviationObject.getData();
			ArrayList<Blgz_Deviation_Info> deviationDataList = new ArrayList<>();
			for (ArrayList<String> deviationData : data) {
				
				Blgz_Deviation_Info blgzDeviationObject = new Blgz_Deviation_Info();
				blgzDeviationObject.setmD(deviationData.get(0));
				blgzDeviationObject.setiNc(deviationData.get(1));
				blgzDeviationObject.setaZM(deviationData.get(2));
				blgzDeviationObject.settVD(String.format("%.6s",deviationData.get(3)));
				blgzDeviationObject.setnS(String.format("%.5s",deviationData.get(4)));
				blgzDeviationObject.seteW(String.format("%.6s",deviationData.get(5)));
				deviationDataList.add(blgzDeviationObject);
				
			}
			
			deviationgrid.setItems(deviationDataList);
		}
		
	}
	
	private void preview2DClickAction() {
//		// TODO Auto-generated method stub
		preview2DButton.addClickListener(event -> {
			JsonObject data = getGridData();
			
			if (data == null)
			{
				PB_Progress_Notification notification = new PB_Progress_Notification();
				String pressCalulate = PetrahubNotification_Utilities.getInstance().pressCalculateButton();
				UI.getCurrent().access(() -> {
					notification.setImage("info");
					notification.setText(pressCalulate);
					notification.setDuration(3000);
					notification.open();
				});
			}
			else
			{
				VerticalLayout plotLayout = new VerticalLayout();
				HorizontalLayout mainLayout = new HorizontalLayout();
				
				mainLayout.add(nonPlotLayout, plotLayout);
				mainLayout.setSizeFull();
				
				plotLayout.setMinHeight("650px");
				plotLayout.setMinWidth("50%");
				
				plotLayout.setId("deviation_survey");
				
				nonPlotLayout.setHeight("50%");
				nonPlotLayout.setMinWidth("40%");
				this.removeAll();
				
				plot2D(data);
				
				add(mainLayout);
			}
			
			//mainLayout.setElement(component, element);
		});
	}
	
	private void preview3DClickAction() {
//		// TODO Auto-generated method stub
		preview3DButton.addClickListener(event -> {
			JsonObject data = getGridData();
			
			if (data == null)
			{
				PB_Progress_Notification notification = new PB_Progress_Notification();
				String pressCalulate = PetrahubNotification_Utilities.getInstance().pressCalculateButton();
				UI.getCurrent().access(() -> {
					notification.setImage("info");
					notification.setText(pressCalulate);
					notification.setDuration(3000);
					notification.open();
				});
			}
			else
			{
				VerticalLayout plotLayout = new VerticalLayout();
				HorizontalLayout mainLayout = new HorizontalLayout();
				
				mainLayout.add(nonPlotLayout, plotLayout);
				mainLayout.setSizeFull();
				
				plotLayout.setMinHeight("650px");
				plotLayout.setMinWidth("50%");
				
				plotLayout.setId("deviation_survey");
				
				nonPlotLayout.setHeight("50%");
				nonPlotLayout.setMinWidth("40%");
				this.removeAll();
				
				plot3D(data);
				
				add(mainLayout);
			}
		});
	}

	private void calculateButtonClickAction() {
		calculateButton.addClickListener(event -> {
			String wellPathValue = wellpathComboBox.getValue().toString();
			Blgz_Calculation_Window_UI confirmWindow = new Blgz_Calculation_Window_UI(deviationgrid, wellPathValue);

			confirmWindow.open();

		});
	}

	/*
	 * method to clear the fields & tables
	 */
	private void clearButtonClickAction() {

		clearButton.addClickListener(event -> {
			/**
			 * Remove all the columns and add again with updated units
			 */

//					ListDataProvider<Blgz_Deviation_Info> dataProvider = (ListDataProvider<Blgz_Deviation_Info>) deviationgrid
//						                                                  	.getDataProvider();
//					List<Blgz_Deviation_Info> deviationInfoList = new Blgz_Deviation_Provider().getDeviationData(selectedWellbore, dataProvider);

//		            dataProvider.getItems().removeAll(deviationInfoList);
//		            dataProvider.refreshAll();
//		            deviationgrid.setItems();
//		            surveyTextField.clear();

		});
	}

}

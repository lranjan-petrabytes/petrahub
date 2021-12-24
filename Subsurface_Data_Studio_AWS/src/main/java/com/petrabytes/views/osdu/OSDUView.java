package com.petrabytes.views.osdu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.MainLayout;
import com.petrabytes.views.UI_Update;
import com.petrabytes.views.basin.BasinViewGridInfo;
import com.petrabytes.views.osdudata.OsduDataInfo;
import com.petrabytes.views.wellbore.Blgz_Calculation_Window_UI;
import com.petrabytes.views.wellbore.Blgz_Deviation_Info;
import com.petrabytes.views.wellbore.Deviation_MetaDataInfo;
import com.petrabytes.views.wells.WellListInfo;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;

@Route(value = "osdu", layout = MainLayout.class)
@PageTitle("OSDU Wells")
public class OSDUView extends HorizontalLayout {

	final static String title = "osdu";

	private PasswordField apiToken = new PasswordField();
	private ComboBox<String> basinCombobox = new ComboBox<>();
	private ComboBox<String> wellCombobox = new ComboBox<>();
	private ComboBox<String> wellboreCombobox = new ComboBox<>();

	private FlexLayout mainLayot = new FlexLayout();
	private VerticalLayout vLayout = new VerticalLayout();
	private PB_FlexLayout mLayout = new PB_FlexLayout();
	private HorizontalLayout mainPlotLayot = new HorizontalLayout();
	private Button button = new Button(VaadinIcon.ANGLE_DOUBLE_LEFT.create());
	private Button searchButton = new Button();
	private Button plotButton = new Button();
	private Button calculateButton = new Button();
	private Button preview2DButton = new Button();
	private Button preview3DButton = new Button();
	private boolean select2d;
	private boolean select3d;
	private SortDirection aSSENDINGDirection = SortDirection.ASCENDING;

 	
	VerticalLayout nonPlotLayout = new VerticalLayout();
 
	Grid<OSDUWellboreTrajectoryInfo> wellboreTrajectoryGrid = new Grid<>();
	Grid<OSDUWellLogsInfo> wellLogsGrid = new Grid<>();

	private Grid<Blgz_Deviation_Info> deviationgrid = new Grid<>();

	private Div plotLayout = new Div();

	public OSDUView() {

		this.setSizeFull();
		this.setSpacing(false);
		this.setPadding(false);
		vLayout.setSpacing(false);
		vLayout.setPadding(false);
		vLayout.setSizeUndefined();
		mainLayot.setSizeFull();

		mLayout.setSizeFull();
		Label label = new Label("Settings");
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

		apiToken.setValue(
				"Bearer eyJraWQiOiJ3c0tYMlp1K2NXS0FsMklWVHhaXC82NGJUYzJKXC93cjJ5dWpWa0FLNGFOQVU9IiwiYWxnIjoiUlMyNTYifQ.eyJvcmlnaW5fanRpIjoiZjUyOTAwMDUtZTYyNS00MTk5LWJjYTYtM2MxMWY4N2NhMTc3Iiwic3ViIjoiNDQ2ODg3ZTQtMGM2Yy00NGNjLTlmNjYtZWZmOGQ1NWVhNWUyIiwiZXZlbnRfaWQiOiJiNzIyNGJlOC01MTc4LTQyYmUtOTk4NS04MTJkMjQxNWVhMzUiLCJ0b2tlbl91c2UiOiJhY2Nlc3MiLCJzY29wZSI6ImF3cy5jb2duaXRvLnNpZ25pbi51c2VyLmFkbWluIiwiYXV0aF90aW1lIjoxNjM0MDIzODEyLCJpc3MiOiJodHRwczpcL1wvY29nbml0by1pZHAudXMtZWFzdC0xLmFtYXpvbmF3cy5jb21cL3VzLWVhc3QtMV9HSDY2ejVnVnAiLCJleHAiOjE2MzQxMzk3NzcsImlhdCI6MTYzNDEyNTM3NywianRpIjoiNWM2NTQ1YWUtMzQ3ZS00N2UwLWE5NTAtY2M4YmZhNDJkNGMwIiwiY2xpZW50X2lkIjoiZWVnNmtxZ3JzNDkwZXQ4M2gxZThnbmVyaCIsInVzZXJuYW1lIjoic2FzaGlndW50dXJ1QHBldHJhYnl0ZXMuY29tIn0.ZuixJlE81Yk9ZrIdWBSHYDO4IBJ63Ssp2smlDfocAVxDzraxJ6gfxZjzIp-IIEkBbDayYq234t987C5wCnGiBlaOPobC17X9bKOxZ1eSgVzhlASFtmWpOU_VGyD9qMckT-GlPubiFkDA1-Dd7lS8CshvHv80XGQ03OV3VQhF6PZdX1Fwq32UdMgPf-HSVggcaC0SPiG90nNg2HuMd3Gnn5sAcHK494LTK_nc1TmVHzyf0TKmpnI-Xf4I1jXcHSb6rzcTY1QGKwWlkDkLSpKYj3isaOGhggzL5QoiIICpx0OK0ezy4s7ILutRUumNCwb7jZoh1L-7UBMetZ5cNUPTMQ");
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
		this.add(mainLayot, vLayout);

		this.expand(plotLayout);

		createPlotLayout();
		createEditorLayout();

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
			UI.getCurrent().getElement().executeJs("reSize($0)", "realdata_input");
		});
		
		}

	}

	private void createPlotLayout() {
		// TODO Auto-generated method stub
		deviationgrid.addColumn(Blgz_Deviation_Info::getmD).setHeader("MD(m)").setAutoWidth(true);
		deviationgrid.addColumn(Blgz_Deviation_Info::getiNc).setHeader("INC(dega)").setAutoWidth(true);
		deviationgrid.addColumn(Blgz_Deviation_Info::getaZM).setHeader("AZM(dega)").setAutoWidth(true);
		deviationgrid.addColumn(Blgz_Deviation_Info::gettVD).setHeader("TVD(m)").setAutoWidth(true);
		deviationgrid.addColumn(Blgz_Deviation_Info::getnS).setHeader("N-S(m)").setAutoWidth(true);
		deviationgrid.addColumn(Blgz_Deviation_Info::geteW).setHeader("E-W(m)").setAutoWidth(true);

		deviationgrid.setSizeFull();
		deviationgrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		deviationgrid.setHeightFull();

		calculateButton.setId("bgz_well_calculateButton");
		Image calculateimage = new Image("images" + File.separator + "calculator_24.png", "add");
		calculateButton.setIcon(calculateimage);

		preview2DButton = new Button();
	//	preview2DButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
		preview2DButton.setId("bgz_well_previewButton");
		Image preview2d = new Image("icons" + File.separator + "24x" + File.separator + "preview_2D_24.png", "preview 2D");
		preview2DButton.setIcon(preview2d);
		preview3DButton = new Button();
	//	preview3DButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
		preview3DButton.setId("bgz_well_previewButton");
		Image preview3d = new Image("icons" + File.separator + "24x" + File.separator + "preview_3D_24.png", "preview 3D");
		preview3DButton.setIcon(preview3d);
		HorizontalLayout plotButtonLayout = new HorizontalLayout();
		plotButtonLayout.setClassName("w-full flex-wrap py-s px-l");
		plotButtonLayout.getStyle().set("padding-left", "0");
		plotButtonLayout.setSpacing(true);

		plotButtonLayout.add(calculateButton, preview2DButton, preview3DButton);

		nonPlotLayout.add(plotButtonLayout, deviationgrid);
//		mainPlotLayot.add(nonPlotLayout);
		mainLayot.add(nonPlotLayout);
	}

	private void createEditorLayout() {
		// TODO Auto-generated method stub
		Div editorLayoutDiv = new Div();
		editorLayoutDiv.setHeightFull();
		editorLayoutDiv.setClassName("flex flex-col");
		editorLayoutDiv.setWidth("300px");

		wellboreTrajectoryGrid.addColumn(OSDUWellboreTrajectoryInfo::getName).setHeader("Name").setAutoWidth(true);
		wellboreTrajectoryGrid.addColumn(OSDUWellboreTrajectoryInfo::getDataset).setHeader("Dataset")
				.setAutoWidth(true);
		wellboreTrajectoryGrid.addColumn(OSDUWellboreTrajectoryInfo::getTopMD).setHeader("Top MD").setAutoWidth(true);
		wellboreTrajectoryGrid.addColumn(OSDUWellboreTrajectoryInfo::getBottomMD).setHeader("Bottom MD")
				.setAutoWidth(true);

		wellboreTrajectoryGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		wellboreTrajectoryGrid.setMinHeight("150px");
		wellboreTrajectoryGrid.setHeightByRows(true);

		wellLogsGrid.addColumn(OSDUWellLogsInfo::getName).setHeader("Name").setAutoWidth(true);
		wellLogsGrid.addColumn(OSDUWellLogsInfo::getDataset).setHeader("Dataset").setAutoWidth(true);

		wellLogsGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		wellLogsGrid.setMinHeight("150px");
		wellLogsGrid.setHeightByRows(true);

		VerticalLayout wellLogsVerticalLayout = new VerticalLayout();
		wellLogsVerticalLayout.setHeightFull();
		FormLayout searchFormLayout = new FormLayout();

		searchFormLayout.addFormItem(apiToken, "API Token");
		apiToken.setRevealButtonVisible(false);
		searchFormLayout.addFormItem(basinCombobox, "Basin");
		searchFormLayout.addFormItem(wellCombobox, "Well");
		searchFormLayout.addFormItem(wellboreCombobox, "Wellbore");

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setClassName("w-full flex-wrap py-s px-l");
		buttonLayout.getStyle().set("padding-left", "0");
		buttonLayout.setSpacing(true);
		Image searchImage = new Image("images" + File.separator + "search24.png", "Search");
		searchButton.setIcon(searchImage);
		searchButton.getElement().setAttribute("title", "Search");

		Image plotImage = new Image("images" + File.separator + "display24.png", "plot");
		plotButton.setIcon(plotImage);
		plotButton.getElement().setAttribute("title", "Display");

		buttonLayout.add(searchButton, plotButton);

		editorLayoutDiv.add(wellLogsVerticalLayout);

		mLayout.add(editorLayoutDiv);
		wellLogsVerticalLayout.add(searchFormLayout, buttonLayout, wellboreTrajectoryGrid);

		select2d = false;
		select3d = false;

		apiTokenEvent();
		basinComboboxEvent();
		wellComboboxEvent();
		searchButtonEvent();
		plotButtonEvent();
		calculateButtonClickAction();
		preview2DClickAction();
		preview3DClickAction();
	}

	private void apiTokenEvent() {
		// TODO Auto-generated method stub
		apiToken.addBlurListener(event -> {
			basinCombobox.clear();
		});
	}

	private void preview2DClickAction() {
//		// TODO Auto-generated method stub
	//	if(userclick) {
		preview2DButton.addClickListener(event -> {
			
			JsonObject data = getGridData();
     
             
			if (data == null) {
				PB_Progress_Notification notification = new PB_Progress_Notification();

				UI.getCurrent().access(() -> {
					notification.setImage("info");
					notification.setText("Add Deviation survey data or press Calculate Button");
					notification.setDuration(3000);
					notification.open();
				});
			} else {
				VerticalLayout trajPlotLayout = new VerticalLayout();
//				trajPlotLayout.setMinHeight("650px");
//				trajPlotLayout.setMinWidth("50%");
				trajPlotLayout.setId("deviation_survey");
				
				plot2D(data);
				mainLayot.removeAll();
				mainLayot.add(nonPlotLayout, trajPlotLayout);
//				}  else {
//	            	PB_Progress_Notification notification = new PB_Progress_Notification();
//	            	notification.setImage("info");
//					notification.setText("Plese First click the calculator button!");
//					notification.open();
//					notification.setDuration(3000);
//	            }
			
			
			
			}
			// mainLayout.setElement(component, element);
		});
	}

	private void preview3DClickAction() {
//		// TODO Auto-generated method stub
		

		preview3DButton.addClickListener(event -> {
			JsonObject data = getGridData();
//			if (data == null && allDeviationData.size() != 0) {
//				PB_Progress_Notification notification = new PB_Progress_Notification();
//            	notification.setImage("info");
//				notification.setText("Plese First click the calculator button!");
//				notification.open();
//				notification.setDuration(3000);
//			} else
			if (data == null) {
				PB_Progress_Notification notification = new PB_Progress_Notification();

				UI.getCurrent().access(() -> {
					notification.setImage("info");
					notification.setText("Add Deviation survey data or press Calculate Button ");
					notification.setDuration(3000);
					notification.open();
				});
			} else 
			   {  
				VerticalLayout trajPlotLayout = new VerticalLayout();
//				trajPlotLayout.setMinHeight("650px");
//				trajPlotLayout.setMinWidth("50%");
				trajPlotLayout.setId("deviation_survey");
				
				plot3D(data);
				mainLayot.removeAll();
				mainLayot.add(nonPlotLayout, trajPlotLayout);
//				  } else {
//		            	PB_Progress_Notification notification = new PB_Progress_Notification();
//		            	notification.setImage("info");
//						notification.setText("Plese First click the calculator button!");
//						notification.open();
//						notification.setDuration(3000);
		            }
			
          
		});
	}

	private void calculateButtonClickAction() {
		calculateButton.addClickListener(event -> {
			
		
			String wellPathValue = "Deviated";
			Blgz_Calculation_Window_UI confirmWindow = new Blgz_Calculation_Window_UI(deviationgrid, wellPathValue);

			confirmWindow.open();
		

		});
	}

	private void plotButtonEvent() {
		// TODO Auto-generated method stub
		plotButton.addClickListener(event -> {
			String tokenID = apiToken.getValue();
			OSDUWellboreTrajectoryInfo trajectoryDataInfo = null;
			for (OSDUWellboreTrajectoryInfo wbTrajectory : wellboreTrajectoryGrid.getSelectedItems()) {
				trajectoryDataInfo = wbTrajectory;
			}
			String datasetName = trajectoryDataInfo.getDataset();

			try {
				ArrayList<Blgz_Deviation_Info> wellboreTrajectoryData = new OSDUQueries()
						.queryWellboreTrajectoryData(datasetName, tokenID);
				deviationgrid.setItems(wellboreTrajectoryData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	private void searchButtonEvent() {
		// TODO Auto-generated method stub
		searchButton.addClickListener(event -> {
			String tokenID = apiToken.getValue();
			String selectedWellboreId = wellboreCombobox.getValue();
			try {
				ArrayList<OSDUWellboreTrajectoryInfo> wellboreTrajectories = new OSDUQueries()
						.queryWellboreTrajectory(selectedWellboreId, tokenID);
				wellboreTrajectoryGrid.setItems(wellboreTrajectories);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

//			try {
//				ArrayList<OSDUWellLogsInfo> wellLogs = new OSDUQueries().queryWellLogs(selectedWellboreId, tokenID);
//				wellLogsGrid.setItems(wellLogs);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}

		});
	}

	private void wellComboboxEvent() {
		// TODO Auto-generated method stub
		wellCombobox.addValueChangeListener(event -> {
			String tokenID = apiToken.getValue();
			String selectedWellId = wellCombobox.getValue();
			try {
				ArrayList<String> wellboreList = new OSDUQueries().queryWellboreInformation(selectedWellId, tokenID);
				Collections.sort(wellboreList); 
				wellboreCombobox.setItems(wellboreList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	private void basinComboboxEvent() {
		// TODO Auto-generated method stub
		ArrayList<String> basinNames = new ArrayList<>();
		basinNames.add("OSDU Basin");

		basinCombobox.setItems(basinNames);

		basinCombobox.addValueChangeListener(event -> {
			String tokenID = apiToken.getValue();
			try {
				ArrayList<String> wellList = new OSDUQueries().queryWellInformation(tokenID);
			
				//ListDataProvider<String>	welllistdata = (ListDataProvider<String>) DataProvider.ofCollection((Collection)	 wellList);
				Collections.sort(wellList); 
				wellCombobox.setItems(wellList);;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}

	private JsonObject getGridData() {
		ListDataProvider<Blgz_Deviation_Info> dataProvider = (ListDataProvider<Blgz_Deviation_Info>) deviationgrid
				.getDataProvider();
		List<Blgz_Deviation_Info> inputs = (List<Blgz_Deviation_Info>) dataProvider.getItems();
		ArrayList<ArrayList<String>> allDeviationData = new ArrayList<>();
		for (Blgz_Deviation_Info userInput : inputs) {
			ArrayList<String> deviationData = new ArrayList<>();
		//	if(userInput.gettVD() !=null) {
			deviationData.add(userInput.getmD());
			deviationData.add(userInput.getiNc());
			deviationData.add(userInput.getaZM());
			deviationData.add(userInput.gettVD());
			deviationData.add(userInput.getnS());
			deviationData.add(userInput.geteW());
		////	}
		//	if (deviationData.get(3) !=null) {
			allDeviationData.add(deviationData);
			//}
			
		}

		if (allDeviationData.size() == 0) {
			return null;
		}
		
//		if (allDeviationData.size() <= 5) {
//			return null;
//		}
		
		
		Deviation_MetaDataInfo meta_data = new Deviation_MetaDataInfo();
		String[] columns_str = { "Measured_Depth", "Inclination", "Azimuth", "TVD", "Easting", "Northing" };
		String[] units_str = { "m", "dega", "dega", "m", "m" };

		// allData.setMeta_data(meta_data);

		// JsonObject main = Json.parse();

		// Need to get wellbore info somehow
		// System.out.println(wellboreInfo.getWellboreID());

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

	private void plot3D(JsonObject data) {
		if (data == null) {
			return;
		}

		select2d = false;
		select3d = true;

		UI.getCurrent().getElement().executeJs("deviationPlotly3D($0, $1)", data, "deviation_survey");
	}

	protected void plot() {
		if (select2d) {
			plot2D(getGridData());
		} else if (select3d) {
			plot3D(getGridData());
		}

	}

	private void plot2D(JsonObject data) {
		if (data == null) {
			return;
		}

		select2d = true;
		select3d = false;

		UI.getCurrent().getElement().executeJs("deviationPlotly($0, $1)", data, "deviation_survey");
	}

	public class PB_FlexLayout extends FlexLayout implements Focusable {

		public PB_FlexLayout() {
			this.setTabIndex(0);
		}
	}

}

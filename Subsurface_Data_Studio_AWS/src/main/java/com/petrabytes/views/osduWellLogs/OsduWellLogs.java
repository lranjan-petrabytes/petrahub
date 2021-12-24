package com.petrabytes.views.osduWellLogs;

import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;

import com.vaadin.flow.router.PageTitle;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.project.util.ProjectSettingsInfo;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.MainLayout;
import com.petrabytes.views.UI_Update;
import com.petrabytes.views.osdu.OSDUQueries;
import com.petrabytes.views.osdu.OSDUWellboreTrajectoryInfo;
import com.petrabytes.views.wellbore.Blgz_Deviation_Info;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSelectionModel;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.sristy.data.CurveInfo;
import com.sristy.parsers.LASKeywords;
import com.sristy.unitsystem.UOM;
import com.sristy.unitsystem.UOMRegistry;
import com.sristy.dataorg.DataSeries;
//
@Route(value = "osduwelllogs", layout = MainLayout.class)
@PageTitle("OSDU Well Logs")

public class OsduWellLogs extends HorizontalLayout {

	private Grid<OsduWellLogsInformation> headerGrid = new Grid<>();
	Grid<OsduWellLogsInformation> wellLogsGrid = new Grid<>();
	private VerticalLayout searchLayout = new VerticalLayout();
	private PasswordField apiToken = new PasswordField();
	private ComboBox<String> basinCombobox = new ComboBox<>();
	private ComboBox<String> wellCombobox = new ComboBox<>();
	private ComboBox<String> wellboreCombobox = new ComboBox<>();

	private FlexLayout mainLayot = new FlexLayout();
	private VerticalLayout vLayout = new VerticalLayout();
	private PB_FlexLayout mLayout = new PB_FlexLayout();
	private Button button = new Button(VaadinIcon.ANGLE_DOUBLE_LEFT.create());
	private Button searchButton = new Button();
	private Button plotButton = new Button();
	private Button settingsbutton = new Button();

	private Div plotLayout = new Div();
	ArrayList<OsduWellLogsInformation> populateWelllogs = new ArrayList<>();
	private OsduWellogsViewSettinginfo osduWelllogsSettings = new OsduWellogsViewSettinginfo();
	private List<OsduWellLogsInformation> traceListData = null;
/////////////////////////////////////////////////////Las Parser Variable////////////////////////
//	private IBlgz_Core_Interface mnemonicServie = new Blgz_Core_Service();
	private float[] nullvalues = { (float) -999.25, (float) -999.0, (float) -999.5, (float) -999.99, (float) -999.9,
			(float) -999.9, (float) -1000.0, (float) -999.9, (float) 999.9 };
	private Vector<CurveInfo> curveInfoList = new Vector<>();
	private Vector<CurveInfo> currentCurveInfoList = new Vector<>();
	private String dateString;
	private String timeString;
	private int asciiStartRow;
	private Date dateobj;
	private SimpleDateFormat formatter;
	private int lineCount;
	private String depthUnit = "m";
	private int totalNumberOfDataPoints;
	private boolean isFileWrapped = false;
	private boolean isNextLine = true;
	private boolean showDebugStatements;
	private String depthUnitCategory = UOMRegistry.LENGTH;
	private UOM[] selectedUnits;
	public OsduWellLogs() {
		this.setSizeFull();
		this.setSpacing(false);
		this.setPadding(false);
		vLayout.setSpacing(false);
		vLayout.setPadding(false);
		vLayout.setSizeUndefined();
		mainLayot.setSizeFull();

		mLayout.setSizeFull();
		Label label = new Label("Settings");
//			label.setWidth("26px");
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

		plotLayout.setSizeFull();
		plotLayout.setId("realdata_input");
		mainLayot.add(plotLayout);

	}

	private void createEditorLayout() {
		// TODO Auto-generated method stub
		Div editorLayoutDiv = new Div();
		editorLayoutDiv.setHeightFull();
		editorLayoutDiv.setClassName("flex flex-col");
		editorLayoutDiv.setWidth("300px");

		VerticalLayout wellLogsVerticalLayout = new VerticalLayout();
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

		Image settingsButtonimage = new Image("icons" + File.separator + "16x" + File.separator + "settings16.png",
				"Settings");
		settingsbutton.getElement().setAttribute("title", "Settings");
		settingsbutton.setIcon(settingsButtonimage);
		buttonLayout.add(searchButton, plotButton);

		headerGrid.addColumn(OsduWellLogsInformation::getLogname).setHeader("Log Name").setAutoWidth(true);
//			headerGrid.addColumn(WellLogsInfo::getMin).setHeader("Unit Category").setAutoWidth(true);
		headerGrid.addColumn(OsduWellLogsInformation::getUnit).setHeader("Unit").setAutoWidth(true);

		headerGrid.setSelectionMode(SelectionMode.MULTI);
		headerGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		headerGrid.setHeightByRows(true);
		headerGrid.setHeight("600px");

		wellLogsVerticalLayout.add(searchFormLayout, buttonLayout, headerGrid);
		editorLayoutDiv.add(wellLogsVerticalLayout);
		mLayout.add(editorLayoutDiv);

		List<String> selectedLogsName = osduWelllogsSettings.getSelectedLogs();
		if (selectedLogsName != null) {
			// _populateLogs(false);

			GridSelectionModel<OsduWellLogsInformation> msm = headerGrid.getSelectionModel();
			for (OsduWellLogsInformation singleData : traceListData) {
				if (selectedLogsName.contains(singleData.getLogname())) {
					msm.select(singleData);
				}
			}

			// _displayLogs(false);

		}
		apiTokenEvent();
		basinComboboxEvent();
		wellComboboxEvent();
		_searchEvent();
		plotButtonEvent();

	}

	private void apiTokenEvent() {
		// TODO Auto-generated method stub
		apiToken.addBlurListener(event -> {
			basinCombobox.clear();
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
				Collections.sort(wellList);
				wellCombobox.setItems(wellList);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

//	private void _plotEvent() {
//		// TODO Auto-generated method stub
//		plotButton.addClickListener(event -> {
//			// _displayLogs(true);
//			selectedlist();
//		});
//
//	}

	private void selectedlist() {
		String lognames = "";
		for (OsduWellLogsInformation row : headerGrid.getSelectedItems()) {
			String logName = row.getLogname();

			lognames += logName + ",";

		}
		if (lognames.endsWith(",")) {
			lognames = lognames.substring(0, lognames.length() - 1);
		}

		String[] myArray = lognames.split(",");
		List<String> myList = Arrays.asList(myArray);
		osduWelllogsSettings.setSelectedLogs(myList);

	}

	private void _searchEvent() {
		// TODO Auto-generated method stub
		searchButton.addClickListener(event -> {
			osduWelllogsSettings.setWellborename(wellboreCombobox.getValue());
			osdu_Populate_welllogs();
		});
	}

	private void _invokeDatabricksJob(String jobID, String wellboreID) {

		ExecutorService executor = Executors.newCachedThreadPool();
		PB_Progress_Notification notificatiion = new PB_Progress_Notification();
		UI ui = getUI().get();
		executor.submit(() -> {
			ui.access(() -> {

				notificatiion.setImage("info");
				notificatiion.setText("Rock Properties Calculation Job Submitted to Databricks...");
				notificatiion.open();

			});
		});
		String basepath = ((HttpServletRequest) VaadinRequest.getCurrent()).getRealPath(File.separator);

		String space = " ";
		String cmdString = "python" + space + basepath + File.separator + "python_scripts" + File.separator
				+ "Invoke_Jobs.py";

		cmdString += space + jobID + space + wellboreID;
		System.out.println(cmdString);
		_invokePythonScript(cmdString);

		ui.access(() -> {
			notificatiion.close();
		});
	}

	private void _invokePythonScript(String cmdString) {

		try {
			final String commandString = cmdString;
			System.out.println("commandString = " + commandString);
			Thread processThread = new Thread(new Runnable() {

				@Override
				public void run() {
					try {
						Runtime runtime = Runtime.getRuntime();
						Process process = runtime.exec(commandString);
						BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
						BufferedReader error = new BufferedReader(new InputStreamReader(process.getErrorStream()));
						String line = "";
						System.out.println("starting thread");
						while ((line = input.readLine()) != null) {
							System.out.println("from output: " + line);
							return;
						}
						while ((line = error.readLine()) != null) {
							System.out.println("from error: " + line);
						}
					} catch (IOException ex) {
						System.out.println(ex);
					}
				}
			});
			processThread.start();
			processThread.join();
			System.out.println("thread done");
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
	}

	private void osdu_Populate_welllogs() {
		String tokenID = apiToken.getValue();
		String selectedWellboreId = wellboreCombobox.getValue();
		try {
			 populateWelllogs = new OSDUQueries().queryWellLogs(selectedWellboreId, tokenID);
			System.out.println(populateWelllogs);

			headerGrid.setItems(populateWelllogs);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void plotButtonEvent() {
		// TODO Auto-generated method stub
		plotButton.addClickListener(event -> {
		
			String tokenID = apiToken.getValue();
			String datasetName = null;
			for(OsduWellLogsInformation row: populateWelllogs) {
				
					 datasetName = row.getDataset();
					 System.out.println(datasetName);
				}
			
		try {
			String[] result = 	new OSDUQueries().queryWellLogsData(datasetName, tokenID);
			List<CurveInfo>logsinfo = 	processHeaders(result);
				List<String >logs = new ArrayList();
				for(CurveInfo logs1: logsinfo) {
					logs.add(logs1.getMnemonic());
				}
				UOM depthUnit1 = UOMRegistry.createUOM(UOMRegistry.LENGTH);
				depthUnit1.setCurrentUnitSymbol(this.depthUnit);
				// To fix Import in TVD issue
				// Nov 09,2015
				depthUnit1.setCategory(UOMRegistry.LENGTH);
				depthUnit1.setType(this.depthUnitCategory);

				
				StringBuffer[] allLogs = readDataAndDumpToFile( result, logs);
	
				
				JsonArray logsArray1 = Json.createArray();
				for (int i = 0; i < allLogs.length; i++) {
					JsonObject mainObject = Json.createObject();
					mainObject.put("seriesNameArray", logs.get(i));
					JsonArray dataSeries = _addDataTODataSeriesFromLogFile(allLogs[i]);
					
					mainObject.put("seriesData", dataSeries);
                   
                     logsArray1.set(i, mainObject);
                    

				}
				
				System.out.println(logsArray1);
				UI ui = getUI().get();
				ui.access(() -> {
					UI.getCurrent().getElement().executeJs("wellLogsPlotly($0,$1,$2,$3)", logsArray1,
							"realdata_input", "m", getElement());
				

				});
				
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		

			
		});
	}
	
	private JsonArray _addDataTODataSeriesFromLogFile(StringBuffer bufferData) {
		
	
	
	
		
			JsonArray datasetArray = Json.createArray();
			int count = 0;
			String[] lines = bufferData.toString().split("\\n");
			for (String line : lines) {
				String[] data = line.split("\t");
				double depth = Double.NaN;
				double val = Double.NaN;
				try {
					// System.out.println(line);
					depth = Double.valueOf(data[0].trim());
					val = Double.valueOf(data[1].trim());
					// System.out.println(" Depth = "+depth +" Val = "+val);
				} catch (NumberFormatException numberFormatException) {

					continue;
				} catch (Exception e) {

					continue;
				}

			
				if (!Double.isNaN(depth) && !Double.isNaN(val)) {
					JsonObject xyObject = Json.createObject();
					xyObject.put("y", val);
					xyObject.put("x", depth);
					datasetArray.set(count, xyObject);

					count++;
				}

			}
			return datasetArray;
						
	
		
	}

	
	public Vector<CurveInfo> processHeaders(String[] result) {
		tracetelemetry();
		if (!curveInfoList.isEmpty()) {
			curveInfoList.clear();
		}
		Vector<CurveInfo> _curveInfoList = new Vector<>();
		if (!_curveInfoList.isEmpty()) {
			_curveInfoList.clear();
		}

		//String linefeed = null;
		try {
//			FileReader f = new FileReader(currentFile);
//			BufferedReader rf = new BufferedReader(f);

			boolean isASCII = false;
			boolean isCurveSection = false;
			boolean isWellSection = false;
			boolean isParameterSection = false;
			float depth = Float.NaN;
			int startIndex = 0; // used for wrapped files.

			float[] array = null;
			int index = 0;
			// while ((linefeed = rf.readLine()) != null &&
			// linefeed.trim().length() > 0) {
			for( String linefeed:result) {
				if (isComment(linefeed) || linefeed.trim().length() == 0) {
					continue;
				}
				if (isASCII) {
					// reached data section.
				} else {
					if (!isWellSection) {
						isWellSection = isWellSection(linefeed);
						if (!isWellSection) {
						}
					} else {
						if (isDate(linefeed)) {
							updateDate(linefeed);
						} else if (isTime(linefeed)) {
							this.updateTime(linefeed);
						}
					}
					// header info
					// get only well info
					if (!isCurveSection) {
						isCurveSection = isCurveSection(linefeed);
						if (!isCurveSection && !skip(linefeed)) {
						}
					} else if (isCurveSection) {
						// check if it is parameter section
						if (!isParameterSection) {
							isParameterSection = isParameterSection(linefeed);
							if (!isParameterSection && !skip(linefeed)) {
								// create curve info
								// curveList.add(getVariableType(linefeed));
								createCurveInfo(linefeed);
							}
						} else if (!skip(linefeed)) {
							// now in parameter section
						}
					}
				}
				String s1 = linefeed;
				if (!isASCII) {
					isASCII = isASCIISection(linefeed);
				} else {
					// reached data section
					asciiStartRow = index + 1;
					break;
				}
				index++;
			} // end while
				// close cleanly
		//	f.close();
		//	rf.close();
		} catch (Exception e) {
			// telemetry.trackException(e);
		//	logger.addLog(ExceptionUtils.getStackTrace(e),"ERROR");
			e.printStackTrace();
			// System.out.println("line feed is " + linefeed);
		}
		_curveInfoList = duplicateInlogList(curveInfoList);
		return _curveInfoList;

	}
	
	private void tracetelemetry() {
		// TODO Auto-generated method stub
//		TelemetryConfiguration.getActive().setInstrumentationKey("3c7354a9-a836-4cf6-a6ee-eed5701ea9c7");
		// telemetry.trackEvent("geomechanics");
		// telemetry.trackMetric("queueLength", 42.0);
		// telemetry.trackPageView("GeomechanicsReviewPage");
	}

	private boolean isComment(String linefeed) {
		String chars = linefeed.trim();
		char[] c = chars.toCharArray();
		if (c.length > 0) {
			if (c[0] == '#') {
				return true;
			}
		}
		return false;
	}

	private boolean isWellSection(String linefeed) {
		boolean isHeader = false;
		String chars = linefeed.trim().substring(0, 2);
		if (chars.equalsIgnoreCase(LASKeywords.LAS_WELL_SECTION_ID)) {
			isHeader = true;
		}
		return isHeader;
	}

	private boolean isDate(String linefeed) {
		boolean isdate = false;
		if (linefeed.trim().length() < 4) {
			return isdate;
		}
		String chars = linefeed.trim().substring(0, 4);

		if (chars.equalsIgnoreCase(LASKeywords.DATE_KEY_WORD)) {
			isdate = true;
		}
		return isdate;
	}

	private boolean isTime(String linefeed) {
		boolean istime = false;
		if (linefeed.trim().length() < 4) {
			return istime;
		}
		String chars = linefeed.trim().substring(0, 4);
		if (chars.equalsIgnoreCase(LASKeywords.TIME_KEY_WORD)) {
			istime = true;
		}
		return istime;
	}

	private void updateDate(String linefeed) {
		String s = linefeed.trim();
		int dotindex = s.indexOf(LASKeywords.LAS_SEPARATOR_DOT);
		if (dotindex != -1) {
			int index = s.indexOf(LASKeywords.LAS_SEPARATOR_COLON);
			if (index != -1) {
				String d = s.substring(dotindex + 1, index).trim();
				dateString = d;
			}
		}
	}

	private void updateTime(String linefeed) {
		String s = linefeed.trim();
		int dotindex = s.indexOf(LASKeywords.LAS_SEPARATOR_DOT);
		if (dotindex != -1) {
			int index = s.indexOf(LASKeywords.LAS_SEPARATOR_COLON);
			if (index != -1) {
				index = s.indexOf(LASKeywords.LAS_SEPARATOR_COLON, index + 1);

			}
			if (index != -1) {
				index = s.indexOf(LASKeywords.LAS_SEPARATOR_COLON, index + 1);
			}
			if (index != -1) {
				String d = s.substring(dotindex + 1, index).trim();
				timeString = d;
				String total = dateString + "." + timeString;
				try {
					dateobj = formatter.parse(total);
					// System.out.println("Date obj = " + dateobj);
				} catch (Exception e) {
				//	logger.addLog(ExceptionUtils.getStackTrace(e),"ERROR");
					e.printStackTrace();

					// telemetry.trackException(e);
					System.out.println("ERROR Parsing date " + total);
				}
			}
		}
	}

	private boolean isCurveSection(String linefeed) {
		boolean isCurveSection = false;
		String chars = linefeed.trim().substring(0, 2);
		if (chars.equalsIgnoreCase(LASKeywords.LAS_CURVE_SECTION_ID)) {
			isCurveSection = true;
		}
		return isCurveSection;
	}

	private boolean isParameterSection(String linefeed) {
		tracetelemetry();
		boolean isCurveSection = false;
		try {
			String chars = linefeed.trim().substring(0, 2);
			if (chars.equalsIgnoreCase(LASKeywords.LAS_PARAMETER_SECTION_ID)) {
				isCurveSection = true;
			}
		} catch (Exception e) {
		//	logger.addLog(ExceptionUtils.getStackTrace(e),"ERROR");
			e.printStackTrace();
			// telemetry.trackException(e);
		}
		return isCurveSection;
	}

	private boolean isASCIISection(String linefeed) {
		boolean isAscii = false;
		if (linefeed.trim().length() > 1) {
			String chars = linefeed.trim().substring(0, 2);
			if (chars.trim().equalsIgnoreCase(LASKeywords.LAS_ASCII_SECTION_ID)) {
				isAscii = true;
			}
			if (linefeed.startsWith(LASKeywords.LAS_ASCII_SECTION_ID)) {
				isAscii = true;
			}
		}
		return isAscii;
	}

	private boolean skip(String linefeed) {
		boolean shouldSkip = false;
		char[] c = linefeed.trim().toCharArray();
		if (c.length > 0) {
			if (c[0] == '~' || c[0] == '#') {
				shouldSkip = true;
			}
		}
		return shouldSkip;
	}

	/**
	 * creating curve information and adding to the curveInfoList
	 * 
	 * @param linefeed
	 */

	private void createCurveInfo(String linefeed) {
		// Vector<CurveInfo> curveInfoList = new Vector<>();
		// Vector<CurveInfo> currentCurveInfoList = new Vector<>();
		String s = linefeed.trim();
		int dotindex = s.indexOf(LASKeywords.LAS_SEPARATOR_DOT);
		if (dotindex != -1) {
			String name = s.substring(0, dotindex);
			int index = s.indexOf(LASKeywords.LAS_SEPARATOR_COLON);
			if (index != -1 && dotindex <= index) {
				String unitString = s.substring(dotindex + 1, index).trim();
				String s1 = s.substring(index + 1).trim();
				char[] c = s1.toCharArray();
				String desc = s1;
				StringBuilder buf = new StringBuilder();
				for (int i = 0; i < c.length; i++) {
					if (Character.isDigit(c[i])) {
						continue;
					} else if (c[0] == ' ') {
						continue;
					} else {
						buf.append(c[i]);
					}
				} // end for
				desc = buf.toString();
				// create curve info

				CurveInfo cf = new CurveInfo(name, desc, unitString);
				curveInfoList.addElement(cf);
				currentCurveInfoList.add(cf);

			}
		}
	}

	/**
	 * method to get log list from curve info
	 * 
	 * @param logList
	 * @return
	 */
	private Vector<CurveInfo> duplicateInlogList(Vector<CurveInfo> logList) {

		for (int i = 0, k = 0; i < logList.size(); i++) {
			for (int j = 0; j < logList.size(); j++) {
				if (i == j) {
					// i & j refer to same entry so do nothing
				} else if (logList.get(j).equals(logList.get(i))) {
					logList.set(j, logList.get(j));
					++k;
				}
			}
		}

		return logList;
	}

	/**
	 * Reads data from LAS file and dumps each log to separate text file
	 */
	public StringBuffer[] readDataAndDumpToFile(String[] result, List<String> logs) {
		tracetelemetry();
	//	File lasfile = new File(filepath);
		StringBuffer[] dataBuffer = null;
		try {
			/**
			 * Writes column headers to string buffer
			 */
			dataBuffer = initDataString(logs);
		} catch (Exception e) {
		//	logger.addLog(ExceptionUtils.getStackTrace(e),"ERROR");
			e.printStackTrace();
			// telemetry.trackException(e);
			System.out.println("Exceptoin" + e);
		}
		if (dataBuffer == null) {
			return dataBuffer;
		}
		try {

		//	FileReader f = new FileReader(filepath);
		//	BufferedReader rf = new BufferedReader(f);
			//String linefeed = null;
			boolean isASCII = false;
			float depth = Float.NaN;
			int startIndex = 0; // used for wrapped files.

			float[] fullarray = null;
			int index = 0;
			int progress = 0;
			int temp = 0;
			lineCount = getLineCount(result);
			totalNumberOfDataPoints = lineCount - this.asciiStartRow;
			for(String linefeed:result) {
				if (isComment(linefeed) || linefeed.trim().length() == 0) {
					continue;
				}
				if (!isASCII) {
					isASCII = isASCIISection(linefeed);
					this.isWrapped(linefeed);
					if (this.isFileWrapped) {
						fullarray = new float[this.curveInfoList.size()];
					}
				} else {
					if (this.isFileWrapped) {
						String s = linefeed.trim();
						if (s.length() < 0) {
							continue;
						}

						int tindex = s.lastIndexOf(' ');
						if (tindex == -1) {
							float temp1 = Float.parseFloat(s);
							if (temp1 != depth) {
								depth = temp1;
							}
							// reset startIndex;
							startIndex = 0;
							isNextLine = true;
							continue;
						} else {
							isNextLine = false;
							startIndex = readWrappedDataSection(linefeed, startIndex, fullarray);
						}
						if (isNextLine) {
							if (showDebugStatements) {
								// System.out.println("========================================================");
								fullarray[0] = depth;
								for (int k = 0; k < fullarray.length; k++) {
									CurveInfo cf = this.curveInfoList.get(k);
								}
							}
							_addWrappedSectionToString(dataBuffer, depth, fullarray, logs);
						}
					} else {
						readUnWrappedDataSectionString(dataBuffer, linefeed, logs);
					}
				}

				index++;
			}
		//	f.close();
			//rf.close();
			// setProgress(100);
		} catch (Exception e) {
			//logger.addLog(ExceptionUtils.getStackTrace(e),"ERROR");
			//e.printStackTrace();
			// telemetry.trackException(e);
		}

		return dataBuffer;
	}

	/**
	 * to get line count
	 */
	/**
	 * read las file to get line count
	 */
	public int getLineCount(String[] result) {
		tracetelemetry();
		int count = 0;
//		if (result != null ) {
//			InputStream is = null;
//			try {
//				is = result;
//			} catch (FileNotFoundException e) {
				// telemetry.trackException(e);
				// TODO Auto-generated catch block
//				logger.addLog(ExceptionUtils.getStackTrace(e),"ERROR");
//				e.printStackTrace();
			//}
//		int i =0;
		for(String linefeed:result) {
			
			count++;
			
		}
		
//			try {
//				byte[] c = new byte[1024];
//				int readChars = 0;
//				boolean empty = true;
//				try {
//					while ((readChars = is.read(c)) != -1) {
//						empty = false;
//						for (int i = 0; i < readChars; ++i) {
//							if (c[i] == '\n') {
//								++count;
//							}
//						}
//					}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					// telemetry.trackException(e);
////					logger.addLog(ExceptionUtils.getStackTrace(e),"ERROR");
////					e.printStackTrace();
//				}
//				count = (count == 0 && !empty) ? 1 : count;
//			} finally {
//				try {
//					is.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					// telemetry.trackException(e);
////					logger.addLog(ExceptionUtils.getStackTrace(e),"ERROR");
////					e.printStackTrace();
//				}
//			}
//		}
		return count;
	}

	/**
	 * getting log information(mnemonicName, category, unit) from the las file
	 * 
	 * @param _logs
	 * @return
	 */
	private StringBuffer[] initDataString(List<String> _logs) {
		StringBuffer[] logs = null;
		int logSize = 0;
		if (_logs.size() == curveInfoList.size()) {
			logs = new StringBuffer[_logs.size()];
			logSize = _logs.size();
		} else {
			logs = new StringBuffer[curveInfoList.size()];
			logSize = curveInfoList.size();
		}

		for (int i = 0; i < logSize; i++) {
			CurveInfo cf = this.curveInfoList.get(i);
			String mnemonicName = cf.getMnemonic();
			String unitCategory = null;
			String unitstring = cf.getUnitString();
			if (cf.getMnemonic().trim().equalsIgnoreCase("DEPT") || cf.getMnemonic().trim().equalsIgnoreCase("DEPTH")) {

				unitCategory = "Measured Depth";
				unitstring = cf.getUnitString();
				depthUnit = cf.getUnitString();

			} else {
				//unitCategory = mnemonicServie.getUnitCategoryForMnemonic(mnemonicName);
				unitstring = cf.getUnitString();
			}

			logs[i] = new StringBuffer();
			logs[i].append(UOMRegistry.MEASURED_DEPTH + "\t" + mnemonicName);
			logs[i].append("\n" + UOMRegistry.LENGTH + "\t" + unitCategory);
			logs[i].append("\n" + this.depthUnit + "\t" + unitstring);

		}
		return logs;

	}

	private boolean isWrapped(String linefeed) {
		boolean iswrapped = false;

		String chars = linefeed.trim();
		int dotindex = chars.indexOf(LASKeywords.LAS_SEPARATOR_DOT);
		if (dotindex != -1) {
			String key = chars.substring(0, dotindex).trim();
			if (key.equalsIgnoreCase(LASKeywords.LAS_KEYWORD_WRAP)) {
				int colonindex = chars.indexOf(LASKeywords.LAS_SEPARATOR_COLON);
				String prop = chars.substring(dotindex + 1, colonindex).trim();
				if (prop.equalsIgnoreCase(LASKeywords.LAS_KEYWORD_WRAP_YES)) {
					iswrapped = true;
					// System.out.println(currentFilePath);
					isFileWrapped = true;
				}
			}
		}
		return iswrapped;
	}

	private int readWrappedDataSection(String linefeed, int startIndex, float[] array) {
		int returnindex = startIndex;
		int numberOfCurves = curveInfoList.size();
		StringTokenizer tokenizer = new StringTokenizer(linefeed, " ");
		int i = startIndex;
		while (tokenizer.hasMoreTokens()) {
			i++;
			String s = tokenizer.nextElement().toString();
			float val = Float.parseFloat(s);
			if (i < numberOfCurves) {
				array[i] = val;
			}
			if (i == numberOfCurves - 1) {
				isNextLine = true;
			}
		}
		returnindex = i;
		return returnindex;
	}

	private void _addWrappedSectionToString(StringBuffer[] strings, float depth, float[] fullarray,
			List<String> logList) {

		int selectedIndices = logList.size();

		for (int i = 0; i < fullarray.length; i++) {
			for (int k = 0; k < selectedIndices; k++) {
				if (i == (k)) {
					float val = fullarray[i];
					/**
					 * Neelaveni - committed just to test
					 * 
					 */
					if (isNullValue(val)) {
						val = Float.NaN;
					}
					strings[k].append("\n" + String.valueOf(val) + "\t" + String.valueOf(depth));
				}
			}
		}
	}

	private boolean isNullValue(float val) {
		boolean isnull = false;
		for (int i = 0; i < nullvalues.length; i++) {
			String value = String.valueOf(val);
			String nullValue = String.valueOf(nullvalues[i]);
			if (val == nullvalues[i]) {
				isnull = true;
				break;
			}
			/**
			 * checking the null values included in the log data if null value is there we
			 * are skipping the values
			 */
			if (value.contains(nullValue + ".0")) {
				isnull = true;
				break;
			}
		}
		return isnull;
	}

	/**
	 * extracting log data from the las and storing to StringBuffer
	 * 
	 * @param strings
	 * @param linefeed
	 * @param logs
	 */
	private void readUnWrappedDataSectionString(StringBuffer[] strings, String linefeed, List<String> logs) {
		tracetelemetry();
		int logIndices = logs.size();

		StringTokenizer tokenizer = new StringTokenizer(linefeed);
		int i = -1;
		List<Float> valList = new ArrayList<Float>(logIndices);
		// String[] nullArray = _readNullValues();
		float depthVal = Float.NaN;
		while (tokenizer.hasMoreElements()) {
			i++;
			String s = tokenizer.nextElement().toString();

			if (s != null && s.contains(",")) {
				s = s.replace(",", "");
			}
			float value = Float.parseFloat(s);
			if (i == 0) {
				depthVal = value;
			}
			// } else {
			for (int k = 0; k < logIndices; k++) {
				if (i == k) {
					valList.add(new Float(value));
					break;
				}
				// }
			}

		} // end while
			// now add the data to the series.
		try {

			for (int k = 0; k < logIndices; k++) {
				// System.out.println(valList.get(k));
				// if (k < valList.size()) {
				float val = valList.get(k);

				if (isNullValue(val)) {
					val = Float.NaN;
				}

				strings[k].append("\n" + String.valueOf(val) + "\t" + depthVal);
				// }
			}
		} catch (Exception e) {
			// telemetry.trackException(e);
//			logger.addLog(ExceptionUtils.getStackTrace(e),"ERROR");
//			System.out.println(e);
		}
	}



	public class PB_FlexLayout extends FlexLayout implements Focusable {

		public PB_FlexLayout() {
			this.setTabIndex(0);
		}
	}

//		
}

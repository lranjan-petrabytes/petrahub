package com.petrabytes.views.welllogs;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.project.util.ProjectSettingsInfo;
import com.petrabytes.project.util.WellLogsProjectViewsSettingsInfo;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.MainLayout;
import com.petrabytes.views.UI_Update;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.basin.BasinViewGridInfo;
import com.petrabytes.views.seismic.SeismicTraceDataInfo;
import com.petrabytes.views.wellRegistryQuery.WellRegistryQuries;
import com.petrabytes.views.wellRegistryQuery.WellboreListInfo;
import com.petrabytes.views.wells.WellListInfo;
import com.petrabytes.views.wells.WellsQueries;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridSelectionModel;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;

import elemental.json.JsonArray;

import com.vaadin.flow.router.PageTitle;

//@Route(value = "well-logs", layout = MainLayout.class)
@PageTitle("Well Logs")
public class WellLogsView extends HorizontalLayout {

	private Grid<WellLogsInfo> headerGrid = new Grid<>();

	private VerticalLayout searchLayout = new VerticalLayout();
    private String logNames = "";
    private JsonArray logsData;
	private FlexLayout mainLayot = new FlexLayout();
	private VerticalLayout vLayout = new VerticalLayout();

	private PB_FlexLayout mLayout = new PB_FlexLayout();
	private Button button = new Button(VaadinIcon.ANGLE_DOUBLE_LEFT.create());
	public ListDataProvider<BasinViewGridInfo> basinDataProvider;
	public ListDataProvider<WellListInfo> wellDataProvider;
	public ListDataProvider<WellboreListInfo> wellboreDataProvider;
	private SortDirection aSSENDINGDirection = SortDirection.ASCENDING;
	private ComboBox<BasinViewGridInfo> basinCombobox = new ComboBox<BasinViewGridInfo>();
	private ComboBox<WellListInfo> wellCombobox = new ComboBox<WellListInfo>();
	private ComboBox<WellboreListInfo> wellboreCombobox = new ComboBox<WellboreListInfo>();
	private ValueChangeListener<ValueChangeEvent<BasinViewGridInfo>> basinComboBoxListerner = null;
	private ValueChangeListener<ValueChangeEvent<WellListInfo>> wellComboBoxListerner = null;
	private ValueChangeListener<ValueChangeEvent<WellboreListInfo>> wellboreComboBoxListerner = null;
	public BasinViewGridInfo selectedBasin = null;
	public WellListInfo selectedWell = null;
	public WellboreListInfo selectedWellbore = null;
	private long wellboreId = 0;

	private Button searchButton = new Button();
	private Button plotButton = new Button();

	private Div plotLayout = new Div();
	private ProjectSettingsInfo projectSettings = null;
	private WellLogsProjectViewsSettingsInfo wellLogSetting = null;
//	private WellboreListInfo wellLogSetting=null;
	private List<WellLogsInfo> traceListData = null;
     private 	String depth = new String();
     private final UI ui = UI.getCurrent();
     
	public WellLogsView() {

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
		HorizontalLayout headLayout = new HorizontalLayout(button, label2);
		headLayout.setSpacing(true);
		headLayout.setPadding(false);
		label2.setVisible(false);
		vLayout.add(headLayout, layout);

		String projectname = (String) VaadinService.getCurrentRequest().getWrappedSession()
				.getAttribute("project_name");
		if (projectname == null) {
			//Notification.show("Please first create the project", 3000, Position.BOTTOM_START).addThemeVariants(NotificationVariant.LUMO_CONTRAST);
//			PB_Progress_Notification notification = new PB_Progress_Notification();
//			
//			notification.setText("Please first create the project");
//			notification.open();
//			notification.setDuration(3000);
		} else {
			UI_Update.updateEnable_topBar();
	       add(mainLayot, vLayout);
			this.expand(mainLayot);

			getProjectSetting();
			createPlotLayout();
			createEditorLayout();
			mLayout.focus();

			mLayout.setVisible(true);
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

	private void getProjectSetting() {
		// TODO Auto-generated method stub
		projectSettings = View_Update.getProjectsettingInfo();
		if (projectSettings != null) {
			wellLogSetting = projectSettings.getViews().getWellLogSetting();
			if (wellLogSetting == null)
				wellLogSetting = new WellLogsProjectViewsSettingsInfo();
		}
	}



	private void createPlotLayout() {
		// TODO Auto-generated method stub
		plotLayout.setSizeFull();
		plotLayout.setHeight("800px");
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

		searchFormLayout.addFormItem(basinCombobox, "Basin");
		searchFormLayout.addFormItem(wellCombobox, "Well");
		searchFormLayout.addFormItem(wellboreCombobox, "Wellbore");

		HorizontalLayout buttonLayout = new HorizontalLayout();
		buttonLayout.setClassName("w-full flex-wrap py-s px-l");
		buttonLayout.getStyle().set("padding-left", "0");
		buttonLayout.setSpacing(true);
		Image searchImage = new Image("images" + File.separator + "search24.png","Search");
		searchButton.setIcon(searchImage);
		searchButton.getElement().setAttribute("title", "Search");

		
		Image plotImage = new Image("images" + File.separator + "display24.png","plot");
		plotButton.setIcon(plotImage);
		plotButton.getElement().setAttribute("title", "Plot");
		buttonLayout.add(searchButton, plotButton);

		headerGrid.addColumn(WellLogsInfo::getLogname).setHeader("Log Name").setAutoWidth(true);
//		headerGrid.addColumn(WellLogsInfo::getMin).setHeader("Unit Category").setAutoWidth(true);
		headerGrid.addColumn(WellLogsInfo::getUnit).setHeader("Unit").setAutoWidth(true);

		headerGrid.setSelectionMode(SelectionMode.MULTI);
		headerGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		headerGrid.setHeightByRows(true);
		headerGrid.setHeight("400px");

		wellLogsVerticalLayout.add(searchFormLayout, buttonLayout, headerGrid);
		wellLogsVerticalLayout.setClassName("left_border");
		editorLayoutDiv.add(wellLogsVerticalLayout);

		mLayout.add(editorLayoutDiv);

		// Populate comboboxes
		try {
			_populateBasins();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String selectedBasin = wellLogSetting.getBasinname();
		if (selectedBasin != null) {
//			basinCombobox.setItems(selectedBasin);
		}

		String selectedWellName = wellLogSetting.getWellname();
		if (selectedWellName != null) {
			_populateWells();
//			wellCombobox.setValue(selectedWellName);
		}
		_basinComboboxEvent();
		_wellComboboxEvent();
		String selectedWellboreName = wellLogSetting.getWellborename();
		if (selectedWellboreName != null) {
			_populateWellbores(false);
//			wellboreCombobox.setValue(selectedWellboreName);
		}
		_wellboreComboboxEvent();

		List<String> selectedLogsName = wellLogSetting.getSelectedLogs();
		if (selectedLogsName != null) {
			_populateLogs(false);

			GridSelectionModel<WellLogsInfo> msm = headerGrid.getSelectionModel();
			for (WellLogsInfo singleData : traceListData) {
				if (selectedLogsName.contains(singleData.getLogname())) {
					msm.select(singleData);
				}
			}

			_displayLogs(false);

		}
		_searchEvent();
		_plotEvent();

	}

	/**
	 * Populate all the wellbores
	 */
	private void _populateBasins() throws SQLException {

		ListDataProvider<BasinViewGridInfo> Basin= new WellRegistryQuries().convertToListBasin();
		basinCombobox.setItemLabelGenerator(BasinViewGridInfo::getBasinName);
		Basin.setSortOrder(BasinViewGridInfo::getBasinName, aSSENDINGDirection);
		basinCombobox.setDataProvider(Basin);
	}
		
	

	/**
	 * Populate all the wellbores
	 */
	private void _populateWells() {
		List names = new ArrayList();
		names.add("Demo Well1");
		wellCombobox.setItems(names);
	}

	/**
	 * Populate all the wellbores
	 */
	private void _populateWellbores(Boolean userClick) {
		String query = "select distinct wellbore_id  from well_logs_db.well_logs_header" + " order by wellbore_id ASC";
		if (userClick) {
			ExecutorService executor = Executors.newCachedThreadPool();
			PB_Progress_Notification notificatiion = new PB_Progress_Notification();
			String wellboreData = PetrahubNotification_Utilities.getInstance().fetchingWellboreData();
			executor.submit(() -> {

				ui.access(() -> {

					notificatiion.setImage("info");
					notificatiion.setText(wellboreData);
					notificatiion.open();
				});
				WellLogQueries logsQuery = new WellLogQueries();
				List<String> wellboreNames = logsQuery.readWellbores(query);

				ui.access(() -> {
//					wellboreCombobox.setItems(wellboreNames);
					notificatiion.close();
				});

			});
		} else {
			WellLogQueries logsQuery = new WellLogQueries();
			List<String> wellboreNames = logsQuery.readWellbores(query);
//			wellboreCombobox.setItems(wellboreNames);
		}

	}

	private void _basinComboboxEvent() {

		basinComboBoxListerner = new ValueChangeListener<HasValue.ValueChangeEvent<BasinViewGridInfo>>() {

			@Override
			public void valueChanged(ValueChangeEvent<BasinViewGridInfo> event) {
				// TODO Auto-generated method stub
				selectedBasin = event.getValue();
				if (selectedBasin != null) {
					try {
						wellDataProvider = new WellRegistryQuries().convertToListWell(selectedBasin.getBasinID());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					wellCombobox.setItemLabelGenerator(WellListInfo::getWellName);
					  wellDataProvider.setSortOrder(WellListInfo::getWellName, aSSENDINGDirection);
					wellCombobox.setDataProvider(wellDataProvider);
				}
			}
		};
		basinCombobox.addValueChangeListener(basinComboBoxListerner);
//		basinCombobox.addValueChangeListener(event -> {
//			wellLogSetting.setBasinname(basinCombobox.getValue());
//			_populateWells();
//
//		});
	}

	private void _wellComboboxEvent() {
		wellComboBoxListerner = new ValueChangeListener<HasValue.ValueChangeEvent<WellListInfo>>() {

			@Override
			public void valueChanged(ValueChangeEvent<WellListInfo> event) {
				// TODO Auto-generated method stub
				selectedWell = event.getValue();
				if (selectedWell != null) {

					try {
						wellboreDataProvider = new WellRegistryQuries().convertToListWellbore(selectedWell.getWellID(),selectedWell.getBasinID() );
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					wellboreCombobox.setItemLabelGenerator(WellboreListInfo::getWellboreName);
					wellboreDataProvider.setSortOrder(WellboreListInfo::getWellboreName,aSSENDINGDirection);
					wellboreCombobox.setDataProvider(wellboreDataProvider);

				}
			}
		};
		wellCombobox.addValueChangeListener(wellComboBoxListerner);
		// TODO Auto-generated method stub
//		wellCombobox.addValueChangeListener(event -> {
//			wellLogSetting.setWellname(wellCombobox.getValue());
//			_populateWellbores(true);
//
//		});
		
	}

	private void _wellboreComboboxEvent() {
		wellboreComboBoxListerner = new ValueChangeListener<HasValue.ValueChangeEvent<WellboreListInfo>>() {

			@Override
			public void valueChanged(ValueChangeEvent<WellboreListInfo> event) {
				// TODO Auto-generated method stub
				selectedWellbore = event.getValue();
				//listDataIntoQCGrid();

			}

		};
		wellboreCombobox.addValueChangeListener(wellboreComboBoxListerner);
		// TODO Auto-generated method stub
//		wellboreCombobox.addValueChangeListener(event -> {
//		});

	}

	private void _plotEvent() {
		// TODO Auto-generated method stub
		plotButton.addClickListener(event -> {
			_displayLogs(true);
			selectedlist();
		});

	}

	private void selectedlist() {
		String lognames = "";
		for (WellLogsInfo row : headerGrid.getSelectedItems()) {
			String logName = row.getLogname();

			lognames += logName + ",";

		}
		if (lognames.endsWith(",")) {
			lognames = lognames.substring(0, lognames.length() - 1);
		}

		String[] myArray = lognames.split(",");
		List<String> myList = Arrays.asList(myArray);
		wellLogSetting.setSelectedLogs(myList);

		projectSettings.getViews().setWellLogSetting(wellLogSetting);
	//	VaadinService.getCurrentRequest().getWrappedSession().setAttribute("project_settings", projectSettings);
		View_Update.setProjectsettingInfo(projectSettings);
	}

	private void _searchEvent() {
		// TODO Auto-generated method stub
		searchButton.addClickListener(event -> {
			wellLogSetting.setWellboreinfo(wellboreCombobox.getValue());
			_populateLogs(true);
		});

	}

	/**
	 * Populate the logs based on the selected wellbore
	 */
	private void _populateLogs(Boolean userClick) {
		WellboreListInfo wellboreName = wellLogSetting.getWellboreinfo();
		if (wellboreName.getWellboreName() != null) {
//			if (wellboreName.) {
				String query = "select * from well_logs_db.well_logs_header where wellbore_id = " + wellboreName.getWellboreID()+ " order by mnemonic ASC";

				if (userClick) {
					ExecutorService executor = Executors.newCachedThreadPool();
					PB_Progress_Notification notificatiion = new PB_Progress_Notification();
					String populatelogData = PetrahubNotification_Utilities.getInstance().queryLogData();
					executor.submit(() -> {
						WellLogQueries logsQuery = new WellLogQueries();
						ui.access(() -> {

							notificatiion.setImage("info");
							notificatiion.setText(populatelogData);
							notificatiion.open();

						});try {
							traceListData = logsQuery.queryLogNamesData(query);
							if(traceListData.isEmpty()) {
								String noLogFound = PetrahubNotification_Utilities.getInstance().noLogDataFound();
								ui.access(() -> {

									notificatiion.setImage("info");
									notificatiion.setText(noLogFound);
									notificatiion.open();
						
								});
								
							}else {

							ui.access(() -> {
								headerGrid.setItems(traceListData);
								notificatiion.close();
							});
							
							}
							ui.access(() -> {
								headerGrid.setItems(traceListData);
								notificatiion.setDuration(4000);
								notificatiion.close();
							});
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					});
					
				} else {
					WellLogQueries logsQuery = new WellLogQueries();
					try {
						traceListData = logsQuery.queryLogNamesData(query);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					headerGrid.setItems(traceListData);

				}
				

			}
		
		
	}

	/**
	 * 
	 * @param logNames abc,abc,abc
	 */
	private void _displayLogs(boolean userClick) {

	    String logNames="";
		List<String> units = new ArrayList<>();

		for (WellLogsInfo row : headerGrid.getSelectedItems()) {
			String logName = row.getLogname();

			logNames += logName + ",";
			units.add(row.getUnit());
		}
		// datapreperationproject.setSelectedLogs(lognames);
		if (logNames.endsWith(",")) {
			logNames = logNames.substring(0, logNames.length() - 1);
		}
		if (traceListData != null) {
//			if (!wellboreName.isEmpty()) {
			String unitcategory = "Measured Depth";
			String mnemonicQuery = "select mnemonic from well_logs_db.well_logs_header WHERE unit_category = '"+unitcategory+"' and wellbore_id ="+wellLogSetting.getWellboreinfo().getWellboreID()+"";
			
			WellLogQueries mnemoniclogsQuery = new WellLogQueries();
			try {
				 depth = mnemoniclogsQuery.getMnemonicByUnitCategory(mnemonicQuery);
				System.out.println(depth);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
				String query = "select "+depth+"," + logNames + " from well_logs_db.well_logs_data_" + traceListData.get(2).getLogfileid() + " sort by "+depth+"";

				if (userClick) {
					ExecutorService executor = Executors.newCachedThreadPool();
					PB_Progress_Notification notificatiion = new PB_Progress_Notification();
					String createProject = PetrahubNotification_Utilities.getInstance().plottingLogData();
					executor.submit(() -> {
						WellLogQueries logsQuery = new WellLogQueries();

						ui.access(() -> {

							notificatiion.setImage("info");
							
							notificatiion.setText(createProject);
							notificatiion.open();

						});
						JsonArray logsData = logsQuery.queryLogsData(query, units);
						ui.access(() -> {
							UI.getCurrent().getElement().executeJs("wellLogsPlotly($0,$1,$2,$3)", logsData,
									"realdata_input", "m", getElement());
							notificatiion.close();

						});
					});

				} else {

					WellLogQueries logsQuery = new WellLogQueries();
					JsonArray logsData = logsQuery.queryLogsData(query, units);
					UI.getCurrent().getElement().executeJs("wellLogsPlotly($0,$1,$2,$3)", logsData, "realdata_input",
							"m", getElement());

				}
			
		}
	}

	public class PB_FlexLayout extends FlexLayout implements Focusable {

		public PB_FlexLayout() {
			this.setTabIndex(0);
		}
	}

	}

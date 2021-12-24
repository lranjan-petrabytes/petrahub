package com.petrabytes.views.seismic;


import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.project.util.ProjectSettingsInfo;
import com.petrabytes.project.util.WellLogsProjectViewsSettingsInfo;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.MainLayout;
import com.petrabytes.views.UI_Update;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.welllogs.WellLogsView.PB_FlexLayout;
import com.petrabytes.views.wells.WellsQueries;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.FormItem;
import com.vaadin.flow.component.grid.Grid;
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
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.json.JSONArray;

import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.VaadinService;

import elemental.json.JsonArray;
import elemental.json.JsonObject;

@JsModule("./src/plotting/commonXY_Plotly.js")

@Route(value = "seismic", layout = MainLayout.class)
@PageTitle("Seismic")
public class SeismicView extends HorizontalLayout {

	Grid<SeismicHeaderInfo> headerGrid = new Grid<>();
	Grid<SeismicTraceDataInfo> seismicTraceGrid = new Grid<>();
	Grid<SeismicHeaderInfo> traceHeaderGrid = new Grid<>();
	private TextField traceNumberField = new TextField("Trace Number");
	private Label traceInfoLabel = new Label();
	
	private FlexLayout mainLayot = new FlexLayout();
	private VerticalLayout vLayout = new VerticalLayout();
	
	private PB_FlexLayout mLayout = new PB_FlexLayout();
	private Button button = new Button(VaadinIcon.ANGLE_DOUBLE_LEFT.create());
	
	private Label inlineLabel = new Label();
	private Label xlineLabel = new Label();
	private Label xLabel = new Label();
	private Label yLabel = new Label();

	private ComboBox<String> basinCombobox = new ComboBox<String>("Basin");

	Div chartLayout = new Div();

	private Button searchTracesButton = new Button();

	SeismicQueries seismicQuery = new SeismicQueries();
	
	private ProjectSettingsInfo projectSettings = null;
	private seismicProjectViewsSettingsInfo seismicSetting; 


	public SeismicView() {
		this.setSizeFull();
		this.setSpacing(false);
		this.setPadding(false);
		vLayout.setSpacing(false);
		vLayout.setPadding(false);
		vLayout.setSizeUndefined();
		
		mainLayot.setSizeFull();
		
		
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
		HorizontalLayout headLayout = new HorizontalLayout(button,label2);
		headLayout.setSpacing(true);
		headLayout.setPadding(false);
		label2.setVisible(false);
		vLayout.add(headLayout, layout);
		
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
			getProjectSetting();
		//this.add(mainLayot,mapLayot, vLayout);
		this.add(mainLayot, vLayout);
		//this.expand(mapLayot);
		this.expand(chartLayout);
		chartLayout.setId("realdata_input");
		//	chartLayout.setWidth("500px");
			chartLayout.setHeight("700px");
	//	createPlotLayout();
		createAnalysisLayout();
		createEditorLayout();
		mLayout.focus();

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

		});
		
		}
	}
	
	private void getProjectSetting() {
		// TODO Auto-generated method stub
		projectSettings = View_Update.getProjectsettingInfo();
		if (projectSettings != null) {
			seismicSetting = projectSettings.getViews().getSeiesmicSettings();
			if (seismicSetting == null)
				seismicSetting = new seismicProjectViewsSettingsInfo();
		}
	}
	

	private void createEditorLayout() {
		// TODO Auto-generated method stub
		Div editorLayoutDiv = new Div();
		editorLayoutDiv.setHeightFull();
		editorLayoutDiv.setClassName("flex flex-col");
		editorLayoutDiv.setWidth("300px");
		editorLayoutDiv.setHeightFull();

		VerticalLayout seismicVerticalLayout = new VerticalLayout();
		seismicVerticalLayout.setClassName("p-l flex-grow");
		editorLayoutDiv.add(seismicVerticalLayout);

		headerGrid.addColumn(SeismicHeaderInfo::getIdentifier).setHeader("Identifier").setAutoWidth(true);
		headerGrid.addColumn(SeismicHeaderInfo::getMin).setHeader("Min").setAutoWidth(true);
		headerGrid.addColumn(SeismicHeaderInfo::getMax).setHeader("Max").setAutoWidth(true);

		headerGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		headerGrid.setMinHeight("200px");
		headerGrid.setHeightByRows(true);

		traceNumberField.setWidth("250px");
		Image searchImage = new Image("images" + File.separator + "search24.png","Search");
		searchTracesButton.setIcon(searchImage);
		searchTracesButton.getElement().setAttribute("title", "Search");
		seismicVerticalLayout.add(basinCombobox, headerGrid, traceNumberField, traceInfoLabel, searchTracesButton);
		seismicVerticalLayout.setClassName("left_border");
		seismicVerticalLayout.setHeight("825px");
		
		mLayout.add(editorLayoutDiv);
		

		
		_populateBasins();
		//String selectedBasinName = seismicSetting.getSelectedBasin();
		//String selectedTraceValue = seismicSetting.getSelectedTraceValue();
		if (seismicSetting.getSelectedBasin() != null) {
			
	
			basinCombobox.setValue(seismicSetting.getSelectedBasin());
			traceNumberField.setValue(seismicSetting.getSelectedTraceValue());
			
			_populateTraceHeaderTable(false);
			_searchSeismicTraces(false);
		}
		
		_populateTraceHeaderTable(true);
		_searchSeismicTraces(true);
	}

	private void _populateTraceHeaderTable(boolean userClick) {

		
      //  if(seismicSetting == null) {
		if(userClick) {
		basinCombobox.addValueChangeListener(event -> {
			
			seismicSetting.setSelectedBasin( basinCombobox.getValue());
			ExecutorService executor = Executors.newCachedThreadPool();
			PB_Progress_Notification notificatiion = new PB_Progress_Notification();
			UI ui = getUI().get();
			executor.submit(() -> {
				ui.access(() -> {
					PB_Progress_Notification notification = new PB_Progress_Notification();
					String siesmicheader = PetrahubNotification_Utilities.getInstance().siesmicHeader();
					notification.setImage("info");
					notification.setText(siesmicheader);
					notification.open();
					notification.setDuration(3000);
					
				});

				try {
					List<SeismicHeaderInfo> headerList = seismicQuery.querySeismicHeaders();
					String totalTraces = seismicQuery.queryTotalTraces();
					ui.access(() -> {
						headerGrid.setItems(headerList);
						traceInfoLabel.setText("Enter trace between 0 - " + totalTraces);
						notificatiion.close();
					});
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			});
		});
        } else {

				try {
					List<SeismicHeaderInfo> headerList = seismicQuery.querySeismicHeaders();
					String totalTraces = seismicQuery.queryTotalTraces();
			
						headerGrid.setItems(headerList);
						traceInfoLabel.setText("Enter trace between 0 - " + totalTraces);
		
				} catch (SQLException e) {
				
					e.printStackTrace();
				}
		
        }
	}

	private void _populateBasins() {
		List names = new ArrayList();
		names.add("Volve Basin");
		basinCombobox.setItems(names);
		
	}

	private void createAnalysisLayout() {
		// TODO Auto-generated method stub

		Tab traceHeaderTab = new Tab("Trace Header");
		HorizontalLayout traceHeaderLayout = new HorizontalLayout();
		traceHeaderLayout.setWidthFull();
	//	traceHeaderLayout.setHeight("700px");

		Tab analysisTab = new Tab("Analysis");
		HorizontalLayout analysisLayout = new HorizontalLayout();
		analysisLayout.setWidthFull();
		
		HorizontalLayout tablayout = new HorizontalLayout();
		tablayout.setWidthFull();

		Map<Tab, Component> tabsToPages = new HashMap<>();
		tabsToPages.put(traceHeaderTab, traceHeaderLayout);
		tabsToPages.put(analysisTab, analysisLayout);
		Tabs tabs = new Tabs(traceHeaderTab, analysisTab);
		tablayout.add(tabs);
		tabs.setWidthFull();
		HorizontalLayout pages = new HorizontalLayout(traceHeaderLayout, analysisLayout);
		pages.setSizeFull();
		tabs.setFlexGrowForEnclosedTabs(1);

		VerticalLayout pagesLayoutadd = new VerticalLayout();
		pagesLayoutadd.add(tablayout,pages);

		mainLayot.add(pagesLayoutadd);
		_setTraceHeaderUI(traceHeaderLayout);
		_setAnalysisUI(analysisLayout);
		_tabsChangeEvent(tabs, tabsToPages,pages);
	}

	private void _setTraceHeaderUI(HorizontalLayout traceHeaderLayout) {

		traceHeaderLayout.setSizeFull();
	//	traceHeaderLayout.setVisible(false);
		

		traceHeaderGrid.addColumn(SeismicHeaderInfo::getHeaderName).setHeader("Name").setAutoWidth(true);
		traceHeaderGrid.addColumn(SeismicHeaderInfo::getHeaderValue).setHeader("Value").setAutoWidth(true);

		traceHeaderGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		traceHeaderGrid.setSizeFull();
		traceHeaderLayout.add(traceHeaderGrid);

	}

	private void _setAnalysisUI(HorizontalLayout analysisLayout) {
		analysisLayout.setVisible(false);
		analysisLayout.setSizeFull();

		seismicTraceGrid.addColumn(SeismicTraceDataInfo::getIndex).setHeader("Index").setAutoWidth(true);
		seismicTraceGrid.addColumn(SeismicTraceDataInfo::getyValue).setHeader("Y").setAutoWidth(true);
		seismicTraceGrid.addColumn(SeismicTraceDataInfo::getxValue).setHeader("X").setAutoWidth(true);

		seismicTraceGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);

		seismicTraceGrid.setWidth("350px");
		seismicTraceGrid.setHeightFull();
	
	

		HorizontalLayout traceHeaderLabels = new HorizontalLayout();
		traceHeaderLabels.add(inlineLabel, xlineLabel, xLabel, yLabel);

		VerticalLayout chartVerticalLayout = new VerticalLayout();
		chartVerticalLayout.add(traceHeaderLabels, chartLayout);

		analysisLayout.add(seismicTraceGrid, chartVerticalLayout);
	}

	private void _tabsChangeEvent(Tabs tabs, Map<Tab, Component> tabsToPages,HorizontalLayout pages) {
		
		tabs.addSelectedChangeListener(event -> {
			tabsToPages.values().forEach(page -> page.setVisible(false));
			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
			selectedPage.setVisible(true);
		});

	}

	private void _searchSeismicTraces(boolean userClick) {
		// TODO Auto-generated method stub
		if (userClick) {
		searchTracesButton.addClickListener(event -> {
			
			ExecutorService executor = Executors.newCachedThreadPool();
			PB_Progress_Notification notificatiion = new PB_Progress_Notification();
			UI ui = getUI().get();
			executor.submit(() -> {
				ui.access(() -> {
					PB_Progress_Notification notification = new PB_Progress_Notification();
					String seismictrace = PetrahubNotification_Utilities.getInstance().siemictrace();
					notification.setImage("info");
					notification.setText(seismictrace);
					notification.open();
					notification.setDuration(3000);
					
				});
				try {
					String selectedValue = traceNumberField.getValue();
					seismicSetting.setSelectedTraceValue(selectedValue);
					SeismicQueries traceQuery = new SeismicQueries();

					List<SeismicTraceDataInfo> traceListData = traceQuery.querySeismicTraceData(selectedValue);

					List<SeismicHeaderInfo> traceHeader = traceQuery.queryFullTraceHeader(selectedValue);
					ui.access(() -> {
						traceHeaderGrid.setItems(traceHeader);
						seismicTraceGrid.setItems(traceListData);
					});

					ui.access(() -> {
						String inlineValue = null;
						String xlineValue = null;
						String xValue = null;
						String yValue = null;
						for (SeismicHeaderInfo singleTraceHeader : traceHeader) {
							if (singleTraceHeader.getHeaderName().equals("INLINE_3D")) {
								inlineValue = singleTraceHeader.getHeaderValue();
								inlineLabel.setText("Inline : " + inlineValue);
								continue;
							}
							if (singleTraceHeader.getHeaderName().equals("CROSSLINE_3D")) {
								xlineValue = singleTraceHeader.getHeaderValue();
								xlineLabel.setText("Xline : " + xlineValue);
								continue;
							}
							if (singleTraceHeader.getHeaderName().equals("CDP_X")) {
								xValue = singleTraceHeader.getHeaderValue();
								xLabel.setText("X : " + xValue);
								continue;
							}
							if (singleTraceHeader.getHeaderName().equals("CDP_Y")) {
								yValue = singleTraceHeader.getHeaderValue();
								yLabel.setText("Y : " + yValue);
								continue;
							}
						}
					});

					JsonArray seismicJsonData = new SeismicPlot().inputJsonData(traceListData);

					ui.access(() -> {
						UI.getCurrent().getElement().executeJs("wellLogsPlotly($0,$1,$2)", seismicJsonData,
								"realdata_input", "ft", getElement());

						notificatiion.close();
					});

					projectSettings.getViews().setSeiesmicSettings(seismicSetting);
					View_Update.setProjectsettingInfo(projectSettings);
				//	VaadinService.getCurrentRequest().getWrappedSession().setAttribute("project_settings", projectSettings);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}


			});
		});
		} else {

				try {
					String selectedValue = traceNumberField.getValue();

					SeismicQueries traceQuery = new SeismicQueries();

					List<SeismicTraceDataInfo> traceListData = traceQuery.querySeismicTraceData(selectedValue);

					List<SeismicHeaderInfo> traceHeader = traceQuery.queryFullTraceHeader(selectedValue);
		
						traceHeaderGrid.setItems(traceHeader);
						seismicTraceGrid.setItems(traceListData);
			
						String inlineValue = null;
						String xlineValue = null;
						String xValue = null;
						String yValue = null;
						for (SeismicHeaderInfo singleTraceHeader : traceHeader) {
							if (singleTraceHeader.getHeaderName().equals("INLINE_3D")) {
								inlineValue = singleTraceHeader.getHeaderValue();
								inlineLabel.setText("Inline : " + inlineValue);
								continue;
							}
							if (singleTraceHeader.getHeaderName().equals("CROSSLINE_3D")) {
								xlineValue = singleTraceHeader.getHeaderValue();
								xlineLabel.setText("Xline : " + xlineValue);
								continue;
							}
							if (singleTraceHeader.getHeaderName().equals("CDP_X")) {
								xValue = singleTraceHeader.getHeaderValue();
								xLabel.setText("X : " + xValue);
								continue;
							}
							if (singleTraceHeader.getHeaderName().equals("CDP_Y")) {
								yValue = singleTraceHeader.getHeaderValue();
								yLabel.setText("Y : " + yValue);
								continue;
							}
						}
		

					JsonArray seismicJsonData = new SeismicPlot().inputJsonData(traceListData);

			
						UI.getCurrent().getElement().executeJs("wellLogsPlotly($0,$1,$2)", seismicJsonData,
								"realdata_input", "ft", getElement());

			
				} catch (SQLException e) {
			
					e.printStackTrace();
				}


		}
	}
	public class PB_FlexLayout extends FlexLayout implements Focusable {

		public PB_FlexLayout() {
			this.setTabIndex(0);
		}
	}
}
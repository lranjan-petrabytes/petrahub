package com.petrabytes.views.well_Information;

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

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.basin.BasinViewGridInfo;
import com.petrabytes.views.wellRegistryQuery.WellRegistryQuries;
import com.petrabytes.views.wellRegistryQuery.WellboreListInfo;
import com.petrabytes.views.welllogs.WellLogQueries;
import com.petrabytes.views.wells.WellListInfo;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Quality_Check_UI extends VerticalLayout {

	public ListDataProvider<BasinViewGridInfo> basinDataProvider;
	public ListDataProvider<WellListInfo> wellDataProvider;
	public ListDataProvider<WellboreListInfo> wellboreDataProvider;
	private SortDirection aSSENDINGDirection = SortDirection.ASCENDING;
	private ValueChangeListener<ValueChangeEvent<BasinViewGridInfo>> basinComboBoxListerner = null;
	private ValueChangeListener<ValueChangeEvent<WellListInfo>> wellComboBoxListerner = null;
	private ValueChangeListener<ValueChangeEvent<WellboreListInfo>> wellboreComboBoxListerner = null;
	private final UI ui = UI.getCurrent();
    private  String depth;
	private DataTab_UI dataTabUI;
	private Log_Summary_UI summaryUI;
	public BasinViewGridInfo selectedBasin = null;
	public WellListInfo selectedWell = null;
	private long wellboreId = 0;
	public  WellboreListInfo selectedWellbore = null;
	private ComboBox<BasinViewGridInfo> basincombobox;
	private ComboBox<WellListInfo> wellcombobox;
	private ComboBox<WellboreListInfo> wellborecombobox;
	private Grid<Quality_Check_Info> qualitygrid = new Grid();
	private Quality_Check_Info selectedQCValue = null;
    public static String sellectedWellboreID = null;
	private Tabs tabs;
	private FlexLayout layout;
	private static String StratDepth;
	private static String EndDepth;
	public Quality_Check_UI() throws SQLException {
		
		String projectname = (String) VaadinService.getCurrentRequest().getWrappedSession()
				.getAttribute("project_name");
		if (projectname == null) {
//			PB_Progress_Notification notification = new PB_Progress_Notification();
//			String createProject = PetrahubNotification_Utilities.getInstance().createProject();
//			notification.setImage("info");
//			notification.setText(createProject);
//			notification.open();
//			notification.setDuration(3000);

		} else {
			
		set_ui();
		qualityGridSetUP();
		listBainFromDB();
		basinChangeListner();
		 wellComboBoxListener();
		 wellboreComboBoxListener();
		 qualityGridLogsActionEvent();
		 
		}
	}

	private void set_ui() {
		this.setSizeFull();
		this.setPadding(false);
		this.setMargin(false);
		// TODO Auto-generated method stub
		HorizontalLayout labelLayout = new HorizontalLayout();
		Label basinlabel = new Label("Basin/Field");
		Label welllabel = new Label("Well");
		welllabel.getStyle().set("padding-left", "182px");
		Label wellborelabel = new Label("Wellbore");
		wellborelabel.getStyle().set("padding-left", "220px");
		labelLayout.getStyle().set("padding-left", "5px");

		labelLayout.add(basinlabel, welllabel, wellborelabel);

		HorizontalLayout comboLayout = new HorizontalLayout();
		basincombobox = new ComboBox<BasinViewGridInfo>();
		basincombobox.setId("bgz_logs_basincombobox");
		basincombobox.getStyle().set("margin-top", "-20px");
		basincombobox.setWidth("255px");
		wellcombobox = new ComboBox<WellListInfo>();
		wellcombobox.setId("bgz_logs_wellcombobox");
		wellcombobox.getStyle().set("margin-top", "-20px");
		// wellcombobox.getStyle().set("padding-left", "20px");
		wellcombobox.setWidth("255px");
		wellborecombobox = new ComboBox<WellboreListInfo>();
		wellborecombobox.setId("bgz_logs_wellborecombobox");
		wellborecombobox.getStyle().set("margin-top", "-20px");
		// wellborecombobox.getStyle().set("margin-left", "30px");
		wellborecombobox.setWidth("255px");

		comboLayout.getStyle().set("padding-left", "5px");
		comboLayout.add(basincombobox, wellcombobox, wellborecombobox);

		HorizontalLayout centerLayout = new HorizontalLayout();
		centerLayout.setSizeFull();
		centerLayout.setMargin(false);
		centerLayout.setPadding(false);

		qualitygrid.getStyle().set("border-style", "groove");
		qualitygrid.getStyle().set("border-width", "thin");
		qualitygrid.getStyle().set("float", "left");
		qualitygrid.setSizeFull();
		centerLayout.add(qualitygrid);

		Tab dataTab = new Tab("Data");
		dataTab.setId("bgz_logs_dataTab");
		dataTabUI = new DataTab_UI();

		Tab summaryTab = new Tab("Summary");
		summaryTab.setId("bgz_logs_summaryTab");
		summaryUI = new Log_Summary_UI();

		Map<Tab, Component> tabsToPages = new HashMap<>();
		tabsToPages.put(dataTab, dataTabUI);
		tabsToPages.put(summaryTab, summaryUI);
		tabs = new Tabs(dataTab, summaryTab);
		layout = new FlexLayout();
		layout.setSizeFull();
		layout.add(dataTabUI);

		tabs.addSelectedChangeListener(event -> {
			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
			selectedPage.setVisible(true);
			layout.removeAll();
			layout.add(selectedPage);
		});

		VerticalLayout tabLayout = new VerticalLayout(tabs, layout);
		tabLayout.setSizeFull();
		tabLayout.setMargin(false);
		tabLayout.setPadding(false);
		tabLayout.expand(layout);
		centerLayout.add(tabLayout);

		this.add(labelLayout, comboLayout, centerLayout);
		this.expand(centerLayout);
	}

	private void qualityGridSetUP() {
		// TODO Auto-generated method stub

		qualitygrid.addColumn(Quality_Check_Info::getMnemonic).setHeader("Log").setWidth("2%");
		qualitygrid.addColumn(Quality_Check_Info::getUnitCategory).setHeader("Unit Category").setWidth("2%");
		qualitygrid.addColumn(Quality_Check_Info::getUnit).setHeader("Unit").setWidth("2%");
//		qualitygrid.getStyle().set("width", "75%");
		qualitygrid.setHeight("430px");
		qualitygrid.setWidth("170%");
	}
	
	private void listBainFromDB() throws SQLException {
		ExecutorService executor = Executors.newCachedThreadPool();
		PB_Progress_Notification notificatiion = new PB_Progress_Notification();
		String logDataQc = PetrahubNotification_Utilities.getInstance().logDataQC();
		executor.submit(() -> {

			ui.access(() -> {

				notificatiion.setImage("info");
				notificatiion.setText(logDataQc);
				notificatiion.open();
			});
		ListDataProvider<BasinViewGridInfo> basin;
		try {
			basin = new WellRegistryQuries().convertToListBasin();
			basincombobox.setItemLabelGenerator(BasinViewGridInfo::getBasinName);
			basin.setSortOrder(BasinViewGridInfo::getBasinName, aSSENDINGDirection);
			basincombobox.setDataProvider(basin);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  ui.access(() ->{
		  notificatiion.close();
	  });
	
		});
	}


	private void basinChangeListner() {

		basinComboBoxListerner = new ValueChangeListener<HasValue.ValueChangeEvent<BasinViewGridInfo>>() {

			@Override
			public void valueChanged(ValueChangeEvent<BasinViewGridInfo> event) {
				// TODO Auto-generated method stub
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				String populatingWells = PetrahubNotification_Utilities.getInstance().populatingWells();
				executor.submit(() -> {

					ui.access(() -> {

						notificatiion.setImage("info");
						notificatiion.setText(populatingWells);
						notificatiion.open();
					});
				selectedBasin = event.getValue();
				if (selectedBasin != null) {
					try {
						wellDataProvider = new WellRegistryQuries().convertToListWell(selectedBasin.getBasinID());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					wellcombobox.setItemLabelGenerator(WellListInfo::getWellName);
                    wellDataProvider.setSortOrder(WellListInfo::getWellName, aSSENDINGDirection);
					wellcombobox.setDataProvider(wellDataProvider);
					ui.access(()->{
						notificatiion.close();
						
					});
				}
				});
			}
		};
		basincombobox.addValueChangeListener(basinComboBoxListerner);
}
	
	private void wellComboBoxListener() {
		// TODO Auto-generated method stub
		wellComboBoxListerner = new ValueChangeListener<HasValue.ValueChangeEvent<WellListInfo>>() {

			@Override
			public void valueChanged(ValueChangeEvent<WellListInfo> event) {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				String populatingWellbores = PetrahubNotification_Utilities.getInstance().populatingWellbores();
				executor.submit(() -> {

					ui.access(() -> {

						notificatiion.setImage("info");
						notificatiion.setText(populatingWellbores);
						notificatiion.open();
					});
				// TODO Auto-generated method stub
				selectedWell = event.getValue();
				if (selectedWell != null) {

					try {
						wellboreDataProvider = new WellRegistryQuries().convertToListWellbore(selectedWell.getWellID(),selectedWell.getBasinID() );
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					wellborecombobox.setItemLabelGenerator(WellboreListInfo::getWellboreName);
					wellboreDataProvider.setSortOrder(WellboreListInfo::getWellboreName,aSSENDINGDirection);
					wellborecombobox.setDataProvider(wellboreDataProvider);
					ui.access(()->{
						notificatiion.close();
						
					});
				}
				});
			}
		};
		wellcombobox.addValueChangeListener(wellComboBoxListerner);
	}

	/**
	 * wellbore combobox listner
	 */
	private void wellboreComboBoxListener() {
		// TODO Auto-generated method stub

		wellboreComboBoxListerner = new ValueChangeListener<HasValue.ValueChangeEvent<WellboreListInfo>>() {

			@Override
			public void valueChanged(ValueChangeEvent<WellboreListInfo> event) {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				String populatingQC = PetrahubNotification_Utilities.getInstance().populatingLogDataQC();
				executor.submit(() -> {

					ui.access(() -> {

						notificatiion.setImage("info");
						notificatiion.setText(populatingQC);
						notificatiion.open();
					});
				// TODO Auto-generated method stub
				selectedWellbore = event.getValue();
				sellectedWellboreID = selectedWellbore.getWellboreID().toString();
				listDataIntoQCGrid();
				ui.access(()->{
					notificatiion.close();
				});
				});
			}

		};
		wellborecombobox.addValueChangeListener(wellboreComboBoxListerner);

	}
	
	private void listDataIntoQCGrid() {
		
		ListDataProvider<Quality_Check_Info> qcGridData = new WellLogsDataMappingQuery().retrieveDataForQc(selectedWellbore.getWellboreID().toString());
		qualitygrid.setDataProvider(qcGridData);
		
		summaryUI.setQualitygrid(qualitygrid);
	}
	
	private void qualityGridLogsActionEvent() {
		qualitygrid.addSelectionListener(event -> {
			ExecutorService executor = Executors.newCachedThreadPool();
			PB_Progress_Notification notificatiion = new PB_Progress_Notification();

			executor.submit(() -> {

				ui.access(() -> {

					notificatiion.setImage("info");
					notificatiion.setText("Querying Deltalake: Fetching Log data and Summary");
					notificatiion.open();
				});
			selectedQCValue = qualitygrid.asSingleSelect().getValue();
			if (selectedQCValue != null) {
			
			     String fileID = selectedQCValue.getFileID();
				String logname = selectedQCValue.getMnemonic();
				String unitcategory = "Measured Depth";
				String mnemonicQuery = "select mnemonic from well_logs_db.well_logs_header WHERE unit_category = '"+unitcategory+"' and wellbore_id ="+selectedWellbore.getWellboreID()+"";
				WellLogQueries mnemoniclogsQuery = new WellLogQueries();
				try {
					 depth = mnemoniclogsQuery.getMnemonicByUnitCategory(mnemonicQuery);
					System.out.println(depth);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
				ListDataProvider<Depth_Data_Info> populateData = new WellLogsDataMappingQuery().populatingLogsDataIngrid( depth,logname, fileID);
				dataTabUI.setPopulateData(populateData);
				dataTabUI.updateGrid();
				
				List<Depth_Data_Info> depthgridinputs = (List<Depth_Data_Info>) populateData.getItems();
				ArrayList<String> depth = new ArrayList();
				for (Depth_Data_Info row:depthgridinputs) {
					
					 String abc = row.getDepthData();
					 depth.add(abc);
				}
				for (String i:depth) {
					 StratDepth = depth.get(0);
					 EndDepth = depth.get(depth.size()-1);
					
				}
				
//				dataTabUI.updateDataIntoGrid(selectedQCValue);
//
				try {
					summaryUI.updateLabel(selectedQCValue);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				summaryUI.updatePlots(selectedQCValue);
			}
			ui.access(() -> {
				notificatiion.close();
			});
			});
		});

	}

	public void updateQualityGrid(Quality_Check_Info selectedQCValue) {
//		wellboreId = selectedWellbore.getWellboreId();
//		List<Blgz_Logs_HB> logsList = coreService.getLogs(wellboreId);
//		qualitygrid.setItems(logsList);
		qualitygrid.select(selectedQCValue);
	}

	public static String getStratDepth() {
		return StratDepth;
	}

	public void setStratDepth(String stratDepth) {
		StratDepth = stratDepth;
	}

	public static String getEndDepth() {
		return EndDepth;
	}

	public void setEndDepth(String endDepth) {
		EndDepth = endDepth;
	}
	
	

}

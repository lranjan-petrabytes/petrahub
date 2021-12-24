package com.petrabytes.views.dts;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.imageio.ImageIO;

import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.MainLayout;
import com.petrabytes.views.UI_Update;
import com.petrabytes.views.basin.BasinViewGridInfo;

import com.petrabytes.views.wellRegistryQuery.WellRegistryQuries;
import com.petrabytes.views.wellRegistryQuery.WellboreListInfo;

import com.petrabytes.views.wells.WellListInfo;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexLayout;

@Route(value = "DTS", layout = MainLayout.class)
@PageTitle("DTS")
public class DTSView extends HorizontalLayout {

	private ComboBox<DTS_Timestamp> starttime = new ComboBox();
	private ComboBox<DTS_Timestamp> endtime = new ComboBox();
	private TextField tracecount = new TextField();
	private TextField totalnumberoftraces = new TextField();
	private ComboBox<DTS_Depth> startDepthComboBox = new ComboBox();
	private ComboBox<DTS_Depth> endDepthComboBox = new ComboBox();
	private Button searchButton = new Button();
	private SortDirection aSSENDINGDirection = SortDirection.ASCENDING;
	public ListDataProvider<BasinViewGridInfo> basinDataProvider;
	public ListDataProvider<WellListInfo> wellDataProvider;
	public ListDataProvider<WellboreListInfo> wellboreDataProvider;

	private FlexLayout mainLayout = new FlexLayout();
	private VerticalLayout vLayout = new VerticalLayout();
	public BasinViewGridInfo selectedBasin = null;
	public WellListInfo selectedWell = null;
	private long wellboreId = 0;
	private Button button = new Button(VaadinIcon.ANGLE_DOUBLE_LEFT.create());
	public WellboreListInfo selectedWellbore = null;
	private ComboBox<BasinViewGridInfo> basincombobox = new ComboBox();
	private ComboBox<WellListInfo> wellcombobox = new ComboBox();
	private ComboBox<WellboreListInfo> wellborecombobox = new ComboBox();
	private ValueChangeListener<ValueChangeEvent<BasinViewGridInfo>> basinComboBoxListerner = null;
	private ValueChangeListener<ValueChangeEvent<WellListInfo>> wellComboBoxListerner = null;
	private ValueChangeListener<ValueChangeEvent<WellboreListInfo>> wellboreComboBoxListerner = null;
	private PB_FlexLayout mLayout = new PB_FlexLayout();
	private final UI ui = UI.getCurrent();
	Grid<DTS_GridData> searchResultGrid = new Grid<>();
	private HorizontalLayout dtsResultLayout = new HorizontalLayout();
	private List<Long> depth_ids = new ArrayList<Long>();
	private List<DTS_Depth> depthsList = new ArrayList<DTS_Depth>();
	private List<DTS_Depth> selectedDepthsList = new ArrayList<DTS_Depth>();
	private List<DTS_Timestamp> timeStampsList = new ArrayList<DTS_Timestamp>();
	private List<DTS_Timestamp> timestampSubList = new ArrayList<DTS_Timestamp>();
	private List<DTS_Timestamp> timeStampsComboBoxList = new ArrayList<DTS_Timestamp>();
	private String[][] dts_data = null;
	private VerticalLayout imageLayout = new VerticalLayout();
	private VerticalLayout gridLayout = new VerticalLayout();
	private Label imageLabel = new Label("DTS Image");
	private Label gridLabel = new Label("DTS Data");
	private Image image = new Image();
	private Image dts_image;
	private String currentTime = null;
	private String query2;
	private String[][] dts_data_for_plotting;

	public DTSView() throws SQLException {
		
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
		Set_Ui();
		listBasinFromDB();
		basinChangeListner();
		wellComboBoxListener();
		wellboreComboBoxListener();
	

		startDepthComboBox.addValueChangeListener(event -> {

			int startDepthIndex = depthsList.indexOf(startDepthComboBox.getValue());
			List<DTS_Depth> depthsSubList = depthsList.subList(startDepthIndex + 1, depthsList.size());
			endDepthComboBox.setItems(depthsSubList);
			endDepthComboBox.setItemLabelGenerator(DTS_Depth::getDepthLabel);
		});
		
		endDepthComboBox.addValueChangeListener(event -> {
			
			selectedDepthsList = depthsList.subList(depthsList.indexOf(startDepthComboBox.getValue()), 
					depthsList.indexOf(endDepthComboBox.getValue()) + 1);
			
		});

		starttime.addValueChangeListener(event1 -> {

			tracecount.clear();

			int startTimeIndex = timeStampsComboBoxList.indexOf(starttime.getValue());
			timestampSubList = timeStampsComboBoxList.subList(startTimeIndex + 1, timeStampsComboBoxList.size());
			endtime.setItems(timestampSubList);
			endtime.setItemLabelGenerator(DTS_Timestamp::getTimestamp);

		});

		endtime.addValueChangeListener(event2 -> {
			DTS_Timestamp endtimeValue = endtime.getValue();

			if (endtimeValue != null) {
				timeStampsList = timeStampsComboBoxList.subList(
						timeStampsComboBoxList.indexOf(starttime.getValue()),
						timeStampsComboBoxList.indexOf(endtime.getValue()) + 1);
				tracecount.setValue(Integer.valueOf(timeStampsList.size()).toString());
			}

		});
		SearchButtonEvent();
		}
	}

	private void _initDepthAndTimeComboBoxData() {

		String query = "select * from dts_db.dts_fiber_length_112233";
		String query1 = "select index, timestamp from dts_db.dts_data_112233";
		depthsList.clear();
		timeStampsComboBoxList.clear();
		try {
			depthsList = DTSQueries.getdepths(query);
			timeStampsComboBoxList = DTSQueries.getTimestamp_ids(query1);
			startDepthComboBox.setItems(depthsList);
			startDepthComboBox.setItemLabelGenerator(DTS_Depth::getDepthLabel);
			starttime.setItems(timeStampsComboBoxList);
			starttime.setItemLabelGenerator(DTS_Timestamp::getTimestamp);
			totalnumberoftraces.setValue(Integer.valueOf(timeStampsComboBoxList.size()).toString());
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}

	private void SearchButtonEvent() throws SQLException {
		
		searchButton.addClickListener(event -> {
//			String query = "select id from dts_db.dts_fiber_length_112233 where depth between "
//					+ startDepthComboBox.getValue() + " and " + endDepthComboBox.getValue();
//			String query1 = "select timestamp from dts_db.dts_data_112233 where timestamp " + "between \""
//					+ starttime.getValue() + "\" and \"" + endtime.getValue() + "\" order by timestamp";
			
			ExecutorService executor = Executors.newCachedThreadPool();
			PB_Progress_Notification notificatiion = new PB_Progress_Notification();
			String dtsimage = PetrahubNotification_Utilities.getInstance().dtsdataAndImage();
			executor.submit(() -> {

				ui.access(() -> {

					notificatiion.setImage("info");
					notificatiion.setText(dtsimage);
					notificatiion.open();
				});


			query2 = "select ";
			int i;
			for (i = 0; i < selectedDepthsList.size(); i++) {
				if (i == selectedDepthsList.size() - 1) {
					query2 = query2 + " `" + selectedDepthsList.get(i).getDepth_id().toString() + "` ";
				} else {

					query2 = query2 + " `" + selectedDepthsList.get(i).getDepth_id().toString() + "`, ";
				}
			}
			query2 = query2 + " from dts_db.dts_data_112233 where index " + "between \"" + starttime.getValue().getIndex().toString()
					+ "\" and \"" + endtime.getValue().getIndex().toString() + "\" order by index";

			int height = timeStampsList.size();
			int width = selectedDepthsList.size();
			try {
				dts_data = DTSQueries.getDTSData(query2, height, width);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}

			System.out.println("");
         ui.access(()->{
			populateGrid();
			notificatiion.close();
         });
			});
		});

	}

	private void populateGrid() {
		searchResultGrid.removeAllColumns();
		int height = timeStampsList.size();
		int width = selectedDepthsList.size();
		String[][] dts_data_for_plotting = new String[width][height]; // to take transpose of dts_data
		currentTime = String.valueOf(System.currentTimeMillis());
		for (int row = 0; row < dts_data.length; row++) {
			for (int col = 0; col < dts_data[row].length; col++) {
				dts_data_for_plotting[col][row] = dts_data[row][col];
			}
		}
		
		List <Double> depths = new ArrayList<Double>();
		for (DTS_Depth depthdata : selectedDepthsList) {
			depths.add(depthdata.getDepth());
		}
		List<DTS_GridData> gridData = DTS_GridData_Provider.createDtsDataProvider(depths, dts_data_for_plotting);

		searchResultGrid.addColumn(DTS_GridData::getIndex).setHeader("Index").setFlexGrow(0).setWidth("100px")
				.setFrozen(true);
		searchResultGrid.addColumn(DTS_GridData::getDepth).setHeader("Depth").setFlexGrow(0).setWidth("100px")
				.setFrozen(true);
		int numTraces = timeStampsList.size();

		for (int i = 0; i < numTraces; i++) {
			int[] numArr = { i };

			searchResultGrid.addColumn(Blgz_DTS_Depth_Data -> Blgz_DTS_Depth_Data.getData().get(numArr[0]))
					.setHeader(timeStampsList.get(i).getTimestamp()).setFlexGrow(0).setWidth("150px");
		}

		searchResultGrid.setItems(gridData);
		createImage();
	}

	private void createImage() {
		
		int height = timeStampsList.size();
		int width = selectedDepthsList.size();
		String[][] dts_data_for_plotting = new String[width][height]; // to take transpose of dts_data
		currentTime = String.valueOf(System.currentTimeMillis());
		for (int row = 0; row < dts_data.length; row++) {
			for (int col = 0; col < dts_data[row].length; col++) {
				dts_data_for_plotting[col][row] = dts_data[row][col];
			}
		}
		try {

			byte[] imageBytes = new PH_DTSImage_Render().renderImage(dts_data_for_plotting);

			StreamResource resource = new StreamResource("dts_img", () -> new ByteArrayInputStream(imageBytes));
			dts_image = new Image(resource, "dts_img");
			imageLayout.removeAll();
			imageLayout.add(imageLabel, dts_image);
			dts_image.setWidthFull();
			dts_image.setHeight("80%");
		} catch (IOException e) {
			
			e.printStackTrace();
		}

	}

	public byte[] extractBytes(File ImageName) throws IOException {
		BufferedImage bufferedImage = ImageIO.read(ImageName);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "png", bos);
		return bos.toByteArray();
	}

	private void Set_Ui() {

		this.setSizeFull();
		this.setSpacing(false);
		this.setPadding(false);
		vLayout.setSpacing(false);
		vLayout.setPadding(false);
		vLayout.setSizeUndefined();
		
		
		gridLayout.add(gridLabel, searchResultGrid);
		imageLayout.add(imageLabel);
		mainLayout.setSizeFull();
		mainLayout.add(gridLayout, imageLayout);
		gridLayout.setHeight("80%");
		gridLayout.setWidth("50%");
		imageLayout.setWidth("50%");
		
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
		createEditorLayout();
//		String projectname = (String) VaadinService.getCurrentRequest().getWrappedSession()
//				.getAttribute("project_name");
//		if (projectname == null) {
//		
//			PB_Progress_Notification notification = new PB_Progress_Notification();
//			
//			notification.setText("Please first create the project");
//			notification.open();
//			notification.setDuration(3000);
//		} else {
//			UI_Update.updateEnable_topBar();
		this.add(mainLayout, vLayout);
		this.expand(mainLayout);
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

	private void createEditorLayout() {

		Div editorLayoutDiv = new Div();
		editorLayoutDiv.setHeightFull();
		editorLayoutDiv.setClassName("flex flex-col");
		editorLayoutDiv.setWidth("250px");

		FormLayout layout1 = new FormLayout();
		layout1.setWidth("200px");

		layout1.addFormItem(basincombobox, "Basin");

		layout1.addFormItem(wellcombobox, "Well");

		layout1.addFormItem(wellborecombobox, "Wellbore");

		layout1.addFormItem(starttime, "Start Time");

		layout1.addFormItem(endtime, "End Time");

		layout1.addFormItem(tracecount, "Trace Count:");
		tracecount.setWidth("190px");

		layout1.addFormItem(totalnumberoftraces, "Total Number Of Traces:");
		totalnumberoftraces.setWidth("190px");
		editorLayoutDiv.add(layout1);
		editorLayoutDiv.getStyle().set("margin-left", "20px");
		layout1.addFormItem(startDepthComboBox, "Start Depth");
		layout1.addFormItem(endDepthComboBox, "End Depth");

		Image searchImage = new Image("images" + File.separator + "search24.png", "Search");
		searchButton.setIcon(searchImage);

		VerticalLayout dtsVerticalLayout = new VerticalLayout();
		dtsVerticalLayout.add(searchButton);
		dtsVerticalLayout.setClassName("p-l flex-grow");
		editorLayoutDiv.add(dtsVerticalLayout);

		dtsVerticalLayout.setClassName("left_border");
		dtsVerticalLayout.setHeight("825px");

		mLayout.add(editorLayoutDiv);

	}

	private void listBasinFromDB() throws SQLException {
		ExecutorService executor = Executors.newCachedThreadPool();
		PB_Progress_Notification notificatiion = new PB_Progress_Notification();
		String populateBasin = PetrahubNotification_Utilities.getInstance().populatingBasins();
		executor.submit(() -> {

			ui.access(() -> {

				notificatiion.setImage("info");
				notificatiion.setText(populateBasin);
				notificatiion.open();
			});
		ListDataProvider<BasinViewGridInfo> Basin;
		try {
			Basin = new WellRegistryQuries().convertToListBasin();
			basincombobox.setItemLabelGenerator(BasinViewGridInfo::getBasinName);
			Basin.setSortOrder(BasinViewGridInfo::getBasinName, aSSENDINGDirection);
			basincombobox.setDataProvider(Basin);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ui.access(()->{
			notificatiion.close();
		});
		
		});

	}

	private void basinChangeListner() {

		basinComboBoxListerner = new ValueChangeListener<HasValue.ValueChangeEvent<BasinViewGridInfo>>() {

			@Override
			public void valueChanged(ValueChangeEvent<BasinViewGridInfo> event) {
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
						
						e.printStackTrace();
					}
					wellcombobox.setItemLabelGenerator(WellListInfo::getWellName);
					  wellDataProvider.setSortOrder(WellListInfo::getWellName, aSSENDINGDirection);
					wellcombobox.setDataProvider(wellDataProvider);
				}
				ui.access(()->{
					notificatiion.close();
				});
				});
			}
		};
		basincombobox.addValueChangeListener(basinComboBoxListerner);
	}

	private void wellComboBoxListener() {
	
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
				selectedWell = event.getValue();
				if (selectedWell != null) {

					try {
						wellboreDataProvider = new WellRegistryQuries().convertToListWellbore(selectedWell.getWellID(),
								selectedWell.getBasinID());
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
					wellborecombobox.setItemLabelGenerator(WellboreListInfo::getWellboreName);
					wellboreDataProvider.setSortOrder(WellboreListInfo::getWellboreName,aSSENDINGDirection);
					wellborecombobox.setDataProvider(wellboreDataProvider);

				}
				ui.access(()->{
					notificatiion.close();
				});
				});
			}
		};
		wellcombobox.addValueChangeListener(wellComboBoxListerner);
	}

	/**
	 * wellbore combobox listner
	 */
	private void wellboreComboBoxListener() {
		

		wellboreComboBoxListerner = new ValueChangeListener<HasValue.ValueChangeEvent<WellboreListInfo>>() {

			@Override
			public void valueChanged(ValueChangeEvent<WellboreListInfo> event) {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				String populatingStarttime = PetrahubNotification_Utilities.getInstance().populatingStarttime();
				executor.submit(() -> {

					ui.access(() -> {

						notificatiion.setImage("info");
						notificatiion.setText(populatingStarttime);
						notificatiion.open();
					});
				selectedWellbore = event.getValue();
				// listDataIntoQCGrid();
				_initDepthAndTimeComboBoxData();
				
				ui.access(()->{
					notificatiion.close();
				});
				});
			}
		
		};
		wellborecombobox.addValueChangeListener(wellboreComboBoxListerner);
		

	}

	public class PB_FlexLayout extends FlexLayout implements Focusable {

		public PB_FlexLayout() {
			this.setTabIndex(0);
		}

	}
}

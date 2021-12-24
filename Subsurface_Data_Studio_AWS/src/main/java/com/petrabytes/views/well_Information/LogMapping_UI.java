package com.petrabytes.views.well_Information;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.basin.BasinViewGridInfo;
import com.petrabytes.views.wellRegistryQuery.WellRegistryQuries;
import com.petrabytes.views.wellRegistryQuery.WellboreListInfo;
import com.petrabytes.views.wells.WellListInfo;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.VaadinService;

public class LogMapping_UI extends PetrabyteUI_Dialog{
	
	private Grid<BasinViewGridInfo> basinGrid = new Grid<>();
	private Grid<WellListInfo> wellGrid = new Grid<>();
	private Grid<WellboreListInfo> wellboreGrid = new Grid<>();
	protected VerticalLayout mainLayout = new VerticalLayout();
	public ListDataProvider<BasinViewGridInfo> basinDataProvider;
	public ListDataProvider<WellListInfo> wellDataProvider;
	public ListDataProvider<WellboreListInfo> wellboreDataProvider;

    private Long basinID;
    private Long wellID;
	private ValueChangeListener<ValueChangeEvent<BasinViewGridInfo>> basinListListerner = null;
	private ValueChangeListener<ValueChangeEvent<WellListInfo>> wellListListerner = null;
	
	//public Basin_Registry_Info selectedBasin = null;
	public Well_Registry_Info selectedWell = null;

	private Data_Mapping_Info _selectedMapValue;
	private Grid<Data_Mapping_Info> _mappinggrid;
	private String companyName = null;


	public LogMapping_UI(Data_Mapping_Info selectedMapValue,Grid<Data_Mapping_Info> mappinggrid) throws SQLException {
		this._selectedMapValue = selectedMapValue;
		this._mappinggrid = mappinggrid;
		this.setTitle("Map Data to Well"); 
		this.setImage("images"+File.separator+"mapping24.png");
		this.setButtonName("Map");
		

		this.content.add(mainLayout);
		
		
		
		
		mainLayout.setWidth("1000px");
		mainLayout.setHeight("650px");
		
		
		listBainFromDB();
		
		Basingridchangelistner();
		wellgridchangeListner();
		 mapbutonListner();
		setUI();
		
		
	}

	

	private void setUI() {
		// TODO Auto-generated method stub
		

		HorizontalLayout gridLayout = new HorizontalLayout();
		basinGrid.setHeight("600px");
		basinGrid.setWidth("300px");
		basinGrid.setClassName("skygridz-basingrid");

		basinGrid.addColumn(BasinViewGridInfo::getBasinName).setHeader("Basin List");
		basinGrid.setSelectionMode(SelectionMode.SINGLE);
		wellGrid.setHeight("600px");
		wellGrid.setWidth("300px");
		wellGrid.setClassName("skygridz-wellGrid");
		wellGrid.addColumn(WellListInfo::getWellName).setHeader("Well List");
		wellGrid.setSelectionMode(SelectionMode.SINGLE);

		wellboreGrid.setHeight("600px");
		wellboreGrid.setWidth("300px");
		wellboreGrid.setClassName("skygridz-wellGrid");
		wellboreGrid.addColumn(WellboreListInfo::getWellboreName).setHeader("Wellbore List");
		wellboreGrid.setSelectionMode(SelectionMode.SINGLE);
		gridLayout.add(basinGrid, wellGrid, wellboreGrid);
		mainLayout.add(gridLayout);


	}
	
	private void listBainFromDB() {
		
		try {

			ListDataProvider<BasinViewGridInfo> poplateDataBasin = new WellRegistryQuries().convertToListBasin();

			basinGrid.setDataProvider(poplateDataBasin);

			System.out.println("Refresh");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	
	}
	
	private void Basingridchangelistner() {

		basinGrid.addSelectionListener(listener -> {
			BasinViewGridInfo row = basinGrid.asSingleSelect().getValue();

		            basinID =   row.getBasinID();
		         //  System.out.println(BasinID);

		            try {
						listWellFromDB();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		});
	}
	
	private void wellgridchangeListner() {
		
		wellGrid.addSelectionListener( event ->{
			WellListInfo row = wellGrid.asSingleSelect().getValue();
			wellID = row.getWellID();
			Long basinId = row.getBasinID();
			ListDataProvider<WellboreListInfo> poplateDataBasin;
			try {
				poplateDataBasin = new WellRegistryQuries()
						.convertToListWellbore(wellID, basinId);
				wellboreGrid.setDataProvider(poplateDataBasin);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			
		});
	}
	
	private void mapbutonListner() {
		//wellboreGrid.addSelectionListener(listener ->{
		this.saveButton.addClickListener(listener ->{
		if(wellboreGrid.asSingleSelect().getValue() == null) {
			PB_Progress_Notification notificatiion = new PB_Progress_Notification();
			String selectARow = PetrahubNotification_Utilities.getInstance().selectArowFromgrid();
			notificatiion.setImage("info");
			notificatiion.setText(selectARow);
			notificatiion.setDuration(4000);
			notificatiion.open();
		}else {
			WellboreListInfo row = wellboreGrid.asSingleSelect().getValue();
			String wellboreName = row.getWellboreName();
			String wellname = row.getWellNmae();
			String basinName = row.getBasinName();
			String wellboreID = row.getWellboreID().toString();
			String wellId = row.getWellID().toString();
			String basinId = row.getBasinID().toString();
			String map = "YES";
			String filename =  _selectedMapValue.getFilename();
			
			
			
			try {
				new WellLogsDataMappingQuery().updateWelllogsHeader(wellboreID, wellboreName, wellId, wellname, basinId, basinName, map, filename);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {

				ArrayList<Data_Mapping_Info> poplatemaping = new WellLogsDataMappingQuery().allmnemonic();
				
				

				_mappinggrid.setItems(poplatemaping);

				System.out.println("Refresh");

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			
			
		}
		this.close();
			
		});
	}
	
	private void listWellFromDB() throws SQLException {
		if (basinID != null) {
			ListDataProvider<WellListInfo> poplateDataBasin = new WellRegistryQuries()
					.convertToListWell(basinID);

			wellGrid.setDataProvider(poplateDataBasin);;
			// basinGrid.getDataProvider().refreshAll();
			System.out.println("Refresh");
		} else {
			 PB_Progress_Notification notification = new PB_Progress_Notification();
			 String selectBasin =     PetrahubNotification_Utilities.getInstance().selectBasinWellboreView();
	  			notification.setText(selectBasin);
	  			notification.open();
	  			notification.setDuration(3000);
		}
		
	}
	
	
	
	
	
	
	private void listWellboreFromDB() {
		
		this.saveButton.addClickListener(listener ->{
			
			
		});
		
	}
	
	
	/**
	 * basin combo box listener
	 */
	
	
	
	/**
	 * well combobox listner
	 */
	
	

	
}
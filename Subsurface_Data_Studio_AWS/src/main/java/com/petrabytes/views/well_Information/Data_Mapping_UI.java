package com.petrabytes.views.well_Information;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.basin.BasinViewGridInfo;
import com.petrabytes.views.wellRegistryQuery.WellRegistryQuries;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class Data_Mapping_UI extends VerticalLayout {
	
	private Button filterButton;
	private Button MaptowellButton;
	private ComboBox mapCombobox;
	private Button ClearButton;
	private  Grid<Data_Mapping_Info> mappinggrid=new Grid();
	private final UI ui = UI.getCurrent();

	
	private ValueChangeListener<ValueChangeEvent<?>> basinValueChangeLister;
	private ValueChangeListener<ValueChangeEvent<?>> WellValueChangeLister;
	public Data_Mapping_UI() {
		
		setUI();
		upDateTable();
		mappingButtonClickaction();
		
	}
	
	
	private void setUI() {
		this.setSizeFull();
		// TODO Auto-generated method stub
		HorizontalLayout headerLayout = new HorizontalLayout();
		
		 MaptowellButton = new Button();
		 MaptowellButton.setId("bgz_logs_MaptowellButton");
		 Image mapToWellimage = new Image("images"+File.separator+"mapping24.png", "Map");
		 MaptowellButton.setIcon(mapToWellimage);
		 MaptowellButton.getElement().setAttribute("title", "Map to Well");
		
		 mapCombobox = new ComboBox<String>();
		 mapCombobox.setId("bgz_logs_mapCombobox");
		 mapCombobox.setItems("Mapped","Unmapped");
		 
		
		headerLayout.add(MaptowellButton);
		mappinggrid.addColumn(Data_Mapping_Info::getExtension).setHeader("Extension").setAutoWidth(true);
		mappinggrid.addColumn(Data_Mapping_Info::getFilename).setHeader("Filename").setAutoWidth(true);
		mappinggrid.addColumn(Data_Mapping_Info::getBasin).setHeader("Basin").setAutoWidth(true);
		mappinggrid.addColumn(Data_Mapping_Info::getWell).setHeader("Well").setAutoWidth(true);
		mappinggrid.addColumn(Data_Mapping_Info::getWellbore).setHeader(" Wellbore").setAutoWidth(true);

		mappinggrid.addColumn(Data_Mapping_Info::getLogs).setHeader("Logs").setAutoWidth(false);
		mappinggrid.addColumn(Data_Mapping_Info::getMapped).setHeader("Mapped").setAutoWidth(true);
		mappinggrid.getStyle().set("border-style", "groove");
		mappinggrid.getStyle().set("border-width", "thin");
		this.add(headerLayout,mappinggrid);
		
		populateMapinGrid();
	}
	
	

	
	
	public void upDateTable() {
		// TODO Auto-generated method stub
//		List<Blgz_Scan_HB> scanList = coreService.getScanDataByCategories("las");
//		List<Data_Mapping_Info> mappingList = new ArrayList();
//		
//		
//		for (Blgz_Scan_HB scanData : scanList) {
//			Data_Mapping_Info mappingInfo = new Data_Mapping_Info();
//			
//			String extenstion = scanData.getExtension();
//			if (extenstion != null) {
//				mappingInfo.setExtension(scanData.getExtension());
//			}
//			String fileName = scanData.getFilename();
//			if (fileName != null) {
//				mappingInfo.setFilename(scanData.getFilename());
//			}
//			String wellName = scanData.getWellname();
//			if (wellName != null) {
//				mappingInfo.setWell(scanData.getWellname());
//			}
//			if (scanData.getWellborename() != null) {
//				mappingInfo.setWellbore(scanData.getWellborename());
//			}
//			if (scanData.getBasin() != null) {
//				mappingInfo.setBasin(scanData.getBasin());
//			}
//			if (scanData.getLatitude() != null) {
//				String lat = String.valueOf(scanData.getLatitude());
//				mappingInfo.setLatitude(lat);
//			}
//			if (scanData.getLongitude() != null) {
//				String lng = String.valueOf(scanData.getLongitude());
//				mappingInfo.setLongitude(lng);
//			}
//			if (scanData.getLogs() != null) {
//				mappingInfo.setLogs(scanData.getLogs());
//			}
//			if (scanData.getDirectory() != null) {
//				mappingInfo.setDirectory(scanData.getDirectory());
//			}
//			if (scanData.getMapped() != null) {
//				mappingInfo.setMapped(scanData.getMapped());
//			}
//			mappingInfo.setID(scanData.getId());
//			
//			mappingList.add(mappingInfo);
//		}
//
//		mappinggrid.setItems(mappingList);

	}
	
	private void populateMapinGrid() {

		try {

			ArrayList<Data_Mapping_Info> poplatemaping = new WellLogsDataMappingQuery().allmnemonic();
			
			

			mappinggrid.setItems(poplatemaping);

			System.out.println("Refresh");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void mappingButtonClickaction() {
		// TODO Auto-generated method stub
		MaptowellButton.addClickListener(event -> {
		
			

			//executor.submit(() -> {

//				ui.access(() -> {
//
//					notificatiion.setImage("info");
//					notificatiion.setText("Querying Deltalake: ");
//					notificatiion.setDuration(4000);
//					notificatiion.open();
//				});
			if(mappinggrid.asSingleSelect().getValue() == null) {
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				String selectARow = PetrahubNotification_Utilities.getInstance().selectArowFromgrid();
				notificatiion.setImage("info");
				notificatiion.setText(selectARow);
				notificatiion.setDuration(4000);
				notificatiion.open();
			}else {
				Data_Mapping_Info selectedMapValue = mappinggrid.asSingleSelect().getValue();
				LogMapping_UI mappingDialog;
				try {
					mappingDialog = new LogMapping_UI(selectedMapValue,mappinggrid);
					mappingDialog.setCloseOnOutsideClick(false);
					mappingDialog.open();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				
				
				
			}
		
		//	});
			
		});
		
	}

	

}

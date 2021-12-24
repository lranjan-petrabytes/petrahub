package com.petrabytes.views.wellbore;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.basin.BasinView;
import com.petrabytes.views.basin.BasinViewGridInfo;
import com.petrabytes.views.wellRegistryQuery.WellRegistryQuries;
import com.petrabytes.views.wellRegistryQuery.WellboreListInfo;
import com.petrabytes.views.wells.WellListInfo;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;

public class Delete_Wellbore_popup_UI extends PetrabyteUI_Dialog{

	
	private Label text = new Label("Remove Wellbore");
	private Grid<WellboreListInfo> wellboreGrid = null;
	private SortDirection aSSENDINGDirection = SortDirection.ASCENDING;
	private final UI ui = UI.getCurrent();
	public  Delete_Wellbore_popup_UI(Grid<WellboreListInfo> wellboreGrid) {
		this.wellboreGrid = wellboreGrid;
		setUI();
		deleteWellbore() ;
	}

	private void setUI() {

		WellboreListInfo wellboreInfo = View_Update.getWellboreInfo();
		if (wellboreInfo !=null) {
		Label message3 = new Label("Wellbore information will be deleted: "+wellboreInfo.getWellboreName()+" ");
		

		message3.getStyle().set("width", "100%");
		message3.getStyle().set("margin-top", "3px");
		
	//	basinNameField.setWidth("300px");
		this.setImage("icons" + File.separator +File.separator + "16x" +  File.separator +"delete16.png");
		VerticalLayout layout = new VerticalLayout(message3);
		layout.getStyle().set("padding-bottom", "0px");
		layout.setWidth("330px");
		mainLayout.setWidth("350px");
		mainLayout.setHeight("210px");
		text.getStyle().set("margin-bottom", "-15px");
		this.content.add(layout);
		this.setTitle("Remove Wellbore");
		this.saveButton.setText("OK");
		
		}
		
		
	}
	
	private void deleteWellbore() {

		this.saveButton.addClickListener(event -> {

			
				WellboreListInfo wellboreInfo = View_Update.getWellboreInfo();
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				String deletewellbore = PetrahubNotification_Utilities.getInstance().deletingWellbore();
				executor.submit(() -> {

					ui.access(() -> {

						notificatiion.setImage("info");
						notificatiion.setText(deletewellbore);
						notificatiion.open();
					});	
				if (wellboreInfo != null) {
				try {
					new WellRegistryQuries().deleteDataInWellboreTable(wellboreInfo.getBasinID(),wellboreInfo.getBasinName(),
							wellboreInfo.getWellID(),	wellboreInfo.getWellNmae(),wellboreInfo.getWellboreID(),wellboreInfo.getWellboreName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					
			
				}
			
				ui.access(()->{
			populateWellboreGrid();
	
			notificatiion.close();
		});
			this.close();
				});
		});
	}

	private void populateWellboreGrid() {
		
//		WellListInfo wellInfo = View_Update.getWellInfo();
//		
//		BasinViewGridInfo basinfo = View_Update.getBasinInfo();
		WellboreListInfo wellboreInfo = View_Update.getWellboreInfo();
		try {

			ListDataProvider<WellboreListInfo> poplateDataBasin = new WellRegistryQuries().convertToListWellbore(wellboreInfo.getWellID(),wellboreInfo.getBasinID());

//			UI ui = getUI().get();
//			ui.access(() -> {
			poplateDataBasin.setSortOrder(WellboreListInfo::getWellboreName, aSSENDINGDirection);
				wellboreGrid.setDataProvider(poplateDataBasin);
		//	});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

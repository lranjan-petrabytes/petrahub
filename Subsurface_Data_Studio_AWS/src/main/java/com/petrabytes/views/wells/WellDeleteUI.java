package com.petrabytes.views.wells;

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
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;

public class WellDeleteUI extends PetrabyteUI_Dialog {
	private Label text = new Label("Remove Well");
	private SortDirection aSSENDINGDirection = SortDirection.ASCENDING;
	private WellListInfo WellListGrid = null;
	private final UI ui = UI.getCurrent();
    private Grid<WellListInfo> wellListGrid2 = null;
	public WellDeleteUI(WellListInfo wellListInfo, Grid<WellListInfo> wellListGrid2) {
		// TODO Auto-generated constructor stub
		this.WellListGrid = wellListInfo;
		this.wellListGrid2 = wellListGrid2;
		setUI();
		deleteWell();
	}

	private void setUI() {
		WellListInfo wellinfo = View_Update.getWellInfo();
		if(wellinfo !=null) {
		Label message1 = new Label("Well information will be deleted: "+wellinfo.getWellName()+" ");
		Label message2 = new Label("All  Wellbore in the Well will be deleted.");

		Label message3 = new Label("Are you sure you want to proceed? ");

		message1.getStyle().set("width", "100%");
		message2.getStyle().set("width", "100%");
		message2.getStyle().set("margin-top", "3px");
		message3.getStyle().set("width", "100%");
		message3.getStyle().set("margin-top", "3px");

		// basinNameField.setWidth("300px");
		this.setImage("icons" + File.separator + File.separator + "16x" + File.separator + "delete16.png");
		VerticalLayout layout = new VerticalLayout(message1, message2, message3);
		layout.getStyle().set("padding-bottom", "0px");
		layout.setWidth("330px");
		mainLayout.setWidth("350px");
		mainLayout.setHeight("210px");
		text.getStyle().set("margin-bottom", "-15px");
		this.content.add(layout);
		this.setTitle("Remove Well");
		this.saveButton.setText("OK");
		}

	}

	private void deleteWell() {

		this.saveButton.addClickListener(event -> {
			WellListInfo wellinfo = View_Update.getWellInfo();
			ExecutorService executor = Executors.newCachedThreadPool();
			PB_Progress_Notification notificatiion = new PB_Progress_Notification();
			 String deleteWell = PetrahubNotification_Utilities.getInstance().deletingWellInDeltalakeInWellDelView();
			executor.submit(() -> {

				ui.access(() -> {

					notificatiion.setImage("info");
					notificatiion.setText(deleteWell);
					notificatiion.open();
				});	
			try {
				new WellRegistryQuries().deleteDataInWellTable(wellinfo.getBasinID(),wellinfo.getBasin(),
						wellinfo.getWellID(),wellinfo.getWellName());
				new WellRegistryQuries().deleteWellboreForDeletedWell(wellinfo.getWellID(),wellinfo.getWellName());
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ui.access(() -> {
			populateBasinGrid();
			notificatiion.close();
			});	
			this.close();
			});
		});
	}

	private void populateBasinGrid() {

		try {
			
			if (WellListGrid.getBasinID() != null) {
				ListDataProvider<WellListInfo> poplateDataBasin = new WellRegistryQuries()
						.convertToListWell( WellListGrid.getBasinID());
				poplateDataBasin.setSortOrder(WellListInfo::getWellName, aSSENDINGDirection);
			
				ui.access(() -> {
					 wellListGrid2.setDataProvider(poplateDataBasin);
				});
			} else {
				
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}

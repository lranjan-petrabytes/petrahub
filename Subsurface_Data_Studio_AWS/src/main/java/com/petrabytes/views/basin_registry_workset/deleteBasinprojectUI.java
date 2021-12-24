package com.petrabytes.views.basin_registry_workset;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.basin.BasinView;
import com.petrabytes.views.basin.BasinViewGridInfo;

import com.petrabytes.views.wellRegistryQuery.WellRegistryQuries;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.server.VaadinService;

public class deleteBasinprojectUI extends PetrabyteUI_Dialog {

	private Label text = new Label("Remove Basin");
	private SortDirection aSSENDINGDirection = SortDirection.ASCENDING;
	private Grid<BasinViewGridInfo> basinGrid = null;
	private final UI ui = UI.getCurrent();
	private BasinViewGridInfo basinfo =null;
	public deleteBasinprojectUI(Grid<BasinViewGridInfo> basinGrid) {
		// TODO Auto-generated constructor stub
		this.basinGrid = basinGrid;
		basinfo	= View_Update.getBasinInfo();
		setUI();
		deleteBasin();
	}

	private void setUI() {
		BasinViewGridInfo basinfo = View_Update.getBasinInfo();
        if (basinfo !=null) {
		Label message1 = new Label("Basin information will be deleted: "+basinfo.getBasinName()+"");
		
        
		Label message2 = new Label("All Wells and Wellbore in the Basin  will be deleted.");

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
		layout.setWidth("420px");
		mainLayout.setWidth("420px");
		mainLayout.setHeight("230px");
		text.getStyle().set("margin-bottom", "-15px");
		this.content.add(layout);
		this.setTitle("Remove Basin");
		this.saveButton.setText("OK");
		
        }

	}

	private void deleteBasin() {

		this.saveButton.addClickListener(event -> {
			ExecutorService executor = Executors.newCachedThreadPool();
			PB_Progress_Notification notificatiion = new PB_Progress_Notification();

			executor.submit(() -> {

				ui.access(() -> {

					notificatiion.setImage("info");
					notificatiion.setText("Deleting Basin");
					notificatiion.open();
				});
		
		
			
			try {
				new WellRegistryQuries().deleteDataInBasinTable(basinfo.getBasinID(), basinfo.getBasinName());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				new WellRegistryQuries().deleteWellForDeletedBasin(basinfo.getBasinID(), basinfo.getBasinName());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				new WellRegistryQuries().deleteWellboreForDeletedBasin(basinfo.getBasinID(), basinfo.getBasinName());
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ui.access(() -> {
			populateBasinGrid();
			notificatiion.close();
			});
			
			});

			this.close();

		
		});

	}

	private void populateBasinGrid() {

		try {

			ListDataProvider<BasinViewGridInfo> poplateDataBasin = new WellRegistryQuries().convertToListBasin();
			poplateDataBasin.setSortOrder(BasinViewGridInfo::getBasinName, aSSENDINGDirection);
		
			ui.access(() -> {
			//	basinGrid.setItems(poplateDataBasin);
				basinGrid.setDataProvider(poplateDataBasin);
			});

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}

package com.petrabytes.views.basin_registry_workset;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang3.exception.ExceptionUtils;

//import com.example.application.views.main.MainView;
import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.project.util.ProjectQueries;
import com.petrabytes.project.util.ProjectSettingsInfo;
import com.petrabytes.project.util.ViewsSettingsInfo;
import com.petrabytes.project.util.WellLogsProjectViewsSettingsInfo;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.MainLayout;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.basin.BasinView;
import com.petrabytes.views.basin.BasinViewGridInfo;

import com.petrabytes.views.wellRegistryQuery.WellRegistryQuries;
import com.petrabytes.views.wells.WellListInfo;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;
import com.vaadin.flow.server.VaadinService;

public class basinCreateProjectUI extends PetrabyteUI_Dialog {

	private Label text = new Label("Enter Basin Name");
	public static TextField basinNameField = new TextField();
	private final UI ui = UI.getCurrent();
	private Random uniqueID = new Random();
	private Grid<BasinViewGridInfo> basinGrid = null;
	private Boolean _editFlag;
	private SortDirection aSSENDINGDirection = SortDirection.ASCENDING;
	private BasinViewGridInfo basinfo = null;
	public basinCreateProjectUI(boolean editFlag,Grid<BasinViewGridInfo> basinGrid) {
		// TODO Auto-generated constructor stub
		this._editFlag = editFlag;
		this.basinGrid = basinGrid;
		 basinfo = View_Update.getBasinInfo();
		if (_editFlag == true) {
			
			this.setImage("icons" + File.separator + "16x" + File.separator + "edit_16.png");
			this.setTitle("Edit Basin");

		} else {
			this.setImage("icons" + File.separator + "purple-24x" + File.separator + "add-24x.png");
			this.setTitle("New Basin");
		basinNameField.setValue("Name");
		}
		setUI();
		insertBasin();
	}

	private void setUI() {
		basinNameField.setWidth("300px");

		VerticalLayout layout = new VerticalLayout(text, basinNameField);
		layout.getStyle().set("padding-bottom", "0px");
		layout.setWidth("330px");
		mainLayout.setWidth("350px");
		mainLayout.setHeight("190px");
		text.getStyle().set("margin-bottom", "-15px");
		this.content.add(layout);
		this.saveButton.setText("OK");
		//this.setTitle("New Basin");
	
		if (_editFlag) {
	
			for (BasinViewGridInfo row : basinGrid.getSelectedItems()) {
				basinNameField.setValue(row.getBasinName());

			}
			
		}
	
	}

	private void insertBasin() {

		this.saveButton.addClickListener(event -> {
			
			if (_editFlag == false) {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
                        String createBasin =     PetrahubNotification_Utilities.getInstance().createBasin();
				executor.submit(() -> {

					ui.access(() -> {

						notificatiion.setImage("info");
						notificatiion.setText(createBasin);
						notificatiion.open();
					});	
			Long basinID = (long) (10000000 + uniqueID.nextInt(90000000));
			String basinName = basinNameField.getValue();
			int isExist = new WellRegistryQuries().BasinExist_ByName(basinName);
			if (isExist == 0) {
			if (basinName.equals("") == false) {

				try {
					new WellRegistryQuries().insertDataInBasin(basinName, basinID);
	
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
               ui.access(()->{
				populateBasinGrid();
				notificatiion.close();
               });
			} else {
				PB_Progress_Notification notificati = new PB_Progress_Notification();
				   String Basincreate =     PetrahubNotification_Utilities.getInstance().basinnotcreated();
				notificati.setText(Basincreate);
				notificati.open();
				notificati.setDuration(4000);

			}
			} else {
				PB_Progress_Notification notificati = new PB_Progress_Notification();
				  String Basincreate =     PetrahubNotification_Utilities.getInstance().basinAlreadyExist();
				notificati.setText(Basincreate);
				notificati.open();
				notificati.setDuration(4000);

			}
				});
			}else {
				ExecutorService executor = Executors.newCachedThreadPool();
				PB_Progress_Notification notificatiion = new PB_Progress_Notification();
				  String editBasin =     PetrahubNotification_Utilities.getInstance().editBasin();
				executor.submit(() -> {

					ui.access(() -> {

						notificatiion.setImage("info");
						notificatiion.setText(editBasin);
						notificatiion.open();
					});
				String UpdateBasinName = basinNameField.getValue();
			//	int isExist = new WellRegistryQuries().BasinExist_ByName(UpdateBasinName);
				if (UpdateBasinName.equals("") == false) {

			
				
				try {
					new WellRegistryQuries().updateDataInBasin(basinfo.getBasinID(), UpdateBasinName);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					new WellRegistryQuries().updateBasinInWellView(basinfo.getBasinID(), UpdateBasinName);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					new WellRegistryQuries().updateBasinInWellbore(basinfo.getBasinID(), UpdateBasinName);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//});
				
		
				ui.access(()->{
				populateBasinGrid();
				notificatiion.close();
				});
				
				} else {
					//Notification.show("Basin name already exist.").addThemeVariants(NotificationVariant.LUMO_CONTRAST);
					 PB_Progress_Notification notification = new PB_Progress_Notification();
			  			
			  			notification.setText("Please enter the Basin name");
			  			notification.open();
			  			notification.setDuration(3000);

				}
			
				});
			}

			this.close();
		});

	}

	private void populateBasinGrid() {

		try {

			ListDataProvider<BasinViewGridInfo> poplateDataBasin = new WellRegistryQuries().convertToListBasin();
			poplateDataBasin.setSortOrder(BasinViewGridInfo::getBasinName, aSSENDINGDirection);
		///	UI ui = getUI().get();
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

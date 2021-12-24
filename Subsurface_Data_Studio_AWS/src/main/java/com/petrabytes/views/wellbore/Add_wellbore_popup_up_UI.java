package com.petrabytes.views.wellbore;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.basin.BasinViewGridInfo;
import com.petrabytes.views.wellRegistryQuery.WellRegistryQuries;
import com.petrabytes.views.wellRegistryQuery.WellboreListInfo;
import com.petrabytes.views.wells.WellListInfo;
import com.petrabytes.views.wells.WellList_view;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.data.provider.SortDirection;

public class Add_wellbore_popup_up_UI extends PetrabyteUI_Dialog  {
	
	

		private Label text = new Label("Enter Wellbore Name");
		protected TextField wellboreNameField = new TextField();
		private Label text2 = new Label("Selected Well Name");
		private TextField wellNameField = new TextField();
		private Label text3 = new Label("Selected Basin Name");
		private TextField basinNameField = new TextField();
		private SortDirection aSSENDINGDirection = SortDirection.ASCENDING;
		private Grid<WellboreListInfo> wellboreGrid = null;
		private Random uniqueID = new Random();
		private Boolean _editFlag;
		private final UI ui = UI.getCurrent();
		WellListInfo wellInfo = null;
		public Add_wellbore_popup_up_UI(boolean editFlag, Grid<WellboreListInfo> wellboreGrid, WellListInfo wellInfo) {
			this.wellInfo = wellInfo;
			this._editFlag = editFlag;
		this.wellboreGrid = wellboreGrid;
		
		if (_editFlag == true) {
			this.setImage("icons" + File.separator + "16x" + File.separator + "edit24x.png");
			this.setTitle("Edit Wellbore");

		} else {
			this.setImage("icons" + File.separator + "purple-24x" + File.separator + "add-24x.png");
			this.setTitle("New Wellbore");
			
		}
			
			setUI();
			insertWellbore();

		}

		private void setUI() {
			wellboreNameField.setWidth("300px");
			wellNameField.setWidth("300px");
			basinNameField.setWidth("300px");
			basinNameField.setReadOnly(true);
			wellNameField.setReadOnly(true);
			VerticalLayout layout = new VerticalLayout(text, wellboreNameField,text2,wellNameField,text3,basinNameField);
		//	WellListInfo wellinfo = View_Update.getWellInfo();
			wellNameField.setValue(wellInfo.getWellName());
			basinNameField.setValue(wellInfo.getBasin());
			layout.getStyle().set("padding-bottom", "0px");
			layout.setWidth("330px");
			mainLayout.setWidth("350px");
			mainLayout.setHeight("390px");
			text.getStyle().set("margin-bottom", "-15px");
			this.content.add(layout);
			this.saveButton.setText("OK");
			
			if (_editFlag) {
				
				for (WellboreListInfo row : wellboreGrid.getSelectedItems()) {
					basinNameField.setValue(row.getBasinName());
					wellboreNameField.setValue(row.getWellboreName());
					wellNameField.setValue(row.getWellNmae());

				}
				
			}
			
		
			
		
		}
		
		private void insertWellbore() {

			this.saveButton.addClickListener(event -> {
				
				if (_editFlag == false) {
					ExecutorService executor = Executors.newCachedThreadPool();
					PB_Progress_Notification notificatiion = new PB_Progress_Notification();
					String addingwellbore = PetrahubNotification_Utilities.getInstance().addingWellbore();
					executor.submit(() -> {

						ui.access(() -> {

							notificatiion.setImage("info");
							notificatiion.setText(addingwellbore);
							notificatiion.open();
						});	

				Long wellboreID = (long) (10000000 + uniqueID.nextInt(90000000));
				String wellboreName = wellboreNameField.getValue();
			//	WellListInfo wellinfo = View_Update.getWellInfo();
				int isExist = new WellRegistryQuries().WellboreExist_ByName(wellboreName,wellInfo.getWellName(),wellInfo.getBasin());
				if (isExist == 0) {
				if (wellboreName.equals("") == false) {

					try {
						
						new WellRegistryQuries().insertDataInWellbore(wellInfo.getBasinID(),wellInfo.getBasin(),
								wellInfo.getWellID(),wellInfo.getWellName(),wellboreID,wellboreName);
				
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					populateWellboreGrid();
                ui.access(()->{
                	notificatiion.close();
                });
				} else {
					//Notification.show("Please first create the Wellbore", 5000, Position.BOTTOM_START)
					//		.addThemeVariants(NotificationVariant.LUMO_CONTRAST);
					PB_Progress_Notification notification = new PB_Progress_Notification();
		  			
		  			notification.setText("Please create a Wellbore.");
		  			notification.open();
		  			notification.setDuration(3000);


				}
				
				} else {
					PB_Progress_Notification notification = new PB_Progress_Notification();
					String wellboreNameExist = PetrahubNotification_Utilities.getInstance().wellboreNameAlreadyExist();
		  			notification.setText(wellboreNameExist);
		  			notification.open();
		  			notification.setDuration(3000);

				}	
					});
				}else {
					WellboreListInfo wellboreInfo = View_Update.getWellboreInfo();
					ExecutorService executor = Executors.newCachedThreadPool();
					PB_Progress_Notification notificatiion = new PB_Progress_Notification();
					String editingwellbore = PetrahubNotification_Utilities.getInstance().editingWellbore();
					executor.submit(() -> {

						ui.access(() -> {

							notificatiion.setImage("info");
							notificatiion.setText(editingwellbore);
							notificatiion.open();
						});	

					String UpdateWellboreName = wellboreNameField.getValue();
					String wellName = wellNameField.getValue();
					String basin = basinNameField.getValue();
			//		int isExist = new WellRegistryQuries().WellboreExist_ByName(UpdateWellboreName,wellName,basin);
					if (UpdateWellboreName.equals("") == false) {
					try {
						
					
						new WellRegistryQuries().updateDataInWellbore(wellboreInfo.getWellboreID(), UpdateWellboreName,wellboreInfo.getWellID(),
								wellboreInfo.getWellNmae(),wellboreInfo.getBasinID(),wellboreInfo.getBasinName());
					
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					} else {
					//	Notification.show("Wellbore name already exist.").addThemeVariants(NotificationVariant.LUMO_CONTRAST);
						 PB_Progress_Notification notification = new PB_Progress_Notification();
						 String fillwellboreName = PetrahubNotification_Utilities.getInstance().fillWellboreName();
				  			notification.setText(fillwellboreName);
				  			notification.open();
				  			notification.setDuration(3000);

					}	
					  ui.access(()->{
					populateWellboreGrid();
			
		                	notificatiion.close();
		                });
					});
				}

				this.close();
			});

		}

		private void populateWellboreGrid() {
		//	WellListInfo wellInfo = View_Update.getWellInfo();
		//	BasinViewGridInfo basinfo = View_Update.getBasinInfo();
			try {
			
				ListDataProvider<WellboreListInfo> poplateDataBasin = new WellRegistryQuries().convertToListWellbore(wellInfo.getWellID(),wellInfo.getBasinID());

//				UI ui = getUI().get();
//				ui.access(() -> {
				poplateDataBasin.setSortOrder(WellboreListInfo::getWellboreName, aSSENDINGDirection);
					wellboreGrid.setDataProvider(poplateDataBasin);
//				});

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}



}

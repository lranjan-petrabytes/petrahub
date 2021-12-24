package com.petrabytes.views.wellbore;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.wellRegistryQuery.WellboreListInfo;
import com.petrabytes.views.wellRegistryQuery.WellboreQueries;
import com.sristy.common.utils.DecimalFormatManager;
import com.sristy.unitsystem.UOM;
import com.sristy.unitsystem.UOMRegistry;
import com.sristy.unitsystem.UnitCategory;
import com.sristy.xkriaextensions.unitsutility.util.XKria_Unit_Conversion_Utility;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;

public class wellCompletionView extends VerticalLayout {

	private ComboBox<String> depthunitComboBox;
	private Button addWellCompletionButton;
	private Button editWellCompletionButton;
	private Button removeWellCompletionButton;
	private Button saveButton;
	private Button clearbutton;
	private Button cancelbutton;

	private Grid<Blgz_Completion_Info> completiongrid = new Grid<>();
	private UnitCategory lenghtUnitCategory = UOMRegistry.getUnitCategoryForName("Measured Depth");
	private String _currentDepthUnit;
	private String bottomMDKey="bottomMD";
	private String topMDkey= "topMD";
	
	

	public wellCompletionView() {

		setSizeFull();
		setUI();
		addCompletionButtonAction();
		editCompletionButtonClickAction();
		populatedepthunitCombobox();
		deleteCompletionRowClickAction();
		saveCompletionDataEvent();
		clearButtonAction();
		setDataToUI();
		_currentDepthUnit = depthunitComboBox.getValue().toString().trim();

	}

	private void setUI() {
		// TODO Auto-generated method stub
		setClassName("wellbore_Padding");
		

		HorizontalLayout unitLayout = new HorizontalLayout();
		Label depthUnitLabel = new Label("Depth Unit");
		depthUnitLabel.getElement().getStyle().set("padding-top", "10px");
		depthunitComboBox = new ComboBox<String>();
		depthunitComboBox.setId("bgz_well_depthunit");

		unitLayout.add(depthUnitLabel, depthunitComboBox);

		completiongrid.addColumn(Blgz_Completion_Info::getId).setHeader("ID").setAutoWidth(true);
		completiongrid.addColumn(Blgz_Completion_Info::getDepth).setHeader("Depth(m)")
				.setAutoWidth(true);
		completiongrid.addColumn(Blgz_Completion_Info::getEndDepth).setHeader("End Depth(m)")
				.setAutoWidth(true);
		completiongrid.addColumn(Blgz_Completion_Info::getCompletion).setHeader("Completion Name").setAutoWidth(true);
		completiongrid.addColumn(Blgz_Completion_Info::getPartID).setHeader("Part ID").setAutoWidth(true);
		completiongrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);

		HorizontalLayout wellCompletionActionLayout = new HorizontalLayout();

		addWellCompletionButton = new Button();
		addWellCompletionButton.setId("bgz_well_addWellCompletion");
		Image createWellComimage = new Image("icons" + File.separator + "16x" + File.separator + "add-16x.png", "add");
		addWellCompletionButton.getElement().setAttribute("title", "Add");
		addWellCompletionButton.setIcon(createWellComimage);

		editWellCompletionButton = new Button();
		editWellCompletionButton.setId("bgz_well_editWellCompletion");
		Image editWellComimage = new Image("icons" + File.separator + "16x" + File.separator + "edit_16.png","Edit");
		editWellCompletionButton.getElement().setAttribute("title", "Edit ");

		editWellCompletionButton.setIcon(editWellComimage);



		removeWellCompletionButton = new Button();
		removeWellCompletionButton.setId("bgz_well_removeWellCompletion");
		Image removeWellComimage = new Image("icons" + File.separator + "16x" + File.separator + "delete16.png", "delete");
		removeWellCompletionButton.getElement().setAttribute("title", "Delete");

		removeWellCompletionButton.setIcon(removeWellComimage);

		wellCompletionActionLayout.add(addWellCompletionButton, editWellCompletionButton, removeWellCompletionButton);

		HorizontalLayout wellCompletionMainLayout = new HorizontalLayout();

		saveButton = new Button("Save");
		saveButton.setId("bgz_well_saveButton");
		saveButton.addThemeVariants(ButtonVariant.LUMO_SMALL,ButtonVariant.LUMO_PRIMARY);

		clearbutton = new Button("Clear");
		clearbutton.setId("bgz_well_clearbutton");

		cancelbutton = new Button("Cancel");
		cancelbutton.setId("bgz_well_cancelbutton");

		wellCompletionMainLayout.add(saveButton, clearbutton);

		add( unitLayout, completiongrid, wellCompletionActionLayout, wellCompletionMainLayout);
		setAlignSelf(Alignment.CENTER, wellCompletionActionLayout);
		setAlignSelf(Alignment.BASELINE, wellCompletionMainLayout);

	}
	private void populatedepthunitCombobox() {
		// TODO Auto-generated method stub
		
		UOM lengthUnitUom = lenghtUnitCategory.getUom();
		String[] lengthUnits = UOMRegistry.getPossibleUnitsForCategory(lenghtUnitCategory);
		
		depthunitComboBox.setItems(lengthUnits);
		depthunitComboBox.setValue(lengthUnits[0]);
		
		for (String depthUnit : lengthUnits) {
			if (depthUnit.equals(depthUnit)) {
				depthunitComboBox.setValue("m");
			}
		}
		
   this.depthunitComboBox.addValueChangeListener(event ->{
			
			String previousDepthunit = _currentDepthUnit;
			_currentDepthUnit = depthunitComboBox.getValue().toString().trim();
			
			completiongrid.getColumnByKey(topMDkey).setHeader("Top MD(" + _currentDepthUnit + ")");
			completiongrid.getColumnByKey(bottomMDKey).setHeader("Bottom MD(" + _currentDepthUnit + ")");
			
			UOM _lengthUnitUom = lenghtUnitCategory.getUom();
			XKria_Unit_Conversion_Utility utility = XKria_Unit_Conversion_Utility.getInstance();
			ListDataProvider<Blgz_Completion_Info> completionAllDatalist = (ListDataProvider<Blgz_Completion_Info>) completiongrid
					.getDataProvider();

			List<Blgz_Completion_Info> rowList = new ArrayList<>();
			if (!completionAllDatalist.getItems().isEmpty()) {

				rowList = (List<Blgz_Completion_Info>) completionAllDatalist.getItems();
			} else {
				rowList = new ArrayList<>();
			}

			List<Blgz_Completion_Info> updatedList = new ArrayList<>();

			for (int i = 0; i < rowList.size(); i++) {
				Blgz_Completion_Info completionInfo = rowList.get(i);
				double top = Double.valueOf(
						completionInfo.getDepth().toString());
				double convertedValue = utility.getConvertedValue(lengthUnitUom, top, previousDepthunit,
						_currentDepthUnit);
				convertedValue = DecimalFormatManager.getDoubleValueInEnUSLocale(convertedValue, 3);

				completionInfo.setDepth(String.valueOf(convertedValue));
				double bottom = Double.valueOf(
						completionInfo.getEndDepth());
				convertedValue = utility.getConvertedValue(lengthUnitUom, bottom, previousDepthunit,
						_currentDepthUnit);
				convertedValue = DecimalFormatManager.getDoubleValueInEnUSLocale(convertedValue, 3);
				completionInfo.setEndDepth(String.valueOf(convertedValue));
				updatedList.add(completionInfo);

			}
			completiongrid.setItems(updatedList);
			
		});
		
	}
	
	private void addCompletionButtonAction() {
		addWellCompletionButton.addClickListener(event -> {
			updateSelectedDialog(false);

			
		});
	}
	
	private void editCompletionButtonClickAction() {
		// TODO Auto-generated method stub
		editWellCompletionButton.addClickListener(event -> {
			if(completiongrid.asSingleSelect().getValue() == null) {
				PB_Progress_Notification notification = new PB_Progress_Notification();
				String selectaRow = PetrahubNotification_Utilities.getInstance().selectArowFromgrid();
								notification.setText(selectaRow);
								notification.open();
								notification.setDuration(3000);
			}else {
				updateSelectedDialog(true);
			}
			
			
		});
	}
	
	private void updateSelectedDialog(boolean editFlag) {
		// TODO Auto-generated method stub
		String[] units = new String[1];
		 units[0] = depthunitComboBox.getValue().toString();
		Blgz_Completions_Window completionWindow = new Blgz_Completions_Window(completiongrid,editFlag,units);
		completionWindow.open();
		
	}
	
	private void deleteCompletionRowClickAction() {
//		// TODO Auto-generated method stub
		removeWellCompletionButton.addClickListener(event -> {

			Blgz_Completion_Info selectedGridRow = completiongrid.asSingleSelect().getValue();
			if(completiongrid.asSingleSelect().getValue() == null) {
				Notification.show("Please select a row ");
			} else {
			ListDataProvider<Blgz_Completion_Info> dataProvider = (ListDataProvider<Blgz_Completion_Info>) completiongrid.getDataProvider();
            dataProvider.getItems().remove(selectedGridRow);
            dataProvider.refreshAll();
            completiongrid.getDataProvider().refreshAll();
            completiongrid.deselect(selectedGridRow);

			}
			
	});
}
	
	private void saveCompletionDataEvent() {
		// TODO Auto-generated method stub
		
		saveButton.addClickListener(event -> {
			ListDataProvider<Blgz_Completion_Info> dataProvider = (ListDataProvider<Blgz_Completion_Info>) completiongrid
					.getDataProvider();
			List<Blgz_Completion_Info> inputs = (List<Blgz_Completion_Info>) dataProvider.getItems();
			ArrayList<ArrayList<String>> allCompletionData = new ArrayList<>();
			for (Blgz_Completion_Info userInput : inputs) {
				ArrayList<String> completionData = new ArrayList<>();
				completionData.add(userInput.getId());
				completionData.add(userInput.getDepth());
				completionData.add(userInput.getEndDepth());
				completionData.add(userInput.getCompletion());
				completionData.add(userInput.getPartID());
				
				allCompletionData.add(completionData);
			}
			WellboreListInfo wellboreInfo = View_Update.getWellboreInfo();
			Long wellboreID = wellboreInfo.getWellboreID();

			CompletionPojoClass allData = new CompletionPojoClass();
			allData.setData(allCompletionData);

			CompletionMetaData meta_data = new CompletionMetaData();
			String[] columns = { "ID","Start_Depth","End_Depth","Completion_Name","Part_ID"};
			String[] units = { "NA","m","m","NA","NA" };
			meta_data.setColumns(columns);
			meta_data.setUnits(units);

			allData.setMeta_data(meta_data);

			JSONObject CompletionObject = new JSONObject(allData);
			
			String query = "select count(*) from well_registry_db.well_completion where wellbore_id=" + wellboreID +"";
			int ifexist = new WellboreQueries(). wellboreExistByID(query);
			
			if (ifexist == 0) {
			try {
				new WellboreQueries().insertCompletionData(wellboreID, CompletionObject);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			} else {
				System.out.println("wellbore exist already, plese update the wellbore");
				try {
					new WellboreQueries().updateDataInCompletionView(wellboreID, CompletionObject);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
//			
			
		});
		
	}
	
	public void setDataToUI() {
		CompletionPojoClass completionObject = null;
		WellboreListInfo wellboreInfo = View_Update.getWellboreInfo();
		Long wellboreID = wellboreInfo.getWellboreID();
//		Long wellboreID= (long) 10450864;
		try {
			completionObject = new WellboreQueries().getCompletionData(wellboreID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (completionObject!=null) {
			ArrayList<ArrayList<String>> data = completionObject.getData();
			ArrayList<Blgz_Completion_Info> completionDataList = new ArrayList<>();
			for (ArrayList<String> completionData : data) {
				
				Blgz_Completion_Info blgzcompletionObject = new Blgz_Completion_Info();
				
				blgzcompletionObject.setId(completionData.get(0));
				blgzcompletionObject.setDepth(completionData.get(1));
				blgzcompletionObject.setEndDepth(completionData.get(2));
				blgzcompletionObject.setCompletion(completionData.get(3));
				blgzcompletionObject.setPartID(completionData.get(4));
				completionDataList.add(blgzcompletionObject);
				
				
			}
			
			completiongrid.setItems(completionDataList);
		
		}
		
	}
	
	private void clearButtonAction() {
		clearbutton.addClickListener(event -> {
//			ListDataProvider<Blgz_Completion_Info> dataProvider = (ListDataProvider<Blgz_Completion_Info>) completiongrid.getDataProvider();
//
//			List<Blgz_Completion_Info> completionInfoList = new Blgz_Completion_Provider().getCompetionData(selectedWellbore);
//			dataProvider.getItems().removeAll(completionInfoList);
//			dataProvider.refreshAll();
//			completiongrid.setItems();
		});
		
	}

}

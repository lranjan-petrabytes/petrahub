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

public class perforationView extends VerticalLayout {

	private ComboBox<String> depthunitComboBox;
	private Button createPerformationButton;
	private Button removePerformationButton;
	private Button editPerformationButton;
	private UnitCategory lenghtUnitCategory = UOMRegistry.getUnitCategoryForName("Measured Depth");
	private String _currentDepthUnit;
	private String bottomMDKey ="bottomMD";
	private String topMDkey = "topMD";

	private Grid<Blgz_Perforation_Info> perforationgrid = new Grid<>();



	private Button savebutton;
	private Button clearbutton;

	public perforationView () {
	
		setSizeFull();
		setUI();
		addPerforationButton();
		editPerforationButtonClickAction();
		populatedepthunitCombobox();
		deletePerforationRowClickAction();
		savePerforationDataEvent();
		clearButtonAction();
		setDataToUI();
		
	
	}

	private void setUI() {
		// TODO Auto-generated method stub
		setSizeFull();
		setClassName("wellbore_Padding");
		

		HorizontalLayout unitLayout = new HorizontalLayout();
		Label depthUnitLabel = new Label("Depth Unit");
		depthUnitLabel.setId("bgz_well_depthUnitLabel");
		depthUnitLabel.getElement().getStyle().set("padding-top", "10px");

		depthunitComboBox = new ComboBox<String>();
		depthunitComboBox.setId("bgz_well_depthunit");
		unitLayout.add(depthUnitLabel, depthunitComboBox);

		perforationgrid.addColumn(Blgz_Perforation_Info::getPerforationName).setHeader("Perforation").setAutoWidth(true);
		perforationgrid.addColumn(Blgz_Perforation_Info::getTopMD).setHeader("Top MD(m)").setAutoWidth(true);
		perforationgrid.addColumn(Blgz_Perforation_Info::getBottomMD).setHeader("Bottom MD(m)").setAutoWidth(true);
		
		perforationgrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
		HorizontalLayout perforationActionLayout = new HorizontalLayout();

		createPerformationButton = new Button();
		createPerformationButton.setId("bgz_well_createPerformation");
		Image createPerformationimage = new Image("icons" + File.separator + "16x" + File.separator + "add-16x.png","add");
		createPerformationButton.getElement().setAttribute("title", "Add");
		createPerformationButton.setIcon(createPerformationimage);

		editPerformationButton = new Button();
		editPerformationButton.setId("bgz_well_editPerformation");
		Image editPerformationimage = new Image("icons" + File.separator + "16x" + File.separator + "edit_16.png","Edit");
		editPerformationButton.getElement().setAttribute("title", "Edit ");
		editPerformationButton.setIcon(editPerformationimage);

		removePerformationButton = new Button();
		removePerformationButton.setId("bgz_well_removePerformation");
		Image removePerformationimage = new Image("icons" + File.separator + "16x" + File.separator + "delete16.png", "delete");
		removePerformationButton.getElement().setAttribute("title", "Delete");
		removePerformationButton.setIcon(removePerformationimage);

		perforationActionLayout.add(createPerformationButton, editPerformationButton, removePerformationButton);

		HorizontalLayout perforationMainLayout = new HorizontalLayout();


		savebutton = new Button("Save");
		savebutton.setId("bgz_well_Perforationsavebutton");
		savebutton.addThemeVariants(ButtonVariant.LUMO_SMALL,ButtonVariant.LUMO_PRIMARY);

		clearbutton = new Button("Clear");
		clearbutton.setId("bgz_well_clearbutton");


		Button Cancelbutton = new Button("Cancel");
		Cancelbutton.setId("bgz_well_Cancelbutton");
	

		perforationMainLayout.add(savebutton, clearbutton);

		add( unitLayout, perforationgrid, perforationActionLayout, perforationMainLayout);
		setAlignSelf(Alignment.CENTER, perforationActionLayout);
		setAlignSelf(Alignment.BASELINE, perforationMainLayout);

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
		
		this.depthunitComboBox.addValueChangeListener(event -> {
			
			String previousDepthunit = _currentDepthUnit;
			
			_currentDepthUnit = depthunitComboBox.getValue().toString().trim();
			
			perforationgrid.getColumnByKey(topMDkey).setHeader("Top MD(" + _currentDepthUnit + ")");
			perforationgrid.getColumnByKey(bottomMDKey).setHeader("Bottom MD(" + _currentDepthUnit + ")");
			UOM lengthUnitUom1 = lenghtUnitCategory.getUom();
			XKria_Unit_Conversion_Utility utility = XKria_Unit_Conversion_Utility.getInstance();
			ListDataProvider<Blgz_Perforation_Info> perforationAllDatalist = (ListDataProvider<Blgz_Perforation_Info>) perforationgrid
					.getDataProvider();

			List<Blgz_Perforation_Info> rowList = new ArrayList<>();
			if (!perforationAllDatalist.getItems().isEmpty()) {

				rowList = (List<Blgz_Perforation_Info>) perforationAllDatalist.getItems();
			} else {
				rowList = new ArrayList<>();
			}

			List<Blgz_Perforation_Info> updatedList = new ArrayList<>();

			for (int i = 0; i < rowList.size(); i++) {
				Blgz_Perforation_Info perforationInfo = rowList.get(i);
				
				double top = Double.valueOf(
						perforationInfo.getTopMD().toString());
				double convertedValue = utility.getConvertedValue(lengthUnitUom1, top, previousDepthunit,
						_currentDepthUnit);
				convertedValue = DecimalFormatManager.getDoubleValueInEnUSLocale(convertedValue, 3);

				// double convertedValue = DecimalFormatManager.getDoubleValueInEnUSLocale(top,
				// 3);

				perforationInfo.setTopMD(String.valueOf(convertedValue));
				double bottom = Double.valueOf(
						perforationInfo.getBottomMD().toString());
				convertedValue = utility.getConvertedValue(lengthUnitUom1, bottom, previousDepthunit,
						_currentDepthUnit);
				convertedValue = DecimalFormatManager.getDoubleValueInEnUSLocale(convertedValue, 3);

				// convertedValue = DecimalFormatManager.getDoubleValueInEnUSLocale(bottom, 3);
				perforationInfo.setBottomMD(String.valueOf(convertedValue));
				updatedList.add(perforationInfo);
			}
		perforationgrid.setItems(updatedList);

	});

		
	}
	private void addPerforationButton() {
		// TODO Auto-generated method stub
		createPerformationButton.addClickListener(event -> {
			updateSelectedDialog(false);
		});
		
	}
	
	private void editPerforationButtonClickAction() {
		// TODO Auto-generated method stub
		editPerformationButton.addClickListener(event -> {
			if(perforationgrid.asSingleSelect().getValue() == null) {
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
		Blgz_Perforation_Window perforationWindow = new Blgz_Perforation_Window(perforationgrid,editFlag,units);
		perforationWindow.open();
		
	}





	private void savePerforationDataEvent() {
		// TODO Auto-generated method stub
		savebutton.addClickListener(event -> {
			ListDataProvider<Blgz_Perforation_Info> dataProvider = (ListDataProvider<Blgz_Perforation_Info>) perforationgrid
					.getDataProvider();
			List<Blgz_Perforation_Info> inputs = (List<Blgz_Perforation_Info>) dataProvider.getItems();
			ArrayList<ArrayList<String>> allPerforationData = new ArrayList<>();
			for (Blgz_Perforation_Info userInput : inputs) {
				ArrayList<String> perforationData = new ArrayList<>();
				perforationData.add(userInput.getPerforationName());
				perforationData.add(userInput.getTopMD());
				perforationData.add(userInput.getBottomMD());
				
				allPerforationData.add(perforationData);
			}
			WellboreListInfo wellboreInfo = View_Update.getWellboreInfo();
			Long wellboreID = wellboreInfo.getWellboreID();

			PerforationPojoClass allData = new PerforationPojoClass();
			allData.setData(allPerforationData);

			PerforationMetaData meta_data = new PerforationMetaData();
			String[] columns = { "Perforation_Name","Top_MD","Bottom_MD"};
			String[] units = { "NA","m","m" };
			meta_data.setColumns(columns);
			meta_data.setUnits(units);

			allData.setMeta_data(meta_data);

			JSONObject PerforationObject = new JSONObject(allData);
			String query = "select count(*) from well_registry_db.perforation where wellbore_id=" + wellboreID +"";
			int ifexist = new WellboreQueries(). wellboreExistByID(query);
			
			if (ifexist == 0) {
			try {
				new WellboreQueries().insertPerforationData(wellboreID, PerforationObject);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			} else {
				System.out.println("wellbore exist already, plese update the wellbore");
				try {
					new WellboreQueries().updateDataInPerforationView(wellboreID, PerforationObject);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
	}
	
	public void setDataToUI() {
		PerforationPojoClass perforationObject = null;
		WellboreListInfo wellboreInfo = View_Update.getWellboreInfo();
		Long wellboreID = wellboreInfo.getWellboreID();
//		Long wellboreID= (long) 10450864;
		try {
			perforationObject = new WellboreQueries().getPerforationData(wellboreID);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (perforationObject!=null) {
			ArrayList<ArrayList<String>> data = perforationObject.getData();
			ArrayList<Blgz_Perforation_Info> perforationDataList = new ArrayList<>();
			for (ArrayList<String> perforationData : data) {
				
				Blgz_Perforation_Info blgzperfoationObject = new Blgz_Perforation_Info();
				
				blgzperfoationObject.setPerforationName(perforationData.get(0));
				blgzperfoationObject.setTopMD(perforationData.get(1));
				blgzperfoationObject.setBottomMD(perforationData.get(2));
				perforationDataList.add(blgzperfoationObject);
				
				
			}
			
			perforationgrid.setItems(perforationDataList);
		
		}
	}
	
	private void deletePerforationRowClickAction() {
//		// TODO Auto-generated method stub
		removePerformationButton.addClickListener(event -> {

			Blgz_Perforation_Info selectedGridRow = perforationgrid.asSingleSelect().getValue();
			ListDataProvider<Blgz_Perforation_Info> dataProvider = (ListDataProvider<Blgz_Perforation_Info>) perforationgrid.getDataProvider();
            dataProvider.getItems().remove(selectedGridRow);
            dataProvider.refreshAll();
            perforationgrid.getDataProvider().refreshAll();
            perforationgrid.deselect(selectedGridRow);
			
	});
  }
	
	private void clearButtonAction() {
		clearbutton.addClickListener(event -> {
//			ListDataProvider<Blgz_Perforation_Info> dataProvider = (ListDataProvider<Blgz_Perforation_Info>) perforationgrid.getDataProvider();
//			List<Blgz_Perforation_Info> perforationInfoList = new Blgz_Perforation_Provider().getPerforationData(selectedWellbore);
//			dataProvider.getItems().removeAll(perforationInfoList);
//			dataProvider.refreshAll();
//			perforationgrid.setItems();
			
		});
	}

}

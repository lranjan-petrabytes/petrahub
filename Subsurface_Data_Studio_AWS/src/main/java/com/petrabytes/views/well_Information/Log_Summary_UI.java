package com.petrabytes.views.well_Information;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;


public class Log_Summary_UI extends HorizontalLayout {

	private Button editUnitButton;
	private Label lognameLabel;
	private Label wellLabel;
	private Label wellboreLabel;
	private Label sourceLabel;
	private Label startdepthLabel;
	private Label enddepthLabel;
	private Label unitcategoryLabel;
	private Label unitLabel;
	private Label depthUnitLabel;
    private Quality_Check_Info _selectedQCValue;
	private FlexLayout chartLayout = new FlexLayout();
	private UI ui = UI.getCurrent();
	private Grid<Quality_Check_Info> qualitygrid;
    private String wellboreID;
	private HorizontalLayout mainChartLayout = new HorizontalLayout();
	private DataTab_UI dataTabUI;
	public Log_Summary_UI() {
		SetUp_UI();
		editButtonActionEvent();
	}

	private void SetUp_UI() {
		// TODO Auto-generated method stub
		this.setSizeFull();
//		chartLayout.setWidth("400px");
		chartLayout.setSizeFull();
		this.setPadding(true);
		VerticalLayout summaryLayout = new VerticalLayout();
		FormLayout formLayout = new FormLayout();
		formLayout.setWidthFull();
		formLayout.setHeight("300px");

		lognameLabel = new Label();
		formLayout.addFormItem(lognameLabel, "Log Name :");

		wellLabel = new Label();
		formLayout.addFormItem(wellLabel, "Well :");

		wellboreLabel = new Label();
		formLayout.addFormItem(wellboreLabel, "Wellbore :");

		sourceLabel = new Label();
		formLayout.addFormItem(sourceLabel, "Source File :");

		startdepthLabel = new Label();
		formLayout.addFormItem(startdepthLabel, "Start Depth :");

		enddepthLabel = new Label();
		formLayout.addFormItem(enddepthLabel, " End Depth :");

		unitcategoryLabel = new Label();
		formLayout.addFormItem(unitcategoryLabel, "Unit Category :");

		unitLabel = new Label();
		formLayout.addFormItem(unitLabel, "Data Unit :");
		formLayout.getStyle().set("padding-left", "10px");

//		formLayout.setWidth("40%");
		formLayout.getStyle().set("float", "left");
		formLayout.getStyle().set("margin-left", "10px");
		
		depthUnitLabel = new Label();
		formLayout.addFormItem(depthUnitLabel, "Depth Unit :");
		
		editUnitButton = new Button("Edit Unit");
		editUnitButton.setId("bgz_logs_editUnitButton");
		editUnitButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		FlexLayout actionLayout = new FlexLayout(editUnitButton);
		// actionLayout.getStyle().set("margin-top", "20px");
		actionLayout.setWidthFull();

		FlexLayout flexLayout = new FlexLayout(formLayout);
		flexLayout.setSizeFull();
		flexLayout.setClassName("summary-layout");
		summaryLayout.setSizeFull();
		summaryLayout.setPadding(false);
		summaryLayout.setMargin(false);
		summaryLayout.add(flexLayout, actionLayout);

		VerticalLayout div = new VerticalLayout(chartLayout);
		div.setSizeFull();
		chartLayout.setClassName("qC_Logs_Plot");
		this.add(summaryLayout, div);
//		this.expand(div);

	}

	
//		dataSummaryUtil.setMainLayout(chartLayout);
//		dataSummaryUtil.d3plotDisplay(_selectedQCValue);
	
	public void updateLabel(Quality_Check_Info selectedQCValue) throws SQLException {
		// TODO Auto-generated method stub
		_selectedQCValue = selectedQCValue;
		String logName = selectedQCValue.getMnemonic().toString();

		Long logId = selectedQCValue.getLog_id();

		lognameLabel.setText(logName);
		
		ArrayList<Data_Mapping_Info> logData = new WellLogsDataMappingQuery().retrieveFileNameLoginID(logId);
		
		for(Data_Mapping_Info row:logData) {
			
			String sourceFile = row.getFilename();
			String wellborename = row.getWellbore();
			String wellname = row.getWell();
			wellboreID = row.getWellboreID();
			wellLabel.setText(wellname);
			wellboreLabel.setText(wellborename);
			sourceLabel.setText(sourceFile);
		}



	
		String startdepth = Quality_Check_UI.getStratDepth();
		startdepthLabel.setText(startdepth);
		String endDepth = Quality_Check_UI.getEndDepth();
		enddepthLabel.setText(endDepth);
		String unitCatagory = selectedQCValue.getUnitCategory();
		unitcategoryLabel.setText(unitCatagory);
		String unitName = selectedQCValue.getUnit();
		unitLabel.setText(unitName);
		
//		String depthUnitName = selectedQCValue.getYunits();
//		depthUnitLabel.setText(depthUnitName);
		

	}

	private void editButtonActionEvent() {
		editUnitButton.addClickListener(event -> {
			Edit_Unit_UI editUnitUI = new Edit_Unit_UI(_selectedQCValue, qualitygrid, unitcategoryLabel,unitLabel);
			editUnitUI.open();
			editUnitUI.setUnitcategoryLabel(unitcategoryLabel);
			editUnitUI.setUnitLabel(unitLabel);

		});

	}

	public void updatePlots(Quality_Check_Info selectedQCValue) {
		// TODO Auto-generated method stub
		chartLayout.removeAll();
		this._selectedQCValue = selectedQCValue;


	}



	public void setQualitygrid(Grid<Quality_Check_Info> qualitygrid) {
		this.qualitygrid = qualitygrid;
	}
	
	


	

}

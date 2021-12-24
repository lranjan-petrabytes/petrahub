package com.petrabytes.project.util;

import java.util.List;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class DataPreparationProjectInfo {

	private String viewName = null;
	private String viewID = null;
	private  WellLogsProjectViewsSettingsInfo viewSettings = null;
	
	
	public DataPreparationProjectInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getViewName() {
		return viewName;
	}
	public void setViewName(String viewName) {
		this.viewName = viewName;
	}
	public String getViewID() {
		return viewID;
	}
	public void setViewID(String viewID) {
		this.viewID = viewID;
	}
	public  WellLogsProjectViewsSettingsInfo getViewSettings() {
		return viewSettings;
	}
	public void setViewSettings( WellLogsProjectViewsSettingsInfo viewSettings) {
		this.viewSettings = viewSettings;
	}



}

package com.petrabytes.views.osduWellLogs;

import java.util.List;

public class OsduWellogsViewSettinginfo {
	private String basinname = null;
	private String wellname = null;
	private String wellborename = null;
	private List<String> selectedLogs = null;
	
	
	
	public OsduWellogsViewSettinginfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getBasinname() {
		return basinname;
	}
	public void setBasinname(String basinname) {
		this.basinname = basinname;
	}
	public String getWellname() {
		return wellname;
	}
	public void setWellname(String wellname) {
		this.wellname = wellname;
	}
	public String getWellborename() {
		return wellborename;
	}
	public void setWellborename(String wellborename) {
		this.wellborename = wellborename;
	}
	public List<String> getSelectedLogs() {
		return selectedLogs;
	}
	public void setSelectedLogs(List<String> selectedLogs) {
		this.selectedLogs = selectedLogs;
	}
	
	
}

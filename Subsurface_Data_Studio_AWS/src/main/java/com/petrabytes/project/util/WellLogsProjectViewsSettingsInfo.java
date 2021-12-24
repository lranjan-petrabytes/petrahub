package com.petrabytes.project.util;

import java.util.List;

import com.petrabytes.views.wellRegistryQuery.WellboreListInfo;

public class WellLogsProjectViewsSettingsInfo {
	private String basinname = null;
	private String wellname = null;
	private String wellborename = null;
	private List<String> selectedLogs = null;
	private WellboreListInfo wellboreinfo;
	

	public WellLogsProjectViewsSettingsInfo() {
		super();
		
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

	public WellboreListInfo getWellboreinfo() {
		return wellboreinfo;
	}

	public void setWellboreinfo(WellboreListInfo wellboreListInfo) {
		this.wellboreinfo = wellboreListInfo;
	}
	

}

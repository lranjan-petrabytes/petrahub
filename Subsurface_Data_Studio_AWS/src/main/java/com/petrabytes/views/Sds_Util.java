package com.petrabytes.views;

import com.petrabytes.project.util.GisInfo;
import com.petrabytes.project.util.ProjectSettingsInfo;
import com.petrabytes.views.basin.BasinViewGridInfo;
import com.petrabytes.views.seismic.seismicProjectViewsSettingsInfo;
import com.petrabytes.views.wellRegistryQuery.WellboreListInfo;
import com.petrabytes.views.wells.WellListInfo;

public class Sds_Util {
	
	private BasinViewGridInfo basinfo;
	private WellListInfo wellInfo;
	private WellboreListInfo wellboreInfo;
	private GisInfo gisviewSettings;
	private ProjectSettingsInfo projectSettings = new ProjectSettingsInfo() ;
	
	private seismicProjectViewsSettingsInfo seismicSetting;
	
	
	
	
	public seismicProjectViewsSettingsInfo getSeismicSetting() {
		return seismicSetting;
	}
	public void setSeismicSetting(seismicProjectViewsSettingsInfo seismicSetting) {
		this.seismicSetting = seismicSetting;
	}
	public GisInfo getGisviewSettings() {
		return gisviewSettings;
	}
	public void setGisviewSettings(GisInfo gisviewSettings) {
		this.gisviewSettings = gisviewSettings;
	}
	public ProjectSettingsInfo getProjectSettings() {
		return projectSettings;
	}
	public void setProjectSettings(ProjectSettingsInfo projectSettings) {
		this.projectSettings = projectSettings;
	}
	public BasinViewGridInfo getBasinfo() {
		return basinfo;
	}
	public void setBasinfo(BasinViewGridInfo basinfo) {
		this.basinfo = basinfo;
	}
	public WellListInfo getWellInfo() {
		return wellInfo;
	}
	public void setWellInfo(WellListInfo wellInfo) {
		this.wellInfo = wellInfo;
	}
	public WellboreListInfo getWellboreInfo() {
		return wellboreInfo;
	}
	public void setWellboreInfo(WellboreListInfo wellboreInfo) {
		this.wellboreInfo = wellboreInfo;
	}
	
	

}

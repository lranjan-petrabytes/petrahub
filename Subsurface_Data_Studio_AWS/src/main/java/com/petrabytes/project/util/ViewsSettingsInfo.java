package com.petrabytes.project.util;

import com.petrabytes.views.basin.BasinViewGridInfo;
import com.petrabytes.views.seismic.seismicProjectViewsSettingsInfo;

public class ViewsSettingsInfo {

	private WellLogsProjectViewsSettingsInfo wellLogSetting;
	private GisInfo gisviewSettings;
	
	private BasinViewProjectSettingInfo basinSetting = new BasinViewProjectSettingInfo();
	private WellboreViewProjectSettingInfo wellboreViewSettings;
    private WellsMainViewSettingsInfo wellSetting = new WellsMainViewSettingsInfo();
    private seismicProjectViewsSettingsInfo seiesmicSettings;
    private CoreDataSettingInfo coreDataSettings = new CoreDataSettingInfo();
    
    
    
    
	

	public CoreDataSettingInfo getCoreDataSettings() {
		return coreDataSettings;
	}

	public void setCoreDataSettings(CoreDataSettingInfo coreDataSettings) {
		this.coreDataSettings = coreDataSettings;
	}

	public seismicProjectViewsSettingsInfo getSeiesmicSettings() {
		return seiesmicSettings;
	}

	public void setSeiesmicSettings(seismicProjectViewsSettingsInfo seiesmicSettings) {
		this.seiesmicSettings = seiesmicSettings;
	}

	public WellboreViewProjectSettingInfo getWellboreViewSettings() {
		return wellboreViewSettings;
	}

	public void setWellboreViewSettings(WellboreViewProjectSettingInfo wellboreViewSettings) {
		this.wellboreViewSettings = wellboreViewSettings;
	}

	public WellsMainViewSettingsInfo getWellSetting() {
		return wellSetting;
	}

	public void setWellSetting(WellsMainViewSettingsInfo wellSetting) {
		this.wellSetting = wellSetting;
	}

	public BasinViewProjectSettingInfo getBasinSetting() {
		return basinSetting;
	}

	public void setBasinSetting(BasinViewProjectSettingInfo basinSetting) {
		this.basinSetting = basinSetting;
	}

	public WellLogsProjectViewsSettingsInfo getWellLogSetting() {
		return wellLogSetting;
	}

	public void setWellLogSetting(WellLogsProjectViewsSettingsInfo wellLogSetting) {
		this.wellLogSetting = wellLogSetting;
	}

	public GisInfo getGisviewSettings() {
		return gisviewSettings;
	}

	public void setGisviewSettings(GisInfo gisviewSettings) {
		this.gisviewSettings = gisviewSettings;
	}



}

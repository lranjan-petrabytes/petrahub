package com.petrabytes.views;


import java.sql.SQLException;

import com.petrabytes.project.util.GisInfo;
import com.petrabytes.project.util.ProjectSettingsInfo;
import com.petrabytes.views.basin.BasinView;
import com.petrabytes.views.basin.BasinViewGridInfo;
import com.petrabytes.views.completions.CompletionsView;
import com.petrabytes.views.coredata.CoreDataView;
import com.petrabytes.views.das.DasView;
import com.petrabytes.views.dts.DTSView;
import com.petrabytes.views.geobodies.GeobodiesView;
import com.petrabytes.views.gis.GISView;
import com.petrabytes.views.seismic.SeismicView;
import com.petrabytes.views.seismic.seismicProjectViewsSettingsInfo;
import com.petrabytes.views.subsurfacedata.SubSurface_DataView;
import com.petrabytes.views.wellRegistryQuery.WellboreListInfo;
import com.petrabytes.views.welllogs.WellLogsView;
import com.petrabytes.views.wells.WellListInfo;
import com.petrabytes.views.wells.WellsMapView;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;

public class View_Update {

	public static Component getPresentView(String title) throws SQLException {
		Component component = null;
		if (title.equals("Completions"))
			component = new CompletionsView();
		else if (title.equals("Core Data"))
			component = new CoreDataView();
		else if (title.equals("DAS"))
			component = new DasView();
		else if (title.equals("DTS"))
			component = new DTSView();
		else if (title.equals("Geobodies"))
			component = new GeobodiesView();
		else if (title.equals("GIS"))
			component = new GISView();
		else if (title.equals("Seismic"))
			component = new SeismicView();
		else if (title.equals("SubSurface"))
			component = new SubSurface_DataView();
		else if (title.equals("Well Logs"))
			component = new WellLogsView();
		else if (title.equals("WellsMap"))
			component = new WellsMapView();
		else if (title.equals("Basin"))
			component = new BasinView();
		return component;
	}
	
	public static GisInfo  getGISInfo() {
		GisInfo gisviewSettings = null;
		MainLayout instance = getAppLayoutInstance();
		if (instance != null) {
			Sds_Util sdsUtil = instance.getAppUtil();
			if (sdsUtil != null)
				gisviewSettings = sdsUtil.getGisviewSettings();

		}
		return gisviewSettings;
	}
	
	/**
	 * set wellbore info
	 * @param basinfo
	 */
	public static void setGISInfo(GisInfo gisviewSettings) {
		MainLayout instance = getAppLayoutInstance();
		if (instance != null) {
			Sds_Util sdsUtil = instance.getAppUtil();
			if (sdsUtil == null)
				sdsUtil = new Sds_Util();
			sdsUtil.setGisviewSettings(gisviewSettings);
			instance.setApputil(sdsUtil);
		}
	}
	
	
	public static seismicProjectViewsSettingsInfo  getSeismicInfo() {
		seismicProjectViewsSettingsInfo seismicSetting = null;
		MainLayout instance = getAppLayoutInstance();
		if (instance != null) {
			Sds_Util sdsUtil = instance.getAppUtil();
			if (sdsUtil != null)
				seismicSetting = sdsUtil.getSeismicSetting();

		}
		return seismicSetting;
	}
	
	/**
	 * set wellbore info
	 * @param basinfo
	 */
	public static void setSeismicInfo(seismicProjectViewsSettingsInfo seismicSetting) {
		MainLayout instance = getAppLayoutInstance();
		if (instance != null) {
			Sds_Util sdsUtil = instance.getAppUtil();
			if (sdsUtil == null)
				sdsUtil = new Sds_Util();
			sdsUtil.setSeismicSetting(seismicSetting);
			instance.setApputil(sdsUtil);
		}
	}
	
	/**
	 * get wellbore info
	 * @return
	 */
	public static ProjectSettingsInfo getProjectsettingInfo() {
		ProjectSettingsInfo projectsetting = null;
		MainLayout instance = getAppLayoutInstance();
		if (instance != null) {
			Sds_Util sdsUtil = instance.getAppUtil();
			if (sdsUtil != null)
				projectsetting = sdsUtil.getProjectSettings();

		}
		return  projectsetting;
	}
	
	/**
	 * set wellbore info
	 * @param basinfo
	 */
	public static void setProjectsettingInfo(	ProjectSettingsInfo projectsetting) {
		MainLayout instance = getAppLayoutInstance();
		if (instance != null) {
			Sds_Util sdsUtil = instance.getAppUtil();
			if (sdsUtil == null)
				sdsUtil = new Sds_Util();
			sdsUtil.setProjectSettings(projectsetting);
			instance.setApputil(sdsUtil);
		}
	}

	/**
	 * get wellbore info
	 * @return
	 */
	public static WellboreListInfo getWellboreInfo() {
		WellboreListInfo wellboreinfo = null;
		MainLayout instance = getAppLayoutInstance();
		if (instance != null) {
			Sds_Util sdsUtil = instance.getAppUtil();
			if (sdsUtil != null)
				wellboreinfo = sdsUtil.getWellboreInfo();

		}
		return wellboreinfo;
	}
	
	/**
	 * set wellbore info
	 * @param basinfo
	 */
	public static void setWellboreInfo(WellboreListInfo wellboreinfo) {
		MainLayout instance = getAppLayoutInstance();
		if (instance != null) {
			Sds_Util sdsUtil = instance.getAppUtil();
			if (sdsUtil == null)
				sdsUtil = new Sds_Util();
			sdsUtil.setWellboreInfo(wellboreinfo);
			instance.setApputil(sdsUtil);
		}
	}
	
	
	/**
	 * get well info
	 * @return
	 */
	public static WellListInfo getWellInfo() {
		WellListInfo wellinfo = null;
		MainLayout instance = getAppLayoutInstance();
		if (instance != null) {
			Sds_Util sdsUtil = instance.getAppUtil();
			if (sdsUtil != null)
				wellinfo = sdsUtil.getWellInfo();

		}
		return wellinfo;
	}
	
	/**
	 * set well info
	 * @param basinfo
	 */
	public static void setWellInfo(WellListInfo wellinfo) {
		MainLayout instance = getAppLayoutInstance();
		if (instance != null) {
			Sds_Util sdsUtil = instance.getAppUtil();
			if (sdsUtil == null)
				sdsUtil = new Sds_Util();
			sdsUtil.setWellInfo(wellinfo);
			instance.setApputil(sdsUtil);
		}
	}

	
	/**
	 * get basin info
	 * @return
	 */
	
	public static BasinViewGridInfo getBasinInfo() {
		BasinViewGridInfo basinfo = null;
		MainLayout instance = getAppLayoutInstance();
		if (instance != null) {
			Sds_Util sdsUtil = instance.getAppUtil();
			if (sdsUtil != null)
				basinfo = sdsUtil.getBasinfo();

		}
		return basinfo;
	}
	

	/**
	 * set basin info
	 * @param basinfo
	 */
	public static void setBasinInfo(BasinViewGridInfo basinfo) {
		MainLayout instance = getAppLayoutInstance();
		if (instance != null) {
			Sds_Util sdsUtil = instance.getAppUtil();
			if (sdsUtil == null)
				sdsUtil = new Sds_Util();
			sdsUtil.setBasinfo(basinfo);
			instance.setApputil(sdsUtil);
		}
	}

	/**
	 * Returns the current AppLayout
	 * 
	 * @return
	 */
	public static MainLayout getAppLayoutInstance() {
		return (MainLayout) UI.getCurrent().getChildren().filter(component -> component.getClass() == MainLayout.class)
				.findFirst().orElse(null);
	}
}

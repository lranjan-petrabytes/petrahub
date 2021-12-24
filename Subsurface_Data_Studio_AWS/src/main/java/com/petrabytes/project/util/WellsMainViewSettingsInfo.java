package com.petrabytes.project.util;

import java.util.HashMap;

import com.petrabytes.views.wells.WellListInfo;

import net.minidev.json.JSONArray;

public class WellsMainViewSettingsInfo {
   private WellListInfo selectedWellGrid;
   
   private String companyName;
   private String status;
   private String State;
   private String welltype;
   private String wellname;
   private String api;
   private String pagesize;

//   private org.json.JSONArray maparrayData = new org.json.JSONArray();
   private HashMap<String, Boolean> sets;
   
   
   







//public org.json.JSONArray getMaparrayData() {
//	return maparrayData;
//}
//
//public void setMaparrayData(org.json.JSONArray maparrayData) {
//	this.maparrayData = maparrayData;
//}

public HashMap<String, Boolean> getSets() {
	return sets;
}

public void setSets(HashMap<String, Boolean> sets) {
	this.sets = sets;
}





public WellListInfo getSelectedWellGrid() {
	return selectedWellGrid;
}

public void setSelectedWellGrid(WellListInfo selectedWellGrid) {
	this.selectedWellGrid = selectedWellGrid;
}

public String getCompanyName() {
	return companyName;
}

public void setCompanyName(String companyName) {
	this.companyName = companyName;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}

public String getState() {
	return State;
}

public void setState(String state) {
	State = state;
}

public String getWelltype() {
	return welltype;
}

public void setWelltype(String welltype) {
	this.welltype = welltype;
}

public String getWellname() {
	return wellname;
}

public void setWellname(String wellname) {
	this.wellname = wellname;
}

public String getApi() {
	return api;
}

public void setApi(String api) {
	this.api = api;
}

public String getPagesize() {
	return pagesize;
}

public void setPagesize(String pagesize) {
	this.pagesize = pagesize;
}

public HashMap<String, Boolean> setDefaults() {
	sets = new HashMap<String, Boolean>();
	sets.put("Company Name", false);
	sets.put("Status",false);
	sets.put("State", false);
	sets.put("well Type", false);
	

	return sets;
}




   
   
}

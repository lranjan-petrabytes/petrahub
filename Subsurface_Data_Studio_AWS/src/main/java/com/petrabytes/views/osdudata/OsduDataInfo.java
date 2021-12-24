package com.petrabytes.views.osdudata;

import java.util.ArrayList;

public class OsduDataInfo {
	String wellname;
	String wellid;
	String basinname;
	private ArrayList<String> data;
	public String getWellname() {
		return wellname;
	}
	public void setWellname(String wellname) {
		this.wellname = wellname;
	}
	public String getWellid() {
		return wellid;
	}
	public void setWellid(String wellid) {
		this.wellid = wellid;
	}
	public String getBasinname() {
		return basinname;
	}
	public void setBasinname(String basinname) {
		this.basinname = basinname;
	}
	public ArrayList<String> getData() {
		return data;
	}
	public void setData(ArrayList<String> data) {
		this.data = data;
	}
	
	

}

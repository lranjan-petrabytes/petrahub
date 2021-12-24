package com.petrabytes.views.wellbore;

import java.util.ArrayList;

public class FormationPojoClass {
	private FormationMetaDataInfo meta_data;
	private ArrayList<ArrayList<String>> data;
	public FormationMetaDataInfo getMeta_data() {
		return meta_data;
	}
	public void setMeta_data(FormationMetaDataInfo meta_data) {
		this.meta_data = meta_data;
	}
	public ArrayList<ArrayList<String>> getData() {
		return data;
	}
	public void setData(ArrayList<ArrayList<String>> data) {
		this.data = data;
	}
	

}

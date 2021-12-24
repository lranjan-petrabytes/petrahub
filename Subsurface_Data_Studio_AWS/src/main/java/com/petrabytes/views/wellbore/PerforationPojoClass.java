package com.petrabytes.views.wellbore;

import java.util.ArrayList;

public class PerforationPojoClass {
	private PerforationMetaData meta_data;
	private ArrayList<ArrayList<String>> data;
	public PerforationMetaData getMeta_data() {
		return meta_data;
	}
	public void setMeta_data(PerforationMetaData meta_data) {
		this.meta_data = meta_data;
	}
	public ArrayList<ArrayList<String>> getData() {
		return data;
	}
	public void setData(ArrayList<ArrayList<String>> data) {
		this.data = data;
	}
	

}

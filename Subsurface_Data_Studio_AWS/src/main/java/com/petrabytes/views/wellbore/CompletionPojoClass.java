package com.petrabytes.views.wellbore;

import java.util.ArrayList;

public class CompletionPojoClass {
	private CompletionMetaData meta_data;
	private ArrayList<ArrayList<String>> data;
	public CompletionMetaData getMeta_data() {
		return meta_data;
	}
	public void setMeta_data(CompletionMetaData meta_data) {
		this.meta_data = meta_data;
	}
	public ArrayList<ArrayList<String>> getData() {
		return data;
	}
	public void setData(ArrayList<ArrayList<String>> data) {
		this.data = data;
	}
	
	

}

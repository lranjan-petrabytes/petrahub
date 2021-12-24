package com.petrabytes.views.dts;

import java.util.ArrayList;
import java.util.List;

public class DTS_GridData {
	
	private long index;
	private String depth;
	private List<String> data;
	
	public DTS_GridData() {
		super();
	}
	
	public DTS_GridData(long index, String depth, List<String> data) {
		super();
		this.index = index;
		this.depth = depth;
		this.data = deepCopy(data);
	}
	
	public long getIndex() {
		return index;
	}
	public void setIndex(long index) {
		this.index = index;
	}
	public String getDepth() {
		return depth;
	}
	public void setDepth(String depth) {
		this.depth = depth;
	}
	
	

	public List<String> getData() {
		return data;
	}
	public void setData(List<String> data) {
		this.data = data;
	}
	
	public List<String> deepCopy(List<String> data) {
		List<String> copy = new ArrayList<>();
		for (String d : data) {
			copy.add(d);
		}
		return copy;
	}
	
	
}

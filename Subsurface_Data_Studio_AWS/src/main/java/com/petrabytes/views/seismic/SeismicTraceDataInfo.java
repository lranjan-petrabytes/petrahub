package com.petrabytes.views.seismic;

public class SeismicTraceDataInfo {

	private int index;
	private int yValue;
	private String xValue;
	
	public SeismicTraceDataInfo(int index, int yValue, String xValue) {
		super();
        this.index = index;
        this.yValue = yValue;
        this.xValue = xValue;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getyValue() {
		return yValue;
	}

	public void setyValue(int yValue) {
		this.yValue = yValue;
	}

	public String getxValue() {
		return xValue;
	}

	public void setxValue(String xValue) {
		this.xValue = xValue;
	}
	
}

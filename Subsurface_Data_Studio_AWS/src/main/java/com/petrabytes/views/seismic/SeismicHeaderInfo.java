package com.petrabytes.views.seismic;

public class SeismicHeaderInfo {
	
	private String identifier;
	private String min;
	private String max;

	private String headerName;
	private String headerValue;
	
	public SeismicHeaderInfo(String identifier, String min, String max) {
		super();
        this.identifier = identifier;
        this.min = min;
        this.max = max;
	}
	
	
	public SeismicHeaderInfo() {
		// TODO Auto-generated constructor stub
	}


	public String getHeaderName() {
		return headerName;
	}
	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}
	public String getHeaderValue() {
		return headerValue;
	}
	public void setHeaderValue(String headerValue) {
		this.headerValue = headerValue;
	}
	
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public String getMax() {
		return max;
	}
	public void setMax(String max) {
		this.max = max;
	}
	
}

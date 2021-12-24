package com.petrabytes.views.dts;

public class DTS_Timestamp {
	
	private Long index;
	private String timestamp;
	
	public DTS_Timestamp() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DTS_Timestamp(Long index, String timestamp) {
		super();
		this.index = index;
		this.timestamp = timestamp;
	}
	public Long getIndex() {
		return index;
	}
	public void setIndex(Long index) {
		this.index = index;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	
}

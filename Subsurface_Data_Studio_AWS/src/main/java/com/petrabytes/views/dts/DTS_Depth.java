package com.petrabytes.views.dts;

public class DTS_Depth {
	
	private Long depth_id;
	private Double depth;
	
	public DTS_Depth() {
		super();	
	}
	public DTS_Depth(Long depth_id, Double depth) {
		super();
		this.depth_id = depth_id;
		this.depth = depth;
	}
	public Long getDepth_id() {
		return depth_id;
	}
	public void setDepth_id(Long depth_id) {
		this.depth_id = depth_id;
	}
	public Double getDepth() {
		return depth;
	}
	public void setDepth(Double depth) {
		this.depth = depth;
	}
	
	public String getDepthLabel() {
		return Double.valueOf(depth).toString();
	}
	
}

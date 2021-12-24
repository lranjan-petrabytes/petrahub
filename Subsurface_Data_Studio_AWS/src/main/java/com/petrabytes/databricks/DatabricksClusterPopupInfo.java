package com.petrabytes.databricks;



import com.vaadin.flow.component.textfield.TextField;

public class DatabricksClusterPopupInfo {
	private String name;
	private String nodeType;
	private String createdBy;
	private String cluster;
	private String worker_min;
	private String status;
	private String worker_max;
	
	public  DatabricksClusterPopupInfo(String name,String cluster, String createdBy,String nodeType,String worker_max,String worker_min,String status) {
		super();
        this.name = name;
        this.cluster = cluster;
        this.createdBy = createdBy;
        this.nodeType = nodeType;
        this.status = status;
        
        this.worker_min = worker_min;
        this.worker_max = worker_max;
	}


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setJobId(String nodeType) {
		this.nodeType = nodeType;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(String cluster) {
		this.cluster = cluster;
	}

	public String getWorker_min() {
		return worker_min;
	}

	public void setLastRun(String worker_min) {
		this.worker_min = worker_min;
	}
	
	public String getWorker_max() {
		return worker_max;
	}

	public void setWorker_max(String worker_max) {
		this.worker_max= worker_max;
	}


}








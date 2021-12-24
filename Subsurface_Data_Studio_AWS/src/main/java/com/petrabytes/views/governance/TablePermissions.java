package com.petrabytes.views.governance;


public class TablePermissions {
	
	private String principal;
	private String action;
	private String type;
	private String key;
	
	public TablePermissions() {
		principal = null;
		action = null;
		type = null;
		key = null;
	}
	
	public TablePermissions(String principal, String action, String type, String key) {
		this.principal = principal;
		this.action = action;
		this.type = type;
		this.key = key;
	}

	public String getPrincipal() {
		return principal;
	}

	public void setPrincipal(String principal) {
		this.principal = principal;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
}

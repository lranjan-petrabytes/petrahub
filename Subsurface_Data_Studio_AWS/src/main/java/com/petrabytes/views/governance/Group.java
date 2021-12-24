package com.petrabytes.views.governance;

public class Group {
	
	private String name;
	private String directGroup;
	
	public Group() {
		name = null;
		directGroup = null;
	}
	
	public Group(String name, String directGroup) {
		this.name = name;
		this.directGroup = directGroup;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDirectGroup() {
		return directGroup;
	}

	public void setDirectGroup(String directGroup) {
		this.directGroup = directGroup;
	}
}

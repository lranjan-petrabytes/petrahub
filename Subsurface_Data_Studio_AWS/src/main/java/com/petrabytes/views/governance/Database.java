package com.petrabytes.views.governance;

public class Database {
	
	private String name;
	
	public Database() {
		name = null;
	}
	
	public Database(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

package com.petrabytes.views.governance;

public class Table {
	
	private String database;
	private String name;
	private String temp;
	
	public Table() {
		database = null;
		name = null;
		temp = null;
	}
	
	public Table(String database, String name, String temp) {
		this.database = database;
		this.name = name;
		this.temp = temp;
	}
	
	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}
}

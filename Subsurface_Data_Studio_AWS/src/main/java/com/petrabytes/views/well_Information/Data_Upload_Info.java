package com.petrabytes.views.well_Information;

public class Data_Upload_Info {
	private String input;
	private String fileExtension;
	
	public Data_Upload_Info(String input,String fileExtension) {
		this.input = input;
		this.fileExtension = fileExtension;
				
		
	}
	
	public Data_Upload_Info() {
		
				
		
	}
	
	
	public String getInput() {
		return input;
	}
	public void setInput(String input) {
		this.input = input;
	}
	public String getFileExtension() {
		return fileExtension;
	}
	public void setFileExtension(String fileExtension) {
		this.fileExtension = fileExtension;
	}
	
	

}

package com.petrabytes.views.well_Information;

public class Quality_Check_Info {
    private String fileID;
	private Long log_id;
	private String unitCategory;
	private String mnemonic;
	private String unit;
	
	private String mappedUnit;

	private String mappedUnitCategory;
	
	
	
	public String getUnitCategory() {
		return unitCategory;
	}
	public void setUnitCategory(String unitCategory) {
		this.unitCategory = unitCategory;
	}
	public String getMnemonic() {
		return mnemonic;
	}
	public void setMnemonic(String mnemonic) {
		this.mnemonic = mnemonic;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getFileID() {
		return fileID;
	}
	public void setFileID(String fileID) {
		this.fileID = fileID;
	}
	public Long getLog_id() {
		return log_id;
	}
	public void setLog_id(Long log_id) {
		this.log_id = log_id;
	}
	public String getMappedUnit() {
		return mappedUnit;
	}
	public void setMappedUnit(String mappedUnit) {
		this.mappedUnit = mappedUnit;
	}
	public String getMappedUnitCategory() {
		return mappedUnitCategory;
	}
	public void setMappedUnitCategory(String mappedUnitCategory) {
		this.mappedUnitCategory = mappedUnitCategory;
	}
	
	
	
	
}

package com.petrabytes.views.well_Information;

public class Well_Registry_Info {
	private long wellId;
	private String wellName;
	private String well_Id;
	private long basinID;

	/**
	 * @param wellId
	 * @param wellName
	 * @param well_Id
	 * @param basinID
	 */
	public Well_Registry_Info(long wellId, String wellName, String well_Id, long basinID) {
		this.wellId = wellId;
		this.wellName = wellName;
		this.well_Id = well_Id;
		this.basinID = basinID;
	}
	
	

	public long getWellId() {
		return wellId;
	}

	public void setWellId(long wellId) {
		this.wellId = wellId;
	}

	public String getWellName() {
		return wellName;
	}

	public void setWellName(String wellName) {
		this.wellName = wellName;
	}

	public String getWell_Id() {
		return well_Id;
	}

	public void setWell_Id(String well_Id) {
		this.well_Id = well_Id;
	}

	public long getBasinID() {
		return basinID;
	}

	public void setBasinID(long basinID) {
		this.basinID = basinID;
	}
}

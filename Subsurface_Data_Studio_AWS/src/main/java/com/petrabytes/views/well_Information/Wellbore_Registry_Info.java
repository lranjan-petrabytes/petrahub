package com.petrabytes.views.well_Information;

public class Wellbore_Registry_Info {

	private long wellboreId;
	private String wellboreName;
	private String wellboreID;
	private long wellID;
	
	private String realTimeWellId ;

	/**
	 * @param wellboreId
	 * @param wellboreName
	 * @param wellboreID2
	 * @param wellID
	 */
	
	public Wellbore_Registry_Info(long wellboreId, String wellboreName, String wellboreID2, long wellID ) {
		this.wellboreId = wellboreId;
		this.wellboreName = wellboreName;
		wellboreID = wellboreID2;
		this.wellID = wellID;
	}
	
	public Wellbore_Registry_Info(long wellboreId, String wellboreName, String wellboreID2, String wellID) {
		this.wellboreId = wellboreId;
		this.wellboreName = wellboreName;
		wellboreID = wellboreID2;
		this.realTimeWellId =wellID;
	}


	public long getWellboreId() {
		return wellboreId;
	}

	public void setWellboreId(long wellboreId) {
		this.wellboreId = wellboreId;
	}

	public String getWellboreName() {
		return wellboreName;
	}

	public void setWellboreName(String wellboreName) {
		this.wellboreName = wellboreName;
	}

	public long getWellID() {
		return wellID;
	}

	public void setWellID(long wellID) {
		this.wellID = wellID;
	}

	public String getWellboreID() {
		return wellboreID;
	}

	public void setWellboreID(String wellboreID) {
		this.wellboreID = wellboreID;
	}

	public String getRealTimeWellId() {
		return realTimeWellId;
	}

	public void setRealTimeWellId(String realTimeWellId) {
		this.realTimeWellId = realTimeWellId;
	}

}

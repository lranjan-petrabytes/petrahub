package com.petrabytes.config;

public class Storage_Info_view {
	private String storageName;
	private int accessKey;//accountName;
	private int scerectKey;//AccountKey;
	private String bucketName;//ContainerName;
	private String cloudProvider;
	public String getStorageName() {
		return storageName;
	}
	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}
	public int getAccessKey() {
		return accessKey;
	}
	public void setAccessKey(int accessKey) {
		this.accessKey = accessKey;
	}
	public int getScerectKey() {
		return scerectKey;
	}
	public void setScerectKey(int scerectKey) {
		this.scerectKey = scerectKey;
	}
	public String getBucketName() {
		return bucketName;
	}
	public void setBucketName(String bucketName) {
		this.bucketName = bucketName;
	}
	public String getCloudProvider() {
		return cloudProvider;
	}
	public void setCloudProvider(String cloudProvider) {
		this.cloudProvider = cloudProvider;
	}
}
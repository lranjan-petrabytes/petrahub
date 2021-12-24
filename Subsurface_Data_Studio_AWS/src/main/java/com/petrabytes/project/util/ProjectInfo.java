package com.petrabytes.project.util;

public class ProjectInfo {
	private String projectName;
	private String projectID;
	private ProjectSettingsInfo projectSettings = new ProjectSettingsInfo();

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectID() {
		return projectID;
	}

	public void setProjectID(String projectID) {
		this.projectID = projectID;
	}

	public ProjectSettingsInfo getProjectSettings() {
		return projectSettings;
	}

	public void setProjectSettings(ProjectSettingsInfo projectSettings) {
		this.projectSettings = projectSettings;
	}
}

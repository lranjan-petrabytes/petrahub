package com.petrabytes.databricks;

import java.util.Hashtable;

/**
 * Class to manage the cluster settings for user pool Due to the volume of data
 * and number of users, group of users will be allocated to a set of clusters
 * for load balancing
 * 
 * @author lranjan, vpatel, sgunturu
 *
 */
public class ClusterSettings {

	private static ClusterSettings _singleton = null;

	private Hashtable<String, UserClusterSettings> _userClusterTable;

	public static ClusterSettings getInstance() {
		if (_singleton == null) {
			_singleton = new ClusterSettings();
		}
		return _singleton;
	}

	private ClusterSettings() {
		_userClusterTable = new Hashtable<String, UserClusterSettings>();
	}

	public void addOrUpdateUserClusterSettings(String selectedUserID, String selectedClusterID) {
		if(this._userClusterTable.contains(selectedUserID)) {
			this._userClusterTable.remove(selectedUserID);
		}
		UserClusterSettings ucs = new UserClusterSettings();
		ucs.set_currentUserID(selectedUserID);
		ucs.set_currentClusterID(selectedClusterID);
		this._userClusterTable.put(selectedUserID, ucs);
	}
	
	public UserClusterSettings getClusterSettingsForUser(String userID) {
		return this._userClusterTable.get(userID);
	}
}

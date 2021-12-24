package com.petrabytes.databricks;

/**
 * As the user base increases, we want to track each user and which cluster he
 * /she is using. We will assign users to a cluster group to access
 * corresponding cluster.
 * 
 * @author lranjan, vpatel, sgunturu
 *
 */
public class UserClusterSettings {

	private String _currentClusterID;
	private String _currentUserID;

	public String get_currentClusterID() {
		return _currentClusterID;
	}

	public void set_currentClusterID(String _currentClusterID) {
		this._currentClusterID = _currentClusterID;
	}

	public String get_currentUserID() {
		return _currentUserID;
	}

	public void set_currentUserID(String _currentUserID) {
		this._currentUserID = _currentUserID;
	}

}

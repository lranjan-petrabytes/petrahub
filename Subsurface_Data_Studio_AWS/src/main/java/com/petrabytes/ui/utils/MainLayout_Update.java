package com.petrabytes.ui.utils;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import com.petrabytes.keyvault.AWS_Key_Vault;
import com.petrabytes.keyvault.PH_KeyVault;
import com.petrabytes.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.tabs.Tab;

public class MainLayout_Update {

	/**
	 * update side bar with tabs for selected view
	 * 
	 * @param tabs
	 */
	public static void updateTabs(Tab[] tabs) {
		MainLayout mainLayout = getInstance();
		if (mainLayout != null) {
			mainLayout.updateMenuItems(tabs);
		}
	}

	/**
	 * update cluster labe by passing string value
	 * 
	 * @throws Exception
	 */
	public static void updateClusterStatus() throws Exception {
		MainLayout mainLayout = getInstance();
		if (mainLayout != null) {
			mainLayout.updateClusterStatus(getCurrentClusterStatusJava());
		}
	}

	/**
	 * set top toolbar sub component (addButton,closeButton , saveButton and
	 * openButton) to Enable true or false
	 * 
	 * @param flag
	 */
	public static void setTopToolbar_component_Enable(boolean flag) {

		MainLayout mainLayout = getInstance();
		if (mainLayout != null) {
			mainLayout.setTopToolbarComponent_Enable(flag);
		}
	}

	/**
	 * call api (GET) to check weither cluster is running or stop or pending
	 * 
	 * @return String value :- cluster status
	 * @throws Exception
	 */
	public static String getCurrentClusterStatusJava() throws Exception {
		// Sending get request
		// Sending get request
		
			String url_value;
			String token_value;
			String cluster_id;
			String databricks_status = PH_KeyVault.getSecretKey("Databricks-Status");
			if (databricks_status.equalsIgnoreCase("azure")) {
				url_value = PH_KeyVault.getSecretKey("Databricks-URL");
				token_value = PH_KeyVault.getSecretKey("Databricks-Token");
				cluster_id = PH_KeyVault.getSecretKey("Databricks-Cluster");
			} else {
				url_value = AWS_Key_Vault.getSecret("Databricks-URL");
				token_value = AWS_Key_Vault.getSecret("Databricks-Token");
				cluster_id = AWS_Key_Vault.getSecret("Databricks-Cluster");
			}
			
			int indexOfCloud = url_value.indexOf("cloud");
	        if (indexOfCloud == -1) {
	        
	        	url_value = url_value + "/api/2.0/clusters/get?cluster_id=" + cluster_id;
	        } else {
	        
	        	int indexOfCom = url_value.indexOf("com") + 3;
	        	url_value = url_value.substring(0, indexOfCom);
	        	url_value = url_value + "/api/2.0/clusters/get?cluster_id=" + cluster_id;
	        }
//			URL url = new URL(
//					"https://adb-3305711601758284.4.azuredatabricks.net/api/2.0/clusters/get?cluster_id=0304-081914-quits100");
			URL url = new URL(url_value);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

//			conn.setRequestProperty("Authorization", "Bearer " + "dapi76e6ef0c81459fca303f219c61b4fa9c");
			conn.setRequestProperty("Authorization", "Bearer " + token_value);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestMethod("GET");

			JSONObject jsonObject = new JSONObject(IOUtils.toString(conn.getInputStream(), Charset.forName("UTF-8")));
			// printing result from response
			System.out.println("Cluster " + jsonObject.getString("state"));
			String status = jsonObject.getString("state");

			return status;

	}
	
	public static void updateEnable_topBar() {
		String enableKeys = "addProject,openProject,deleteProject";
		String disableKeys = "saveProject,closeProject";

		
		/*
		 * disable
		 */
		UI.getCurrent().getPage().executeJavaScript("disableComponents($0)", disableKeys);
		/*
		 * enable
		 */
		UI.getCurrent().getPage().executeJavaScript("enableComponents($0)", enableKeys);

	}

	/**
	 * Returns the current AppLayout
	 * 
	 * @return
	 */
	public static MainLayout getInstance() {
		return (MainLayout) UI.getCurrent().getChildren().filter(component -> component.getClass() == MainLayout.class)
				.findFirst().orElse(null);
	}

}

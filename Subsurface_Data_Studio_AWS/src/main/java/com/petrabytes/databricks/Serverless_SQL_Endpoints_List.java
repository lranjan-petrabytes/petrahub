package com.petrabytes.databricks;

import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Serverless_SQL_Endpoints_List {
	public ArrayList<DatabricksClusterPopupInfo> getDatabricks_Cluster_List(String urlvalue, String username, String token) throws Exception {
		ArrayList<DatabricksClusterPopupInfo> popupObjectList = new ArrayList<DatabricksClusterPopupInfo>();
		// Sending get request
//		URL url = new URL(
//				"https://adb-3305711601758284.4.azuredatabricks.net/api/2.0/clusters/list");
		URL url = new URL(urlvalue);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

//		conn.setRequestProperty("Authorization", "Bearer " + "dapi76e6ef0c81459fca303f219c61b4fa9c");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		// e.g. bearer token=
		// eyJhbGciOiXXXzUxMiJ9.eyJzdWIiOiPyc2hhcm1hQHBsdW1zbGljZS5jb206OjE6OjkwIiwiZXhwIjoxNTM3MzQyNTIxLCJpYXQiOjE1MzY3Mzc3MjF9.O33zP2l_0eDNfcqSQz29jUGJC-_THYsXllrmkFnk85dNRbAw66dyEKBP5dVcFUuNTA8zhA83kk3Y41_qZYx43T

		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestMethod("GET");

		JSONObject jsonObject = new JSONObject(IOUtils.toString(conn.getInputStream(), Charset.forName("UTF-8")));
		// printing result from response
//		System.out.println("Response:-" + jsonObject.getString("state"));
		System.out.println("Response:-" + jsonObject.toString());
		
		for (Object key : jsonObject.keySet()) {
			// based on you key types
			String keyStr = (String) key;
			Object keyvalue = jsonObject.get(keyStr);

			JSONArray object = (JSONArray) keyvalue;
			for (int i = 0; i < object.length(); i++) {
				JSONObject jsonObjectJobs2 = object.getJSONObject(i);
				String cluster_name = jsonObjectJobs2.getString("name");
				String cluster_ID = jsonObjectJobs2.getString("id");
				String creator = jsonObjectJobs2.getString("creator_name");
				String state = jsonObjectJobs2.getString("state");
				String instance_source1 = jsonObjectJobs2.getString("size");
//				JSONObject instance_source2 = new JSONObject(instance_source1);
//				String instance_source = instance_source2.get("node_type_id").toString();
				String autoscale_max_worker;
				String autoscale_min_worker;
				try {
					String autoscale1 = jsonObjectJobs2.get("autoscale").toString();
					JSONObject autoscale2 = new JSONObject(autoscale1);
					autoscale_max_worker = autoscale2.get("max_workers").toString();
					autoscale_min_worker = autoscale2.get("min_workers").toString();
				}
				catch(Exception e)
				{
					autoscale_max_worker = "0";
					autoscale_min_worker = "0";
				}
				String instance_source = "";
				DatabricksClusterPopupInfo popupObject = new DatabricksClusterPopupInfo(cluster_name, cluster_ID,
						creator, instance_source, autoscale_max_worker, autoscale_min_worker,state);	
				popupObjectList.add(popupObject);
				System.out.println("Cluster Name: " + cluster_name);
				System.out.println("cluster_ID: " + cluster_ID);
				System.out.println("creator: " + creator);
				System.out.println("instance_source: " + instance_source);
				System.out.println("Worker(Max): " + autoscale_max_worker);
				System.out.println("Worker(Min): " + autoscale_min_worker);
				System.out.println("Status: " + state);				
				System.out.println("*************");
				
//	            JSONObject jobObject = new JSONObject(jobSettings);
//	            String jobName = jobObject.getString("name");
//	            System.out.println(jobID);
				
			}
		}
		return popupObjectList;
	}
}
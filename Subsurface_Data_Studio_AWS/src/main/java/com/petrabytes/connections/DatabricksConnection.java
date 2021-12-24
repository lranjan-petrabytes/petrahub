package com.petrabytes.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrabytes.config.Cloud_Util;
import com.petrabytes.config.Cluster_Info_view;
import com.petrabytes.keyvault.AWS_Key_Vault;
import com.petrabytes.keyvault.PH_KeyVault;
import com.petrabytes.views.basin.BasinViewGridInfo;
import com.petrabytes.views.wellRegistryQuery.WellRegistryQuries;
import com.vaadin.flow.data.provider.ListDataProvider;

public class DatabricksConnection {

	private String driver;
	private String url;
	private String username;
	private String password;
	private String number;
	private String cluster_id;
	private String url_value;
	private String username_value;
	private String token_value;
	private Boolean serverlessFlag;
	private Cluster_Info_view config;
	private String endpointID;
	
	public DatabricksConnection() {

	}
	
	public Connection connect() {
		
		String cloud_provider = Cloud_Util.getInstance().getCloud_provider();
		
		if (cloud_provider.equalsIgnoreCase("azure")) {
			url_value = PH_KeyVault.getSecretKey("Databricks-URL");
			username_value = PH_KeyVault.getSecretKey("Databricks-Username");
			token_value = PH_KeyVault.getSecretKey("Databricks-Token");
			cluster_id = PH_KeyVault.getSecretKey("Databricks-Cluster");
		} else {
			serverlessFlag = true;
			if (serverlessFlag) {
				String jsonString = AWS_Key_Vault.getSecret("databricks-serverless-current-config");
				endpointID = AWS_Key_Vault.getSecret("databricks-serverless-endpoint");
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					config = objectMapper.readValue(jsonString, Cluster_Info_view.class);
					url_value = config.getUrl();
					username_value = config.getUserName();
					token_value = config.getToken();
				} catch (JsonMappingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				url_value = AWS_Key_Vault.getSecret("Databricks-URL");
				username_value = AWS_Key_Vault.getSecret("Databricks-Username");
				token_value = AWS_Key_Vault.getSecret("Databricks-Token");
				cluster_id = AWS_Key_Vault.getSecret("Databricks-Cluster");
			}
			
		}
		driver = "com.simba.spark.jdbc.Driver";
//		url = "jdbc:spark://adb-3305711601758284.4.azuredatabricks.net:443/default;transportMode=http;ssl=1;httpPath=sql/protocolv1/o/3305711601758284/0304-081914-quits100";
		url_value = url_value.replace("https://", "");
//		cluster_id = PH_KeyVault.getSecretKey("Databricks-Cluster");
		System.out.println(cluster_id);
		int indexOfCloud = url_value.indexOf("cloud");
		
        if (indexOfCloud == -1) {
        	
        	int beginIndex = url_value.indexOf("-") + 1;
    		int endIndex = url_value.indexOf(".");
    		number = url_value.substring(beginIndex, endIndex);

    		url = "jdbc:spark://" + url_value + ":443/default;transportMode=http;ssl=1;httpPath=sql/protocolv1/o/" + number + "/" + cluster_id;
        } else {
        	// parsing aws url
        	if (serverlessFlag) {
        		url_value = url_value.replace("https://", "");
        		
        		url = "jdbc:spark://" + url_value + ":443/default;transportMode=http;ssl=1;"
    					+ "AuthMech=3;httpPath=/sql/1.0/endpoints/" + endpointID;
        	} else {
        		int beginIndex = url_value.indexOf("=") + 1;
        		int endIndex = url_value.indexOf("#");
        		number = url_value.substring(beginIndex, endIndex);
            	int indexOfCom = url_value.indexOf("com") + 3;
            	url_value = url_value.substring(0, indexOfCom);
            	url = "jdbc:spark://" + url_value + ":443/default;transportMode=http;ssl=1;httpPath=sql/protocolv1/o/" + number + "/" + cluster_id;
            	
        	}
        	
        }

		username = username_value;
		password = token_value;
		
		//testing serverless sql:::::::::::::::::::::
//		url = "jdbc:spark://dbc-59ae521b-6354.cloud.databricks.com:443/default;"
//				+ "transportMode=http;ssl=1;AuthMech=3;httpPath=/sql/1.0/endpoints"
//				+ "/2edc7c9e73f53eea";
//		password = "dapib6a8aa1c67e61027e8dad070405caa2f";
//		username = "sashigunturu@petrabytes.com";
		
		Connection con = null;
		
		try {
			Class.forName(driver);
			System.out.println("Trying to Connect to Databricks Cluster . . . ");
			
			con = DriverManager.getConnection(url, username, password);
			System.out.println("Connection Successful");

			Statement stmt = con.createStatement();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
}

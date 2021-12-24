package com.petrabytes.views.dts;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.petrabytes.connections.DatabricksConnection;


public class DTSQueries {
	
public static List getTimestamp_ids(String query) throws SQLException {
		
		List timestamp_list = new ArrayList<DTS_Timestamp>();
		
		Connection connection = new DatabricksConnection().connect();
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet queryResult = stmt.executeQuery(query);
			ResultSetMetaData rsmd = queryResult.getMetaData();
			int numColumns = rsmd.getColumnCount();
			
			while (queryResult.next()) {
//				timestamp_list.add(queryResult.getObject(i));	
				String index = null;
				String timestamp = null;
				
				List timestamp_data = new ArrayList<>();
				for (int i = 1; i <= numColumns; i++) {

					timestamp_data.add(queryResult.getObject(i));
					
				}
				index = timestamp_data.get(0).toString();
				timestamp = timestamp_data.get(1).toString();
				DTS_Timestamp depthData = new DTS_Timestamp(Long.parseLong(index), timestamp);
				timestamp_list.add(depthData);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.close();
		return timestamp_list;
	
	}

	public static String[][] getDTSData(String query, int height, int width) throws SQLException{
		String[][] dts_data_list = new String[height][width];
		
		Connection connection = new DatabricksConnection().connect();
		try {
			
			Statement stmt = connection.createStatement();
			ResultSet queryResult = stmt.executeQuery(query);
			ResultSetMetaData rsmd = queryResult.getMetaData();
			int row = 0;
			while (queryResult.next()) {
				
				List data = new ArrayList<String[]>();
				int numColumns = rsmd.getColumnCount();
				for (int i = 1; i <= numColumns; i++) {
					dts_data_list[row][i-1] = (String) queryResult.getObject(i);
					
				}
				row++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		connection.close();
		return dts_data_list;
	}
	
	public static List getdepths(String query) throws SQLException {
		
		List depthsList = new ArrayList<DTS_Depth>();
		
		Connection connection = new DatabricksConnection().connect();
		
		try {
			Statement stmt = connection.createStatement();
			ResultSet queryResult = stmt.executeQuery(query);
			ResultSetMetaData rsmd = queryResult.getMetaData();
			int numColumns = rsmd.getColumnCount();
			while (queryResult.next()) {
				String depthid = null;
				String depth = null;
				List depthDataList = new ArrayList<>();
				for (int i = 1; i <= numColumns; i++) {
//					if (i == 1) depthid = (Long) queryResult.getObject(i);
//					if (i == 2) depth = (Double) queryResult.getObject(i);
					depthDataList.add(queryResult.getObject(i));
					
				}
				depthid = depthDataList.get(0).toString();
				depth = depthDataList.get(1).toString();
				DTS_Depth depthData = new DTS_Depth(Long.parseLong(depthid), Double.parseDouble(depth));
				depthsList.add(depthData);
			}
			
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		connection.close();
		return depthsList;
	
	}
}

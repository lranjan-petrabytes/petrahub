package com.petrabytes.views.drilling_report;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.petrabytes.connections.DatabricksConnection;

public class DrillReportQuery {

	public ArrayList<String> getGISQuery(String query) {
		Connection connection = new DatabricksConnection().connect();

		try {
			Statement stmt = connection.createStatement();

			ResultSet queryResult = stmt.executeQuery(query);
			//System.out.println(queryResult);

			ArrayList<String> resultSetArray = new ArrayList<>();
			int count = 0;

			ResultSetMetaData rsmd = queryResult.getMetaData();
			while (queryResult.next()) {
				int numColumns = rsmd.getColumnCount();
				
				StringBuilder sb = new StringBuilder();

                for (int i = 1; i <= numColumns; i++) {
                	if(String.valueOf(queryResult.getString(i)).equals("null"))
                	{
                		sb.append(",");
                	}
                	else if(i != numColumns)
                	{
                		sb.append(String.valueOf(queryResult.getString(i)) + ",");
                	}
                	else
                	{
                		sb.append(String.valueOf(queryResult.getString(i)));
                	}
                }
                resultSetArray.add(sb.toString());
            
				count++;
			}
			
			connection.close();
			return resultSetArray;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error");
			e.printStackTrace();
		}
		return null;
	}
}

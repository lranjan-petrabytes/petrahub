package com.petrabytes.views.wellbore;

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

public class DeviationQuery {

	public String getDeviationQuery(String query) {
		Connection connection = new DatabricksConnection().connect();

		try {
			Statement stmt = connection.createStatement();

			ResultSet queryResult = stmt.executeQuery(query);
			//System.out.println(queryResult);

			//Checks if there are any data points
			int count = 0;
			ResultSetMetaData rsmd = queryResult.getMetaData();
			
			String object = null;
			while (queryResult.next()) {
				int numColumns = rsmd.getColumnCount();
				for (int i = 1; i <= numColumns; i++) {
					String column_name = rsmd.getColumnName(i);
					object = queryResult.getObject(column_name).toString();
					count++;
				}
			}

			if (count == 0)
			{
				return "DNE";
			}
			
			connection.close();
			return object;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("error");
			e.printStackTrace();
		}
		return null;
	}
}

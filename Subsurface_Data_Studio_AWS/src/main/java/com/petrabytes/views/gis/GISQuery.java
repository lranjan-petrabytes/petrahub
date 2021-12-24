package com.petrabytes.views.gis;

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

public class GISQuery {

	public JSONArray getGISQuery(String query) {
		Connection connection = new DatabricksConnection().connect();

		try {
			Statement stmt = connection.createStatement();

			ResultSet queryResult = stmt.executeQuery(query);
			System.out.println(queryResult);

			JSONArray array = new JSONArray();
			int count = 0;

			ResultSetMetaData rsmd = queryResult.getMetaData();
			while (queryResult.next()) {
				int numColumns = rsmd.getColumnCount();
				JSONObject object = new JSONObject();
				for (int i = 1; i <= numColumns; i++) {
					String column_name = rsmd.getColumnName(i);
					object.put(column_name, queryResult.getObject(column_name));
				}
				array.put(count, object);
				count++;
			}

			connection.close();
			return array;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}

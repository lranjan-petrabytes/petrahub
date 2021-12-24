package com.petrabytes.views.welllogs;

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
import com.petrabytes.views.wellRegistryQuery.WellboreListInfo;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;

public class WellLogQueries {

	public List<WellLogsInfo> queryLogNamesData(String query) throws SQLException {

		Connection connection = new DatabricksConnection().connect();

		Statement stmt = connection.createStatement();

		ResultSet queryResult = stmt.executeQuery(query);
		ResultSetMetaData rsmd = queryResult.getMetaData();
//		List<List<String>> resultList = convertToList(queryResult);

		List<WellLogsInfo> traceListData = new ArrayList<>();
//		int numColumns = rsmd.getColumnCount();

		while (queryResult.next()) {
			WellLogsInfo data = new WellLogsInfo();
			int numColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				String column_name = rsmd.getColumnName(i);
				Object object = queryResult.getObject(column_name);
				if (object != null) {
					if (column_name.equalsIgnoreCase("mnemonic")) {
						data.setLogname(queryResult.getObject(column_name).toString());
						
					}
					
					if (column_name.equalsIgnoreCase("unit")) {
						data.setUnit(queryResult.getObject(column_name).toString());
					}
					
					if (column_name.equalsIgnoreCase("unit_category")) {
						data.setUnitcategory(queryResult.getObject(column_name).toString());
					}
					if (column_name.equalsIgnoreCase("basin_id")) {
						data.setBasinID(queryResult.getObject(column_name).toString());
					}
					
					if (column_name.equalsIgnoreCase("basin_name")) {
						data.setBasinName(queryResult.getObject(column_name).toString());
					}
					
					if (column_name.equalsIgnoreCase("wellbore_name")) {
						data.setWellboreName(queryResult.getObject(column_name).toString());
					}
					if (column_name.equalsIgnoreCase("wellbore_id")) {
						data.setWellboreID(queryResult.getObject(column_name).toString());
					}
					
					if (column_name.equalsIgnoreCase("well_id")) {
						data.setWellID(queryResult.getObject(column_name).toString());
					}
					if (column_name.equalsIgnoreCase("well_name")) {
						data.setWellNmae(queryResult.getObject(column_name).toString());
					}
					
					if (column_name.equalsIgnoreCase("log_id")) {
						data.setLogid(queryResult.getObject(column_name).toString());
					}
					if (column_name.equalsIgnoreCase("log_file_id")) {
						data.setLogfileid(queryResult.getObject(column_name).toString());
					}
				}
			}
			traceListData.add(data);
		}
		

		connection.close();
		return traceListData;

	}

	public JsonArray queryLogsData(String query, List<String> units) {
		Connection connection = new DatabricksConnection().connect();

		try {
			Statement stmt = connection.createStatement();
			ResultSet queryResult = stmt.executeQuery(query);

			ResultSetMetaData rsmd = queryResult.getMetaData();

			int numColumns = rsmd.getColumnCount();

			List<Double[]> dataArray = new ArrayList<>();

			while (queryResult.next()) {

				Double[] data = new Double[numColumns];
				for (int i = 1; i <= numColumns; i++) {
					String column_name = rsmd.getColumnName(i);

					Object object = queryResult.getObject(column_name);
					if (object == null) {
						data[i - 1] = Double.NaN;
					} else {
						String dataValue = queryResult.getObject(column_name).toString();
						data[i - 1] = Double.parseDouble(dataValue);
					}
				}
				dataArray.add(data);
			}

			JsonArray logsArray = Json.createArray();
			for (int i = 0; i < numColumns; i++) {
				if (i==0) {
					continue;
				}
				JsonObject mainObject = Json.createObject();

				mainObject.put("seriesNameArray", rsmd.getColumnName(i + 1) + " (" + units.get(i - 1) + ")");
//				mainObject.put("nameArray", rsmd.getColumnName(i + 1) + " (" + units.get(i - 1) + ")");

				int count = 0;
				JsonArray datasetArray = Json.createArray();
				for (Double[] data : dataArray) {

					double depth = data[0];
					double value = data[i];

					JsonObject xyObject = Json.createObject();
					xyObject.put("x", value);
					xyObject.put("y", depth);

					datasetArray.set(count, xyObject);

					count++;
				}

				mainObject.put("seriesData", datasetArray);
				logsArray.set(i - 1, mainObject);
			}

			return logsArray;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<String> readWellbores(String query) {
		Connection connection = new DatabricksConnection().connect();
		List<String> wellbores = new ArrayList<>();

		Statement stmt;
		try {
			stmt = connection.createStatement();
			ResultSet queryResult = stmt.executeQuery(query);

			ResultSetMetaData rsmd = queryResult.getMetaData();
			while (queryResult.next()) {
				String column_name = rsmd.getColumnName(1);
				wellbores.add(queryResult.getObject(column_name).toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wellbores;
	}

	private List<List<String>> convertToList(ResultSet queryResult) throws SQLException {

		List<List<String>> finalList = new ArrayList<>();

		ResultSetMetaData rsmd = queryResult.getMetaData();
		while (queryResult.next()) {
			List<String> data = new ArrayList<>();

			int numColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				String column_name = rsmd.getColumnName(i);
				Object object = queryResult.getObject(column_name);
				if (object != null) {
					data.add(queryResult.getObject(column_name).toString());
				}
			}
			finalList.add(data);
		}

		return finalList;
	}
	
	public String getMnemonicByUnitCategory(String query) throws SQLException {
		Connection connection = new DatabricksConnection().connect();

		Statement stmt = connection.createStatement();
		String data = new String();
		ResultSet queryResult = stmt.executeQuery(query);
		ResultSetMetaData rsmd = queryResult.getMetaData();
		while (queryResult.next()) {
			

			int numColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				String column_name = rsmd.getColumnName(i);
				Object object = queryResult.getObject(column_name);
				if (object != null) {
					data = queryResult.getObject(column_name).toString();
				}
			}
			//finalList.add(data);
		}
		return data;
		
	}

}

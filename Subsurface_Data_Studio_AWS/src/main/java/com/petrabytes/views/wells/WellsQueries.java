package com.petrabytes.views.wells;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.petrabytes.connections.DatabricksConnection;

public class WellsQueries {

	public List queryAllCompanies(Connection connection) throws SQLException {

		List arrayList = null;
//		Connection connection = new DatabricksConnection().connect();

		Statement stmt = connection.createStatement();
		String query = "select distinct(companyname)  from wells_db.wells where companyname is not Null order by companyname asc";
		ResultSet queryResult = stmt.executeQuery(query);
		System.out.println(queryResult);

		ResultSetMetaData rsmd = queryResult.getMetaData();

		int numColumns = rsmd.getColumnCount();
		String column_name = rsmd.getColumnName(numColumns);

		arrayList = convertToList(queryResult);

		return arrayList;
	}

	public List queryAllStates(Connection connection) throws SQLException {

		List arrayList = null;
//		Connection connection = new DatabricksConnection().connect();

		Statement stmt = connection.createStatement();
		String query = "select distinct(state)  from wells_db.wells order by state asc";
		ResultSet queryResult = stmt.executeQuery(query);
		System.out.println(queryResult);

		ResultSetMetaData rsmd = queryResult.getMetaData();

		int numColumns = rsmd.getColumnCount();
		String column_name = rsmd.getColumnName(numColumns);

		arrayList = convertToList(queryResult);

		return arrayList;
	}

	public List queryAllStatus(Connection connection) throws SQLException {

		List arrayList = null;
//		Connection connection = new DatabricksConnection().connect();

		Statement stmt = connection.createStatement();
		String query = "select distinct(status)  from wells_db.wells where status is not Null order by status asc";
		ResultSet queryResult = stmt.executeQuery(query);
		System.out.println(queryResult);

		ResultSetMetaData rsmd = queryResult.getMetaData();

		int numColumns = rsmd.getColumnCount();
		String column_name = rsmd.getColumnName(numColumns);

		arrayList = convertToList(queryResult);

		return arrayList;
	}

	public List queryAllWellTypes(Connection connection) throws SQLException {

		List arrayList = null;
//		Connection connection = new DatabricksConnection().connect();

		Statement stmt = connection.createStatement();
		String query = "select distinct(welltype)  from wells_db.wells where welltype is not Null order by welltype asc";
		ResultSet queryResult = stmt.executeQuery(query);
		System.out.println(queryResult);

		ResultSetMetaData rsmd = queryResult.getMetaData();

		int numColumns = rsmd.getColumnCount();
		String column_name = rsmd.getColumnName(numColumns);

		arrayList = convertToList(queryResult);

		return arrayList;
	}

	private List convertToList(ResultSet queryResult) throws SQLException {
		JSONArray json = new JSONArray();
		List finalList = new ArrayList();

		ResultSetMetaData rsmd = queryResult.getMetaData();
		while (queryResult.next()) {
			int numColumns = rsmd.getColumnCount();
			JSONObject obj = new JSONObject();
			for (int i = 1; i <= numColumns; i++) {
				String column_name = rsmd.getColumnName(i);
				finalList.add(queryResult.getObject(column_name));
			}
		}

		return finalList;

	}

	public List queryListData(String query) throws SQLException {

		Connection connection = new DatabricksConnection().connect();

		Statement stmt = connection.createStatement();

		ResultSet queryResult = stmt.executeQuery(query);
		System.out.println(queryResult);

		List finalList = new ArrayList();

		ResultSetMetaData rsmd = queryResult.getMetaData();
		while (queryResult.next()) {

			int numColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				String column_name = rsmd.getColumnName(i);
				System.out.println(column_name + " - " + rsmd.getColumnTypeName(i));

				finalList.add(queryResult.getObject(column_name));
			}
		}

		connection.close();
		return finalList;

	}

	public JSONArray querySearchData(String query) throws SQLException {

		Connection connection = new DatabricksConnection().connect();

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

	}

	public String querySingleValue(String query) throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		ResultSet queryResult = stmt.executeQuery(query);
		System.out.println(queryResult);

		String finalNumber = null;

		ResultSetMetaData rsmd = queryResult.getMetaData();
		while (queryResult.next()) {
			int numColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				String column_name = rsmd.getColumnName(i);
				finalNumber = queryResult.getObject(column_name).toString();
			}
		}
		connection.close();
		return finalNumber;
	}

	private JSONArray convertToJSON(ResultSet rs) throws SQLException {

		JSONArray json = new JSONArray();
		ResultSetMetaData rsmd = rs.getMetaData();
//        int k = 0;
		while (rs.next()) {
			int numColumns = rsmd.getColumnCount();
			JSONObject obj = new JSONObject();
			String commaSeparatedValueStr = "";
			String column_name = rsmd.getColumnName(numColumns);
			String trace_index = (String) rs.getObject(column_name);
			for (int i = 1; i <= numColumns - 1; i++) {
				column_name = rsmd.getColumnName(i);
				commaSeparatedValueStr += rs.getObject(column_name);
				if (i <= numColumns - 2) {
					commaSeparatedValueStr += ",";
				}
//                obj.put(column_name, rs.getObject(column_name));
			}
			obj.put(trace_index, commaSeparatedValueStr);
			json.put(obj);
		}
		return json;
	}

}

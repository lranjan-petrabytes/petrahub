package com.petrabytes.views.seismic;

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

public class SeismicQueries {

	public List<SeismicHeaderInfo> querySeismicHeaders() throws SQLException {

		String query;
		ResultSet queryResult;
		SeismicHeaderInfo header;

		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		List<SeismicHeaderInfo> finalList = new ArrayList<SeismicHeaderInfo>();
		
		query = "select min( INLINE_3D ), max( INLINE_3D ), min(CROSSLINE_3D), max(CROSSLINE_3D), min(CDP_X), max(CDP_X), min(CDP_Y), max(CDP_Y) from seismic_volve_db.seismic_header";
		queryResult = stmt.executeQuery(query);
		List<String> metaDataList = convertToList(queryResult);
		header = new SeismicHeaderInfo("Inline", metaDataList.get(0), metaDataList.get(1));
		finalList.add(header);
		header = new SeismicHeaderInfo("Xline", metaDataList.get(2), metaDataList.get(3));
		finalList.add(header);
		header = new SeismicHeaderInfo("X Coordinate", metaDataList.get(4), metaDataList.get(5));
		finalList.add(header);
		header = new SeismicHeaderInfo("Y Coordinate", metaDataList.get(6), metaDataList.get(7));
		finalList.add(header);

		connection.close();

		return finalList;
	}

	public String queryTotalTraces() throws SQLException {

		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		String query = "select count(*) from seismic_volve_db.seismic_trace_data";
		ResultSet queryResult = stmt.executeQuery(query);
		String totalTraces = convertSingleValueToString(queryResult);

		connection.close();

		return totalTraces;
	}

	public List<SeismicTraceDataInfo> querySeismicTraceData(String selectedValue) throws SQLException {

		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		String query = "select * from seismic_volve_db.seismic_trace_data where trace_id = '" + selectedValue + "'";
		ResultSet queryResult = stmt.executeQuery(query);
		List<String> traceDataList = convertToList(queryResult);

		List<SeismicTraceDataInfo> finalList = new ArrayList<SeismicTraceDataInfo>();
		int count = 1;
		for (String traceData : traceDataList) {
			if (!traceData.equals(selectedValue)) {
			SeismicTraceDataInfo traceDataObject = new SeismicTraceDataInfo(count, count*20, traceData);
			finalList.add(traceDataObject);
			count = count + 1;
			}
		}

		connection.close();

		return finalList;
	}
	
	public List<SeismicHeaderInfo> queryFullTraceHeader(String selectedValue) throws SQLException {

		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		String query = "select * from seismic_volve_db.seismic_header where TRACE_SEQUENCE_file = '" + selectedValue + "'";
		ResultSet queryResult = stmt.executeQuery(query);
		List<List> traceHeaderList = convertToHeaderValueList(queryResult);

		List<SeismicHeaderInfo> finalList = new ArrayList<SeismicHeaderInfo>();
		for (List traceData : traceHeaderList) {
			
			SeismicHeaderInfo traceDataObject = new SeismicHeaderInfo();
			traceDataObject.setHeaderName((String) traceData.get(0));
			traceDataObject.setHeaderValue((String) traceData.get(1));
			finalList.add(traceDataObject);
		}

		connection.close();

		return finalList;
	}

	
	private List<String> convertToList(ResultSet queryResult) throws SQLException {

		List<String> finalList = new ArrayList<String>();

		ResultSetMetaData rsmd = queryResult.getMetaData();
		while (queryResult.next()) {
			int numColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				String column_name = rsmd.getColumnName(i);
				finalList.add(queryResult.getObject(column_name).toString());
			}
		}

		return finalList;
	}
	
	private List<List> convertToHeaderValueList(ResultSet queryResult) throws SQLException {

		List<List> finalList = new ArrayList<List>();

		ResultSetMetaData rsmd = queryResult.getMetaData();
		while (queryResult.next()) {
			int numColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				List<String> singleList = new ArrayList<String>();
				String column_name = rsmd.getColumnName(i);
				String column_value = queryResult.getObject(column_name).toString();
				singleList.add(column_name);
				singleList.add(column_value);
				finalList.add(singleList);
			}
		}

		return finalList;

	}

	public String convertSingleValueToString(ResultSet queryResult) throws SQLException {

		String finalValue = null;

		ResultSetMetaData rsmd = queryResult.getMetaData();
		while (queryResult.next()) {
			int numColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				String column_name = rsmd.getColumnName(i);
				finalValue = queryResult.getObject(column_name).toString();
			}
		}
		return finalValue;
	}
}

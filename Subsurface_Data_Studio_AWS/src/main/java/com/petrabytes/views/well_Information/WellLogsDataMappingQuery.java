package com.petrabytes.views.well_Information;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import com.petrabytes.connections.DatabricksConnection;
import com.petrabytes.views.wellRegistryQuery.WellboreListInfo;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;

public class WellLogsDataMappingQuery {

	public ArrayList<Data_Mapping_Info> retrieveFileNameLoginID(Long logID) throws SQLException {

		Connection connection = new DatabricksConnection().connect();
		Statement stmt;
		ArrayList<Data_Mapping_Info> finalList = new ArrayList<>();

		try {
			stmt = connection.createStatement();
			String query = "select* from well_logs_db.well_logs_header where log_id = "+logID+" ";
			ResultSet queryResult = stmt.executeQuery(query);

			ResultSetMetaData rsmd = queryResult.getMetaData();
			while (queryResult.next()) {
				Data_Mapping_Info data = new Data_Mapping_Info();

				int numColumns = rsmd.getColumnCount();
				for (int i = 1; i <= numColumns; i++) {
					String column_name = rsmd.getColumnName(i);
					Object object = queryResult.getObject(column_name);
					if (object != null) {
						if (column_name.equalsIgnoreCase("mnemonic")) {
							data.setLogs(queryResult.getObject(column_name).toString());

						}

						if (column_name.equalsIgnoreCase("filename")) {
							data.setFilename(queryResult.getObject(column_name).toString());
						}

						if (column_name.equalsIgnoreCase("basin_name")) {
							data.setBasin(queryResult.getObject(column_name).toString());
						}
						if (column_name.equalsIgnoreCase("well_name")) {
							data.setWell(queryResult.getObject(column_name).toString());
						}

						if (column_name.equalsIgnoreCase("mapped")) {
							data.setMapped(queryResult.getObject(column_name).toString());
						}

						if (column_name.equalsIgnoreCase("wellbore_name")) {
							data.setWellbore(queryResult.getObject(column_name).toString());
						}
						data.setExtension("las");

						if (column_name.equalsIgnoreCase("log_id")) {

							data.setID((long) queryResult.getObject(column_name));
						}

					}
				}
				finalList.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return finalList;

	}

	public ArrayList distnictFileName() {
		Connection connection = new DatabricksConnection().connect();

		ArrayList allFile = new ArrayList();
		Statement stmt;

		try {
			stmt = connection.createStatement();
			String query = "select DISTINCT filename from well_logs_db.well_logs_header ";
			ResultSet queryResult = stmt.executeQuery(query);

			ResultSetMetaData rsmd = queryResult.getMetaData();
			while (queryResult.next()) {

				int numColumns = rsmd.getColumnCount();
				for (int i = 1; i <= numColumns; i++) {
					String column_name = rsmd.getColumnName(i);
					Object object = queryResult.getObject(column_name);
					if (object != null) {
						if (column_name.equalsIgnoreCase("filename")) {
							allFile.add(queryResult.getObject(column_name).toString());

						}
					}
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return allFile;

	}
	
	
	public void updateUnitAndUnitCategory(String wellboreID,Long logID,String unit,String unitCategory) throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		
		
		String update_query = "update well_logs_db.well_logs_header set unit ='" +unit+"',unit_category = '"+unitCategory+"'"
				+ " where log_id = " + logID + " and wellbore_id = '"+wellboreID+"'";

		stmt.execute(update_query);
		System.out.println("unit update Executed Successfully ...");
		
	}

	public void updateWelllogsHeader(String wellboreID, String wellboreName, String wellID, String wellName,
			String basinID, String basinName, String mapped, String filename) throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		String update_query = "update well_logs_db.well_logs_header set wellbore_name ='" + wellboreName
				+ "',wellbore_id = '" + wellboreID + "', basin_id  ='" + basinID + "' , well_id = '" + wellID
				+ "' , well_name = '" + wellName + "',  basin_name = '" + basinName + "',mapped = '" + mapped + "'"
				+ " where filename = '" + filename + "' ";

		stmt.execute(update_query);
		System.out.println("Well update Executed Successfully ...");
	}

	public ArrayList<Data_Mapping_Info> allmnemonic() {
		Connection connection = new DatabricksConnection().connect();

		ArrayList<String> allFile = distnictFileName();

		// Data_Mapping_Info data = new Data_Mapping_Info();
		ArrayList<Data_Mapping_Info> alldata = new ArrayList();
		Statement stmt;
		for (String filename : allFile) {

			try {
				stmt = connection.createStatement();

				String query = "select DISTINCT mnemonic,basin_name,well_name,mapped,wellbore_name,log_file_id from well_logs_db.well_logs_header where fileName = '"
						+ filename + "'";

				ResultSet queryResult = stmt.executeQuery(query);

				ResultSetMetaData rsmd = queryResult.getMetaData();
				Data_Mapping_Info data = new Data_Mapping_Info();
				data.setFilename(filename);
				data.setExtension("las");

				while (queryResult.next()) {

					StringBuilder sb = new StringBuilder();
					int numColumns = rsmd.getColumnCount();
					for (int i = 1; i <= numColumns; i++) {
						String column_name = rsmd.getColumnName(i);
						Object object = queryResult.getObject(column_name);
						if (object != null) {
							if (column_name.equalsIgnoreCase("mnemonic")) {

								sb.append(queryResult.getObject(column_name).toString());
								data.setLogs(sb.toString());

							}

							if (column_name.equalsIgnoreCase("basin_name")) {
								data.setBasin(queryResult.getObject(column_name).toString());
							}
							if (column_name.equalsIgnoreCase("well_name")) {
								data.setWell(queryResult.getObject(column_name).toString());
							}

							if (column_name.equalsIgnoreCase("mapped")) {
								data.setMapped(queryResult.getObject(column_name).toString());
							}

							if (column_name.equalsIgnoreCase("wellbore_name")) {
								data.setWellbore(queryResult.getObject(column_name).toString());
							}

						}
					}

				}

				data.getLogs().toString();

				alldata.add(data);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		return alldata;

	}

	public ListDataProvider<Quality_Check_Info> retrieveDataForQc(String wellboreID) {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt;
		ArrayList<Quality_Check_Info> finalList = new ArrayList<>();

		try {
			stmt = connection.createStatement();
			String query = "select* from well_logs_db.well_logs_header where wellbore_id = '" + wellboreID + "' ";
			ResultSet queryResult = stmt.executeQuery(query);

			ResultSetMetaData rsmd = queryResult.getMetaData();
			while (queryResult.next()) {
				Quality_Check_Info data = new Quality_Check_Info();

				int numColumns = rsmd.getColumnCount();
				for (int i = 1; i <= numColumns; i++) {
					String column_name = rsmd.getColumnName(i);
					Object object = queryResult.getObject(column_name);
					if (object != null) {
						if (column_name.equalsIgnoreCase("mnemonic")) {
							data.setMnemonic(queryResult.getObject(column_name).toString());

						}

						if (column_name.equalsIgnoreCase("unit")) {
							data.setUnit(queryResult.getObject(column_name).toString());
						}

						if (column_name.equalsIgnoreCase("unit_category")) {
							data.setUnitCategory(queryResult.getObject(column_name).toString());
						}

						if (column_name.equalsIgnoreCase("log_file_id")) {
							data.setFileID(queryResult.getObject(column_name).toString());
						}
						
						if (column_name.equalsIgnoreCase("log_id")) {

							data.setLog_id((long) queryResult.getObject(column_name));
						}

					}
				}
				finalList.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return (ListDataProvider<Quality_Check_Info>) DataProvider.ofCollection((Collection) finalList);

	}

	public ListDataProvider<Depth_Data_Info> populatingLogsDataIngrid(String depth,String logname, String logFileId) {
		//String logFile = "988431";

		Connection connection = new DatabricksConnection().connect();
		Statement stmt;
		ArrayList<Depth_Data_Info> finalList = new ArrayList<>();

		// if (logFile.equalsIgnoreCase(logFileId)) {

		try {
			stmt = connection.createStatement();
			String query = "select "+depth+"," + logname + " from well_logs_db.well_logs_data_" + logFileId + " ";
			ResultSet queryResult = stmt.executeQuery(query);

			ResultSetMetaData rsmd = queryResult.getMetaData();
			while (queryResult.next()) {
				Depth_Data_Info data = new Depth_Data_Info();
				// String logName = logname
				int numColumns = rsmd.getColumnCount();
				for (int i = 1; i <= numColumns; i++) {
					String column_name = rsmd.getColumnName(i);
					Object object = queryResult.getObject(column_name);
					if (object != null) {
						if (column_name.equalsIgnoreCase(depth)) {
							data.setDepthData(queryResult.getObject(column_name).toString());

						}

						if (column_name.equalsIgnoreCase(logname)) {
							data.setMnemonicData(queryResult.getObject(column_name).toString());
						}

					}
				}
				finalList.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// }
		return (ListDataProvider<Depth_Data_Info>) DataProvider.ofCollection((Collection) finalList);

	}

}

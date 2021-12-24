package com.petrabytes.views.wellRegistryQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrabytes.connections.DatabricksConnection;
import com.petrabytes.project.util.OpenProjectInfo;
import com.petrabytes.project.util.ProjectInfo;
import com.petrabytes.views.basin.BasinViewGridInfo;
import com.petrabytes.views.wells.WellListInfo;
import com.simba.spark.jdbc42.internal.apache.arrow.flatbuf.Int;
import com.vaadin.flow.data.provider.DataProvider;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.VaadinService;

public class WellRegistryQuries {

	public void insertDataInBasin(String basin_name, Long basin_id) throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		String valueStr = "'" + basin_name + "'";
		Long valueINT = basin_id;
		String description = "'discription'";
		String insert_query = "insert into well_registry_db.basin  values(" + valueINT + "," + valueStr + ","
				+ description + ")";

		stmt.execute(insert_query);
		System.out.println("Basin Insert Executed Successfully ...");
	}

	public void updateDataInBasin(Long basinID, String basinName) throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		// Int basinID = getSelectedBasinID();
		String update_query = "update well_registry_db.basin set basin_name='" + basinName + "'where basin_id  ="
				+ basinID + "";

		stmt.execute(update_query);
		System.out.println("Basin update Executed Successfully ...");
	}

	public void deleteDataInBasinTable(Long basinID, String basinName) throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		String delete_query = "DELETE from well_registry_db.basin WHERE basin_id = " + basinID + " and  basin_name = '"
				+ basinName + "'";
		try {
			stmt.execute(delete_query);
			System.out.println("Basin Delete  Executed Successfully ...");
		} catch (SQLException e) {

			System.out.println("Basin Delete Not Executed Successfully ...");
		}

		System.out.println("B Delete Executed Successfully ...");
	}

	public ListDataProvider<BasinViewGridInfo> convertToListBasin() throws SQLException {
		Connection connection = new DatabricksConnection().connect();

		List<BasinViewGridInfo> finalList = new ArrayList<>();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			String query = "select basin_id, basin_name from well_registry_db.basin ";
			ResultSet queryResult = stmt.executeQuery(query);

			ResultSetMetaData rsmd = queryResult.getMetaData();
			while (queryResult.next()) {
				BasinViewGridInfo data = new BasinViewGridInfo();

				int numColumns = rsmd.getColumnCount();
				for (int i = 1; i <= numColumns; i++) {
					String column_name = rsmd.getColumnName(i);
					Object object = queryResult.getObject(column_name);
					if (object != null) {
						if (column_name.equalsIgnoreCase("basin_id")) {
							data.setBasinID((Long) queryResult.getObject(column_name));
						}
						if (column_name.equalsIgnoreCase("basin_name")) {
							data.setBasinName(queryResult.getObject(column_name).toString());
						}

					}
				}
				finalList.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return (ListDataProvider<BasinViewGridInfo>) DataProvider.ofCollection((Collection) finalList);
	}


	public void insertDataInWell( Long wellID,String wellName,String location,String apinumber,Double airgap,
			String airGapUnit,String company,Double waterDepth,String waterDepthUnit,Long basinID,String basinName,
			String country,Double elevation,String elevationUnit,String countryArea,Double waterDensity,String densityUnit,
			String stateProvinence,String timezone,Double latitude,Double longitude) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

	
		String description = "'discription'";
		String insert_query = "insert into well_registry_db.wells  values("+wellID+",'"+wellName+"','"+location+"','"+apinumber+"',"
				+ ""+airgap+",'"+airGapUnit+"','"+company+"',"+waterDepth+",'"+waterDepthUnit+"',"+basinID+",'"+basinName+"',"
						+ "'"+country+"',"+elevation+",'"+elevationUnit+"','"+countryArea+"',"+waterDensity+","
								+ "'"+densityUnit+"','"+stateProvinence+"','"+timezone+"',"+latitude+","+longitude+","+description+")";

		stmt.execute(insert_query);
		System.out.println("Basin Insert Executed Successfully ...");
		
		
	}
	
	public void updateDataInWell( Long wellID,String wellName,String location,String apinumber,Double airgap,
			String airGapUnit,String company,Double waterDepth,String waterDepthUnit,
			String country,Double elevation,String elevationUnit,String countryArea,Double waterDensity,String densityUnit,
			String stateProvinence,String timezone,Double latitude,Double longitude,Long basinID) throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		// Int basinID = getSelectedBasinID();
		String update_query = "update well_registry_db.wells set well_name = '" + wellName + "',location = '"+location+"',api_number = '"+apinumber+"',"
				+ "air_gap = "+airgap+",air_gap_unit = '"+airGapUnit+"',company = '"+company+"',water_depth = "+waterDepth+","
						+ "water_depth_unit = '"+waterDepthUnit+"',country = '"+country+"',gl_elevation = "+elevation+","
								+ "gl_elevation_unit = '"+elevationUnit+"',country_area = '"+countryArea+"',water_density = "+waterDensity+","
										+ "water_density_unit = '"+densityUnit+"',state_provinence = '"+stateProvinence+"',time_zone = '"+timezone+"',"
											+"	latitude = "+latitude+",longitude = "+longitude+" where  well_id = "+wellID+"  ";

		stmt.execute(update_query);
		System.out.println("Well update Executed Successfully ...");
	}
	
	public void updateBasinInWellView(Long basinID,String basinName) throws SQLException {
		
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		String update_query = "update well_registry_db.wells set basin_name = '" +basinName+ "' where basin_id = "+basinID+"";
		stmt.execute(update_query);
	}
	
	public void deleteDataInWellTable(Long basinID, String basinName,Long wellID,String wellName) throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		String delete_query = "DELETE from well_registry_db.wells WHERE well_id = " + wellID + " and  well_name = '"
				+ wellName + "' and basin_id = "+basinID+" and basin_name = '"+basinName+"'";
		try {
			stmt.execute(delete_query);
			System.out.println("well Delete  Executed Successfully ...");
		} catch (SQLException e) {

			System.out.println("well Delete Not Executed Successfully ...");
		}

		System.out.println("B Delete Executed Successfully ...");
	}
	
	public void deleteWellForDeletedBasin(Long basinID,String basinName) throws SQLException {
		
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		String delete_query = "DELETE from well_registry_db.wells WHERE basin_id = "+basinID+" and basin_name = '"+basinName+"'";
		try {
			stmt.execute(delete_query);
			System.out.println("well Delete  Executed Successfully ...");
		} catch (SQLException e) {

			System.out.println("well Delete Not Executed Successfully ...");
		}

		System.out.println("B Delete Executed Successfully ...");
	}
	
	
public void deleteWellboreForDeletedWell(Long wellID,String wellName) throws SQLException {
		
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		String delete_query = "DELETE from well_registry_db.wellbore WHERE well_id = "+wellID+" and well_name = '"+wellName+"'";
		try {
			stmt.execute(delete_query);
			System.out.println("well Delete  Executed Successfully ...");
		} catch (SQLException e) {

			System.out.println("well Delete Not Executed Successfully ...");
		}

		System.out.println("B Delete Executed Successfully ...");
	}
	public ListDataProvider<WellListInfo> convertToListWell(Long basinid) throws SQLException {
		Connection connection = new DatabricksConnection().connect();

		List<WellListInfo> finalList = new ArrayList<>();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			String query = "select* from well_registry_db.wells where basin_id="+basinid;
			ResultSet queryResult = stmt.executeQuery(query);

			ResultSetMetaData rsmd = queryResult.getMetaData();
			while (queryResult.next()) {
				WellListInfo data = new WellListInfo();

				int numColumns = rsmd.getColumnCount();
				for (int i = 1; i <= numColumns; i++) {
					String column_name = rsmd.getColumnName(i);
					Object object = queryResult.getObject(column_name);
					if (object != null) {
						if (column_name.equalsIgnoreCase("well_id")) {
							data.setWellID((Long) queryResult.getObject(column_name));
						}
						if (column_name.equalsIgnoreCase("well_name")) {
							data.setWellName(queryResult.getObject(column_name).toString());
						}
						if (column_name.equalsIgnoreCase("location")) {
							data.setWellType(queryResult.getObject(column_name).toString());
						}
						if (column_name.equalsIgnoreCase("api_number")) {
							data.setApi(queryResult.getObject(column_name).toString());
						}
						
						if (column_name.equalsIgnoreCase("air_gap")) {
							data.setAirGap((Double) queryResult.getObject(column_name));
						}
						if (column_name.equalsIgnoreCase("api_gap_unit")) {
							data.setAirGapUnit( queryResult.getObject(column_name).toString());
						}
						
						if (column_name.equalsIgnoreCase("company")) {
							data.setCompanyName( queryResult.getObject(column_name).toString());
						}
						if (column_name.equalsIgnoreCase("water_depth")) {
							data.setWaterDepth((Double) queryResult.getObject(column_name));
						}
						
						if (column_name.equalsIgnoreCase("water_depth_unit")) {
							data.setWaterdepthunit( queryResult.getObject(column_name).toString());
						}
						
						if (column_name.equalsIgnoreCase("basin_id")) {
							data.setBasinID((Long) queryResult.getObject(column_name));
						}
						
						if (column_name.equalsIgnoreCase("basin_name")) {
							data.setBasin(queryResult.getObject(column_name).toString());
						}
						
						if (column_name.equalsIgnoreCase("country")) {
							data.setCountry(queryResult.getObject(column_name).toString());
						}
						if (column_name.equalsIgnoreCase("gl_elevation")) {
							data.setGlElevation((Double) queryResult.getObject(column_name));
						}
						
						if (column_name.equalsIgnoreCase("gl_elevation_unit")) {
							data.setGlelEvationUnit(queryResult.getObject(column_name).toString());
						}
						
						if (column_name.equalsIgnoreCase("country_area")) {
							data.setCountryarea(queryResult.getObject(column_name).toString());
						}
						
						if (column_name.equalsIgnoreCase("water_density")) {
							data.setWaterDensity((Double) queryResult.getObject(column_name));
						}
						
						if (column_name.equalsIgnoreCase("water_density_unit")) {
							data.setWaterdensityunit(queryResult.getObject(column_name).toString());
						}
						
						if (column_name.equalsIgnoreCase("state_provinence")) {
							data.setState(queryResult.getObject(column_name).toString());
						}
						
						if (column_name.equalsIgnoreCase("time_zone")) {
							data.setTimezone(queryResult.getObject(column_name).toString());
						}
						
						if (column_name.equalsIgnoreCase("latitude")) {
							data.setLatitude((Double) queryResult.getObject(column_name));
						}
						
						if (column_name.equalsIgnoreCase("longitude")) {
							data.setLongtide((Double) queryResult.getObject(column_name));
						}

					}
				}
				finalList.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return (ListDataProvider<WellListInfo>) DataProvider.ofCollection((Collection) finalList);
	}
	
	public void insertDataInWellbore(Long basinID,String basinName,Long wellID, String wellName, Long wellboreID, String wellboreName) throws SQLException {
		// TODO Auto-generated method stub
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		String description = "'discription'";
		String insert_query = "insert into well_registry_db.wellbore  values("+basinID+",'"+basinName+"',"+wellID+",'"+wellName+"',"+wellboreID+",'"+wellboreName+"',"+ description + " )";

		stmt.execute(insert_query);
		System.out.println("Basin Insert Executed Successfully ...");
		
		
	}
	
	public void updateDataInWellbore(Long wellboreID,String wellboreName,Long wellID,String wellName,Long basinID, String basinName) throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		
		String update_query = "update well_registry_db.wellbore set wellbore_name='" + wellboreName + "'where wellbore_id = "+wellboreID+" and basin_id  ="
				+ basinID + " and well_id = "+wellID+" and well_name = '"+wellName+"' and basin_name = '"+basinName+"' ";

		stmt.execute(update_query);
		System.out.println("Well update Executed Successfully ...");
	}
	
	public void updateBasinInWellbore(Long basinID, String basinName) throws SQLException {
		
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		String update_query = "update well_registry_db.wellbore set basin_name = '" +basinName+ "' where basin_id = "+basinID+"";
		stmt.execute(update_query);
	}
	
	public void updateWellInWellboreView(Long wellID,String wellName) throws SQLException {
		
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		String update_query = "update well_registry_db.wellbore set well_name = '" +wellName+ "' where well_id = "+wellID+"";
		stmt.execute(update_query);
	}
	
	public void deleteDataInWellboreTable(Long basinID, String basinName,Long wellID,String wellName,Long wellboreID,String wellboreName) throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		String delete_query = "DELETE from well_registry_db.wellbore WHERE wellbore_id= "+wellboreID+"and wellbore_name = '"+wellboreName+"' and well_id = " + wellID + " and  well_name = '"
				+ wellName + "' and basin_id = "+basinID+" and basin_name = '"+basinName+"'";
		try {
			stmt.execute(delete_query);
			System.out.println("wellbore Delete  Executed Successfully ...");
		} catch (SQLException e) {

			System.out.println("wellbore Delete Not Executed Successfully ...");
		}

		System.out.println("B Delete Executed Successfully ...");
	}
	
public void deleteWellboreForDeletedBasin(Long basinID,String basinName) throws SQLException {
		
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		String delete_query = "DELETE from well_registry_db.wellbore WHERE basin_id = "+basinID+" and basin_name = '"+basinName+"'";
		try {
			stmt.execute(delete_query);
			System.out.println("well Delete  Executed Successfully ...");
		} catch (SQLException e) {

			System.out.println("well Delete Not Executed Successfully ...");
		}

		System.out.println("B Delete Executed Successfully ...");
	}
	
	
	public ListDataProvider<WellboreListInfo> convertToListWellbore(Long wellID,Long basinID) throws SQLException {
		Connection connection = new DatabricksConnection().connect();

		ArrayList<WellboreListInfo> finalList = new ArrayList<>();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			String query = "select* from well_registry_db.wellbore where well_id="+wellID+" and basin_id = "+basinID+"";
			
			ResultSet queryResult = stmt.executeQuery(query);

			ResultSetMetaData rsmd = queryResult.getMetaData();
			while (queryResult.next()) {
				WellboreListInfo data = new WellboreListInfo();

				int numColumns = rsmd.getColumnCount();
				for (int i = 1; i <= numColumns; i++) {
					String column_name = rsmd.getColumnName(i);
					Object object = queryResult.getObject(column_name);
					if (object != null) {
						if (column_name.equalsIgnoreCase("basin_id")) {
							data.setBasinID((Long) queryResult.getObject(column_name));
							
						}
						
						if (column_name.equalsIgnoreCase("basin_name")) {
							data.setBasinName(queryResult.getObject(column_name).toString());
						}
						
						if (column_name.equalsIgnoreCase("well_id")) {
							data.setWellID((Long) queryResult.getObject(column_name));
						}
						if (column_name.equalsIgnoreCase("well_name")) {
							data.setWellNmae(queryResult.getObject(column_name).toString());
						}
						
						if (column_name.equalsIgnoreCase("wellbore_id")) {
							data.setWellboreID((Long) queryResult.getObject(column_name));
						}
						
						if (column_name.equalsIgnoreCase("wellbore_name")) {
							data.setWellboreName(queryResult.getObject(column_name).toString());
						}
						
					}
				}
				finalList.add(data);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return  (ListDataProvider<WellboreListInfo>) DataProvider.ofCollection((Collection) finalList);
	}
	
	
	public int BasinExist_ByName(String basinName) {
		int isExist = 0;

		Connection connection = new DatabricksConnection().connect();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			String query = "select count(*) from well_registry_db.basin where basin_name='" + basinName + "'";
			ResultSet queryResult = stmt.executeQuery(query);

			while (queryResult.next())
				isExist = queryResult.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isExist;
	}
	
	public int wellExist_ByName(String wellName,String basinName) {
		int isExist = 0;

		Connection connection = new DatabricksConnection().connect();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			String query = "select count(*) from well_registry_db.wells where well_name='" + wellName + "' and basin_name = '"+basinName+"'";
			ResultSet queryResult = stmt.executeQuery(query);

			while (queryResult.next())
				isExist = queryResult.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isExist;
	}

	public int WellboreExist_ByName(String wellboreName,String wellName,String basin) {
		int isExist = 0;

		Connection connection = new DatabricksConnection().connect();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			String query = "select count(*) from well_registry_db.wellbore where wellbore_name='" + wellboreName + "' and well_name ='"+wellName+"'and basin_name = '"+basin+"'";
			ResultSet queryResult = stmt.executeQuery(query);

			while (queryResult.next())
				isExist = queryResult.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isExist;
	}

	
	

}

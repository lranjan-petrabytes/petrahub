package com.petrabytes.views.wellRegistryQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrabytes.connections.DatabricksConnection;
import com.petrabytes.project.util.ProjectInfo;
import com.petrabytes.views.wellbore.CasingPojoClass;
import com.petrabytes.views.wellbore.CasingReferenceInfo;
import com.petrabytes.views.wellbore.CompletionPojoClass;
import com.petrabytes.views.wellbore.Deviation_PojoClass;
import com.petrabytes.views.wellbore.FormationPojoClass;
import com.petrabytes.views.wellbore.PerforationPojoClass;
import com.petrabytes.views.wellbore.TubingPojaClass;
import com.petrabytes.views.wells.WellListInfo;

public class WellboreQueries {
	
	public void insertDeviationData(Long wellboreid, JSONObject deviationdata) throws SQLException{
		
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		String deviationdatainfo = deviationdata.toString();
		String insert_query = "insert into well_registry_db.deviation_survey values("+wellboreid+" ,'"+deviationdatainfo+"')";
		stmt.execute(insert_query);
        System.out.println("Deviation Data stored Successfully ...");

		
		
	}
	
	
	public int wellboreExist_ByIDDeviationView(Long wellboreId) {
		int isExist = 0;

		Connection connection = new DatabricksConnection().connect();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			String query = "select count(*) from well_registry_db.deviation_survey where wellbore_id=" + wellboreId + "";
			ResultSet queryResult = stmt.executeQuery(query);

			while (queryResult.next())
				isExist = queryResult.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isExist;
	}
	public int wellboreExistByID(String query) {
		int isExist = 0;

		Connection connection = new DatabricksConnection().connect();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			ResultSet queryResult = stmt.executeQuery(query);
			while (queryResult.next())
				isExist = queryResult.getInt(1);		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isExist;
	
	}
	public void updateDataInCasingTubingView(Long wellboreID,JSONObject casingobject , JSONObject tubingobject ) throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		String casingdatainfo = casingobject.toString();
		String tubingdatainfo = tubingobject.toString();
		// Int basinID = getSelectedBasinID();
		String update_query = "update well_registry_db.casing_tubing set casing_data='"+casingdatainfo+"set tubing_data="+tubingdatainfo+"'where wellbore_id="+ wellboreID + "";

		stmt.execute(update_query);
		System.out.println("data update Executed Successfully ...");
	}
	
	public void updateDataInDeviationView(Long wellboreID,JSONObject deviationdata ) throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		String deviationdatainfo = deviationdata.toString();
		// Int basinID = getSelectedBasinID();
		String update_query = "update well_registry_db.deviation_survey set deviation_data='" +deviationdatainfo  + "'where wellbore_id  ="
				+ wellboreID + "";

		stmt.execute(update_query);
		System.out.println("data update Executed Successfully ...");
	}
	
	public void insertCasingTubingData(Long wellboreid, JSONObject casingobject,JSONObject tubingobject) throws SQLException{
		
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		String casingdatainfo = casingobject.toString();
		String tubingdatainfo = tubingobject.toString();
		String insert_query = "insert into well_registry_db.casing_tubing values("+wellboreid+" ,'"+casingdatainfo+"','"+tubingdatainfo+"')";
		stmt.execute(insert_query);
        System.out.println("casing and tubing Data stored Successfully ...");

		
		
	}
	
	
	public static List<CasingReferenceInfo> getAllCasingReference(String parameter) throws SQLException {
		Connection connection = new DatabricksConnection().connect();

		List<CasingReferenceInfo> finalList = new ArrayList<>();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			String query = "select * from well_registry_db.ph_casing_reference_"+parameter;
			ResultSet queryResult = stmt.executeQuery(query);

			ResultSetMetaData rsmd = queryResult.getMetaData();
			while (queryResult.next()) {
				CasingReferenceInfo data = new CasingReferenceInfo();

				int numColumns = rsmd.getColumnCount();
				for (int i = 1; i <= numColumns; i++) {
					String column_name = rsmd.getColumnName(i);
					Object object = queryResult.getObject(column_name);
					if (object != null) {
						if (column_name.equalsIgnoreCase("casing_id")) {
							data.setCasing_id((String) queryResult.getObject(column_name));
						}
						if (column_name.equalsIgnoreCase("casing_od")) {
							data.setCasing_od((String) queryResult.getObject(column_name));
						}
						if (column_name.equalsIgnoreCase("drift")) {
							data.setDrift(queryResult.getObject(column_name).toString());
						}
						if (column_name.equalsIgnoreCase("max_od_liner")) {
							data.setMax_od_liner(queryResult.getObject(column_name).toString());
						}
						
						if (column_name.equalsIgnoreCase("wall")) {
							data.setWall((String) queryResult.getObject(column_name));
						}
						if (column_name.equalsIgnoreCase("weight")) {
							data.setWeight( queryResult.getObject(column_name).toString());
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
	public List getCasingOdList(String parameter) throws SQLException {
		String query = "select casing_od from well_registry_db.ph_casing_reference_"+parameter;
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
	public List getWeightsList(String parameter) throws SQLException {
		String query = "select weight from well_registry_db.ph_casing_reference_"+parameter;
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
	
	public CasingPojoClass getCasingData(Long wellboreid) throws SQLException {
		String query ="select casing_data from well_registry_db.casing_tubing where wellbore_id="+ wellboreid + "";
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		ResultSet queryResult = stmt.executeQuery(query);
		System.out.println(queryResult);

		CasingPojoClass casingData = null;

		ResultSetMetaData rsmd = queryResult.getMetaData();
		while (queryResult.next()) {
			int numColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				String column_name = rsmd.getColumnName(i);
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					casingData = objectMapper.readValue((String)queryResult.getObject(column_name), CasingPojoClass.class);
				} catch (JsonProcessingException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		connection.close();
		
		
		return casingData;
		
	}
	public Deviation_PojoClass getDeviationData(Long wellboreid) throws SQLException {
		String query ="select deviation_data from well_registry_db.deviation_survey where wellbore_id="+ wellboreid + "";
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		ResultSet queryResult = stmt.executeQuery(query);
		System.out.println(queryResult);

		Deviation_PojoClass deviationData = null;

		ResultSetMetaData rsmd = queryResult.getMetaData();
		while (queryResult.next()) {
			int numColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				String column_name = rsmd.getColumnName(i);
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					deviationData = objectMapper.readValue((String)queryResult.getObject(column_name), Deviation_PojoClass.class);
				} catch (JsonProcessingException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		connection.close();
		
		
		return deviationData;
		
	}
	public TubingPojaClass getTubingData(Long wellboreid) throws SQLException {
		String query ="select tubing_data from well_registry_db.casing_tubing where wellbore_id="+ wellboreid + "";
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		ResultSet queryResult = stmt.executeQuery(query);
		System.out.println(queryResult);

		TubingPojaClass tubingData = null;

		ResultSetMetaData rsmd = queryResult.getMetaData();
		while (queryResult.next()) {
			int numColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				String column_name = rsmd.getColumnName(i);
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					tubingData = objectMapper.readValue((String)queryResult.getObject(column_name), TubingPojaClass.class);
				} catch (JsonProcessingException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		connection.close();
		
		
		return tubingData;
		
	}
	public void insertPerforationData(Long wellboreid, JSONObject perforationobject) throws SQLException{
		
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		String perforationdatainfo = perforationobject.toString();
		String insert_query = "insert into well_registry_db.perforation values("+wellboreid+" ,'"+perforationdatainfo+"')";
		stmt.execute(insert_query);
        System.out.println("perforation Data stored Successfully ...");

		
		
	}
	public PerforationPojoClass getPerforationData(Long wellboreid) throws SQLException {
		String query ="select perforation_data from well_registry_db.perforation where wellbore_id="+ wellboreid + "";
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		ResultSet queryResult = stmt.executeQuery(query);
		System.out.println(queryResult);

		PerforationPojoClass perforationdata = null;

		ResultSetMetaData rsmd = queryResult.getMetaData();
		while (queryResult.next()) {
			int numColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				String column_name = rsmd.getColumnName(i);
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					perforationdata = objectMapper.readValue((String)queryResult.getObject(column_name), PerforationPojoClass.class);
				} catch (JsonProcessingException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		connection.close();
		
		
		return perforationdata;
		
	}
	public void updateDataInPerforationView(Long wellboreID,JSONObject perforationobject ) throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		String perforationinfo = perforationobject.toString();
		// Int basinID = getSelectedBasinID();
		String update_query = "update well_registry_db.perforation set perforation_data='" +perforationinfo + "'where wellbore_id  ="
				+ wellboreID + "";

		stmt.execute(update_query);
		System.out.println("data update Executed Successfully ...");
	}
	
	public void insertCompletionData(Long wellboreid, JSONObject completiondata) throws SQLException{
		
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		String completiondatainfo = completiondata.toString();
		String insert_query = "insert into well_registry_db.well_completion values("+wellboreid+" ,'"+completiondatainfo+"')";
		stmt.execute(insert_query);
        System.out.println("completions Data stored Successfully ...");

		
		
	}
	public CompletionPojoClass getCompletionData(Long wellboreid) throws SQLException {
		String query ="select well_completion_data  from well_registry_db.well_completion where wellbore_id="+ wellboreid + "";
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		ResultSet queryResult = stmt.executeQuery(query);
		System.out.println(queryResult);

		CompletionPojoClass completiondata = null;

		ResultSetMetaData rsmd = queryResult.getMetaData();
		while (queryResult.next()) {
			int numColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				String column_name = rsmd.getColumnName(i);
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					completiondata = objectMapper.readValue((String)queryResult.getObject(column_name), CompletionPojoClass.class);
				} catch (JsonProcessingException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		connection.close();
		
		
		return completiondata;
		
	}
	public void updateDataInCompletionView(Long wellboreID,JSONObject completionobject ) throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		String completioninfo = completionobject.toString();
		// Int basinID = getSelectedBasinID();
		String update_query = "update well_registry_db.well_completion set well_completion_data='" +completioninfo + "'where wellbore_id  ="
				+ wellboreID + "";

		stmt.execute(update_query);
		System.out.println("completion data update Executed Successfully ...");
	}
	
	public void insertFormationData(Long wellboreid, JSONObject formationdata) throws SQLException{
		
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		String formationdatainfo = formationdata.toString();
		String insert_query = "insert into well_registry_db.formation values("+wellboreid+" ,'"+formationdata+"')";
		stmt.execute(insert_query);
        System.out.println("formation Data stored Successfully ...");

		
		
	}
	public FormationPojoClass getFormationData(Long wellboreid) throws SQLException {
		String query ="select formation_data from well_registry_db.formation where wellbore_id="+ wellboreid + "";
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		ResultSet queryResult = stmt.executeQuery(query);
		System.out.println(queryResult);

		FormationPojoClass formationdata = null;

		ResultSetMetaData rsmd = queryResult.getMetaData();
		while (queryResult.next()) {
			int numColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				String column_name = rsmd.getColumnName(i);
				ObjectMapper objectMapper = new ObjectMapper();
				try {
					formationdata = objectMapper.readValue((String)queryResult.getObject(column_name), FormationPojoClass.class);
				} catch (JsonProcessingException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		connection.close();
		
		
		return formationdata;
		
	}
	public void updateDataInFormationView(Long wellboreID,JSONObject formationdata ) throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		String formationinfo = formationdata.toString();
		// Int basinID = getSelectedBasinID();
		String update_query = "update well_registry_db.formation set formation_data='" +formationinfo + "'where wellbore_id  ="
				+ wellboreID + "";

		stmt.execute(update_query);
		System.out.println("formation data update Executed Successfully ...");
	}
	
	
	
	



}

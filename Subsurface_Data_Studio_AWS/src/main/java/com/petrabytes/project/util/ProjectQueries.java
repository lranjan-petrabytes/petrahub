package com.petrabytes.project.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.petrabytes.connections.DatabricksConnection;
import com.vaadin.flow.server.VaadinService;

public class ProjectQueries {

	public void insertDataInDeltalake(String projectName, String projectId, JSONObject projectSettingsJSON) throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		
        String projectSettings = projectSettingsJSON.toString();
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd_HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentTime = dtf.format(now);
        
        HashMap<String, String>  userDataObj = (HashMap<String, String>)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userdata");
        String userName = userDataObj.get("userdata");
        String userId = userDataObj.get("userdata");
        String projectType = "SDS App" ;
        String projectStatus = "created";
        
        String valueStr = "'"+userId+"'"+","+"'"+userName+"'"+","+"'"+projectId+"'"+","+"'"+projectName+"'"+","+"'"+currentTime+"'"+","+"'"+projectType+"'"+","+"'"+projectStatus+"'"+","+"'"+projectSettings+"'";

        String insert_query = "insert into projects_db.project_settings values("+valueStr+")";

        stmt.execute(insert_query);
        System.out.println("Project Insert Executed Successfully ...");
	}
	
	public void updateDataInDeltalake(String projectName, String projectId, JSONObject projectSettingsJSON) throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		
        String projectSettings = projectSettingsJSON.toString();
        
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd_HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String currentTime = dtf.format(now);
        
        HashMap<String, String>  userDataObj = (HashMap<String, String>)VaadinService.getCurrentRequest().getWrappedSession().getAttribute("userdata");
        String userName = userDataObj.get("userdata");
        String userId = userDataObj.get("userdata");
        String projectType = "Petrahub" ;
        String projectStatus = "created";

        String update_query = "update projects_db.project_settings set project_settings='" + projectSettings + "', last_modified_date='" + currentTime + "' where user_id='" + userId + "' and project_id='" + projectId + "'";
        String valueStr = "'"+userId+"'"+","+"'"+userName+"'"+","+"'"+projectId+"'"+","+"'"+projectName+"'"+","+"'"+currentTime+"'"+","+"'"+projectType+"'"+","+"'"+projectStatus+"'"+","+"'"+projectSettings+"'";
        String insert_query = "insert into projects_db.project_settings values("+valueStr+")";
        
        List<String> projectIDs = getAllProjectIdFromUserId(userId);
        if (projectIDs.contains(projectId)) {
        	stmt.execute(update_query);
        } else {
        	stmt.execute(insert_query);
        }
        	
        System.out.println("Project Insert Executed Successfully ...");
	}
	
	
	public void deleteDataInDeltalake(String projectId) throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		String delete_query = "DELETE from projects_db.project_settings WHERE project_id = '" +projectId+ "' ";
		try {
			stmt.execute(delete_query);
		} catch (SQLException e) {

			System.out.println("Project Delete Not Executed Successfully ...");
		}

		System.out.println("Project Delete Executed Successfully ...");
	}
	
	public OpenProjectInfo getProjectBYID(String projectID,String username) throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();

		String query = "select * from projects_db.project_settings where project_id='" + projectID + "' and user_name = '"+username+"'";

		ResultSet queryResult = stmt.executeQuery(query);
		List<OpenProjectInfo> resultList = convertToList(queryResult);
		if (resultList.size() == 1) {
			OpenProjectInfo resultdata = resultList.get(0);
			return resultdata;
		}
		return null;
	}
	
	public List<OpenProjectInfo> fetchProjectsFromDeltalake() throws SQLException {
		Connection connection = new DatabricksConnection().connect();
		Statement stmt = connection.createStatement();
		
		String query = "select * from projects_db.project_settings where project_type = 'Petrahub' ";
		
		ResultSet queryResult = stmt.executeQuery(query);
		List<OpenProjectInfo> resultList = convertToList(queryResult);
		return resultList;
	}
	
	public List<String> getAllProjectIdFromUserId(String UserId) {
		Connection connection = new DatabricksConnection().connect();
		List<String> projectIDs = new ArrayList<>();

		Statement stmt;
		try {
			stmt = connection.createStatement();
			String query = "select project_id from projects_db.project_settings where user_id='" + UserId + "'";
			ResultSet queryResult = stmt.executeQuery(query);

			ResultSetMetaData rsmd = queryResult.getMetaData();
			while (queryResult.next()) {
				String column_name = rsmd.getColumnName(1);
				projectIDs.add(queryResult.getObject(column_name).toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return projectIDs;
	}
	
	private List<OpenProjectInfo> convertToList(ResultSet queryResult) throws SQLException {

		List<OpenProjectInfo> finalList = new ArrayList<>();

		ResultSetMetaData rsmd = queryResult.getMetaData();
		while (queryResult.next()) {
			OpenProjectInfo data = new OpenProjectInfo();

			int numColumns = rsmd.getColumnCount();
			for (int i = 1; i <= numColumns; i++) {
				String column_name = rsmd.getColumnName(i);
				Object object = queryResult.getObject(column_name);
				if (object != null) {
					if (column_name.equalsIgnoreCase("user_id")) {
						data.setUserID(queryResult.getObject(column_name).toString());
					}
					if (column_name.equalsIgnoreCase("user_name")) {
						data.setUserName(queryResult.getObject(column_name).toString());
					}
					if (column_name.equalsIgnoreCase("project_id")) {
						data.setProjectID(queryResult.getObject(column_name).toString());
					}
					if (column_name.equalsIgnoreCase("project_name")) {
						data.setProjectName(queryResult.getObject(column_name).toString());
					}
					if (column_name.equalsIgnoreCase("last_modified_date")) {
						data.setLastModifiedDate(queryResult.getObject(column_name).toString());
					}
					if (column_name.equalsIgnoreCase("project_type")) {
						data.setProjectType(queryResult.getObject(column_name).toString());
					}
					if (column_name.equalsIgnoreCase("project_status")) {
						data.setProjectStatus(queryResult.getObject(column_name).toString());
					}
					if (column_name.equalsIgnoreCase("project_settings")) {
						ObjectMapper objectMapper = new ObjectMapper();
						ProjectInfo projectInfoObj = null;
						try {
							projectInfoObj = objectMapper.readValue((String)queryResult.getObject(column_name), ProjectInfo.class);
						} catch (JsonProcessingException | SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						data.setProjectSettings(projectInfoObj);
					}
				}
			}
			finalList.add(data);
		}

		return finalList;
	}
	
	public int projectExist_ByName(String projectName) {
		int isExist = 0;

		Connection connection = new DatabricksConnection().connect();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			String query = "select count(*) from projects_db.project_settings where project_name='" + projectName + "'";
			ResultSet queryResult = stmt.executeQuery(query);

			while (queryResult.next())
				isExist = queryResult.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return isExist;
	}
	
	public int projectExist_ByID(String projectId) {
		int isExist = 0;

		Connection connection = new DatabricksConnection().connect();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			String query = "select count(*) from projects_db.project_settings where project_id='" + projectId + "'";
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

package com.petrabytes.views.governance;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.petrabytes.connections.DatabricksConnection;

public class GovernanceQuery {
	
	/*
	 * Get all users
	 */
	public static ArrayList<User> getUsers() {
		String query = "show users";
	
		Connection connection = new DatabricksConnection().connect();
		ArrayList<User> users = new ArrayList<>();
		int name = 1;
		
		try {
			Statement stmt = connection.createStatement();

			ResultSet queryResult = stmt.executeQuery(query);
			//System.out.println(queryResult);
			while(queryResult.next())
			{
				System.out.println((String)queryResult.getObject(name));
				users.add((new User((String)queryResult.getObject(name))));
				//queryResult.next();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return users;
	}

	
	/*
	 * Get all groups
	 */
	public static ArrayList<Group> getGroups() {
		String query = "show groups";
	
		Connection connection = new DatabricksConnection().connect();
		ArrayList<Group> groups = new ArrayList<>();
		int name = 1;
		int directGroup = 2;
		
		try {
			Statement stmt = connection.createStatement();

			ResultSet queryResult = stmt.executeQuery(query);
			
			while(queryResult.next())
			{
				Group g = new Group();
				//System.out.println((String) queryResult.getObject(name));
				//System.out.println((String) queryResult.getObject(directGroup));
				
				g.setName((String) queryResult.getObject(name));
				g.setDirectGroup((String) queryResult.getObject(directGroup));
				
				groups.add(g);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return groups;
	}

	
	/*
	 * Get the groups of the user
	 */
	public static ArrayList<Group> getGroups(String username) {
		String query = "show groups with user `"+ username + "`";
	
		Connection connection = new DatabricksConnection().connect();
		ArrayList<Group> groups = new ArrayList<>();
		int name = 1;
		int directGroup = 2;
		
		try {
			Statement stmt = connection.createStatement();

			ResultSet queryResult = stmt.executeQuery(query);
			
			while(queryResult.next())
			{
				Group g = new Group();
				//System.out.println((String) queryResult.getObject(name));
				//System.out.println((String) queryResult.getObject(directGroup));
				
				g.setName((String) queryResult.getObject(name));
				
				if ((Boolean) queryResult.getObject(directGroup)) {
					g.setDirectGroup("True");
				}
				else
				{
					g.setDirectGroup("False");
				}
				//g.setDirectGroup((String) queryResult.getObject(directGroup));
				
				groups.add(g);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return groups;
	}
	
	/*
	 * Get all databases
	 */
	public static ArrayList<Database> getDatabases() {
		String query = "show databases";
	
		Connection connection = new DatabricksConnection().connect();
		ArrayList<Database> db = new ArrayList<>();
		int name = 1;
		
		try {
			Statement stmt = connection.createStatement();

			ResultSet queryResult = stmt.executeQuery(query);
			
			while(queryResult.next())
			{
				Database d = new Database();
				//System.out.println((String) queryResult.getObject(name));
				
				d.setName((String) queryResult.getObject(name));
				
				db.add(d);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return db;
	}

	
	/*
	 * Get the tables of the database
	 */
	public static ArrayList<Table> getTables(String dbName) {
		String query = "show tables from "+ dbName;
	
		Connection connection = new DatabricksConnection().connect();
		ArrayList<Table> tables = new ArrayList<>();
		int db = 1;
		int name = 2;
		int temp = 3;
		
		try {
			Statement stmt = connection.createStatement();

			ResultSet queryResult = stmt.executeQuery(query);
			
			while(queryResult.next())
			{
				Table t = new Table();
				//System.out.println((String) queryResult.getObject(name));
				//System.out.println((String) queryResult.getObject(directGroup));
				
				t.setDatabase((String) queryResult.getObject(db));
				t.setName((String) queryResult.getObject(name));
				
				if ((Boolean) queryResult.getObject(temp)) {
					t.setTemp("True");
				}
				else
				{
					t.setTemp("False");
				}
				//g.setDirectGroup((String) queryResult.getObject(directGroup));
				
				tables.add(t);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tables;
	}
	
	/*
	 * Get the permissions for a user on a table
	 */
	public static ArrayList<TablePermissions> getTablePerms(String username, String dbName, String tableName) {
		String query = "show grant `" + username + "` on table " + dbName + "." + tableName;
	
		Connection connection = new DatabricksConnection().connect();
		ArrayList<TablePermissions> tablePerms = new ArrayList<>();
		
		int principal = 1;
		int action = 2;
		int type = 3;
		int key = 4;
		
		try {
			Statement stmt = connection.createStatement();

			ResultSet queryResult = stmt.executeQuery(query);
			
			while(queryResult.next())
			{
				TablePermissions tp = new TablePermissions();
				//System.out.println((String) queryResult.getObject(name));
				//System.out.println((String) queryResult.getObject(directGroup));
				
				tp.setPrincipal((String) queryResult.getObject(principal));
				tp.setAction((String) queryResult.getObject(action));
				tp.setType((String) queryResult.getObject(type));
				tp.setKey((String) queryResult.getObject(key));
				
				//g.setDirectGroup((String) queryResult.getObject(directGroup));
				
				tablePerms.add(tp);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tablePerms;
	}
	
}

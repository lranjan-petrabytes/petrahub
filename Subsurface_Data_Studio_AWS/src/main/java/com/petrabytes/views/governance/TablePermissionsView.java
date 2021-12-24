package com.petrabytes.views.governance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


import com.petrabytes.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

public class TablePermissionsView extends VerticalLayout{
	
	/*
	 * //private VerticalLayout mainLayout = new VerticalLayout(); private Tab
	 * userTab = new Tab(); private Tab groupTab = new Tab(); private Tabs tabs =
	 * new Tabs(groupTab, userTab);
	 * 
	 * //Groups Tab private VerticalLayout groupLayout = new VerticalLayout();
	 * private Button getGroupsButton = new Button(); private Grid<Group> groupGrid
	 * = new Grid<>(Group.class, false);
	 */
	
	//Users Tab
	private HorizontalLayout  permLayout = new HorizontalLayout();
	private VerticalLayout  permLeftLayout = new VerticalLayout();
	private VerticalLayout  permMiddleLayout = new VerticalLayout();
	private VerticalLayout  permRightLayout = new VerticalLayout();
	private Button getUsersButton = new Button();
	private Grid<User> userGrid = new Grid<>(User.class, false);
	private Button getTablesOfDBButton = new Button();
	private Grid<TablePermissions> permGrid = new Grid<>(TablePermissions.class, false);
	private Button getPermsOfUserButton = new Button();
	private Grid<Table> tablesGrid;
	private String dbName;
	private String tableName;
	private String username;

	
	public TablePermissionsView()
	{
		//setUI();
	}
	
	public TablePermissionsView(Grid<Table> t, String dbName, String tableName)
	{
		this.dbName = dbName;
		this.tableName = tableName;
		this.tablesGrid = t;
		
		getUsersButton.setText("Get Users");
		permLeftLayout.add(tablesGrid, getUsersButton);
		permLayout.add(permLeftLayout);
		this.add(permLayout);
		
		getUsersButtonAction();
		//setUI();
	}
	
	private void getUsersButtonAction()
	{
		getUsersButton.addClickListener(event -> {
			permMiddleLayout.remove(userGrid);
			tablesGrid.setWidth("500px");
			
			userGrid.removeAllColumns();
			userGrid.addColumn(User::getName).setHeader("User Name");
			userGrid.setHeight("400px");
			userGrid.setWidth("400px");
			userGrid.setItems(GovernanceQuery.getUsers());
			
			getPermsOfUserButton.setText("Select a User to View Permissions");
			
			permMiddleLayout.add(userGrid, getPermsOfUserButton);
			permLayout.add(permMiddleLayout);
			//userGrid.getSelectedItems();
			userGridAction();
			//getGroupsOfUserButtonAction();
		});
	}
	
	private void userGridAction(){
		userGrid.addSelectionListener(selection -> {
		    Optional<User> u = selection.getFirstSelectedItem();
		    
		    if (u.isPresent()) {
		    	getPermsOfUserButton.setText("View Table Permissions");
		    	//dbRightLayout.remove(selectTableButton);
		    	//selectTableButton.setText("View Permissions of Selected Table");
		    	//dbRightLayout.add(selectTableButton);
		    	username = u.get().getName();
		    	
		    	permsOfUserButtonAction();
		    }
		});
	}
	
	private void permsOfUserButtonAction()
	{
		getPermsOfUserButton.addClickListener(event -> {
		    //this.removeAll();
		    //TablePermissionsView tablePermView = new TablePermissionsView(tablesOfDBGrid, dbName, tableName);
		    //this.add(tablePermView);
			permRightLayout.remove(permGrid);
			tablesGrid.setWidth("400px");
			userGrid.setWidth("300px");
			
			permGrid.removeAllColumns();
			permGrid.setHeight("400px");
			permGrid.setWidth("700px");
			permGrid.setItems(GovernanceQuery.getTablePerms(username, dbName, tableName));
			permGrid.addColumn(TablePermissions::getPrincipal).setHeader("Principal");
			permGrid.addColumn(TablePermissions::getAction).setHeader("Action Type");
			permGrid.addColumn(TablePermissions::getType).setHeader("Object Type");
			permGrid.addColumn(TablePermissions::getKey).setHeader("Object Key");
			
			permGrid.getColumns().get(0).setAutoWidth(true);
			permGrid.getColumns().get(1).setAutoWidth(true);
			permGrid.getColumns().get(2).setAutoWidth(true);
			permGrid.getColumns().get(3).setAutoWidth(true);
			
			//permRightLayout.setMargin(true);
			permRightLayout.add(permGrid);
			permLayout.add(permRightLayout);
		});
	}
	
	/*
	private void setUI()
	{
		
		groupTab.getStyle().set("width", "800px");
		groupTab.add("Groups");
		
		userTab.getStyle().set("width", "800px");
		userTab.add("Users");
				
		//userLayout.setSizeFull();
		userLayout.setSizeFull();
		userLayout.setJustifyContentMode(JustifyContentMode.EVENLY);
		userLayout.add(userLeftLayout);
		getUsersButton.setText("Get Users");
		userLeftLayout.add(getUsersButton);
		
		groupLayout.setSizeFull();
		getGroupsButton.setText("Get Groups");
		groupLayout.add(getGroupsButton);
		
		Map<Tab, Component> tabsToPages = new HashMap<>();
		tabsToPages.put(groupTab, groupLayout);
		tabsToPages.put(userTab, userLayout);
		
		this.add(tabs);
		
		createTabsWithPages();
		
		getGroupsButtonAction();
		getUsersButtonAction();
		
		
		dbLayout.setSizeFull();
		dbLayout.setJustifyContentMode(JustifyContentMode.EVENLY);
		getDBButton.setText("Get Databases");
		dbLeftLayout.add(getDBButton);
		dbLayout.add(dbLeftLayout);
		
		this.add(dbLayout);
		
		getDBButtonAction();
		//this.setSpacing(false);
		//this.setPadding(false);
		//this.setMargin(false);
	}
	
	
	private void createTabsWithPages() {
		this.add(groupLayout);
		
		tabs.addSelectedChangeListener(event -> {
			Tab selectedTab = tabs.getSelectedTab();
			
			if (selectedTab.equals(userTab)) {
				this.remove(groupLayout);
				this.add(userLayout);
			} else if (selectedTab.equals(groupTab)) {
				this.remove(userLayout);
				this.add(groupLayout);
			}
		});
	}
	
	private void getGroupsButtonAction()
	{
		getGroupsButton.addClickListener(event -> {
			groupLayout.remove(groupGrid);
			
			
			groupGrid.removeAllColumns();
			groupGrid.addColumn(Group::getName).setHeader("Group Name");
			//groupGrid.addColumn(Group::getDirectGroup).setHeader("Direct Group");
			
			groupLayout.add(groupGrid);
			
			groupGrid.setItems(GovernanceQuery.getGroups());
			
			groupGrid.setHeight("400px");
			groupGrid.setWidth("400px");
		});
	}
	
	
	private void getDBButtonAction()
	{
		getDBButton.addClickListener(event -> {
			dbLeftLayout.remove(dbGrid);
			
			dbGrid.removeAllColumns();
			dbGrid.addColumn(Database::getName).setHeader("Database Name");
			dbGrid.setHeight("400px");
			dbGrid.setWidth("400px");
			dbGrid.setItems(GovernanceQuery.getDatabases());
			
			getTablesOfDBButton.setText("Select a Database to View Tables");
			
			dbLeftLayout.add(dbGrid, getTablesOfDBButton);
			//userGrid.getSelectedItems();
			
			getTablesOfDBButtonAction();
		});
	}
	
	private void getTablesOfDBButtonAction()
	{
		dbGrid.addSelectionListener(selection -> {
		    Optional<Database> u = selection.getFirstSelectedItem();
		    
		    if (u.isPresent()) {
		    	dbLeftLayout.remove(getTablesOfDBButton);
		    	getTablesOfDBButton.setText("View Tables of Selected Database");
		    	dbLeftLayout.add(getTablesOfDBButton);
		    }
		});
		
		getTablesOfDBButton.addClickListener(event -> {
			Iterator<Database> iter = dbGrid.getSelectedItems().iterator();
			
			Database u = iter.next();
			String name = u.getName();
			
			dbRightLayout.remove(tablesOfDBGrid);
			
			tablesOfDBGrid.removeAllColumns();
			tablesOfDBGrid.setHeight("400px");
			tablesOfDBGrid.setWidth("900px");
			tablesOfDBGrid.setItems(GovernanceQuery.getTables(name));
			
			tablesOfDBGrid.addColumn(Table::getDatabase).setHeader("Database Name");
			tablesOfDBGrid.addColumn(Table::getName).setHeader("Table Name");
			//tablesOfDBGrid.getColumnByKey(2).setAutoWidth(true);
			tablesOfDBGrid.addColumn(Table::getTemp).setHeader("Temporary");
			tablesOfDBGrid.getColumns().get(1).setFlexGrow(1);
			tablesOfDBGrid.getColumns().get(1).setAutoWidth(true);
			
			dbRightLayout.setMargin(true);
			//userRightLayout.set
			
			//userLayout.setWidth("50%");
			selectTableButton.setText("Select a Table");
			dbRightLayout.add(tablesOfDBGrid, selectTableButton);
			
			dbLayout.add(dbRightLayout);
			//userGLayout.setSpacing(false);
	
			//userLayout.add(groupsOfUserGrid);
			selectTableButtonAction();
		});
	}
	
	
	private void selectTableButtonAction()
	{
		tablesOfDBGrid.addSelectionListener(selection -> {
		    Optional<Table> u = selection.getFirstSelectedItem();
		    
		    if (u.isPresent()) {
		    	dbRightLayout.remove(selectTableButton);
		    	selectTableButton.setText("View Permissions of Selected Table");
		    	dbRightLayout.add(selectTableButton);
		    }
		});
	}
	*/
}

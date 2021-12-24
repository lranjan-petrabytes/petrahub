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

@Route(value = "governance/databases", layout = MainLayout.class)
//@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Databases")
public class DatabasesView extends VerticalLayout{
	
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
	private HorizontalLayout  dbLayout = new HorizontalLayout();
	private VerticalLayout  dbLeftLayout = new VerticalLayout();
	private VerticalLayout  dbRightLayout = new VerticalLayout();
	private Button getDBButton = new Button();
	private Grid<Database> dbGrid = new Grid<>(Database.class, false);
	private Button getTablesOfDBButton = new Button();
	private Grid<Table> tablesOfDBGrid = new Grid<>(Table.class, false);
	private Button selectTableButton = new Button();
	private String dbName;
	private String tableName;

	
	public DatabasesView()
	{
		setUI();
	}
	
	private void setUI()
	{
		/*
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
		*/
		
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
	
	/*
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
	*/
	
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
		    Optional<Database> db = selection.getFirstSelectedItem();
		    
		    if (db.isPresent()) {
		    	dbLeftLayout.remove(getTablesOfDBButton);
		    	getTablesOfDBButton.setText("View Tables of Selected Database");
		    	dbLeftLayout.add(getTablesOfDBButton);
		    }
		});
		
		getTablesOfDBButton.addClickListener(event -> {
			Iterator<Database> iter = dbGrid.getSelectedItems().iterator();
			
			Database db = iter.next();
			dbName = db.getName();
			
			dbRightLayout.remove(tablesOfDBGrid);
			
			tablesOfDBGrid.removeAllColumns();
			tablesOfDBGrid.setHeight("400px");
			tablesOfDBGrid.setWidth("900px");
			tablesOfDBGrid.setItems(GovernanceQuery.getTables(dbName));
			
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
			selectTableAction();
		});
	}
	
	
	private void selectTableAction()
	{
		tablesOfDBGrid.addSelectionListener(selection -> {
		    Optional<Table> t = selection.getFirstSelectedItem();
		    
		    if (t.isPresent()) {
		    	dbRightLayout.remove(selectTableButton);
		    	selectTableButton.setText("View Permissions of Selected Table");
		    	dbRightLayout.add(selectTableButton);
		    	selectTableButtonAction();
		    	tableName = t.get().getName();
		    }
		});
	}
	
	private void selectTableButtonAction()
	{
		selectTableButton.addClickListener(event -> {
		    this.removeAll();
		    TablePermissionsView tablePermView = new TablePermissionsView(tablesOfDBGrid, dbName, tableName);
		    this.add(tablePermView);
		});
	}
}

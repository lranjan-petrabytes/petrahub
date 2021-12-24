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

@Route(value = "governance/groups-users", layout = MainLayout.class)
//@RouteAlias(value = "", layout = MainLayout.class)
@PageTitle("Groups & Users")
public class UserGroupsView extends VerticalLayout{
	
	//private VerticalLayout mainLayout = new VerticalLayout();
	private Tab userTab = new Tab();
	private Tab groupTab = new Tab();
	private Tabs tabs = new Tabs(groupTab, userTab);
	
	//Groups Tab
	private VerticalLayout groupLayout = new VerticalLayout();
	private Button getGroupsButton = new Button();
	private Grid<Group> groupGrid = new Grid<>(Group.class, false);
	
	//Users Tab
	private HorizontalLayout  userLayout = new HorizontalLayout();
	private VerticalLayout  userLeftLayout = new VerticalLayout();
	private VerticalLayout  userRightLayout = new VerticalLayout();
	private Button getUsersButton = new Button();
	private Grid<User> userGrid = new Grid<>(User.class, false);
	private Button getGroupsOfUserButton = new Button();
	private Grid<Group> groupsOfUserGrid = new Grid<>(Group.class, false);
	
	
	
	
	public UserGroupsView()
	{
		setUI();
	}
	
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
	
	private void getUsersButtonAction()
	{
		getUsersButton.addClickListener(event -> {
			userLeftLayout.remove(userGrid);
			
			userGrid.removeAllColumns();
			userGrid.addColumn(User::getName).setHeader("User Name");
			userGrid.setHeight("400px");
			userGrid.setWidth("400px");
			userGrid.setItems(GovernanceQuery.getUsers());
			
			getGroupsOfUserButton.setText("Select a User to View Groups");
			
			userLeftLayout.add(userGrid, getGroupsOfUserButton);
			//userGrid.getSelectedItems();
			
			getGroupsOfUserButtonAction();
		});
	}
	
	private void getGroupsOfUserButtonAction()
	{
		userGrid.addSelectionListener(selection -> {
		    Optional<User> u = selection.getFirstSelectedItem();
		    
		    if (u.isPresent()) {
		    	userLeftLayout.remove(getGroupsOfUserButton);
		    	getGroupsOfUserButton.setText("View Groups of Selected User");
		    	userLeftLayout.add(getGroupsOfUserButton);
		    }
		});
		
		getGroupsOfUserButton.addClickListener(event -> {
			Iterator<User> iter = userGrid.getSelectedItems().iterator();
			
			User u = iter.next();
			String name = u.getName();
			
			userRightLayout.remove(groupsOfUserGrid);
			
			groupsOfUserGrid.removeAllColumns();
			groupsOfUserGrid.setHeight("400px");
			groupsOfUserGrid.setWidth("400px");
			groupsOfUserGrid.setItems(GovernanceQuery.getGroups(name));
			groupsOfUserGrid.addColumn(Group::getName).setHeader("Group Name");
			groupsOfUserGrid.addColumn(Group::getDirectGroup).setHeader("Direct Group");
			
			userRightLayout.setMargin(true);
			//userRightLayout.set
			
			//userLayout.setWidth("50%");
			userRightLayout.add(groupsOfUserGrid);
			userLayout.add(userRightLayout);
			//userGLayout.setSpacing(false);
	
			//userLayout.add(groupsOfUserGrid);
			
			
			
		});
	}
	
}

package com.petrabytes.views.wells;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.MainLayout;
import com.petrabytes.views.UI_Update;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
@Route(value = "wells", layout = MainLayout.class)
@PageTitle("Wells")
public class WellsmainView extends Div{
	
	private WellList_view wellInformation = new WellList_view();
	private WellsMapView WellMapView = new WellsMapView();
	private VerticalLayout centerLayout = new VerticalLayout();
	private HorizontalLayout toolbarLayout = new HorizontalLayout();

	private Tab wellinformationTab;
	private Tab welllogstab;
	private Tabs tabs;

	private Map<Tab, Component> tabsToPages = new HashMap<>();

	WellsmainView() throws SQLException {
		String projectname = (String) VaadinService.getCurrentRequest().getWrappedSession()
				.getAttribute("project_name");
		if (projectname == null) {
			PB_Progress_Notification notification = new PB_Progress_Notification();
			String createProject = PetrahubNotification_Utilities.getInstance().createProject();
			notification.setImage("info");
			notification.setText(createProject);
			notification.open();
			notification.setDuration(3000);

		} else {
			
			UI_Update.updateEnable_topBar();
		analysis_setUI();

		}
	}

	private void analysis_setUI() {
		wellinformationTab = new Tab();

		wellinformationTab.add("Well List");

		welllogstab = new Tab();

		welllogstab.add("MAP");

		tabsToPages.put(wellinformationTab, wellInformation);
		tabsToPages.put(welllogstab,WellMapView);
		tabs = new Tabs(wellinformationTab, welllogstab);
		tabs.setWidthFull();
		tabs.setFlexGrowForEnclosedTabs(1);
		tabs.setHeightFull();
		tabs.addThemeVariants(TabsVariant.LUMO_SMALL);
		toolbarLayout.add(tabs);

		add(toolbarLayout, centerLayout);

		centerLayout.expand(centerLayout);
		createTabsWithPages();
	}

	private void createTabsWithPages() {
		centerLayout.add(wellInformation);
		Set<Component> pagesShown = Stream.of(wellinformationTab).collect(Collectors.toSet());
		tabs.addSelectedChangeListener(event -> {
			pagesShown.clear();
			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
			selectedPage.setVisible(true);
			Tab selectedTagName = tabs.getSelectedTab();

			if (selectedTagName.equals(welllogstab)) {
				System.out.println(selectedTagName);
			
				centerLayout.removeAll();
				centerLayout.add(WellMapView);

			} else if (selectedTagName.equals(wellinformationTab)) {

				System.out.println(selectedTagName);
				
				centerLayout.removeAll();
				centerLayout.add(wellInformation);

			}
		});

	}


}

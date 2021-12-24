package com.petrabytes.views.welllogsmain;

import java.io.File;
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
import com.petrabytes.views.well_Information.Well_Main_UI;
import com.petrabytes.views.welllogs.WellLogsView;
import com.petrabytes.views.wells.WellList_view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;

@Route(value = "well-logs", layout = MainLayout.class)
@PageTitle("Well Logs")
public class WellLogsMain extends Div {

	private Well_Main_UI wellInformation = new Well_Main_UI();
	private WellLogsView welllogsview = new WellLogsView();
	private VerticalLayout centerLayout = new VerticalLayout();
	private HorizontalLayout toolbarLayout = new HorizontalLayout();

	private Tab wellinformationTab;
	private Tab welllogstab;
	private Tabs tabs;

	private Map<Tab, Component> tabsToPages = new HashMap<>();

	WellLogsMain() throws SQLException {
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

		wellinformationTab.add("Well Logs");

		welllogstab = new Tab();

		welllogstab.add("Well Logs Search");

		tabsToPages.put(wellinformationTab, wellInformation);
		tabsToPages.put(welllogstab, welllogsview);
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
				centerLayout.add(welllogsview);

			} else if (selectedTagName.equals(wellinformationTab)) {

				System.out.println(selectedTagName);
				
				centerLayout.removeAll();
				centerLayout.add(wellInformation);

			}
		});

	}

}

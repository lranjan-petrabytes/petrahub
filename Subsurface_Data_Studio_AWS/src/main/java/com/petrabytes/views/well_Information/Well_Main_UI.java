package com.petrabytes.views.well_Information;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.petrabytes.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.GeneratedVaadinTabs;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

//@Route(value = "WellInformation", layout = MainLayout.class)
//@PageTitle("Well Information")
public class Well_Main_UI extends VerticalLayout {

	public static final String title = "WellInformation";
	private VerticalLayout centerLayout = new VerticalLayout();
	private HorizontalLayout toolbarLayout = new HorizontalLayout();
	protected Data_Upload_View uploadUI = new Data_Upload_View();
	protected Data_Mapping_UI maapingUI = new Data_Mapping_UI();
	public Quality_Check_UI qcUI = new Quality_Check_UI();

	private Tab uploadTab;
	private Tab mappingTab;
	private Tab qcTab;
	private Tabs tabs;

	Map<Tab, Component> tabsToPages = new HashMap<>();

	private String presentView = "welllog";

	public Well_Main_UI() throws SQLException{
		// TODO Auto-generated constructor stub
		setWellLog_UI();

	}

	private void setWellLog_UI() {
		// TODO Auto-generated method stub
		this.setSizeFull();
		this.setPadding(false);
		this.setMargin(false);

		centerLayout.getElement().getStyle().set("margin-top", "0");
		centerLayout.setSizeFull();
		createTebsWithThemeVariants();
		createTabsWithPages();
		createTabsAutoselectFalse();

	}

	private void createTebsWithThemeVariants() {

		uploadTab = new Tab("Data Upload");
		uploadTab.setId("bgz_logs_uploadTab");
		mappingTab = new Tab("Data Mapping");
		mappingTab.setId("bgz_logs_mappingTab");

		qcTab = new Tab("Data QC");
		qcTab.setId("bgz_logs_qcTab");

		tabsToPages.put(uploadTab, uploadUI);
		tabsToPages.put(mappingTab, maapingUI);
		tabsToPages.put(qcTab, qcUI);

		tabs = new Tabs(uploadTab, mappingTab, qcTab);
		tabs.setId("bgz_logs_tabs");
		tabs.addThemeVariants(TabsVariant.LUMO_SMALL);
		toolbarLayout.add(tabs);

		centerLayout.add(uploadUI);
		add(toolbarLayout, centerLayout);
		centerLayout.expand(centerLayout);

	}

	private void createTabsWithPages() {

		Set<Component> pagesShown = Stream.of(uploadTab).collect(Collectors.toSet());
		tabs.addSelectedChangeListener(event -> {
//		            tabsToPages.values().forEach(page -> page.setVisible(false));

			pagesShown.clear();
			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
			selectedPage.setVisible(true);
			Tab selectedTagName = tabs.getSelectedTab();
			if (selectedTagName.equals(mappingTab)) {
				System.out.println(selectedTagName);
				centerLayout.removeAll();
				centerLayout.add(toolbarLayout, maapingUI);
				maapingUI.upDateTable();
			} else if (selectedTagName.equals(uploadTab)) {
				System.out.println(selectedTagName);
				centerLayout.removeAll();
				centerLayout.add(toolbarLayout, uploadUI);

			} else if (selectedTagName.equals(qcTab)) {
				try {
					qcUI = new Quality_Check_UI();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				centerLayout.removeAll();
				centerLayout.add(toolbarLayout, qcUI);
//				qcUI.listDataIntoQCGrid();
			}
			pagesShown.add(selectedPage);

		});
	}

	private void createTabsAutoselectFalse() {
		// begin-source-example
		// source-example-heading: Tabs with automatic select set to false
		Text newText = new Text("");
		Text oldText = new Text("");
		tabs.addSelectedChangeListener(event -> {
			newText.setText("Current tab : " + event.getSelectedTab().getLabel());

			if (event.getPreviousTab() != null) {
				oldText.setText("Previous tab : " + event.getPreviousTab().getLabel());
			}
		});
		// end-source-example

		tabs.setId("tabs-auto-select-false");

	}

}

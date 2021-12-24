package com.petrabytes.views.wellbore;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Focusable;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.notification.Notification.Position;
import com.petrabytes.views.MainLayout;
import com.petrabytes.views.View_Update;
import com.petrabytes.views.basin.BasinViewGridInfo;
import com.petrabytes.views.seismic.SeismicHeaderInfo;
import com.petrabytes.views.seismic.SeismicTraceDataInfo;
import com.petrabytes.views.wellRegistryQuery.WellboreListInfo;
import com.petrabytes.views.wells.WellListInfo;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.tabs.TabsVariant;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "ConfigwellboreView", layout = MainLayout.class)
@PageTitle("Wellbore Configuration")
public class ConfigwellboreView extends Div {

	private VerticalLayout centerLayout = new VerticalLayout();
	private HorizontalLayout toolbarLayout = new HorizontalLayout();
	private HorizontalLayout displayLayout = new HorizontalLayout();

	private deviationSurveyView deviationSurveyLayout = new deviationSurveyView();
	private casingTubingView casingTubingLayout = new casingTubingView();
	public wellCompletionView wellCompletionLayout = new wellCompletionView();
	public perforationView perforationLayout = new perforationView();
	public formationView formationLayout = new formationView();
	public wellCommentsView wellCommentsLayout = new wellCommentsView();

	private Tab deviationSurveyTab;
	private Tab casingTubingTab;
	private Tab wellCompletionTab;
	private Tab perforationTab;
	private Tab formationTab;
	private Tab wellCommentsTab;
	private Tabs tabs;

	Map<Tab, Component> tabsToPages = new HashMap<>();

	public ConfigwellboreView() {

		createAnalysisLayout();

	}

	private void createAnalysisLayout() {
		
		WellboreListInfo wellboreInfo = View_Update.getWellboreInfo();
		
		Label basinNameLabel = new Label("Basin:");
		basinNameLabel.setClassName("config_label");
		
		Label basinName = new Label();
		basinName.setClassName("config_text");
		
		
		if (wellboreInfo != null ) {
		basinName.setText(wellboreInfo.getBasinName());
		}
		Label wellNameLabel = new Label("Well:");
		wellNameLabel.setClassName("config_label");
		Label wellName = new Label();
		wellName.setClassName("config_text");
		if (wellboreInfo != null) {
		wellName.setText(wellboreInfo.getWellNmae());
		}

		Label wellboreLabel = new Label("Wellbore:");
		wellboreLabel.setClassName("config_label");
		Label wellboreName = new Label();
		wellboreName.setClassName("config_text");
		//WellboreListInfo wellboreInformation = View_Update.getWellboreInfo();
		if (wellboreInfo != null) {
		
		
		wellboreName.setText(wellboreInfo.getWellboreName());
		
		} 

		Button wellbore_list_Button = new Button();
		Image WellboreListImage = new Image("icons" + File.separator + "16x" + File.separator + "wb-list-16.png",
				"Wellbore List");
		wellbore_list_Button.setIcon(WellboreListImage);
		wellbore_list_Button.getElement().setAttribute("title", "Wellbore List");
		wellbore_list_Button.getStyle().set("margin-top", "10px");
	//	wellbore_list_Button.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_PRIMARY);
		HorizontalLayout config = new HorizontalLayout();
		config.setWidth("350px");
	
		
		config.add(wellbore_list_Button);
		config.getElement().getStyle().set("margin-left", "704px");
		displayLayout.add(basinNameLabel, basinName, wellNameLabel, wellName, wellboreLabel, wellboreName,config);
				displayLayout.setClassName("config");
		wellbore_list_Button.addClickListener(event -> {
			// wellbore_buttonpopup();
			UI.getCurrent().navigate("wellboreView");
		});

		deviationSurveyTab = new Tab();
		Image deviationSurveyimage = new Image("icons" + File.separator + "purple-24x" + File.separator + "well_deviation_24.png",
				"Deviation Survey");
		Label deviationSurveylabel = new Label("Deviation Survey");
		// deviationSurveylabel.setClassName("tab_label_spacing");
		deviationSurveyTab.add(deviationSurveyimage);
		deviationSurveyTab.add("Deviation Survey");

		casingTubingTab = new Tab();
		Image casingTubingimage = new Image("icons" + File.separator + "24x" + File.separator + "casing_tubing24_3.png", "Casing Tubing");
//		Label casingTubinglabel = new Label("Casing Tubing");
//		casingTubinglabel.setClassName("tab_label_spacing");
		casingTubingTab.add(casingTubingimage);
		casingTubingTab.add("Casing Tubing");

		wellCompletionTab = new Tab();
		Image wellCompletionimage = new Image("icons" + File.separator + "purple-24x" + File.separator + "well_completions24.png", "Well Completion");
		Label wellCompletionlabel = new Label("Well Completion");
		wellCompletionlabel.setClassName("tab_label_spacing");
		wellCompletionTab.add(wellCompletionimage);
		wellCompletionTab.add("Well Completion");

		perforationTab = new Tab();
		Image perforationTabimage = new Image("icons" + File.separator + "24x" + File.separator + "perforations24_6.png", "Perforation");
		Label perforationTablabel = new Label("Perforation");
		perforationTablabel.setClassName("tab_label_spacing");
		perforationTab.add(perforationTabimage);
		perforationTab.add("Perforation");

		formationTab = new Tab();
		Image formationTabimage = new Image("icons" + File.separator + "24x" + File.separator + "formation24_2.png", "Formation");
		Label formationTablabel = new Label("Formation ");
		formationTablabel.setClassName("tab_label_spacing");
		formationTab.add(formationTabimage);
		formationTab.add("Formation");

		wellCommentsTab = new Tab();
		Image wellCommentsimage = new Image("icons" + File.separator + "purple-24x" + File.separator + "well_comments24_2.png", "Well Comments");
		Label wellCommentslabel = new Label("Well Comments");
		wellCommentslabel.setClassName("tab_label_spacing");
		wellCommentsTab.add(wellCommentsimage);wellCommentsTab.add("Well Comments");

		tabsToPages.put(deviationSurveyTab, deviationSurveyLayout);
		tabsToPages.put(casingTubingTab, casingTubingLayout);
		tabsToPages.put(wellCompletionTab, wellCompletionLayout);
		tabsToPages.put(perforationTab, perforationLayout);
		tabsToPages.put(formationTab, formationLayout);
		tabsToPages.put(wellCommentsTab, wellCommentsLayout);

		tabs = new Tabs(deviationSurveyTab, casingTubingTab, wellCompletionTab, perforationTab, formationTab,
				wellCommentsTab);

//		tabs.setId("Tabs");
		tabs.setWidthFull();
		tabs.setFlexGrowForEnclosedTabs(1);
		tabs.setHeightFull();
		tabs.addThemeVariants(TabsVariant.LUMO_SMALL);
		toolbarLayout.add(tabs);

		// centerLayout.add(deviationSurveyLayout);
		// centerLayout.add(toolbarLayout,deviationSurveyLayout);
		add(displayLayout, toolbarLayout, centerLayout);
		// add(centerLayout);
		centerLayout.expand(centerLayout);

		createTabsWithPages();

	}

	private void createTabsWithPages() {
		centerLayout.add(deviationSurveyLayout);
		Set<Component> pagesShown = Stream.of(deviationSurveyTab).collect(Collectors.toSet());
		tabs.addSelectedChangeListener(event -> {
			pagesShown.clear();
			Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
			selectedPage.setVisible(true);
			Tab selectedTagName = tabs.getSelectedTab();

			if (selectedTagName.equals(casingTubingTab)) {
				System.out.println(selectedTagName);
				centerLayout.removeAll();
				centerLayout.add(casingTubingLayout);

			} else if (selectedTagName.equals(deviationSurveyTab)) {
				System.out.println(selectedTagName);
				centerLayout.removeAll();
				centerLayout.add(deviationSurveyLayout);
				deviationSurveyLayout.plot();

			} else if (selectedTagName.equals(wellCompletionTab)) {
				System.out.println(selectedTagName);
				centerLayout.removeAll();
				centerLayout.add(wellCompletionLayout);

			} else if (selectedTagName.equals(perforationTab)) {
				System.out.println(selectedTagName);
				centerLayout.removeAll();
				centerLayout.add(perforationLayout);

			} else if (selectedTagName.equals(formationTab)) {
				System.out.println(selectedTagName);
				centerLayout.removeAll();
				centerLayout.add(formationLayout);

			} else if (selectedTagName.equals(wellCommentsTab)) {
				System.out.println(selectedTagName);
				centerLayout.removeAll();
				centerLayout.add(wellCommentsLayout);
			}

		});
	}

//	private void wellbore_buttonpopup() {
//		// TODO Auto-generated method stub
//		 Wellbore_Popup_UI equationWindow = new  Wellbore_Popup_UI();
//
//	  	equationWindow.open();
//
//	}

}

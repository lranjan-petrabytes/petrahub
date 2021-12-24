package com.petrabytes.views.das;

import com.petrabytes.views.MainLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "DAS", layout = MainLayout.class)
@PageTitle("DAS")

public class DasView extends Div {

	

	    public DasView() {
	    	Tab tab1 = new Tab("Search");
	    	Tab tab2 = new Tab("Results");
	    	Tabs tabs = new Tabs(tab1, tab2);
	    	tabs.setFlexGrowForEnclosedTabs(1);

	    	add(tabs);
	    }
}

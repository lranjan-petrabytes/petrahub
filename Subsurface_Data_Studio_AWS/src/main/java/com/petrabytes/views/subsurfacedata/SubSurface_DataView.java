package com.petrabytes.views.subsurfacedata;

import com.petrabytes.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

@Route(value = "SubSurface", layout = MainLayout.class)
@PageTitle("SubSurface")

	
	
	public class SubSurface_DataView extends Div {

	    public SubSurface_DataView() {
	    	Tab tab1 = new Tab("Search");
	    	Tab tab2 = new Tab("Results");
	    	Tabs tabs = new Tabs(tab1, tab2);
	    	tabs.setFlexGrowForEnclosedTabs(1);

	    	add(tabs);
	    }
}

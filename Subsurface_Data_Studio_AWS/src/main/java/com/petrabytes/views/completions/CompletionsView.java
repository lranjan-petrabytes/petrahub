package com.petrabytes.views.completions;

import com.petrabytes.ui.utils.MainLayout_Update;
import com.petrabytes.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

@Route(value = "completions", layout = MainLayout.class)
@PageTitle("Completions")
public class CompletionsView extends Div {

    public CompletionsView() {
    	Tab tab1 = new Tab("Search");
    	Tab tab2 = new Tab("Results");
    	Tabs tabs = new Tabs(tab1, tab2);
    	tabs.setFlexGrowForEnclosedTabs(1);

    	try {
			MainLayout_Update.updateClusterStatus();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	add(tabs);
    }

}

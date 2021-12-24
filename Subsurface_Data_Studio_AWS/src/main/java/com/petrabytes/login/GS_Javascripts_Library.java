package com.petrabytes.login;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;

public class GS_Javascripts_Library {

	public void addJS_Library() {
		/**
		 * adding d3.v4 library
		 */
		UI.getCurrent().getPage().addJavaScript("https://d3js.org/d3.v4.min.js");
		UI.getCurrent().getPage().addJavaScript("https://cdn.plot.ly/plotly-latest.min.js");
		UI.getCurrent().getPage().addJavaScript("https://underscorejs.org/underscore.js");
		/**
		 * adding threejs library
		 */
	
	}
}

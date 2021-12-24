package com.petrabytes.views;

import com.vaadin.flow.component.UI;

public class UI_Update {
	
	
	public static void updateEnable_topBar() {
		String disableKeys = "addProject,openProject,deleteProject";
		String enableKeys = "saveProject,closeProject";

		
		/*
		 * disable
		 */
		UI.getCurrent().getPage().executeJavaScript("disableComponents($0)", disableKeys);
		/*
		 * enable
		 */
		UI.getCurrent().getPage().executeJavaScript("enableComponents($0)", enableKeys);

	}

}

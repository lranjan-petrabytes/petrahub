package com.petrabytes.dashboard;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.petrabytes.ui.utils.MainLayout_Update;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentUtil;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinRequest;

public class Dashboard_Generator {

	private String path = ((HttpServletRequest) VaadinRequest.getCurrent()).getRealPath("/");
	private String dashboardPath = path + "files" + File.separator + "dashboard.json";
	private Tab[] tabs = null;

	public List<NativeButton> generateDashboardDescriptorButtons(String key) {
		/*
		 * get json object from given path
		 */
		JSONObject dashboardJsonObject = null;
		File file = new File(dashboardPath);
		if (file.exists()) {
			try {
				String content = FileUtils.readFileToString(file, "utf-8");
				dashboardJsonObject = new JSONObject(content);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// logger.addLog(ExceptionUtils.getStackTrace(e), "ERROR");
				e.printStackTrace();
			}
		}
		if (key.contains("/")) {

			String[] keys = key.split("/");
			for (String kValue : keys) {
				dashboardJsonObject = getObjectByKey(dashboardJsonObject, kValue);
			}
			MainLayout_Update.setTopToolbar_component_Enable(true);
			MainLayout_Update.updateEnable_topBar();
		} else {
			dashboardJsonObject = dashboardJsonObject.getJSONObject(key);
			MainLayout_Update.setTopToolbar_component_Enable(false);
		}

		/**
		 * views Object
		 */
		if (dashboardJsonObject.has("views")) {
			JSONObject viewsObject = dashboardJsonObject.getJSONObject("views");
			List<NativeButton> dashboardButtonList = new ArrayList<NativeButton>();
			Iterator<String> keys = viewsObject.keys();
			while (keys.hasNext()) {
				String k = keys.next();
				JSONObject obj = viewsObject.getJSONObject(k);
				String name = obj.getString("name");
				String icon = obj.getString("icon");
				String id = obj.getString("id");
				String navi = obj.getString("naviagte");

				NativeButton nButton = new NativeButton();

				Image image = new Image(icon, name);
				image.setClassName("dashboardbutton_image");
				nButton.add(image);

				nButton.setSizeFull();
//				nButton.setText(nativeButtonName);
				Label label = new Label(name);
				label.setClassName("dashboardbutton_label");
				nButton.add(label);
				nButton.setClassName("dashboardbutton");
				nButton.setId(id);

				nButton.getElement().setAttribute("navi", navi);
				nButton.addClickListener(event -> {
					NativeButton button = event.getSource();
					String buttonid = button.getId().get();
					String view = button.getElement().getAttribute("navi");
					UI.getCurrent().navigate(view);
				});
				dashboardButtonList.add(nButton);
			}
			return dashboardButtonList;
		}

		/**
		 * tabs object
		 */
		if (dashboardJsonObject.has("tabs")) {
			JSONArray objArray = dashboardJsonObject.getJSONArray("tabs");
			tabs = new Tab[objArray.length()];
			for (int i = 0; i < objArray.length(); i++) {
				JSONObject obj = objArray.getJSONObject(i);
				String name = obj.getString("name");
				String classview = obj.getString("classview");
				String icon = obj.getString("icon");
				tabs[i] = createTab(name, vewClassName(classview), icon);
			}
		}
		return null;
	}

	private static Tab createTab(String text, Class<? extends Component> navigationTarget, String imagepath) {
		final Tab tab = new Tab();
		Image image = new Image(imagepath, "add");
		image.getElement().getStyle().set("margin-right", "20px");
		image.getElement().getStyle().set("width", "35px");
		tab.add(image, new RouterLink(text, navigationTarget));
		ComponentUtil.setData(tab, Class.class, navigationTarget);
		return tab;
	}

	/**
	 * to get only class name instead of getting classpath from xml file
	 * 
	 * @param className
	 * @return
	 */
	public Class vewClassName(String className) {
		// TODO Auto-generated method stub
		Class viewClass = null;
		try {
			viewClass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
		return viewClass;
	}

	private JSONObject getObjectByKey(JSONObject dashboardJsonObject, String kValue) {
		// TODO Auto-generated method stub

		return dashboardJsonObject.getJSONObject(kValue);
	}

	public Tab[] getTabs() {
		return tabs;
	}

}

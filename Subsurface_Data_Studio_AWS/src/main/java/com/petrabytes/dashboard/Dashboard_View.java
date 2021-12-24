package com.petrabytes.dashboard;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.petrabytes.login.GS_Javascripts_Library;
import com.petrabytes.ui.navigation.util.PB_Dashboard_Registry_XMLIO;
import com.petrabytes.ui.navigation.util.PB_Dashboard_User_Registry_XMLIO;
import com.petrabytes.ui.utils.MainLayout_Update;
import com.petrabytes.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;

@Route(value = "dashboard", layout = MainLayout.class)

public class Dashboard_View extends VerticalLayout implements BeforeEnterObserver, HasUrlParameter<String> {

	public static final String title = "dashboard";

	private HorizontalLayout layout;
	private Div content;

	public Dashboard_View() {
		// TODO Auto-generated constructor stub
		HashMap<String, Object> userDataList = (HashMap<String, Object>) VaadinService.getCurrentRequest()
				.getWrappedSession().getAttribute("userdata");
		if(userDataList == null) {
			UI.getCurrent().getPage().setLocation("login");
		}
		setUI();
	}

	private void setUI() {
		// TODO Auto-generated method stub
		setSizeFull();
		setClassName("dashboard-layout");
		layout = new HorizontalLayout();
		layout.setHeightFull();
		layout.setSpacing(false);
		layout.setMargin(true);
		add(layout);
		expand(layout);

	}

	@Override
	public void setParameter(BeforeEvent event, String parameter) {
		// TODO Auto-generated method stub
		if (layout.getComponentCount() > 0)
			layout.remove(content);
		createTextLayout(parameter);

	}

	private void createTextLayout(String parameter) {
		content = new Div();
//		content.setHeightFull();
		content.getStyle().set("display", "flex");
		content.getStyle().set("background", "transparent");
//		content.setClassName("content");
		content.getStyle().set("alignContent", "center");
//		content.setHeight("350px");
		layout.add(content);
		layout.getStyle().set("margin", "auto");
		layout.expand(content);
//		layout.setDefaultVerticalComponentAlignment(Alignment.STRETCH);
		setUPDashboard(parameter);
	}

	private void setUPDashboard(String parameter) {
		String key = "dashboard/";
		if (parameter.equals("main"))
			key = "dashboard";
		else
			key = key + "views/" + parameter;

		Dashboard_Generator dashboardGenerator = new Dashboard_Generator();
		List<NativeButton> navButtons = dashboardGenerator.generateDashboardDescriptorButtons(key);
		if (navButtons != null) {
			for (NativeButton navButton : navButtons) {
				content.add(createButtonBlock(navButton));
			}
		}

		HorizontalLayout imageLayout = new HorizontalLayout();
		imageLayout.setClassName("load_refresh_Image");

		makeContentScrollable();

		Tab[] tabs = dashboardGenerator.getTabs();
		new MainLayout_Update().updateTabs(tabs);
	}

	private Div createButtonBlock(NativeButton button) {
		final Div d = new Div();
		d.getStyle().set("background", "transparent");
		d.setHeight("250px");
		d.setWidth("350px");
		d.getStyle().set("margin", "5px");
		d.add(button);
		return d;
	}

	private void makeContentScrollable() {
		content.getStyle().set("flexWrap", "wrap");
		content.getStyle().set("overflowY", "auto");
	}

	@Override
	public void beforeEnter(BeforeEnterEvent event) {
		// TODO Auto-generated method stub
//yyy		HashMap<String, Object> userDataList = (HashMap<String, Object>) VaadinService.getCurrentRequest()
//				.getWrappedSession().getAttribute("userdata");
//		if (userDataList == null)
//			getAuth();
	}
}

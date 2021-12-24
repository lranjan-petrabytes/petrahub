package com.petrabytes.loginsso2;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bluegridz.msal4j.helpers.AuthException;
import com.bluegridz.msal4j.helpers.AuthHelper;
import com.bluegridz.msal4j.helpers.IdentityContextAdapterServlet;
import com.bluegridz.msal4j.helpers.IdentityContextData;
import com.microsoft.aad.msal4j.IAccount;
import com.petrabytes.login.GS_Javascripts_Library;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.PWA;

import com.vaadin.flow.server.VaadinService;

//@Push()
//@Route("login")
//@RouteAlias("")
//@PWA(name = "GEOSmartOnline", shortName = "GEOSmartOnline", iconPath = "icons/favicon_geosmart_online32x.png")
//@Theme(Material.class)
//@CssImport("styles/shared-styles.css")
//@CssImport("styles/dashboard.css")
//@CssImport("styles/dashboard1.css")
//@CssImport("styles/login-form.css")
//@CssImport("styles/skygridz-common.css")
//@CssImport(value = "styles/skyz-text.css", themeFor = "vaadin-text-field")
//@CssImport(value = "styles/skyz-grid.css", themeFor = "vaadin-grid")
//@CssImport(value = "styles/skyz-form.css", themeFor = "vaadin-form-item")
//@CssImport(value = "styles/vaadin-text-area-field-styles.css", themeFor = "vaadin-text-area")

public class GS_Login_SSO2 extends VerticalLayout {

	public GS_Login_SSO2() {
		// TODO Auto-generated constructor stub

		this.setSizeFull();
		HttpServletRequest req = (HttpServletRequest) VaadinService.getCurrentRequest();
		HttpServletResponse resp = (HttpServletResponse) VaadinService.getCurrentResponse();
		IdentityContextData context = new IdentityContextAdapterServlet(req, resp).getContext();
		IAccount account = context.getAccount();
		if (account != null) {
			UI.getCurrent().getPage().setLocation("dashboard/main");
		} else {
			try {
				AuthHelper.signIn(new IdentityContextAdapterServlet(req, resp));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (AuthException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

//	@Override
//	public void beforeEnter(BeforeEnterEvent event) {
//		// TODO Auto-generated method stub
//		
//
//		HttpServletRequest req = (HttpServletRequest) VaadinService.getCurrentRequest();
//		HttpServletResponse resp = (HttpServletResponse) VaadinService.getCurrentResponse();
//
//		try {
//			AuthHelper.signIn(new IdentityContextAdapterServlet(req, resp));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (AuthException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}

package com.petrabytes.loginsso2;

import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.petrabytes.login.Bluegridz_Utilities;
import com.petrabytes.login.login_view_ui;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@Push()
@Route("login")
@RouteAlias("")
public class LoginMain_Page extends VerticalLayout {

	public LoginMain_Page() {
		this.setSizeFull();
		loginSheduleAction();
	
	}

	public void loginSheduleAction() {

		String login_pointer = Bluegridz_Utilities.getInstance().getDatabasePointerProperty();
		if (login_pointer.equals("Azure")) {

			add(new GS_Login_SSO2());

		} else if (login_pointer.equals("AWS")) {

			add(new login_view_ui());

		} else {

//			UI.getCurrent().navigate("applogin");
			UI.getCurrent().getPage().setLocation("applogin");

		}

	}
}

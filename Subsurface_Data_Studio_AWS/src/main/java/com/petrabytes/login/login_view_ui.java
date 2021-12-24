package com.petrabytes.login;

import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;
//@Push()
//@Route("login")
//@RouteAlias("")

@PageTitle("Sub-surface Data Studio")

public class login_view_ui extends VerticalLayout {
	
	public login_view_ui() {
		UI_SetUp();
	}

	private void UI_SetUp() {
		// TODO Auto-generated method stub
		setSizeFull();
		setClassName("back_Image");
		add(new Blgz_login_UI_Action());
	}
	
	

}

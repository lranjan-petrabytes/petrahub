package com.petrabytes.login;

import javax.servlet.http.HttpServletRequest;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;

public class Signout_UI_Dialog extends PetrabyteUI_Dialog {
	private Label signoutText = new Label("Are you sure you want to Signout?");

	public Signout_UI_Dialog() {

		
		setUI();
		okButtonEvent();
		
	}
	
	private void setUI() {
		
		
		VerticalLayout layout = new VerticalLayout(signoutText);
		layout.getStyle().set("padding-bottom", "0px");
		layout.setWidth("100%");
		mainLayout.setWidth("360px");
		mainLayout.setHeight("190px");
		this.content.add(layout);
		this.setTitle("Confirm Signout");
		this.setButtonName("OK");

	}
	
	private void okButtonEvent() {
		this.saveButton.addClickListener(event -> {
			this.close();
			UI.getCurrent().navigate("applogin");
			
		});
		
	}

}

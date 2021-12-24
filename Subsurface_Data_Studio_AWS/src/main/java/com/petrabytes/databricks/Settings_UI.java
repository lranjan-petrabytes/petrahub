package com.petrabytes.databricks;

import java.io.File;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;


public class Settings_UI extends PetrabyteUI_Dialog {
	
	private HorizontalLayout mainLayout = new HorizontalLayout();
	private Label userNameLabel = new Label();
	private Label passwordLabel = new Label();
	private Label TokenLabel = new Label();
	private Label databricksLabel = new Label();
	private TextField account = new TextField();
	private TextField password = new TextField();
	private TextField Token = new TextField();
	private TextField databricksid = new TextField();

	
	public Settings_UI() {
	this.setTitle(" Settings");
	this.setImage("icons" + File.separator + "purple-24x" + File.separator + "settings24.png");
	this.setButtonName("OK");
	VerticalLayout headerLayout = new VerticalLayout();
	userNameLabel.setText("User Name");
	passwordLabel.setText("Password");
	TokenLabel.setText("Token");
	databricksLabel.setText("Databricks ID");
	headerLayout.add(userNameLabel,passwordLabel,TokenLabel,databricksLabel);
	userNameLabel.getStyle().set("margin-top", "25%");
	passwordLabel.getStyle().set("margin-top", "27%");
	TokenLabel.getStyle().set("margin-top", "33%");
	databricksLabel.getStyle().set("margin-top", "32%");
	
	
	VerticalLayout passwordLayout = new VerticalLayout();
	TextField account = new TextField();
	
	TextField password = new TextField();
	TextField Token = new TextField();
	TextField databricksid = new TextField();
	passwordLayout.add(account,password,Token,databricksid);
//	passwordLayout.getStyle().set("margin-right", "40px");
	
	HorizontalLayout tokenLayout = new HorizontalLayout();
	
	
	
	HorizontalLayout databrickLayout = new HorizontalLayout();
	
	
	
	mainLayout.add(headerLayout,passwordLayout);
	mainLayout.setWidth("380px");
	mainLayout.setHeight("250px");
	this.content.add(mainLayout);

}
}

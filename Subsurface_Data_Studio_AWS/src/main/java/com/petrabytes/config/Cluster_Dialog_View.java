package com.petrabytes.config;

import java.io.File;

import org.json.JSONObject;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
//@Route(value = "Cluster", layout = MainLayout.class)
public class Cluster_Dialog_View extends PetrabyteUI_Dialog {
	
	private HorizontalLayout HeaderLayout = new HorizontalLayout();
	private Label userNameLabel = new Label();
	private Label urlLabel = new Label();
	private Label serverNameLabel = new Label();
	private Label typeLabel = new Label();
	private Label TokenLabel = new Label();
	private TextField userName = new TextField();
	private TextField url = new TextField();
	private TextField serverName = new TextField();
	private ComboBox<String> typeComboBox = new ComboBox();
	private PasswordField Token = new PasswordField();
	private Boolean _editFlag;
	JSONObject json = new JSONObject();

	public Cluster_Dialog_View(boolean editFlag) {
		setui();
		HeaderLayout.setWidth("380px");
		HeaderLayout.setHeight("330px");
		this.content.add(HeaderLayout);
		this._editFlag = editFlag;
		if (_editFlag == true) {
			this.setImage("icons" + File.separator + "16x" + File.separator + "edit24x.png");
			this.setTitle("Edit Server");

		} else {
			this.setImage("icons" + File.separator + "purple-24x" + File.separator + "add-24x.png");
			this.setTitle("Add Server");
			
		}
		saveButtonEvent();
		
	}

	public void saveButtonEvent() {
		// TODO Auto-generated method stub
		
		this.saveButton.addClickListener(event -> {
			json.put("servername", serverName.getValue());
			json.put("url", url.getValue());
			json.put("userName", userName.getValue());
			json.put("token", Token.getValue());
			json.put("type", typeComboBox.getValue());
			this.close();
		});
	
	}

	

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	private void setui() {
		// TODO Auto-generated method stub
		
		typeComboBox.setItems("Serverless", "Compute");
		VerticalLayout labelLayout = new VerticalLayout();
		serverNameLabel.setText("Server Name");
		serverNameLabel.getStyle().set("margin-top", "25px");
		urlLabel.setText("URL");
		urlLabel.getStyle().set("margin-top", "35px");
		userNameLabel.setText("User Name");
		userNameLabel.getStyle().set("margin-top", "35px");
		TokenLabel.setText("Token");
		TokenLabel.getStyle().set("margin-top", "33px");
		Token.setRevealButtonVisible(false);
		typeLabel.setText("Type");
		typeLabel.getStyle().set("margin-top", "33px");
		labelLayout.add(serverNameLabel,urlLabel,userNameLabel,TokenLabel,typeLabel);
		
		VerticalLayout textfieldLayout = new VerticalLayout();
		textfieldLayout.add(serverName,url,userName,Token,typeComboBox);
		HeaderLayout.add(labelLayout,textfieldLayout);
		mainLayout.add(HeaderLayout);
	}
	
	
//	public static MainLayout getInstance() {
//		return (MainLayout) UI.getCurrent().getChildren().filter(component -> component.getClass() == MainLayout.class)
//				.findFirst().orElse(null);
//	}
}

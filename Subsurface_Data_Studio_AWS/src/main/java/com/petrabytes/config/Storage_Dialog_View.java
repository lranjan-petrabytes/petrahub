package com.petrabytes.config;

import java.io.File;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.views.MainLayout;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

public class Storage_Dialog_View extends PetrabyteUI_Dialog {
	
	private HorizontalLayout HeaderLayout = new HorizontalLayout();
	private Label serverNameLabel = new Label();
	private Label acesskeyLabel = new Label();
	private Label scerectkeyLabel = new Label();
	private Label bucketnameLabel = new Label();
	private Label cloudproviderLabel = new Label();
	private TextField serverName = new TextField();
	private TextField acesskey = new TextField();
	private TextField scerectkey = new TextField();
	private ComboBox<String> cloudproviderComboBox = new ComboBox();
	private TextField bucketname = new TextField();
	private Boolean _editFlag;

	public Storage_Dialog_View(boolean editFlag) {
		setui();
		HeaderLayout.setWidth("550px");
		HeaderLayout.setHeight("330px");
		this.content.add(HeaderLayout);
		this._editFlag = editFlag;
		if (_editFlag == true) {
			this.setImage("icons" + File.separator + "16x" + File.separator + "edit24x.png");
			this.setTitle("Edit Storage");

		} else {
			this.setImage("icons" + File.separator + "purple-24x" + File.separator + "add-24x.png");
			this.setTitle("Add Storage");
			
		}
	}



	private void setui() {
		// TODO Auto-generated method stub
		VerticalLayout labelLayout = new VerticalLayout();
		serverNameLabel.setText("Storage Name");
		serverNameLabel.getStyle().set("margin-top", "25px");
		acesskeyLabel.setText("Access Key/ Account Name");
		acesskeyLabel.getStyle().set("margin-top", "35px");
		scerectkeyLabel.setText("Secret Key/ Account Key");
		scerectkeyLabel.getStyle().set("margin-top", "35px");
		bucketnameLabel.setText("Bucket Name/ Container Name");
		bucketnameLabel.getStyle().set("margin-top", "33px");
	//	Token.setRevealButtonVisible(false);
		cloudproviderLabel.setText("Cloud Provider");
		cloudproviderLabel.getStyle().set("margin-top", "33px");
		labelLayout.add(serverNameLabel,acesskeyLabel,scerectkeyLabel,bucketnameLabel,cloudproviderLabel);
		
		VerticalLayout textfieldLayout = new VerticalLayout();
		textfieldLayout.add(serverName,acesskey,scerectkey,bucketname,cloudproviderComboBox);
		HeaderLayout.add(labelLayout,textfieldLayout);
		mainLayout.add(HeaderLayout);
	}
	public static MainLayout getInstance() {
		return (MainLayout) UI.getCurrent().getChildren().filter(component -> component.getClass() == MainLayout.class)
				.findFirst().orElse(null);
	}
}




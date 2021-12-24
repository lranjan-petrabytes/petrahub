package com.petrabytes.login;


import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

public class OtpSignUpView extends Dialog {

	protected Button loginButton;
	protected Button cancel;
	protected TextField otpTextField;
	// protected Label label;
	private Label confirmUser = new Label("Verification");
//	protected ComboBox<Blgz_Company> companyComboBox;
	private VerticalLayout newvertical = new VerticalLayout();
	
	//private String userName;

//	private InitiateAuthResult result;
//	private CognitoHelper helper;
//	private String userName;

	public OtpSignUpView(String userName) {
		// TODO Auto-generated constructor stub
	//	this.result = result;
	//	this.helper = helper;
	//	this.userName = userName;
		add(newvertical);
		loginFormSetUI( userName);
//		reportBugButton();
	}

	private void loginFormSetUI(String userName) {

		// label = new Label("Conferm");
		 confirmUser.setClassName("verification");
		otpTextField = new TextField("Enter OTP Code");
		otpTextField.setId("bgz_login_text");
		HorizontalLayout newlayout = new HorizontalLayout();
		loginButton = new Button("Validate");

		loginButton.setId("bgz_login_button");
		loginButton.setSizeUndefined();

		cancel = new Button("Cancel");

		cancel.setId("bgz_login_button");
		cancel.setSizeUndefined();
		newlayout.add(loginButton, cancel);
		newlayout.getStyle().set("align-self", "center");
		cancel.getStyle().set("background-color", "#48278F");
		cancel.getStyle().set("color", "white");
		loginButton.getStyle().set("background-color", "#48278F");
		loginButton.getStyle().set("color", "white");
		newvertical.add(confirmUser, otpTextField, newlayout);
		loginButtonAction(userName);
	}

	private void loginButtonAction(String userName) {
		// TODO Auto-generated method stub
		loginButton.addClickListener((task) -> {
			CognitoHelper helper = new CognitoHelper();
			   boolean success=helper.VerifyAccessCode(userName, otpTextField.getValue());
	            if (success){
	                System.out.println("OTP validation is successful");
	                Notification.show("OTP validation is successful",3000, Position.BOTTOM_START).addThemeVariants(NotificationVariant.LUMO_CONTRAST);
	                this.close();
	                UI.getCurrent().navigate("applogin");

	            }
	            else {
	                System.out.println("OTP validation has failed");
	                Notification.show("OTP validation has failed. Please re-enter",3000, Position.BOTTOM_START).addThemeVariants(NotificationVariant.LUMO_CONTRAST);
	            }
			
			});
		
		 cancel.addClickListener(event -> {

	            close();
	        });

	}

}

package com.petrabytes.login;

import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.amazonaws.services.rekognition.model.Label;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

public class Signinview extends Dialog {

	protected Button loginButton;
	protected Button cancel;
	protected TextField otpTextField;
	// protected Label label;
	private H2 confermUser = new H2("Confirm User");
//	protected ComboBox<Blgz_Company> companyComboBox;
	private VerticalLayout newvertical = new VerticalLayout();

	private InitiateAuthResult result;
	private CognitoHelper helper;
	private String userName;

	public Signinview(InitiateAuthResult result, CognitoHelper helper, String userName) {
		// TODO Auto-generated constructor stub
		this.result = result;
		this.helper = helper;
		this.userName = userName;
		add(newvertical);
		loginFormSetUI();
//		reportBugButton();
	}

	private void loginFormSetUI() {

		// label = new Label("Conferm");
		// label.setName("Conferm User");
		otpTextField = new TextField("Enter OTP Code");
		otpTextField.setId("bgz_login_text");
		HorizontalLayout newlayout = new HorizontalLayout();
		loginButton = new Button("Login");

		loginButton.setId("bgz_login_button");
		loginButton.setSizeUndefined();

		cancel = new Button("Cancel");

		cancel.setId("bgz_login_button");
		cancel.setSizeUndefined();
		newlayout.add(loginButton, cancel);
		newvertical.add(confermUser, otpTextField, newlayout);
		loginButtonAction();
	}

	private void loginButtonAction() {
		// TODO Auto-generated method stub
		loginButton.addClickListener((task) -> {
			String otp = otpTextField.getValue();

			AuthenticationResultType mfaResult = helper.mfavalid(userName, otp, result.getSession());
//			boolean success=helper.VerifyAccessCode(userName, otpTextField.getValue());

			if (mfaResult != null) {
				System.out.println("OTP validation is successful");
				UI.getCurrent().navigate("dashboard/main");
				close();
			} else {
				System.out.println("OTP validation has failed");
				close();
			}
		});
	}

}
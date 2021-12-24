package com.petrabytes.login;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.Route;
import java.util.regex.*;


@Route(value = "signup")

public class SignUpView extends VerticalLayout {

	public static final String title = "Sign-up Form";
	private H2 signUp = new H2("Sign-up Form");
	H2 label1 = new H2("Sign-up Form");
	// Private Label label = new Label("Sign-up Form")
	private Button registerButton;
	private TextField userNameTextField;
	private PasswordField passwordTextField;
	private PasswordField repeatTextField;

	private EmailField emailTextField;
	private TextField contactNumberTextField;

	private NativeButton loginLink;
   
	public SignUpView() {
		setUI();

		loginButton();
	}

	private void setUI() {
		// TODO Auto-generated method stub
		setSizeFull();
		setClassName("signup-layout");
//		VerticalLayout imageLayout = new VerticalLayout(signUp);
//		signUp.getStyle().set("margin-left", "30%");
//		Label label1 = new Label();
//	//	formLayout.add(imageLayout);

		FormLayout formLayout = new FormLayout();
		formLayout.setWidth("30%");
//		label.getStyle().set("margin-left", "30%");
//		label.setAlignSelf(FlexComponent.Alignment.CENTER);

		Image image = new Image("images/Logo_v10.png", "SDS_ App logo");
		image.setWidth("300px");
		image.getStyle().set("margin-top", "-15px");
		VerticalLayout imageLayout1 = new VerticalLayout(image);
		formLayout.add(imageLayout1);
		imageLayout1.setAlignSelf(FlexComponent.Alignment.CENTER, image);
//		Label label=new Label("Sign-up Form");
//		prodtctsLabel.getElement().getStyle().set("padding-top", "10px");
//		prodtctsLabel.getElement().getStyle().set("padding-bottom", "10px");

		formLayout.add(imageLayout1);

		userNameTextField = new TextField("Username");
		// firstNameTextField.setId("bgz_signup_firstnametext");
		userNameTextField.setWidth("100%");
		formLayout.add(userNameTextField);

		passwordTextField = new PasswordField("Password");
		// lastNameTextField.setId("bgz_signup_lastnametext");
		passwordTextField.setPlaceholder("Enter password");
	//	passwordTextField.setImmediate(true);
		
//		if (password_success) {
//		passwordTextField.setValue(str1);
//		}else {
//			Notification.show("Enter Right password ");
//			
//		}
		passwordTextField.setWidth("100%");
		formLayout.add(passwordTextField);

		formLayout.getElement().appendChild(ElementFactory.createBr());
		
//		companyComboBox = new ComboBox<Blgz_Company>("Company");
//		companyComboBox.setId("bgz_signup_companycombo");
//		companyComboBox.setWidth("100%");
//		companyField = new TextField("Company Name");
//		companyField.setWidth("100%");
//		companyField.getStyle().set("margin", "0px");
//		companyField.setVisible(false);
//
//		formLayout.add(companyComboBox,companyField);

		repeatTextField = new PasswordField("Confirm Password");
		// designationTextField.setId("bgz_signup_designation");
		repeatTextField.setPlaceholder("Enter password");
		
		repeatTextField.setWidth("100%");
		formLayout.add(repeatTextField);

//		
//		roleComboBox = new ComboBox<Blgz_Role_Info_IO>("Role");
//		roleComboBox.setId("bgz_signup_rolecombo");
//		roleComboBox.setWidth("100%");
//		formLayout.add(roleComboBox);

		emailTextField = new EmailField("Email");
		emailTextField.setClearButtonVisible(true);
		emailTextField.setErrorMessage("Please enter a valid email address");
		emailTextField.setId("bgz_signup_email");
		emailTextField.setWidth("100%");
		formLayout.add(emailTextField);

		contactNumberTextField = new TextField("Mobile");
		contactNumberTextField.setId("bgz_signup_contactnumber");
		contactNumberTextField.setWidth("100%");
		contactNumberTextField.setHelperText("Enter mobile number with country code. Ex: +1234567890");
		formLayout.add(contactNumberTextField);

//		addressTextField = new TextField("Address");
//		addressTextField.setId("bgz_signup_address");
//		addressTextField.setWidth("100%");
//		formLayout.add(addressTextField);

//		productSeletion = new MultiSelectListBox<String>();
//		productSeletion.setId("bgz_signup_product");
//		productSeletion.setWidth("100%");
//		productSeletion.getElement().getStyle().set("height", "100px");
//		productSeletion.getElement().getStyle().set("border-style", "solid");	
//		productSeletion.getElement().getStyle().set("border-width", "thin");

		registerButton = new Button("Register");
		registerButton.setId("bgz_signup_textegisterbutton");
		registerButton.setClassName("RegisterButton");

		registerButton.setSizeUndefined();
		HorizontalLayout actionLayout = new HorizontalLayout(registerButton);
		actionLayout.getStyle().set("margin-top", "-15px");
		formLayout.add(actionLayout);
//		actionLayout.setAlignSelf(FlexComponent.Alignment.CENTER, registerButton);
//		registerButton.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);

		registerButton.getElement().getStyle().set("margin", "auto");
		registerButton.getStyle().set("margin-top", "30px");
		registerButton.getStyle().set("background-color", "#48278F");
		registerButton.getStyle().set("color", "white");

//		Button loginButton = new Button("LOGIN", event -> {
//			UI.getCurrent().navigate("login");
//		});
//		
//		loginButton.setClassName("LOGINButton");
//		addCompanyButton.addClickListener(event -> {
//		});

//		loginButton.setSizeUndefined();
//		HorizontalLayout actionLayout1 = new HorizontalLayout(loginButton);
//		formLayout.add(actionLayout1);
//		actionLayout1.setAlignSelf(FlexComponent.Alignment.CENTER, loginButton);
//		loginButton.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
//
//		loginButton.getElement().getStyle().set("margin", "auto");

		HorizontalLayout loginLayout = new HorizontalLayout();
		Label label = new Label("Have an account?");
//		label.getElement().getStyle().set("font-weight", "bold");
//		label.getStyle().set("color", "#6200ee");

//		RouterLink loginLink = new RouterLink("Log in", Blgz_Login_UI.class);
		loginLink = new NativeButton("Login");
		label.setClassName("login_account");
		loginLink.setClassName("login_buttons");

		loginLink.getStyle().set("color", "#6200ee");
		String skygridz_version = Bluegridz_Utilities.getInstance().getCurrentVersion();

		loginLayout.add(label, loginLink);
		loginLayout.getStyle().set("margin-top", "-15px");
//		VerticalLayout copyRightLayout = new VerticalLayout();
//		H2 copyright = new H2("Copyrights (C) 2020 Petrabytes Corporation.");
//		Label copyright1 = new Label("All rights reserved. Version 3.1 RC1 ("+skygridz_version+")");
//		copyRightLayout.setAlignSelf(Alignment.CENTER, copyright);
//		copyRightLayout.setAlignSelf(Alignment.CENTER, copyright1);
//		copyRightLayout.setPadding(true);
//
//		copyRightLayout.add(loginLayout,copyright, copyright1);

		formLayout.add(loginLayout);

		add(formLayout);
		formLayout.getElement().getStyle().set("margin", "auto");
		formLayout.getElement().getStyle().set("background-color", "white");
		formLayout.getElement().getStyle().set("border-radius", "20px");
		formLayout.getElement().getStyle().set("padding", "2%");

		formLayout.getElement().getStyle().set("border-style", "solid");
		formLayout.getElement().getStyle().set("border-color", "#D1D1D1");
		formLayout.setResponsiveSteps(new ResponsiveStep("25em", 1), new ResponsiveStep("35em", 1));
		formLayout.setClassName("signUp-theme");

		/**
		 * Company List for the Sign Up company List
		 */

//		dataprovider = Blgz_Company_Provider.createCompanyListProvider();
//		companyComboBox.setItemLabelGenerator(Blgz_Company::getName);
//		companyComboBox.setDataProvider(dataprovider);

		/**
		 * Role list for the Sign Up Page
		 */
//		roleDataProvider = Blgz_Role_Provider.createRoleListProvider();
//		roleComboBox.setItemLabelGenerator(Blgz_Role_Info_IO::getRoleType);
//		roleComboBox.setDataProvider(roleDataProvider);

		ragisterButton();

	}

	private void loginButton() {
		loginLink.addClickListener(event -> {
			UI.getCurrent().navigate("login");
		});
	}
	
	
	private void ragisterButton() {
		
		registerButton.addClickListener(event -> {
			boolean phone_success = isValidPhoneNumber(contactNumberTextField.getValue());
			String str1 = passwordTextField.getValue();
			boolean password_success =  isValidPassword(str1);
			 System.out.println(password_success);
			System.out.println(str1);
			if(phone_success) {
			if (password_success) {
			if (passwordTextField.getValue().equals(repeatTextField.getValue())) {
				CognitoHelper helper = new CognitoHelper();
				boolean success = helper.SignUpUser(userNameTextField.getValue(), passwordTextField.getValue(),
						emailTextField.getValue(), contactNumberTextField.getValue());
				if (success) {
					

					System.out.println("Enter your validation code from email");
					Notification.show("Enter your validation code from email", 3000, Position.BOTTOM_START).addThemeVariants(NotificationVariant.LUMO_CONTRAST);
					updateSelectedDialog(userNameTextField);
					
					// ConfirmUser.display("re:Invent 2017 - Cognito Workshop", "Confirm
					// User",userNameTextField.getValue());
				} else {
					System.out.println("User creation failed");
					// usercreation_message.setText("User creation failed");
				}

			} else {
						Notification.show("Passwords did not match. Please re-enter.", 3000, Position.BOTTOM_START).addThemeVariants(NotificationVariant.LUMO_CONTRAST);
			}
			
			}else {
				Notification.show("Password must contain at least 8 character,one digit,one upper case alphabet,"
						+ "one lower case alphabet,one special character which includes !@#$%&*()-+=^.", 3000, Position.BOTTOM_START).addThemeVariants(NotificationVariant.LUMO_CONTRAST);
			}
			
			}else {
				Notification.show("Mobile number in-correct. Please re-enter.", 3000, Position.BOTTOM_START).addThemeVariants(NotificationVariant.LUMO_CONTRAST);
			}

		});
		
		

	}

	private void updateSelectedDialog(TextField userNameTextField) {
		// TODO Auto-generated method stub
		OtpSignUpView otpsignUpWindow = new OtpSignUpView(userNameTextField.getValue());
//		Dialog equationWindow = new Dialog();
//		equationWindow.add(new Text("Close me with the esc-key or an outside click"));
//
//		equationWindow.setWidth("400px");
//		equationWindow.setHeight("150px");
		otpsignUpWindow.open();

	}
	

	private  boolean isValidPassword(String password) {
		
		String regex = "^(?=.*[0-9])"
                + "(?=.*[a-z])(?=.*[A-Z])"
                + "(?=.*[@#$%^&+=])"
                + "(?=\\S+$).{8,20}$";
		
		   Pattern p = Pattern.compile(regex);
		   
	        // If the password is empty
	        // return false
	        if (password == null) {
	            return false;
	        }
	  
	        // Pattern class contains matcher() method
	        // to find matching between given password
	        // and regular expression.
	        Matcher m = p.matcher(password);
	  
	        // Return if the password
	        // matched the ReGex
	        return m.matches();
			
		
	}
	
	
private  boolean isValidPhoneNumber(String password) {
		
		String regex = "^\\+\\d{1,40}";
		
		   Pattern p = Pattern.compile(regex);
		   
	  
	        if (password == null) {
	            return false;
	        }
	  
	        Matcher m = p.matcher(password);
	  
	        // Return if the password
	        // matched the ReGex
	        return m.matches();
			
		
	}



}

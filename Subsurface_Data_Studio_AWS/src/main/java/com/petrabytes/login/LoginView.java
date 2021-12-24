package com.petrabytes.login;

import org.json.JSONObject;

import com.amazonaws.services.cognitoidentity.model.Credentials;
import com.amazonaws.services.cognitoidp.model.InitiateAuthResult;
import com.bluegridz.msal4j.helpers.IdentityContextAdapterServlet;
import com.bluegridz.msal4j.helpers.IdentityContextData;
import com.microsoft.aad.msal4j.IAccount;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.petrabytes.databricks.DatabricksClusterPopup;
import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.login.*;
import com.petrabytes.ui.utils.PB_Progress_Notification;

@Route(value = "applogin")
//@RouteAlias(value = "")
//@PageTitle("GEOCrust")
//@CssImport("./views/main/login.css")
//@CssImport("./views/main/Dashboard.css")

public class LoginView extends VerticalLayout {

	private FormLayout formLayout = new FormLayout();
	protected Button loginButton;
	protected TextField usernameTextField;
	protected PasswordField passwordField;

	protected Checkbox individualcheck;
	private NativeButton reportLink;
	protected Image logoImage;
	protected VerticalLayout loginFooterLayout;
	CognitoHelper helper = new CognitoHelper();

	public LoginView() {
		setSizeFull();
		setClassName("back_Image");

		loginFormSetUI();

	}

	private void loginFormSetUI() {

		// TODO Auto-generated method stub
		logoImage = new Image("images/Logo_v10.png", "SDS_ App logo");
		logoImage.setWidth("300px");
		//logoImage.setHeight("150px");
		logoImage.getStyle().set("margin-top", "-20%");

		logoImage.setClassName("login-logo");
		VerticalLayout imageLayout = new VerticalLayout(logoImage);
		imageLayout.setAlignSelf(FlexComponent.Alignment.CENTER, logoImage);
		imageLayout.getStyle().set("margin-top", "20%");
		formLayout.add(imageLayout);

		usernameTextField = new TextField("Username");
		usernameTextField.setId("bgz_login_text");

		usernameTextField.setClassName("username-field");

		formLayout.add(usernameTextField);

		passwordField = new PasswordField("Password");
		passwordField.setId("bgz_login_password");

		passwordField.setClassName("password-field");
		formLayout.add(passwordField);

		loginButton = new Button("Login");

		loginButton.setId("bgz_login_button");
		loginButton.setSizeUndefined();
		HorizontalLayout actionLayout = new HorizontalLayout(loginButton);
		formLayout.add(actionLayout);
		actionLayout.setPadding(true);
		actionLayout.setAlignSelf(FlexComponent.Alignment.CENTER, loginButton);

		loginButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
//		loginButton.getStyle().set("margin", "auto");
		loginButton.getStyle().set("width", "250px");
		loginButton.getStyle().set("background-color", "#48278F");
		loginButton.getStyle().set("color", "white");
		actionLayout.setClassName("login-button");

		Div linkerLayout = new Div();
		linkerLayout.getStyle().set("background", "transparent");

		linkerLayout.setClassName("login-content");
		linkerLayout.getStyle().set("alignContent", "start");
		RouterLink singupLink = new RouterLink("Don't have an account? Register", SignUpView.class);
		singupLink.getStyle().set("margin-top", "-10px");
		linkerLayout.add(createContectBlock(singupLink));

		HorizontalLayout linkmainLayout = new HorizontalLayout(linkerLayout);
		linkmainLayout.setWidth("100%");
		linkmainLayout.setPadding(true);
		linkmainLayout.setAlignSelf(FlexComponent.Alignment.CENTER, linkerLayout);
		formLayout.add(linkmainLayout);

		String skygridz_version = Bluegridz_Utilities.getInstance().getCurrentVersion();
		RouterLink releaseNoteLink = new RouterLink();
		releaseNoteLink.add("End User License Agreement (EULA)");
		VaadinRequest vaadinRequest = VaadinService.getCurrentRequest();
		HttpServletRequest httpServletRequest = ((VaadinServletRequest) vaadinRequest).getHttpServletRequest();
		String requestUrl = httpServletRequest.getRequestURL().toString();
		Anchor reportAnchor = new Anchor(requestUrl + "licence", "End User License Agreement (EULA)");
		reportAnchor.getStyle().set("align-self", "center");
		reportAnchor.setTarget("_blank");
		
		loginFooterLayout = new VerticalLayout();
		Label copyright = new Label("© 2022 Petrabytes Corporation.");
		Label copyright1 = new Label("All Rights Reserved. Ver: 2.1 RC1 (" + skygridz_version + ")");
		copyright1.getStyle().set("text-align", "center");
		loginFooterLayout.setAlignSelf(Alignment.CENTER, copyright);
		loginFooterLayout.setAlignSelf(Alignment.CENTER, copyright1);
		loginFooterLayout.setPadding(true);
		loginFooterLayout.add(copyright, copyright1,reportAnchor);

		copyright.getStyle().set("margin-top", "-17px");
		loginFooterLayout.setClassName("footer-layout");
		formLayout.add(loginFooterLayout);

		formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("25em", 1),
				new FormLayout.ResponsiveStep("35em", 1));
		formLayout.setClassName("login-form");
		this.add(formLayout);
		loginButtonAction();
		// awsbypassAndsession();
	}

	public void loginButtonAction() {

		String db_pointer = Bluegridz_Utilities.getInstance().getDatabasePointerProperty();
		String notificationString = PetrahubNotification_Utilities.getInstance().userloginNotification();
		PB_Progress_Notification notification = new PB_Progress_Notification();
		loginButton.addClickListener(event -> {

			if (db_pointer.equals("AWS")) {

				InitiateAuthResult result = helper.siginUSer(usernameTextField.getValue(), passwordField.getValue());
				boolean password_success = isValidPassword(passwordField.getValue());
				if (password_success) {
					if (result != null) {
						System.out.println("User is authenticated:" + result);

						HashMap<String, String> filteredClaims = new HashMap<>();
						filteredClaims.put("userdata", usernameTextField.getValue());

						HttpServletRequest req = (HttpServletRequest) VaadinService.getCurrentRequest();
						req.getSession().setAttribute("claims", filteredClaims);
						VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userdata", filteredClaims);
                        
//						updateSelectedDialog(result, helper, usernameTextField.getValue());
						UI.getCurrent().navigate("dashboard/main");

					} else {
						
						
						
						notification.setText(notificationString);
						notification.open();
						notification.setDuration(3000);


					}

				} else {

				//	PB_Progress_Notification notification = new PB_Progress_Notification();
					
					notification.setText(notificationString);
					notification.open();
					notification.setDuration(3000);

				}
			} else {
				HashMap<String, String> filteredClaims = new HashMap<>();
				filteredClaims.put("userdata", "Localuser");
				HttpServletRequest req = (HttpServletRequest) VaadinService.getCurrentRequest();
				req.getSession().setAttribute("claims", filteredClaims);
				VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userdata", filteredClaims);

				UI.getCurrent().navigate("dashboard/main");
			}

		});

	}

	private void updateSelectedDialog(InitiateAuthResult result, CognitoHelper helper, String userName) {
		// TODO Auto-generated method stub

		Signinview signInWindow = new Signinview(result, helper, userName);

		signInWindow.open();

	}

	public void addBreakLine(int n) {
		for (int i = 0; i < n; i++) {
			getElement().appendChild(ElementFactory.createBr());
		}
	}

	private Div createContectBlock(RouterLink link) {
		final Div d = new Div();
		d.getStyle().set("background", "transparent");
		d.getStyle().set("margin", "5px");
		d.getStyle().set("display", "block ruby");
		d.add(link);
		return d;
	}

	private void reportBugButton() {
//		reportLink.addClickListener(event -> {
//			Sky_report_bug_UI reportBug = new Sky_report_bug_UI();
//
//		});
	}

	private boolean isValidPassword(String password) {

		String regex = "^(?=.*[0-9])" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$).{8,20}$";

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

}

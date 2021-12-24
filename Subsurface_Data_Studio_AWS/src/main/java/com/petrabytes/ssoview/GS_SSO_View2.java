package com.petrabytes.ssoview;

import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.bluegridz.msal4j.helpers.AuthException;
import com.bluegridz.msal4j.helpers.AuthHelper;
import com.bluegridz.msal4j.helpers.IdentityContextAdapterServlet;
import com.bluegridz.msal4j.helpers.IdentityContextData;

import com.microsoft.aad.msal4j.IAccount;
import com.microsoft.aad.msal4j.IAuthenticationResult;
import com.nimbusds.jwt.JWTParser;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;

//@Push()
@Route("auth") // .auth/login/aad/callback ///redirect
public class GS_SSO_View2 extends VerticalLayout implements HasUrlParameter<String> {
	//private Bluegridz_Logger logger = null;
	private String username = null;

	public GS_SSO_View2() {
		// TODO Auto-generated constructor stub
		//logger = Bluegridz_Logger_Factory.getCurrentSessionLogger();
		this.setSizeFull();
	}

	private void getAuth() {
		HttpServletRequest req = (HttpServletRequest) VaadinService.getCurrentRequest();
		HttpServletResponse resp = (HttpServletResponse) VaadinService.getCurrentResponse();
		IdentityContextData context = new IdentityContextAdapterServlet(req, resp).getContext();
		IAccount account = context.getAccount();
		if (account == null) {
			UI.getCurrent().getPage().setLocation("login");
		} else {
			final HashMap<String, String> filteredClaims = filterClaims(context);

			req.getSession().setAttribute("claims", filteredClaims);
		//	String  userSql = filteredClaims.get(username);
//			IBlgz_LoginDB_SQL_Interface loginSqlService = new Blgz_LoginDB_SQL_Services();
//			Blgz_Users_Sql_HB userSql = loginSqlService.getUserData(username);
			if (filteredClaims == null) {
				UI.getCurrent().getPage().setLocation("user");
				return;
			}else
				updateUserData(filteredClaims);
		//	UI.getCurrent().getPage().setLocation("dashboard/main");
//			VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userdata", userSql);
//			UI.getCurrent().getPage().setLocation("dashboard/main");
//				UI.getCurrent().navigate("gsdashboard/main");
			//	UI.getCurrent().getPage().setLocation("gsdashboard/main");
				
		}

	}
	

	private void updateUserData(HashMap<String, String> filteredClaims) {
		// TODO Auto-generated method stub
//		IBlgz_LoginDB_SQL_Interface loginSqlService = new Blgz_LoginDB_SQL_Services();
//		Blgz_Company_Sql_HB company = loginSqlService.getCompanyByID1(userSql.getCompanyid());
//
		HashMap<String, Object> userDataList = new HashMap<String, Object>();
		//userDataList.put("userID", filteredClaims.get(getId()));
		userDataList.put("userName", filteredClaims.get(username));
		//userDataList.put("role", filteredClaims.getRoleid().getRoletype());
//		userDataList.put("companyID", company.getId());
//		userDataList.put("companyName", company.getName());
		//userDataList.put("userIDst", userSql.getUserID());
		VaadinService.getCurrentRequest().getWrappedSession().setAttribute("userdata",filteredClaims );
		UI.getCurrent().getPage().setLocation("dashboard/main");
		
//		UI.getCurrent().navigate("gsdashboard/main");
	//	UI.getCurrent().getPage().setLocation("gsdashboard/main");
		//UI.getCurrent().getPage().setLocation("dashboard/main");
	}

	@Override
	public void setParameter(BeforeEvent event, String parameter) {
		// TODO Auto-generated method stub

		HttpServletRequest req = (HttpServletRequest) VaadinService.getCurrentRequest();
		HttpServletResponse resp = (HttpServletResponse) VaadinService.getCurrentResponse();

		try {
			AuthHelper.processAADCallback(new IdentityContextAdapterServlet(req, resp));
			getAuth();
		} catch (AuthException ex) {

		}

		try {

			HttpServletRequest httpServletRequest = ((VaadinServletRequest) VaadinRequest.getCurrent())
					.getHttpServletRequest();
			String requestUrl = httpServletRequest.getRequestURL().toString();

			getAuth();

		} catch (Exception ex) {
			// TODO: handle exception
			ex.printStackTrace();
		}

	}

	private HashMap<String, String> filterClaims(IdentityContextData context) {
		final String[] claimKeys = { "sub", "aud", "ver", "iss", "name", "oid", "preferred_username", "nonce", "tid",
				"roles", "groups", "_claim_names", "_claim_sources" };
		final List<String> includeClaims = Arrays.asList(claimKeys);

		HashMap<String, String> filteredClaims = new HashMap<>();
		context.getIdTokenClaims().forEach((k, v) -> {
			if (k.equals("name"))
				username = v.toString();
			if (includeClaims.contains(k))
				filteredClaims.put("userdata", v.toString());
		});
		return filteredClaims;
	}

}

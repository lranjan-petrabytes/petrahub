package com.petrabytes.login;

import java.io.File;

import com.bluegridz_logger.util.utility.Bluegridz_Utilities;
import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;

public class About_UI_Dialog extends PetrabyteUI_Dialog{
	private Image geosmartImage ;
	private Label version_label;
	private String bluegridz_version = Bluegridz_Utilities.getInstance().getBluegridz_version();
	private Label version_text;
	
	public About_UI_Dialog() {

		
		setUI();

	}
	
	private void setUI() {
		this.setTitle("About Petrahub");
		geosmartImage = new Image("images/Logo_v10.png", "Petrahub logo");
		geosmartImage.setWidth("300px");
		geosmartImage.setHeight("150px");
		version_label = new Label();	
		version_label.getStyle().set("font-size", "large");
		version_label.setText("Version: " + bluegridz_version);
//		version_text = new Label(bluegridz_version);
//		HorizontalLayout versionLayout = new HorizontalLayout(version_label,version_text);
		VerticalLayout layout = new VerticalLayout(geosmartImage,version_label);
		layout.setHeight("200px");
		layout.setWidth("400px");
		layout.setAlignItems(Alignment.CENTER);
		layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

		
		this.content.add(layout);
		this.saveButton.setVisible(false);
		this.closeButton.setVisible(false);
	}
}

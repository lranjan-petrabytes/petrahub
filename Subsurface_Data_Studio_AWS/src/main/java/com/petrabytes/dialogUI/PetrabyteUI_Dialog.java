package com.petrabytes.dialogUI;

import javax.crypto.SecretKey;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class PetrabyteUI_Dialog extends Dialog {

	protected VerticalLayout mainLayout = new VerticalLayout();
	protected VerticalLayout content = new VerticalLayout();
	protected Image titleImage = new Image();
	protected Label titleLabel = null;
	protected Label messageLabel = null;
	protected Button saveButton = new Button("Save");
	protected Button closeButton = new Button("Cancel");
	protected Button close = new Button();
	protected FlexLayout subfooterLayout = new FlexLayout();
	protected FlexLayout customLayout = new FlexLayout();
	
	public PetrabyteUI_Dialog() {

		// TODO Auto-generated constructor stub
		setUI();
		closeButtonlistner();
//	padding-right: 10px;
	}

	private void setUI() {
		this.setCloseOnOutsideClick(false);
		getElement().getStyle().set("padding", "0");
		titleImage.getElement().getStyle().set("padding-left", "10px");
		titleImage.getElement().getStyle().set("padding-right", "9px");
		mainLayout.setSpacing(false);
		mainLayout.setPadding(false);
		content.setSpacing(false);
		content.setPadding(false);
		content.setSizeFull();
	
//	this.setDraggable(true);
//  this.setResizable(true);
		header();
		mainLayout.add(content);
		mainLayout.expand(content);
		mainLayout.setHeightFull();
		mainLayout.setWidthFull();
		footer();
		add(mainLayout);

	}

	private void header() {
		// TODO Auto-generated method stub
		titleLabel = new Label();
		titleLabel.getElement().getStyle().set("margin", "auto 0");
		titleLabel.getElement().getStyle().set("font-size", "1.3em");

		// font-weight: bold;

		close = new Button();
		close.getElement().getStyle().set("min-width", " 40px");
		close.getElement().getStyle().set("color", "#48278F ");
		close.setIcon(VaadinIcon.CLOSE.create());
		close.addClickListener(buttonClickEvent -> close());
		HorizontalLayout headerLayout = new HorizontalLayout();
		headerLayout.getStyle().set("background-color", "#F3F5F7");
		headerLayout.setWidthFull();
		 headerLayout.add(titleImage, titleLabel, close);
	//	headerLayout.add(titleImage, titleLabel);
		// headerLayout.setPadding(false);
		headerLayout.setFlexGrow(1, titleLabel);
		headerLayout.setAlignItems(FlexComponent.Alignment.CENTER);
		mainLayout.add(headerLayout);
	}

	private void footer() {
		// TODO Auto-generated method stub
		HorizontalLayout footerLayout = new HorizontalLayout();
		footerLayout.setWidthFull();
		FlexLayout wrapper = new FlexLayout();
		HorizontalLayout layout = new HorizontalLayout();
		layout.setPadding(false);
		layout.setSpacing(false);
		layout.add(subfooterLayout);
//	saveButton.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
//	saveButton.getStyle().set("background-color", "#48278F");
//	saveButton.getStyle().set("color", "white");
		saveButton.addThemeVariants(ButtonVariant.LUMO_SMALL,ButtonVariant.LUMO_PRIMARY);
//	layout.add(saveButton);
		Button resetButton = new Button("Reset");
//	resetButton.getStyle().set("background-color", "#48278F");
//	resetButton.getStyle().set("color", "white");
		resetButton.addThemeVariants(ButtonVariant.LUMO_SMALL,ButtonVariant.LUMO_PRIMARY);
//	resetButton.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);
//	wrapper.add(resetButton);
		closeButton = new Button("Cancel", event -> close());
//	closeButton.getStyle().set("background-color", "#48278F");
//	closeButton.getStyle().set("color", "white");
	//	closeButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		layout.add(closeButton, saveButton);
	closeButton.addThemeVariants(ButtonVariant.LUMO_SMALL,ButtonVariant.LUMO_TERTIARY);
	
	footerLayout.getStyle().set("background-color", "#F3F5F7");
//	footerLayout.getStyle().set("padding-top", "10px");

		wrapper.add(layout);
		wrapper.setJustifyContentMode(FlexComponent.JustifyContentMode.END);
		messageLabel = new Label();
		footerLayout.add(customLayout,messageLabel, wrapper);
		footerLayout.setFlexGrow(1, messageLabel);
		mainLayout.add(footerLayout);
	}

	protected void setTitle(String title) {
		titleLabel.setText(title);
	}

	protected void SetMessage(String message) {
		messageLabel.setText(message);
	}

	public void setImage(String imagePath) {
		titleImage.setSrc(imagePath);
	}

	protected void setButtonName(String name) {
		saveButton.setText(name);

	}

	protected void setCloseButtonName(String name) {
		closeButton.setText(name);

	}
	
	private void closeButtonlistner() {
		this.closeButton.addClickListener(event->{
			this.close();
			
		});
	}

}

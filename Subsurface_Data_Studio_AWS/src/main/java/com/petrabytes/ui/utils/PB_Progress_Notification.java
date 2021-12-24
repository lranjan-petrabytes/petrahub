package com.petrabytes.ui.utils;

import java.io.File;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class PB_Progress_Notification extends Notification {

	private HorizontalLayout layout = new HorizontalLayout();
	private Text text = new Text("");
	private Image image = new Image();

	public PB_Progress_Notification() {
		// TODO Auto-generated constructor stub
		_setUI();
	}

	private void _setUI() {
		// TODO Auto-generated method stub
//		layout.getElement().getStyle().set("padding", "10px");
		this.getElement().setAttribute("padding", "4px");
		this.getElement().setProperty("padding", "4px");
		layout.setPadding(true);
		layout.setSpacing(true);
		image.getStyle().set("padding-right", "10px");
		layout.add(image, text);
		this.add(layout);
		this.addThemeVariants(NotificationVariant.LUMO_CONTRAST);

	}

	public void setText(String txt) {
		/**
		 * TODO: we need to fix this, temporary fix Set some space between Icon and
		 * Text.
		 */
		text.setText(" " + txt);
	}
	
	public void setTextspace(String txt) {
		/**
		 * TODO: we need to fix this, temporary fix Set some space between Icon and
		 * Text.
		 */
		text.setText(" " + txt);
	}

	/**
	 * static image apply
	 */
	public void setImage(String imagest) {
		image.setSrc("images" + File.separator + imagest + ".png");
		

	}
}

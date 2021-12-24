package com.petrabytes.config;

import java.io.File;

import com.petrabytes.databricks.DatabricksClusterPopupInfo;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

public class Storage_view extends VerticalLayout{
	private Grid<Storage_Info_view>storageGrid = new Grid<>();
	
	private Button storageaddbutton = new Button();
	private Button storageeditButton = new Button();
	private Button storagedeleteButton = new Button();

public Storage_view() {
		
		setui();
		storageaddbutton.addClickListener(event -> {
			try {

				savebuttonpopup();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		
		storageeditButton.addClickListener(event -> {
			try {

				editbuttonpopup();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});

		
	}

private void setui() {
	// TODO Auto-generated method stub
	Image storageaddbuttonImage = new Image("icons" + File.separator + "16x" + File.separator + "add-16x.png",
			"Add Storage");
	storageaddbutton.getElement().setAttribute("tiDtle", "Add Storage");
	storageaddbutton.setIcon(storageaddbuttonImage);

	Image storageeditButtonImage = new Image("icons" + File.separator + "16x" + File.separator + "edit_16.png",
			"Edit Storage");
	storageeditButton.getElement().setAttribute("title", "Edit Storage");
	storageeditButton.setIcon(storageeditButtonImage);

	Image storagedeleteButtonImage1 = new Image("icons" + File.separator + "16x" + File.separator + "delete16.png",
			"Delete Storage");
	storagedeleteButton.getElement().setAttribute("title", "Delete Storage");
	storagedeleteButton.setIcon(storagedeleteButtonImage1);
	 HorizontalLayout ButtonLayout = new HorizontalLayout();
	 ButtonLayout.add(storageaddbutton,storageeditButton,storagedeleteButton);
	 ButtonLayout.getStyle().set("margin-top", "-20px"); 
	 add(ButtonLayout);
	 
	 storageGrid.setSelectionMode(SelectionMode.SINGLE);
	 storageGrid.addColumn(Storage_Info_view::getStorageName).setHeader("Storage Name").setAutoWidth(true);
	 storageGrid.addColumn(Storage_Info_view::getAccessKey).setHeader("Access Key/ Account Name").setAutoWidth(true);
	 storageGrid.addColumn(Storage_Info_view::getScerectKey).setHeader("Secret Key/ Account Key").setAutoWidth(true);
	 storageGrid.addColumn(Storage_Info_view::getBucketName).setHeader("Bucket Name/ Container Name").setAutoWidth(true);
	 storageGrid.addColumn(Storage_Info_view::getCloudProvider).setHeader("Cloud Provider").setAutoWidth(true);
	 
	 storageGrid.addThemeVariants(GridVariant.LUMO_COLUMN_BORDERS);
	 storageGrid.setHeightByRows(true);
	 storageGrid.setWidth("1400px");
	 add(storageGrid);
}
private void savebuttonpopup()  throws Exception {

	Storage_Dialog_View  equationWindow = new Storage_Dialog_View(false);

		equationWindow.open();

	}

private void editbuttonpopup()  throws Exception {

	Storage_Dialog_View  equationWindow = new Storage_Dialog_View(true);

		equationWindow.open();

	}
}


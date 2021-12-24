package com.petrabytes.views.well_Information;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.upload.MultiFileReceiver;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;


public class Data_Upload_View extends VerticalLayout {

	private HorizontalLayout mainLayout = new HorizontalLayout();
	private HorizontalLayout mainActionLayout;
	private Grid<Data_Upload_Info> fileUploadGrid = new Grid<>();
//	private Blgz_Logs_Service_Util<Data_Upload_Info> logsUploadService = new Blgz_Logs_Service_Util<>();
//	private Blgz_Logs_Service_Util<Data_Upload_Info> scanDataService = new Blgz_Logs_Service_Util<>();

	private Upload upload;
	private File selectedFilePath;
	private Button readButton;
	private String fileName;
	String companyName = null;
	String userName = null;

	private Random uniqueID = new Random();

	private Binder<Data_Upload_Info> binder = new Binder<>();


	public Data_Upload_View() {


		setUI();
		newFileBinder();
	}

	private void setUI() {
		// TODO Auto-generated method stub
		this.setSizeFull();
		this.setMargin(false);
		this.setPadding(false);
		mainLayout.setSizeFull();
		VerticalLayout rawgridLayoutAction = new VerticalLayout();
		rawgridLayoutAction.setMargin(false);
		rawgridLayoutAction.getElement().getStyle().set("border", "solid");
		rawgridLayoutAction.getElement().getStyle().set("border-color", "#D1D1D1");
		rawgridLayoutAction.setHeight("450px");
//		rawgridLayoutAction.setWidth("900px");
//		rawgridLayoutAction.setHeight("600px");
//		rawgridLayoutAction.setSizeFull();

		Label label = new Label("Raw Files");

		fileUploadGrid.setSizeFull();
		fileUploadGrid.setSelectionMode(SelectionMode.MULTI);
		fileUploadGrid.addColumn(Data_Upload_Info::getInput).setHeader("Input");
		fileUploadGrid.addColumn(Data_Upload_Info::getFileExtension).setHeader("File extension");
		
		rawgridLayoutAction.add(label, fileUploadGrid);
		rawgridLayoutAction.expand(fileUploadGrid);

		fileUploadGrid.setClassName("skygridz-grid");

		VerticalLayout uploadLayout = new VerticalLayout();

		uploadLayout.setMargin(false);

		uploadLayout.getElement().getStyle().set("padding-top", "30px");
		uploadLayout.getElement().getStyle().set("border", "solid");
		uploadLayout.getElement().getStyle().set("border-color", "#D1D1D1");
		uploadLayout.setHeight("450px");
	
		FlexLayout flex = new FlexLayout();
		uploadLayout.add();
		readButton = new Button("Read Files");
		readButton.setId("bgz_logs_readButton");
		readButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
		mainActionLayout = new HorizontalLayout();
		mainActionLayout.add(readButton);

		mainLayout.add(rawgridLayoutAction, uploadLayout);
		add(mainLayout, mainActionLayout);
		expand(mainLayout);

		setAlignSelf(Alignment.CENTER, rawgridLayoutAction);
		setAlignSelf(Alignment.CENTER, uploadLayout);
		setAlignSelf(Alignment.BASELINE, rawgridLayoutAction);

	}

	private void newFileBinder() {
		// TODO Auto-generated method stub

	}

	

}

package com.petrabytes.views.wellbore;

import java.awt.Color;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.vaadin.flow.data.binder.ValidationException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.atmosphere.interceptor.AtmosphereResourceStateRecovery.B;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.provider.ListDataProvider;
//import com.github.juchar.colorpicker.ColorPickerField;
//import com.github.juchar.colorpicker.ColorPickerFieldRaw;

public class Blgz_Formation_Window extends PetrabyteUI_Dialog {

	private VerticalLayout mainLayout = new VerticalLayout();

	private TextField formationNameTextField;
	private ComboBox<String> rockTypeComboBox;
	private TextField topMDTextField;
	private TextField bottomMDTextField;
	private Label colorlabel;
//	private ColorPickerField colorPickerField;
	private boolean _editFlag;
	private String[] currentUnits;
	private Blgz_formation_Info _formationInfo;

	ValueChangeListener<ValueChangeEvent<?>> topMDValueChangeListener = null;
	ValueChangeListener<ValueChangeEvent<?>> bottomMDValueChangeListener = null;

	private Binder<Blgz_formation_Info> binder = new Binder<>();

	private Grid<Blgz_formation_Info> _formationGrid;

	public Blgz_Formation_Window(Grid<Blgz_formation_Info> formationGrid, boolean editFlag, String[] units) {
		

		
		_formationGrid = formationGrid;
		this._editFlag = editFlag;
		this.currentUnits = units;

		if (_editFlag == true) {
			this.setTitle("Edit Formation");
			this.setImage("icons" + File.separator + "purple-24x" + File.separator + "formation24.png");
			setUI();
			updateFormationDataUI();
		} else {
			this.setTitle("New Formation");
			this.setImage("icons" + File.separator + "purple-24x" + File.separator + "formation24.png");
			setUI();

			topMDTextBoxAction();
			topMDTextField.addValueChangeListener(topMDValueChangeListener);
			bottomMDTextBoxAction();
			bottomMDTextField.addValueChangeListener(bottomMDValueChangeListener);

		}

		this.getElement().getStyle().set("padding", "0 0");
		

		this.content.add(mainLayout);
		mainLayout.setWidth("400px");
//		mainLayout.setHeight("350px");
		mainLayout.setSpacing(false);

		formationBinder();
		saveNewFormationButtonClickAction();
	}

	private void setUI() {
		// TODO Auto-generated method stub
//		createHeader();

		FormLayout formLayout = new FormLayout();

		formationNameTextField = new TextField();
		formationNameTextField.setId("bgz_well_formationName");
		formLayout.addFormItem(formationNameTextField, "Formation Name");

		rockTypeComboBox = new ComboBox<String>();
		rockTypeComboBox.setId("bgz_well_rockType");
		formLayout.addFormItem(rockTypeComboBox, "Rock Type");
		String[] rockTypeItems = { "Anhydrite", "Basalt", "Chalk", "Claystone", "Dolomite", "Granite", "Gypsum",
				"Halite", "Limestone", "Marl", "Sandstone", "Shale", "Siltstone" };
		rockTypeComboBox.setItems(rockTypeItems);

		topMDTextField = new TextField();
		topMDTextField.setId("bgz_well_FormationtopMD");

		formLayout.addFormItem(topMDTextField, "Top MD(" + currentUnits[0] + ")");

		bottomMDTextField = new TextField();
		bottomMDTextField.setId("bgz_well_FormationbottomMD");

		formLayout.addFormItem(bottomMDTextField, "Bottom MD(" + currentUnits[0] + ")");

		// color picker code by Moumita
//		final ColorPickerField colorPickerField = new ColorPickerField("As Color", Color.BLUE, "#fff");
//		colorPickerField.setPinnedPalettes(true);
//		colorPickerField.setHexEnabled(false);
//		colorPickerField.setPalette(Color.RED, Color.GREEN, Color.BLUE);
//		colorPickerField.getTextField().addValueChangeListener(event -> System.out.println(event.getValue()));
//		colorPickerField.setChangeFormatButtonVisible(true);
//		colorPickerField.setWidth("400px");
//		formLayout.add(colorPickerField);
//		formLayout.addFormItem(colorPickerField, "Color");
//		Button ColorButton = new Button("color", event -> {
//			Color_test cp = new Color_test();
//			cp.open();
//		});

		formLayout.setResponsiveSteps(new ResponsiveStep("10em", 1), new ResponsiveStep("30em", 2));

		mainLayout.add(formLayout);
		mainLayout.expand(formLayout);
//		add(mainLayout);
	}

	private void createHeader() {
		// TODO Auto-generated method stub
		Label TitleLabel = new Label();
		TitleLabel.setId("bgz_well_TitleLabel");
		TitleLabel.add("New Formation");
		TitleLabel.setClassName("formation_window");
		TitleLabel.getElement().getStyle().set("color", "#6200ee");
		Button close = new Button();
		close.setId("bgz_well_closeButton");
		close.setIcon(VaadinIcon.CLOSE.create());
		close.addClickListener(buttonClickEvent -> close());

		HorizontalLayout header = new HorizontalLayout();
		header.add(TitleLabel, close);
		header.setPadding(false);
		header.setFlexGrow(1, TitleLabel);
		header.setAlignItems(FlexComponent.Alignment.CENTER);
		add(header);
		// vaadin-form-item-label-width: 8em;
	}

	private void bottomMDTextBoxAction() {
		// TODO Auto-generated method stub
		bottomMDValueChangeListener = new ValueChangeListener<ValueChangeEvent<?>>() {

			@Override
			public void valueChanged(ValueChangeEvent<?> event) {
				// TODO Auto-generated method stub
				try {
					String topStg = topMDTextField.getValue().toString();
					if (!topStg.isEmpty()) {
						String bottomStg = bottomMDTextField.getValue().toString();
						if (!bottomStg.isEmpty()) {
							double depthValue = Double.valueOf(topStg.trim());
							double endDepthValue = Double.valueOf(bottomStg.trim());
							if (endDepthValue < depthValue) {
								Notification.show("Value should be greater than " + topStg.toString());
								bottomMDTextField.clear();
								bottomMDTextField.addValueChangeListener(bottomMDValueChangeListener);
							}
						}
					} else {
						Notification.show("Enter top value");
						bottomMDTextField.clear();
						bottomMDTextField.addValueChangeListener(bottomMDValueChangeListener);
					}
				} catch (NumberFormatException e) {
					e.printStackTrace();
					Notification.show("Please enter numeric value.");
					bottomMDTextField.clear();
					bottomMDTextField.addValueChangeListener(bottomMDValueChangeListener);
				}

			}

		};

	}

	private void topMDTextBoxAction() {
		// TODO Auto-generated method stub
		topMDValueChangeListener = new ValueChangeListener<ValueChangeEvent<?>>() {

			@Override
			public void valueChanged(ValueChangeEvent<?> event) {
				// TODO Auto-generated method stub
				try {
					Blgz_formation_Info lastRow = null;
					ListDataProvider<Blgz_formation_Info> dataProvider = (ListDataProvider<Blgz_formation_Info>) _formationGrid
							.getDataProvider();
					List<Blgz_formation_Info> ItemsList = (List<Blgz_formation_Info>) dataProvider.getItems();
					if (!ItemsList.isEmpty()) {

						lastRow = ItemsList.get(ItemsList.size() - 1);

						Object bottomMD_Obj = lastRow.getBottomMD().toString();
						if (bottomMD_Obj != null) {
							double bottomMD_Value = Double.valueOf(bottomMD_Obj.toString().trim());
							double toMDValue = Double.valueOf(topMDTextField.getValue().toString().trim());
							if (toMDValue < bottomMD_Value) {
								Notification.show("Value should be greater than " + bottomMD_Obj.toString());
//									topMDTextField.removeValueChangeListener(topMDValueChangeListener);
								topMDTextField.clear();
								topMDTextField.addValueChangeListener(topMDValueChangeListener);
							}
						}
					}

				} catch (NumberFormatException e) {
					// telemetry.trackException(e);
					e.printStackTrace();
//					Notification.show("Please enter numeric value.");
//					topMDTextField.removeValueChangeListener(topMDValueChangeListener);
					topMDTextField.clear();
					topMDTextField.addValueChangeListener(topMDValueChangeListener);
//					logger.addLog(ExceptionUtils.getStackTrace(e), "ERROR");
				}

			}
		};

	}

	private void formationBinder() {
		// TODO Auto-generated method stub
		binder.forField(formationNameTextField).bind(Blgz_formation_Info::getFormationName,
				Blgz_formation_Info::setFormationName);
		binder.forField(rockTypeComboBox).asRequired("Formation name should not null")
				.bind(Blgz_formation_Info::getRockType, Blgz_formation_Info::setRockType);
		binder.forField(topMDTextField).asRequired("Enter Top md value").bind(Blgz_formation_Info::getTopMD,
				Blgz_formation_Info::setTopMD);
		binder.forField(bottomMDTextField).asRequired("Enter Bottom MD value").bind(Blgz_formation_Info::getBottomMD,
				Blgz_formation_Info::setBottomMD);

	}

	private void saveNewFormationButtonClickAction() {
		// TODO Auto-generated method stub
		this.saveButton.addClickListener(event -> {
			Blgz_formation_Info formationInfo = new Blgz_formation_Info();
			if (formationNameTextField.isEmpty() || rockTypeComboBox.isEmpty() || topMDTextField.isEmpty()
					|| bottomMDTextField.isEmpty()) {
				Notification.show("Please fill all the fields");

			} else {
				if (_editFlag == false) {
					try {
						binder.writeBean(formationInfo);
						addAllFormation(formationInfo);
						close();

					} catch (ValidationException e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				} else {
					try {
						binder.writeBean(formationInfo);
					} catch (ValidationException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					ListDataProvider<Blgz_formation_Info> formationAllDatalist = (ListDataProvider<Blgz_formation_Info>) _formationGrid
							.getDataProvider();

					// Clear all the rows

					List<Blgz_formation_Info> rowList = null;
					if (!formationAllDatalist.getItems().isEmpty()) {
						rowList = (List<Blgz_formation_Info>) formationAllDatalist.getItems();
					} else {
						rowList = new ArrayList<>();
					}

					Blgz_formation_Info selectedFormationValue = _formationGrid.asSingleSelect().getValue();

					List<Blgz_formation_Info> updatedList = new ArrayList<>();
					for (Blgz_formation_Info eachFormation : rowList) {

						if (eachFormation.getFormationName().equals(selectedFormationValue.getFormationName())) {
							updatedList.add(formationInfo);
						} else {
							updatedList.add(eachFormation);
						}

					}
					_formationGrid.setItems(updatedList);
					close();
				}
			}
		});
	}

	private void updateFormationDataUI() {
		// TODO Auto-generated method stub
		if (_editFlag) {
			Blgz_formation_Info formationInfo = _formationGrid.asSingleSelect().getValue();
			if (formationInfo != null) {

				formationNameTextField.setValue(formationInfo.getFormationName());

				rockTypeComboBox.setValue(formationInfo.getRockType());

				topMDTextField.setValue(formationInfo.getTopMD());

				bottomMDTextField.setValue(formationInfo.getBottomMD());

				// colorTextBox.setValue(formationInfo.getColorPick());;

			}

		}

	}
	public void addAllFormation(Blgz_formation_Info formationInfo) {
		_formationInfo = formationInfo;

		ListDataProvider<Blgz_formation_Info> list = (ListDataProvider<Blgz_formation_Info>) _formationGrid
				.getDataProvider();
		List<Blgz_formation_Info> rowList = null;
		if (!list.getItems().isEmpty()) {
			rowList = (List<Blgz_formation_Info>) list.getItems();
		} else {
			rowList = new ArrayList<>();
		}
		rowList.add(formationInfo);
		_formationGrid.setItems(rowList);

	}

}

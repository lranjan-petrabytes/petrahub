package com.petrabytes.views.wellbore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.ListDataProvider;

public class Blgz_Deviation_Window extends PetrabyteUI_Dialog {
	
	private Grid<Blgz_Deviation_Info> _deviationGrid;
	private boolean _editFlag;
	private Binder<Blgz_Deviation_Info> binder = new Binder<>();

	private VerticalLayout mainLayout = new VerticalLayout();
	private TextField mDTextBoxTextField;
	private TextField iNCTextField;
	private TextField aZMTextField;
	
	public Blgz_Deviation_Window(Grid<Blgz_Deviation_Info> deviationGrid, boolean editFlag) {
		

		
		this._deviationGrid = deviationGrid;
		this._editFlag = editFlag;
		
		if (_editFlag == true) {
			this.setTitle("Edit Deviation Survey");
			this.setImage("icons" + File.separator + "purple-24x" + File.separator + "well_deviation_24.png");
			
		} else {
			
			this.setTitle("New Deviation Survey");
			this.setImage("icons" + File.separator + "purple-24x" + File.separator + "well_deviation_24.png");
		}
		
        this.getElement().getStyle().set("padding", "0 0");
	
		this.content.add(mainLayout);
		mainLayout.setWidth("400px");
		setUI();
		updateDeviationDataUI();
		deviationBinder();
		saveDeviationOkAction();
	}

	

	private void setUI() {
		// TODO Auto-generated method stub
		
		FormLayout formLayout = new FormLayout();
		
		
		mDTextBoxTextField = new TextField();
		mDTextBoxTextField.setId("bgz_well_mDTextBox");
		formLayout.addFormItem(mDTextBoxTextField, "MD(m)");
		mDTextBoxTextField.getElement().getStyle().set("margin-left", "-20px");
		
		
		iNCTextField = new TextField();
		iNCTextField.setId("bgz_well_iNCTextField");
		formLayout.addFormItem(iNCTextField, "INC(dega)");
		iNCTextField.getElement().getStyle().set("margin-left", "-20px");
		
		
		aZMTextField = new TextField();
		aZMTextField.setId("bgz_well_aZMTextField");
		formLayout.addFormItem(aZMTextField, "AZM(dega)");
		aZMTextField.getElement().getStyle().set("margin-left", "-20px");
		
		formLayout.setResponsiveSteps(new ResponsiveStep("10em", 1), new ResponsiveStep("30em", 2));
		

		 mainLayout.add(formLayout);
		 mainLayout.expand(formLayout);	
		
		
		
		
	}

	private void deviationBinder() {
		// TODO Auto-generated method stub
		
		binder.forField(mDTextBoxTextField).bind(Blgz_Deviation_Info::getmD,Blgz_Deviation_Info::setmD);
		binder.forField(iNCTextField).bind(Blgz_Deviation_Info::getiNc, Blgz_Deviation_Info::setiNc);
		binder.forField(aZMTextField).bind(Blgz_Deviation_Info::getaZM, Blgz_Deviation_Info::setaZM);

	}
	
	private void saveDeviationOkAction() {
		// TODO Auto-generated method stub
		
			// TODO Auto-generated method stub
			this.saveButton.addClickListener(event -> {
			Blgz_Deviation_Info deviationInfo = new Blgz_Deviation_Info();
			if (_editFlag == false) {

				try {
					binder.writeBean( deviationInfo);
					addAllDeviation( deviationInfo);
					close();

				} catch (ValidationException e) {
					e.printStackTrace();
				}
				/**
				 * Casing Window editing
				 */
			} 
			
			 else {

					try {
						binder.writeBean(deviationInfo);
					} catch (ValidationException e) {
						e.printStackTrace();
					}
//						
						ListDataProvider<Blgz_Deviation_Info> dataProviderList = (ListDataProvider<Blgz_Deviation_Info>) _deviationGrid
								.getDataProvider();
						
						
						List<Blgz_Deviation_Info> rowList = null;
						if (!dataProviderList.getItems().isEmpty()) {
							rowList = (List<Blgz_Deviation_Info>) dataProviderList.getItems();
						} else {
							rowList = new ArrayList<>();
						}

						Blgz_Deviation_Info selectedCAsingValue = _deviationGrid.asSingleSelect().getValue();

						List<Blgz_Deviation_Info> updatedList = new ArrayList<>();
						for (Blgz_Deviation_Info eachFormation : rowList) {

							if (eachFormation.getmD() == selectedCAsingValue.getmD()) {
								updatedList.add(deviationInfo);
							} else {
								updatedList.add(eachFormation);
							}

						}
						
						
						_deviationGrid.setItems(updatedList);

						close();

				}
		          

			});

	}
	
	public void addAllDeviation(Blgz_Deviation_Info deviationInfo) {
		ListDataProvider<Blgz_Deviation_Info> list = (ListDataProvider<Blgz_Deviation_Info>) _deviationGrid.getDataProvider();
		List<Blgz_Deviation_Info> rowList = null;
		if (!list.getItems().isEmpty()) {
			rowList = (List<Blgz_Deviation_Info>) list.getItems();
		} else {
			rowList = new ArrayList<>();
		}
		rowList.add(deviationInfo);
		_deviationGrid.setItems(rowList);
	

	}
	
	private void updateDeviationDataUI() {
		// TODO Auto-generated method stub
		mDTextBoxTextField.clear();
		iNCTextField.clear();
		aZMTextField.clear();
		
		if (_editFlag) {
			Blgz_Deviation_Info deviationInfo = _deviationGrid.asSingleSelect().getValue();
			if (deviationInfo != null) {

				mDTextBoxTextField.setValue(deviationInfo.getmD());
				
				iNCTextField.setValue(deviationInfo.getiNc());
				
				aZMTextField.setValue(deviationInfo.getaZM());
				
				
			}

		}

	}

}

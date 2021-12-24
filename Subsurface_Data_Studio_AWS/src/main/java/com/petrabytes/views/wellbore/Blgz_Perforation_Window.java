package com.petrabytes.views.wellbore;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
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
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.provider.ListDataProvider;

public class Blgz_Perforation_Window extends  PetrabyteUI_Dialog{
	
	private VerticalLayout mainLayout = new VerticalLayout();
	private Grid<Blgz_Perforation_Info> _perforationgrid;
	
	ValueChangeListener<ValueChangeEvent<?>> topMDValueChangeListener = null;
	ValueChangeListener<ValueChangeEvent<?>> bottomMDValueChangeListener = null;
	
	private TextField perforationnameTextField;
	private TextField topMDTextField;
	private TextField bottomMDTextField;
	private boolean _editFlag;
	private String[] currentUnits ;
//	private Bluegridz_Logger logger = null;

	private Binder<Blgz_Perforation_Info> binder = new Binder<>();

	
	public Blgz_Perforation_Window(Grid<Blgz_Perforation_Info> perforationgrid,boolean editFlag,String[] units) {
		
//		logger = Bluegridz_Logger_Factory.getCurrentSessionLogger();

		_perforationgrid = perforationgrid;
		this._editFlag = editFlag;
		this.currentUnits = units;
		if (_editFlag == true) {
			this.setTitle("Edit Perforation");
			this.setImage("icons" + File.separator + "purple-24x" + File.separator + "perforation24.png");
			setUI();
			updatePerformationDataUI();

		} else {
			this.setTitle("New Perforation");
			this.setImage("icons" + File.separator + "purple-24x" + File.separator + "perforation24.png");
			setUI();
			topMDTextBoxAction();
			topMDTextField.addValueChangeListener(topMDValueChangeListener);
			bottomMDTextBoxAction();
			bottomMDTextField.addValueChangeListener(bottomMDValueChangeListener);

		}
		
        this.getElement().getStyle().set("padding", "0 0");
	
		this.content.add(mainLayout);
		
		mainLayout.setWidth("350px");
		mainLayout.setHeight("250px");
		
		
		newPerforationBinding();
		saveNewPerforationButtonClickAction();
	}




	private void setUI() {
		// TODO Auto-generated method stub
//		createHeader();
		FormLayout formLayout = new FormLayout();
		
		
		perforationnameTextField = new TextField();
		perforationnameTextField.setId("bgz_well_perforationname");
		formLayout.addFormItem(perforationnameTextField, "Perforation Name");
		
		
		topMDTextField = new TextField();
		topMDTextField.setId("bgz_well_topMD");
		formLayout.addFormItem(topMDTextField, "Top MD(" + currentUnits[0] + ")");
		
		
		bottomMDTextField = new TextField();
		bottomMDTextField.setId("bgz_well_bottomMD");
		formLayout.addFormItem(bottomMDTextField, "Bottom MD(" + currentUnits[0] + ")");
	
		
		formLayout.setResponsiveSteps(new ResponsiveStep("10em", 1), new ResponsiveStep("30em", 2));
		HorizontalLayout footerLayout=new HorizontalLayout();
		footerLayout.add(saveButton,closeButton);
		footerLayout.getStyle().set("margin-left", "50%");
		add(footerLayout);

	   mainLayout.add(formLayout,footerLayout);
	   mainLayout.expand(formLayout);

	 
	}
	
	private void createHeader() {
		// TODO Auto-generated method stub
		Label titleLabel = new Label();
		titleLabel.setId("bgz_well_titleLabel");
		titleLabel.add("New Perforation");
		titleLabel.setClassName("perforation_window");
		titleLabel.getElement().getStyle().set("color", "#6200ee");
		Button close = new Button();
		close.setIcon(VaadinIcon.CLOSE.create());
		close.addClickListener(buttonClickEvent -> close());

		HorizontalLayout header = new HorizontalLayout();
		header.add(titleLabel, close);
		header.setPadding(false);
		header.setFlexGrow(1, titleLabel);
		header.setAlignItems(FlexComponent.Alignment.CENTER);
		add(header);
	}
	private void newPerforationBinding() {
		// TODO Auto-generated method stub
		
		binder.forField(perforationnameTextField).bind(Blgz_Perforation_Info::getPerforationName, Blgz_Perforation_Info::setPerforationName);
		binder.forField(topMDTextField).bind(Blgz_Perforation_Info::getTopMD, Blgz_Perforation_Info::setTopMD);
		binder.forField(bottomMDTextField).bind(Blgz_Perforation_Info::getBottomMD, Blgz_Perforation_Info::setBottomMD);
	}
	
	private void saveNewPerforationButtonClickAction() {
		// TODO Auto-generated method stub
		this.saveButton.addClickListener(event -> {
			if(perforationnameTextField.isEmpty() || topMDTextField.isEmpty() || bottomMDTextField.isEmpty()) {
	        	  Notification.show("Please fill all the fields");
	        	  
	          } else {
			
			Blgz_Perforation_Info perforationInfo = new Blgz_Perforation_Info();
			if (_editFlag == false) {
			try {
				binder.writeBean(perforationInfo);
				addAllPerforationData(perforationInfo);	
				close();
				
			} catch (ValidationException e) {
//				logger.addLog(ExceptionUtils.getStackTrace(e),"ERROR");
				e.printStackTrace();
			}
			} else {
				try {
					binder.writeBean(perforationInfo);
				} catch (ValidationException e) {
					// TODO Auto-generated catch block
//					logger.addLog(ExceptionUtils.getStackTrace(e),"ERROR");
					e.printStackTrace();
				}
			
					ListDataProvider<Blgz_Perforation_Info> perforationlist = (ListDataProvider<Blgz_Perforation_Info>) _perforationgrid.getDataProvider();
					List<Blgz_Perforation_Info> rowList = null;
					if (!perforationlist.getItems().isEmpty()) {
						rowList = (List<Blgz_Perforation_Info>) perforationlist.getItems();
					} else {
						rowList = new ArrayList<>();
					}

					Blgz_Perforation_Info selectedFormationValue = _perforationgrid.asSingleSelect().getValue();

					List<Blgz_Perforation_Info> updatedList = new ArrayList<>();
					for (Blgz_Perforation_Info eachFormation : rowList) {

						if (eachFormation.getPerforationName().equals(selectedFormationValue.getPerforationName())) {
							updatedList.add(perforationInfo);
						} else {
							updatedList.add(eachFormation);
						}

					}
					_perforationgrid.setItems(updatedList);
					close();
				
			}
			
	          }
		});

	}
	
	private void topMDTextBoxAction() {
		// TODO Auto-generated method stub
		topMDValueChangeListener = new ValueChangeListener<ValueChangeEvent<?>>() {

			@Override
			public void valueChanged(ValueChangeEvent<?> event) {
				// TODO Auto-generated method stub
				try {
					Blgz_Perforation_Info lastRow = null;
				ListDataProvider<Blgz_Perforation_Info> dataProvider = (ListDataProvider<Blgz_Perforation_Info>) _perforationgrid.getDataProvider();
				List<Blgz_Perforation_Info> ItemsList=(List<Blgz_Perforation_Info>) dataProvider.getItems();
				if (!ItemsList.isEmpty()) {
	
					
							 lastRow = ItemsList.get(ItemsList.size()-1);
						
							Object bottomMD_Obj =lastRow.getBottomMD().toString() ;
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
//					Notification.show("Please enter numeric value.");
//					topMDTextField.removeValueChangeListener(topMDValueChangeListener);
					topMDTextField.clear();
					topMDTextField.addValueChangeListener(topMDValueChangeListener);
//					logger.addLog(ExceptionUtils.getStackTrace(e), "ERROR");
					e.printStackTrace();
				}
				
			}
		};
		
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
//					logger.addLog(ExceptionUtils.getStackTrace(e),"ERROR");
					e.printStackTrace();
					Notification.show("Please enter numeric value.");
					bottomMDTextField.clear();
					bottomMDTextField.addValueChangeListener(bottomMDValueChangeListener);
				}

			}

		};

	}


	

	private void updatePerformationDataUI() {
		// TODO Auto-generated method stub
		if (_editFlag) {
			perforationnameTextField.setValue("");
			topMDTextField.setValue("");
			bottomMDTextField.setValue("");
			Blgz_Perforation_Info performationInfo = _perforationgrid.asSingleSelect().getValue();
		if(performationInfo != null) {
			perforationnameTextField.setValue(performationInfo.getPerforationName());;
			topMDTextField.setValue(performationInfo.getTopMD());;
			bottomMDTextField.setValue(performationInfo.getBottomMD());;
		}
	}
	}
public void addAllPerforationData(Blgz_Perforation_Info perforationInfo) {
		
		String perforationName = perforationInfo.getPerforationName();
		String topMD = perforationInfo.getTopMD();
		String bottom = perforationInfo.getBottomMD();
		
		perforationInfo.setPerforationName(perforationName);
		perforationInfo.setTopMD(topMD);
		perforationInfo.setBottomMD(bottom);
		
		ListDataProvider<Blgz_Perforation_Info> list = (ListDataProvider<Blgz_Perforation_Info>) _perforationgrid.getDataProvider();
		List<Blgz_Perforation_Info> rowList = null;
		if (!list.getItems().isEmpty()) {
			rowList = (List<Blgz_Perforation_Info>) list.getItems();
		} else {
			rowList = new ArrayList<>();
		}
		rowList.add(perforationInfo);
		_perforationgrid.setItems(rowList);
		
		
	}

}

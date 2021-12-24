package com.petrabytes.views.wellbore;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.swing.DefaultListModel;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.petrabytes.wellbore.parts.mod.Petrabytes_WellboreParts_IconLoader;
import com.vaadin.flow.component.HasValue.ValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.data.converter.StringToDoubleConverter;
import com.vaadin.flow.data.provider.ListDataProvider;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.VaadinRequest;
//import com.vaadin.server.ClassResource;



public class Blgz_Completions_Window extends PetrabyteUI_Dialog {
	
	
public List<String> tableData = new ArrayList<String>();	
	private TextField depthTextField;
	private TextField enddepthTextField;
	private VerticalLayout mainLayout = new VerticalLayout();
	private Grid<Blgz_Completion_Info> _completiongrid;
	private boolean _editFlag;
	private Binder<Blgz_Completion_Info> binder = new Binder<>();
	private ListBox<String> completionNameListBox;
	private String completionID;
	private String[] currentUnits ;
	private VerticalLayout imagelayout;
//	private Bluegridz_Logger logger = null;
	
	private HashMap<String, String> paths = new HashMap<>();
	private HashMap<String, String> nameID = new HashMap<>();
	
	ValueChangeListener<ValueChangeEvent<?>> topMDValueChangeListener = null;
	ValueChangeListener<ValueChangeEvent<?>> bottomMDValueChangeListener = null;
	
	private String basepath = ((HttpServletRequest) VaadinRequest.getCurrent()).getRealPath("/");

	public Blgz_Completions_Window(Grid<Blgz_Completion_Info> completiongrid,boolean editFlag,String[] units) {
		
//		logger = Bluegridz_Logger_Factory.getCurrentSessionLogger();

    this._completiongrid = completiongrid;
    this._editFlag = editFlag;
    this.currentUnits = units;
    
    if (_editFlag == true) {
		this.setTitle("Edit Completion");
		this.setImage("icons" + File.separator + "purple-24x" + File.separator + "well_completions24.png");
		SetUI();

		completionNameListValue();
		completionNameListBoxClickAction();
		updateCompletionDataUI();


	} else {
		this.setTitle("New Completion");
		this.setImage("icons" + File.separator + "purple-24x" + File.separator + "well_completions24.png");
		SetUI();
		completionNameListValue();

		completionNameListBoxClickAction();

		topMDTextBoxAction();
		depthTextField.addValueChangeListener(topMDValueChangeListener);
		bottomMDTextBoxAction();
		enddepthTextField.addValueChangeListener(bottomMDValueChangeListener);
	}
    this.getElement().getStyle().set("padding", "0 0");
		this.content.add(mainLayout);
  
    mainLayout.setWidth("470px");
    mainLayout.setHeight("400");
    
//	SetUI();
//	completionNameListBoxClickAction();
//	updateCompletionDataUI();

	
	newCompletionBinding();
	saveNewCompletionButtonClickAction();
	
	
	
	
	
	}


	private void SetUI() {
	 HorizontalLayout headerlayout = new HorizontalLayout();
	 FormLayout formLayout = new FormLayout();
	 
	 depthTextField = new TextField();
	 depthTextField.setId("bgz_well_depthunit");
	 formLayout.addFormItem(depthTextField, "Top MD(" + currentUnits[0] + ")");
	 
	 
	 enddepthTextField = new TextField();
	 enddepthTextField.setId("bgz_well_enddepth");
	 formLayout.addFormItem(enddepthTextField, "Bottom MD(" + currentUnits[0] + ")");
	 
	 
	 VerticalLayout CompletionLayout = new VerticalLayout();
	 CompletionLayout.setWidth("60%");

     Label CompletionLabel = new Label("Completion Name");
     CompletionLabel.setId("bgz_well_enddepthCompletionName");
     CompletionLabel.getElement().getStyle().set("font-weight", "bold");
     CompletionLabel.getElement().getStyle().set("padding-bottom", "10px");
     CompletionLabel.getElement().getStyle().set("margin-left", "-16px");
	 completionNameListBox = new ListBox<String>();
	// completionNameListBox.setId("bgz_well_completionName");
	 
	 completionNameListBox.setId("wellCompletionNameListID");
	 
	
	 completionNameListBox.setHeight("300px");
	 completionNameListBox.setWidth("220px");
	 completionNameListBox.setId("bgz_well_CompletionNameListBox");
	 completionNameListBox.getElement().getStyle().set("border", "1px solid");
	 completionNameListBox.getElement().getStyle().set("margin-left", "-16px");
	 
	 imagelayout = new VerticalLayout();
	 imagelayout.setHeight("301px");
	 imagelayout.setWidth("200px");
	 imagelayout.getElement().getStyle().set("border", "1px solid");
	 imagelayout.getElement().getStyle().set("margin-top"," 48px");
	 imagelayout.getElement().getStyle().set("margin-left", "-5px");
	 imagelayout.setSpacing(false);
	 CompletionLayout.setSpacing(false);
	 headerlayout.setSpacing(false);
	 CompletionLayout.add(CompletionLabel,completionNameListBox);
	 headerlayout.add(CompletionLayout,imagelayout);
	 
	 HorizontalLayout footer=new HorizontalLayout();
		footer.add(saveButton,closeButton);
		footer.getStyle().set("margin-left", "62%");
		add(footer);
	 mainLayout.add(formLayout,headerlayout,footer);
	 }
	
	 private void newCompletionBinding() {
			// TODO Auto-generated method stub
		
		    binder.forField(depthTextField).bind(Blgz_Completion_Info::getDepth, Blgz_Completion_Info::setDepth);
			binder.forField(enddepthTextField).bind(Blgz_Completion_Info::getEndDepth, Blgz_Completion_Info::setEndDepth);
			binder.forField(completionNameListBox).bind(Blgz_Completion_Info::getCompletion, Blgz_Completion_Info::setCompletion);
//			binder.forField(completionID).bind(Blgz_Completion_Info::getPartID, Blgz_Completion_Info::setPartID);

		}
	 
	 private void topMDTextBoxAction() {
			// TODO Auto-generated method stub
			topMDValueChangeListener = new ValueChangeListener<ValueChangeEvent<?>>() {

				@Override
				public void valueChanged(ValueChangeEvent<?> event) {
					// TODO Auto-generated method stub
					try {
						Blgz_Completion_Info lastRow = null;
					ListDataProvider<Blgz_Completion_Info> dataProvider = (ListDataProvider<Blgz_Completion_Info>) _completiongrid.getDataProvider();
					List<Blgz_Completion_Info> ItemsList=(List<Blgz_Completion_Info>) dataProvider.getItems();
					if (!ItemsList.isEmpty()) {
		
						
								 lastRow = ItemsList.get(ItemsList.size()-1);
							
								Object bottomMD_Obj =lastRow.getEndDepth().toString() ;
								if (bottomMD_Obj != null) {
									double bottomMD_Value = Double.valueOf(bottomMD_Obj.toString().trim());
									double toMDValue = Double.valueOf(depthTextField.getValue().toString().trim());
									if (toMDValue < bottomMD_Value) {
										Notification.show("Value should be greater than " + bottomMD_Obj.toString());
//										topMDTextField.removeValueChangeListener(topMDValueChangeListener);
										depthTextField.clear();
										depthTextField.addValueChangeListener(topMDValueChangeListener);
									}
								}
							}
						
					} catch (NumberFormatException e) {
						// telemetry.trackException(e);
//						Notification.show("Please enter numeric value.");
//						topMDTextField.removeValueChangeListener(topMDValueChangeListener);
						depthTextField.clear();
						depthTextField.addValueChangeListener(topMDValueChangeListener);
//						logger.addLog(ExceptionUtils.getStackTrace(e), "NumberFormatException");
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
						String topStg = depthTextField.getValue().toString();
						if (!topStg.isEmpty()) {
							String bottomStg = enddepthTextField.getValue().toString();
							if (!bottomStg.isEmpty()) {
								double depthValue = Double.valueOf(topStg.trim());
								double endDepthValue = Double.valueOf(bottomStg.trim());
								if (endDepthValue < depthValue) {
									Notification.show("Value should be greater than " + topStg.toString());
									enddepthTextField.clear();
									enddepthTextField.addValueChangeListener(bottomMDValueChangeListener);
								}
							}
						} else {
							Notification.show("Enter top value");
							enddepthTextField.clear();
							enddepthTextField.addValueChangeListener(bottomMDValueChangeListener);
						}
					} catch (NumberFormatException e) {

						Notification.show("Please enter numeric value.");
						enddepthTextField.clear();
						enddepthTextField.addValueChangeListener(bottomMDValueChangeListener);
//						logger.addLog(ExceptionUtils.getStackTrace(e), "ERROR");
						e.printStackTrace();
					}

				}

			};

		}



		private void completionNameListValue() {
			// TODO Auto-generated method stub

			String fileLocation = basepath + "files"+File.separator+"wellboreParts.properties";
			FileReader fileread = null;
			try {
				fileread = new FileReader(fileLocation);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				// telemetry.trackException(e);
//				logger.addLog(ExceptionUtils.getStackTrace(e),"ERROR");
				e.printStackTrace();
				
			}
			BufferedReader bufferread = new BufferedReader(fileread);
			String sCurrentLine;
			String[] partDesc = new String[1000];
			final DefaultListModel model = new DefaultListModel();
			try {
				ArrayList sortlist = new ArrayList();
				while ((sCurrentLine = bufferread.readLine()) != null) {
					if (!sCurrentLine.startsWith("#") || !sCurrentLine.startsWith("")) {
						String[] part = sCurrentLine.split("=");
						String partID = part[0];

						partDesc = part[1].split(",");

						nameID.put(partID, partDesc[0]);

						paths.put(partDesc[0], partDesc[1] + "," + partDesc[2]);
						model.addElement(partDesc[0]);
						sortlist = Collections.list(model.elements());
						Collections.sort(sortlist, String.CASE_INSENSITIVE_ORDER);

					}
				}
				
				
				
//				for (Object list : sortlist) {
					completionNameListBox.setItems(sortlist);

//				}
			} catch (UnsupportedOperationException e) {
				// TODO Auto-generated catch block
				// telemetry.trackException(e);
//				logger.addLog(ExceptionUtils.getStackTrace(e),"ERROR");
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				// telemetry.trackException(e);
//      			logger.addLog(ExceptionUtils.getStackTrace(e),"ERROR");
      			e.printStackTrace();
			}
			
		}
		
		public byte[] extractBytes(String ImageName) throws IOException {
			// open image
			InputStream isInput = Petrabytes_WellboreParts_IconLoader.class.getResourceAsStream(ImageName);
			ByteArrayOutputStream bos = null;
			if (isInput != null) {
				
			System.out.println("Loading Image .... " + ImageName);
			BufferedImage bufferedImage = ImageIO.read(isInput);

			// get DataBufferBytes from Raster
			 bos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "png", bos);
			}
			return bos.toByteArray();
			
		}
	 
	 private void updateCompletionDataUI() {
			// TODO Auto-generated method stub
			if (_editFlag) {
				Blgz_Completion_Info completionInfo = _completiongrid.asSingleSelect().getValue();
			if(completionInfo != null) {
				depthTextField.setValue(completionInfo.getDepth());;
				enddepthTextField.setValue(completionInfo.getEndDepth());;
				completionNameListBox.setValue(completionInfo.getCompletion());
				String selectedName = completionNameListBox.getValue().toString();
				complitionDataWithImage(selectedName, imagelayout);
			}
		}
		}
	
	 
	 private void saveNewCompletionButtonClickAction() {
			// TODO Auto-generated method stub
			this.saveButton.addClickListener(event -> {
				if(completionNameListBox.isEmpty() || depthTextField.isEmpty() ||enddepthTextField.isEmpty() ) {
					
					 Notification.show("Please fill all the fields");
		        	  
		          } else {
				
				Blgz_Completion_Info completionInfo = new Blgz_Completion_Info();
				if (_editFlag == false) {
				try {
					binder.writeBean(completionInfo);
					addAllCompletionDatatoTable(completionInfo,nameID);	
					close();
					
				} catch (ValidationException e) {
//					logger.addLog(ExceptionUtils.getStackTrace(e),"ERROR");
					e.printStackTrace();
				}
				} else {
					try {
						binder.writeBean(completionInfo);
					} catch (ValidationException e) {
						// TODO Auto-generated catch block
//						logger.addLog(ExceptionUtils.getStackTrace(e),"ERROR");
						e.printStackTrace();
					}
				
						ListDataProvider<Blgz_Completion_Info> complitionlist = (ListDataProvider<Blgz_Completion_Info>) _completiongrid.getDataProvider();
						List<Blgz_Completion_Info> rowList = null;
						if (!complitionlist.getItems().isEmpty()) {
							rowList = (List<Blgz_Completion_Info>) complitionlist.getItems();
						} else {
							rowList = new ArrayList<>();
						}

						Blgz_Completion_Info selectedCompletionValue = _completiongrid.asSingleSelect().getValue();
						completionInfo.setId(selectedCompletionValue.getId());
						
						String selectedPartID = "";
						String completionName = completionInfo.getCompletion().toString();
						for (String partID : nameID.keySet()) {
							String name = nameID.get(partID);
							if (name.equalsIgnoreCase(completionName)) {
								selectedPartID = partID;
								completionInfo.setPartID(selectedPartID);
								break;
							}
						}
						
						List<Blgz_Completion_Info> updatedList = new ArrayList<>();
						for (Blgz_Completion_Info eachComplition : rowList) {

							if (eachComplition.getDepth().equals(selectedCompletionValue.getDepth())) {
								updatedList.add(completionInfo);
							} else {
								updatedList.add(eachComplition);
							}

						}
						_completiongrid.setItems(updatedList);
						close();
					
				}
		      }	
			});

		}
	 
	 
	 
	 private void completionNameListBoxClickAction() {
		 
		 completionNameListBox.addValueChangeListener(event ->{
			 imagelayout.removeAll();
				String selectedName = completionNameListBox.getValue().toString();
				
				complitionDataWithImage(selectedName,imagelayout);
			 
		 });
	 }
	 
	 private void complitionDataWithImage(String selectedName , VerticalLayout imageSetLayout) {
		 
		 StreamResource resource = null;
			try {
				String selectednamePath = paths.get(selectedName);
				String[] iconPaths = selectednamePath.split(",");   
				String iconpath = iconPaths[0];
				byte[] imageBytes = extractBytes(iconpath);
			                            
				String name = (selectedName).substring(selectedName.lastIndexOf("/") + 1);
			                            
				resource = new StreamResource(name, () -> new ByteArrayInputStream(imageBytes));
				                      
				Image image = new Image(resource, selectedName);
			                            
				image.setClassName("dashboardbutton_image");
				image.getElement().getStyle().set("margin-top", "70%");
				imageSetLayout.removeAll();                   
				imageSetLayout.add(image);


			                        
			} catch (IOException e) {
//				logger.addLog(ExceptionUtils.getStackTrace(e),"ERROR");
				e.printStackTrace();

			                        }
			
		 
	 }
	 public void addAllCompletionDatatoTable(Blgz_Completion_Info completionInfo,HashMap<String, String> nameID) {
			
			
			
			ListDataProvider<Blgz_Completion_Info> list = (ListDataProvider<Blgz_Completion_Info>) _completiongrid.getDataProvider();
			List<Blgz_Completion_Info> rowList = null;
			
			String selectedPartID = "";

			String completionName = completionInfo.getCompletion().toString();
			for (String partID : nameID.keySet()) {
				String name = nameID.get(partID);
				if (name.equalsIgnoreCase(completionName)) {
					selectedPartID = partID;
					completionInfo.setPartID(selectedPartID);
					break;
				}
			}
			
			long rowCount = 0;
			String ID = "";
			List<Blgz_Completion_Info> ItemsList=(List<Blgz_Completion_Info>) list.getItems();

				rowCount = ItemsList.size();
				rowCount = rowCount + 1;
				ID = String.valueOf(rowCount);
			
				completionInfo.setId(ID);
				
				if (!list.getItems().isEmpty()) {
					rowList = (List<Blgz_Completion_Info>) list.getItems();
				} else {
					rowList = new ArrayList<>();
				}
				rowList.add(completionInfo);
				

			_completiongrid.setItems(rowList);
			
			
		}
	 
}

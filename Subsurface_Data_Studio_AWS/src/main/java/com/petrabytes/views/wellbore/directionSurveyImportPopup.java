package com.petrabytes.views.wellbore;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;


import com.helger.css.parser.ParseException;
import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.sristy.dataorg.WellSurveyPoint;
import com.sristy.unitsystem.UOM;
import com.sristy.unitsystem.UOMRegistry;
import com.sristy.unitsystem.UnitCategory;
import com.sristy.unitsystem.util.PoscUnitsXMLParser;
//import com.sristy.unitsystem.UOM;
//import com.sristy.unitsystem.UOMRegistry;
//import com.sristy.unitsystem.UnitCategory;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.FileBuffer;
import com.vaadin.flow.component.upload.receivers.FileData;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.data.provider.ListDataProvider;

public class directionSurveyImportPopup extends PetrabyteUI_Dialog {
	private Upload uploadbutton;
	private VerticalLayout settingCenterLayout = new VerticalLayout();
	private TextArea rawDataTextArea = new TextArea();
	private HorizontalLayout comboBoxheaderLayout = new HorizontalLayout();
	private VerticalLayout comboBoxVLayout = new VerticalLayout();
	private VerticalLayout columnVLayout = new VerticalLayout();
	private VerticalLayout catagoryVLayout = new VerticalLayout();
	private VerticalLayout SystemVLayout = new VerticalLayout();
	private HorizontalLayout mainHorizonLayout = new HorizontalLayout();
	private TextField surveyFile = new TextField();
	public String _fileName = "";

	private String[] unitCatgoryList = { "Measured Depth", "Inclination", "Azimuth", "TVD", "Easting", "Northing" };
	private String[] systemUnit1 = { "m", "ft" };
	private String[] systemUnit2 = { "dega", "rad" };
	private Map<String, Map<String, String>> selectedUnits;
	private DecimalFormat dformat = (DecimalFormat) DecimalFormat.getInstance();
	private UOM _LENGTH_UNIT = null;
	private UOM _ANGLE_UNIT = null;
	private Component component;
	private String wellTypePath = "";
	private List<WellSurveyPoint> surveyPoints;
	//private Bluegridz_Logger logger = null;

	private ComboBox<String> delimiterCombobox = new ComboBox<String>("Delimiter");
	private TextField dataStartTextField = new TextField("Data Start Line No.");
	private TextField columHeaderTextField = new TextField("Column Header Specifier");
	private TextField unitSpeciferTextField = new TextField("Unit Specifier Line");

	Blgz_Import_File_Upload uploadFile = new Blgz_Import_File_Upload();
//	FileBuffer fileBuffer = new FileBuffer();
	MemoryBuffer fileBuffer = new MemoryBuffer();
	private Grid<Blgz_Deviation_Info> _deviationgrid;
//
	public directionSurveyImportPopup(Grid<Blgz_Deviation_Info> deviationgrid) {
//		
//		
//		logger = Bluegridz_Logger_Factory.getCurrentSessionLogger();
//
//		
//		// TODO Auto-generated constructor stub
		_deviationgrid = deviationgrid;
//		
		setDivationUI();
		uploadbuttonClickAction();
		delimiterComboboxClickAction();
		_LENGTH_UNIT = UOMRegistry.createUOM(UOMRegistry.LENGTH);
		_ANGLE_UNIT = UOMRegistry.createUOM(UOMRegistry.ANGLE);
	}

	private void setDivationUI() {
		// TODO Auto-generated method stub
		this.titleLabel.add("Import Deviation Data");
		this.setButtonName("Import");
		this.setImage("images" + File.separator + "import24.png");
		VerticalLayout centerLayout = new VerticalLayout();
		centerLayout.setWidth("1450px");
		centerLayout.setHeight("580px");
		this.content.add(centerLayout);

		HorizontalLayout headerLayout = new HorizontalLayout();
		
		uploadbutton = new Upload(fileBuffer);
		uploadbutton.setId("bgz_well_uploadbutton");
//		uploadbutton.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);
	//	uploadbutton.getStyle().set("margin-left", "30px");
	
		headerLayout.add(uploadbutton);

		Label settingLabel = new Label("Import Settings");
		settingLabel.setId("bgz_well_settingLabel");
		settingLabel.getStyle().set("margin-left", "-18px");
	//	settingLabel.getStyle().set("float", "right");

		settingCenterLayout.setSizeFull();

		Label labeldata = new Label("Raw Data");
		labeldata.setId("bgz_well_labeldata");
		labeldata.getStyle().set("width", "100%");
		labeldata.getStyle().set("float", "right");

		rawDataTextArea.setHeight("450px");
		rawDataTextArea.setWidth("700px");

		rawDataTextArea.getStyle().set("border-style", "solid");
		rawDataTextArea.getStyle().set("border-width", "thin");
	//	rawDataTextArea.getStyle().set("margin-left", "-20px");
		VerticalLayout settingLayout = new VerticalLayout(settingLabel, settingCenterLayout);
		// settingLayout.expand(settingCenterLayout);
		// settingLayout.setSizeFull();
		VerticalLayout rawDataLayout = new VerticalLayout(labeldata, rawDataTextArea);
//		rawDataLayout.expand(rawDataTextArea);
		// rawDataLayout.setSizeFull();
//		rawDataLayout.setWidth("800px");
//		rawDataLayout.setHeight("700px");
		rawDataLayout.getStyle().set("margin-left", "-25px");
		HorizontalLayout bodyLayout = new HorizontalLayout(settingLayout, rawDataLayout);
		bodyLayout.setSizeFull();

		centerLayout.add(headerLayout, bodyLayout);
		centerLayout.expand(bodyLayout);
		setComponent();
	}

	private void setComponent() {
		// TODO Auto-generated method stub
		HorizontalLayout hlayout1 = new HorizontalLayout(delimiterCombobox, dataStartTextField);
		hlayout1.setPadding(false);
		hlayout1.setWidthFull();
		hlayout1.getStyle().set("margin-left", "-18px");
		delimiterCombobox.setId("bgz_well_delimiter");
	//	delimiterCombobox.setId("delimiterComboxID");
		delimiterCombobox.setWidth("150px");
		delimiterCombobox.setItems("\\t", ",", "$", "\\s", "@", ":", ";");
		delimiterCombobox.getStyle().set("width", "40%");
		dataStartTextField.setId("bgz_well_dataStart");
		dataStartTextField.setValue("3");
		dataStartTextField.getStyle().set("width", "40%");

		HorizontalLayout hlayout2 = new HorizontalLayout(columHeaderTextField, unitSpeciferTextField);
		hlayout2.setPadding(false);
		hlayout2.setWidthFull();
		hlayout2.getStyle().set("margin-left", "-18px");
	//	columHeaderTextField.setId("bgz_well_columHeader");
		columHeaderTextField.setValue("1");
		unitSpeciferTextField.setValue("2");
		columHeaderTextField.getStyle().set("width", "40%");
		unitSpeciferTextField.setId("bgz_well_unitSpecifer");
		unitSpeciferTextField.getStyle().set("width", "40%");

		VerticalLayout vLayout = new VerticalLayout(hlayout1, hlayout2);
		vLayout.add(comboBoxheaderLayout);
		vLayout.add(mainHorizonLayout);
		vLayout.setPadding(false);
		vLayout.setWidthFull();
		vLayout.setSpacing(true);

		settingCenterLayout.add(vLayout);
		settingCenterLayout.setPadding(false);

	}

	public void uploadbuttonClickAction() {
		uploadbutton.addSucceededListener(event -> {
			Div outpput = new Div();
			uploadbutton.setId("uploadDeviationSurveyID");

			component = uploadFile.createComponent(event.getMIMEType(), event.getFileName(),
					fileBuffer.getInputStream());

			uploadFile.showOutput(event.getFileName(), component, outpput);

			_fileName = fileBuffer.getFileName();
			System.out.println("Value ->" + _fileName);
			if (event.getMIMEType().startsWith("image")) {
				rawDataTextArea.setPrefixComponent(outpput);
			} else {
				rawDataTextArea.setValue(component.getElement().getText());

			}
		});
	}

	private void delimiterComboboxClickAction() {
		delimiterCombobox.addValueChangeListener(event -> {
			selectedUnits = new HashMap<>();
			InputStream filePath = fileBuffer.getInputStream();

			String delimeter = delimiterCombobox.getValue().toString();

			if (delimeter.equalsIgnoreCase("$") || delimeter.equalsIgnoreCase(".")
					|| delimeter.equalsIgnoreCase("\n")) {
				delimeter = "\\" + delimeter;
			}
			if (delimeter.equalsIgnoreCase("\\s")) {
				delimeter = "\\s+";
			}

			int columnSpecifier = Integer.valueOf(columHeaderTextField.getValue().toString());
			int unitSpecifier = Integer.valueOf(unitSpeciferTextField.getValue().toString());
			int dataStartSpecifier = Integer.valueOf(dataStartTextField.getValue().toString());
			String fileName = fileBuffer.getFileName();
			String textCintent = component.getElement().getText();

			InputStreamReader inputStreamReader = new InputStreamReader(fileBuffer.getInputStream(),
					StandardCharsets.UTF_8);
//	        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
//	        List<String> columnMapping = new ArrayList<>();

			if (textCintent != null) {
				String[] split = null;
				try {
					Scanner scanner = new Scanner(textCintent);
					int lineNo = 1;
					String[] columnSplit = null, unitSplit = null, startData = null;

					while (scanner.hasNext()) {
						String nextLine = scanner.nextLine();
						if (!nextLine.trim().isEmpty() && validateLine(nextLine)) {
							if (lineNo == columnSpecifier) {
								columnSplit = nextLine.split(delimeter);
								// index =
								// reorderMDColumn(split);
							} else if (lineNo == unitSpecifier) {
								unitSplit = nextLine.split(delimeter);
							}
							if (lineNo == dataStartSpecifier) {
								startData = nextLine.split(delimeter);
							}
							/**
							 * if column line and unit line identified from text file, then no need to
							 * iterate
							 */
							if (columnSplit != null && unitSplit != null) {
								break;
							} /**
								 * If column line is set and unit specifier line is not specified, then breaks
								 * loop
								 */
							else if (columnSplit != null && unitSpecifier == -1) {
								break;
							} /**
								 * If unit line is set and column specifier line is not specified, breaks loop
								 */
							else if (unitSplit != null && columnSpecifier == -1) {
								break;
							}
							/**
							 * If column and unit lines are not specified and first data line is set, then
							 * breaks loop; First data line is for identifying no columns in text file
							 */
							else if (unitSpecifier == -1 && columnSpecifier == -1 && startData != null) {
								break;
							}
						}
						lineNo++;
					}
					int max = 0;
					if (columnSplit != null && unitSplit != null) {
						max = Math.max(columnSplit.length, unitSplit.length);
					} else if (columnSplit != null) {
						max = columnSplit.length;
					} else if (unitSplit != null) {
						max = unitSplit.length;
					} else if (startData != null) {
						max = startData.length;
					}
					int maxsize = max + 1;
					Label header1 = new Label("Index");
					Label header2 = new Label("Column Name");
					Label header3 = new Label("Category");
					Label header4 = new Label("System Unit");

					comboBoxVLayout.removeAll();
					columnVLayout.removeAll();
					catagoryVLayout.removeAll();
					SystemVLayout.removeAll();
					comboBoxheaderLayout.removeAll();

					for (int i = 0; i < max; i++) {

						String columnName = "", unit = "";
						if (columnSplit != null && i < columnSplit.length) {
							columnName = columnSplit[i].replaceAll("\\W+", "").replace(" ", " ");
						}
						if (unitSplit != null && i < unitSplit.length) {
							unit = unitSplit[i];
						}
						Label indexLabel = new Label("a");
						indexLabel.setText(String.valueOf(i));
						Label columnLabel = new Label();
						columnLabel.setText(columnName);
						ComboBox<String> unitCategoryBox = new ComboBox<String>();
						unitCategoryBox.setId(columnName.toLowerCase() + "UnitCategoryID");
						unitCategoryBox.setItems(Arrays.asList(unitCatgoryList));

						ComboBox<String> systemUnitBox = new ComboBox<String>();
						unitCategoryBox.setId(columnName.toLowerCase() + "SystemUnitID");

						/**
						 * value change listener add units to systemUnitsBox based selected unit
						 * category from unitCategoryBox
						 */
						unitCategoryBox.addValueChangeListener(e -> {

							String unitCategory = unitCategoryBox.getValue().toString();

							if (unitCategory.equalsIgnoreCase("Measured Depth") || unitCategory.equalsIgnoreCase("TVD")
									|| unitCategory.equalsIgnoreCase("Easting")
									|| unitCategory.equalsIgnoreCase("Northing")) {
								systemUnitBox.setItems(Arrays.asList(systemUnit1));
							} else {
								systemUnitBox.setItems(Arrays.asList(systemUnit2));
							}

						});

						/**
						 * value change listener to select the units from systemUnitsBox
						 */
						systemUnitBox.addValueChangeListener(ev -> {
							try {
								Map<String, String> units = new HashMap<>();
								if (selectedUnits.size() < maxsize) {
									String selectUnitCategory = unitCategoryBox.getValue().toString();
									units.put(selectUnitCategory, systemUnitBox.getValue().toString());
									selectedUnits.put(indexLabel.getText().toString(), units);
								}

							} catch (Exception e) {
						e.printStackTrace();
								// telemetry.trackException(e);
								// System.out.println(
								// "Removed all units system unit combobox for select unit
								// category");
							}

						});
//				vLayout.add(indexLabel);
						comboBoxheaderLayout.getStyle().set("width", "100%");
						header1.getStyle().set("width", "10%");
						header2.getStyle().set("width", "20%");
						header3.getStyle().set("width", "30%");
						header4.getStyle().set("width", "30%");
						header4.getStyle().set("padding-left", "10px");

						comboBoxheaderLayout.add(header1, header2, header3, header4);

						comboBoxVLayout.add(indexLabel);
//				comboBoxVLayout.setSpacing(false);
						comboBoxVLayout.getStyle().set("line-height", "30px");

						columnVLayout.add(columnLabel);
						// columnVLayout.setSpacing(true);
						columnVLayout.getStyle().set("line-height", "30px");
						columnVLayout.getStyle().set("margin-left", "50px");

						catagoryVLayout.add(unitCategoryBox);
						catagoryVLayout.setSpacing(false);
						catagoryVLayout.getStyle().set("margin-left", "60px");

						SystemVLayout.add(systemUnitBox);
						SystemVLayout.setSpacing(false);
						SystemVLayout.getStyle().set("margin-left", "-10px");

						mainHorizonLayout.getStyle().set("width", "100%");
						columnVLayout.getStyle().set("width", "10%");
						comboBoxVLayout.getStyle().set("width", "10%");
						catagoryVLayout.getStyle().set("width", "50%");

						mainHorizonLayout.removeAll();
						mainHorizonLayout.add(comboBoxVLayout, columnVLayout, catagoryVLayout, SystemVLayout);
//				vLayout.add(indexLabel,columnLabel,unitCategoryBox,systemUnitBox);
//				comboBoxheaderLayout.add(vLayout);

					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				Notification.show("Please upload a file");
			}

		});
		HorizontalLayout footerLayout = new HorizontalLayout();

		footerLayout.add(saveButton, closeButton);
		footerLayout.getStyle().set("margin-left", "1%");
		footerLayout.getStyle().set("margin-top", "70px");
		footerLayout.getStyle().set("margin-bottom", "15px");
		add(footerLayout);

		this.saveButton.addClickListener(event2 -> {
//			wellTypePath = surveyFileText.getValue().toString();

			String textCintent = component.getElement().getText();

			InputStreamReader inputStreamReader = new InputStreamReader(fileBuffer.getInputStream(),
					StandardCharsets.UTF_8);
			if (textCintent != null) {

//	                columHeaderTextField 
//					 unitSpeciferTextField

				if (delimiterCombobox.getValue() != null) {
					String delimeter = delimiterCombobox.getValue().toString();
					int dataStartSpecifier = Integer.valueOf(dataStartTextField.getValue().toString());
					boolean importFlag = updateSurveyTable(textCintent, delimeter, dataStartSpecifier);
				}
			}
		});

	}
//
	private boolean validateLine(String nextLine) {
		String chars = nextLine.trim();
		char[] c = chars.toCharArray();
		if (c.length > 0) {
			if (c[0] == '#') {
				return false;
			}
			return true;
		}
		return false;
	}
	
//
	private boolean updateSurveyTable(String selectedFile, String delimeter, int dataStartSpecifier) {
		// TODO Auto-generated method stub
		if (surveyPoints == null) {
			surveyPoints = new ArrayList<WellSurveyPoint>();
		} else {
			surveyPoints.clear();
//		}
		UnitCategory unitCategoryForName = UOMRegistry.getUnitCategoryForName("Azimuth");
		String currentAzmUnit = unitCategoryForName.getUnitString();
		UnitCategory unitCategoryForName2 = UOMRegistry.getUnitCategoryForName("Inclination");
		String currentIncUnit = unitCategoryForName2.getUnitString();
		if (delimeter.equalsIgnoreCase("$") || delimeter.equalsIgnoreCase(".") || delimeter.equalsIgnoreCase("\n")) {
			delimeter = "\\" + delimeter;
		}
		if (delimeter.equalsIgnoreCase("\\s")) {
			delimeter = "\\s+";
		}
		int count = 1;
		int rawSize = 0;
		ArrayList<Float> mdList = new ArrayList<>();
		ArrayList<Float> aziList = new ArrayList<>();
		ArrayList<Float> incList = new ArrayList<>();
		ArrayList<Float> tvdList = new ArrayList<>();
		ArrayList<Float> eastingList = new ArrayList<>();
		ArrayList<Float> nortingList = new ArrayList<>();
		try {
			if (selectedUnits.isEmpty()) {
//				String message = notifications.readPropertiesFile("37");
//				String label = "Deviation units";
//				notificationWindowEvent(message, label);
//				logger.addLog(message, "INFO");
				Notification.show("Units are Not selected.");
				return false;
			}

			Scanner scanner = new Scanner(selectedFile);
			while (scanner.hasNext()) {
				String nextLine = scanner.nextLine();
				// if (!nextLine.trim().isEmpty() || !validateLine(nextLine)) {
				// continue;
				// }
				/**
				 * need to add unit conversion code in this loop for
				 * md,inc,azi,tvd,norting,easting.
				 */
				if (count >= dataStartSpecifier && validateLine(nextLine)) {
					WellSurveyPoint wellSurvey = new WellSurveyPoint();
					rawSize++;
					String[] columnSplit = nextLine.split(delimeter);
					for (int i = 0; i < columnSplit.length; i++) {
						Map<String, String> mapUnits = selectedUnits.get(String.valueOf(i));
						if (mapUnits != null) {
							for (Object key : mapUnits.keySet()) {
								if (key.equals("Measured Depth")) {
									String mdUnit = mapUnits.get(key);
									float mdValue = (float)_LENGTH_UNIT.convert(Double.valueOf(columnSplit[i]), mdUnit,
											"m");
									try {
										mdValue = dformat.parse(String.valueOf(mdValue)).floatValue();
									} catch (java.text.ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
//									String mdValue= columnSplit[i];
									mdList.add(Float.valueOf(mdValue));
									wellSurvey.setMeasuredDepth(Float.valueOf(mdValue));
								} else if (key.equals("Inclination")) {
									String incUnit = mapUnits.get(key);
									float incValue = (float) _ANGLE_UNIT.convert(Double.valueOf(columnSplit[i]),
											incUnit, "dega");
									try {
										incValue = dformat.parse(String.valueOf(incValue)).floatValue();
									} catch (java.text.ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
//									String incValue=columnSplit[i];
									incList.add(Float.valueOf(incValue));
									wellSurvey.setInclination(Float.valueOf(incValue));
								} else if (key.equals("Azimuth")) {
									String aziUnit = mapUnits.get(key);
									float aziValue = (float) _ANGLE_UNIT.convert(Double.valueOf(columnSplit[i]),
											aziUnit, "dega");
									try {
										aziValue = dformat.parse(String.valueOf(aziValue)).floatValue();
									} catch (java.text.ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
//									String aziValue=columnSplit[i];
									aziList.add(Float.valueOf(aziValue));
									wellSurvey.setAzimuth(Float.valueOf(aziValue));
								} else if (key.equals("TVD")) {
									String tvdUnit = mapUnits.get(key);
									float tvdValue = (float) _ANGLE_UNIT.convert(Double.valueOf(columnSplit[i]),
											tvdUnit, "m");
									try {
										tvdValue = dformat.parse(String.valueOf(tvdValue)).floatValue();
									} catch (java.text.ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
//									String tvdValue=columnSplit[i];
									tvdList.add(Float.valueOf(tvdValue));
									wellSurvey.setTrueVerticalDepth(Float.valueOf(tvdValue));
								} else if (key.equals("Easting")) {
									String eastingUnit = mapUnits.get(key);
									float eatingValue = (float) _ANGLE_UNIT.convert(Double.valueOf(columnSplit[i]),
											eastingUnit, "m");
									try {
										eatingValue = dformat.parse(String.valueOf(eatingValue)).floatValue();
									} catch (java.text.ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
//									String eatingValue=columnSplit[i];
									eastingList.add(Float.valueOf(eatingValue));
									wellSurvey.setEastWest(Float.valueOf(eatingValue));
								} else if (key.equals("Northing")) {
									String northingUnit = mapUnits.get(key);
									float northingValue = (float) _ANGLE_UNIT.convert(Double.valueOf(columnSplit[i]),
											northingUnit, "m");
									try {
										northingValue = dformat.parse(String.valueOf(northingValue)).floatValue();
									} catch (java.text.ParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
//									String northingValue= columnSplit[i];
									nortingList.add(Float.valueOf(northingValue));
									wellSurvey.setNorthSouth(Float.valueOf(northingValue));
								}
							}

						}

					}
					wellSurvey.setHorizontalDistance(Float.NaN);
					wellSurvey.setDogleg(Float.NaN);
					surveyPoints.add(wellSurvey);
				}

				count++;
			}

			if (mdList.isEmpty() || incList.isEmpty() || aziList.isEmpty()) {
//				String message = notifications.readPropertiesFile("38");
//				String label = "Deviation units";
//				notificationWindowEvent(message, label);
//				logger.addLog(message, "INFO");
				// Notification.show("Please select proper Units.",
				// Notification.TYPE_WARNING_MESSAGE);
				Notification.show("Please select proper Units.");
				return false;
			}
			float[] mdListarray = ArrayUtils.toPrimitive(mdList.toArray(new Float[mdList.size()]));
			float[] incListarray = ArrayUtils.toPrimitive(incList.toArray(new Float[incList.size()]));
			float[] aziListarray = ArrayUtils.toPrimitive(aziList.toArray(new Float[aziList.size()]));
			if (selectedUnits.size() == 3) {
				// surveyPoints = SurveyCalculations.calculate(mdListarray,
				// incListarray, aziListarray);
				// _updateTable();
				_updateTable(mdListarray, incListarray, aziListarray, rawSize);
			} else if (selectedUnits.size() == 6) {
				if (tvdList.isEmpty() || eastingList.isEmpty() || nortingList.isEmpty()) {
//					String message = notifications.readPropertiesFile("38");
//					String label = "Deviation units";
//					notificationWindowEvent(message, label);
//					logger.addLog(message, "INFO");
					// Notification.show("Please select proper Units.",
					// Notification.TYPE_WARNING_MESSAGE);
					Notification.show("Data missing.");
					return false;
				}
				float[] tvdListarray = ArrayUtils.toPrimitive(tvdList.toArray(new Float[tvdList.size()]));
				float[] eastingListarray = ArrayUtils.toPrimitive(eastingList.toArray(new Float[eastingList.size()]));
				float[] northingListarray = ArrayUtils.toPrimitive(nortingList.toArray(new Float[nortingList.size()]));
				_updateTable(mdListarray, incListarray, aziListarray, tvdListarray, eastingListarray, northingListarray,
						rawSize);

			} else {
//				String message = notifications.readPropertiesFile("37");
//				String label = "Deviation units";
//				notificationWindowEvent(message, label);
//				logger.addLog(message, "INFO");
				// Notification.show("Units are not Selected.",
				// Notification.TYPE_WARNING_MESSAGE);
				Notification.show("Units are not Selected.");
				return false;
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			// telemetry.trackException(e);
			e.printStackTrace();
		}
		}
		return true;
	}
	
	
//
	private void _updateTable(float[] mdListarray, float[] incListarray, float[] aziListarray, int rawSize) {
		// TODO Auto-generated method stub
//		_deviationgrid.removeAllColumns();
		List<Blgz_Deviation_Info> rowList = new ArrayList<>();
		for (int i = 0; i < rawSize; i++) {
			Blgz_Deviation_Info deviationInfoData = new Blgz_Deviation_Info();

			if (mdListarray.length != 0) {
				deviationInfoData.setmD(String.valueOf(mdListarray[i]));
			}
			if (incListarray.length != 0) {
				deviationInfoData.setiNc(String.valueOf(incListarray[i]));
			}
			if (aziListarray.length != 0) {
				deviationInfoData.setaZM(String.valueOf(aziListarray[i]));
			}
			rowList.add(deviationInfoData);
		}

//		ListDataProvider<Blgz_Deviation_Info> list = (ListDataProvider<Blgz_Deviation_Info>) _deviationgrid
//				.getDataProvider();
//		if (!list.getItems().isEmpty()) {
//			rowList = (List<Blgz_Deviation_Info>) list.getItems();
//		} else {
//			rowList = new ArrayList<>();
//		}

		surveyFile.setValue(_fileName);
		_deviationgrid.setItems(rowList);
		close();
	}

	/**
	 * updating the table with 6 data
	 * 
	 * @param mdListarray
	 * @param incListarray
	 * @param aziListarray
	 * @param tvdListarray
	 * @param eastingListarray
	 * @param northingListarray
	 */
	private void _updateTable(float[] mdListarray, float[] incListarray, float[] aziListarray, float[] tvdListarray,
			float[] eastingListarray, float[] northingListarray, int rawSize) {
		// TODO Auto-generated method stub
		List<Blgz_Deviation_Info> rowList = new ArrayList<>();
		for (int i = 0; i < rawSize; i++) {
			Blgz_Deviation_Info deviationInfo = new Blgz_Deviation_Info();

			if (mdListarray.length != 0) {
				deviationInfo.setmD(String.valueOf(mdListarray[i]));
			}
			if (incListarray.length != 0) {
				deviationInfo.setiNc(String.valueOf(incListarray[i]));
			}
			if (aziListarray.length != 0) {
				deviationInfo.setaZM(String.valueOf(aziListarray[i]));
			}
			if (tvdListarray.length != 0) {
				deviationInfo.settVD(String.valueOf(tvdListarray[i]));
			}
			if (northingListarray.length != 0) {
				deviationInfo.setnS(String.valueOf(northingListarray[i]));
			}
			if (eastingListarray.length != 0) {
				deviationInfo.seteW(String.valueOf(eastingListarray[i]));
			}

			rowList.add(deviationInfo);

		}
		surveyFile.setValue(_fileName);
		_deviationgrid.setItems(rowList);

		close();

	}

	public TextField getSurveyFile() {
		return surveyFile;
	}

	public void setSurveyFile(TextField surveyFile) {
		this.surveyFile = surveyFile;
	}

	public List<WellSurveyPoint> getSurveyPoints() {
		return surveyPoints;
	}

	public void setSurveyPoints(List<WellSurveyPoint> surveyPoints) {
		this.surveyPoints = surveyPoints;
	}

}
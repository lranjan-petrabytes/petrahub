package com.petrabytes.views.wellbore;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

import com.helger.css.parser.ParseException;
import com.petrabytes.dialogUI.PetrabyteUI_Dialog;
import com.sristy.dataorg.WellSurveyPoint;
import com.sristy.numericalRecipes.SurveyCalculations;
import com.sristy.unitsystem.UOM;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;

/**
 * 
 * @author Moumita
 *
 */
public class Blgz_Calculation_Window_UI extends PetrabyteUI_Dialog {
	
	private VerticalLayout mainLayout = new VerticalLayout();
	private Label message1=new Label("Calculation will overwrite the values.");
	private Label message2=new Label("Are you sure you want to proceed?");
	private Boolean editFlag = false;
	private Grid<Blgz_Deviation_Info> _deviationgrid;
	private String _wellPathValue = null;
	private UOM _LENGTH_UNIT = null;
	private UOM _ANGLE_UNIT = null;
	private DecimalFormat dformat = (DecimalFormat) DecimalFormat.getInstance();
	
	
	private List<WellSurveyPoint> surveyPoints;


	
	public Blgz_Calculation_Window_UI(Grid<Blgz_Deviation_Info> deviationgrid,String wellPathValue) {
		
		
		 _wellPathValue = wellPathValue;
		 _deviationgrid = deviationgrid;
		
	
			message1.getStyle().set("width", "100%");
			message2.getStyle().set("width", "100%");
			message2.getStyle().set("margin-top", "-20px");

			this.setTitle("Survey Calculation");
			this.setImage("icons" + File.separator + "purple-24x" + File.separator + "well_deviation_24.png");
			mainLayout.setWidth("370px");
			mainLayout.setHeight("110px");
			this.setButtonName("OK");
			this.setCloseButtonName("Cancel");	
			this.content.add(mainLayout);
			setupUI();
			savebuttonclick();
		}

		

		private void setupUI() {
			// TODO Auto-generated method stub
			HorizontalLayout messageText1=new HorizontalLayout();
			messageText1.add(message1);
			HorizontalLayout messageText2=new HorizontalLayout();
			messageText2.add(message2);
			add(messageText1,messageText2);
			mainLayout.add(messageText1,messageText2);
			
			
			
		}
		
		private void savebuttonclick() {
			// TODO Auto-generated method stub
			
			this.saveButton.addClickListener(event -> {
				if (_wellPathValue.toString().equals("Deviated")) {
					
					List<Blgz_Deviation_Info> rowList = null;
					ListDataProvider<Blgz_Deviation_Info> deviationAllDatalist = (ListDataProvider<Blgz_Deviation_Info>) _deviationgrid
							.getDataProvider();
					if (!deviationAllDatalist.getItems().isEmpty()) {
						rowList = (List<Blgz_Deviation_Info>) deviationAllDatalist.getItems();
					} else {
						rowList = new ArrayList<>();
					}
					int rowCount = rowList.size();

					int rows = rowList.size();
					
					float[] md = new float[rows];
					float[] inc_Dega = new float[rows];
					float[] azm_Dega = new float[rows];
					for (int i = 0; i < rowCount; i++) {
						Blgz_Deviation_Info deviationInfo = rowList.get(i);
						
						try {
							md[i] = Float.NaN;
							inc_Dega[i] = Float.NaN;
							azm_Dega[i] = Float.NaN;
							String md_str = deviationInfo.getmD().toString();
									
							try {
								md[i] = dformat.parse(md_str).floatValue();
							} catch (java.text.ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							String inc_str = deviationInfo.getiNc().toString().trim();
							try {
								inc_Dega[i] = dformat.parse(inc_str).floatValue();
							} catch (java.text.ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (!Double.isNaN(inc_Dega[i])) {
//								inc_Dega[i] = (float) _ANGLE_UNIT.convert(inc_Dega[i], "dega", "dega");
							
							}

							String azm_str = deviationInfo.getaZM()
									.toString().trim();
							try {
								azm_Dega[i] = dformat.parse(azm_str).floatValue();
							} catch (java.text.ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (!Double.isNaN(azm_Dega[i])) {
//								azm_Dega[i] = (float) _ANGLE_UNIT.convert(azm_Dega[i], "dega", "dega");
							}

						} catch (Exception e) {
							// telemetry.trackException(e);
							e.printStackTrace();
							continue;
						}
					}
					
					surveyPoints =SurveyCalculations.calculate(md, inc_Dega, azm_Dega);
					_updateTable();
					close();
				}
			});
			
		}

    private void _updateTable() {
	
	/**
	 * need to add unit conversion code in this loop
	 */
    	List<Blgz_Deviation_Info> updatedList = new ArrayList<>();
	for (WellSurveyPoint surveyPoint : surveyPoints) {
		Blgz_Deviation_Info deviationInfo = new Blgz_Deviation_Info();

			deviationInfo.setmD(String.valueOf(String.valueOf(surveyPoint.getMeasuredDepth())));
			deviationInfo.setiNc(String.valueOf(String.valueOf(surveyPoint.getInclination())));
			deviationInfo.setaZM(String.valueOf(String.valueOf(surveyPoint.getAzimuth())));
			deviationInfo.settVD(String.valueOf(String.valueOf(dformat.format(surveyPoint.getTrueVerticalDepth()))));
			deviationInfo.setnS(String.valueOf(dformat.format(surveyPoint.getNorthSouth())));
			deviationInfo.seteW(String.valueOf(dformat.format(surveyPoint.getEastWest())));
			updatedList.add(deviationInfo);
		System.out.println(surveyPoint.getTrueVerticalDepth());
	}
	_deviationgrid.setItems(updatedList);
  }
}

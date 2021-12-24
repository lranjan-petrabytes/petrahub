package com.petrabytes.views.drilling_report;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.petrabytes.dialogUI.PetrahubNotification_Utilities;
import com.petrabytes.ui.utils.PB_Progress_Notification;

//import org.apache.commons.text.WordUtils;

//import org.apache.commons.lang3.exception.ExceptionUtils;
//import org.apache.xmlbeans.XmlException;
//import org.witsml.schemas.x1Series.CsLogCurveInfo;
//import org.witsml.schemas.x1Series.CsLogData;
//import org.witsml.schemas.x1Series.ObjLog;

//import com.common.io.D3_Project_IO;
//import com.common.io.PH_DataPreparation_Chart;
//import com.common.io.Skyz_Logs_Info;
import com.petrabytes.views.MainLayout;
import com.petrabytes.views.UI_Update;
//import com.sristy.petrabytes.dataorg.PB_Data_Attributes;
//import com.sristy.unitsystem.UOMRegistry;
//import com.sristy.unitsystem.UnitCategory;
//import com.sristy.unitsystem.util.PoscUnitsXMLParser;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;



@Route(value = "drilling_report", layout = MainLayout.class)
@PageTitle("Drilling Report")
@JsModule("./src/plotting/drillReport_Plotly.js")
public class DrillReports_UI extends VerticalLayout {


	private final UI ui = UI.getCurrent();
	private HorizontalLayout mainLayout = new HorizontalLayout();
	private String yaxisFlag = "true";
	private Text text;

	/**
	 * Plotly Plot
	 */
	public void launchPlot(String filePath) {
		String projectname = (String) VaadinService.getCurrentRequest().getWrappedSession()
				.getAttribute("project_name");
		if (projectname == null) {
		
			PB_Progress_Notification notification = new PB_Progress_Notification();
			String createProject = PetrahubNotification_Utilities.getInstance().createProject();
			notification.setImage("info");
			notification.setText(createProject);
			notification.open();
			notification.setDuration(3000);
		} else {
			UI_Update.updateEnable_topBar();

//		WellLogs_IO logsIO = new WellLogs_IO();
		File path = new File(filePath);
		File[] allLogs = path.listFiles();

//		ArrayList<String> importLogs = logsIO.getAllImportsLogs(filePath, "96602634", "97057435", "99517794");
		BufferedReader b = null;
		String readLine;
		
		
		JsonObject logs = Json.createObject();
		JsonArray mdArray = Json.createArray();
		JsonArray datType = Json.createArray();
		JsonArray datVal = Json.createArray();
		JsonArray dateArray = Json.createArray();
		JsonArray logName = Json.createArray();
		
		ExecutorService executor = Executors.newCachedThreadPool();
		PB_Progress_Notification notificatiion = new PB_Progress_Notification();
		String drillingreport = PetrahubNotification_Utilities.getInstance().drillingReport();
		executor.submit(() -> {

			ui.access(() -> {

				notificatiion.setImage("info");
				notificatiion.setText(drillingreport);
				notificatiion.open();
			});	
		
		
		/**
		 * Multiple tables
		 * 	Each table has multiple columns
		 * 
		 * for (each table)
		 * 	for (each row in the table)
		 */
		String[] queryArr = {
				"select * from drilling_report_db.cuttings",
				"select * from drilling_report_db.gas",
				"select * from drilling_report_db.lot_fit",
				"select * from drilling_report_db.mw_drilling_depth",
				"select * from drilling_report_db.packoff",
				"select * from drilling_report_db.partial_losses",
				"select * from drilling_report_db.reaming",
				"select * from drilling_report_db.stuck_pipe",
				"select * from drilling_report_db.tight_hole",
				"select * from drilling_report_db.total_losses",
				};
		DrillReportQuery reportQuery = new DrillReportQuery();
		ArrayList<String> queryResult;
	
		//fileName = query.substring(query.indexOf(".")+1, query.length());
		try
		{
			int lineCount = 0;
			
			for (int i=0; i<queryArr.length; i++)
			{
				String query = queryArr[i];
				
				String fileName = query.substring(query.indexOf(".")+1, query.length());
				fileName = formatName(fileName);
				queryResult = reportQuery.getGISQuery(query);
				
				//System.out.println(fileName);
				
				for (int j=2; j<queryResult.size(); j++)
				{
					//System.out.println(queryResult.get(j));
					
					String[] datLine = queryResult.get(j).split(","); 
//					JsonObject jLine = Json.createObject();
					if (datLine.length > 4) {
//						System.out.println("*"+lineCount+"*"+readLine);
						String dateStr = datLine[1].trim();
						String[] dParts;
						String year, month, day;
						if (dateStr.contains("-")) {
							dParts = dateStr.split("-");
							year = "20" + dParts[2];
						} else {
							dParts = dateStr.split("/");
							year = dParts[2];
						}
						month = dParts[0];
						day = dParts[1];
						dateStr = year + "-" + month + "-" + day;
//						System.out.println("New date: "+dateStr);
//						Date date = df.parse(dateStr);
						String mdVal = datLine[2].trim();
						String mwVal = datLine[4].trim();
						String datName = "MW";

						mdArray.set(lineCount, mdVal);
						dateArray.set(lineCount, dateStr);
						datVal.set(lineCount, mwVal);
						datType.set(lineCount, datName);
						logName.set(lineCount, fileName);
						lineCount++;
					}
				}
			}
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
		
		
		logs.put("Date", dateArray);
		logs.put("MD", mdArray);
		logs.put("MW", datVal);
		logs.put("Data Type", datType);
		logs.put("Log", logName);

		this.removeAll();
		FlexLayout chartLayout = new FlexLayout();
		chartLayout.setId("WellLogs");
		chartLayout.setMinHeight("750px");
		chartLayout.setMinWidth("1200px");

		this.add(chartLayout);
		ui.access(() -> {
		UI.getCurrent().getElement().executeJs("drillReportPlotly($0, $1)", logs, "WellLogs");
		notificatiion.close();
		});
		
		});
		}
		
	}
	
	public String formatName(String oName)
	{
		oName = oName.replaceAll("_", " ");
		//oName = WordUtils.capitalize(oName);
		
		oName = oName.replace("Mw", "MW");
		
		return oName;
	}

	public void setMainLayout(HorizontalLayout mainLayout) {
		this.mainLayout = mainLayout;
	}

	public void setText(Text text) {
		this.text = text;
	}

	public DrillReports_UI() {
		String basePath = "D:\\home\\site\\wwwroot\\prototype_files\\files";
		String filePath = basePath + "//Drilling_Report//";

		
		//System.out.println(test);
		launchPlot(filePath);
	}

}

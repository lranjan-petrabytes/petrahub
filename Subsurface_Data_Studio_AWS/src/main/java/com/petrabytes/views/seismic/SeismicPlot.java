package com.petrabytes.views.seismic;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.exception.ExceptionUtils;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import elemental.json.Json;
import elemental.json.JsonArray;
import elemental.json.JsonObject;

import java.io.StringReader;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;


public class SeismicPlot extends VerticalLayout {

	private HashSet<Double> defaulthYaxis = new HashSet<Double>();

	private String yaxisFlag = "true";
	private Text text;

	public JsonArray inputJsonData(List<SeismicTraceDataInfo> dataArray) {
		// TODO Auto-generated method stub
		HashSet<Double> hYaxis = new HashSet<Double>();

		int count = 0;
		Color c = null;
		HashSet<Double> hXaxis = new HashSet<Double>();
		hYaxis = new HashSet<Double>();
		String yCurrentUnit = "m";
		String xCurrentUnit = "dega";

		JsonArray datasetArray = Json.createArray();

		List<String> logNmaeList = new ArrayList<String>();

		JsonArray dataset = Json.createArray();

		for (int j = 0; j < dataArray.size(); j++) {
			double xValue = Double.parseDouble(dataArray.get(j).getxValue());
			double yValue = dataArray.get(j).getyValue();
			if (!Double.isNaN(xValue) && !Double.isNaN(yValue) && !Double.isInfinite(xValue)
					&& !Double.isInfinite(yValue)) {
				// DataSeriesItem dataPoint = new DataSeriesItem(yValue, xValue);
				JsonObject xyObject = Json.createObject();
				xyObject.put("x", xValue);
				xyObject.put("y", yValue);
				// xyDataList.add(new float[] { (float) xValue, (float) yValue });
				datasetArray.set(count, xyObject);
				count ++;
			}
		}

		

		// logNmaeList.add(name);
		// xyDataSeriesList.add(xyDataList);
//		double objXMin = Collections.min(hXaxis);
//		double objXMax = Collections.max(hXaxis);

//		double objYMin = Collections.min(hYaxis);
//		double objYMax = Collections.max(hYaxis);
//		defaulthYaxis.add(objYMin);
//		defaulthYaxis.add(objYMax);

//		JsonArray xRange = Json.createArray();
//		xRange.set(0, objXMin);
//		xRange.set(1, objXMax);
//
//		JsonArray yRange = Json.createArray();
//		yRange.set(0, objYMin);
//		yRange.set(1, objYMax);
//
//		String yaxisLabel = "MD (" + yCurrentUnit + ")";
//
		String seriesNameArray = "Seismic Log";

		JsonObject mainObject = Json.createObject();
		mainObject.put("seriesData", datasetArray);
		mainObject.put("seriesNameArray", seriesNameArray);
		mainObject.put("nameArray", seriesNameArray + "(" + "ft/s" + ")");

		yaxisFlag = "false";
		
		
		JsonArray logsArray = Json.createArray();
			logsArray.set(0, mainObject);
		
		
		return logsArray;
	}

}

package com.petrabytes.views.well_Information;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;

//import com.sristy.dataorg.DataSeries;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.provider.ListDataProvider;

import elemental.json.Json;
import elemental.json.JsonObject;

public class DataTab_UI extends VerticalLayout {

	private HorizontalLayout mainLayout = new HorizontalLayout();
	private Grid<Depth_Data_Info> depthMnemonicGrid = new Grid<Depth_Data_Info>();
	private String mnemonicIdValue = "mnemonicId";
	private ListDataProvider<Depth_Data_Info> populateData;
	String StratDepth;
	String EndDepth;
	public DataTab_UI() {
		setUI();
		//startAndEndDepth();
	}

	private void setUI() {
		// TODO Auto-generated method stub
		this.setWidth("400px");
	//	this.setHeightFull();
		//depthMnemonicGrid.setSizeFull();
		depthMnemonicGrid.setHeight("355px");
		depthMnemonicGrid.setWidth("400px");
		depthMnemonicGrid.addColumn(Depth_Data_Info::getDepthData).setHeader("Depth");
		depthMnemonicGrid.addColumn(Depth_Data_Info::getMnemonicData).setHeader("Logdata").setKey(mnemonicIdValue);
		depthMnemonicGrid.setSelectionMode(SelectionMode.SINGLE);
		mainLayout.setSizeFull();
		mainLayout.add(depthMnemonicGrid);
		this.add(mainLayout);

	}
	
	public void setPopulateData(ListDataProvider<Depth_Data_Info> populateData) {
		this.populateData = populateData;
		//startAndEndDepth() ;
	}
	
	public void updateGrid() {
		
		depthMnemonicGrid.setDataProvider(populateData);
		
	}
	
	
	

	
	
	private void startAndEndDepth() {
		ListDataProvider<Depth_Data_Info> dataProvider = (ListDataProvider<Depth_Data_Info>) depthMnemonicGrid
				.getDataProvider();
		List<Depth_Data_Info> depthgridinputs = (List<Depth_Data_Info>) dataProvider.getItems();
		ArrayList<String> depth = new ArrayList();
		for (Depth_Data_Info row:depthgridinputs) {
			
			 String abc = row.getDepthData();
			 depth.add(abc);
		}
		for (String i:depth) {
			 StratDepth = depth.get(0);
			 EndDepth = depth.get(depth.size()-1);
			
		}
	}

	public String getStratDepth() {
		return StratDepth;
	}

	public void setStratDepth(String stratDepth) {
		StratDepth = stratDepth;
	}

	public String getEndDepth() {
		return EndDepth;
	}

	public void setEndDepth(String endDepth) {
		EndDepth = endDepth;
	}
	
	
	

}

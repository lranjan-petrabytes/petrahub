package com.petrabytes.views.dts;

import java.util.ArrayList;
import java.util.List;

public class DTS_GridData_Provider {
	public static List<DTS_GridData> createDtsDataProvider(List<Double> depthList, String[][] dsDataList) {
		List<DTS_GridData> rowDataList = new ArrayList<>();
		int index;
		Double depth;
		List<String> tList = new ArrayList<>();
		
		// Use 2d array and depthList:
		int currtime = 0;
		System.out.println("\n");
		for (int i = 0; i < dsDataList.length; i++) { //numDepths
			
			index = i;
			depth = depthList.get(currtime);
			System.out.println(i);
			System.out.println("\n");
			for (int j = 0; j < dsDataList[0].length; j++) { //numTraces
//				System.out.println(j);
				tList.add(dsDataList[i][j]);
				
			}
			
			DTS_GridData row = new DTS_GridData(index, Double.valueOf(depth).toString(), tList);
			rowDataList.add(row);
			tList.clear();
			currtime ++;
//			System.out.println("\n");
		}
		
		return rowDataList;
	}
}

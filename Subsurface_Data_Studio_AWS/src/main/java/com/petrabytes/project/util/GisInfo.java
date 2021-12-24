package com.petrabytes.project.util;

import java.util.HashMap;

public class GisInfo {

	private HashMap<String, Boolean> sets;

	public HashMap<String, Boolean> getSets() {
		return sets;
	}

	public void setSets(HashMap<String, Boolean> sets) {
		this.sets = sets;
	}

	public HashMap<String, Boolean> setDefaults() {
		sets = new HashMap<String, Boolean>();
		sets.put("Bio Diesel", false);
		sets.put("Border Crossing Electric",false);
		sets.put("Border Crossing Liquids", false);
		sets.put("Ethanol Plants", false);
		sets.put("Coal Mines", false);
		sets.put("Crude Oil Rail Terminals", false);
		sets.put("Product Lines", false);
		sets.put("Natural Gas Lines", false);
		sets.put("HG Lines", false);
		sets.put("Natural Gas Market Hubs", false);
		sets.put("Well Schematic", false);
		sets.put("Tight Oil Shale Play", false);
		sets.put("Natural Gas Storage", false);
		sets.put("Ethylene Crackers", false);
		sets.put("Geothermal", false);
		sets.put("LNG Impexp Terminals", false);
		sets.put("Crude Oil Pipeline", false);
		sets.put("Shale Play Shx", false);
	//	sets.put("LNG Impexp Terminals", false);

		return sets;
	}

}

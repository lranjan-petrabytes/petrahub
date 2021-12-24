package com.petrabytes.views.osdudata;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import org.apache.commons.io.IOUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import com.petrabytes.views.osduWellLogs.OsduWellLogsInformation;
import com.petrabytes.views.wellbore.Blgz_Deviation_Info;

public class OSDUQueries {
	private final static String infoQueryUrl = "https://petrabytes.preview.paas.47lining.com/api/search/v2/query";
	private final static String dataQueryUrl = "https://petrabytes.preview.paas.47lining.com/api/dataset/v1/getRetrievalInstructions";
	private String requestType = "POST";
	private String tokenID = "";

	private OutputStream initConnection(HttpURLConnection con) throws IOException {
		con.setRequestMethod(requestType);
		con.setRequestProperty("Authorization", tokenID);
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("data-partition-id", "opendes");
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();

		return os;
	}

	public ArrayList<String> queryWellInformation(String tokenID) throws IOException {
		this.tokenID = tokenID;
		ArrayList<String> well_ids = new ArrayList<>();
		String queryJsonBody = "{\r\n" + "    \"kind\": \"opendes:*:*:*\",\r\n"
				+ "    \"query\": \"data.WellID:*\",\r\n" + "    \"offset\": 0,\r\n" + "    \"limit\": 10000,\r\n"
				+ "    \"returnedFields\": [\"data.WellID\"]\r\n" + "    }";

		URL obj = new URL(infoQueryUrl);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		OutputStream os = initConnection(con);

		os.write(queryJsonBody.getBytes());
		os.flush();
		os.close();
		int responseCode = con.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			try {
				JSONObject json = new JSONObject(response.toString());
				JSONArray jsonArray = json.getJSONArray("results");

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsObj = (JSONObject) jsonArray.get(i);
					String well_id = jsObj.getJSONObject("data").getString("WellID").split(":")[2];
					if (well_ids.contains(well_id))
						continue;
					well_ids.add(well_id);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return well_ids;
	}

	public ArrayList<String> queryWellboreInformation(String selectedWellId, String tokenID) throws IOException {
		this.tokenID = tokenID;
		ArrayList<String> wellbore_ids = new ArrayList<String>();
		String queryJsonBody = "{\r\n" + "    \"kind\": \"opendes:*:master-data--Wellbore:*\",\r\n"
				+ "    \"query\": \"data.WellID:" + selectedWellId + "\",\r\n" + "    \"offset\": 0,\r\n"
				+ "    \"limit\": 10000,\r\n" + "    \"returnedFields\": [\"id\"]\r\n" + "    }";

		URL obj = new URL(infoQueryUrl);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		OutputStream os = initConnection(con);

		os.write(queryJsonBody.getBytes());
		os.flush();
		os.close();
		int responseCode = con.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			try {
				JSONObject json = new JSONObject(response.toString());
				JSONArray jsonArray = json.getJSONArray("results");

				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsObj = (JSONObject) jsonArray.get(i);
					String wellbore_id = jsObj.getString("id").split(":")[2];
					wellbore_ids.add(wellbore_id);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return wellbore_ids;
	}

	

}

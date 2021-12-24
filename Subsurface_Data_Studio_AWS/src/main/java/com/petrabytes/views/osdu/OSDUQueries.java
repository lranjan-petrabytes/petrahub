package com.petrabytes.views.osdu;

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

import com.petrabytes.ui.utils.PB_Progress_Notification;
import com.petrabytes.views.osduWellLogs.OsduWellLogsInformation;
import com.petrabytes.views.osdudata.OsduDataInfo;
import com.petrabytes.views.wellbore.Blgz_Deviation_Info;
import com.vaadin.flow.component.UI;

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
		ArrayList<String> well_ids = new ArrayList<String>();
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
			PB_Progress_Notification notification = new PB_Progress_Notification();
			notification.setImage("info");
			notification.setText("Fetching Basin information");
			notification.setDuration(3000);
			notification.open();
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
		} else {
			
			PB_Progress_Notification notification = new PB_Progress_Notification();
			
			//UI.getCurrent().access(() -> {
				notification.setImage("info");
				notification.setText("Please enter correct Token");
				notification.setDuration(3000);
				notification.open();
			//});
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

	public ArrayList<OSDUWellboreTrajectoryInfo> queryWellboreTrajectory(String selectedWellboreId, String tokenID)
			throws IOException {
		this.tokenID = tokenID;
		ArrayList<OSDUWellboreTrajectoryInfo> wellboreTrajectories = new ArrayList<OSDUWellboreTrajectoryInfo>();
		String queryJsonBody = "{\r\n" + "    \"kind\": \"opendes:*:work-product-component--WellboreTrajectory:*\",\r\n"
				+ "    \"query\": \"data.WellboreID:" + selectedWellboreId + "\",\r\n" + "    \"offset\": 0,\r\n"
				+ "    \"limit\": 10000\r\n" + "    }";

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

					OSDUWellboreTrajectoryInfo wellboreTrajectory = new OSDUWellboreTrajectoryInfo();

					JSONObject jsObj = (JSONObject) jsonArray.get(i);
					wellboreTrajectory.setName(jsObj.getJSONObject("data").getString("Name"));
					wellboreTrajectory.setDataset(jsObj.getJSONObject("data").getJSONArray("Datasets").getString(0));
					wellboreTrajectory.setTopMD(jsObj.getJSONObject("data").getDouble("TopDepthMeasuredDepth"));
					wellboreTrajectory.setBottomMD(jsObj.getJSONObject("data").getDouble("BaseDepthMeasuredDepth"));

					wellboreTrajectories.add(wellboreTrajectory);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return wellboreTrajectories;
	}

	public ArrayList<OsduWellLogsInformation> queryWellLogs(String selectedWellboreId, String tokenID) throws IOException {
		this.tokenID = tokenID;
		ArrayList<OsduWellLogsInformation> wellboreLogs = new ArrayList<OsduWellLogsInformation>();
		String queryJsonBody = "{\r\n" + "    \"kind\": \"opendes:*:work-product-component--WellLog:*\",\r\n"
				+ "    \"query\": \"data.WellboreID:" + selectedWellboreId + "\",\r\n" + "    \"offset\": 0,\r\n"
				+ "    \"limit\": 10000\r\n" + "    }";

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
				JSONArray jsonArray1 = json.getJSONArray("results");
				JSONObject json1 = (JSONObject) jsonArray1.get(0);
				JSONObject jsonArray2 = json1.getJSONObject("data");
				JSONArray jsonArray = jsonArray2.getJSONArray("Curves");
				String datasets = jsonArray2.getJSONArray("Datasets").getString(0);
				
			
				for (int i = 0; i < jsonArray.length(); i++) {

					OsduWellLogsInformation wellboreLog = new OsduWellLogsInformation();

					JSONObject jsObj = (JSONObject) jsonArray.get(i);

					String unit = jsObj.get("CurveUnit").toString().split(":")[2];
                     wellboreLog.setDataset(datasets);
					wellboreLog.setLogname((String) jsObj.get("Mnemonic"));
					wellboreLog.setUnit(unit);
					wellboreLogs.add(wellboreLog);

				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return wellboreLogs;
	}

	public ArrayList<Blgz_Deviation_Info> queryWellboreTrajectoryData(String datasetName, String tokenID)
			throws IOException {
		this.tokenID = tokenID;
		ArrayList<Blgz_Deviation_Info> wellboreTrajectories = new ArrayList<Blgz_Deviation_Info>();
		String queryJsonBody = "{\r\n" + "  \"datasetRegistryIds\": [\"" + datasetName + "\"]\r\n" + "}";

		URL obj = new URL(dataQueryUrl);
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
				String signedUrl = json.getJSONArray("delivery").getJSONObject(0).getJSONObject("retrievalProperties")
						.getString("signedUrl");

				String[] result = IOUtils.toString(new URL(signedUrl), Charset.forName("UTF-8")).split("\r\n");

				int i = 0;
				for (String line : result) {

					i++;
					if (i == 1)
						continue;

					Blgz_Deviation_Info deviationData = new Blgz_Deviation_Info();
					String[] data = line.split(",");
					deviationData.setmD(data[2].replace("\"", ""));
					deviationData.setiNc(data[5].replace("\"", ""));
					deviationData.setaZM(data[4].replace("\"", ""));

					wellboreTrajectories.add(deviationData);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return wellboreTrajectories;
	}
	
	
	public String[] queryWellLogsData(String datasetName, String tokenID)
			throws IOException {
		this.tokenID = tokenID;
		String[] result = null;
		String queryJsonBody = "{\r\n" + "  \"datasetRegistryIds\": [\"" + datasetName + "\"]\r\n" + "}";

		URL obj = new URL(dataQueryUrl);
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
				String signedUrl = json.getJSONArray("delivery").getJSONObject(0).getJSONObject("retrievalProperties")
						.getString("signedUrl");

				result = IOUtils.toString(new URL(signedUrl), Charset.forName("UTF-8")).split("\r\n");

			

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return result;
	}

}

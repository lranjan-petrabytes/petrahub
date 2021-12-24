package com.petrabytes.databricks;

import java.net.HttpURLConnection;
import java.net.URL;
//import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import com.petrabytes.keyvault.AWS_Key_Vault;
import com.petrabytes.keyvault.PH_KeyVault;
//import com.vaadin.flow.component.textfield.PasswordField;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

public class invokeDatabricksJob {
	public static void osduDataJob(String jobID2,String apitoken,String input_string) {
//		int JobID = 5875;
//		int WellboreID = 12345;
		/// Getting JSON String of person Object
		String cluster_jsonString = "{\"job_id\": " + jobID2 + ",\"notebook_params\": {\"well_id\": " + input_string
				+ ",\"Token\":\""+apitoken+"\"}} ";
		System.out.println(cluster_jsonString);
		
		String url_value;
	
		String token_value;
	
		String databricks_status = PH_KeyVault.getSecretKey("Databricks-Status");
		if (databricks_status.equalsIgnoreCase("azure")) {
			url_value = PH_KeyVault.getSecretKey("Databricks-URL");
			token_value = PH_KeyVault.getSecretKey("Databricks-Token");
			url_value = url_value + "/api/2.0/jobs/run-now";
			
		} else {
			url_value = AWS_Key_Vault.getSecret("Databricks-URL");
			token_value = AWS_Key_Vault.getSecret("Databricks-Token");
			int indexOfCom = url_value.indexOf("com") + 3;
        	url_value = url_value.substring(0, indexOfCom);
        	url_value = url_value + "/api/2.0/jobs/run-now";
		}
		
		
		String requestType = "POST";
		try {
//			URL obj = new URL("https://adb-3305711601758284.4.azuredatabricks.net/api/2.0/jobs/run-now");
			URL obj = new URL(url_value);
			
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod(requestType);
			con.setRequestProperty("Content-Type", "application/json; utf-8");
//			con.setRequestProperty("Authorization", "Bearer " + "dapi76e6ef0c81459fca303f219c61b4fa9c");
			con.setRequestProperty("Authorization", "Bearer " + token_value);
			con.setDoOutput(true);
			OutputStream os = con.getOutputStream();
			os.write(cluster_jsonString.getBytes());
			os.flush();
			os.close();
			int responseCode = con.getResponseCode();

			JSONObject jsonObject = new JSONObject(IOUtils.toString(con.getInputStream(), Charset.forName("UTF-8")));
			// printing result from response
			System.out.println("Created Run_ID:-" + jsonObject.getInt("run_id"));
//			if (responseCode == HttpURLConnection.HTTP_CREATED) {
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
//				System.out.println("Response:-" + response.toString());
//			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			System.out.println("Response: Cluster Already Started !");
			e.printStackTrace();
		}

	
		
	}
}

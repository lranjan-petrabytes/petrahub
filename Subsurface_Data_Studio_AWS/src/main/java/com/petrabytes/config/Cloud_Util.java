package com.petrabytes.config;

import java.io.IOException;
import java.util.Properties;

public class Cloud_Util {
	private static Cloud_Util _singleton = null;

	private Properties globalProps = new Properties();
	String cloud_provider;
	
	public static Cloud_Util getInstance() {
		if (_singleton == null) {
			_singleton = new Cloud_Util();
		}
		return _singleton;
	}
	
	private Cloud_Util() {
		try {
			globalProps.load(this.getClass().getResourceAsStream("/bluegridz_version.properties"));
			cloud_provider =  globalProps.getProperty("cloud_provider");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getCloud_provider() {
		return cloud_provider;
	}

	public void setCloud_provider(String cloud_provider) {
		this.cloud_provider = cloud_provider;
	}
	
	
}

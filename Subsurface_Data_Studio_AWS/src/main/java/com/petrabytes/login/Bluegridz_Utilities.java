package com.petrabytes.login;

import java.util.Properties;

public class Bluegridz_Utilities {
	private static Bluegridz_Utilities _singleton = null;
	private Properties globalProps = new Properties();
   
	public static Bluegridz_Utilities getInstance() {
		if (_singleton == null) {
			_singleton = new Bluegridz_Utilities();
		}
		return _singleton;
	}

	private Bluegridz_Utilities() {
		try {
			globalProps.load(this.getClass().getResourceAsStream("/bluegridz_version.properties"));
			
			Object obj = this.getClass().getClassLoader().getResourceAsStream("pom.xml");

			Object obj2 = this.getClass()
					.getResourceAsStream("/META-INF/maven/com.skygridz/Skygridz_Main/pom.properties");

			String db_pointer_prop = globalProps.getProperty("login_pointer");
//			System.out.print(" DB Pointer Property: " + db_pointer_prop);

			String db_type_prop = globalProps.getProperty("database_type");
//			System.out.print(" DB Type Property: " + db_type_prop);

			String product_type_prop = globalProps.getProperty("product_name");
//			System.out.print(" Product Type Property: " + product_type_prop);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	public String getCurrentVersion() {
		return globalProps.getProperty("bluegridz.version", "Unknown");
	}

	public String getDatabasePointerProperty() {
		return globalProps.getProperty("login_pointer", "Local");
	}
}

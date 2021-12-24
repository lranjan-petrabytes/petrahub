package com.petrabytes.views.coredata;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import com.petrabytes.connections.DatabricksConnection;

public class PB_Image_DB {
	private static String driver = "com.simba.spark.jdbc.Driver"; // attach the Spark jar to the Classpath.
	private static String url = "jdbc:spark://adb-3305711601758284.4.azuredatabricks.net:443/default;transportMode=http;ssl=1;httpPath=sql/protocolv1/o/3305711601758284/0304-081914-quits100";
	private static String username = "vpatel@petrabytes.com";
	private static String password = "dapi4e5313c9a81011768f44ae356b5675e7";

	public static byte[] imageQueryDB(String depth) {
		byte[] imageBytes = null;
		try {
			Class.forName(driver);
		//	Connection con = DriverManager.getConnection(url, username, password);
			Connection con = new DatabricksConnection().connect();
			System.out.println("Connection Successful");

			Statement stmt = con.createStatement();

			String image_query = "select content from pb_core_data.core_data_image where depth ='" + depth + "'";

			ResultSet rs = stmt.executeQuery(image_query);
			System.out.println("Query Done ...");
			int columnCount = rs.getMetaData().getColumnCount();
			System.out.println("---------------------------------------");

			while (rs.next()) {
				InputStream is = rs.getBinaryStream("content");
				Image image = ImageIO.read(is);

				BufferedImage bi = (BufferedImage) image;
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				ImageIO.write(bi, "png", bos);

				imageBytes = bos.toByteArray();
			}

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return imageBytes;
	}
	
	
	public static List<String> readImageDepths(String query) {
		Connection connection = new DatabricksConnection().connect();
		List<String> depths = new ArrayList<>();

		Statement stmt;
		try {
			stmt = connection.createStatement();
			ResultSet queryResult = stmt.executeQuery(query);

			ResultSetMetaData rsmd = queryResult.getMetaData();
			while (queryResult.next()) {
				String column_name = rsmd.getColumnName(1);
				depths.add(queryResult.getObject(column_name).toString());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return depths;
	}
}

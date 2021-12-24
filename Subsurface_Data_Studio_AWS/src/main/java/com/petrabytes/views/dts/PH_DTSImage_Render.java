package com.petrabytes.views.dts;

import com.petrabytes.petrahub.colorscale.utils.PH_ColorScale;
import com.petrabytes.petrahub.colorscale.utils.PH_ColorScaleReader;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class PH_DTSImage_Render {

	private PH_ColorScaleReader colorScaleReader = new PH_ColorScaleReader();
//	private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
	String _schema = "";

	private String _rootPath;
	// private DTS_SQL_Data_Service dtsDataService;
	// private PB_DataMapping_Service mappingService = new PB_DataMapping_Service();

	public byte[] renderImage(String[][] dtsSearchData) throws IOException {

		// rootPath = mappingService.getRootPath();
		
		String dsData = null;
		// TODO Auto-generated method stub

		List<Color> colorScale = colorScaleReader.parse(colorScaleReader.jet256);

		double Min = 0.0;
		double Max = 240.0;
		int height = dtsSearchData.length;
		int width = dtsSearchData[0].length;
		int max = 0;
		byte[][] imageByteData = new byte[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				double value = 0.0;
				String dataValue = dtsSearchData[i][j].trim();
				if (!dataValue.equalsIgnoreCase("nan")) {
					value = Double.valueOf(dataValue);
				} else {
					value = Double.valueOf(Double.NaN);
				}
				value = (value < Min) ? Min : value;
				value = (value > Max) ? Max : value;
				byte data = (byte) Math.round(255 * ((value - Min) / (Max - Min)));
				if (max < data) {
					max = data;
				}
				imageByteData[i][j] = data;
			}
		}

		List<PH_ColorScale> scaleList = colorScaleReader.resetColorScaleToDefault(0, max, colorScale);

		BufferedImage img = _getImage(imageByteData, width, height);

		applyColorScale(0, max, img, imageByteData, scaleList);
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(img,"png",bos);
		return bos.toByteArray();

	}

	/**
	 * generating buffered image
	 */
	public BufferedImage getImage(int pixels[][], int w, int h) {
		int w1 = pixels.length;
		int h1 = pixels[0].length;
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < h1; i++) {
			for (int j = 0; j < w1; j++) {
				img.setRGB(i, j, pixels[j][i]);
			}
		}

		return img;
	}

	public BufferedImage _getImage(byte pixels[][], int w, int h) {
		int w1 = pixels.length;
		int h1 = pixels[0].length;
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < h1; i++) {
			for (int j = 0; j < w1; j++) {
				img.setRGB(i, j, pixels[j][i]);
			}
		}

		return img;
	}

	public void applyColorScale(int[][] colorPallette, float minValue, float maxValue, BufferedImage image,
			int[][] data, List<PH_ColorScale> scaleList) {
		int height = image.getHeight();
		int width = image.getWidth();
		// System.out.println("height = " + height + " width = " + width);
		// System.out.println("data.length = " + data.length + " data[0].length
		// = " + data[0].length);
		// System.out.println("minValue = " + minValue + " maxValue = " +
		// maxValue);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				float imageData = data[i][j];
				int index = colorScaleReader.getColorIndex(imageData, scaleList);
				Color c = new Color(scaleList.get(index).getRgbValue());
				int rgb = c.getRGB();
				image.setRGB(j, i, rgb);
			}
		}
	}

	public void applyColorScale(float minValue, float maxValue, BufferedImage image, byte[][] data,
			List<PH_ColorScale> scaleList) {
		int height = image.getHeight();
		int width = image.getWidth();
		// System.out.println("height = " + height + " width = " + width);
		// System.out.println("data.length = " + data.length + " data[0].length
		// = " + data[0].length);
		// System.out.println("minValue = " + minValue + " maxValue = " +
		// maxValue);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				float imageData = data[i][j];
				int index = colorScaleReader.getColorIndex(imageData, scaleList);
				Color c = new Color(scaleList.get(index).getRgbValue());
				int rgb = c.getRGB();
				image.setRGB(j, i, rgb);
			}
		}
	}

}

package com.petrabytes.views.wellbore;
import com.vaadin.flow.component.html.Label;

import com.vaadin.flow.component.html.Paragraph;
import java.nio.charset.StandardCharsets;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;


import com.fasterxml.jackson.annotation.JsonFormat.Feature;
import com.helger.css.parser.Node;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.HasComponents;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.UploadI18N;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import com.vaadin.flow.internal.MessageDigestUtil;
import com.vaadin.flow.internal.nodefeature.NodeFeature;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

/**
 * 
 * @author Moumita
 *
 */
public class Blgz_Import_File_Upload {

	private TextArea textArea;
	private TextField textField;
	private String FileName;
	private String currentFilepath;
	public String text;
	

	public Blgz_Import_File_Upload() {
		

          
	}
	
	public Component createComponent(String mimeType, String fileName, InputStream stream) {
		FileName = fileName;

//		if (mimeType.startsWith("application/octet-stream")) {
		if (mimeType.startsWith("text")) {
			 return createTextComponent(stream);
			 
		} else if (mimeType.startsWith("image")) {
			Image image = new Image();
			try {

				byte[] bytes = IOUtils.toByteArray(stream);
				image.getElement().setAttribute("src",
						new StreamResource(fileName, () -> new ByteArrayInputStream(bytes)));
				try (ImageInputStream in = ImageIO.createImageInputStream(new ByteArrayInputStream(bytes))) {
					final Iterator<ImageReader> readers = ImageIO.getImageReaders(in);
					if (readers.hasNext()) {
						ImageReader reader = readers.next();
						try {
							reader.setInput(in);
							image.setWidth(reader.getWidth(0) + "px");
							image.setHeight(reader.getHeight(0) + "px");
						} finally {
							reader.dispose();
						}
					}
				}

			} catch (IOException e) {
				
				e.printStackTrace();
			}

			return image;
		} else if (mimeType.startsWith("application/octet-stream")) {
			return createTextComponent(stream);
		 }  else if (mimeType.startsWith("application/vnd.ms-excel")) {
			 return createTextComponent(stream); 
		 }
		
		Div content = new Div();
		String text = String.format("Mime type: '%s'\nSHA-256 hash: '%s'", mimeType,
				MessageDigestUtil.sha256(stream.toString()));
		content.setText(text);
		
		return content;

	}

	public void showOutput(String text, Component content, HasComponents outputContainer) {
		HtmlComponent p = new HtmlComponent(Tag.P);
		p.getElement().setText(text);
//		outputContainer.add(p);
		outputContainer.add(content);
	}

	private Component createTextComponent(InputStream stream) {
		String text;
		String fileAsString;
		try {
//			fileAsString = FileUtils.readFileToString(uploadedFile);
			text = IOUtils.toString(stream, StandardCharsets.UTF_8).toString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			text = "exception reading stream";
		}

		return new Text(text);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	
	

}

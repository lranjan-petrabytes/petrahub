package com.petrabytes.views.wellbore;

import java.io.File;
import java.util.Random;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.richtexteditor.RichTextEditor;

public class wellCommentsView extends VerticalLayout{
	
	
	private VerticalLayout mainLayout = new VerticalLayout();
	private Button createWellCommentsButton;
	private RichTextEditor wellCommentsRichText = new RichTextEditor();
	private Button saveButton;
	private Button clearbutton;
	private Button Cancelbutton ;
	private Random uniqueID = new Random();
	
	private String  wellId = null;



	
	public  wellCommentsView() {
		
		setUI();
		
	}


	private void setUI() {
		// TODO Auto-generated method stub
		setSizeFull();
		

		wellCommentsRichText.getValue();
		wellCommentsRichText.setId("bgz_well_wellCommentsRichText");
		wellCommentsRichText.getElement().getStyle().set("margin-left", "-20px");
		// To input value from persistence storage, you can set it in Delta format
		wellCommentsRichText.setValue("[{\"attributes\":{\"underline\":true},\"insert\":\"Fight\"},{\"insert\":\" for simplicity\n\"}]");

		// The value can be retrieved as HTML
		wellCommentsRichText.getHtmlValue();
		
		wellCommentsRichText.setSizeFull();
		mainLayout.add(wellCommentsRichText);
		HorizontalLayout WellCommentsMainLayout = new HorizontalLayout();
//		WellCommentsMainLayout.setWidth("100%");
		 
		 saveButton = new Button("Save");
		 saveButton.setId("bgz_well_wellcommentssaveButton");
		 saveButton.addThemeVariants(ButtonVariant.LUMO_SMALL,ButtonVariant.LUMO_PRIMARY);
	//	 saveButton.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
		
		 clearbutton = new Button("Clear");

		 clearbutton.setId("bgz_well_clearbutton");
	//	 clearbutton.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);
		 

		WellCommentsMainLayout.add(saveButton,clearbutton);

//		WellCommentsMainLayout.getStyle().set("padding-left", "78%");

		add(mainLayout,WellCommentsMainLayout);
		setAlignSelf(Alignment.BASELINE, WellCommentsMainLayout); 
	}
}

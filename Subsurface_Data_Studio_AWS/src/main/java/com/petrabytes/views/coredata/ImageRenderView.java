/*package com.petrabytes.views.coredata;

import com.petrabytes.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.PageTitle;

import java.io.ByteArrayInputStream;

import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.StreamResource;

@PageTitle("Image Render")
@Route(value = "image", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class ImageRenderView extends VerticalLayout {

	private Button renderButton = new Button("Render");
	private FlexLayout layout = new FlexLayout();
	private Image image = new Image();

	public ImageRenderView() {
		addClassName("image-render-view");
		this.setSizeFull();
		this.add(renderButton, layout);
		layout.setSizeFull();
//		image.setSizeFull();
		this.expand(layout);
		layout.add(image);

		buttonEvent();
	}

	private void buttonEvent() {
		// TODO Auto-generated method stub
		renderButton.addClickListener(e -> {
			byte[] imageBytes = PB_Image_DB.imageQueryDB();
			if (imageBytes != null) {
				try {
					StreamResource resource = new StreamResource("sample", () -> new ByteArrayInputStream(imageBytes));
					image.setSrc(resource);
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			}
		});
	}

}
*/
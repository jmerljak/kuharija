package si.merljak.magistrska.client.mvp.error;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.mvp.AbstractView;

import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.constants.ImageType;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * 404 page view.
 * 
 * @author Jakob Merljak
 * 
 */
public class NotFoundView extends AbstractView {

	public NotFoundView() {
		Image image = new Image(IMG_BASE_FOLDER + "404.jpg");
		image.setType(ImageType.ROUNDED);
		image.setAltText(""); // nothing to show

		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, messages.oops()));
		main.add(new Paragraph(messages.pageNotFound()));
		main.add(image);
		initWidget(main);
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(null);
		return super.asWidget();
	}
}

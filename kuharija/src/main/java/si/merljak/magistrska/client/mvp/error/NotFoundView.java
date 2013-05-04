package si.merljak.magistrska.client.mvp.error;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.mvp.AbstractView;

import com.github.gwtbootstrap.client.ui.Heading;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * 404 page view.
 * 
 * @author Jakob Merljak
 * 
 */
public class NotFoundView extends AbstractView {

	public NotFoundView() {
		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, messages.oops()));
		main.add(new HTML(messages.pageNotFound()));
		initWidget(main);
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(null);
		return super.asWidget();
	}
}

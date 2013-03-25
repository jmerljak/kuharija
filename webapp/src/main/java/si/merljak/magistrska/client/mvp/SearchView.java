package si.merljak.magistrska.client.mvp;

import java.util.List;

import si.merljak.magistrska.client.i18n.CommonConstants;

import com.github.gwtbootstrap.client.ui.Heading;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class SearchView extends AbstractView {

	// constants & formatters
	private static final CommonConstants constants = GWT.create(CommonConstants.class);

	private static final RootPanel main = RootPanel.get("searchWrapper");

	public SearchView () {
		initWidget(main);
	}

	public void displaySearchResults(List<Long> results) {
		main.clear();
		main.add(new Heading(2, constants.search()));
		for (Long result : results) {
			main.add(new Label(Long.toString(result)));
		}
		setVisible(true);
	}
}

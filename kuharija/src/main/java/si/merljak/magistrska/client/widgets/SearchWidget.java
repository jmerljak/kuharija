package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.client.mvp.search.SearchPresenter;

import com.github.gwtbootstrap.client.ui.AppendButton;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;

public class SearchWidget extends Composite {

	// i18n
	private final CommonConstants constants = Kuharija.constants;

	// widgets
	private final TextBox searchBox = new TextBox();

	public SearchWidget() {
		searchBox.setTitle(constants.searchQuery());
		searchBox.getElement().setAttribute("placeholder", constants.searchQuery());
		searchBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					doSearch();
				}
			}
		});

		Button searchButton = new Button(constants.search());
		searchButton.setStyleName(Constants.BTN);
		searchButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doSearch();
			}
		});

		AppendButton formPanel = new AppendButton();
		formPanel.addStyleName("searchWidget");
		formPanel.add(searchBox);
		formPanel.add(searchButton);
		initWidget(formPanel);
	}

	/**
	 * Initiates search for search string.
	 */
	private void doSearch() {
		SearchPresenter.doSearch(searchBox.getValue());
		searchBox.setText("");
	}

}

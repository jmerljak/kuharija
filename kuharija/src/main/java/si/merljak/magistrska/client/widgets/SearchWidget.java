package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.client.mvp.search.SearchPresenter;

import com.github.gwtbootstrap.client.ui.AppendButton;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.aria.client.Roles;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Composite;

public class SearchWidget extends Composite {

	// i18n
	private final CommonConstants constants = Kuharija.constants;

	// widgets
	private final TextBox searchBox = new TextBox();

	public SearchWidget() {
		searchBox.setPlaceholder(constants.searchQuery());
//		searchBox.setSearchQuery(true);
		searchBox.setAccessKey('/');
		searchBox.setTitle(constants.searchQuery());
		searchBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					doSearch();
				}
			}
		});

		Button searchButton = new Button(constants.search());
		searchButton.setIcon(IconType.SEARCH);
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
		initWidget(new ListItem(formPanel));

		Roles.getSearchRole().set(formPanel.getElement());
	}

	/**
	 * Initiates search for search string.
	 */
	private void doSearch() {
		SearchPresenter.doSearch(searchBox.getValue());
		searchBox.setText("");
	}

}

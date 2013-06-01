package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.CommonConstants;

import com.github.gwtbootstrap.client.ui.AppendButton;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.TextBox;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.aria.client.Roles;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;

/**
 * Search widget.
 * 
 * @author Jakob Merljak
 * 
 */
public class SearchWidget extends Composite implements HasText {

	public interface SearchHandler {
		void doSearch();
	}

	// i18n
	private final CommonConstants constants = Kuharija.constants;

	// widgets
	private final TextBox searchBox = new TextBox();

	// handler
	private final SearchHandler handler;

	/**
	 * Search widget. Search handler parameter must not be {@code null}.
	 * 
	 * @param searchHandler handler
	 */
	public SearchWidget(SearchHandler searchHandler) {
		this.handler = searchHandler;

//		searchBox.setSearchQuery(true);
//		searchBox.setAccessKey('/');
		searchBox.setTitle(constants.searchQuery());
		searchBox.setPlaceholder(constants.searchQuery());
		searchBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					handler.doSearch();
				}
			}
		});

		Button searchButton = new Button(constants.search());
		searchButton.setIcon(IconType.SEARCH);
		searchButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				handler.doSearch();
			}
		});

		// layout
		AppendButton formPanel = new AppendButton();
		formPanel.addStyleName("searchWidget");
		formPanel.add(searchBox);
		formPanel.add(searchButton);
		initWidget(formPanel);

		// ARIA
		Roles.getSearchRole().set(getElement());
	}

	/** Clears search text. */
	public void clear() {
		setText("");
	}

	@Override
	public void setText(String searchString) {
		searchBox.setValue(searchString);
	}

	@Override
	public String getText() {
		return searchBox.getValue();
	}
}

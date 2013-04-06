package si.merljak.magistrska.client.mvp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import si.merljak.magistrska.client.i18n.UrlConstants;
import si.merljak.magistrska.client.i18n.UtensilsConstants;
import si.merljak.magistrska.common.dto.UtensilDto;

import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.github.gwtbootstrap.client.ui.base.UnorderedList;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class UtensilView extends AbstractView {

	// constants & formatters
	private static final UtensilsConstants constants = GWT.create(UtensilsConstants.class);
	private static final UrlConstants urlConstants = GWT.create(UrlConstants.class);
	private static final Map<String, String> utensilMap = constants.utensilsMap();

	private static final RootPanel main = RootPanel.get("utensilWrapper");
	private FlowPanel utensilsIndex;

	public UtensilView () {
		initWidget(main);
	}

	/** Displays single utensil or shows appropriate message if utensil not found. */
	public void displayUtensil(UtensilDto utensil, String name) {
		main.clear();
		if (utensil == null) {
			// no utensil found
			main.add(new Heading(2, messages.oops()));
			main.add(new HTML(messages.utensilNotFoundTry()));
		} else {
			main.add(new Heading(2, utensilMap.get(utensil.getName())));
			main.add(new Image(UTENSIL_IMG_FOLDER + utensil.getImageUrl()));
			main.add(new Label(constants.utensilsDescriptionMap().get(utensil.getName() + "_DESC")));
			String localizedName = utensilMap.get(utensil.getName()).toLowerCase();
			main.add(new Anchor(messages.utensilReadMoreOnWikipedia(localizedName), urlConstants.localWikipediaSearchUrl() + localizedName, "_blank"));
		}

		setVisible(true);
	}

	/** Displays index of utensils. */
	public void displayUtensilsIndex() {
		if (utensilsIndex == null) {
			// lazy load
			initUtensilsIndex();
		}

		main.clear();
		main.add(new Heading(2, constants.utensilsIndex()));
		main.add(utensilsIndex);
		setVisible(true);
	}

	/** Generates localized and alphabetically sorted index of utensils. */ 
	private void initUtensilsIndex() {
		// generate inverse (localized value -> key) utensils map
		Map<String, String> inverseMap = new HashMap<String, String>();
		for (String key : utensilMap.keySet()) {
			inverseMap.put(utensilMap.get(key), key);
		}

		// sort localized values using locale sensitive comparator
		List<String> values = new ArrayList<String>(utensilMap.values());
		Collections.sort(values, new LocaleSensitiveComparator());

		// create index
		utensilsIndex = new FlowPanel();
		utensilsIndex.setStyleName("index");

		String currentLetter = null;
		UnorderedList ul = null;
		for (String localizedName : values) {
			String firstLetter = String.valueOf(localizedName.charAt(0));
			if (currentLetter == null || !firstLetter.equalsIgnoreCase(currentLetter)) {
				// new first letter, add heading and new unordered list instance
				currentLetter = firstLetter.toUpperCase();
				ul = new UnorderedList();
				utensilsIndex.add(new Heading(3, currentLetter));
				utensilsIndex.add(ul);
			}
			ul.add(new ListItem(new Anchor(localizedName, UtensilPresenter.buildUtensilUrl(inverseMap.get(localizedName)))));
		}
	}
}

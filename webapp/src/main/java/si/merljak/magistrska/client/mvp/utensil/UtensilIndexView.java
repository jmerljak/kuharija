package si.merljak.magistrska.client.mvp.utensil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.UtensilsConstants;
import si.merljak.magistrska.client.mvp.AbstractView;
import si.merljak.magistrska.client.utils.LocaleSensitiveComparator;

import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.github.gwtbootstrap.client.ui.base.UnorderedList;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Simple view that lists all utensils in a localized and alphabetically sorted index.
 * 
 * @author Jakob Merljak
 * 
 */
public class UtensilIndexView extends AbstractView {

	// i18n
	private final UtensilsConstants utensilsConstants = Kuharija.utensilsConstants;

	public UtensilIndexView() {
		// create inverse (localized value -> key) utensils map
		BiMap<String, String> utensilMap = HashBiMap.create(utensilsConstants.utensilsMap());
		BiMap<String, String> inverseMap = utensilMap.inverse();;

		// sort localized values using locale sensitive comparator
		List<String> values = new ArrayList<String>(utensilMap.values());
		Collections.sort(values, new LocaleSensitiveComparator());

		// create localized and alphabetically sorted index of utensils
		FlowPanel utensilsIndex = new FlowPanel();
		utensilsIndex.setStyleName("index");

		String currentLetter = null;
		UnorderedList ul = null;
		for (String localizedName : values) {
			String firstLetter = String.valueOf(localizedName.charAt(0));
			if (currentLetter == null || !firstLetter.equalsIgnoreCase(currentLetter)) {
				// new first letter, add heading and new unordered list instance
				currentLetter = firstLetter.toUpperCase();
				utensilsIndex.add(new Heading(HEADING_SIZE + 1, currentLetter));
				ul = new UnorderedList();
				utensilsIndex.add(ul);
			}
			// link to utensil details view
			ul.add(new ListItem(new Anchor(localizedName, UtensilPresenter.buildUtensilUrl(inverseMap.get(localizedName)))));
		}

		// initialize view
		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, constants.utensilsIndex()));
		main.add(utensilsIndex);
		initWidget(main);
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(constants.utensilsIndex());
		return super.asWidget();
	}
}

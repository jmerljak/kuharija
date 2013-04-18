package si.merljak.magistrska.client.mvp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.IngredientsConstants;

import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.github.gwtbootstrap.client.ui.base.UnorderedList;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Simple view that lists all ingredients in a localized and alphabetically sorted index.
 * 
 * @author Jakob Merljak
 * 
 */
public class IngredientIndexView extends AbstractView {

	// i18n
	private final IngredientsConstants ingredientsConstants = Kuharija.ingredientsConstants;

	public IngredientIndexView() {
		// generate inverse (localized value -> key) ingredients map
		Map<String, String> ingredientMap = ingredientsConstants.ingredientMap();
		Map<String, String> inverseMap = new HashMap<String, String>();
		for (String key : ingredientMap.keySet()) {
			inverseMap.put(ingredientMap.get(key), key);
		}

		// sort localized values using locale sensitive comparator
		List<String> values = new ArrayList<String>(ingredientMap.values());
		Collections.sort(values, new LocaleSensitiveComparator());

		// create localized and alphabetically sorted index of ingredients
		FlowPanel ingredientsIndex = new FlowPanel();
		ingredientsIndex.setStyleName("index");

		String currentLetter = null;
		UnorderedList ul = null;
		for (String localizedName : values) {
			String firstLetter = String.valueOf(localizedName.charAt(0));
			if (currentLetter == null || !firstLetter.equalsIgnoreCase(currentLetter)) {
				// new first letter, add heading and new unordered list instance
				currentLetter = firstLetter.toUpperCase();
				ingredientsIndex.add(new Heading(HEADING_SIZE + 1, currentLetter));
				ul = new UnorderedList();
				ingredientsIndex.add(ul);
			}
			// link to ingredient details view
			ul.add(new ListItem(new Anchor(localizedName, IngredientPresenter.buildIngredientUrl(inverseMap.get(localizedName)))));
		}

		// initialize view
		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, ingredientsConstants.ingredientsIndex()));
		main.add(ingredientsIndex);
		initWidget(main);
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(ingredientsConstants.ingredientsIndex());
		return super.asWidget();
	}
}

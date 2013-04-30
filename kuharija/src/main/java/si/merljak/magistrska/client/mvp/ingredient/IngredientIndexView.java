package si.merljak.magistrska.client.mvp.ingredient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.IngredientsConstants;
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
 * Simple view that lists all ingredients in a localized and alphabetically sorted index.
 * 
 * @author Jakob Merljak
 * 
 */
public class IngredientIndexView extends AbstractView {

	// i18n
	private final IngredientsConstants ingredientsConstants = Kuharija.ingredientsConstants;

	public IngredientIndexView() {
		// create inverse (localized value -> key) ingredients map
		BiMap<String, String> ingredientMap = HashBiMap.create(ingredientsConstants.ingredientMap());
		BiMap<String, String> inverseMap = ingredientMap.inverse();

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
		main.add(new Heading(HEADING_SIZE, constants.ingredientsIndex()));
		main.add(ingredientsIndex);
		initWidget(main);
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(constants.ingredientsIndex());
		return super.asWidget();
	}
}

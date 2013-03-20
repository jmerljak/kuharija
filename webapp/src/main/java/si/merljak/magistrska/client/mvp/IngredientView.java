package si.merljak.magistrska.client.mvp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import si.merljak.magistrska.client.KuharijaEntry;
import si.merljak.magistrska.client.i18n.GlobalMessages;
import si.merljak.magistrska.client.i18n.IngredientsConstants;
import si.merljak.magistrska.common.dto.IngredientDto;

import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.github.gwtbootstrap.client.ui.base.UnorderedList;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class IngredientView extends AbstractView {

	// constants & formatters
	private static final IngredientsConstants constants = GWT.create(IngredientsConstants.class);
	private static final GlobalMessages messages = KuharijaEntry.messages;
	private static final Map<String, String> ingredientMap = constants.ingredientMap();

	private static final RootPanel main = RootPanel.get("ingredientWrapper");
	private FlowPanel ingredientsIndex;

	public IngredientView () {
		initWidget(main);
	}

	/** Displays single ingredient or shows appropriate message if ingredient not found. */
	public void displayIngredient(IngredientDto ingredient) {
		main.clear();
		if (ingredient == null) {
			// TODO handle it properly
			main.add(new Label(messages.ingredientNotFound("unknown") + " Try search or "));
			main.add(new Anchor(constants.ingredientsIndex(), "#ingredient"));
		} else {
			main.clear();
			main.add(new Heading(2, ingredientMap.get(ingredient.getName())));
			main.add(new Image(GWT.getHostPageBaseURL() + "img/" + ingredient.getImageUrl()));
			main.add(new Label(constants.ingredientDescriptionMap().get(ingredient.getName() + "_DESC")));
			main.add(new Anchor("Read more on wikipedia", "https://en.wikipedia.org/w/index.php?search=" + ingredientMap.get(ingredient.getName()).toLowerCase(), "_blank"));
		}

		setVisible(true);
	}

	/** Displays index of ingredients. */
	public void displayIngredientsIndex() {
		if (ingredientsIndex == null) {
			// lazy load
			initIngredientsIndex();
		}

		main.clear();
		main.add(new Heading(2, constants.ingredientsIndex()));
		main.add(ingredientsIndex);
		setVisible(true);
	}

	/** Generates localized and alphabetically sorted index of ingredients. */ 
	private void initIngredientsIndex() {
		// generate inverse ingredients map
		Map<String, String> inverseMap = new HashMap<String, String>();
		for (String key : ingredientMap.keySet()) {
			inverseMap.put(ingredientMap.get(key), key);
		}

		// sort localized values using locale sensitive comparator
		List<String> values = new ArrayList<String>(ingredientMap.values());
		Collections.sort(values, new LocaleSensitiveComparator());

		// create index
		ingredientsIndex = new FlowPanel();
		ingredientsIndex.setStyleName("index");

		String currentLetter = null;
		UnorderedList ul = null;
		for (String ingredientName : values) {
			String firstLetter = String.valueOf(ingredientName.charAt(0));
			if (currentLetter == null || !firstLetter.equalsIgnoreCase(currentLetter)) {
				// new first letter, add heading and new unordered list 
				currentLetter = firstLetter.toUpperCase();
				ul = new UnorderedList();
				ingredientsIndex.add(new Heading(3, currentLetter));
				ingredientsIndex.add(ul);
			}
			ul.add(new ListItem(new Anchor(ingredientName, "#ingredient&name=" + inverseMap.get(ingredientName))));
		}
	}

	/** Locale sensitive string comparator */
	class LocaleSensitiveComparator implements Comparator<String> {
		public native int compare(String source, String target) /*-{
			return source.localeCompare(target);
		}-*/;
	}
}

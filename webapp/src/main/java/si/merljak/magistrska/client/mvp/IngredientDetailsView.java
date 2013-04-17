package si.merljak.magistrska.client.mvp;

import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.IngredientsConstants;
import si.merljak.magistrska.client.i18n.UrlConstants;
import si.merljak.magistrska.common.dto.IngredientDto;

import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;

/** 
 * Simple view that displays single ingredient details.
 * 
 * @author Jakob Merljak
 *
 */
public class IngredientDetailsView extends AbstractView {

	// i18n
	private final IngredientsConstants ingredientsConstants = Kuharija.ingredientsConstants;
	private final UrlConstants urlConstants = GWT.create(UrlConstants.class);
	private final Map<String, String> ingredientMap = ingredientsConstants.ingredientMap();
	private final Map<String, String> ingredientDescriptionMap = ingredientsConstants.ingredientDescriptionMap();

	// widgets
	private final Heading heading = new Heading(HEADING_SIZE);
	private final FlowPanel content = new FlowPanel();
	private final Paragraph message404 = new Paragraph(messages.ingredientNotFoundTry());

	public IngredientDetailsView () {
		FlowPanel main = new FlowPanel();
		main.add(heading);
		main.add(content);
		initWidget(main);
	}

	/** 
	 * Displays single ingredient or shows appropriate message if ingredient not found.
	 * @param ingredient DTO or {@code null} if ingredient not found
	 */
	public void displayIngredient(IngredientDto ingredient) {
		content.clear();
		if (ingredient == null) {
			// no ingredient found
			heading.setText(messages.oops());
			content.add(message404);
			Kuharija.setWindowTitle(null);
		} else {
			String ingredientName = ingredient.getName();
			String localizedName = ingredientMap.get(ingredientName);
			String localizedDescription = ingredientDescriptionMap.get(ingredientName + "_DESC");

			// heading and title
			heading.setText(localizedName);
			Kuharija.setWindowTitle(localizedName);
			
			// TODO check for null URL
			Image img = new Image(INGREDIENT_IMG_FOLDER + ingredient.getImageUrl());
			img.setAltText(localizedName);

			content.add(img);
			content.add(new Paragraph(localizedDescription));
			content.add(new Anchor(messages.searchByIngredient(localizedName.toLowerCase()), SearchPresenter.buildSearchByIngredientUrl(ingredientName)));
			content.add(new Anchor(messages.ingredientReadMoreOnWikipedia(localizedName), urlConstants.localWikipediaSearchUrl() + localizedName, "_blank"));
		}
	}
}

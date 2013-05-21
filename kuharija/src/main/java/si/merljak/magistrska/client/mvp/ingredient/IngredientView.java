package si.merljak.magistrska.client.mvp.ingredient;

import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.IngredientsConstants;
import si.merljak.magistrska.client.mvp.AbstractView;
import si.merljak.magistrska.client.mvp.search.SearchPresenter;
import si.merljak.magistrska.common.dto.IngredientDto;

import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.github.gwtbootstrap.client.ui.constants.ImageType;
import com.google.gwt.aria.client.Roles;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Simple view that displays single ingredient details.
 * 
 * @author Jakob Merljak
 * 
 */
public class IngredientView extends AbstractView {

	// i18n
	private final IngredientsConstants ingredientsConstants = Kuharija.ingredientsConstants;
	private final Map<String, String> ingredientMap = ingredientsConstants.ingredientMap();
	private final Map<String, String> ingredientDescriptionMap = ingredientsConstants.ingredientDescriptionMap();

	// widgets
	private final Heading heading = new Heading(HEADING_SIZE);
	private final FlowPanel content = new FlowPanel();
	private final Paragraph message404 = new Paragraph(messages.ingredientNotFoundTry());

	public IngredientView() {
		FlowPanel main = new FlowPanel();
		main.add(heading);
		main.add(content);
		initWidget(main);

		Roles.getArticleRole().set(main.getElement());
	}

	/**
	 * Displays single ingredient or shows appropriate message if ingredient not found.
	 * 
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

			// image
			String imageUrl = ingredient.getImageUrl();
			if (imageUrl != null) {
				Image img = new Image(INGREDIENT_IMG_FOLDER + imageUrl);
				img.setAltText(localizedName);
				img.setType(ImageType.ROUNDED);
				content.add(img);
			}

			// links
			Anchor linkRecipesByIngredient = new Anchor(messages.searchByIngredient(localizedName.toLowerCase()), SearchPresenter.buildSearchByIngredientUrl(ingredientName));
			linkRecipesByIngredient.setStyleName(Constants.BTN);
			linkRecipesByIngredient.addStyleDependentName("success");

			Anchor linkWikipedia = new Anchor(messages.ingredientReadMoreOnWikipedia(), messages.wikipediaSearchUrl(URL.encodeQueryString(localizedName)), "_blank");
			linkWikipedia.setStyleName(Constants.BTN);

			content.add(new Paragraph(localizedDescription));
			content.add(linkRecipesByIngredient);
			content.add(linkWikipedia);
		}
	}
}

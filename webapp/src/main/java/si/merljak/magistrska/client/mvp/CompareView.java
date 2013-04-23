package si.merljak.magistrska.client.mvp;

import java.util.List;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.IngredientsConstants;
import si.merljak.magistrska.common.dto.IngredientDto;
import si.merljak.magistrska.common.dto.RecipeDetailsDto;

import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.base.InlineLabel;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.github.gwtbootstrap.client.ui.base.UnorderedList;
import com.github.gwtbootstrap.client.ui.constants.ImageType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CompareView extends AbstractView {

	// i18n
	private static final IngredientsConstants ingredientsConstants = GWT.create(IngredientsConstants.class);

	// widgets
	private final FlexTable resultsPanel = new FlexTable();

	public CompareView () {
		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, constants.recipeComparison()));
		main.add(resultsPanel);
		initWidget(main);
	}

	public void displaySearchResults(List<RecipeDetailsDto> results) {
		resultsPanel.removeAllRows();

		if (results.isEmpty()) {
			resultsPanel.add(new Label(constants.searchNoResults()));
		}

		int column = 1;
		for (RecipeDetailsDto result : results) {
			String heading = result.getHeading();
			Image image = new Image(RECIPE_THUMB_IMG_FOLDER + result.getImageUrl());
			image.setAltText(heading);
			image.setType(ImageType.ROUNDED);
			Anchor link = new Anchor(heading, RecipePresenter.buildRecipeUrl(result.getId()));

			resultsPanel.setWidget(0, column, link);
			resultsPanel.setWidget(1, column, image);
			resultsPanel.setText(2, column, localizeEnum(result.getDifficulty()));
			resultsPanel.setText(3, column, timeFromMinutes(result.getTimePreparation()));
			resultsPanel.setText(4, column, timeFromMinutes(result.getTimeCooking()));
			resultsPanel.setText(5, column, timeFromMinutes(result.getTimeOverall()));
			resultsPanel.setText(6, column, result.getAuthor());
			UnorderedList ingredients = new UnorderedList();
			for (IngredientDto ingredient : result.getIngredients()) {
				ingredients.add(new ListItem(new InlineLabel(ingredientsConstants.ingredientMap().get(ingredient.getName()))));
			}
			resultsPanel.setWidget(7, column, ingredients);
			column++;
		}
	}

	public void clearSearchResults() {
		resultsPanel.removeAllRows();
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(constants.recipeComparison());
		return super.asWidget();
	}
}

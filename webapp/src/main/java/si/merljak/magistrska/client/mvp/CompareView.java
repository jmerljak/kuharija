package si.merljak.magistrska.client.mvp;

import java.util.List;

import si.merljak.magistrska.client.i18n.IngredientsConstants;
import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.dto.RecipeIngredientDto;

import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.github.gwtbootstrap.client.ui.base.UnorderedList;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class CompareView extends AbstractView {
	private static final IngredientsConstants ingredientsConstants = GWT.create(IngredientsConstants.class);

	private static final RootPanel main = RootPanel.get("compareWrapper");
	
	private FlexTable resultsPanel = new FlexTable();

	public CompareView () {
		main.add(new Heading(2, constants.recipeComparison()));
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
			Image image = new Image(RECIPE_THUMB_IMG_FOLDER + result.getImageUrl());
			Anchor link = new Anchor(result.getHeading(), RecipePresenter.buildRecipeUrl(result.getId()));

			resultsPanel.setWidget(0, column, link);
			resultsPanel.setWidget(1, column, image);
			resultsPanel.setText(2, column, localizeEnum(result.getDifficulty()));
			resultsPanel.setText(3, column, result.getTimePreparation());
			resultsPanel.setText(4, column, result.getTimeCooking());
			resultsPanel.setText(5, column, result.getTimeOverall());
			resultsPanel.setText(6, column, result.getAuthor());
			UnorderedList ingredients = new UnorderedList();
			for (RecipeIngredientDto ingredient : result.getIngredients()) {
				ingredients.add(new ListItem(new Label(ingredientsConstants.ingredientMap().get(ingredient.getName()))));
			}
			resultsPanel.setWidget(7, column, ingredients);
			column++;
		}
		setVisible(true);
	}

	public void clearSearchResults() {
		resultsPanel.removeAllRows();
		setVisible(true);
	}
}

package si.merljak.magistrska.client.mvp;

import java.util.List;
import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.IngredientsConstants;
import si.merljak.magistrska.common.dto.IngredientDto;
import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Season;

import com.github.gwtbootstrap.client.ui.Badge;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.github.gwtbootstrap.client.ui.base.UnorderedList;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.github.gwtbootstrap.client.ui.constants.ImageType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * View for recipe comparison.
 * 
 * @author Jakob Merljak
 * 
 */
public class CompareView extends AbstractView {

	// constants
//	private static final int ROW_TITLE = 0;
	private static final int ROW_IMG = 1;
	private static final int ROW_DIFFICULTY = 2;
	private static final int ROW_TIME_PREPARATION = 3;
	private static final int ROW_TIME_COOKING = 4;
	private static final int ROW_TIME_OVERALL = 5;
	private static final int ROW_CATEGORIES = 6;
	private static final int ROW_INGREDIENTS = 7;
	private static final int ROW_SEASONS = 8;
	private static final int ROW_AUTHOR = 9;

	// i18n
	private final IngredientsConstants ingredientsConstants = GWT.create(IngredientsConstants.class);
	private final Map<String, String> ingredientMap = ingredientsConstants.ingredientMap();

	// widgets
	private final SimplePanel placeholder = new SimplePanel();
	private final Paragraph noResultsMessage = new Paragraph(constants.searchNoResults());

	public CompareView () {
		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, constants.recipeComparison()));
		main.add(placeholder);
		initWidget(main);
		getElement().setId("compareView");
	}

	public void displayResults(List<RecipeDetailsDto> results) {
		placeholder.clear();

		if (results == null || results.isEmpty()) {
			placeholder.setWidget(noResultsMessage);
		} else {
			FlexTable compareTable = new FlexTable();
			compareTable.setStyleName(Constants.TABLE);
			compareTable.addStyleDependentName(Constants.STRIPED);

			// table header
			Element thead = DOM.createTHead();
			compareTable.getElement().insertFirst(thead);
			Element tr = DOM.createTR();
			thead.appendChild(tr);

			// labels
			tr.appendChild(DOM.createTH());
			compareTable.setText(ROW_DIFFICULTY, 0, constants.difficulty());
			compareTable.setText(ROW_TIME_PREPARATION, 0, constants.timePreparation());
			compareTable.setText(ROW_TIME_COOKING, 0, constants.timeCooking());
			compareTable.setText(ROW_TIME_OVERALL, 0, constants.timeOverall());
			compareTable.setText(ROW_AUTHOR, 0, constants.recipeAuthor());
			compareTable.setText(ROW_INGREDIENTS, 0, ingredientsConstants.ingredients());
			compareTable.setText(ROW_SEASONS, 0, constants.seasons());
			compareTable.setText(ROW_CATEGORIES, 0, constants.categories());

			int column = 1;
			for (RecipeDetailsDto result : results) {
				String heading = result.getHeading();
				String recipeUrl = RecipePresenter.buildRecipeUrl(result.getId());
				Element th = DOM.createTH();
				th.appendChild(new Anchor(heading, recipeUrl).getElement());
				tr.appendChild(th);

				// image
				String imageUrl = result.getImageUrl();
				if (imageUrl == null) {
					imageUrl = RECIPE_IMG_FALLBACK;
				}
				Image image = new Image(RECIPE_THUMB_IMG_FOLDER + imageUrl);
				image.setAltText(heading);
				image.setType(ImageType.ROUNDED);

				Anchor imageLink = new Anchor("", recipeUrl);
				imageLink.getElement().appendChild(image.getElement());

//				compareTable.setText(ROW_TITLE, column, result.getSubHeading());
				compareTable.setWidget(ROW_IMG, column, imageLink);
				compareTable.setText(ROW_DIFFICULTY, column, localizeEnum(result.getDifficulty()));
				compareTable.setText(ROW_TIME_PREPARATION, column, timeFromMinutes(result.getTimePreparation()));
				compareTable.setText(ROW_TIME_COOKING, column, timeFromMinutes(result.getTimeCooking()));
				compareTable.setText(ROW_TIME_OVERALL, column, timeFromMinutes(result.getTimeOverall()));
				compareTable.setText(ROW_AUTHOR, column, result.getAuthor());

				// ingredients
				UnorderedList ingredients = new UnorderedList();
				for (IngredientDto ingredient : result.getIngredients()) {
					String name = ingredient.getName();
					String localizedName = ingredientMap.get(name);
					ingredients.add(new ListItem(new Anchor(localizedName, IngredientPresenter.buildIngredientUrl(name))));
				}
				compareTable.setWidget(ROW_INGREDIENTS, column, ingredients);

				// categories
				FlowPanel categories = new FlowPanel();
				for (Category category : result.getCategories()) {
					Badge categoryBadge = new Badge(localizeEnum(category));
					categoryBadge.setStylePrimaryName("category");
					categoryBadge.addStyleDependentName(category.name().toLowerCase());
					categoryBadge.setWordWrap(false);
					Anchor categoryAnchor = new Anchor("", SearchPresenter.buildSearchByCategoryUrl(category));
					categoryAnchor.getElement().appendChild(categoryBadge.getElement());
					categories.add(categoryAnchor);
				}
				compareTable.setWidget(ROW_CATEGORIES, column, categories);

				// seasons
				FlowPanel seasons = new FlowPanel();
				for (Season season : result.getSeasons()) {
					Badge seasonBadge = new Badge(localizeEnum(season));
					seasonBadge.setStylePrimaryName("season");
					seasonBadge.setWordWrap(false);
					seasonBadge.addStyleDependentName(season.name().toLowerCase());
					Anchor seasonAnchor = new Anchor("", SearchPresenter.buildSearchBySeasonUrl(season));
					seasonAnchor.getElement().appendChild(seasonBadge.getElement());
					seasons.add(seasonAnchor);
				}
				compareTable.setWidget(ROW_SEASONS, column, seasons);

				column++;
			}
			placeholder.setWidget(compareTable);
		}
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(constants.recipeComparison());
		return super.asWidget();
	}
}

package si.merljak.magistrska.client.mvp;

import java.util.List;
import java.util.Map;
import java.util.Set;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.handler.PagingHandler;
import si.merljak.magistrska.client.i18n.IngredientsConstants;
import si.merljak.magistrska.client.i18n.UtensilsConstants;
import si.merljak.magistrska.client.widgets.PagingWidget;
import si.merljak.magistrska.common.SearchParameters;
import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.RecipeListDto;
import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.RecipeSortKey;
import si.merljak.magistrska.common.enumeration.Season;

import com.github.gwtbootstrap.client.ui.AppendButton;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.github.gwtbootstrap.client.ui.constants.ImageType;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Generic search view.
 * 
 * @author Jakob Merljak
 * 
 */
public class SearchView extends AbstractView implements PagingHandler {

	// i18n
	private final IngredientsConstants ingredientsConstants = GWT.create(IngredientsConstants.class);
	private final Map<String, String> ingredientMap = ingredientsConstants.ingredientMap();
	private final UtensilsConstants utensilsConstants = Kuharija.utensilsConstants;
	private final Map<String, String> utensilMap = utensilsConstants.utensilsMap();

	// widgets
	private final TextBox searchBox = new TextBox();
	private final FlowPanel advancedFilters = new FlowPanel();
	private final Button clearFiltersButton = new Button(constants.searchFiltersClear());
	private final FlowPanel resultsPanel = new FlowPanel();
	private final PagingWidget pagingWidget = new PagingWidget(this);

	// variables
	private SearchParameters searchParameters;

	public SearchView () {
		searchBox.setTitle(constants.searchQuery());
		searchBox.getElement().setAttribute("placeholder", constants.searchQuery());
		searchBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					doSearch();
				}
			}
		});

		Button searchButton = new Button(constants.search());
		searchButton.setStyleName(Constants.BTN);
		searchButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doSearch();
			}
		});

		AppendButton formPanel = new AppendButton();
		formPanel.add(searchBox);
		formPanel.add(searchButton);

		clearFiltersButton.setStyleName(Constants.BTN);
		clearFiltersButton.addStyleDependentName("link");
		clearFiltersButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				searchParameters = new SearchParameters(null, null);
				advancedFilters.clear();
				doSearch();
			}
		});
		
		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, constants.search()));
		main.add(formPanel);
		main.add(advancedFilters);
		main.add(resultsPanel);
		main.add(pagingWidget);
		initWidget(main);
	}

	private void doSearch() {
		searchParameters.setSearchString(searchBox.getValue());
		SearchPresenter.doSearch(searchParameters);
	}

	public void displaySearchResults(RecipeListDto results, SearchParameters parameters) {
		// clear old data
		clearSearchResults();

		setSearchParameters(parameters);

		List<RecipeDto> recipes = results.getRecipes();
		if (recipes.isEmpty()) {
			resultsPanel.add(new Label(constants.searchNoResults()));
		}

		for (RecipeDto recipe : recipes) {
			String heading = recipe.getHeading();
			String imageUrl = recipe.getImageUrl();
			if (imageUrl == null) {
				imageUrl = RECIPE_IMG_FALLBACK;
			}

			Image image = new Image(RECIPE_THUMB_IMG_FOLDER + imageUrl);
			image.setAltText(heading);
			image.setType(ImageType.POLAROID);

			Anchor link = new Anchor(heading, RecipePresenter.buildRecipeUrl(recipe.getId()));
			link.getElement().appendChild(image.getElement());

			FlowPanel resultEntry = new FlowPanel();
			resultEntry.setStyleName("resultEntry");
			resultEntry.add(link);
			resultEntry.add(new Label(localizeEnum(recipe.getDifficulty())));
			resultEntry.add(new Label(timeFromMinutes(recipe.getTimeOverall())));

			resultsPanel.add(resultEntry);
		}

		// paging
		pagingWidget.setPage(searchParameters.getPage(), searchParameters.getPageSize(), results.getAllCount());
		pagingWidget.setVisible(results.getAllCount() > 0);
	}

	/** 
	 * Display search string, filters, sorting etc.
	 * 
	 * @param searchParameters
	 */
	private void setSearchParameters(SearchParameters parameters) {
		searchParameters = parameters;

		String searchString = searchParameters.getSearchString();
		Set<Difficulty> difficulties = searchParameters.getDifficulties();
		Set<Category> categories = searchParameters.getCategories();
		Set<Season> seasons = searchParameters.getSeasons();
		Set<String> ingredients = searchParameters.getIngredients();
		String utensil = searchParameters.getUtensil();
		RecipeSortKey sortKey = searchParameters.getSortKey();

		if (searchString != null) {
			searchBox.setText(searchString);
		}

		for (Difficulty difficulty : difficulties) {
			// TODO
			advancedFilters.add(new Label(localizeEnum(difficulty)));
		}

		for (Category category : categories) {
			// TODO
			advancedFilters.add(new Label(localizeEnum(category)));
		}

		for (Season season : seasons) {
			// TODO
			advancedFilters.add(new Label(localizeEnum(season)));
		}

		for (String ingredient : ingredients) {
			// TODO
			advancedFilters.add(new Label(ingredientMap.get(ingredient)));
		}

		if (utensil != null) {
			// TODO
			advancedFilters.add(new Label(utensilMap.get(utensil)));
		}

		if (sortKey != null && sortKey != SearchParameters.DEFAULT_SORT_KEY) {
			// TODO
			advancedFilters.add(new Label(constants.sortKeyMap().get(sortKey.name())));
		}

		advancedFilters.add(clearFiltersButton);
	}


	public void clearSearchResults() {
		searchBox.setText("");
		advancedFilters.clear();
		resultsPanel.clear();
		pagingWidget.setVisible(false);
	}

	@Override
	public void onPageChange(long page) {
		searchParameters.setPage(page);
		doSearch();
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(constants.search());
		return super.asWidget();
	}
}

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
import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.Close;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.RadioButton;
import com.github.gwtbootstrap.client.ui.base.InlineLabel;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.github.gwtbootstrap.client.ui.constants.ImageType;
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
	private final IngredientsConstants ingredientsConstants = Kuharija.ingredientsConstants;
	private final UtensilsConstants utensilsConstants = Kuharija.utensilsConstants;
	private final Map<String, String> ingredientMap = ingredientsConstants.ingredientMap();
	private final Map<String, String> utensilMap = utensilsConstants.utensilsMap();
	private final Map<String, String> sortKeyMap = constants.sortKeyMap();

	// widgets
	private final TextBox searchBox = new TextBox();
	private final FlowPanel filtersPanel = new FlowPanel();
	private final InlineLabel advancedSearch = new InlineLabel(constants.searchFilters());
	private final Button filtersToggle = new Button(constants.searchFiltersShow());
	private final Label labelDifficulty = new Label(constants.difficulty());
	private final Label labelCategories = new Label(constants.categories());
	private final Label labelSeasons = new Label(constants.seasons());
	private final Label labelIngredients = new Label(constants.ingredients());
	private final Label labelUtensils = new Label(constants.utensils());
	private final Label labelSortBy = new Label(constants.sortBy());
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

		filtersToggle.setStyleName(Constants.BTN);
		filtersToggle.addStyleDependentName("link");
		filtersToggle.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (filtersPanel.isVisible()) {
					searchParameters = new SearchParameters(null, null);
					filtersPanel.setVisible(false);
					filtersToggle.setText(constants.searchFiltersShow());
					doSearch();
				} else {
					filtersPanel.setVisible(true);
					filtersToggle.setText(constants.searchFiltersClear());
				}
			}
		});
		
		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, constants.search()));
		main.add(formPanel);
		main.add(advancedSearch);
		main.add(filtersToggle);
		main.add(filtersPanel);
		main.add(resultsPanel);
		main.add(pagingWidget);
		initWidget(main);
	}

	private void doSearch() {
		searchParameters.setSearchString(searchBox.getValue());
		SearchPresenter.doSearch(searchParameters);
	}

	/**
	 * Displays search results.
	 * 
	 * @param results list of results
	 * @param parameters search parameters
	 */
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

		// search string
		String searchString = searchParameters.getSearchString();
		if (searchString != null) {
			searchBox.setText(searchString);
		}

		// difficulties
		filtersPanel.add(labelDifficulty);
		Set<Difficulty> difficulties = searchParameters.getDifficulties();
		for (final Difficulty difficulty : Difficulty.values()) {
			final CheckBox checkBox = new CheckBox(localizeEnum(difficulty));
			checkBox.setValue(difficulties.contains(difficulty));
			checkBox.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (checkBox.getValue()) {
						searchParameters.addDifficulty(difficulty);
						searchParameters.setPage(1);
						doSearch();
					} else {
						searchParameters.removeDifficulty(difficulty);
						searchParameters.setPage(1);
						doSearch();
					}
				}
			});
			filtersPanel.add(checkBox);
		}

		// categories
		filtersPanel.add(labelCategories);
		Set<Category> categories = searchParameters.getCategories();
		for (final Category category : Category.values()) {
			final CheckBox checkBox = new CheckBox(localizeEnum(category));
			checkBox.setValue(categories.contains(category));
			checkBox.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (checkBox.getValue()) {
						searchParameters.addCategory(category);
						searchParameters.setPage(1);
						doSearch();
					} else {
						searchParameters.removeCategory(category);
						searchParameters.setPage(1);
						doSearch();
					}
				}
			});
			filtersPanel.add(checkBox);
		}

		// seasons
		filtersPanel.add(labelSeasons);
		Set<Season> seasons = searchParameters.getSeasons();
		for (final Season season : Season.values()) {
			final CheckBox checkBox = new CheckBox(localizeEnum(season));
			checkBox.setValue(seasons.contains(season));
			checkBox.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (checkBox.getValue()) {
						searchParameters.addSeason(season);
						searchParameters.setPage(1);
						doSearch();
					} else {
						searchParameters.removeSeason(season);
						searchParameters.setPage(1);
						doSearch();
					}
				}
			});
			filtersPanel.add(checkBox);
		}

		// ingredients
		filtersPanel.add(labelIngredients);
		Set<String> ingredients = searchParameters.getIngredients();
		for (final String ingredient : ingredients) {
			Close removeIcon = new Close();
			removeIcon.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					searchParameters.removeIngredient(ingredient);
					searchParameters.setPage(1);
					doSearch();
				}
			});
			InlineLabel ingredientLabel = new InlineLabel(ingredientMap.get(ingredient));
			filtersPanel.add(ingredientLabel);
			filtersPanel.add(removeIcon);
		}

		// utensil
		filtersPanel.add(labelUtensils);
		Set<String> utensils = searchParameters.getUtensils();
		for (final String utensil : utensils) {
			Close removeIcon = new Close();
			removeIcon.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					searchParameters.removeUtensil(utensil);
					searchParameters.setPage(1);
					doSearch();
				}
			});
			InlineLabel utensilLabel = new InlineLabel(utensilMap.get(utensil));
			filtersPanel.add(utensilLabel);
			filtersPanel.add(removeIcon);
		}

		filtersPanel.add(labelSortBy);
		final RecipeSortKey sortKey = searchParameters.getSortKey();
		for (final RecipeSortKey key : RecipeSortKey.values()) {
			final RadioButton radioButton = new RadioButton("sortKey", sortKeyMap.get(key.name()));
			radioButton.setValue(key == sortKey);
			radioButton.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					if (key != sortKey) {
						searchParameters.setSortKey(key);
						searchParameters.setPage(1);
						doSearch();
					}
				}
			});
			filtersPanel.add(radioButton);
		}

		if (!difficulties.isEmpty() || !categories.isEmpty() || !seasons.isEmpty() || 
			!ingredients.isEmpty() || !utensils.isEmpty() || sortKey != RecipeSortKey.ID) {
			filtersPanel.setVisible(true);
			filtersToggle.setText(constants.searchFiltersClear());
		} else {
			filtersToggle.setText(constants.searchFiltersShow());
		}
	}

	/** Clears search parameters and results. */
	public void clearSearchResults() {
		searchBox.setText("");
		filtersToggle.setText(constants.searchFiltersShow());
		filtersPanel.clear();
		filtersPanel.setVisible(false);
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

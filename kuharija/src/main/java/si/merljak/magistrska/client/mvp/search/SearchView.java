package si.merljak.magistrska.client.mvp.search;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.handler.PagingHandler;
import si.merljak.magistrska.client.i18n.IngredientsConstants;
import si.merljak.magistrska.client.i18n.UtensilsConstants;
import si.merljak.magistrska.client.mvp.AbstractView;
import si.merljak.magistrska.client.mvp.compare.ComparePresenter;
import si.merljak.magistrska.client.mvp.ingredient.IngredientPresenter;
import si.merljak.magistrska.client.mvp.recipe.RecipePresenter;
import si.merljak.magistrska.client.mvp.utensil.UtensilPresenter;
import si.merljak.magistrska.client.widgets.PagingWidget;
import si.merljak.magistrska.common.SearchParameters;
import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.RecipeListDto;
import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.RecipeSortKey;
import si.merljak.magistrska.common.enumeration.Season;

import com.github.gwtbootstrap.client.ui.AppendButton;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Icon;
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.RadioButton;
import com.github.gwtbootstrap.client.ui.base.IconAnchor;
import com.github.gwtbootstrap.client.ui.base.InlineLabel;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.github.gwtbootstrap.client.ui.constants.IconPosition;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.client.ui.constants.ImageType;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SuggestBox;
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
	private final BiMap<String, String> ingredientMap = HashBiMap.create(ingredientsConstants.ingredientMap());
	private final BiMap<String, String> ingredientInverseMap = ingredientMap.inverse();
	private final BiMap<String, String> utensilMap = HashBiMap.create(utensilsConstants.utensilsMap());
	private final BiMap<String, String> utensilInverseMap = utensilMap.inverse();
	private final Map<String, String> sortKeyMap = constants.sortKeyMap();

	// widgets
	private final TextBox searchBox = new TextBox();

	private final InlineLabel labelAdvancedSearch = new InlineLabel(constants.searchFilters());
	private final com.google.gwt.user.client.ui.Button filtersToggle = new com.google.gwt.user.client.ui.Button(constants.searchFiltersShow());
	private final FlowPanel filtersPanel = new FlowPanel();

	private final Label labelDifficulty = new Label(constants.difficulty());
	private final Label labelCategories = new Label(constants.categories());
	private final Label labelSeasons = new Label(constants.seasons());
	private final Label labelIngredients = new Label(constants.ingredients());
	private final Label labelUtensils = new Label(constants.utensils());

	private SuggestBox ingredientSuggest;
	private SuggestBox utensilSuggest;

	private final Label labelSortBy = new Label(constants.sortBy());
	private final FlowPanel sortPanel = new FlowPanel();

	private final FlowPanel resultsPanel = new FlowPanel();
	private final PagingWidget pagingWidget = new PagingWidget(this);
	private final Button compareLink = new Button(constants.recipeCompare());

	// variables
	private SearchParameters searchParameters = new SearchParameters(null, null);
	private Set<Long> selectedRecipes = new HashSet<Long>();

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
		searchButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				doSearch();
			}
		});

		AppendButton formPanel = new AppendButton();
		formPanel.add(searchBox);
		formPanel.add(searchButton);

		// filters
		filtersToggle.setStyleName(Constants.BTN);
		filtersToggle.addStyleName(ButtonType.LINK.get());
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

		labelDifficulty.setStyleName("filterLabel");
		labelCategories.setStyleName("filterLabel");
		labelSeasons.setStyleName("filterLabel");
		labelIngredients.setStyleName("filterLabel");
		labelUtensils.setStyleName("filterLabel");
		labelSortBy.setStyleName("filterLabel");
		filtersPanel.getElement().setId("filtersPanel");
		sortPanel.getElement().setId("sortPanel");

		// suggest boxes
		MultiWordSuggestOracle utensilSuggestOracle = new MultiWordSuggestOracle();
		utensilSuggestOracle.addAll(utensilInverseMap.keySet());
		utensilSuggest = new SuggestBox(utensilSuggestOracle);
		utensilSuggest.getElement().setPropertyString("placeholder", constants.searchAndAddUtensil());
		utensilSuggest.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					String value = utensilSuggest.getValue();
					if (utensilInverseMap.containsKey(value)) {
						searchParameters.addUtensil(utensilInverseMap.get(value));
						doSearch();
					} else {
						// TODO show error
					}
				}
			}
		});
		utensilSuggest.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				String value = event.getValue();
				if (utensilInverseMap.containsKey(value)) {
					searchParameters.addUtensil(utensilInverseMap.get(value));
					doSearch();
				}
			}
		});

		MultiWordSuggestOracle ingredientSuggestOracle = new MultiWordSuggestOracle();
		ingredientSuggestOracle.addAll(ingredientInverseMap.keySet());
		ingredientSuggest = new SuggestBox(ingredientSuggestOracle);
		ingredientSuggest.getElement().setPropertyString("placeholder", constants.searchAndAddIngredient());
		ingredientSuggest.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					String value = ingredientSuggest.getValue();
					if (ingredientInverseMap.containsKey(value)) {
						searchParameters.addIngredient(ingredientInverseMap.get(value));
						doSearch();
					} else {
						// TODO show error
					}
				}
			}
		});
		ingredientSuggest.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				String value = event.getValue();
				if (ingredientInverseMap.containsKey(value)) {
					searchParameters.addIngredient(ingredientInverseMap.get(value));
					doSearch();
				}
			}
		});

		compareLink.setIcon(IconType.SHARE_ALT);
		compareLink.setIconPosition(IconPosition.RIGHT);
		compareLink.setType(ButtonType.SUCCESS);
		compareLink.addStyleName(Constants.DISABLED);
		compareLink.addStyleName("pull-right");
		compareLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (selectedRecipes.size() > 1) {
					ComparePresenter.compare(selectedRecipes);
				}
			}
		});

		// layout
		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, constants.search()));
		main.add(formPanel);
		main.add(labelAdvancedSearch);
		main.add(filtersToggle);
		main.add(filtersPanel);
		main.add(resultsPanel);
		main.add(pagingWidget);
		main.add(compareLink);
		initWidget(main);
	}

	/** 
	 * Gets search string from input box and initiates search (other search parameters remain intact).
	 */
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
		selectedRecipes.clear();
		compareLink.addStyleName(Constants.DISABLED);
		clearSearchResults();
		Heading headingResults = new Heading(HEADING_SIZE + 1, constants.searchResults());
		headingResults.setStyleName("visuallyhidden");
		resultsPanel.add(headingResults);
		resultsPanel.add(sortPanel);

		setSearchParameters(parameters);
		searchForIngredientOrUtensil(parameters.getSearchString());

		List<RecipeDto> recipes = results.getRecipes();
		if (recipes.isEmpty()) {
			resultsPanel.add(new Label(constants.searchNoResults()));
		}
		final boolean isComparePossible = recipes.size() > 1;
		compareLink.setVisible(isComparePossible);
		labelAdvancedSearch.setVisible(true);
		filtersToggle.setVisible(true);

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

			Label timeOverall = new Label(" " + timeFromMinutes(recipe.getTimeOverall()));
			timeOverall.setTitle(constants.timeOverall());
			timeOverall.getElement().insertFirst(new Icon(IconType.TIME).getElement());
			resultEntry.add(timeOverall);

			if (isComparePossible) {
				final long recipeId = recipe.getId();
				final CheckBox checkbox = new CheckBox(constants.recipeComparisonSelect());
				checkbox.addClickHandler(new ClickHandler() {
					@Override
					public void onClick(ClickEvent event) {
						if (checkbox.getValue()) {
							selectedRecipes.add(recipeId);
						} else {
							selectedRecipes.remove(recipeId);
						}
						if (selectedRecipes.size() > 1) {
							compareLink.setEnabled(true);
							compareLink.removeStyleName(Constants.DISABLED);
						} else {
							compareLink.setEnabled(false);
							compareLink.addStyleName(Constants.DISABLED);
						}
					}
				});
				resultEntry.add(checkbox);
			}

			resultsPanel.add(resultEntry);
		}

		// paging
		pagingWidget.setPage(searchParameters.getPage(), searchParameters.getPageSize(), results.getAllCount());
		pagingWidget.setVisible(results.getAllCount() > 0);
	}

	private void searchForIngredientOrUtensil(String searchString) {
		if (searchString == null || searchString.length() < 3) {
			return;
		}

		// search for ingredient by key word
		if (ingredientInverseMap.containsKey(searchString)) {
			resultsPanel.add(new Anchor(searchString, IngredientPresenter.buildIngredientUrl(ingredientInverseMap.get(searchString))));
		} else {
			for (String key : ingredientInverseMap.keySet()) {
				if (key.toLowerCase().contains(searchString.toLowerCase())) {
					resultsPanel.add(new Anchor(key, IngredientPresenter.buildIngredientUrl(ingredientInverseMap.get(key))));
				}
			}
		}

		// search for ingredient by key word
		if (utensilInverseMap.containsKey(searchString)) {
			resultsPanel.add(new Anchor(searchString, UtensilPresenter.buildUtensilUrl(utensilInverseMap.get(searchString))));
		} else {
			for (String key : utensilInverseMap.keySet()) {
				if (key.toLowerCase().contains(searchString.toLowerCase())) {
					resultsPanel.add(new Anchor(key, UtensilPresenter.buildUtensilUrl(utensilInverseMap.get(key))));
				}
			}
		}
		
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
		FlowPanel filterDifficulty = new FlowPanel();
		filtersPanel.add(filterDifficulty);
		filterDifficulty.add(labelDifficulty);
		Set<Difficulty> difficulties = searchParameters.getDifficulties();
		for (final Difficulty difficulty : Difficulty.values()) {
			boolean active = difficulties.contains(difficulty);
			final CheckBox checkBox = new CheckBox(localizeEnum(difficulty));
			checkBox.setStyleName("badge");
			checkBox.setStyleDependentName("active", active);
			checkBox.setValue(active);
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
			filterDifficulty.add(checkBox);
		}

		// categories
		FlowPanel filterCategory = new FlowPanel();
		filtersPanel.add(filterCategory);
		filterCategory.add(labelCategories);
		Set<Category> categories = searchParameters.getCategories();
		for (final Category category : Category.values()) {
			boolean active = categories.contains(category);
			final CheckBox checkBox = new CheckBox(localizeEnum(category));
			checkBox.setStyleName("badge");
			checkBox.setStyleDependentName("active", active);
			checkBox.setValue(active);
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
			filterCategory.add(checkBox);
		}

		// seasons
		FlowPanel filterSeason = new FlowPanel();
		filtersPanel.add(filterSeason);
		filterSeason.add(labelSeasons);
		Set<Season> seasons = searchParameters.getSeasons();
		for (final Season season : Season.values()) {
			boolean active = seasons.contains(season);
			final CheckBox checkBox = new CheckBox(localizeEnum(season));
			checkBox.setStyleName("badge");
			checkBox.setStyleDependentName("active", active);
			checkBox.setValue(active);
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
			filterSeason.add(checkBox);
		}

		// ingredients
		FlowPanel filterIngredient = new FlowPanel();
		filtersPanel.add(filterIngredient);
		filterIngredient.add(labelIngredients);
		Set<String> ingredients = searchParameters.getIngredients();
		for (final String ingredient : ingredients) {
			IconAnchor removeIcon = new IconAnchor();
			removeIcon.setIcon(IconType.REMOVE);
			removeIcon.setText(constants.remove());
			removeIcon.setStyleName("removeFilter");
			removeIcon.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					searchParameters.removeIngredient(ingredient);
					searchParameters.setPage(1);
					doSearch();
				}
			});
			InlineLabel ingredientLabel = new InlineLabel(ingredientMap.get(ingredient) + " ");
			filterIngredient.add(ingredientLabel);
			filterIngredient.add(removeIcon);
			filterIngredient.add(new InlineLabel(", "));
		}
		ingredientSuggest.setValue("");
		filterIngredient.add(ingredientSuggest);

		// utensil
		FlowPanel filterUtensil = new FlowPanel();
		filtersPanel.add(filterUtensil);
		filterUtensil.add(labelUtensils);
		Set<String> utensils = searchParameters.getUtensils();
		for (final String utensil : utensils) {
			IconAnchor removeIcon = new IconAnchor();
			removeIcon.setIcon(IconType.REMOVE);
			removeIcon.setText(constants.remove());
			removeIcon.setStyleName("removeFilter");
			removeIcon.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					searchParameters.removeUtensil(utensil);
					searchParameters.setPage(1);
					doSearch();
				}
			});
			InlineLabel utensilLabel = new InlineLabel(utensilMap.get(utensil) + " ");
			filterUtensil.add(utensilLabel);
			filterUtensil.add(removeIcon);
			filterUtensil.add(new InlineLabel(", "));
		}
		utensilSuggest.setValue("");
		filterUtensil.add(utensilSuggest);

		sortPanel.add(labelSortBy);
		final RecipeSortKey sortKey = searchParameters.getSortKey();
		for (final RecipeSortKey key : RecipeSortKey.values()) {
			boolean active = key == sortKey;
			final RadioButton radioButton = new RadioButton("sortKey", sortKeyMap.get(key.name()));
			radioButton.setStyleName("badge");
			radioButton.setStyleDependentName("active", active);
			radioButton.setValue(active);
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
			sortPanel.add(radioButton);
		}

		if (!difficulties.isEmpty() || !categories.isEmpty() || !seasons.isEmpty() || 
			!ingredients.isEmpty() || !utensils.isEmpty()) {
			filtersPanel.setVisible(true);
			filtersToggle.setText(constants.searchFiltersClear());
		} else {
			filtersToggle.setText(constants.searchFiltersShow());
		}
	}

	/** Clears search parameters and results. */
	public void clearSearchResults() {
		searchBox.setText("");
		labelAdvancedSearch.setVisible(false);
		filtersToggle.setVisible(false);
		filtersToggle.setText(constants.searchFiltersShow());
		filtersPanel.clear();
		filtersPanel.setVisible(false);
		sortPanel.clear();
		resultsPanel.clear();
		pagingWidget.setVisible(false);
		compareLink.setVisible(false);
	}

	@Override
	public void changePage(long page) {
		searchParameters.setPage(page);
		doSearch();
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(constants.search());
		return super.asWidget();
	}
}

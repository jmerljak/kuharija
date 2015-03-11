package si.merljak.magistrska.client.mvp.search;

import java.util.ArrayList;
import java.util.Collections;
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
import si.merljak.magistrska.client.utils.EnumUtils;
import si.merljak.magistrska.client.utils.LocaleSensitiveComparator;
import si.merljak.magistrska.client.widgets.PagingWidget;
import si.merljak.magistrska.client.widgets.SearchWidget;
import si.merljak.magistrska.common.SearchParameters;
import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.RecipeListDto;
import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.RecipeSortKey;
import si.merljak.magistrska.common.enumeration.Season;

import com.github.gwtbootstrap.client.ui.Alert;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.Column;
import com.github.gwtbootstrap.client.ui.Emphasis;
import com.github.gwtbootstrap.client.ui.FluidRow;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Icon;
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.ListBox;
import com.github.gwtbootstrap.client.ui.base.InlineLabel;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.github.gwtbootstrap.client.ui.base.UnorderedList;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.github.gwtbootstrap.client.ui.constants.IconPosition;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.client.ui.constants.ImageType;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.watopi.chosen.client.gwt.ChosenListBox;

/**
 * Generic search view.
 *
 * @author Jakob Merljak
 *
 */
public class SearchView extends AbstractView implements PagingHandler, SearchWidget.SearchHandler {

	// i18n
	private final IngredientsConstants ingredientsConstants = Kuharija.ingredientsConstants;
	private final UtensilsConstants utensilsConstants = Kuharija.utensilsConstants;
	private final BiMap<String, String> ingredientMap = HashBiMap.create(ingredientsConstants.ingredientMap());
	private final BiMap<String, String> ingredientInverseMap = ingredientMap.inverse();
	private final BiMap<String, String> utensilMap = HashBiMap.create(utensilsConstants.utensilsMap());
	private final BiMap<String, String> utensilInverseMap = utensilMap.inverse();
	private final Map<String, String> sortKeyMap = constants.sortKeyMap();

	// widgets
	private final SearchWidget searchWidget = new SearchWidget(this);
	private final FlowPanel searchPanel = new FlowPanel();

	private final Heading headingResults;
	private final FluidRow resultsPanel = new FluidRow();
	private final Column resultsMain = new Column(10);
	private final Column resultsLexicon = new Column(2);
	private final FlowPanel resultsRecipes = new FlowPanel();
	private final FlowPanel resultsUtensils = new FlowPanel();
	private final FlowPanel resultsIngredients = new FlowPanel();
	private final UnorderedList resultsUtensilList = new UnorderedList();
	private final UnorderedList resultsIngredientList = new UnorderedList();

	private final FlowPanel sortPanel = new FlowPanel();
	private final ListBox sortListBox = new ListBox();

	private final FlowPanel filtersContainer = new FlowPanel();
	private final FluidRow filtersPanel = new FluidRow();
	private final ChosenListBox filterDifficulty;
	private final ChosenListBox filterCategory;
	private final ChosenListBox filterSeason;
	private final ChosenListBox filterIngredient;

	// TODO should be real buttons (not bootstrap's "buttons" = links), using it for convenience
	private final Button filtersShowButton = new Button(constants.searchAdvanced() + " ");
	private final Button filtersHideButton = new Button(constants.searchBasic() + " ");

	private final Label labelUtensils = new Label(constants.utensils());
	private final FlowPanel filterUtensil = new FlowPanel();

	private final PagingWidget pagingWidget = new PagingWidget(this);
	private final Button compareLink = new Button(constants.recipeCompare());

	// variables
	private SearchParameters searchParameters = new SearchParameters(null, null);
	private final Set<Long> selectedRecipes = new HashSet<Long>();

	public SearchView () {
		// headings
		Heading heading = new Heading(HEADING_SIZE, constants.search());
		headingResults = new Heading(HEADING_SIZE + 1, constants.searchResults());
		Heading headingRecipes = new Heading(HEADING_SIZE + 2, constants.recipes());
		Heading headingLexicon = new Heading(HEADING_SIZE + 2, constants.lexicon());
		Heading headingIngredients = new Heading(HEADING_SIZE + 3, constants.ingredients());
		Heading headingUtensils = new Heading(HEADING_SIZE + 3, constants.utensils());
		headingResults.setStyleName(Kuharija.CSS_VISUALLY_HIDDEN);
		headingLexicon.setStyleName(Kuharija.CSS_VISUALLY_HIDDEN);

		// filters show/hide
		filtersShowButton.setType(ButtonType.LINK);
		filtersShowButton.setIcon(IconType.CHEVRON_DOWN);
		filtersShowButton.setIconPosition(IconPosition.RIGHT);
		filtersShowButton.setTitle(constants.searchToggleInfo());
		filtersShowButton.getElement().setId("filtersShowButton");
		filtersShowButton.addStyleName("hasBoxShaddow");
		filtersShowButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				filtersShowButton.setVisible(false);
				filtersContainer.setVisible(true);
				filtersHideButton.setVisible(true);
			}
		});

		filtersHideButton.setType(ButtonType.LINK);
		filtersHideButton.setIcon(IconType.CHEVRON_UP);
		filtersHideButton.setIconPosition(IconPosition.RIGHT);
		filtersHideButton.setTitle(constants.searchToggleInfo());
		filtersHideButton.getElement().setId("filtersHideButton");
		filtersHideButton.addStyleName("hasBoxShaddow");
		filtersHideButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				searchParameters = new SearchParameters(null, null);
				filtersContainer.setVisible(false);
				filterDifficulty.clear();
				filterCategory.clear();
				filterSeason.clear();
				filterIngredient.clear();
				filtersHideButton.setVisible(false);
				filtersShowButton.setVisible(true);
				doSearch(searchWidget.getText());
			}
		});

		// filters
		Label labelDifficulty = new Label(constants.difficulty());
		Label labelCategories = new Label(constants.categories());
		Label labelSeasons = new Label(constants.seasons());
		Label labelIngredients = new Label(constants.searchByAllIngredients());

		labelDifficulty.setStyleName("filterLabel");
		labelCategories.setStyleName("filterLabel");
		labelSeasons.setStyleName("filterLabel");
		labelIngredients.setStyleName("filterLabel");
		labelUtensils.setStyleName("filterLabel");

		filterDifficulty = new ChosenListBox(true);
		filterDifficulty.setDisableSearchThreshold(7);
		filterDifficulty.setNoResultsText(constants.searchNoResultsForQuery() + " ");
		filterDifficulty.setPlaceholderText(constants.searchFilterAll());
		filterDifficulty.setSingleBackstrokeDelete(true);

		filterCategory = new ChosenListBox(true);
		filterCategory.setDisableSearchThreshold(7);
		filterCategory.setNoResultsText(constants.searchNoResultsForQuery() + " ");
		filterCategory.setPlaceholderText(constants.searchFilterAll());
		filterCategory.setSingleBackstrokeDelete(true);

		filterSeason = new ChosenListBox(true);
		filterSeason.setDisableSearchThreshold(7);
		filterSeason.setNoResultsText(constants.searchNoResultsForQuery() + " ");
		filterSeason.setPlaceholderText(constants.searchFilterAll());
		filterSeason.setSingleBackstrokeDelete(true);

		filterIngredient = new ChosenListBox(true);
		filterIngredient.setDisableSearchThreshold(7);
		filterIngredient.setNoResultsText(constants.searchNoResultsForQuery() + " ");
		filterIngredient.setPlaceholderText(constants.searchAndAddIngredient());
		filterIngredient.setSingleBackstrokeDelete(true);

		setSearchParameters(searchParameters);

		// filters layout
		Column filterPanelDifficulty = new Column(3);
		filterPanelDifficulty.add(labelDifficulty);
		filterPanelDifficulty.add(filterDifficulty);

		Column filterPanelCategories = new Column(3);
		filterPanelCategories.add(labelCategories);
		filterPanelCategories.add(filterCategory);

		Column filterPanelSeasons = new Column(3);
		filterPanelSeasons.add(labelSeasons);
		filterPanelSeasons.add(filterSeason);

		Column filterPanelIngredients = new Column(3);
		filterPanelIngredients.add(labelIngredients);
		filterPanelIngredients.add(filterIngredient);

		filtersPanel.add(filterPanelDifficulty);
		filtersPanel.add(filterPanelCategories);
		filtersPanel.add(filterPanelSeasons);
		filtersPanel.add(filterPanelIngredients);

		filtersContainer.addStyleName("filtersPanel");
		filtersContainer.addStyleName("hasBoxShaddow");
		filtersContainer.add(new Alert(messages.tipSearch(), AlertType.INFO));
		filtersContainer.add(filtersPanel);

		// sorting
		for (RecipeSortKey key : RecipeSortKey.values()) {
			String name = key.name();
			sortListBox.addItem(sortKeyMap.get(name), name);
		}
		sortListBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				RecipeSortKey selectedKey = RecipeSortKey.valueOf(sortListBox.getValue());
				if (selectedKey != searchParameters.getSortKey()) {
					searchParameters.setSortKey(selectedKey);
					searchParameters.setPage(1);
					doSearch(searchWidget.getText());
				}
			}
		});
		sortPanel.add(new InlineLabel(constants.sortBy()));
		sortPanel.add(sortListBox);
		sortPanel.add(compareLink);

		// compare link
		compareLink.setIcon(IconType.CHEVRON_RIGHT);
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

		// search panel
		SimplePanel searchWidgetHolder = new SimplePanel(searchWidget);
		searchWidgetHolder.setStyleName("searchWidgetHolder");

		searchPanel.setStyleName("searchPanel");
		searchPanel.add(searchWidgetHolder);
		searchPanel.add(filtersShowButton);
		searchPanel.add(filtersContainer);
		searchPanel.add(filtersHideButton);

		// results panels
		resultsPanel.add(resultsLexicon);
		resultsPanel.add(resultsMain);

		resultsLexicon.addStyleName("resultsPanel");
		resultsLexicon.addStyleName("hasBoxShaddow");
		resultsLexicon.add(headingLexicon);
		resultsLexicon.add(new Emphasis(messages.didYouSearchFor()));
		resultsLexicon.add(resultsIngredients);
		resultsLexicon.add(resultsUtensils);

		resultsIngredients.add(headingIngredients);
		resultsIngredients.add(resultsIngredientList);

		resultsUtensils.add(headingUtensils);
		resultsUtensils.add(resultsUtensilList);

		resultsMain.addStyleName("resultsPanel");
		resultsMain.addStyleName("hasBoxShaddow");
		resultsMain.add(headingRecipes);
		resultsMain.add(sortPanel);
		resultsMain.add(resultsRecipes);
		resultsMain.add(pagingWidget);

		// layout
		FlowPanel main = new FlowPanel();
		main.add(heading);
		main.add(searchPanel);
		main.add(headingResults);
		main.add(resultsPanel);
		initWidget(main);
	}

	@Override
	public void doSearch(String searchString) {
		searchParameters.setSearchString(searchString);

		searchParameters.clearDifficulties();
		for (String difficulty : filterDifficulty.getValues()) {
			searchParameters.addDifficulty(Difficulty.valueOf(difficulty));
		}

		searchParameters.clearCategories();
		for (String category : filterCategory.getValues()) {
			searchParameters.addCategory(Category.valueOf(category));
		}

		searchParameters.clearSeasons();
		for (String season : filterSeason.getValues()) {
			searchParameters.addSeason(Season.valueOf(season));
		}

		searchParameters.clearIngredients();
		for (String ingredient : filterIngredient.getValues()) {
			searchParameters.addIngredient(ingredient);
		}

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

		setSearchParameters(parameters);
		searchForIngredientOrUtensil(parameters.getSearchString());

		List<RecipeDto> recipes = results.getRecipes();
		if (recipes.isEmpty()) {
			resultsRecipes.add(new Label(constants.searchNoResults()));
		}

		boolean isComparePossible = recipes.size() > 1;
		compareLink.setVisible(isComparePossible);
		sortPanel.setVisible(isComparePossible);

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
			resultEntry.add(new Label(EnumUtils.localizeEnum(recipe.getDifficulty())));

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

			resultsRecipes.add(resultEntry);
		}

		// paging
		long allCount = results.getAllCount();
		pagingWidget.setPage(searchParameters.getPage(), searchParameters.getPageSize(), (int) allCount);
		pagingWidget.setVisible(allCount > 0);
		headingResults.setVisible(true);
		resultsMain.setVisible(true);
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
			searchWidget.setText(searchString);
		} else {
			searchWidget.clear();
		}

		// difficulties
		filterDifficulty.clear(false);
		Set<Difficulty> difficulties = searchParameters.getDifficulties();
		for (final Difficulty difficulty : Difficulty.values()) {
			boolean active = difficulties.contains(difficulty);
			filterDifficulty.addItem(EnumUtils.localizeEnum(difficulty), difficulty.name());
			filterDifficulty.setItemSelected(filterDifficulty.getItemCount() - 1, active);
		}
		filterDifficulty.update();

		// categories
		filterCategory.clear(false);
		Set<Category> categories = searchParameters.getCategories();
		for (final Category category : Category.values()) {
			boolean active = categories.contains(category);
			filterCategory.addItem(EnumUtils.localizeEnum(category), category.name());
			filterCategory.setItemSelected(filterCategory.getItemCount() - 1, active);
		}
		filterCategory.update();

		// seasons
		filterSeason.clear(false);
		Set<Season> seasons = searchParameters.getSeasons();
		for (final Season season : Season.values()) {
			if (season == Season.ALLYEAR) {
				continue;
			}

			boolean active = seasons.contains(season);
			filterSeason.addItem(EnumUtils.localizeEnum(season), season.name());
			filterSeason.setItemSelected(filterSeason.getItemCount() - 1, active);
		}
		filterSeason.update();

		// ingredients
		filterIngredient.clear(false);
		Set<String> ingredients = searchParameters.getIngredients();
		// sort localized values using locale sensitive comparator
		List<String> localizedIngredientList = new ArrayList<String>(ingredientMap.values());
		Collections.sort(localizedIngredientList, new LocaleSensitiveComparator());
		for (String localizedIngredient : localizedIngredientList) {
			String ingredient = ingredientInverseMap.get(localizedIngredient);
			boolean active = ingredients.contains(ingredient);
			filterIngredient.addItem(localizedIngredient, ingredient);
			filterIngredient.setItemSelected(filterIngredient.getItemCount() - 1, active);
		}
		filterIngredient.update();

		// utensil
		Set<String> utensils = searchParameters.getUtensils();
		if (!utensils.isEmpty()) {
			filterUtensil.add(labelUtensils);
			for (final String utensil : utensils) {
				final FlowPanel utensilEntry = new FlowPanel();

				InlineLabel utensilLabel = new InlineLabel(utensilMap.get(utensil) + " ");
				utensilEntry.add(utensilLabel);

				filterUtensil.add(utensilEntry);
			}
			filtersContainer.add(filterUtensil);
		}

		// sort
		sortListBox.setSelectedValue(searchParameters.getSortKey().name());

		if (!difficulties.isEmpty() || !categories.isEmpty() || !seasons.isEmpty() ||
			!ingredients.isEmpty() || !utensils.isEmpty()) {
			filtersContainer.setVisible(true);
			filtersHideButton.setVisible(true);
		} else {
			filtersShowButton.setVisible(true);
		}
	}

	private void searchForIngredientOrUtensil(String searchString) {
		if (searchString == null || searchString.length() < 3) {
			// skip very short words
			return;
		}

		// search for ingredient by key word
		if (ingredientInverseMap.containsKey(searchString)) {
			resultsIngredientList.add(new ListItem(new Anchor(searchString, IngredientPresenter.buildIngredientUrl(ingredientInverseMap.get(searchString)))));
		} else {
			for (String key : ingredientInverseMap.keySet()) {
				if (key.toLowerCase().contains(searchString.toLowerCase())) {
					resultsIngredientList.add(new ListItem(new Anchor(key, IngredientPresenter.buildIngredientUrl(ingredientInverseMap.get(key)))));
				}
			}
		}

		// search for ingredient by key word
		if (utensilInverseMap.containsKey(searchString)) {
			resultsUtensilList.add(new ListItem(new Anchor(searchString, UtensilPresenter.buildUtensilUrl(utensilInverseMap.get(searchString)))));
		} else {
			for (String key : utensilInverseMap.keySet()) {
				if (key.toLowerCase().contains(searchString.toLowerCase())) {
					resultsUtensilList.add(new ListItem(new Anchor(key, UtensilPresenter.buildUtensilUrl(utensilInverseMap.get(key)))));
				}
			}
		}

		resultsIngredients.setVisible(resultsIngredientList.getWidgetCount() > 0);
		resultsUtensils.setVisible(resultsUtensilList.getWidgetCount() > 0);
		if (resultsIngredients.isVisible() || resultsUtensils.isVisible()) {
			resultsMain.setSize(10);
			resultsPanel.insert(resultsLexicon, 0);
		}
	}

	/** Clears search parameters and results. */
	public void clearSearchResults() {
		searchWidget.clear();
		filtersShowButton.setVisible(false);
		filtersHideButton.setVisible(false);
		filtersContainer.setVisible(false);

		headingResults.setVisible(false);
		resultsPanel.remove(resultsLexicon);
		resultsMain.setSize(12);
		resultsMain.setVisible(false);
		resultsRecipes.clear();
		resultsIngredients.setVisible(false);
		resultsIngredientList.clear();
		resultsUtensils.setVisible(false);
		resultsUtensilList.clear();

		filterUtensil.clear();
		filtersContainer.remove(filterUtensil);

		pagingWidget.setVisible(false);
		compareLink.setVisible(false);
	}

	@Override
	public void changePage(int page) {
		searchParameters.setPage(page);
		doSearch(searchWidget.getText());
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(constants.search());
		return super.asWidget();
	}
}

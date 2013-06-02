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
import si.merljak.magistrska.client.widgets.SearchWidget;
import si.merljak.magistrska.common.SearchParameters;
import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.RecipeListDto;
import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.RecipeSortKey;
import si.merljak.magistrska.common.enumeration.Season;

import com.github.gwtbootstrap.client.ui.Alert;
import com.github.gwtbootstrap.client.ui.AppendButton;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.CheckBox;
import com.github.gwtbootstrap.client.ui.Column;
import com.github.gwtbootstrap.client.ui.Emphasis;
import com.github.gwtbootstrap.client.ui.FluidRow;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Icon;
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.ListBox;
import com.github.gwtbootstrap.client.ui.base.IconAnchor;
import com.github.gwtbootstrap.client.ui.base.InlineLabel;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.github.gwtbootstrap.client.ui.base.UnorderedList;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.github.gwtbootstrap.client.ui.constants.ControlGroupType;
import com.github.gwtbootstrap.client.ui.constants.IconPosition;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.client.ui.constants.ImageType;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.gwt.aria.client.InvalidValue;
import com.google.gwt.aria.client.Roles;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.SuggestBox;
import com.google.gwt.user.client.ui.SuggestOracle;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;
import com.google.gwt.user.client.ui.Widget;

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
	private final ListBox filterDifficulty = new ListBox(true);
	private final ListBox filterCategory = new ListBox(true);
	private final ListBox filterSeason = new ListBox(true);
	private final FlowPanel filterIngredient = new FlowPanel();
	
	// TODO should be real buttons (not bootstrap's "buttons" = links), using it for convenience
	private final Button filtersShowButton = new Button(constants.searchAdvanced() + " ");
	private final Button filtersHideButton = new Button(constants.searchBasic() + " ");

	private SuggestBox ingredientSuggest;
	private final AppendButton ingredientSuggestForm = new AppendButton();
	private final Button ingredientAdd = new Button();

	private final Label labelUtensils = new Label(constants.utensils());
	private final FlowPanel filterUtensil = new FlowPanel();

	private final PagingWidget pagingWidget = new PagingWidget(this);
	private final Button compareLink = new Button(constants.recipeCompare());

	// variables
	private SearchParameters searchParameters = new SearchParameters(null, null);
	private Set<Long> selectedRecipes = new HashSet<Long>();

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

		// filters
		filtersShowButton.setType(ButtonType.LINK);
		filtersShowButton.setIcon(IconType.CHEVRON_DOWN);
		filtersShowButton.setIconPosition(IconPosition.RIGHT);
		filtersShowButton.setTitle(constants.searchToggleInfo());
		filtersShowButton.getElement().setId("filtersShowButton");
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
		filtersHideButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				searchParameters = new SearchParameters(null, null);
				filtersContainer.setVisible(false);
				filtersHideButton.setVisible(false);
				filtersShowButton.setVisible(true);
				doSearch();
			}
		});

		// TODO cleanup!!!
		Label labelDifficulty = new Label(constants.difficulty());
		labelDifficulty.setStyleName("filterLabel");
		filterDifficulty.setSize(8);
		filterDifficulty.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
//				boolean clear = filterDifficulty.isItemSelected(0);
				for (int i=0/*1*/; i < filterDifficulty.getItemCount(); i++) {
					Difficulty difficulty = Difficulty.valueOf(filterDifficulty.getValue(i));
					if (/*!clear &&*/ filterDifficulty.isItemSelected(i)) {
						searchParameters.addDifficulty(difficulty);
					} else {
						searchParameters.removeDifficulty(difficulty);
//						filterDifficulty.setItemSelected(i, false);
					}
					searchParameters.setPage(1);
				}
			}
		});
		Column filterPanelDifficulty = new Column(3);
		filterPanelDifficulty.add(labelDifficulty);
		filterPanelDifficulty.add(filterDifficulty);

		Label labelCategories = new Label(constants.categories());
		labelCategories.setStyleName("filterLabel");
		filterCategory.setSize(8);
		filterCategory.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
//				boolean clear = filterCategory.isItemSelected(0);
				for (int i=0/*1*/; i < filterCategory.getItemCount(); i++) {
					Category category = Category.valueOf(filterCategory.getValue(i));
					if (/*!clear &&*/ filterCategory.isItemSelected(i)) {
						searchParameters.addCategory(category);
					} else {
						searchParameters.removeCategory(category);
//						filterCategory.setItemSelected(i, false);
					}
					searchParameters.setPage(1);
				}
			}
		});
		Column filterPanelCategories = new Column(3);
		filterPanelCategories.add(labelCategories);
		filterPanelCategories.add(filterCategory);

		Label labelSeasons = new Label(constants.seasons());
		labelSeasons.setStyleName("filterLabel");
		filterSeason.setSize(8);
		filterSeason.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
//				boolean clear = filterSeason.isItemSelected(0);
				for (int i=0/*1*/; i < filterSeason.getItemCount(); i++) {
					Season season = Season.valueOf(filterSeason.getValue(i));
					if (/*!clear &&*/ filterSeason.isItemSelected(i)) {
						searchParameters.addSeason(season);
					} else {
						searchParameters.removeSeason(season);
//						filterSeason.setItemSelected(i, false);
					}
					searchParameters.setPage(1);
				}
			}
		});
		Column filterPanelSeasons = new Column(3);
		filterPanelSeasons.add(labelSeasons);
		filterPanelSeasons.add(filterSeason);

		// suggest boxes
		MultiWordSuggestOracle ingredientSuggestOracle = new MultiWordSuggestOracle();
		ingredientSuggestOracle.addAll(ingredientInverseMap.keySet());
		ingredientSuggest = new SuggestBox(ingredientSuggestOracle);
		ingredientSuggest.getElement().setPropertyString("placeholder", constants.searchAndAddIngredient());
		ingredientSuggest.setStyleName(Constants.SPAN + 8);
		ingredientSuggest.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				String value = ingredientSuggest.getValue();
				if (ingredientInverseMap.containsKey(value)) {
					Roles.getTextboxRole().setAriaInvalidState(ingredientSuggest.getElement(), InvalidValue.FALSE);
					ingredientSuggestForm.removeStyleName(ControlGroupType.ERROR.get());
					if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
						ingredientSuggestForm.removeStyleName(ControlGroupType.SUCCESS.get());
						ingredientSuggest.setText("");
						boolean added = searchParameters.addIngredient(ingredientInverseMap.get(value));
						if (added) {
							filterIngredient.add(getIngredientLabel(ingredientInverseMap.get(value)));
						}
					} else {
						ingredientSuggestForm.addStyleName(ControlGroupType.SUCCESS.get());
					}
				} else {
					// show error
					ingredientSuggestForm.addStyleName(ControlGroupType.ERROR.get());
					ingredientSuggestForm.removeStyleName(ControlGroupType.SUCCESS.get());
					Roles.getTextboxRole().setAriaInvalidState(ingredientSuggest.getElement(), InvalidValue.TRUE);
				}
			}
		});
		ingredientSuggest.addSelectionHandler(new SelectionHandler<SuggestOracle.Suggestion>() {
			@Override
			public void onSelection(SelectionEvent<Suggestion> event) {
				String value = event.getSelectedItem().getReplacementString();
				if (ingredientInverseMap.containsKey(value)) {
					ingredientSuggestForm.removeStyleName(ControlGroupType.ERROR.get());
					ingredientSuggestForm.removeStyleName(ControlGroupType.SUCCESS.get());
					Roles.getTextboxRole().setAriaInvalidState(ingredientSuggest.getElement(), InvalidValue.FALSE);
					ingredientSuggest.setText("");
					boolean added = searchParameters.addIngredient(ingredientInverseMap.get(value));
					if (added) {
						filterIngredient.add(getIngredientLabel(ingredientInverseMap.get(value)));
					}
				} else {
					// show error
					ingredientSuggestForm.addStyleName(ControlGroupType.ERROR.get());
					ingredientSuggestForm.removeStyleName(ControlGroupType.SUCCESS.get());
					Roles.getTextboxRole().setAriaInvalidState(ingredientSuggest.getElement(), InvalidValue.TRUE);
				}
			}
		});

		// TODO set title / label
		ingredientAdd.setIcon(IconType.OK);
		ingredientAdd.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String value = ingredientSuggest.getValue();
				if (ingredientInverseMap.containsKey(value)) {
					ingredientSuggestForm.removeStyleName(ControlGroupType.ERROR.get());
					ingredientSuggestForm.removeStyleName(ControlGroupType.SUCCESS.get());
					Roles.getTextboxRole().setAriaInvalidState(ingredientSuggest.getElement(), InvalidValue.FALSE);
					ingredientSuggest.setText("");
					boolean added = searchParameters.addIngredient(ingredientInverseMap.get(value));
					if (added) {
						filterIngredient.add(getIngredientLabel(ingredientInverseMap.get(value)));
					}
				} else {
					ingredientSuggestForm.addStyleName(ControlGroupType.ERROR.get());
					ingredientSuggestForm.removeStyleName(ControlGroupType.SUCCESS.get());
					Roles.getTextboxRole().setAriaInvalidState(ingredientSuggest.getElement(), InvalidValue.TRUE);
				}
			}
		});

		Label labelIngredients = new Label(constants.ingredients());
		labelIngredients.setStyleName("filterLabel");
		labelUtensils.setStyleName("filterLabel");

		ingredientSuggestForm.addStyleName(Constants.CONTROL_GROUP);
		ingredientSuggestForm.add(ingredientSuggest);
		ingredientSuggestForm.add(ingredientAdd);

		Column filterIngredients = new Column(3);
		filterIngredients.add(labelIngredients);
		filterIngredients.add(filterIngredient);
		filterIngredients.add(ingredientSuggestForm);

		filtersPanel.add(filterPanelDifficulty);
		filtersPanel.add(filterPanelCategories);
		filtersPanel.add(filterPanelSeasons);
		filtersPanel.add(filterIngredients);

		filtersContainer.addStyleName("filtersPanel");
		filtersContainer.add(new Alert(messages.tipSearch(), AlertType.INFO));
		filtersContainer.add(filtersPanel);
		
		// sort
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
					doSearch();
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
		resultsLexicon.add(headingLexicon);
		resultsLexicon.add(new Emphasis(messages.didYouSearchFor()));
		resultsLexicon.add(resultsIngredients);
		resultsLexicon.add(resultsUtensils);

		resultsIngredients.add(headingIngredients);
		resultsIngredients.add(resultsIngredientList);

		resultsUtensils.add(headingUtensils);
		resultsUtensils.add(resultsUtensilList);
		
		resultsMain.addStyleName("resultsPanel");
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

	/** 
	 * Gets search string from search widget and initiates search (other search parameters remain intact).
	 */
	@Override
	public void doSearch() {
		searchParameters.setSearchString(searchWidget.getText());
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
			}
	
			// difficulties
			filterDifficulty.clear();
//			filterDifficulty.addItem(constants.searchFilterAll(), "");
			Set<Difficulty> difficulties = searchParameters.getDifficulties();
			for (final Difficulty difficulty : Difficulty.values()) {
				boolean active = difficulties.contains(difficulty);
				filterDifficulty.addItem(localizeEnum(difficulty), difficulty.name());
				filterDifficulty.setItemSelected(filterDifficulty.getItemCount() - 1, active);
			}
//			if (difficulties.isEmpty()) {
//				filterDifficulty.setItemSelected(0, true);
//			}
	
			// categories
			filterCategory.clear();
//			filterCategory.addItem(constants.searchFilterAll(), "");
			Set<Category> categories = searchParameters.getCategories();
			for (final Category category : Category.values()) {
				boolean active = categories.contains(category);
				filterCategory.addItem(localizeEnum(category), category.name());
				filterCategory.setItemSelected(filterCategory.getItemCount() - 1, active);
			}
//			if (categories.isEmpty()) {
//				filterCategory.setItemSelected(0, true);
//			}
	
			// seasons
			filterSeason.clear();
			Set<Season> seasons = searchParameters.getSeasons();
			for (final Season season : Season.values()) {
				if (season == Season.ALLYEAR) {
					continue;
				}

				boolean active = seasons.contains(season);
				String seasonName = season.name();
				filterSeason.addItem(constants.seasonMap().get(seasonName), seasonName);
				filterSeason.setItemSelected(filterSeason.getItemCount() - 1, active);
			}
//			if (seasons.isEmpty()) {
//				filterSeason.setItemSelected(0, true);
//			}
	
			// ingredients
			ingredientSuggest.setValue("");
			ingredientSuggestForm.removeStyleName(ControlGroupType.ERROR.get());
			ingredientSuggestForm.removeStyleName(ControlGroupType.SUCCESS.get());
			filterIngredient.clear();
			Set<String> ingredients = searchParameters.getIngredients();
			for (final String ingredient : ingredients) {
				filterIngredient.add(getIngredientLabel(ingredient));
			}
	
			// utensil
			Set<String> utensils = searchParameters.getUtensils();
			if (!utensils.isEmpty()) {
				filterUtensil.add(labelUtensils);
				for (final String utensil : utensils) {
					final FlowPanel utensilEntry = new FlowPanel(); 
					
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
					utensilEntry.add(utensilLabel);
					utensilEntry.add(removeIcon);

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

	private FlowPanel getIngredientLabel(final String ingredient) {
		final FlowPanel ingredientEntry = new FlowPanel(); 

		IconAnchor removeIcon = new IconAnchor();
		removeIcon.setIcon(IconType.REMOVE);
		removeIcon.setText(constants.remove());
		removeIcon.setStyleName("removeFilter");
		removeIcon.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				searchParameters.removeIngredient(ingredient);
				searchParameters.setPage(1);
				ingredientEntry.clear();
//				doSearch();
			}
		});
		InlineLabel ingredientLabel = new InlineLabel(ingredientMap.get(ingredient) + " ");
		ingredientEntry.add(ingredientLabel);
		ingredientEntry.add(removeIcon);
		return ingredientEntry;
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
		doSearch();
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(constants.search());
		return super.asWidget();
	}
}

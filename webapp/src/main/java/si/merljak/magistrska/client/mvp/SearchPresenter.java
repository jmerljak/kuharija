package si.merljak.magistrska.client.mvp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.common.SearchParameters;
import si.merljak.magistrska.common.dto.RecipeListDto;
import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.enumeration.RecipeSortKey;
import si.merljak.magistrska.common.enumeration.Season;
import si.merljak.magistrska.common.rpc.SearchServiceAsync;

import com.google.common.base.Joiner;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 * Presenter for generic search.
 * Use provided static methods for building proper anchor URL or initiating search.
 * 
 * @author Jakob Merljak
 * 
 */
public class SearchPresenter extends AbstractPresenter {

	// screen and parameters name
	public static final String SCREEN_NAME = "search";
	private static final String PARAMETER_SEARCH_STRING = "q";
	private static final String PARAMETER_PAGE = "page";
	private static final String PARAMETER_PAGE_SIZE = "pageSize";
	private static final String PARAMETER_CATEGORY = "category";
	private static final String PARAMETER_DIFFICULTY = "difficulty";
	private static final String PARAMETER_INGREDIENT = "ingredient";
	private static final String PARAMETER_SEASON = "season";
	private static final String PARAMETER_UTENSIL = "utensil";
	private static final String PARAMETER_SORT_BY = "sortby";

	// remote service
	private SearchServiceAsync searchService;

	// view
    private final SearchView searchView = new SearchView();

	public SearchPresenter(Language language, SearchServiceAsync searchService) {
		super(language);
		this.searchService = searchService;
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		if (parameters.isEmpty()) {
			searchView.clearSearchResults();
			searchView.setVisible(true);
		} else {
			String encodedSearchString = parameters.get(PARAMETER_SEARCH_STRING);
			String searchString = encodedSearchString != null ? URL.decodePathSegment(encodedSearchString) : null;
			SearchParameters searchParameters = new SearchParameters(searchString, language);

			if (parameters.containsKey(PARAMETER_PAGE)) {
				try {
					int page = Integer.parseInt(parameters.get(PARAMETER_PAGE));
					searchParameters.setPage(page);
				} catch (Exception e) { /* ignore */ }
			}

			if (parameters.containsKey(PARAMETER_PAGE_SIZE)) {
				try {
					int pageSize = Integer.parseInt(parameters.get(PARAMETER_PAGE_SIZE));
					searchParameters.setPageSize(pageSize);
				} catch (Exception e) { /* ignore */ }
			}

			if (parameters.containsKey(PARAMETER_DIFFICULTY)) {
				String[] difficulties = parameters.get(PARAMETER_DIFFICULTY).toUpperCase().split(",");
				for (int i = 0; i < difficulties.length; i++) {
					try {
						Difficulty difficulty = Difficulty.valueOf(difficulties[i]);
						searchParameters.addDifficulty(difficulty);
					} catch (Exception e) { /* ignore */ }
				}
			}

			if (parameters.containsKey(PARAMETER_CATEGORY)) {
				String[] categories = parameters.get(PARAMETER_CATEGORY).toUpperCase().split(",");
				for (int i = 0; i < categories.length; i++) {
					try {
						Category category = Category.valueOf(categories[i]);
						searchParameters.addCategory(category);
					} catch (Exception e) { /* ignore */ }
				}
			}

			if (parameters.containsKey(PARAMETER_SEASON)) {
				String[] seasons = parameters.get(PARAMETER_SEASON).toUpperCase().split(",");
				for (int i = 0; i < seasons.length; i++) {
					try {
						Season season = Season.valueOf(seasons[i]);
						searchParameters.addSeason(season);
					} catch (Exception e) { /* ignore */ }
				}
			}

			if (parameters.containsKey(PARAMETER_INGREDIENT)) {
				String[] ingredients = parameters.get(PARAMETER_INGREDIENT).toUpperCase().split(",");
				for (int i = 0; i < ingredients.length; i++) {
					try {
						searchParameters.addIngredient(ingredients[i]);
					} catch (Exception e) { /* ignore */ }
				}
			}

			if (parameters.containsKey(PARAMETER_UTENSIL)) {
				searchParameters.setUtensil(parameters.get(PARAMETER_UTENSIL).toUpperCase());
			}

			if (parameters.containsKey(PARAMETER_SORT_BY)) {
				try {
					RecipeSortKey sortKey = RecipeSortKey.valueOf(parameters.get(PARAMETER_SORT_BY).toUpperCase());
					searchParameters.setSortKey(sortKey);
				} catch (Exception e) { /* ignore */ }
			}

			search(searchParameters);
		}
		return searchView.asWidget();
	}

	/** 
	 * Searches for recipes and displays results.
	 * @param searchParameters search parameters
	 */
	private void search(final SearchParameters searchParameters) {
		searchView.setVisible(false);
		searchService.search(searchParameters, new AsyncCallback<RecipeListDto>() {
			@Override
			public void onSuccess(RecipeListDto results) {
				searchView.displaySearchResults(results, searchParameters);
				searchView.setVisible(true);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Kuharija.handleException(caught);
			}
		});
	}

	/**
	 * Initiates search for search string.
	 * @param searchString search string
	 */
	public static void doSearch(String searchString) {
		if (searchString != null) {
			String encodedSearchString = URL.encodePathSegment(searchString.trim().toLowerCase());
			History.newItem(SCREEN_NAME + "&" + PARAMETER_SEARCH_STRING + "=" + encodedSearchString);
		}
	}

	/**
	 * Initiates search for search parameters.
	 * @param searchParameters search parameters
	 */
	public static void doSearch(SearchParameters searchParameters) {
		// parameters
		Integer page = searchParameters.getPage();
		Integer pageSize = searchParameters.getPageSize();
		String searchString = searchParameters.getSearchString();
		Set<Difficulty> difficulties = searchParameters.getDifficulties();
		Set<Category> categories = searchParameters.getCategories();
		Set<Season> seasons = searchParameters.getSeasons();
		Set<String> ingredients = searchParameters.getIngredients();
		String utensil = searchParameters.getUtensil();
		RecipeSortKey sortKey = searchParameters.getSortKey();

		Map<String, String> parametersMap = new HashMap<String, String>();
		if (page > 1) {
			parametersMap.put(PARAMETER_PAGE, page.toString());
		}

		if (pageSize != SearchParameters.DEFAULT_PAGE_SIZE) {
			parametersMap.put(PARAMETER_PAGE_SIZE, pageSize.toString());
		}

		if (searchString != null && !searchString.isEmpty()) {
			parametersMap.put(PARAMETER_SEARCH_STRING, URL.encodePathSegment(searchString));
		}

		if (!difficulties.isEmpty()) {
			List<String> difficultyStrings = new ArrayList<String>();
			for (Difficulty difficulty : difficulties) {
				difficultyStrings.add(difficulty.name().toLowerCase());
			}
			parametersMap.put(PARAMETER_DIFFICULTY, Joiner.on(",").skipNulls().join(difficultyStrings));
		}

		if (!categories.isEmpty()) {
			List<String> categoryStrings = new ArrayList<String>();
			for (Category category : categories) {
				categoryStrings.add(category.name().toLowerCase());
			}
			parametersMap.put(PARAMETER_CATEGORY, Joiner.on(",").skipNulls().join(categoryStrings));
		}

		if (!seasons.isEmpty()) {
			List<String> seasonStrings = new ArrayList<String>();
			for (Season season : seasons) {
				seasonStrings.add(season.name().toLowerCase());
			}
			parametersMap.put(PARAMETER_SEASON, Joiner.on(",").skipNulls().join(seasonStrings));
		}

		if (!ingredients.isEmpty()) {
			parametersMap.put(PARAMETER_INGREDIENT, Joiner.on(",").skipNulls().join(ingredients).toLowerCase());
		}

		if (utensil != null) {
			parametersMap.put(PARAMETER_UTENSIL, utensil.toLowerCase());
		}

		if (sortKey != null && sortKey != SearchParameters.DEFAULT_SORT_KEY) {
			parametersMap.put(PARAMETER_SORT_BY, sortKey.name().toLowerCase());
		}

		String parametersString = Joiner.on("&").withKeyValueSeparator("=").join(parametersMap);
		History.newItem(Joiner.on("&").join(SCREEN_NAME, parametersString));
	}

	/**
	 * Builds proper anchor URL for search by category.
	 * @param category category enumerator
	 * @return anchor URL
	 */
	public static String buildSearchByCategoryUrl(Category category) {
		return "#" + SCREEN_NAME + 
			   "&" + PARAMETER_CATEGORY + "=" + category.name().toLowerCase();
	}

	/**
	 * Builds proper anchor URL for search by difficulty.
	 * @param difficulty difficulty enumerator
	 * @return anchor URL
	 */
	public static String buildSearchByDifficultyUrl(Difficulty difficulty) {
		return "#" + SCREEN_NAME + 
			   "&" + PARAMETER_DIFFICULTY + "=" + difficulty.name().toLowerCase();
	}

	/**
	 * Builds proper anchor URL for search by ingredient.
	 * @param ingredientName ingredient's name
	 * @return anchor URL
	 */
	public static String buildSearchByIngredientUrl(String ingredientName) {
		return "#" + SCREEN_NAME + 
			   "&" + PARAMETER_INGREDIENT + "=" + ingredientName.toLowerCase();
	}

	/**
	 * Builds proper anchor URL for search by season.
	 * @param season season enumerator
	 * @return anchor URL
	 */
	public static String buildSearchBySeasonUrl(Season season) {
		return "#" + SCREEN_NAME + 
			   "&" + PARAMETER_SEASON + "=" + season.name().toLowerCase();
	}

	/**
	 * Builds proper anchor URL for search by utensil.
	 * @param utensilName utensil's name
	 * @return anchor URL
	 */
	public static String buildSearchByUtensilUrl(String utensilName) {
		return "#" + SCREEN_NAME + 
			   "&" + PARAMETER_UTENSIL + "=" + utensilName.toLowerCase();
	}

}

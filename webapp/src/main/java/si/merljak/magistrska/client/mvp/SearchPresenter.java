package si.merljak.magistrska.client.mvp;

import java.util.List;
import java.util.Map;

import si.merljak.magistrska.common.SearchParameters;
import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.enumeration.RecipeSortKey;
import si.merljak.magistrska.common.enumeration.Season;
import si.merljak.magistrska.common.rpc.SearchServiceAsync;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SearchPresenter extends AbstractPresenter {

	// screen and parameters name
	public static final String SCREEN_NAME = "search";
	public static final String PARAMETER_SEARCH_STRING = "q";
	public static final String PARAMETER_PAGE = "page";
	public static final String PARAMETER_PAGE_SIZE = "pageSize";
	public static final String PARAMETER_CATEGORY = "categories";
	public static final String PARAMETER_DIFFICULTY = "difficulties";
	public static final String PARAMETER_INGREDIENT = "ingredients";
	public static final String PARAMETER_SEASON = "seasons";
	public static final String PARAMETER_SORT_BY = "sortby";

	// remote service
	private SearchServiceAsync searchService;

	// view
    private SearchView searchView = new SearchView();

	public SearchPresenter(Language language, SearchServiceAsync searchService) {
		super(language);
		this.searchService = searchService;
	}

	@Override
	protected boolean isPresenterForScreen(String screenName) {
		return SCREEN_NAME.equalsIgnoreCase(screenName);
	}

	@Override
	protected void parseParameters(String screenName, Map<String, String> parameters) {
		if (parameters.isEmpty()) {
			searchView.clearSearchResults();
		} else {
			SearchParameters searchParameters = new SearchParameters(parameters.get(PARAMETER_SEARCH_STRING), language);

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

			if (parameters.containsKey(PARAMETER_SORT_BY)) {
				try {
					RecipeSortKey sortKey = RecipeSortKey.valueOf(parameters.get(PARAMETER_SORT_BY).toUpperCase());
					searchParameters.setSortKey(sortKey);
				} catch (Exception e) { /* ignore */ }
			}

			search(searchParameters);
		}
	}

	/** 
	 * Searches for recipes and displays results.
	 * @param searchParameters search parameters
	 */
	private void search(final SearchParameters searchParameters) {
		searchService.search(searchParameters, new AsyncCallback<List<RecipeDto>>() {
			@Override
			public void onSuccess(List<RecipeDto> results) {
				searchView.displaySearchResults(results, searchParameters);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	@Override
	protected void hideView() {
		searchView.hide();
	}

	/**
	 * Builds proper history token for generic search.
	 * @param searchString search string
	 * @return history token
	 */
	public static String buildSearchUrl(String searchString) {
		return "#" + SCREEN_NAME + 
			   "&" + PARAMETER_SEARCH_STRING + "=" + searchString.toLowerCase();
	}

	/**
	 * Builds proper history token for search by category.
	 * @param category category enumerator
	 * @return history token
	 */
	public static String buildSearchByCategoryUrl(Category category) {
		return "#" + SCREEN_NAME + 
			   "&" + PARAMETER_CATEGORY + "=" + category.name().toLowerCase();
	}

	/**
	 * Builds proper history token for search by difficulty.
	 * @param difficulty difficulty enumerator
	 * @return history token
	 */
	public static String buildSearchByDifficultyUrl(Difficulty difficulty) {
		return "#" + SCREEN_NAME + 
			   "&" + PARAMETER_DIFFICULTY + "=" + difficulty.name().toLowerCase();
	}

	/**
	 * Builds proper history token for search by ingredient.
	 * @param ingredientName ingredient's name
	 * @return history token
	 */
	public static String buildSearchByIngredientUrl(String ingredientName) {
		return "#" + SCREEN_NAME + 
			   "&" + PARAMETER_INGREDIENT + "=" + ingredientName.toLowerCase();
	}

	/**
	 * Builds proper history token for search by season.
	 * @param season season enumerator
	 * @return history token
	 */
	public static String buildSearchByCategoryUrl(Season season) {
		return "#" + SCREEN_NAME + 
			   "&" + PARAMETER_SEASON + "=" + season.name().toLowerCase();
	}

}
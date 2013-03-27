package si.merljak.magistrska.client.mvp;

import java.util.List;
import java.util.Map;

import si.merljak.magistrska.client.KuharijaEntry;
import si.merljak.magistrska.common.SearchParameters;
import si.merljak.magistrska.common.dto.RecipeBasicDto;
import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.enumeration.Season;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class SearchPresenter extends AbstractPresenter {

    private SearchView searchView = new SearchView();

	public SearchPresenter(Language language) {
		super(language);
	}

	@Override
	protected boolean isPresenterForScreen(String screenName) {
		return screenName != null && screenName.equalsIgnoreCase("search");
	}

	@Override
	protected void parseParameters(String screenName, Map<String, String> parameters) {
		if (parameters.containsKey("q")) {
			String searchString = parameters.get("q");
			SearchParameters searchParameters = new SearchParameters(searchString, language);

			try {
				int page = Integer.parseInt(parameters.get("page"));
				searchParameters.setPage(page);
			} catch (Exception e) { /* ignore */ }
			try {
				int pageSize = Integer.parseInt(parameters.get("pageSize"));
				searchParameters.setPageSize(pageSize);
			} catch (Exception e) { /* ignore */ }
			try {
				Difficulty difficulty = Difficulty.valueOf(parameters.get("difficulty").toUpperCase());
				searchParameters.setDifficulty(difficulty);
			} catch (Exception e) { /* ignore */ }
			try {
				Category category = Category.valueOf(parameters.get("category").toUpperCase());
				searchParameters.setCategory(category);
			} catch (Exception e) { /* ignore */ }
			try {
				Season season = Season.valueOf(parameters.get("season").toUpperCase());
				searchParameters.setSeason(season);
			} catch (Exception e) { /* ignore */ }
			try {
				// TODO parse multiple ingredients
				String ingredients = parameters.get("ingredient").toUpperCase();
				searchParameters.addIngredient(ingredients);
			} catch (Exception e) { /* ignore */ }

			search(searchParameters);
		} else {
			searchView.clearSearchResults();
		}
	}

	private void search(final SearchParameters searchParameters) {
		KuharijaEntry.searchService.search(searchParameters, new AsyncCallback<List<RecipeBasicDto>>() {
			@Override
			public void onSuccess(List<RecipeBasicDto> results) {
				searchView.displaySearchResults(results, searchParameters);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	protected void hideView() {
		searchView.hide();
	}

}

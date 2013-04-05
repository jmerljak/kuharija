package si.merljak.magistrska.client.mvp;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.RecipeServiceAsync;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ComparePresenter extends AbstractPresenter {

	// screen and parameters name
	private static final String SCREEN_NAME = "compare";
	private static final String PARAMETER_RECIPE_ID_LIST = "recipes";

	// remote service
	private RecipeServiceAsync recipeService;

	// view
    private CompareView searchView = new CompareView();

	public ComparePresenter(Language language, RecipeServiceAsync recipeService) {
		super(language);
		this.recipeService = recipeService;
	}

	@Override
	protected boolean isPresenterForScreen(String screenName) {
		return SCREEN_NAME.equalsIgnoreCase(screenName);
	}

	@Override
	protected void parseParameters(String screenName, Map<String, String> parameters) {
		if (parameters.containsKey(PARAMETER_RECIPE_ID_LIST)) {
			String[] idStringList = parameters.get(PARAMETER_RECIPE_ID_LIST).split(",");
			Set<Long> recipeIdList = new HashSet<Long>();
			for (int i = 0; i < idStringList.length; i++) {
				try {
					recipeIdList.add(Long.parseLong(idStringList[i]));
				} catch (Exception e) { /* ignore */ }
			}
			if (!recipeIdList.isEmpty()) {
				getRecipes(recipeIdList);
			} else {
				searchView.clearSearchResults();
			}
		} else {
			searchView.clearSearchResults();
		}
	}

	private void getRecipes(Set<Long> recipeIdList) {
		recipeService.getRecipes(recipeIdList, language, new AsyncCallback<List<RecipeDetailsDto>>() {
			@Override
			public void onSuccess(List<RecipeDetailsDto> result) {
				searchView.displaySearchResults(result);
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
	 * Builds proper history token for recipes comparison.
	 * @param recipeIdList list of recipe IDs
	 * @return history token
	 */
	public static String buildCompareUrl(Set<Long> recipeIdList) {
		String url = "#" + SCREEN_NAME + "&" + PARAMETER_RECIPE_ID_LIST + "=";
		for (Long id : recipeIdList) {
			url += id.toString() + ",";
		}
		return url;
	}

}
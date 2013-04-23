package si.merljak.magistrska.client.mvp;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.RecipeServiceAsync;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class ComparePresenter extends AbstractPresenter {

	// screen and parameters name
	public static final String SCREEN_NAME = "compare";
	private static final String PARAMETER_RECIPE_ID_LIST = "recipes";

	// remote service
	private RecipeServiceAsync recipeService;

	// view
    private CompareView searchView = new CompareView();

    // utils
    private final Splitter splitter = Splitter.on(",").omitEmptyStrings().trimResults();
    
	public ComparePresenter(Language language, RecipeServiceAsync recipeService) {
		super(language);
		this.recipeService = recipeService;
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		searchView.setVisible(false);
		if (parameters.containsKey(PARAMETER_RECIPE_ID_LIST)) {
			Set<Long> recipeIdList = new HashSet<Long>();
			for (String idString : splitter.split(parameters.get(PARAMETER_RECIPE_ID_LIST))) {
				try {
					recipeIdList.add(Long.parseLong(idString));
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
		return searchView.asWidget();
	}

	private void getRecipes(Set<Long> recipeIdList) {
		recipeService.getRecipes(recipeIdList, language, new AsyncCallback<List<RecipeDetailsDto>>() {
			@Override
			public void onSuccess(List<RecipeDetailsDto> result) {
				searchView.displaySearchResults(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Kuharija.handleException(caught);
			}
		});
	}

	/**
	 * Builds proper anchor URL for recipes comparison.
	 * @param recipeIdList list of recipe IDs
	 * @return anchor URL
	 */
	public static String buildCompareUrl(Set<Long> recipeIdList) {
		return "#" + SCREEN_NAME + 
			   "&" + PARAMETER_RECIPE_ID_LIST + "=" + Joiner.on(",").skipNulls().join(recipeIdList);
	}

}

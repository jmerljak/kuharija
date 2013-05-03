package si.merljak.magistrska.client.mvp.compare;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.mvp.AbstractPresenter;
import si.merljak.magistrska.client.mvp.recipe.RecipePresenter;
import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.RecipeServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 * Presenter for recipe comparison.
 * 
 * @author Jakob Merljak
 * 
 */
public class ComparePresenter extends AbstractPresenter {

	// screen and parameters name
	public static final String SCREEN_NAME = "compare";
	private static final String PARAMETER_ID_LIST = "id";

	// remote service
	private final RecipeServiceAsync recipeService;

	// view
	private final CompareView view = new CompareView();

	public ComparePresenter(Language language, RecipeServiceAsync recipeService) {
		super(language);
		this.recipeService = recipeService;
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		Set<Long> recipeIdList = new HashSet<Long>();
		if (parameters.containsKey(PARAMETER_ID_LIST)) {
			for (String idString : Kuharija.listSplitter.split(parameters.get(PARAMETER_ID_LIST))) {
				try {
					recipeIdList.add(Long.parseLong(idString));
				} catch (Exception e) { /* ignore */ }
			}
		}

		if (recipeIdList.isEmpty()) {
			view.displayResults(null);
		} else {
			getRecipes(recipeIdList);
		}
		return view.asWidget();
	}

	/**
	 * Gets recipes by IDs.
	 * 
	 * @param recipeIdList ID list
	 */
	private void getRecipes(Set<Long> recipeIdList) {
		view.setVisible(false);
		recipeService.getRecipes(recipeIdList, language, new AsyncCallback<List<RecipeDetailsDto>>() {
			@Override
			public void onSuccess(List<RecipeDetailsDto> recipes) {
				view.displayResults(recipes);
				view.setVisible(true);
			}

			@Override
			public void onFailure(Throwable caught) {
				Kuharija.handleException(caught);
			}
		});
	}

	/**
	 * Builds proper anchor URL for recipes comparison.
	 * 
	 * @param recipeIdList list of recipe IDs
	 * @return anchor URL
	 */
	public static String buildCompareUrl(Set<Long> recipeIdList) {
		return "#" + SCREEN_NAME + 
			   "&" + PARAMETER_ID_LIST + "=" + Kuharija.listJoiner.join(recipeIdList);
	}

	@Override
	public String getScreenName() {
		return SCREEN_NAME;
	}

	@Override
	public String getParentName() {
		return RecipePresenter.SCREEN_NAME;
	}
}

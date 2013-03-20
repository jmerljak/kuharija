package si.merljak.magistrska.client.mvp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import si.merljak.magistrska.client.KuharijaEntry;
import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.StepDto;
import si.merljak.magistrska.common.enumeration.Language;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RecipePresenter extends AbstractPresenter {

	private long recipeId;
    private RecipeView recipeView = new RecipeView();

	public RecipePresenter(Language language) {
		super(language);
	}

	private final List<String> screenNames = Arrays.asList("recipe", "basic", "video", "audio");
	
	@Override
	protected boolean isPresenterForScreen(String screenName) {
		return screenName != null && screenNames.contains(screenName);
	}

	@Override
	protected void parseParameters(String screenName, Map<String, String> parameters) {
		// TODO Auto-generated method stub
		try {
			getRecipe(Long.parseLong(parameters.get("id")));
		} catch (Exception e) {
			
		}
	}

	private void getRecipe(long recipeId) {
		KuharijaEntry.recipeService.getRecipe(recipeId, language, new AsyncCallback<RecipeDto>() {
			@Override
			public void onSuccess(RecipeDto recipe) {
				recipeView.displayRecipe(recipe);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	private void getStep() {
		// get parameter
		long recipeId = -1;
		String paramRecipeId = Window.Location.getParameter("recipe");
		try {
			recipeId = Long.parseLong(paramRecipeId);
		} catch (Exception e) {
			
		}

		int page;
		String paramPage = Window.Location.getParameter("page");
		try {
			page = Integer.parseInt(paramPage);
		} catch (Exception e) {
			page = 1;
		}

		KuharijaEntry.recipeService.getStep(recipeId, language, page, new AsyncCallback<StepDto>() {
			@Override
			public void onSuccess(StepDto step) {
				recipeView.displayStep(step);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	@Override
	protected void hideView() {
		recipeView.hide();
	}

}

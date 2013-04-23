package si.merljak.magistrska.client.mvp;

import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.RecipeServiceAsync;
import si.merljak.magistrska.common.rpc.UserServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class RecipePresenter extends AbstractPresenter {

	// screen and parameters name
	public static final String SCREEN_NAME = "recipe";
	private static final String PARAMETER_RECIPE_ID = "id";
	private static final String PARAMETER_VIEW = "view"; // basic, steps, video, audio

	// remote service
	private RecipeServiceAsync recipeService;
	private UserServiceAsync userService;

	private long recipeId;
    private RecipeView recipeView = new RecipeView();

	public RecipePresenter(Language language, RecipeServiceAsync recipeService, UserServiceAsync userService) {
		super(language);
		this.recipeService = recipeService;
		this.userService = userService;
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		try {
			recipeId = Long.parseLong(parameters.get(PARAMETER_RECIPE_ID));
			String view = parameters.get(PARAMETER_VIEW);
			getRecipe(recipeId, view);
		} catch (Exception e) {
			// TODO display 404 page 
		}
		return recipeView.asWidget();
	}

	private void getRecipe(long recipeId, final String view) {
		recipeService.getRecipeDetails(recipeId, language, new AsyncCallback<RecipeDetailsDto>() {
			@Override
			public void onSuccess(RecipeDetailsDto recipe) {
				recipeView.displayRecipe(recipe, view);
			}

			@Override
			public void onFailure(Throwable caught) {
				Kuharija.handleException(caught);
			}
		});
	}

	public void bookmark(boolean add) {
		// TODO get user
		userService.bookmarkRecipe("user1", recipeId, add, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				// TODO show as bookmarked / non bookmarked
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Kuharija.handleException(caught);
			}
		});
	}

	public void comment(String content) {
		// TODO get user
		userService.commentRecipe(null, recipeId, content, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				// TODO display comment
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Kuharija.handleException(caught);
			}
		});
	}

	/**
	 * Builds proper anchor URL for recipe.
	 * @param id recipe's id
	 * @return anchor URL
	 */
	public static String buildRecipeUrl(long id) {
		return "#" + SCREEN_NAME +
			   "&" + PARAMETER_RECIPE_ID + "=" + id;
	}

}

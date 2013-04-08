package si.merljak.magistrska.client.mvp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.RecipeServiceAsync;
import si.merljak.magistrska.common.rpc.UserServiceAsync;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class RecipePresenter extends AbstractPresenter {

	// screen and parameters name
	public static final String SCREEN_NAME = "recipe";
	public static final String PARAMETER_RECIPE_ID = "id";
	public static final String PARAMETER_VIEW = "view"; // basic, steps, video, audio

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

	private final List<String> screenNames = Arrays.asList("recipe", "basic", "video", "audio");
	
	@Override
	protected boolean isPresenterForScreen(String screenName) {
		return screenName != null && screenNames.contains(screenName);
	}

	@Override
	protected void parseParameters(String screenName, Map<String, String> parameters) {
		// TODO Auto-generated method stub
		try {
			recipeId = Long.parseLong(parameters.get(PARAMETER_RECIPE_ID));
			getRecipe(recipeId);
		} catch (Exception e) {
			// TODO display 404 page 
		}
	}

	private void getRecipe(long recipeId) {
		recipeService.getRecipeDetails(recipeId, language, new AsyncCallback<RecipeDetailsDto>() {
			@Override
			public void onSuccess(RecipeDetailsDto recipe) {
				recipeView.displayRecipe(recipe);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	private void bookmark(boolean add) {
		userService.bookmarkRecipe(null, recipeId, add, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	private void comment(String content) {
		userService.commentRecipe(null, recipeId, content, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				// TODO Auto-generated method stub
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

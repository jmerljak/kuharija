package si.merljak.magistrska.client.mvp;

import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.event.LoginEvent;
import si.merljak.magistrska.client.event.LoginEventHandler;
import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.dto.UserDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.RecipeServiceAsync;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class RecipePresenter extends AbstractPresenter implements LoginEventHandler {

	// screen and parameters name
	public static final String SCREEN_NAME = "recipe";
	private static final String PARAMETER_RECIPE_ID = "id";
	private static final String PARAMETER_VIEW = "view"; // basic, steps, video, audio

	// remote service
	private final RecipeServiceAsync recipeService;

	private long recipeId;
    private final RecipeView recipeView = new RecipeView();
    private UserDto user;

	public RecipePresenter(Language language, RecipeServiceAsync recipeService, EventBus eventBus) {
		super(language);
		this.recipeService = recipeService;
		eventBus.addHandler(LoginEvent.TYPE, this);
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		try {
			recipeId = Long.parseLong(parameters.get(PARAMETER_RECIPE_ID));
			String view = parameters.get(PARAMETER_VIEW);
			getRecipe(recipeId, view);
		} catch (Exception e) {
			recipeView.displayRecipe(null, null);
		}
		return recipeView.asWidget();
	}

	/** 
	 * Gets recipe by ID.
	 * 
	 * @param recipeId recipe ID
	 * @param view
	 */
	private void getRecipe(long recipeId, final String view) {
		String username = user != null ? user.getUsername() : null;
		recipeService.getRecipeDetails(recipeId, language, username, new AsyncCallback<RecipeDetailsDto>() {
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

	/** Bookmarks / removes user bookmark. */
	public void bookmark(final boolean isBookmarked) {
		String username = user != null ? user.getUsername() : null;
		recipeService.bookmarkRecipe(username, recipeId, isBookmarked, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void nothing) {
				recipeView.setBookmarked(isBookmarked);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Kuharija.handleException(caught);
			}
		});
	}

	/** Adds user comment. */
	public void comment(String content) {
		String username = user != null ? user.getUsername() : null;
		recipeService.commentRecipe(username, recipeId, content, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void nothing) {
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

	@Override
	public void onLogin(LoginEvent event) {
		// register user
		user = event.getUser();
	}

}

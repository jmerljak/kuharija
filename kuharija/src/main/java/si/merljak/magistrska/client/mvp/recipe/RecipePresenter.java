package si.merljak.magistrska.client.mvp.recipe;

import java.util.List;
import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.event.LoginEvent;
import si.merljak.magistrska.client.event.LoginEventHandler;
import si.merljak.magistrska.client.mvp.AbstractPresenter;
import si.merljak.magistrska.client.mvp.home.HomePresenter;
import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.dto.UserDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.RecipeServiceAsync;
import si.merljak.magistrska.common.rpc.mock.WOzServiceAsync;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 * Presenter for recipe details.
 * 
 * @author Jakob Merljak
 * 
 */
public class RecipePresenter extends AbstractPresenter implements LoginEventHandler {

	// screen and parameters name
	public static final String SCREEN_NAME = "recipe";
	private static final String PARAMETER_RECIPE_ID = "id";
	private static final String PARAMETER_VIEW = "view";

	// remote service
	private final RecipeServiceAsync recipeService;
	private final WOzServiceAsync wozService;

	// view
	private final RecipeView recipeView = new RecipeView();

	// variables
	private RecipeDetailsDto recipeDto;
	private UserDto user;
	private Map<String, String> userPreferences = null;

	// timer (WOz)
	private final Timer timer;

	public RecipePresenter(Language language, RecipeServiceAsync recipeService, WOzServiceAsync wozService, EventBus eventBus) {
		super(language);
		this.recipeService = recipeService;
		this.wozService = wozService;
		eventBus.addHandler(LoginEvent.TYPE, this);

		timer = new Timer() {
			@Override
			public void run() {
				if (recipeView.isAttached()) {
					getActions();
				} else {
					timer.cancel();
				}
			}
		};
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		try {
			long recipeId = Long.parseLong(parameters.get(PARAMETER_RECIPE_ID));

			if (recipeDto == null || recipeDto.getId() != recipeId) {
				String view = userPreferences != null ? userPreferences.get(PARAMETER_VIEW) : null;
				getRecipe(recipeId, view);
			}
		} catch (Exception e) {
			recipeView.setVisible(false);
			recipeView.displayRecipe(null, null);
			recipeView.setVisible(true);
		}

		if (user != null) {
			timer.scheduleRepeating(2500);
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
		recipeView.setVisible(false);
		String username = user != null ? user.getUsername() : null;
		recipeService.getRecipeDetails(recipeId, language, username, new AsyncCallback<RecipeDetailsDto>() {
			@Override
			public void onSuccess(RecipeDetailsDto recipe) {
				recipeView.displayRecipe(recipe, view);
				recipeView.setVisible(true);
				recipeDto = recipe;
			}

			@Override
			public void onFailure(Throwable caught) {
				Kuharija.handleException(caught);
			}
		});
	}

	/**
	 * Bookmarks / removes user bookmark.
	 * 
	 * @param isBookmarked <em>true</em> if addding bookmark, <em>false</em> if removing
	 */
	public void bookmark(final boolean isBookmarked) {
		if (user != null && recipeDto != null) {
			recipeService.bookmarkRecipe(user.getUsername(), recipeDto.getId(), isBookmarked, new AsyncCallback<Void>() {
				@Override
				public void onSuccess(Void nothing) {
//					recipeView.setBookmarked(isBookmarked);
				}

				@Override
				public void onFailure(Throwable caught) {
					Kuharija.handleException(caught);
				}
			});
		}
	}

	/**
	 * Adds user comment.
	 * 
	 * @param content comment content
	 */
	public void comment(String content) {
		if (user != null && recipeDto != null) {
			recipeService.commentRecipe(user.getUsername(), recipeDto.getId(), content, new AsyncCallback<Void>() {
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

	/**
	 * Builds proper anchor URL for recipe.
	 * 
	 * @param id recipe's id
	 * @param view view
	 * @return anchor URL
	 */
	public static String buildRecipeUrl(long id, String view) {
		return "#" + SCREEN_NAME +
			   "&" + PARAMETER_RECIPE_ID + "=" + id +
			   "&" + PARAMETER_VIEW + "=" + view;
	}

	@Override
	public void onLogin(LoginEvent event) {
		// register user
		user = event.getUser();

		if (user != null && user.getPreferences() != null) {
			userPreferences = Kuharija.keyValueSplitter.split(user.getPreferences());
			if (recipeView.isAttached()) {
				timer.scheduleRepeating(2500);
			}
		} else {
			userPreferences = null;
			timer.cancel();
		}
	}

	private void getActions() {
		wozService.getActions(new AsyncCallback<List<String>>() {
			@Override
			public void onSuccess(List<String> actions) {
				if (actions != null && !actions.isEmpty()) {
					recipeView.performActions(actions);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				// do nothing
			}
		});
	}

	@Override
	public String getScreenName() {
		return SCREEN_NAME;
	}

	@Override
	public String getParentName() {
		return HomePresenter.SCREEN_NAME;
	}

}

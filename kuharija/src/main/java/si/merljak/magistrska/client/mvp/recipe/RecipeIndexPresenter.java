package si.merljak.magistrska.client.mvp.recipe;

import java.util.Map;

import si.merljak.magistrska.client.event.LoginEvent;
import si.merljak.magistrska.client.event.LoginEventHandler;
import si.merljak.magistrska.client.mvp.AbstractPresenter;
import si.merljak.magistrska.client.mvp.home.HomePresenter;
import si.merljak.magistrska.common.dto.UserDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.RecipeServiceAsync;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Widget;

/**
 * Presenter for recipe index.
 * 
 * @author Jakob Merljak
 * 
 */
public class RecipeIndexPresenter extends AbstractPresenter implements LoginEventHandler {

	// screen and parameters name
	public static final String SCREEN_NAME = "recipes";

	// remote service
	private final RecipeServiceAsync recipeService;

	// view
	private final RecipeIndexView view = new RecipeIndexView();

	// variables
	private UserDto user;

	public RecipeIndexPresenter(Language language, RecipeServiceAsync recipeService, EventBus eventBus) {
		super(language);
		this.recipeService = recipeService;
		eventBus.addHandler(LoginEvent.TYPE, this);
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		return view.asWidget();
	}

	@Override
	public void onLogin(LoginEvent event) {
		// register user
		user = event.getUser();
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

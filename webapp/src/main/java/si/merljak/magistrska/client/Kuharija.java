package si.merljak.magistrska.client;

import java.util.HashMap;
import java.util.Map;

import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.client.i18n.CommonMessages;
import si.merljak.magistrska.client.i18n.Formatters;
import si.merljak.magistrska.client.i18n.IngredientsConstants;
import si.merljak.magistrska.client.i18n.UrlConstants;
import si.merljak.magistrska.client.i18n.UtensilsConstants;
import si.merljak.magistrska.client.mvp.AbstractPresenter;
import si.merljak.magistrska.client.mvp.ComparePresenter;
import si.merljak.magistrska.client.mvp.HomePresenter;
import si.merljak.magistrska.client.mvp.IngredientPresenter;
import si.merljak.magistrska.client.mvp.LoginPresenter;
import si.merljak.magistrska.client.mvp.LoginView;
import si.merljak.magistrska.client.mvp.RecipePresenter;
import si.merljak.magistrska.client.mvp.SearchPresenter;
import si.merljak.magistrska.client.mvp.UtensilPresenter;
import si.merljak.magistrska.client.widgets.LocaleWidget;
import si.merljak.magistrska.client.widgets.MainNav;
import si.merljak.magistrska.client.widgets.UserWidget;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.IngredientService;
import si.merljak.magistrska.common.rpc.IngredientServiceAsync;
import si.merljak.magistrska.common.rpc.RecipeService;
import si.merljak.magistrska.common.rpc.RecipeServiceAsync;
import si.merljak.magistrska.common.rpc.RecommendationService;
import si.merljak.magistrska.common.rpc.RecommendationServiceAsync;
import si.merljak.magistrska.common.rpc.SearchService;
import si.merljak.magistrska.common.rpc.SearchServiceAsync;
import si.merljak.magistrska.common.rpc.UserService;
import si.merljak.magistrska.common.rpc.UserServiceAsync;

import com.github.gwtbootstrap.client.ui.Breadcrumbs;
import com.google.gwt.aria.client.Roles;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.Position.Coordinates;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class Kuharija implements EntryPoint {

	// remote services
	private static final IngredientServiceAsync ingredientService = GWT.create(IngredientService.class);
	private static final RecipeServiceAsync recipeService = GWT.create(RecipeService.class);
	private static final SearchServiceAsync searchService = GWT.create(SearchService.class);
	private static final UserServiceAsync userService = GWT.create(UserService.class);
	private static final RecommendationServiceAsync recommendationService = GWT.create(RecommendationService.class);

	// i18n
	public static final CommonConstants constants = GWT.create(CommonConstants.class);
	public static final CommonMessages messages = GWT.create(CommonMessages.class);
	public static final IngredientsConstants ingredientsConstants = GWT.create(IngredientsConstants.class);
	public static final UtensilsConstants utensilsConstants = GWT.create(UtensilsConstants.class);
	public static final UrlConstants urlConstants = GWT.create(UrlConstants.class);

    // formatters
	public static final Formatters formatters = GWT.create(Formatters.class);
	public static final NumberFormat numberFormat = NumberFormat.getFormat(formatters.numberFormat());
	public static final DateTimeFormat dateFormat = DateTimeFormat.getFormat(formatters.dateFormat());
	public static final DateTimeFormat timestampFormat = DateTimeFormat.getFormat(formatters.timestampFormat());

	// presenters
	private Map<String, AbstractPresenter> presenters = new HashMap<String, AbstractPresenter>();

	// widgets
	private LocaleWidget localeWidget;
	private Breadcrumbs breadcrumbs;

	// user
	private static Coordinates coordinates;

	boolean isSessionCheckComplete = false;
	boolean isGeolocateComplete = false;

	private RootPanel main;

	public void onModuleLoad() {
		main = RootPanel.get("main");
		Roles.getMainRole().set(main.getElement());
		Roles.getMainRole().getAriaLiveProperty(main.getElement());

		// user
		geolocate();
		
		breadcrumbs = new Breadcrumbs("→");

		localeWidget = new LocaleWidget();
		RootPanel.get("nav").add(localeWidget);
		RootPanel.get("nav").add(breadcrumbs);
		RootPanel.get("nav").add(new MainNav());
		Language language = localeWidget.getCurrentLanguage();

		// TODO refactoriae MVP architecture!
		LoginPresenter loginPresenter = new LoginPresenter(language, userService, new LoginView());
		presenters.put(IngredientPresenter.SCREEN_NAME, new IngredientPresenter(language, ingredientService));
		presenters.put(UtensilPresenter.SCREEN_NAME, new UtensilPresenter(language, ingredientService));
		presenters.put(RecipePresenter.SCREEN_NAME, new RecipePresenter(language, recipeService, userService));
		presenters.put(SearchPresenter.SCREEN_NAME, new SearchPresenter(language, searchService));
		presenters.put(ComparePresenter.SCREEN_NAME, new ComparePresenter(language, recipeService));
		presenters.put(LoginPresenter.SCREEN_NAME, loginPresenter);
		presenters.put(HomePresenter.SCREEN_NAME, new HomePresenter(language, recommendationService));

		RootPanel.get("userWrapper").add(new UserWidget(loginPresenter));

		// history handler
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				String[] historyTokens = event.getValue().split("&");

				// screen name should be first token
				String screenName = historyTokens[0];

				// get parameters
				Map<String, String> parameters = new HashMap<String, String>();
				for (int i = 1; i < historyTokens.length; i++) {
					String[] param = historyTokens[i].split("=");
					if (param.length > 1) {
						parameters.put(param[0], param[1]);
					} else {
						parameters.put(param[0], "");
					}
				}

				AbstractPresenter presenter = presenters.get(screenName);
				if (presenter != null) {
					main.clear();
					main.add(presenter.parseParameters(parameters));
				} else {
					// 404 view
				}

				breadcrumbs.clear();
				breadcrumbs.add(new Anchor("home", "#home"));
				breadcrumbs.add(new Label(screenName));
			}
		});
		fireCurrentHistory();
	}

	private void fireCurrentHistory() {
		// init current history state
		History.fireCurrentHistoryState();
	}

	/** Tries to get user's location. */
	private void geolocate() {
		Geolocation geolocation = Geolocation.getIfSupported();
		if (geolocation != null) {
			geolocation.getCurrentPosition(new Callback<Position, PositionError>() {
				@Override
				public void onSuccess(Position position) {
					coordinates = position.getCoordinates();
					isGeolocateComplete = true;
					if (isSessionCheckComplete) {
						fireCurrentHistory();
					}
				}
				
				@Override
				public void onFailure(PositionError reason) {
					// ignore
					isGeolocateComplete = true;
					if (isSessionCheckComplete) {
						fireCurrentHistory();
					}
				}
			});
		} else {
			isGeolocateComplete = true;
			if (isSessionCheckComplete) {
				fireCurrentHistory();
			}
		}
	}

	/** Handles common RPC call exceptions. */  
	public static void handleException(Throwable caught) {
		Window.alert(caught.getMessage());
	}

	/** 
	 * Sets document/window title.
	 * @param title title for the screen (nullable).
	 */
	public static void setWindowTitle(String title) {
		if (title != null) {
			Document.get().setTitle(title + " - " + constants.appTitle());
		} else {
			Document.get().setTitle(constants.appTitle());
		}
	}

	public static Coordinates getCoordinates() {
		return coordinates;
	}
}

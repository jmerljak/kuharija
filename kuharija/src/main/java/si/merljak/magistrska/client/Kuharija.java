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
import si.merljak.magistrska.client.mvp.HomePresenter;
import si.merljak.magistrska.client.mvp.RecipePresenter;
import si.merljak.magistrska.client.mvp.compare.ComparePresenter;
import si.merljak.magistrska.client.mvp.ingredient.IngredientPresenter;
import si.merljak.magistrska.client.mvp.login.LoginPresenter;
import si.merljak.magistrska.client.mvp.login.LoginView;
import si.merljak.magistrska.client.mvp.search.SearchPresenter;
import si.merljak.magistrska.client.mvp.utensil.UtensilPresenter;
import si.merljak.magistrska.client.widgets.LocaleWidget;
import si.merljak.magistrska.client.widgets.MainMenuWidget;
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
import si.merljak.magistrska.common.rpc.UtensilService;
import si.merljak.magistrska.common.rpc.UtensilServiceAsync;

import com.github.gwtbootstrap.client.ui.Alert;
import com.github.gwtbootstrap.client.ui.Breadcrumbs;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.github.gwtbootstrap.client.ui.event.CloseEvent;
import com.github.gwtbootstrap.client.ui.event.CloseHandler;
import com.google.common.base.Splitter;
import com.google.common.base.Splitter.MapSplitter;
import com.google.gwt.aria.client.Roles;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.Position.Coordinates;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

public class Kuharija implements EntryPoint {

	// remote services
	private static final IngredientServiceAsync ingredientService = GWT.create(IngredientService.class);
	private static final RecipeServiceAsync recipeService = GWT.create(RecipeService.class);
	private static final SearchServiceAsync searchService = GWT.create(SearchService.class);
	private static final UserServiceAsync userService = GWT.create(UserService.class);
	private static final UtensilServiceAsync utensilService = GWT.create(UtensilService.class);
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

	// utils
	private static final MapSplitter keyValueSplitter = Splitter.on("&").trimResults().omitEmptyStrings().withKeyValueSeparator("=");

	// presenters
	private Map<String, AbstractPresenter> presenters = new HashMap<String, AbstractPresenter>();

	// panels & widgets
	private RootPanel mainPanel;
	private LocaleWidget localeWidget;
	private Breadcrumbs breadcrumbs = new Breadcrumbs("→");
	private static final SimplePanel alertPlaceholder = new SimplePanel();

	// user
	private static Coordinates coordinates;

	public void onModuleLoad() {
		mainPanel = RootPanel.get("main");
		Roles.getMainRole().set(mainPanel.getElement());
		Roles.getMainRole().getAriaLiveProperty(mainPanel.getElement());
		Roles.getAlertRole().set(alertPlaceholder.getElement());

        EventBus eventBus = new SimpleEventBus();

        // user
		geolocate();

		localeWidget = new LocaleWidget();
		RootPanel.get("nav").add(localeWidget);
		RootPanel.get("nav").add(breadcrumbs);
		RootPanel.get("nav").add(new MainMenuWidget());
		RootPanel.get("nav").add(alertPlaceholder);
		Language language = localeWidget.getCurrentLanguage();

		// TODO refactorize MVP architecture!
		LoginPresenter loginPresenter = new LoginPresenter(language, userService, new LoginView(), eventBus);
		presenters.put(IngredientPresenter.SCREEN_NAME, new IngredientPresenter(language, ingredientService));
		presenters.put(UtensilPresenter.SCREEN_NAME, new UtensilPresenter(language, utensilService));
		presenters.put(RecipePresenter.SCREEN_NAME, new RecipePresenter(language, recipeService, userService, eventBus));
		presenters.put(SearchPresenter.SCREEN_NAME, new SearchPresenter(language, searchService));
		presenters.put(ComparePresenter.SCREEN_NAME, new ComparePresenter(language, recipeService));
		presenters.put(LoginPresenter.SCREEN_NAME, loginPresenter);
		HomePresenter homePresenter = new HomePresenter(language, recommendationService, eventBus);
		presenters.put(HomePresenter.SCREEN_NAME, homePresenter);

		UserWidget userWidget = new UserWidget(loginPresenter, eventBus);
		RootPanel.get("userWrapper").add(userWidget);

		// history handler
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				String historyToken = event.getValue();
				String screenName = historyToken;
				Map<String, String> parameters = new HashMap<String, String>(0);
				
				if (historyToken.contains("&")) {
					int separatorIndex = historyToken.indexOf("&");
					// screen name should be first token
					screenName = historyToken.substring(0, separatorIndex);
					try {
						// rest are parameters
						historyToken = historyToken.substring(separatorIndex, historyToken.length());
						parameters = keyValueSplitter.split(historyToken);
					} catch (Exception e) {
						// incorrect URL, redirect to screen name
						History.newItem(screenName);
					}
				}

				// get parameters
				AbstractPresenter presenter = presenters.get(screenName);
				mainPanel.clear();
				if (presenter != null) {
					mainPanel.add(presenter.parseParameters(parameters));
				} else {
					// TODO 404 view
				}

				alertPlaceholder.clear();

				breadcrumbs.clear();
				breadcrumbs.add(new Anchor(constants.home(), "#" + HomePresenter.SCREEN_NAME));
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
		if (Geolocation.isSupported()) {
			Geolocation geolocation = Geolocation.getIfSupported();
			geolocation.getCurrentPosition(new Callback<Position, PositionError>() {
				@Override
				public void onSuccess(Position position) {
					coordinates = position.getCoordinates();
					((HomePresenter) presenters.get(HomePresenter.SCREEN_NAME)).setCoordinates(coordinates);
					// TODO geolocate event
				}
				
				@Override
				public void onFailure(PositionError reason) {
					// ignore
				}
			});
		}
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

	/** Handles common RPC call exceptions. */  
	public static void handleException(Throwable caught) {
		Alert alert = new Alert(messages.unknownError(), AlertType.ERROR);
		alert.setAnimation(true);
		alert.addCloseHandler(new CloseHandler() {
			@Override
			public void onClose(CloseEvent closeEvent) {
				alertPlaceholder.clear();
			}
		});
		alertPlaceholder.setWidget(alert);
	}
}

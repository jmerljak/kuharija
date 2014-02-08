package si.merljak.magistrska.client;

import java.util.HashMap;
import java.util.Map;

import si.merljak.magistrska.client.event.GeolocateEvent;
import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.client.i18n.CommonMessages;
import si.merljak.magistrska.client.i18n.Formatters;
import si.merljak.magistrska.client.i18n.IngredientsConstants;
import si.merljak.magistrska.client.i18n.UrlConstants;
import si.merljak.magistrska.client.i18n.UtensilsConstants;
import si.merljak.magistrska.client.mvp.AbstractPresenter;
import si.merljak.magistrska.client.mvp.AbstractView;
import si.merljak.magistrska.client.mvp.compare.ComparePresenter;
import si.merljak.magistrska.client.mvp.error.NotFoundPresenter;
import si.merljak.magistrska.client.mvp.home.HomePresenter;
import si.merljak.magistrska.client.mvp.ingredient.IngredientIndexPresenter;
import si.merljak.magistrska.client.mvp.ingredient.IngredientPresenter;
import si.merljak.magistrska.client.mvp.login.LoginPresenter;
import si.merljak.magistrska.client.mvp.login.LoginView;
import si.merljak.magistrska.client.mvp.mock.WizardControlsPresenter;
import si.merljak.magistrska.client.mvp.recipe.RecipePresenter;
import si.merljak.magistrska.client.mvp.search.SearchPresenter;
import si.merljak.magistrska.client.mvp.utensil.UtensilIndexPresenter;
import si.merljak.magistrska.client.mvp.utensil.UtensilPresenter;
import si.merljak.magistrska.client.widgets.LocaleWidget;
import si.merljak.magistrska.client.widgets.MainMenuWidget;
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
import si.merljak.magistrska.common.rpc.mock.WOzService;
import si.merljak.magistrska.common.rpc.mock.WOzServiceAsync;

import com.github.gwtbootstrap.client.ui.Alert;
import com.github.gwtbootstrap.client.ui.Breadcrumbs;
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.base.AlertBase;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.google.common.base.Joiner;
import com.google.common.base.Joiner.MapJoiner;
import com.google.common.base.Splitter;
import com.google.common.base.Splitter.MapSplitter;
import com.google.gwt.aria.client.Roles;
import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Entry point for application.
 * 
 * @author Jakob Merljak
 * 
 */
public class Kuharija implements EntryPoint {

	// remote services
	private static final IngredientServiceAsync ingredientService = GWT.create(IngredientService.class);
	private static final RecipeServiceAsync recipeService = GWT.create(RecipeService.class);
	private static final SearchServiceAsync searchService = GWT.create(SearchService.class);
	private static final UserServiceAsync userService = GWT.create(UserService.class);
	private static final UtensilServiceAsync utensilService = GWT.create(UtensilService.class);
	private static final RecommendationServiceAsync recommendationService = GWT.create(RecommendationService.class);
	private static final WOzServiceAsync wOzService = GWT.create(WOzService.class);

	// i18n
	public static final CommonConstants constants = GWT.create(CommonConstants.class);
	public static final CommonMessages messages = GWT.create(CommonMessages.class);
	public static final IngredientsConstants ingredientsConstants = GWT.create(IngredientsConstants.class);
	public static final UtensilsConstants utensilsConstants = GWT.create(UtensilsConstants.class);
	public static final UrlConstants urlConstants = GWT.create(UrlConstants.class);

	// constants
	public static final String CSS_VISUALLY_HIDDEN = "visuallyhidden";

	// formatters
	public static final Formatters formatters = GWT.create(Formatters.class);
	public static final NumberFormat numberFormat = NumberFormat.getFormat(formatters.numberFormat());
	public static final DateTimeFormat dateFormat = DateTimeFormat.getFormat(formatters.dateFormat());
	public static final DateTimeFormat timestampFormat = DateTimeFormat.getFormat(formatters.timestampFormat());

	// utilities
	public static final Joiner listJoiner = Joiner.on(",").skipNulls();
	public static final Splitter listSplitter = Splitter.on(",").trimResults().omitEmptyStrings();
	public static final MapJoiner keyValueJoiner = Joiner.on("&").withKeyValueSeparator("=");
	public static final MapSplitter keyValueSplitter = Splitter.on("&").trimResults().omitEmptyStrings().withKeyValueSeparator("=");

	// event bus
	private final EventBus eventBus = new SimpleEventBus();

	// presenters
	private final Map<String, AbstractPresenter> presenters = new HashMap<String, AbstractPresenter>();

	// panels
	private final RootPanel navPanel = RootPanel.get("nav");
	private final RootPanel headerPanel = RootPanel.get("header");
	private final RootPanel mainPanel = RootPanel.get("main");
	private final RootPanel footerPanel = RootPanel.get("footer");

	// widgets
	private final LocaleWidget localeWidget = new LocaleWidget();
	private final Breadcrumbs breadcrumbs = new Breadcrumbs("→");
	private static final SimplePanel alertPlaceholder = new SimplePanel();

	public void onModuleLoad() {
		Roles.getMainRole().set(mainPanel.getElement());
		Roles.getMainRole().getAriaLiveProperty(mainPanel.getElement());
		Roles.getNavigationRole().set(navPanel.getElement());
		Roles.getNavigationRole().set(breadcrumbs.getElement());
		Roles.getAlertRole().set(alertPlaceholder.getElement());

		Language language = localeWidget.getCurrentLanguage();

		// MVP
		final LoginPresenter loginPresenter = new LoginPresenter(language, userService, new LoginView(), eventBus);
		presenters.put(IngredientIndexPresenter.SCREEN_NAME, new IngredientIndexPresenter(language));
		presenters.put(IngredientPresenter.SCREEN_NAME, new IngredientPresenter(language, ingredientService));
		presenters.put(UtensilPresenter.SCREEN_NAME, new UtensilPresenter(language, utensilService));
		presenters.put(UtensilIndexPresenter.SCREEN_NAME, new UtensilIndexPresenter(language));
		presenters.put(RecipePresenter.SCREEN_NAME, new RecipePresenter(language, recipeService, wOzService, eventBus));
		presenters.put(SearchPresenter.SCREEN_NAME, new SearchPresenter(language, searchService));
		presenters.put(ComparePresenter.SCREEN_NAME, new ComparePresenter(language, recipeService));
		presenters.put(LoginPresenter.SCREEN_NAME, loginPresenter);
		presenters.put(HomePresenter.SCREEN_NAME, new HomePresenter(language, recommendationService, eventBus));
		presenters.put(NotFoundPresenter.SCREEN_NAME, new NotFoundPresenter(language));
		presenters.put(WizardControlsPresenter.SCREEN_NAME, new WizardControlsPresenter(language, wOzService));

		MainMenuWidget mainMenu = new MainMenuWidget(loginPresenter, eventBus);
		navPanel.add(mainMenu);

		Label breadcrumbsLabel = new InlineLabel(constants.youAreHere());
		breadcrumbsLabel.setStyleName(CSS_VISUALLY_HIDDEN);
		breadcrumbs.addStyleName(CSS_VISUALLY_HIDDEN);

		Image headerImage = new Image();
		headerImage.setUrl(AbstractView.IMG_BASE_FOLDER + "kuharija.jpg");
		headerImage.setAltText("logo");
		headerImage.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				History.newItem(HomePresenter.SCREEN_NAME);
			}
		});

		headerPanel.add(headerImage);
		headerPanel.add(breadcrumbsLabel);
		headerPanel.add(breadcrumbs);
		headerPanel.add(alertPlaceholder);

		Label languageLabel = new InlineLabel(constants.language());
		languageLabel.setStyleName(CSS_VISUALLY_HIDDEN);
		footerPanel.add(languageLabel);
		footerPanel.add(localeWidget);

		// geolocate
		geolocate();

		// history handler
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				String historyToken = event.getValue();
				String screenName = historyToken;
				Map<String, String> parameters = new HashMap<String, String>(0);

				// get parameters
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

				loginPresenter.cancelRedirectTimer();
				breadcrumbs.clear();
				mainPanel.clear();

				// let appropriate presenter handles history event
				AbstractPresenter presenter = presenters.get(screenName);
				if (presenter == null) {
					// 404 view
					presenter = presenters.get(NotFoundPresenter.SCREEN_NAME);
				}
				mainPanel.add(presenter.parseParameters(parameters));
				buildBreadcrumbs(presenter, true);

				alertPlaceholder.clear();
			}
		});

		History.fireCurrentHistoryState();
	}

	/** 
	 * Recursively builds breadcrumbs.
	 * 
	 * @param presenter
	 * @param isLast if it is last level
	 */
	private void buildBreadcrumbs(AbstractPresenter presenter, boolean isLast) {
		AbstractPresenter parentPresenter = presenters.get(presenter.getParentName());
		if (parentPresenter != null) {
			buildBreadcrumbs(parentPresenter, false);
		}

		String screenName = presenter.getScreenName();
		String localizedName = urlConstants.screenNameMap().get(screenName.isEmpty() ? "home" : screenName);
		if (isLast) {
			breadcrumbs.add(new Label(localizedName));
		} else {
			breadcrumbs.add(new Anchor(localizedName, "#" + screenName));
		}
	}

	/** Tries to get user's location. */
	private void geolocate() {
		if (Geolocation.isSupported()) {
			Geolocation geolocation = Geolocation.getIfSupported();
			geolocation.getCurrentPosition(new Callback<Position, PositionError>() {
				@Override
				public void onSuccess(Position position) {
					eventBus.fireEvent(new GeolocateEvent(position.getCoordinates()));
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

	/** Handles common RPC exceptions. */  
	public static void handleException(Throwable caught) {
		Alert alert = new Alert(messages.unknownError(), AlertType.ERROR);
		alert.setAnimation(true);
		alert.addCloseHandler(new CloseHandler<AlertBase>() {
			@Override
			public void onClose(CloseEvent<AlertBase> closeEvent) {
				alertPlaceholder.clear();
			}
		});
		alertPlaceholder.setWidget(alert);
	}
}

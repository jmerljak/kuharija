package si.merljak.magistrska.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.client.i18n.CommonMessages;
import si.merljak.magistrska.client.i18n.Formatters;
import si.merljak.magistrska.client.mvp.AbstractPresenter;
import si.merljak.magistrska.client.mvp.ComparePresenter;
import si.merljak.magistrska.client.mvp.IngredientPresenter;
import si.merljak.magistrska.client.mvp.RecipePresenter;
import si.merljak.magistrska.client.mvp.SearchPresenter;
import si.merljak.magistrska.client.widgets.LocaleWidget;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.IngredientService;
import si.merljak.magistrska.common.rpc.IngredientServiceAsync;
import si.merljak.magistrska.common.rpc.RecipeService;
import si.merljak.magistrska.common.rpc.RecipeServiceAsync;
import si.merljak.magistrska.common.rpc.SearchService;
import si.merljak.magistrska.common.rpc.SearchServiceAsync;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;

public class Kuharija implements EntryPoint {

	// remote services
	private static final IngredientServiceAsync ingredientService = GWT.create(IngredientService.class);
	private static final RecipeServiceAsync recipeService = GWT.create(RecipeService.class);
	private static final SearchServiceAsync searchService = GWT.create(SearchService.class);

	// common constants
	public static final CommonConstants constants = GWT.create(CommonConstants.class);
	public static final CommonMessages messages = GWT.create(CommonMessages.class);

    // formatters
	public static final Formatters formatters = GWT.create(Formatters.class);
	public static final NumberFormat numberFormat = NumberFormat.getFormat(formatters.numberFormat());
	public static final DateTimeFormat dateFormat = DateTimeFormat.getFormat(formatters.dateFormat());

	// presenters
	private List<AbstractPresenter> presenters = new ArrayList<AbstractPresenter>();

	// widgets
	private LocaleWidget localeWidget;
    
	public void onModuleLoad() {

		localeWidget = new LocaleWidget();
		RootPanel.get("nav").add(localeWidget);
		Language language = localeWidget.getCurrentLanguage();

		presenters.add(new IngredientPresenter(language, ingredientService));
		presenters.add(new RecipePresenter(language, recipeService));
		presenters.add(new SearchPresenter(language, searchService));
		presenters.add(new ComparePresenter(language, recipeService));

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

				// notify and let presenters handle screen change
				for (AbstractPresenter presenter : presenters) {
					presenter.handleScreenChange(screenName, parameters);
				}
			}
		});

		// init current history state
		History.fireCurrentHistoryState();
	}

	/** Loads custom javascript library. */
	public static void addScript(String url) {
	    Element e = DOM.createElement("script");
	    e.setAttribute("language", "JavaScript");
	    e.setAttribute("src", url);
	    DOM.appendChild(RootPanel.get().getElement(), e);
	}
}

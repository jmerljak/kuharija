package si.merljak.magistrska.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.client.i18n.CommonMessages;
import si.merljak.magistrska.client.i18n.Formatters;
import si.merljak.magistrska.client.mvp.AbstractPresenter;
import si.merljak.magistrska.client.mvp.IngredientPresenter;
import si.merljak.magistrska.client.mvp.RecipePresenter;
import si.merljak.magistrska.client.mvp.SearchPresenter;
import si.merljak.magistrska.client.rpc.IngredientService;
import si.merljak.magistrska.client.rpc.IngredientServiceAsync;
import si.merljak.magistrska.client.rpc.RecipeService;
import si.merljak.magistrska.client.rpc.RecipeServiceAsync;
import si.merljak.magistrska.client.rpc.SearchService;
import si.merljak.magistrska.client.rpc.SearchServiceAsync;
import si.merljak.magistrska.client.widgets.LocaleWidget;
import si.merljak.magistrska.client.widgets.TabsWidget;

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

public class KuharijaEntry implements EntryPoint {

	// remote service proxies
	public static final IngredientServiceAsync ingredientService = GWT.create(IngredientService.class);
	public static final RecipeServiceAsync recipeService = GWT.create(RecipeService.class);
	public static final SearchServiceAsync searchService = GWT.create(SearchService.class);

	// constants
	public static final CommonConstants constants = GWT.create(CommonConstants.class);
	public static final Formatters formatters = GWT.create(Formatters.class);
	public static final CommonMessages messages = GWT.create(CommonMessages.class);

    // formatters
	public static final NumberFormat numberFormat = NumberFormat.getFormat(formatters.numberFormat());
	public static final DateTimeFormat dateFormat = DateTimeFormat.getFormat(formatters.dateFormat());

	// presenters
	private List<AbstractPresenter> presenters = new ArrayList<AbstractPresenter>();
    private LocaleWidget localeWidget;
	private TabsWidget tabsWidget;
    
	public void onModuleLoad() {

		localeWidget = new LocaleWidget();
		RootPanel.get("nav").add(localeWidget);

		presenters.add(new IngredientPresenter(localeWidget.getCurrentLanguage()));
		presenters.add(new RecipePresenter(localeWidget.getCurrentLanguage()));
		presenters.add(new SearchPresenter(localeWidget.getCurrentLanguage()));
		
		// tabs
		tabsWidget = new TabsWidget();
		RootPanel.get("tabs").add(tabsWidget);

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
					parameters.put(param[0], param[1]);
				}
				// TODO catch exception

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

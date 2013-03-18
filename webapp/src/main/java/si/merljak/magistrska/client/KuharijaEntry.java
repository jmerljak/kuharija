package si.merljak.magistrska.client;

import java.util.List;

import si.merljak.magistrska.client.i18n.GlobalConstants;
import si.merljak.magistrska.client.i18n.GlobalFormatters;
import si.merljak.magistrska.client.i18n.GlobalMessages;
import si.merljak.magistrska.client.mvp.RecipeView;
import si.merljak.magistrska.client.rpc.RecipeService;
import si.merljak.magistrska.client.rpc.RecipeServiceAsync;
import si.merljak.magistrska.client.rpc.SearchService;
import si.merljak.magistrska.client.rpc.SearchServiceAsync;
import si.merljak.magistrska.client.widgets.LocaleWidget;
import si.merljak.magistrska.client.widgets.TabsWidget;
import si.merljak.magistrska.common.dto.RecipeDto;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class KuharijaEntry implements EntryPoint {

	private static final int PAGE_SIZE = 15;

	// remote service proxies
	private final RecipeServiceAsync recipeService = GWT.create(RecipeService.class);
	private final SearchServiceAsync searchService = GWT.create(SearchService.class);

	// constants
	private static final GlobalConstants constants = GWT.create(GlobalConstants.class);
	private static final GlobalFormatters formatters = GWT.create(GlobalFormatters.class);
	private static final GlobalMessages messages = GWT.create(GlobalMessages.class);

    // formatters
    private static final NumberFormat numberFormat = NumberFormat.getFormat(formatters.numberFormat());
    private static final DateTimeFormat dateFormat = DateTimeFormat.getFormat(formatters.dateFormat());

    private RecipeView recipeView;
    private LocaleWidget localeWidget;
    
	public void onModuleLoad() {
		recipeView = new RecipeView();

		localeWidget = new LocaleWidget();
		RootPanel.get("nav").add(localeWidget);
		
		// tabs
		RootPanel.get("tabs").add(new TabsWidget());

		// history handler
		History.addValueChangeHandler(new ValueChangeHandler<String>() {
			public void onValueChange(ValueChangeEvent<String> event) {
				String historyToken = event.getValue();
				if (historyToken.equalsIgnoreCase("basic")) {
					getRecipe();
				} else if (historyToken.equalsIgnoreCase("search")) {
					recipeView.setVisible(false);
					search();
				} else {
					getRecipe();
				}
			}
		});

		// init current history state
		History.fireCurrentHistoryState();
	}
	private void getRecipe() {
		// get parameter
		long recipeId = -1;
		String parameter = Window.Location.getParameter("recipe");
		try {
			recipeId = Long.parseLong(parameter);
		} catch (Exception e) {
			
		}

		recipeService.getRecipe(recipeId, localeWidget.getCurrentLanguage(), new AsyncCallback<RecipeDto>() {
			@Override
			public void onSuccess(RecipeDto recipe) {
				recipeView.displayRecipe(recipe);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	private void search() {
		String searchParam = Window.Location.getParameter("searchFor");
		if (searchParam != null) {
			int page;
			try {
				page = Integer.parseInt(Window.Location.getParameter("page"));
			} catch (Exception e) {
				page = 1;
			}
			
			searchService.basicSearch(searchParam, page, PAGE_SIZE, new AsyncCallback<List<String>>() {
				@Override
				public void onSuccess(List<String> results) {
					RootPanel commentsPanel = RootPanel.get("searchWrapper");
					commentsPanel.setVisible(true);
					commentsPanel.clear();
					for (String result : results) {
						commentsPanel.add(new Label(result));
					}
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}

	public static GlobalConstants getConstants() {
		return constants;
	}
	
	public static NumberFormat getNumberFormat() {
		return numberFormat;
	}

	public static GlobalMessages getMessages() {
		return messages;
	}

	public static DateTimeFormat getDateformat() {
		return dateFormat;
	}
}

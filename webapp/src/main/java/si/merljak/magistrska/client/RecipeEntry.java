package si.merljak.magistrska.client;

import java.util.ArrayList;
import java.util.List;

import si.merljak.magistrska.client.i18n.GlobalConstants;
import si.merljak.magistrska.client.i18n.GlobalFormatters;
import si.merljak.magistrska.client.rpc.RecipeService;
import si.merljak.magistrska.client.rpc.RecipeServiceAsync;
import si.merljak.magistrska.client.widgets.IngredientsWidget;
import si.merljak.magistrska.client.widgets.LocaleWidget;
import si.merljak.magistrska.client.widgets.TabsWidget;
import si.merljak.magistrska.common.dto.CommentDto;
import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.TextDto;
import si.merljak.magistrska.common.dto.ToolDto;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class RecipeEntry implements EntryPoint {

	// remote service proxy
	private final RecipeServiceAsync recipeService = GWT.create(RecipeService.class);

	// constants
	private static final GlobalConstants constants = GWT.create(GlobalConstants.class);
	private static final GlobalFormatters formatters = GWT.create(GlobalFormatters.class);

    // formatters
    private static final NumberFormat numberFormat = NumberFormat.getFormat(formatters.numberFormat());
    private static final DateTimeFormat dateFormat = DateTimeFormat.getFormat(formatters.dateFormat());

    private LocaleWidget localeWidget;
    
	public void onModuleLoad() {
		localeWidget = new LocaleWidget();
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
				displayRecipe(recipe);
			}

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	private void displayRecipe(RecipeDto recipe) {
		// titles
		RootPanel.get("recipeTitle").getElement().setInnerHTML(recipe.getTitle());
		RootPanel.get("toolsTitle").getElement().setInnerHTML(constants.tools());
		RootPanel.get("commentsTitle").getElement().setInnerHTML(constants.comments());

		// recipe info
		RootPanel recipeDetailsPanel = RootPanel.get("recipeInfo");
		recipeDetailsPanel.add(new Label(constants.preparationTime() + ": " + recipe.getPreparationTime()));
//		recipeDetailsPanel.add(new Label(constants.numberOfMeals() + ": " + recipe.getNumberOfMeals()));
		recipeDetailsPanel.add(new Label(constants.recipeAuthor() + ": " + recipe.getAuthor()));
		recipeDetailsPanel.add(new Label(constants.difficulty() + ": " + constants.difficultyMap().get(recipe.getDifficulty().name())));

		// ingredients
		RootPanel ingredientsPanel = RootPanel.get("ingredients");
		ingredientsPanel.add(new IngredientsWidget(recipe.getIngredients(), recipe.getNumberOfMeals()));

		// tools
		RootPanel toolsPanel = RootPanel.get("tools");
		for (ToolDto tool : recipe.getTools()) {
			toolsPanel.add(new Label(tool.getQuantity() + " " + tool.getTitle()));
		}
		
		// tabs
		RootPanel.get("tabs").add(new TabsWidget());

		RootPanel tabsPanel = RootPanel.get("basic");
		for (TextDto text : recipe.getTexts()) {
			tabsPanel.add(new HTML(text.getContent() + " (" + constants.languageMap().get(text.getLanguage().name()) + ")"));
			tabsPanel = RootPanel.get("details");
		}

		RootPanel commentsPanel = RootPanel.get("comments");
		List<Widget> tools = new ArrayList<Widget>();
		for (CommentDto comment : recipe.getComments()) {
			tools.add(new Label(dateFormat.format(comment.getDate()) + " - " + comment.getUser() + ": " + comment.getContent()));
			commentsPanel.add(new Label(dateFormat.format(comment.getDate()) + " - " + comment.getUser() + ": " + comment.getContent()));
		}

		commentsPanel.add(localeWidget);
		
	}

	public static GlobalConstants getConstants() {
		return constants;
	}
	
	public static NumberFormat getNumberFormat() {
		return numberFormat;
	}
}

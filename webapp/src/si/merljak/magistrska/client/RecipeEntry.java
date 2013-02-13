package si.merljak.magistrska.client;

import si.merljak.magistrska.client.i18n.GlobalConstants;
import si.merljak.magistrska.client.i18n.GlobalFormatters;
import si.merljak.magistrska.client.rpc.RecipeService;
import si.merljak.magistrska.client.rpc.RecipeServiceAsync;
import si.merljak.magistrska.dto.CommentDto;
import si.merljak.magistrska.dto.IngredientDto;
import si.merljak.magistrska.dto.RecipeDto;
import si.merljak.magistrska.dto.TextDto;
import si.merljak.magistrska.dto.ToolDto;
import si.merljak.magistrska.enumeration.Language;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class RecipeEntry implements EntryPoint {

	// remote service proxy
	private final RecipeServiceAsync recipeService = GWT.create(RecipeService.class);

	// constants
	private GlobalConstants constants = GWT.create(GlobalConstants.class);
	private GlobalFormatters formatters = GWT.create(GlobalFormatters.class);

    // formatters
    private NumberFormat numberFormat = NumberFormat.getFormat(formatters.numberFormat());
    private DateTimeFormat dateFormat = DateTimeFormat.getFormat(formatters.dateFormat());

	public void onModuleLoad() {
		recipeService.getRecipe(0, Language.sl_SI, new AsyncCallback<RecipeDto>() {
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
		RootPanel.get("ingredientsTitle").getElement().setInnerHTML(constants.ingredients());
		RootPanel.get("toolsTitle").getElement().setInnerHTML(constants.tools());
		RootPanel.get("commentsTitle").getElement().setInnerHTML(constants.comments());

		// recipe info
		RootPanel recipeDetailsPanel = RootPanel.get("recipeInfo");
		recipeDetailsPanel.add(new Label(constants.preparationTime() + ": " + recipe.getPreparationTime()));
		recipeDetailsPanel.add(new Label(constants.numberOfMeals() + ": " + recipe.getNumberOfMeals()));
		recipeDetailsPanel.add(new Label(constants.recipeAuthor() + ": " + recipe.getAuthor()));
		recipeDetailsPanel.add(new Label(constants.difficulty() + ": " + constants.difficultyMap().get(recipe.getDifficulty().name())));

		// ingredients
		RootPanel ingredientsPanel = RootPanel.get("ingredients");
		for (IngredientDto ingredient : recipe.getIngredients()) {
			ingredientsPanel.add(new Label(numberFormat.format(ingredient.getAmount()) + " " + constants.unitMap().get(ingredient.getUnit().name()) + " " + ingredient.getName()));
		}

		// tools
		RootPanel toolsPanel = RootPanel.get("tools");
		for (ToolDto tool : recipe.getTools()) {
			toolsPanel.add(new Label(tool.getQuantity() + " " + tool.getTitle()));
		}

		// tabs
		RootPanel.get("tabBasic").getElement().setInnerHTML(constants.tabBasic());
		RootPanel.get("tabDetails").getElement().setInnerHTML(constants.tabDetails());
		RootPanel.get("tabVideo").getElement().setInnerHTML(constants.tabVideo());
		RootPanel.get("tabAudio").getElement().setInnerHTML(constants.tabAudio());

		RootPanel tabsPanel = RootPanel.get("textBasic");
		tabsPanel.add(new Label("Besedila:"));
		for (TextDto text : recipe.getTexts()) {
			tabsPanel.add(new Label(text.getContent() + " (" + constants.languageMap().get(text.getLanguage().name()) + ")"));
		}

		RootPanel commentsPanel = RootPanel.get("comments");
		for (CommentDto comment : recipe.getComments()) {
			commentsPanel.add(new Label(dateFormat.format(comment.getDate()) + " - " + comment.getUser() + ": " + comment.getContent()));
		}
	}
}

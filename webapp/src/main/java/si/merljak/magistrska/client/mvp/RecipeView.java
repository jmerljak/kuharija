package si.merljak.magistrska.client.mvp;

import si.merljak.magistrska.client.KuharijaEntry;
import si.merljak.magistrska.client.i18n.GlobalConstants;
import si.merljak.magistrska.client.widgets.AudioWidget;
import si.merljak.magistrska.client.widgets.IngredientsWidget;
import si.merljak.magistrska.client.widgets.VideoWidget;
import si.merljak.magistrska.common.dto.AudioDto;
import si.merljak.magistrska.common.dto.CommentDto;
import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.TextDto;
import si.merljak.magistrska.common.dto.ToolDto;
import si.merljak.magistrska.common.dto.VideoDto;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class RecipeView extends Composite {
	private static final GlobalConstants constants = KuharijaEntry.getConstants();
	private static final DateTimeFormat dateFormat = KuharijaEntry.getDateformat();

	private RootPanel title;
	private RootPanel titleTools;
	private RootPanel titleComments;
	private RootPanel recipeDetailsPanel;

	public RecipeView () {
		title = RootPanel.get("recipeTitle");
		titleTools = RootPanel.get("toolsTitle");
		titleComments = RootPanel.get("commentsTitle");
		recipeDetailsPanel = RootPanel.get("recipeInfo");

		initWidget(RootPanel.get("recipeWrapper"));
	}

	public void displayRecipe(RecipeDto recipe) {
		if (recipe == null) {
			// TODO handle it
			return;
		}

		// titles
		title.getElement().setInnerHTML(recipe.getTitle());
		titleTools.getElement().setInnerHTML(constants.tools());
		titleComments.getElement().setInnerHTML(constants.comments());

		// recipe info
		recipeDetailsPanel.clear();
		recipeDetailsPanel.add(new Label(constants.preparationTime() + ": " + recipe.getPreparationTime()));
//		recipeDetailsPanel.add(new Label(constants.numberOfMeals() + ": " + recipe.getNumberOfMeals()));
		recipeDetailsPanel.add(new Label(constants.recipeAuthor() + ": " + recipe.getAuthor()));
		recipeDetailsPanel.add(new Label(constants.difficulty() + ": " + constants.difficultyMap().get(recipe.getDifficulty().name())));

		// ingredients
		RootPanel ingredientsPanel = RootPanel.get("ingredients");
		ingredientsPanel.clear();
		ingredientsPanel.add(new IngredientsWidget(recipe.getIngredients(), recipe.getNumberOfMeals()));

		// tools
		RootPanel toolsPanel = RootPanel.get("tools");
		toolsPanel.clear();
		for (ToolDto tool : recipe.getTools()) {
			toolsPanel.add(new Label(tool.getQuantity() + "x " + constants.toolsMap().get(tool.getTitle())));
		}

		// texts
		RootPanel textPanel = RootPanel.get("basic");
		textPanel.clear();
		for (TextDto text : recipe.getTexts()) {
			textPanel.add(new HTML(text.getContent() + " (" + constants.languageMap().get(text.getLanguage().name()) + ")"));
			textPanel = RootPanel.get("details");
		}

		RootPanel audioPanel = RootPanel.get("audio");
		audioPanel.clear();
		for (AudioDto audioDto : recipe.getAudios()) {
			audioPanel.add(new AudioWidget(audioDto));
		}

		RootPanel videoPanel = RootPanel.get("video");
		videoPanel.clear();
		for (VideoDto videoDto : recipe.getVideos()) {
			videoPanel.add(new VideoWidget(videoDto));
		}

		RootPanel commentsPanel = RootPanel.get("comments");
		commentsPanel.clear();
		for (CommentDto comment : recipe.getComments()) {
			commentsPanel.add(new Label(dateFormat.format(comment.getDate()) + " - " + comment.getUser() + ": " + comment.getContent()));
		}

		setVisible(true);
	}
}

package si.merljak.magistrska.client.mvp;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.UtensilsConstants;
import si.merljak.magistrska.client.widgets.AppendixWidget;
import si.merljak.magistrska.client.widgets.AudioWidget;
import si.merljak.magistrska.client.widgets.CommentWidget;
import si.merljak.magistrska.client.widgets.IngredientsWidget;
import si.merljak.magistrska.client.widgets.TabsWidget;
import si.merljak.magistrska.client.widgets.VideoWidget;
import si.merljak.magistrska.common.dto.AppendixDto;
import si.merljak.magistrska.common.dto.AudioDto;
import si.merljak.magistrska.common.dto.CommentDto;
import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.dto.StepDto;
import si.merljak.magistrska.common.dto.TextDto;
import si.merljak.magistrska.common.dto.UtensilDto;
import si.merljak.magistrska.common.dto.VideoDto;

import com.google.gwt.aria.client.Roles;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class RecipeView extends AbstractView {

	// constants & formatters
	private UtensilsConstants utensilsConstants = Kuharija.utensilsConstants;

	// panels
	private static final RootPanel title = RootPanel.get("recipeTitle");
	private static final RootPanel titleTools = RootPanel.get("toolsTitle");
	private static final RootPanel titleComments = RootPanel.get("commentsTitle");
	private static final RootPanel recipeDetailsPanel = RootPanel.get("recipeInfo");
	private static final RootPanel toolsPanel = RootPanel.get("tools");
	private static final RootPanel ingredientsPanel = RootPanel.get("ingredients");
	private static final RootPanel panelBasic = RootPanel.get("basic");
	private static final RootPanel panelSteps = RootPanel.get("steps");
	private static final RootPanel panelAudio = RootPanel.get("audio");
	private static final RootPanel panelVideo = RootPanel.get("video");
	private static final RootPanel commentsPanel = RootPanel.get("comments");

	// widgets
	private TabsWidget tabsWidget = new TabsWidget();

	public RecipeView () {
		initWidget(RootPanel.get("recipeWrapper"));
		Roles.getMainRole().set(getElement());
		
		// tabs
		RootPanel.get("tabs").add(tabsWidget);
	}

	public void clearAll() {
		recipeDetailsPanel.clear();
		ingredientsPanel.clear();
		toolsPanel.clear();
		panelBasic.clear();
		panelSteps.clear();
		panelAudio.clear();
		panelVideo.clear();
		commentsPanel.clear();
	}

	public void displayRecipe(RecipeDetailsDto recipe) {
		clearAll();
		if (recipe == null) {
			// TODO handle it
			return;
		}

		// titles
		title.getElement().setInnerHTML(recipe.getHeading());
		titleTools.getElement().setInnerHTML(constants.tools());
		titleComments.getElement().setInnerHTML(constants.comments());

		// recipe info
		recipeDetailsPanel.add(new Label(constants.timePreparation() + ": " + timeFromMinutes(recipe.getTimePreparation())));
		recipeDetailsPanel.add(new Label(constants.timeCooking() + ": " + timeFromMinutes(recipe.getTimeCooking())));
		recipeDetailsPanel.add(new Label(constants.timeOverall() + ": " + timeFromMinutes(recipe.getTimeOverall())));
//		recipeDetailsPanel.add(new Label(constants.numberOfMeals() + ": " + recipe.getNumberOfMeals()));
		recipeDetailsPanel.add(new Label(constants.recipeAuthor() + ": " + recipe.getAuthor()));
		recipeDetailsPanel.add(new Label(constants.difficulty() + ": " + constants.difficultyMap().get(recipe.getDifficulty().name())));
		recipeDetailsPanel.add(new Image(RECIPE_IMG_FOLDER + recipe.getImageUrl()));

		// ingredients
		ingredientsPanel.add(new IngredientsWidget(recipe.getIngredients(), recipe.getNumberOfMeals()));

		// utensils
		for (UtensilDto tool : recipe.getUtensils()) {
			toolsPanel.add(new Label(tool.getQuantity() + "x " + utensilsConstants.utensilsMap().get(tool.getName()).toLowerCase()));
		}

		// texts
		for (TextDto text : recipe.getTexts()) {
			panelBasic.add(new HTML(text.getContent()));
		}

		for (AudioDto audioDto : recipe.getAudios()) {
			panelAudio.add(new AudioWidget(audioDto));
		}

		for (VideoDto videoDto : recipe.getVideos()) {
			panelVideo.add(new VideoWidget(videoDto));
		}

		for (CommentDto comment : recipe.getComments()) {
			commentsPanel.add(new CommentWidget(comment));
		}
		
		for (AppendixDto appendix : recipe.getAppendencies()) {
			commentsPanel.add(new AppendixWidget(appendix));
		}

		setVisible(true);
	}

	public void displayStep(StepDto step) {
		if (step == null) {
			// TODO handle it
			return;
		}

		final int page = step.getPage();

		Button btnPrevious = new Button("← previous");
		btnPrevious.setStyleName("btn");
		btnPrevious.setEnabled(page > 1);
		btnPrevious.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				UrlBuilder builder = Location.createUrlBuilder().setParameter("page", Integer.toString(page - 1));
				Window.Location.replace(builder.buildString());
			}
		});

		Button btnNext = new Button("next →");
		btnNext.setStyleName("btn");
		btnNext.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				UrlBuilder builder = Location.createUrlBuilder().setParameter("page", Integer.toString(page + 1));
				Window.Location.replace(builder.buildString());
			}
		});

		panelSteps.clear();
		panelSteps.add(new Label("step " + page + ": " + step.getContent()));
		panelSteps.add(btnPrevious);
		panelSteps.add(btnNext);

		setVisible(true);
	}
}

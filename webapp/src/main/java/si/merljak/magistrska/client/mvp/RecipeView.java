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

import com.github.gwtbootstrap.client.ui.Heading;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;

public class RecipeView extends AbstractView {

	// i18n
	private UtensilsConstants utensilsConstants = Kuharija.utensilsConstants;

	// widgets
	private TabsWidget tabsWidget = new TabsWidget();
	private Heading heading = new Heading(HEADING_SIZE);
	private FlowPanel recipeDetailsPanel= new FlowPanel();
	private FlowPanel ingredientsPanel= new FlowPanel();
	private FlowPanel toolsPanel= new FlowPanel();
	private FlowPanel panelBasic= new FlowPanel();
	private FlowPanel panelSteps= new FlowPanel();
	private FlowPanel panelAudio= new FlowPanel();
	private FlowPanel panelVideo= new FlowPanel();
	private FlowPanel commentsPanel= new FlowPanel();

	public RecipeView () {
		FlowPanel side = new FlowPanel();
		side.setStyleName("span3");
		side.add(ingredientsPanel);
		side.add(toolsPanel);

		FlowPanel center = new FlowPanel();
		side.setStyleName("span9");
		side.add(recipeDetailsPanel);
		side.add(tabsWidget);
		side.add(panelBasic);
		side.add(panelSteps);
		side.add(panelAudio);
		side.add(panelVideo);
		side.add(commentsPanel);

		FlowPanel main = new FlowPanel();
		main.setStyleName("row-fluid");
		main.add(heading);
		main.add(side);
		main.add(center);
		initWidget(main);
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
		heading.setText(recipe.getHeading());
		Kuharija.setWindowTitle(recipe.getHeading());

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
		toolsPanel.clear();
		toolsPanel.add(new Heading(HEADING_SIZE + 1, constants.tools()));
		for (UtensilDto utensil : recipe.getUtensils()) {
			String name = utensil.getName();
			toolsPanel.add(new Anchor(utensil.getQuantity() + "x " + utensilsConstants.utensilsMap().get(name).toLowerCase(), UtensilPresenter.buildUtensilUrl(name)));
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

		commentsPanel.clear();
		commentsPanel.add(new Heading(HEADING_SIZE + 1, constants.comments()));
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

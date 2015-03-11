package si.merljak.magistrska.client.mvp.recipe;

import java.util.List;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.mvp.AbstractView;
import si.merljak.magistrska.client.utils.Badges;
import si.merljak.magistrska.client.widgets.AppendixWidget;
import si.merljak.magistrska.client.widgets.CommentWidget;
import si.merljak.magistrska.client.widgets.IngredientsWidget;
import si.merljak.magistrska.client.widgets.TabsWidget;
import si.merljak.magistrska.client.widgets.UtensilsWidget;
import si.merljak.magistrska.common.dto.AppendixDto;
import si.merljak.magistrska.common.dto.CommentDto;
import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Season;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Column;
import com.github.gwtbootstrap.client.ui.Emphasis;
import com.github.gwtbootstrap.client.ui.FluidRow;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Icon;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.base.InlineLabel;
import com.github.gwtbootstrap.client.ui.constants.IconPosition;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.client.ui.constants.ImageType;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * View for recipe details and cooking instructions.
 * 
 * @author Jakob Merljak
 * 
 */
public class RecipeView extends AbstractView {

	// widgets
	private final Heading heading = new Heading(HEADING_SIZE);
	private final Emphasis subHeading = new Emphasis();
	private final Paragraph notFoundMessage = new Paragraph(messages.recipeNotFoundTry());

	private final TabsWidget tabsWidget = new TabsWidget();
	private final UtensilsWidget utensilsWidget = new UtensilsWidget();
	private final IngredientsWidget ingredientsWidget = new IngredientsWidget();

	private final FlowPanel commentsPanel = new FlowPanel();
	private final FlowPanel appendixPanel = new FlowPanel();
	private final SimplePanel appendixHolder = new SimplePanel();
	private final FlowPanel appendixListPanel = new FlowPanel();
	// TODO should be real button (not bootstrap's "button" = link), using it for convenience
	private final Button appendixToggle = new Button(constants.appendicesShow());

	// grid
	private final FluidRow fluidTop = new FluidRow();
	private final FluidRow fluidMain = new FluidRow();
	private final Column columnImg = new Column(9);
	private final Column columnInfo = new Column(3);
	private final Column columnSide = new Column(3);
	private final Column columnMain = new Column(9);

	public RecipeView() {
		// fluid top
		columnImg.addStyleName(ImageType.ROUNDED.get());
		columnImg.getElement().setId("columnImg");
		columnInfo.getElement().setId("columnInfo");

		fluidTop.add(columnImg);
		fluidTop.add(columnInfo);

		// fluid main
		columnSide.add(ingredientsWidget);
		columnSide.add(utensilsWidget);

		columnMain.add(new Heading(HEADING_SIZE + 1, constants.recipeProcedure()));
		columnMain.add(tabsWidget);
		columnMain.add(appendixPanel);
		columnMain.add(commentsPanel);

		fluidMain.add(columnSide);
		fluidMain.add(columnMain);

		// styles
		notFoundMessage.setVisible(false);

		// appendix toggle
		appendixPanel.add(new Heading(HEADING_SIZE + 1, constants.appendices()));
		appendixPanel.add(appendixHolder);

		appendixToggle.setIcon(IconType.CHEVRON_RIGHT);
		appendixToggle.setIconPosition(IconPosition.RIGHT);
		appendixToggle.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				appendixHolder.setWidget(appendixListPanel);
			}
		});

		// layout
		FlowPanel main = new FlowPanel();
		main.setStyleName("recipeView");
		main.add(heading);
		main.add(subHeading);
		main.add(fluidTop);
		main.add(fluidMain);
		main.add(notFoundMessage);
		initWidget(main);
	}

	public void displayRecipe(RecipeDetailsDto recipe, String view) {
		clearAll();
		if (recipe == null) {
			heading.setText(messages.oops());
			notFoundMessage.setVisible(true);
			Kuharija.setWindowTitle(null);
			return;
		}

		// titles
		String recipeHeading = recipe.getHeading();
		heading.setText(recipeHeading);
		Kuharija.setWindowTitle(recipeHeading);

		String recipeSubHeading = recipe.getSubHeading();
		if (recipeSubHeading != null) {
			subHeading.setText(recipeSubHeading);
		}

		// recipe info
		String imageUrl = recipe.getImageUrl();
		columnImg.setStyleName("image", imageUrl != null);
		columnImg.setStyleName("empty", imageUrl == null);
		if (imageUrl != null) {
			columnImg.getElement().setAttribute("style", "background-image: url('" + RECIPE_IMG_FOLDER + imageUrl + "')");
		} else {
			columnImg.getElement().removeAttribute("style");
		}
		
		Label timePreparation = new Label(" " + constants.timePreparation() + ": " + timeFromMinutes(recipe.getTimePreparation()));
		timePreparation.getElement().insertFirst(new Icon(IconType.TIME).getElement());
		
		Label timeCooking = new Label(" " + constants.timeCooking() + ": " + timeFromMinutes(recipe.getTimeCooking()));
		timeCooking.getElement().insertFirst(new Icon(IconType.TIME).getElement());
		
		Label timeOverall = new Label(" " + constants.timeOverall() + ": " + timeFromMinutes(recipe.getTimeOverall()));
		timeOverall.getElement().insertFirst(new Icon(IconType.TIME).getElement());

		// review stars
		int stars;
		String reviewText = "";
		if (recipe.getReviewStars() == null) {
			stars = 0;
			reviewText = messages.recipeNoReview();
		} else {
			stars = recipe.getReviewStars().intValue();
			reviewText = messages.recipeReviewOutOfFive(stars);
		}

		InlineLabel reviewLabel = new InlineLabel();
		reviewLabel.setStyleName(Kuharija.CSS_VISUALLY_HIDDEN);
		reviewLabel.setText(reviewText);

		FlowPanel review = new FlowPanel();
		review.setTitle(reviewText);
		review.add(reviewLabel);
		for (int i=0; i < 5; i++) {
			if (i < stars) {
				review.add(new Icon(IconType.STAR));
			} else {
				review.add(new Icon(IconType.STAR_EMPTY));
			}
		}

		columnInfo.add(review);
		columnInfo.add(timePreparation);
		columnInfo.add(timeCooking);
		columnInfo.add(timeOverall);
		columnInfo.add(new Label(constants.recipeAuthor() + ": " + recipe.getAuthor()));
		columnInfo.add(new Label(constants.difficulty() + ": " + constants.difficultyMap().get(recipe.getDifficulty().name())));

		// categories & seasons
		Label categoriesLabel = new Label(constants.categories());
		categoriesLabel.setStyleName(Kuharija.CSS_VISUALLY_HIDDEN);
		columnInfo.add(categoriesLabel);
		for (Category category : recipe.getCategories()) {
			columnInfo.add(Badges.getCategoryBadge(category));
		}

		Label seasonsLabel = new Label(constants.seasons());
		seasonsLabel.setStyleName(Kuharija.CSS_VISUALLY_HIDDEN);
		columnInfo.add(seasonsLabel);
		for (Season season : recipe.getSeasons()) {
			columnInfo.add(Badges.getSeasonBadge(season));
		}

		// ingredients
		ingredientsWidget.setIngredients(recipe.getIngredients(), recipe.getNumberOfMeals(), recipe.getMealUnit(), true);

		// utensils
		utensilsWidget.setUtensils(recipe.getUtensils());

		// appendices
		List<AppendixDto> appendices = recipe.getAppendices();
		if (!appendices.isEmpty()) {
			appendixHolder.setWidget(appendixToggle);
			appendixPanel.setVisible(true);
			for (AppendixDto appendix : appendices) {
				appendixListPanel.add(new AppendixWidget(appendix));
			}
		}

		// comments
		List<CommentDto> comments = recipe.getComments();
		commentsPanel.add(new Heading(HEADING_SIZE + 1, constants.comments()));
		if (comments.isEmpty()) {
			commentsPanel.add(new Paragraph(constants.noComments()));
		}
		for (CommentDto comment : comments) {
			commentsPanel.add(new CommentWidget(comment));
		}

		// procedure text, audio, video
		tabsWidget.displayRecipe(recipe, view);

		// show
		fluidTop.setVisible(true);
		fluidMain.setVisible(true);
	}

	private void clearAll() {
		subHeading.setText("");
		notFoundMessage.setVisible(false);
		columnInfo.clear();
		columnImg.clear();
		tabsWidget.clearAll();
		appendixPanel.setVisible(false);
		appendixListPanel.clear();
		commentsPanel.clear();
		fluidTop.setVisible(false);
		fluidMain.setVisible(false);
	}

	public void performActions(List<String> actions) {
		tabsWidget.performActions(actions);
	}
}

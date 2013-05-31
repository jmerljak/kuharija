package si.merljak.magistrska.client.mvp.recipe;

import java.util.List;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.mvp.AbstractView;
import si.merljak.magistrska.client.mvp.search.SearchPresenter;
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

import com.github.gwtbootstrap.client.ui.Badge;
import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Emphasis;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Icon;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.github.gwtbootstrap.client.ui.constants.IconPosition;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.client.ui.constants.ImageType;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
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

	private final FlowPanel imgPanel = new FlowPanel();
	private final FlowPanel infoPanel = new FlowPanel();
	private final TabsWidget tabsWidget = new TabsWidget();
	private final UtensilsWidget utensilsWidget = new UtensilsWidget();
	private final IngredientsWidget ingredientsWidget = new IngredientsWidget();

	private final FlowPanel appendixPanel = new FlowPanel();
	private final SimplePanel appendixHolder = new SimplePanel();
	private final Button appendixToggle = new Button(constants.appendicesShow());
	private final FlowPanel appendixListPanel = new FlowPanel();

	private final FlowPanel commentsPanel = new FlowPanel();

	private final FlowPanel center = new FlowPanel();
	private final FlowPanel fluid1 = new FlowPanel();
	private final FlowPanel fluid2 = new FlowPanel();

	public RecipeView() {
		// fluid 1
		imgPanel.getElement().setId("imgPanel");
		imgPanel.setStyleName(Constants.SPAN + 9);
		imgPanel.addStyleName(ImageType.ROUNDED.get());
		infoPanel.setStyleName(Constants.SPAN + 3);
		infoPanel.getElement().setId("infoPanel");

		fluid1.setStyleName(Constants.ROW_FLUID);
		fluid1.add(imgPanel);
		fluid1.add(infoPanel);

		// fluid 2
		FlowPanel side = new FlowPanel();
		side.setStyleName(Constants.SPAN + 3);
		side.add(ingredientsWidget);
		side.add(utensilsWidget);

		center.setStyleName(Constants.SPAN + 9);
		center.add(new Heading(HEADING_SIZE + 1, constants.recipeProcedure()));
		center.add(tabsWidget);
		center.add(appendixPanel);
		center.add(commentsPanel);

		fluid2.setStyleName(Constants.ROW_FLUID);
		fluid2.add(side);
		fluid2.add(center);

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
		main.add(notFoundMessage);
		main.add(fluid1);
		main.add(fluid2);
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
		if (imageUrl != null) {
//			Image img = new Image(RECIPE_IMG_FOLDER + imageUrl);
//			img.setAltText(recipeHeading);
//			img.setType(ImageType.ROUNDED);
//			imgPanel.add(img);
			imgPanel.getElement().setAttribute("style", "background-image: url('" + RECIPE_IMG_FOLDER + imageUrl + "')");
		} else {
			imgPanel.getElement().removeAttribute("style");
		}
		
		Label timePreparation = new Label(" " + constants.timePreparation() + ": " + timeFromMinutes(recipe.getTimePreparation()));
		timePreparation.getElement().insertFirst(new Icon(IconType.TIME).getElement());
		
		Label timeCooking = new Label(" " + constants.timeCooking() + ": " + timeFromMinutes(recipe.getTimeCooking()));
		timeCooking.getElement().insertFirst(new Icon(IconType.TIME).getElement());
		
		Label timeOverall = new Label(" " + constants.timeOverall() + ": " + timeFromMinutes(recipe.getTimeOverall()));
		timeOverall.getElement().insertFirst(new Icon(IconType.TIME).getElement());

		infoPanel.add(timePreparation);
		infoPanel.add(timeCooking);
		infoPanel.add(timeOverall);
		infoPanel.add(new Label(constants.recipeAuthor() + ": " + recipe.getAuthor()));
		infoPanel.add(new Label(constants.difficulty() + ": " + constants.difficultyMap().get(recipe.getDifficulty().name())));

		// categories & seasons
		Label categoriesLabel = new Label(constants.categories());
		categoriesLabel.setStyleName(Kuharija.CSS_VISUALLY_HIDDEN);
		infoPanel.add(categoriesLabel);
		for (Category category : recipe.getCategories()) {
			Badge categoryBadge = new Badge(localizeEnum(category));
			categoryBadge.setStylePrimaryName("category");
			categoryBadge.addStyleDependentName(category.name().toLowerCase());
			categoryBadge.setWordWrap(false);
			Anchor categoryAnchor = new Anchor("", SearchPresenter.buildSearchByCategoryUrl(category));
			categoryAnchor.getElement().appendChild(categoryBadge.getElement());
			infoPanel.add(categoryAnchor);
		}

		Label seasonsLabel = new Label(constants.seasons());
		seasonsLabel.setStyleName(Kuharija.CSS_VISUALLY_HIDDEN);
		infoPanel.add(seasonsLabel);
		for (Season season : recipe.getSeasons()) {
			Badge seasonBadge = new Badge(localizeEnum(season));
			seasonBadge.setStylePrimaryName("season");
			seasonBadge.addStyleDependentName(season.name().toLowerCase());
			seasonBadge.setWordWrap(false);
			Anchor seasonAnchor = new Anchor("", SearchPresenter.buildSearchBySeasonUrl(season));
			seasonAnchor.getElement().appendChild(seasonBadge.getElement());
			infoPanel.add(seasonAnchor);
		}

		// ingredients
		ingredientsWidget.setIngredients(recipe.getIngredients(), recipe.getNumberOfMeals(), recipe.getMealUnit(), true);

		// utensils
		utensilsWidget.update(recipe.getUtensils());

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
		fluid1.setVisible(true);
		fluid2.setVisible(true);
	}

	private void clearAll() {
		subHeading.setText("");
		notFoundMessage.setVisible(false);
		infoPanel.clear();
		imgPanel.clear();
		tabsWidget.clearAll();
		appendixPanel.setVisible(false);
		appendixListPanel.clear();
		commentsPanel.clear();
		fluid1.setVisible(false);
		fluid2.setVisible(false);
	}

	public void performActions(List<String> actions) {
		tabsWidget.performActions(actions);
	}
}

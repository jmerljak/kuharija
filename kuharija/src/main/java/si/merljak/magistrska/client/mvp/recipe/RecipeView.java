package si.merljak.magistrska.client.mvp.recipe;

import java.util.List;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.mvp.AbstractView;
import si.merljak.magistrska.client.mvp.search.SearchPresenter;
import si.merljak.magistrska.client.widgets.AppendixWidget;
import si.merljak.magistrska.client.widgets.AudioWidget;
import si.merljak.magistrska.client.widgets.CommentWidget;
import si.merljak.magistrska.client.widgets.IngredientsWidget;
import si.merljak.magistrska.client.widgets.TabsWidget;
import si.merljak.magistrska.client.widgets.TabsWidget.TabChangeHandler;
import si.merljak.magistrska.client.widgets.UtensilsWidget;
import si.merljak.magistrska.client.widgets.VideoWidget;
import si.merljak.magistrska.common.dto.AppendixDto;
import si.merljak.magistrska.common.dto.AudioDto;
import si.merljak.magistrska.common.dto.CommentDto;
import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.dto.StepDto;
import si.merljak.magistrska.common.dto.TextDto;
import si.merljak.magistrska.common.dto.VideoDto;
import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Season;

import com.github.gwtbootstrap.client.ui.Badge;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.base.IconAnchor;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.github.gwtbootstrap.client.ui.constants.IconSize;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * View for recipe details and cooking instructions.
 * 
 * @author Jakob Merljak
 * 
 */
public class RecipeView extends AbstractView implements TabChangeHandler {

	// widgets
	private final Heading heading = new Heading(HEADING_SIZE);
	private final TabsWidget tabsWidget = new TabsWidget(this);
	private final UtensilsWidget utensilsWidget = new UtensilsWidget();
	private final IngredientsWidget ingredientsWidget = new IngredientsWidget();

	private Paragraph notFoundMessage = new Paragraph(messages.recipeNotFoundTry());
	private FlowPanel recipeDetailsPanel = new FlowPanel();
	private SimplePanel mainPanel = new SimplePanel();
	private FlowPanel panelBasic = new FlowPanel();
	private FlowPanel panelSteps = new FlowPanel();
	private FlowPanel panelAudio = new FlowPanel();
	private FlowPanel panelVideo = new FlowPanel();
	private FlowPanel commentsPanel = new FlowPanel();
	private IconAnchor bookmark = new IconAnchor();

	public RecipeView() {
		FlowPanel side = new FlowPanel();
		side.setStyleName(Constants.SPAN + 3);
		side.add(ingredientsWidget);
		side.add(utensilsWidget);

		FlowPanel center = new FlowPanel();
		center.setStyleName(Constants.SPAN + 9);
		center.add(tabsWidget);
		center.add(mainPanel);
		center.add(commentsPanel);

		FlowPanel fluid = new FlowPanel();
		fluid.setStyleName(Constants.ROW_FLUID);
		fluid.add(side);
		fluid.add(center);

		notFoundMessage.setVisible(false);

		FlowPanel main = new FlowPanel();
		main.add(heading);
		main.add(notFoundMessage);
		main.add(recipeDetailsPanel);
		main.add(fluid);
		initWidget(main);
	}

	public void clearAll() {
		recipeDetailsPanel.clear();
		panelBasic.clear();
		panelSteps.clear();
		panelAudio.clear();
		panelVideo.clear();
		commentsPanel.clear();
	}

	public void displayRecipe(RecipeDetailsDto recipe, String view) {
		clearAll();
		notFoundMessage.setVisible(recipe == null);
		if (recipe == null) {
			heading.setText(messages.oops());
			Kuharija.setWindowTitle(null);
			// TODO handle it
			return;
		}

		// TODO display user preferred / default view
		setView(view);

		// titles
		heading.setText(recipe.getHeading());
		Kuharija.setWindowTitle(recipe.getHeading());

		// recipe info
		recipeDetailsPanel.add(new Label(recipe.getSubHeading()));
		String imageUrl = recipe.getImageUrl();
		if (imageUrl != null) {
			recipeDetailsPanel.add(new Image(RECIPE_IMG_FOLDER + imageUrl));
		}
		recipeDetailsPanel.add(new InlineLabel(constants.timePreparation() + ": " + timeFromMinutes(recipe.getTimePreparation())));
		recipeDetailsPanel.add(new InlineLabel(constants.timeCooking() + ": " + timeFromMinutes(recipe.getTimeCooking())));
		recipeDetailsPanel.add(new InlineLabel(constants.timeOverall() + ": " + timeFromMinutes(recipe.getTimeOverall())));
//		recipeDetailsPanel.add(new InlineLabel(constants.numberOfMeals() + ": " + recipe.getNumberOfMeals()));
		recipeDetailsPanel.add(new InlineLabel(constants.recipeAuthor() + ": " + recipe.getAuthor()));
		recipeDetailsPanel.add(new InlineLabel(constants.difficulty() + ": " + constants.difficultyMap().get(recipe.getDifficulty().name())));

		final boolean isBookmarked = recipe.isBookmarked();
		bookmark.setIcon(isBookmarked ? IconType.BOOKMARK : IconType.BOOKMARK_EMPTY);
		bookmark.setIconSize(IconSize.LARGE);
		bookmark.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// TODO link presenter
				// TODO get bookmarked or not
//				presenter.bookmark(!isBookmarked);
			}
		});
//		recipeDetailsPanel.add(bookmark);

		// categories & seasons
		for (Category category : recipe.getCategories()) {
			Badge categoryBadge = new Badge(localizeEnum(category));
			categoryBadge.setStylePrimaryName("category");
			categoryBadge.addStyleDependentName(category.name().toLowerCase());
			categoryBadge.setWordWrap(false);
			Anchor categoryAnchor = new Anchor("", SearchPresenter.buildSearchByCategoryUrl(category));
			categoryAnchor.getElement().appendChild(categoryBadge.getElement());
			recipeDetailsPanel.add(categoryAnchor);
		}

		for (Season season : recipe.getSeasons()) {
			Badge seasonBadge = new Badge(localizeEnum(season));
			seasonBadge.setStylePrimaryName("season");
			seasonBadge.addStyleDependentName(season.name().toLowerCase());
			seasonBadge.setWordWrap(false);
			Anchor seasonAnchor = new Anchor("", SearchPresenter.buildSearchBySeasonUrl(season));
			seasonAnchor.getElement().appendChild(seasonBadge.getElement());
			recipeDetailsPanel.add(seasonAnchor);
		}

		// ingredients
		ingredientsWidget.setIngredients(recipe.getIngredients(), recipe.getNumberOfMeals(), recipe.getMealUnit(), true);

		// utensils
		utensilsWidget.update(recipe.getUtensils());

		// texts
		for (TextDto text : recipe.getTexts()) {
			panelBasic.add(new HTML(text.getContent()));
		}
		for (StepDto step : recipe.getSteps()) {
			panelSteps.add(new HTML(step.getContent()));
		}

		List<AudioDto> audios = recipe.getAudios();
		if (audios.isEmpty()) {
			panelAudio.add(new HTML(messages.recipeNoAudio()));
		} else if (!Audio.isSupported()) {
			panelAudio.add(new HTML(messages.htmlAudioNotSupported()));
		}
		for (AudioDto audioDto : audios) {
			panelAudio.add(new AudioWidget(audioDto));
		}

		List<VideoDto> videos = recipe.getVideos();
		if (audios.isEmpty()) {
			panelVideo.add(new HTML(messages.recipeNoVideo()));
		} else if (!Video.isSupported()) {
			panelVideo.add(new HTML(messages.htmlVideoNotSupported()));
		}
		for (VideoDto videoDto : videos) {
			panelVideo.add(new VideoWidget(videoDto));
		}

		commentsPanel.add(new Heading(HEADING_SIZE + 1, constants.comments()));
		for (CommentDto comment : recipe.getComments()) {
			commentsPanel.add(new CommentWidget(comment));
		}

		for (AppendixDto appendix : recipe.getAppendencies()) {
			commentsPanel.add(new AppendixWidget(appendix));
		}

		setVisible(true);
	}

	public void setView(String view) {
		if (view == null || view.equalsIgnoreCase("basic")) {
			tabsWidget.setActiveTab(TabsWidget.TAB_BASIC);
			onTabChange(TabsWidget.TAB_BASIC);
		} else if (view.equalsIgnoreCase("steps")) {
			tabsWidget.setActiveTab(TabsWidget.TAB_STEPS);
			onTabChange(TabsWidget.TAB_STEPS);
		} else if (view.equalsIgnoreCase("video")) {
			tabsWidget.setActiveTab(TabsWidget.TAB_VIDEO);
			onTabChange(TabsWidget.TAB_VIDEO);
		} else if (view.equalsIgnoreCase("audio")) {
			tabsWidget.setActiveTab(TabsWidget.TAB_AUDIO);
			onTabChange(TabsWidget.TAB_AUDIO);
		}
	}

	@Override
	public void onTabChange(int tab) {
		switch (tab) {
		case TabsWidget.TAB_BASIC:
			mainPanel.setWidget(panelBasic);
			break;
		case TabsWidget.TAB_STEPS:
			mainPanel.setWidget(panelSteps);
			break;
		case TabsWidget.TAB_VIDEO:
			mainPanel.setWidget(panelVideo);
			break;
		case TabsWidget.TAB_AUDIO:
			mainPanel.setWidget(panelAudio);
			break;
		}
	}

	public void setBookmarked(boolean isBookmarked) {
		bookmark.setIcon(isBookmarked ? IconType.BOOKMARK : IconType.BOOKMARK_EMPTY);
	}
}

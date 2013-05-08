package si.merljak.magistrska.client.mvp.recipe;

import java.util.List;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.handler.PagingHandler;
import si.merljak.magistrska.client.mvp.AbstractView;
import si.merljak.magistrska.client.mvp.search.SearchPresenter;
import si.merljak.magistrska.client.widgets.AppendixWidget;
import si.merljak.magistrska.client.widgets.AudioWidget;
import si.merljak.magistrska.client.widgets.CommentWidget;
import si.merljak.magistrska.client.widgets.IngredientsWidget;
import si.merljak.magistrska.client.widgets.SimplePagingWidget;
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
import com.github.gwtbootstrap.client.ui.Caption;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.Tab;
import com.github.gwtbootstrap.client.ui.TabPanel;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.client.ui.resources.Bootstrap.Tabs;
import com.google.gwt.aria.client.Roles;
import com.google.gwt.dom.client.Element;
import com.google.gwt.media.client.Audio;
import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
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
//	private final TabsWidget tabsWidget = new TabsWidget(this);
	private final UtensilsWidget utensilsWidget = new UtensilsWidget();
	private final IngredientsWidget ingredientsWidget = new IngredientsWidget();

	private final Paragraph notFoundMessage = new Paragraph(messages.recipeNotFoundTry());
	private final FlowPanel recipeDetailsPanel = new FlowPanel();
//	private final SimplePanel mainPanel = new SimplePanel();
	private final TabPanel tabPanel = new TabPanel(Tabs.ABOVE);
	private final FlowPanel panelBasic = new FlowPanel();
	private final FlowPanel panelSteps = new FlowPanel();
	private final SimplePanel stepPanel = new SimplePanel();
	private final FlowPanel panelAudio = new FlowPanel();
	private final FlowPanel panelVideo = new FlowPanel();
	private final FlowPanel commentsPanel = new FlowPanel();
//	private final IconAnchor bookmark = new IconAnchor();

	private final FlowPanel center = new FlowPanel();
	private final FlowPanel fluid = new FlowPanel();


	public RecipeView() {
		FlowPanel side = new FlowPanel();
		side.setStyleName(Constants.SPAN + 3);
		side.add(ingredientsWidget);
		side.add(utensilsWidget);

		Tab tabBasic = new Tab();
		tabBasic.setActive(true);
		tabBasic.setIcon(IconType.LIST);
		tabBasic.setHeading(constants.tabBasic());
		tabBasic.add(panelBasic);
		
		Tab tabSteps = new Tab();
		tabSteps.setIcon(IconType.LIST_ALT);
		tabSteps.setHeading(constants.tabSteps());
		tabSteps.add(panelSteps);
		
		Tab tabVideo = new Tab();
		tabVideo.setIcon(IconType.FILM);
		tabVideo.setHeading(constants.tabVideo());
		tabVideo.add(panelVideo);
		
		Tab tabAudio = new Tab();
		tabAudio.setIcon(IconType.MUSIC);
		tabAudio.setHeading(constants.tabAudio());
		tabAudio.add(panelAudio);

		tabPanel.add(tabBasic);
		tabPanel.add(tabSteps);
		tabPanel.add(tabVideo);
		tabPanel.add(tabAudio);

		// ARIA roles
		Roles.getTabpanelRole().set(panelBasic.getElement());
		Roles.getTabpanelRole().set(panelSteps.getElement());
		Roles.getTabpanelRole().set(panelVideo.getElement());
		Roles.getTabpanelRole().set(panelAudio.getElement());
		Element tablistElement = tabPanel.getElement().getFirstChildElement();
		Roles.getTablistRole().set(tablistElement);
		for (int i = 0; i < tablistElement.getChildCount(); i++) {
			Roles.getTabRole().set(Element.as(tablistElement.getChild(i)));
		}
		
		center.setStyleName(Constants.SPAN + 9);
		center.add(new Heading(HEADING_SIZE + 1, constants.recipeProcedure()));
		center.add(tabPanel);
//		center.add(mainPanel);
		center.add(commentsPanel);

		fluid.setStyleName(Constants.ROW_FLUID);
		fluid.add(side);
		fluid.add(center);

		notFoundMessage.setVisible(false);
		recipeDetailsPanel.getElement().setId("recipeDetails");

		FlowPanel main = new FlowPanel();
		main.add(heading);
		main.add(notFoundMessage);
		main.add(recipeDetailsPanel);
		main.add(fluid);
		initWidget(main);
	}

	public void clearAll() {
		notFoundMessage.setVisible(false);
		recipeDetailsPanel.clear();
		center.setVisible(false);
		fluid.setVisible(false);
//		mainPanel.clear();
		panelBasic.clear();
		panelSteps.clear();
		panelAudio.clear();
		panelVideo.clear();
		commentsPanel.clear();
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
			recipeDetailsPanel.add(new Caption(recipeSubHeading));
		}

		// recipe info
		String imageUrl = recipe.getImageUrl();
		if (imageUrl != null) {
			recipeDetailsPanel.add(new Image(RECIPE_IMG_FOLDER + imageUrl));
		}
		recipeDetailsPanel.add(new Label(constants.timePreparation() + ": " + timeFromMinutes(recipe.getTimePreparation())));
		recipeDetailsPanel.add(new Label(constants.timeCooking() + ": " + timeFromMinutes(recipe.getTimeCooking())));
		recipeDetailsPanel.add(new Label(constants.timeOverall() + ": " + timeFromMinutes(recipe.getTimeOverall())));
		recipeDetailsPanel.add(new Label(constants.recipeAuthor() + ": " + recipe.getAuthor()));
		recipeDetailsPanel.add(new Label(constants.difficulty() + ": " + constants.difficultyMap().get(recipe.getDifficulty().name())));

//		final boolean isBookmarked = recipe.isBookmarked();
//		bookmark.setIcon(isBookmarked ? IconType.BOOKMARK : IconType.BOOKMARK_EMPTY);
//		bookmark.setIconSize(IconSize.LARGE);
//		bookmark.addClickHandler(new ClickHandler() {
//			@Override
//			public void onClick(ClickEvent event) {
//				// TODO link presenter
//				// TODO get bookmarked or not
//				presenter.bookmark(!isBookmarked);
//			}
//		});
//		recipeDetailsPanel.add(bookmark);

		// categories & seasons
		Label categoriesLabel = new Label(constants.categories());
		categoriesLabel.setStyleName("visuallyhidden");
		recipeDetailsPanel.add(categoriesLabel);
		for (Category category : recipe.getCategories()) {
			Badge categoryBadge = new Badge(localizeEnum(category));
			categoryBadge.setStylePrimaryName("category");
			categoryBadge.addStyleDependentName(category.name().toLowerCase());
			categoryBadge.setWordWrap(false);
			Anchor categoryAnchor = new Anchor("", SearchPresenter.buildSearchByCategoryUrl(category));
			categoryAnchor.getElement().appendChild(categoryBadge.getElement());
			recipeDetailsPanel.add(categoryAnchor);
		}

		Label seasonsLabel = new Label(constants.seasons());
		seasonsLabel.setStyleName("visuallyhidden");
		recipeDetailsPanel.add(seasonsLabel);
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

		final List<StepDto> steps = recipe.getSteps();
		SimplePagingWidget simplePaging = new SimplePagingWidget(new PagingHandler() {
			@Override
			public void changePage(long page) {
				stepPanel.setWidget(new HTML((page + 1) + ". " + steps.get((int)page).getContent()));
			}
		});
		if (!steps.isEmpty()) {
			stepPanel.setWidget(new HTML("1. " + steps.get(0).getContent()));
			panelSteps.add(stepPanel);
			panelSteps.add(simplePaging);
			simplePaging.setPage(0, steps.size());
		}

		// audio
		List<AudioDto> audios = recipe.getAudios();
		if (audios.isEmpty()) {
			panelAudio.add(new HTML(messages.recipeNoAudio()));
		} else if (!Audio.isSupported()) {
			panelAudio.add(new HTML(messages.htmlAudioNotSupported()));
		}
		for (AudioDto audioDto : audios) {
			panelAudio.add(new AudioWidget(audioDto));
		}

		// video
		List<VideoDto> videos = recipe.getVideos();
		if (audios.isEmpty()) {
			panelVideo.add(new HTML(messages.recipeNoVideo()));
		} else if (!Video.isSupported()) {
			panelVideo.add(new HTML(messages.htmlVideoNotSupported()));
		}
		for (VideoDto videoDto : videos) {
			panelVideo.add(new VideoWidget(videoDto));
		}

		// appendices
		List<AppendixDto> appendices = recipe.getAppendices();
		if (!appendices.isEmpty()) {
			commentsPanel.add(new Heading(HEADING_SIZE + 1, constants.appendices()));
			for (AppendixDto appendix : appendices) {
				commentsPanel.add(new AppendixWidget(appendix));
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

		// TODO display user preferred / default view
		setView(view);
		center.setVisible(true);
		fluid.setVisible(true);
	}

	public void setView(String view) {
		if (view == null || view.equalsIgnoreCase("basic")) {
			tabPanel.selectTab(0);
		} else if (view.equalsIgnoreCase("steps")) {
			tabPanel.selectTab(1);
		} else if (view.equalsIgnoreCase("video")) {
			tabPanel.selectTab(2);
		} else if (view.equalsIgnoreCase("audio")) {
			tabPanel.selectTab(3);
		}
		/*if (view == null || view.equalsIgnoreCase("basic")) {
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
		}*/
	}

	@Override
	public void onTabChange(int tab) {
//		switch (tab) {
//		case TabsWidget.TAB_BASIC:
//			mainPanel.setWidget(panelBasic);
//			break;
//		case TabsWidget.TAB_STEPS:
//			mainPanel.setWidget(panelSteps);
//			break;
//		case TabsWidget.TAB_VIDEO:
//			mainPanel.setWidget(panelVideo);
//			break;
//		case TabsWidget.TAB_AUDIO:
//			mainPanel.setWidget(panelAudio);
//			break;
//		}
	}

	public void setBookmarked(boolean isBookmarked) {
//		bookmark.setIcon(isBookmarked ? IconType.BOOKMARK : IconType.BOOKMARK_EMPTY);
	}
}

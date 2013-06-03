package si.merljak.magistrska.client.mvp.home;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.mvp.AbstractView;
import si.merljak.magistrska.client.mvp.ingredient.IngredientIndexPresenter;
import si.merljak.magistrska.client.mvp.login.LoginPresenter;
import si.merljak.magistrska.client.mvp.recipe.RecipePresenter;
import si.merljak.magistrska.client.mvp.search.SearchPresenter;
import si.merljak.magistrska.client.mvp.utensil.UtensilIndexPresenter;
import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.RecommendationsDto;
import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.RecommendationType;
import si.merljak.magistrska.common.enumeration.Season;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Column;
import com.github.gwtbootstrap.client.ui.Emphasis;
import com.github.gwtbootstrap.client.ui.FluidRow;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Icon;
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.Lead;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.github.gwtbootstrap.client.ui.base.UnorderedList;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.client.ui.constants.ImageType;
import com.github.gwtbootstrap.client.ui.resources.ButtonSize;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Home page view.
 * 
 * @author Jakob Merljak
 * 
 */
public class HomeView extends AbstractView {

	// widgets
	private final Button linkBrowseAll = new Button(messages.browseAllRecipes());
	private final Button linkLogin = new Button(messages.loginForRecommendations());
	private final FluidRow recommendPanel = new FluidRow();
	private final FluidRow featuredPanel = new FluidRow();

	private List<RecommendationType> recommendationTypes = Arrays.asList(
			RecommendationType.INGREDIENTS_FROM_FRIDGE, 
			RecommendationType.USER_PREFERENCES, 
			RecommendationType.LOCAL_TIME,
			RecommendationType.FEATURED,
			RecommendationType.LOCAL_SEASON,
			RecommendationType.LOCAL_SPECIALTY);

	private Set<Long> idSet = new HashSet<Long>();

	public HomeView() {
		Heading heading = new Heading(HEADING_SIZE, constants.appTitle());
		Heading headingLexicon = new Heading(HEADING_SIZE + 1, constants.lexicon());
		Heading headingCategories = new Heading(HEADING_SIZE + 1, constants.recipesByCategory());

		// featured and recommendations
		Lead featuredIntro = new Lead();
		featuredIntro.addStyleName("lead-home");
		featuredIntro.setText(constants.haveYouTried());

		featuredPanel.setStyleName("featuredPanel");
		recommendPanel.addStyleName("recommendPanel");

		// links
		linkLogin.setVisible(false);
		linkLogin.setType(ButtonType.SUCCESS);
		linkLogin.setSize(ButtonSize.LARGE);
		linkLogin.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				History.newItem(LoginPresenter.SCREEN_NAME);
			}
		});

		linkBrowseAll.setIcon(IconType.SEARCH);
		linkBrowseAll.setType(ButtonType.PRIMARY);
		linkBrowseAll.setSize(ButtonSize.LARGE);
		linkBrowseAll.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				SearchPresenter.doSearch("");
			}
		});

		FlowPanel linksHolder = new FlowPanel();
		linksHolder.setStyleName("linksHolder");
		linksHolder.add(linkLogin);
		linksHolder.add(linkBrowseAll);

		// lexicon
		Paragraph lexiconIntro = new Paragraph(messages.lexiconIntro());

		Anchor ingredientsLink = new Anchor(constants.ingredients(), "#" + IngredientIndexPresenter.SCREEN_NAME);
		Column ingredientsLinkHolder = new Column(6);
		ingredientsLinkHolder.add(ingredientsLink);
		ingredientsLinkHolder.addStyleName("imageLink");
		ingredientsLinkHolder.addStyleName("imageLink-ingredients");
		ingredientsLinkHolder.addStyleName(ImageType.ROUNDED.get());

		Anchor utensilsLink = new Anchor(constants.utensils(), "#" + UtensilIndexPresenter.SCREEN_NAME);
		Column utensilsLinkHolder = new Column(6);
		utensilsLinkHolder.add(utensilsLink);
		utensilsLinkHolder.addStyleName("imageLink");
		utensilsLinkHolder.addStyleName("imageLink-utensils");
		utensilsLinkHolder.addStyleName(ImageType.ROUNDED.get());
		
		FluidRow lexiconRow = new FluidRow();
		lexiconRow.add(ingredientsLinkHolder);
		lexiconRow.add(utensilsLinkHolder);

		// recipes by category
		UnorderedList categories = new UnorderedList();
		categories.setStyleName("categoriesLinkList");
		for (Category category : Category.values()) {
			Anchor link = new Anchor(localizeEnum(category));
			link.setHref(SearchPresenter.buildSearchByCategoryUrl(category));
			categories.add(new ListItem(link));
		}

		UnorderedList seasons = new UnorderedList();
		seasons.setStyleName("categoriesLinkList");
		for (Season season : Season.values()) {
			Anchor link = new Anchor(localizeEnum(season));
			link.setHref(SearchPresenter.buildSearchBySeasonUrl(season));
			seasons.add(new ListItem(link));
		}

		UnorderedList difficulties = new UnorderedList();
		difficulties.setStyleName("categoriesLinkList");
		for (Difficulty difficulty : Difficulty.values()) {
			Anchor link = new Anchor(localizeEnum(difficulty));
			link.setHref(SearchPresenter.buildSearchByDifficultyUrl(difficulty));
			difficulties.add(new ListItem(link));
		}

		Column categoriesColumn = new Column(4);
		categoriesColumn.add(new Paragraph(constants.categories()));
		categoriesColumn.add(categories);

		Column seasonsColumn = new Column(4);
		seasonsColumn.add(new Paragraph(constants.seasons()));
		seasonsColumn.add(seasons);

		Column difficultiesColumn = new Column(4);
		difficultiesColumn.add(new Paragraph(constants.difficulty()));
		difficultiesColumn.add(difficulties);

		FluidRow categoriesIndex = new FluidRow();
		categoriesIndex.add(categoriesColumn);
		categoriesIndex.add(seasonsColumn);
		categoriesIndex.add(difficultiesColumn);

		// layout
		FlowPanel main = new FlowPanel();
		main.add(heading);
		main.add(recommendPanel);
		main.add(featuredIntro);
		main.add(featuredPanel);
		main.add(linksHolder);
		main.add(headingLexicon);
		main.add(lexiconIntro);
		main.add(lexiconRow);
		main.add(headingCategories);
		main.add(categoriesIndex);
		initWidget(main);
	}

	public void displayRecommendations(RecommendationsDto result, boolean isUserLoggedIn) {
		idSet.clear();
		recommendPanel.clear();
		featuredPanel.clear();
		linkLogin.setVisible(!isUserLoggedIn);

		Map<RecommendationType, List<RecipeDto>> recommendations = result.getRecommendations();
		// using predefined type list (instead of recommendations.keySet()) to assure order
		for (RecommendationType type : recommendationTypes) {
			if (recommendations.containsKey(type)) {
				for (RecipeDto recipe : recommendations.get(type)) {
					long recipeId = recipe.getId();
					if (!idSet.add(recipeId)) {
						// skip duplicates
						continue;
					}
	
					if (type == RecommendationType.INGREDIENTS_FROM_FRIDGE || type == RecommendationType.USER_PREFERENCES) {
						addExposedEntry(recipe, type);
					} else {
						addResultEntry(recipe, type);
					}
				}
			}
		}
	}

	// TODO cleanup!!!
	private void addResultEntry(RecipeDto recipe, RecommendationType type) {
		String heading = recipe.getHeading();
		String imageUrl = recipe.getImageUrl();
		if (imageUrl == null) {
			imageUrl = AbstractView.RECIPE_IMG_FALLBACK;
		}

		Image image = new Image(AbstractView.RECIPE_THUMB_IMG_FOLDER + imageUrl);
		image.setType(ImageType.POLAROID);
		image.setAltText(heading);

		Anchor link = new Anchor(heading, RecipePresenter.buildRecipeUrl(recipe.getId()));
		link.getElement().appendChild(image.getElement());
		
		Label timeOverall = new Label(" " + timeFromMinutes(recipe.getTimeOverall()));
		timeOverall.setTitle(constants.timeOverall());
		timeOverall.getElement().insertFirst(new Icon(IconType.TIME).getElement());

		FlowPanel recommendationEntry = new FlowPanel();
		recommendationEntry.setStyleName("resultEntry");
		recommendationEntry.addStyleDependentName(type.name().toLowerCase());
		recommendationEntry.add(link);
		recommendationEntry.add(new Label(localizeEnum(recipe.getDifficulty())));
		recommendationEntry.add(timeOverall);
//		recommendationEntry.add(new Emphasis(constants.recommendationMap().get(type.name())));
		featuredPanel.add(recommendationEntry);
	}

	private void addExposedEntry(RecipeDto recipe, RecommendationType type) {
		String heading = recipe.getHeading();
		String imageUrl = recipe.getImageUrl();
		if (imageUrl == null) {
			imageUrl = AbstractView.RECIPE_IMG_FALLBACK;
		}

		Image image = new Image(AbstractView.RECIPE_THUMB_IMG_FOLDER + imageUrl);
		image.setType(ImageType.POLAROID);
		image.setAltText(heading);

		Anchor link = new Anchor(heading, RecipePresenter.buildRecipeUrl(recipe.getId()));
		link.getElement().appendChild(image.getElement());
		
		Label timeOverall = new Label(" " + timeFromMinutes(recipe.getTimeOverall()));
		timeOverall.setTitle(constants.timeOverall());
		timeOverall.getElement().insertFirst(new Icon(IconType.TIME).getElement());

		Lead lead = new Lead();
		lead.addStyleDependentName("home");
		lead.setText(constants.recommendationMap().get(type.name()));

		Column recommendationEntry = new Column(6);
		recommendationEntry.addStyleName("exposedEntry");
		recommendationEntry.addStyleName("exposedEntry-" + type.name().toLowerCase());
		recommendationEntry.add(lead);
		recommendationEntry.add(link);
		recommendationEntry.add(new Label(localizeEnum(recipe.getDifficulty())));
		recommendationEntry.add(timeOverall);
		recommendPanel.add(recommendationEntry);
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(null);
		return super.asWidget();
	}
}

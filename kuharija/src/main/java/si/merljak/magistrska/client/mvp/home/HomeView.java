package si.merljak.magistrska.client.mvp.home;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.mvp.AbstractView;
import si.merljak.magistrska.client.mvp.recipe.RecipePresenter;
import si.merljak.magistrska.client.mvp.search.SearchPresenter;
import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.RecommendationsDto;
import si.merljak.magistrska.common.enumeration.RecommendationType;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Column;
import com.github.gwtbootstrap.client.ui.Emphasis;
import com.github.gwtbootstrap.client.ui.FluidRow;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Icon;
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.Lead;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.client.ui.constants.ImageType;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
	private final FluidRow userRecommendPanel = new FluidRow();
	private final FlowPanel recommendListPanel = new FlowPanel();

	private List<RecommendationType> recommendationTypes = Arrays.asList(
			RecommendationType.INGREDIENTS_FROM_FRIDGE, 
			RecommendationType.USER_PREFERENCES, 
			RecommendationType.LOCAL_TIME,
			RecommendationType.FEATURED,
			RecommendationType.LOCAL_SEASON,
			RecommendationType.LOCAL_SPECIALTY);

	private Set<Long> idSet = new HashSet<Long>();

	public HomeView() {
		Lead heading2 = new Lead();
		heading2.setText(constants.recommendations2());
		heading2.addStyleDependentName("home");

		userRecommendPanel.addStyleName("userRecommendations");

		Button buttonAll = new Button(messages.browseAllRecipes());
		buttonAll.setType(ButtonType.PRIMARY);
		buttonAll.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				SearchPresenter.doSearch("");
			}
		});

		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, constants.appTitle()));
		main.add(userRecommendPanel);
		main.add(heading2);
		main.add(recommendListPanel);
		main.add(new Lead());
		main.add(buttonAll);
		initWidget(main);
	}

	public void displayRecommendations(RecommendationsDto result) {
		idSet.clear();
		userRecommendPanel.clear();
		recommendListPanel.clear();

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
		recommendationEntry.add(new Emphasis(constants.recommendationMap().get(type.name())));
		recommendListPanel.add(recommendationEntry);
	}

	private void addExposedEntry(RecipeDto recipe, RecommendationType type) {
		String heading = recipe.getHeading();
		String imageUrl = recipe.getImageUrl();
		if (imageUrl == null) {
			imageUrl = AbstractView.RECIPE_IMG_FALLBACK;
		}

		Image image = new Image(AbstractView.RECIPE_THUMB_IMG_FOLDER + imageUrl);
		image.setType(ImageType.ROUNDED);
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
		userRecommendPanel.add(recommendationEntry);
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(null);
		return super.asWidget();
	}
}

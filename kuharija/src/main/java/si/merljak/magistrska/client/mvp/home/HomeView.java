package si.merljak.magistrska.client.mvp.home;

import java.util.List;
import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.mvp.AbstractView;
import si.merljak.magistrska.client.mvp.recipe.RecipePresenter;
import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.RecommendationsDto;
import si.merljak.magistrska.common.enumeration.RecommendationType;

import com.github.gwtbootstrap.client.ui.Heading;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
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
	private final FlowPanel recommendPanel = new FlowPanel();

	public HomeView () {
		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, constants.appTitle()));
		main.add(recommendPanel);
		initWidget(main);
	}

	public void displayRecommendations(RecommendationsDto result) {
		recommendPanel.clear();
		Map<RecommendationType, List<RecipeDto>> recommendations = result.getRecommendations();
		for (RecommendationType type : recommendations.keySet()) {
			recommendPanel.add(new Label(constants.recommendationMap().get(type.name())));
			for (RecipeDto recipe : recommendations.get(type)) {
				String imageUrl = recipe.getImageUrl();
				if (imageUrl == null) {
					imageUrl = RECIPE_IMG_FALLBACK;
				}
				Image image = new Image(RECIPE_THUMB_IMG_FOLDER + imageUrl);
				image.setAltText(recipe.getHeading());
				Anchor link = new Anchor(recipe.getHeading(), RecipePresenter.buildRecipeUrl(recipe.getId()));

				FlowPanel resultEntry = new FlowPanel();
				resultEntry.setStyleName("resultEntry");
				resultEntry.add(link);
				resultEntry.add(image);
				resultEntry.add(new Label(localizeEnum(recipe.getDifficulty())));
				resultEntry.add(new Label(timeFromMinutes(recipe.getTimeOverall())));

				recommendPanel.add(resultEntry);
			}
		}
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(null);
		return super.asWidget();
	}
}

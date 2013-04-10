package si.merljak.magistrska.client.mvp;

import java.util.List;
import java.util.Map;

import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.RecommendationsDto;
import si.merljak.magistrska.common.enumeration.RecommendationType;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class HomeView extends AbstractView {

	private static final RootPanel main = RootPanel.get("homeWrapper");

	public HomeView () {
		initWidget(main);
	}

	public void displayRecommendations(RecommendationsDto result) {
		main.clear();
		Map<RecommendationType, List<RecipeDto>> recommendations = result.getRecommendations();
		for (RecommendationType type : recommendations.keySet()) {
			main.add(new Label(type.name()));
			for (RecipeDto recipe : recommendations.get(type)) {
				Image image = new Image(RECIPE_THUMB_IMG_FOLDER + recipe.getImageUrl());
				image.setAltText(recipe.getHeading());
				Anchor link = new Anchor(recipe.getHeading(), RecipePresenter.buildRecipeUrl(recipe.getId()));

				FlowPanel resultEntry = new FlowPanel();
				resultEntry.setStyleName("resultEntry");
				resultEntry.add(link);
				resultEntry.add(image);
				resultEntry.add(new Label(localizeEnum(recipe.getDifficulty())));
				resultEntry.add(new Label(timeFromMinutes(recipe.getTimeOverall())));

				main.add(resultEntry);
			}
		}
		setVisible(true);
	}
}

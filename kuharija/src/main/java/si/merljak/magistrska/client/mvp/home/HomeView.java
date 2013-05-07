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
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.constants.ImageType;
import com.google.gwt.user.client.Timer;
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

	// constants
	private static final int SCEDULE = 10000;

	// widgets
	private final FlowPanel recommendPanel = new FlowPanel();

	// timer
	private int selected;
	private final Timer timer;

	public HomeView () {
		recommendPanel.getElement().setId("recommendations");
		timer = new Timer() {
			@Override
			public void run() {
				int widgetCount = recommendPanel.getWidgetCount();
				if (widgetCount > 0) {
					recommendPanel.getWidget(selected).addStyleName("visuallyhidden");
					selected = (selected  + 1) % widgetCount;
					recommendPanel.getWidget(selected).removeStyleName("visuallyhidden");
				}
			}
		};

		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, constants.appTitle()));
		main.add(recommendPanel);
		initWidget(main);
	}

	public void displayRecommendations(RecommendationsDto result) {
		timer.cancel();
		recommendPanel.clear();
		Map<RecommendationType, List<RecipeDto>> recommendations = result.getRecommendations();
		for (RecommendationType type : recommendations.keySet()) {
			for (RecipeDto recipe : recommendations.get(type)) {
				String heading = recipe.getHeading();
				String imageUrl = recipe.getImageUrl();
				if (imageUrl == null) {
					imageUrl = RECIPE_IMG_FALLBACK;
				}

				Image image = new Image(RECIPE_IMG_FOLDER + imageUrl);
				image.setAltText(heading);
				image.setType(ImageType.POLAROID);

				Anchor link = new Anchor(heading, RecipePresenter.buildRecipeUrl(recipe.getId()));
				link.getElement().appendChild(image.getElement());

				FlowPanel recommendationEntry = new FlowPanel();
				recommendationEntry.setStyleName("recommendation");
				recommendationEntry.add(link);
				recommendationEntry.add(new Label(localizeEnum(recipe.getDifficulty())));
				recommendationEntry.add(new Label(timeFromMinutes(recipe.getTimeOverall())));
				recommendationEntry.add(new Label(constants.recommendationMap().get(type.name())));
//				recommendationEntry.addHandler(new MouseOverHandler() {
//					@Override
//					public void onMouseOver(MouseOverEvent event) {
//						timer.cancel();
//					}
//				}, MouseOverEvent.getType());
//				recommendationEntry.addHandler(new MouseOutHandler() {
//					@Override
//					public void onMouseOut(MouseOutEvent event) {
//						timer.scheduleRepeating(SCEDULE);
//					}
//				}, MouseOutEvent.getType());

				recommendationEntry.addStyleName("visuallyhidden");
				recommendPanel.add(recommendationEntry);
			}
		}

		if (recommendPanel.getWidgetCount() > 0) {
			selected = 0;
			recommendPanel.getWidget(selected).removeStyleName("visuallyhidden");
			timer.scheduleRepeating(SCEDULE);
		}
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(null);
		return super.asWidget();
	}
}

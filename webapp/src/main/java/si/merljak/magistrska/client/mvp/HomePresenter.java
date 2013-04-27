package si.merljak.magistrska.client.mvp;

import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.common.dto.RecommendationsDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.RecommendationServiceAsync;

import com.google.gwt.geolocation.client.Position.Coordinates;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class HomePresenter extends AbstractPresenter {

	// screen and parameters name
	public static final String SCREEN_NAME = "home";

	// remote service
	private RecommendationServiceAsync recommendationService;

	// view
    private final HomeView homeView = new HomeView();

	public HomePresenter(Language language, RecommendationServiceAsync recommendationService) {
		super(language);
		this.recommendationService = recommendationService;
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		getRecommendations();
		return homeView.asWidget();
	}

	
	private void getRecommendations() {
		String username = null;// TODO = LoginPresenter.getUsername();
		Coordinates coordinates = Kuharija.getCoordinates();
		Double latitude = coordinates != null ? coordinates.getLatitude() : null;
		Double longitude = coordinates != null ? coordinates.getLongitude() : null;

		recommendationService.recommendRecipes(username, latitude, longitude, language, new AsyncCallback<RecommendationsDto>() {
			@Override
			public void onSuccess(RecommendationsDto result) {
				homeView.displayRecommendations(result);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Kuharija.handleException(caught);
			}
		});
	}

}

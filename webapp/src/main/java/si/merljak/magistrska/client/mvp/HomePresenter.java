package si.merljak.magistrska.client.mvp;

import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.common.dto.RecommendationsDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.RecommendationServiceAsync;

import com.google.gwt.geolocation.client.Position.Coordinates;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class HomePresenter extends AbstractPresenter {

	// screen and parameters name
	public static final String SCREEN_NAME = "home";

	// remote service
	private RecommendationServiceAsync recommendationService;

	// view
    private HomeView homeView = new HomeView();

	public HomePresenter(Language language, RecommendationServiceAsync recommendationService) {
		super(language);
		this.recommendationService = recommendationService;
	}

	@Override
	protected boolean isPresenterForScreen(String screenName) {
		return SCREEN_NAME.equalsIgnoreCase(screenName);
	}

	@Override
	protected void parseParameters(String screenName, Map<String, String> parameters) {
		getRecommendations();
	}

	
	private void getRecommendations() {
		String username = "user1"; // TODO get user
		Coordinates coordinates = Kuharija.getCoordinates();
		Double latitude = coordinates != null ? coordinates.getLatitude() : null;
		Double longitude = coordinates != null ? coordinates.getLongitude() : null;
		recommendationService.recommendRecipes(username, latitude, longitude, language, new AsyncCallback<RecommendationsDto>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}

			@Override
			public void onSuccess(RecommendationsDto result) {
				homeView.displayRecommendations(result);
			}
		});
	}

	@Override
	protected void hideView() {
		homeView.hide();
	}

}

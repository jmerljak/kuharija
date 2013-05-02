package si.merljak.magistrska.client.mvp;

import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.event.LoginEvent;
import si.merljak.magistrska.client.event.LoginEventHandler;
import si.merljak.magistrska.common.dto.RecommendationsDto;
import si.merljak.magistrska.common.dto.UserDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.RecommendationServiceAsync;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.geolocation.client.Position.Coordinates;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class HomePresenter extends AbstractPresenter implements LoginEventHandler {

	// screen and parameters name
	public static final String SCREEN_NAME = "";

	// remote service
	private RecommendationServiceAsync recommendationService;

	// view
    private final HomeView homeView = new HomeView();
    
    // variables
    private Coordinates coordinates;
    private UserDto user;

	public HomePresenter(Language language, RecommendationServiceAsync recommendationService, EventBus eventBus) {
		super(language);
		this.recommendationService = recommendationService;
		eventBus.addHandler(LoginEvent.TYPE, this);
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		getRecommendations();
		return homeView.asWidget();
	}

	
	private void getRecommendations() {
		String username = user != null ? user.getUsername() : null;
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

	public void setCoordinates(Coordinates coordinates) {
		this.coordinates = coordinates;
		getRecommendations();
	}

	@Override
	public void onLogin(LoginEvent event) {
		// register user
		user = event.getUser();
		getRecommendations();
	}

}

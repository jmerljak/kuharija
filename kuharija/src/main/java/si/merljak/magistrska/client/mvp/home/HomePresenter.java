package si.merljak.magistrska.client.mvp.home;

import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.event.GeolocateEvent;
import si.merljak.magistrska.client.event.GeolocateEventHandler;
import si.merljak.magistrska.client.event.LoginEvent;
import si.merljak.magistrska.client.event.LoginEventHandler;
import si.merljak.magistrska.client.mvp.AbstractPresenter;
import si.merljak.magistrska.common.dto.RecommendationsDto;
import si.merljak.magistrska.common.dto.UserDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.RecommendationServiceAsync;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.geolocation.client.Position.Coordinates;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 * Home page presenter.
 * 
 * @author Jakob Merljak
 * 
 */
public class HomePresenter extends AbstractPresenter implements LoginEventHandler, GeolocateEventHandler {

	// screen name
	public static final String SCREEN_NAME = "";

	// remote service
	private final RecommendationServiceAsync recommendationService;

	// view
	private final HomeView homeView = new HomeView();

	// user variables
	private Coordinates coordinates;
	private UserDto user;

	public HomePresenter(Language language, RecommendationServiceAsync recommendationService, EventBus eventBus) {
		super(language);
		this.recommendationService = recommendationService;
		eventBus.addHandler(LoginEvent.TYPE, this);

		getRecommendations();
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		return homeView.asWidget();
	}

	/** Gets recommendations for user. */
	private void getRecommendations() {
		String username = user != null ? user.getUsername() : null;
		Double latitude = null, longitude = null;
		if (coordinates != null) {
			latitude = coordinates.getLatitude();
			longitude = coordinates.getLongitude();
		}

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

	@Override
	public void onLogin(LoginEvent event) {
		// register user and get recommendations
		user = event.getUser();
		getRecommendations();
	}

	@Override
	public void onGeolocate(GeolocateEvent event) {
		// register coordinates and get recommendations
		coordinates = event.getCoordinates();
		getRecommendations();
	}

	@Override
	public String getScreenName() {
		return SCREEN_NAME;
	}

	@Override
	public String getParentName() {
		return null;
	}
}

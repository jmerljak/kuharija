package si.merljak.magistrska.client.mvp;

import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.handler.LogoutHandler;
import si.merljak.magistrska.common.dto.SessionDto;
import si.merljak.magistrska.common.dto.UserDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.enumeration.LoginError;
import si.merljak.magistrska.common.rpc.UserServiceAsync;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class LoginPresenter extends AbstractPresenter implements LogoutHandler {
	
	public interface View {
		/**
		 * Displays login form and error message (if any).
		 * @param error enumerator (optional)
		 */
		void displayLoginForm(LoginError error);

		/**
		 * Displays register form and error message (if any).
		 * @param error enumerator (optional)
		 */
		void displayRegisterForm(LoginError error);

		void setPresenter(LoginPresenter presenter);
		void hide();
		Widget asWidget();
	}

	// screen and parameter name
	public static final String SCREEN_NAME = "login";

	private static final String SESSION_COOKIE_NAME = "sid";

	// remote service
	private UserServiceAsync userService;

	// user
	private static UserDto user;
	
	// view
	View view;

	public LoginPresenter(Language language, UserServiceAsync userService, LoginView view) {
		super(language);
		this.userService = userService;
		this.view = view;
		view.setPresenter(this);

		checkSession();
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		if (user == null) {
			view.displayLoginForm(null);
		} else {
			// redirect back to previous screen
			History.back();
		}
		return view.asWidget();
	}

	/** Checks if there is valid session. */
	private void checkSession() {
		String sessionId = Cookies.getCookie(SESSION_COOKIE_NAME);
		if (sessionId != null) {
			userService.checkSession(sessionId, new AsyncCallback<UserDto>() {
				@Override
				public void onSuccess(UserDto userDto) {
					if (userDto != null) {
						user = userDto;
//						view.displayLogedInUser(user);
						// TODO fire event
					} else {
						// session expired
						Cookies.removeCookie(SESSION_COOKIE_NAME);
						view.displayLoginForm(LoginError.SESSION_EXPIRED);
					}
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// ignore, assume no user is logged in
				}
			});
		}
	}

	/** 
	 * Tries to register and log in new user.
	 * 
	 * @param username (must be unique)
	 * @param password desired password
	 * @param name custom name to show
	 * @param email (optional)
	 */
	public void register(String username, String password, String name, String email) {
		userService.register(username, password, name, email, new AsyncCallback<SessionDto>() {
			@Override
			public void onSuccess(SessionDto session) {
				if (session != null) {
					user = session.getUser();
					Cookies.setCookie(SESSION_COOKIE_NAME, session.getSessionId(), session.getExpires());
//					view.displayLogedInUser(user);
					// TODO fire event
					History.back();
				} else {
					// username already exists
					// view.displayRegisterForm(LoginError.USERNAME_ALREADY_EXISTS);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Kuharija.handleException(caught);
			}
		});
	}

	/** 
	 * Tries to login with provided credentials.
	 * 
	 * @param username username
	 * @param attemptedPassword password
	 */
	public void login(String username, String attemptedPassword) {
		userService.login(username, attemptedPassword, new AsyncCallback<SessionDto>() {
			@Override
			public void onSuccess(SessionDto session) {
				if (session != null) {
					user = session.getUser();
				    Cookies.setCookie(SESSION_COOKIE_NAME, session.getSessionId(), session.getExpires());
//					view.displayLogedInUser(user);
					// TODO fire event
				} else {
					user = null;
					view.displayLoginForm(LoginError.INCORRECT_USERNAME_PASSWORD);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Kuharija.handleException(caught);
			}
		});
	}

	@Override
	public void logout() {
		user = null;
		String sessionId = Cookies.getCookie(SESSION_COOKIE_NAME);
		if (sessionId != null) {
			Cookies.removeCookie(SESSION_COOKIE_NAME);
			userService.logout(sessionId, new AsyncCallback<Void>() {
				@Override
				public void onSuccess(Void result) {
					// do nothing
//					view.displayLogedInUser(null);
					// TODO fire event
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// ignore
				}
			});
		}
	}

	/** Returns proper anchor URL for login. */
	public static String getLoginUrl() {
		return "#" + SCREEN_NAME;
	}

	public String getUsername() {
		return user != null ? user.getUsername() : null;
	}
}

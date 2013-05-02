package si.merljak.magistrska.client.mvp.login;

import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.event.LoginEvent;
import si.merljak.magistrska.client.handler.LoginHandler;
import si.merljak.magistrska.client.handler.LogoutHandler;
import si.merljak.magistrska.client.mvp.AbstractPresenter;
import si.merljak.magistrska.common.dto.SessionDto;
import si.merljak.magistrska.common.dto.UserDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.enumeration.LoginError;
import si.merljak.magistrska.common.rpc.UserServiceAsync;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

public class LoginPresenter extends AbstractPresenter implements LoginHandler, LogoutHandler {
	
	public interface LoginView {
		/** Clears login form and message. */
		void clear();

		/**
		 * Displays error message.
		 * @param error enumerator
		 */
		void showError(LoginError error);

		/** Displays login success message. */
		void showLoginSuccess();

		/** Displays logout success message. */
		void showLogoutSuccess();

		/** 
		 * Sets login handler.
		 * @param loginHandler handler implementation
		 */
		void setLoginHandler(LoginHandler loginHandler);

		/** Gets view as widget. */
		Widget asWidget();
	}

	// screen name
	public static final String SCREEN_NAME = "login";

	// cookie name
	private static final String SESSION_COOKIE_NAME = "sid";

	// remote service
	private final UserServiceAsync userService;

	// event bus
	private final EventBus eventBus;

	// user
	private UserDto user;
	
	// view
	private final LoginView loginView;

	public LoginPresenter(Language language, UserServiceAsync userService, LoginView view, EventBus eventBus) {
		super(language);
		this.userService = userService;
		this.loginView = view;
		this.eventBus = eventBus;
		view.setLoginHandler(this);

		checkSession();
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		loginView.clear();
		if (user != null) {
		    loginView.showLoginSuccess();
		}
		return loginView.asWidget();
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
					    loginView.showLoginSuccess();
					    eventBus.fireEvent(new LoginEvent(user));
					} else {
						// session expired
						Cookies.removeCookie(SESSION_COOKIE_NAME);
						loginView.showError(LoginError.SESSION_EXPIRED);
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
				    loginView.showLoginSuccess();
				    eventBus.fireEvent(new LoginEvent(user));
//					History.back();
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

	@Override
	public void login(String username, String attemptedPassword) {
		userService.login(username, attemptedPassword, new AsyncCallback<SessionDto>() {
			@Override
			public void onSuccess(SessionDto session) {
				if (session != null) {
					user = session.getUser();
					Cookies.setCookie(SESSION_COOKIE_NAME, session.getSessionId(), session.getExpires());
					loginView.showLoginSuccess();
					eventBus.fireEvent(new LoginEvent(user));
				} else {
					user = null;
					loginView.showError(LoginError.INCORRECT_USERNAME_PASSWORD);
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
					loginView.showLogoutSuccess();
					eventBus.fireEvent(new LoginEvent(user));
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
}

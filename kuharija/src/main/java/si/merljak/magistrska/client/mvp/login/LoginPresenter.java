package si.merljak.magistrska.client.mvp.login;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.handler.LoginEventHandler;
import si.merljak.magistrska.client.handler.LoginHandler;
import si.merljak.magistrska.client.handler.LogoutHandler;
import si.merljak.magistrska.client.mvp.AbstractPresenter;
import si.merljak.magistrska.common.dto.SessionDto;
import si.merljak.magistrska.common.dto.UserDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.enumeration.LoginError;
import si.merljak.magistrska.common.rpc.UserServiceAsync;

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

	// screen and parameter name
	public static final String SCREEN_NAME = "login";

	private static final String SESSION_COOKIE_NAME = "sid";

	// remote service
	private UserServiceAsync userService;

	// user
	private UserDto user;
	private Set<LoginEventHandler> loginEventHandlers = new HashSet<LoginEventHandler>();
	
	// view
	private LoginView loginView;

	public LoginPresenter(Language language, UserServiceAsync userService, LoginView view) {
		super(language);
		this.userService = userService;
		this.loginView = view;
		view.setLoginHandler(this);

		checkSession();
	}

	public void addLoginEventHandler(LoginEventHandler handler) {
		if (handler != null) {
			loginEventHandlers.add(handler);
		}
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
						notifyOthers();
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
					notifyOthers();
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
					notifyOthers();
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
					notifyOthers();
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// ignore
				}
			});
		}
	}

	/** Notifies others about login event. */
	private void notifyOthers() {
		for (LoginEventHandler handler : loginEventHandlers) {
			handler.onUserLogin(user);
		}
	}

	/** Returns proper anchor URL for login. */
	public static String getLoginUrl() {
		return "#" + SCREEN_NAME;
	}
}

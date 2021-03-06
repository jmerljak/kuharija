package si.merljak.magistrska.client.mvp.login;

import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.event.LoginEvent;
import si.merljak.magistrska.client.handler.LoginHandler;
import si.merljak.magistrska.client.handler.LogoutHandler;
import si.merljak.magistrska.client.handler.RegisterHandler;
import si.merljak.magistrska.client.mvp.AbstractPresenter;
import si.merljak.magistrska.client.mvp.home.HomePresenter;
import si.merljak.magistrska.common.dto.SessionDto;
import si.merljak.magistrska.common.dto.UserDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.enumeration.LoginError;
import si.merljak.magistrska.common.rpc.UserServiceAsync;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;

/**
 * Login presenter.
 * 
 * @author Jakob Merljak
 * 
 */
public class LoginPresenter extends AbstractPresenter implements LoginHandler, LogoutHandler, RegisterHandler {

	public interface LoginView extends IsWidget {
		/**
		 * Displays error message.
		 * 
		 * @param error enumerator
		 */
		void showError(LoginError error);

		/**
		 * Displays login success message.
		 * 
		 * @param withTimer if redirect timer is set
		 */
		void showLoginSuccess(boolean withTimer);

		/** Displays logout success message. */
		void showLogoutSuccess();

		/**
		 * Sets login handler.
		 * 
		 * @param loginHandler handler implementation
		 */
		void setLoginHandler(LoginHandler loginHandler);

		/**
		 * Sets logout handler.
		 * 
		 * @param logoutHandler handler implementation
		 */
		void setLogoutHandler(LogoutHandler logoutHandler);
	}

	// screen name
	public static final String SCREEN_NAME = "login";

	// cookie name
	private static final String SESSION_COOKIE_NAME = "sid";

	// redirect timer timeout (in miliseconds)
	private static final int REDIRECT_TIMEOUT = 5000;

	// remote service
	private final UserServiceAsync userService;

	// event bus
	private final EventBus eventBus;

	// user
	private UserDto user;

	// view
	private final LoginView loginView;

	private final Timer redirectTimer;

	public LoginPresenter(Language language, UserServiceAsync userService, LoginView view, EventBus eventBus) {
		super(language);
		this.userService = userService;
		this.loginView = view;
		this.eventBus = eventBus;
		view.setLoginHandler(this);
		view.setLogoutHandler(this);

		redirectTimer = new Timer() {
			@Override
			public void run() {
				History.back();
			}
		};

		checkSession();
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		if (user != null) {
			loginView.showLoginSuccess(false);
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
						loginView.showLoginSuccess(false);
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

	@Override
	public void register(String username, String password, String name, String email) {
		userService.register(username, password, name, email, new AsyncCallback<SessionDto>() {
			@Override
			public void onSuccess(SessionDto session) {
				if (session != null) {
					user = session.getUser();
					Cookies.setCookie(SESSION_COOKIE_NAME, session.getSessionId(), session.getExpires());
					eventBus.fireEvent(new LoginEvent(user));
					loginView.showLoginSuccess(true);
					// set timer
					redirectTimer.schedule(REDIRECT_TIMEOUT);
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
					eventBus.fireEvent(new LoginEvent(user));
					loginView.showLoginSuccess(true);
					// set timer
					redirectTimer.schedule(REDIRECT_TIMEOUT);
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

	public void cancelRedirectTimer() {
		redirectTimer.cancel();
	}

	/** Returns proper anchor URL for login. */
	public static String getLoginUrl() {
		return "#" + SCREEN_NAME;
	}

	@Override
	public String getScreenName() {
		return SCREEN_NAME;
	}

	@Override
	public String getParentName() {
		return HomePresenter.SCREEN_NAME;
	}
}

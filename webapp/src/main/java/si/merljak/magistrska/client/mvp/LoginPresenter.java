package si.merljak.magistrska.client.mvp;

import java.util.Date;
import java.util.Map;

import si.merljak.magistrska.common.dto.SessionDto;
import si.merljak.magistrska.common.dto.UserDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.UserServiceAsync;

import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginPresenter extends AbstractPresenter {

	// screen and parameter name
	public static final String SCREEN_NAME = "login";

	// remote service
	private UserServiceAsync userService;

	// logged in user
	private UserDto user;

	public LoginPresenter(Language language, UserServiceAsync userService) {
		super(language);
		this.userService = userService;
	}
	
	@Override
	protected boolean isPresenterForScreen(String screenName) {
		return SCREEN_NAME.equalsIgnoreCase(screenName);
	}

	@Override
	protected void parseParameters(String screenName, Map<String, String> parameters) {
		logout();
	}

	private void register() {
		userService.register("user2", "password2", "User 2", null, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void result) {
				Window.alert("user added!");
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	private void login() {
		userService.login("user1", "password1", new AsyncCallback<SessionDto>() {
			@Override
			public void onSuccess(SessionDto session) {
				if (session != null) {
					user = session.getUser();
					Window.alert(user.getName() + " successfully loged in!");
					// set cookie
				    Cookies.setCookie("sid", session.getSessionId(), session.getExpires(), null, "/", false);
				} else {
					user = null;
					Window.alert("incorect username or password");
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	private void checkSession() {
		String sessionID = Cookies.getCookie("sid");
		if (sessionID == null) {
			Window.alert("no session, logging in...");
			login();
		} else {
			userService.checkSession(sessionID, new AsyncCallback<UserDto>() {
				@Override
				public void onSuccess(UserDto userDto) {
					if (userDto != null) {
						user = userDto;
						Window.alert("user " + user.getName() + " already signed in, session valid.");
					} else {
						Window.alert("session expired, logging in...");
						login();
					}
				}
				
				@Override
				public void onFailure(Throwable caught) {
					Window.alert(caught.getMessage());
				}
			});
		}
	}

	private void logout() {
		user = null;
		String sessionID = Cookies.getCookie("sid");
		if (sessionID != null) {
			Cookies.removeCookie("sid");
			userService.logout(sessionID, new AsyncCallback<Void>() {
				@Override
				public void onSuccess(Void result) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFailure(Throwable caught) {
					// TODO Auto-generated method stub
					
				}
			});
		}
	}

	@Override
	protected void hideView() {
//		utensilView.hide();
	}
}

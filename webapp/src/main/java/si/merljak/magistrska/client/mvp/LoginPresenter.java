package si.merljak.magistrska.client.mvp;

import java.util.Map;

import si.merljak.magistrska.common.dto.UserDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.UserServiceAsync;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginPresenter extends AbstractPresenter {

	// screen and parameter name
	public static final String SCREEN_NAME = "login";

	// remote service
	private UserServiceAsync userService;


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
		login();
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
		userService.login("user2", "password2", new AsyncCallback<UserDto>() {
			@Override
			public void onSuccess(UserDto result) {
				if (result != null) {
					Window.alert(result.getName() + " successfully loged in!");
				} else {
					Window.alert("incorect username or password");
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	@Override
	protected void hideView() {
//		utensilView.hide();
	}
}

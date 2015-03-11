package si.merljak.magistrska.client.event;

import si.merljak.magistrska.common.dto.UserDto;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Event for user login or logout.
 * 
 * @author Jakob Merljak
 * 
 */
public class LoginEvent extends GwtEvent<LoginEventHandler> {

	public static Type<LoginEventHandler> TYPE = new Type<LoginEventHandler>();

	private UserDto user;

	/**
	 * Event for user login or logout.
	 * 
	 * @param user DTO if user logged in or {@code null} in user logged out
	 */
	public LoginEvent(UserDto user) {
		super();
		this.user = user;
	}

	@Override
	public Type<LoginEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(LoginEventHandler handler) {
		handler.onLogin(this);
	}

	public UserDto getUser() {
		return user;
	}
}

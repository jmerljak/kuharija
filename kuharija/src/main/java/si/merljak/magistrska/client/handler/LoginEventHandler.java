package si.merljak.magistrska.client.handler;

import si.merljak.magistrska.common.dto.UserDto;

public interface LoginEventHandler {
	/** 
	 * Handles user login or logout.
	 * 
	 * @param user DTO or {@code null} in user logged out
	 */
	void onUserLogin(UserDto user);
}

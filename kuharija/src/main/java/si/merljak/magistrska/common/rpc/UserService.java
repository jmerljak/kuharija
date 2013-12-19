package si.merljak.magistrska.common.rpc;

import si.merljak.magistrska.common.dto.SessionDto;
import si.merljak.magistrska.common.dto.UserDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author Jakob Merljak
 */
@RemoteServiceRelativePath("user")
public interface UserService extends RemoteService {

	/** 
	 * Registers and logs in new user.
	 * 
	 * @param username username
	 * @param password password
	 * @param name custom name to show
	 * @param user's email (optional)
	 * 
	 * @return session and user details, or <em>null</em> if user with given username already exists 
	 * 
	 */
	SessionDto register(String username, String password, String name, String email);

	/** 
	 * Logs in user.
	 * 
	 * @param username
	 * @param attemptedPassword
	 * 
	 * @return session and user details, or <em>null</em> if username or password is invalid
	 */
	SessionDto login(String username, String attemptedPassword);

	/**
	 * Checks if session is valid.
	 * 
	 * @param sessionId session ID
	 * @return user details, or <em>null</em> if session not exists or has expired
	 */
	UserDto checkSession(String sessionId);
	
	/** Logs out user.
	 * 
	 * @param sessionID session ID
	 */
	void logout(String sessionId);
}

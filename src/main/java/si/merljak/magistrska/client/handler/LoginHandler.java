package si.merljak.magistrska.client.handler;

/**
 * 
 * @author Jakob Merljak
 *
 */
public interface LoginHandler {
	/** 
	 * Tries to login with provided credentials.
	 * 
	 * @param username username
	 * @param attemptedPassword password
	 */
	void login(String username, String attemptedPassword);
}

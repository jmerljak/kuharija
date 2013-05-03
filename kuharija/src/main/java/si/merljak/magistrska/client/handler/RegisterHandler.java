package si.merljak.magistrska.client.handler;

/**
 * 
 * @author Jakob Merljak
 *
 */
public interface RegisterHandler {
	/**
	 * Tries to register and log in new user.
	 * 
	 * @param username (must be unique)
	 * @param password desired password
	 * @param name custom name to show
	 * @param email (optional)
	 */
	void register(String username, String password, String name, String email);
}

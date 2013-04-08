package si.merljak.magistrska.common.rpc;

import si.merljak.magistrska.common.dto.UserDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

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
	 * @throws RunntimeException if user with given username already exists 
	 */
	void register(String username, String password, String name, String email);

	/** 
	 * Logs in user.
	 * 
	 * @param username
	 * @param password
	 * 
	 * @return user details and preferences, or <em>null</em> if username or password is invalid
	 */
	UserDto login(String username, String password);

	/** 
	 * Bookmarks recipe.
	 * @param username user's username
	 * @param recipeId recipe ID
	 * @param add <em>true</em> to add a bookmark or <em>false</em> to remove it
	 */
	void bookmarkRecipe(String username, long recipeId, boolean add);

	/** 
	 * Adds comment to recipe.
	 * @param username user's username
	 * @param recipeId recipe ID
	 * @param content comment body
	 */
	void commentRecipe(String username, long recipeId, String content);
}

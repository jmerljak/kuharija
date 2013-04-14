package si.merljak.magistrska.common.rpc;

import si.merljak.magistrska.common.dto.SessionDto;
import si.merljak.magistrska.common.dto.UserDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {

	void register(String username, String password, String name, String email, AsyncCallback<SessionDto> callback);

	void login(String username, String attemptedPassword, AsyncCallback<SessionDto> callback);

	void checkSession(String sessionId, AsyncCallback<UserDto> callback);

	void logout(String sessionId, AsyncCallback<Void> callback);
	
	void bookmarkRecipe(String username, long recipeId, boolean add, AsyncCallback<Void> callback);

	void commentRecipe(String username, long recipeId, String content, AsyncCallback<Void> callback);

}

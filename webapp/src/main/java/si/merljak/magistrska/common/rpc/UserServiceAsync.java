package si.merljak.magistrska.common.rpc;

import si.merljak.magistrska.common.dto.UserDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface UserServiceAsync {

	void bookmarkRecipe(String username, long recipeId, boolean add, AsyncCallback<Void> callback);

	void commentRecipe(String username, long recipeId, String content, AsyncCallback<Void> callback);

	void login(String username, String password, AsyncCallback<UserDto> callback);

	void register(String username, String password, String name, String email, AsyncCallback<Void> callback);

}

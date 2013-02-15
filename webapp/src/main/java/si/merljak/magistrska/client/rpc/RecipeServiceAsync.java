package si.merljak.magistrska.client.rpc;

import si.merljak.magistrska.dto.RecipeDto;
import si.merljak.magistrska.enumeration.Language;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RecipeServiceAsync {

	void getRecipe(long recipeId, Language language, AsyncCallback<RecipeDto> callback);
}

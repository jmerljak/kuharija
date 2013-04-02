package si.merljak.magistrska.common.rpc;

import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.enumeration.Language;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RecipeServiceAsync {

	void getRecipeDetails(long recipeId, Language language, AsyncCallback<RecipeDetailsDto> callback);
}

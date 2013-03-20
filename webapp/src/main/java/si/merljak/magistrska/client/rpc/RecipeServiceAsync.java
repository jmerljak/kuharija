package si.merljak.magistrska.client.rpc;

import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.StepDto;
import si.merljak.magistrska.common.enumeration.Language;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RecipeServiceAsync {

	void getRecipe(long recipeId, Language language, AsyncCallback<RecipeDto> callback);

	void getStep(long recipeId, Language language, int page, AsyncCallback<StepDto> callback);
}

package si.merljak.magistrska.common.rpc;

import java.util.List;
import java.util.Set;

import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.enumeration.Language;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RecipeServiceAsync {

	void getRecipeDetails(long recipeId, Language language, String username, AsyncCallback<RecipeDetailsDto> callback);

	void getRecipes(Set<Long> recipeIdList, Language language, AsyncCallback<List<RecipeDetailsDto>> callback);
}

package si.merljak.magistrska.common.rpc;

import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.enumeration.Language;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("recipe")
public interface RecipeService extends RemoteService {
	RecipeDetailsDto getRecipeDetails(long recipeId, Language language);
}

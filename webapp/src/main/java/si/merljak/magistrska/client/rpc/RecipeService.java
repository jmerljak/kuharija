package si.merljak.magistrska.client.rpc;

import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.enumeration.Language;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("recipe")
public interface RecipeService extends RemoteService {
	RecipeDto getRecipe(long recipeId, Language language);
}

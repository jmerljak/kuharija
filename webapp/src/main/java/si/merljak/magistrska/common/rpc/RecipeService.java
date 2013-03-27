package si.merljak.magistrska.common.rpc;

import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.StepDto;
import si.merljak.magistrska.common.enumeration.Language;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("recipe")
public interface RecipeService extends RemoteService {
	RecipeDto getRecipe(long recipeId, Language language);
	StepDto getStep(long recipeId, Language language, int page);
}

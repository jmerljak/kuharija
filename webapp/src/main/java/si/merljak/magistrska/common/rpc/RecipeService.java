package si.merljak.magistrska.common.rpc;

import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.dto.StepDto;
import si.merljak.magistrska.common.enumeration.Language;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("recipe")
public interface RecipeService extends RemoteService {
	RecipeDetailsDto getRecipe(long recipeId, Language language);
	StepDto getStep(long recipeId, Language language, int page);
}

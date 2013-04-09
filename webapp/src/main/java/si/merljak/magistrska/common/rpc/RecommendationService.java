package si.merljak.magistrska.common.rpc;

import java.util.List;

import si.merljak.magistrska.common.dto.RecipeDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("recommend")
public interface RecommendationService extends RemoteService {

	List<RecipeDto> recommendRecipesForUser();

	List<RecipeDto> recommendSimmilarRecipes(long recipeId);

	String geolocate(double latitude, double longitude);
}

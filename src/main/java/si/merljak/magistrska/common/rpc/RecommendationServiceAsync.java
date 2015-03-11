package si.merljak.magistrska.common.rpc;

import java.util.List;

import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.RecommendationsDto;
import si.merljak.magistrska.common.enumeration.Language;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Jakob Merljak
 */
public interface RecommendationServiceAsync {

	void recommendRecipes(String username, Double latitude, Double longitude, Language language, AsyncCallback<RecommendationsDto> callback);

	void recommendSimmilarRecipes(long recipeId, String username, AsyncCallback<List<RecipeDto>> callback);


}

package si.merljak.magistrska.common.rpc;

import java.util.List;

import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.RecommendationsDto;
import si.merljak.magistrska.common.enumeration.Language;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("recommend")
public interface RecommendationService extends RemoteService {
	
	/**
	 * Recommends recipes for user based on his/her habits and preferences, 
	 * ingredients in his/her smart refrigerator, current season, local time of day,
	 * list of featured recipes etc.
	 * 
	 * @param username user unique name
	 * @param latitude current user's latitude
	 * @param longitude current user's longitude
	 * @param language language
	 * 
	 * @return list of recommendations with explanation
	 */
	RecommendationsDto recommendRecipes(String username, Double latitude, Double longitude, Language language);

	/**
	 * Recommends recipes similar to shown one.
	 * 
	 * @param recipeId shown recipe's id
	 * @param username user unique name (optional)
	 * 
	 * @return list of recommended similar recipes
	 */
	List<RecipeDto> recommendSimmilarRecipes(long recipeId, String username);
}

package si.merljak.magistrska.common.rpc;

import java.util.List;

import si.merljak.magistrska.common.dto.RecipeDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface RecommendationServiceAsync {

	void recommendRecipesForUser(AsyncCallback<List<RecipeDto>> callback);
	
	void recommendSimmilarRecipes(long recipeId, AsyncCallback<List<RecipeDto>> callback);
	
	void geolocate(double latitude, double longitude, AsyncCallback<String> callback);

}

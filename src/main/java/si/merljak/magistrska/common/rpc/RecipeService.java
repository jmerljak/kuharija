package si.merljak.magistrska.common.rpc;

import java.util.List;
import java.util.Set;

import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.enumeration.Language;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("recipe")
public interface RecipeService extends RemoteService {
	/**
	 * Gets single recipe.
	 * 
	 * @param recipeId recipe ID
	 * @param language language
	 * @param username logged in user's name or {@code null} if no user is logged in 
	 * @return recipe details or {@code null} if recipe does not exist or has no details for given language
	 */
	RecipeDetailsDto getRecipeDetails(long recipeId, Language language, String username);

	/**
	 * Gets multiple recipes.
	 * @param recipeIdList list of recipe IDs
	 * @param language language
	 * @return list of recipes
	 */
	List<RecipeDetailsDto> getRecipes(Set<Long> recipeIdList, Language language);

	/** 
	 * Bookmarks recipe.
	 * @param username user's username
	 * @param recipeId recipe ID
	 * @param add <em>true</em> to add a bookmark or <em>false</em> to remove it
	 */
	void bookmarkRecipe(String username, long recipeId, boolean add);

	/** 
	 * Adds comment to recipe.
	 * @param username user's username
	 * @param recipeId recipe ID
	 * @param content comment body
	 */
	void commentRecipe(String username, long recipeId, String content);
}

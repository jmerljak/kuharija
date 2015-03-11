package si.merljak.magistrska.common.rpc;

import si.merljak.magistrska.common.SearchParameters;
import si.merljak.magistrska.common.dto.RecipeListDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author Jakob Merljak
 */
@RemoteServiceRelativePath("search")
public interface SearchService extends RemoteService {

	/**
	 * Searches for recipes.
	 * 
	 * @param searchParameters search parameters
	 * @return list of recipes (a page) and number of all results
	 */
	RecipeListDto search(SearchParameters searchParameters);
}

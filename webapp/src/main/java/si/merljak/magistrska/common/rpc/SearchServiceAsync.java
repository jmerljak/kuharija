package si.merljak.magistrska.common.rpc;

import si.merljak.magistrska.common.SearchParameters;
import si.merljak.magistrska.common.dto.RecipeListDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SearchServiceAsync {

	void search(SearchParameters searchParameters, AsyncCallback<RecipeListDto> callback);

}

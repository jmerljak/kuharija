package si.merljak.magistrska.common.rpc;

import java.util.List;

import si.merljak.magistrska.common.SearchParameters;
import si.merljak.magistrska.common.dto.RecipeBasicDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SearchServiceAsync {

	void search(SearchParameters searchParameters, AsyncCallback<List<RecipeBasicDto>> callback);

}

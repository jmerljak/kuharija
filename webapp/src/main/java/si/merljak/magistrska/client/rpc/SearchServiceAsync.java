package si.merljak.magistrska.client.rpc;

import java.util.List;

import si.merljak.magistrska.common.SearchParameters;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SearchServiceAsync {

	void search(SearchParameters searchParameters, AsyncCallback<List<Long>> callback);

}

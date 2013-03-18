package si.merljak.magistrska.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SearchServiceAsync {

	void basicSearch(String searchString, int page, int pageSize, AsyncCallback<List<String>> callback);

}

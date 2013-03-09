package si.merljak.magistrska.client.rpc;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SearchServiceAsync {

	void basicSearch(String searchString, int page, int pageSize, AsyncCallback<Void> callback);

}

package si.merljak.magistrska.common.rpc.mock;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface WOzServiceAsync {

	void getActions(AsyncCallback<List<String>> callback);

	void setAction(String action, AsyncCallback<Void> callback);

}

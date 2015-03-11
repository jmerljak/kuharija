package si.merljak.magistrska.common.rpc.mock;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Jakob Merljak
 */
public interface WOzServiceAsync {

	void getActions(AsyncCallback<List<String>> callback);

	void setAction(String action, AsyncCallback<Void> callback);

}

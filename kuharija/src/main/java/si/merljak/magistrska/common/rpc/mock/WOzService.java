package si.merljak.magistrska.common.rpc.mock;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("woz")
public interface WOzService extends RemoteService {

	/**
	 * Gets requested actions from a "wizard".
	 * 
	 * @return actions to perform or {@code null} if there are no action to perform
	 */
	List<String> getActions();

	/**
	 * Sets an action by a "wizard".
	 * 
	 * @param action
	 */
	void setAction(String action);
}

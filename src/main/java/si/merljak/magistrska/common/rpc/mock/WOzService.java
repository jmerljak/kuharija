package si.merljak.magistrska.common.rpc.mock;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * Remote control interface.
 * See article about <a href="https://en.wikipedia.org/wiki/Wizard_of_Oz_experiment">Wizard of Oz Experiment</a>.
 *  
 * @author Jakob Merljak
 */
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

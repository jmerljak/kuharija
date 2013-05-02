package si.merljak.magistrska.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface LoginEventHandler extends EventHandler {
	/** 
	 * Registers user login or logout.
	 * 
	 * @param event login/logout event
	 */
	void onLogin(LoginEvent event);	
}

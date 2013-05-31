package si.merljak.magistrska.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * 
 * @author Jakob Merljak
 * 
 */
public interface ActionEventHandler extends EventHandler {
	/**
	 * Responds to action events.
	 * 
	 * @param event action event
	 */
	void onAction(ActionEvent event);
}

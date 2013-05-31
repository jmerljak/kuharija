package si.merljak.magistrska.client.event;

import java.util.List;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Action event.
 * 
 * @author Jakob Merljak
 * 
 */
public class ActionEvent extends GwtEvent<ActionEventHandler> {

	public static Type<ActionEventHandler> TYPE = new Type<ActionEventHandler>();

	private List<String> actions;

	/**
	 * Action event.
	 * 
	 * @param actions list of actions to perform
	 */
	public ActionEvent(List<String> actions) {
		super();
		this.actions = actions;
	}

	@Override
	public Type<ActionEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(ActionEventHandler handler) {
		handler.onAction(this);
	}

	public List<String> getActions() {
		return actions;
	}
}

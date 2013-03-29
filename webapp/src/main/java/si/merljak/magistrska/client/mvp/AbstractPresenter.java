package si.merljak.magistrska.client.mvp;

import java.util.Map;

import si.merljak.magistrska.common.enumeration.Language;

/** 
 * This is the abstract class that serves as a base for all presenters.
 * It defines some abstract methods every presenter have to implement.
 * 
 * @author Jakob Merljak
 *
 */
public abstract class AbstractPresenter {
	
	protected Language language;

	public AbstractPresenter(Language language) {
		this.language = language;
	}

	/**
	 * Handles screen change.
	 * @param screenName name of the screen
	 * @param parameters parameters
	 */
	public void handleScreenChange(String screenName, Map<String, String> parameters) {
		if (isPresenterForScreen(screenName)) {
			parseParameters(screenName, parameters);
		} else {
			hideView();
		}
	}

	/** 
	 * Checks if presenter is handler for screen.
	 * @param screenName name of the screen
	 * @return <em>true</em> if presenter is handler for given screen name, <em>false</em> otherwise
	 */
	protected abstract boolean isPresenterForScreen(String screenName);

	/** Parses parameters and activates view. */
	protected abstract void parseParameters(String screenName, Map<String, String> parameters);

	/** Hides view and all of its widgets. */ 
	protected abstract void hideView();
}

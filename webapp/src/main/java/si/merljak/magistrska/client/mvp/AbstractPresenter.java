package si.merljak.magistrska.client.mvp;

import java.util.Map;

import com.google.gwt.user.client.ui.Widget;

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

	/** Parses parameters and returns proper view. */
	public abstract Widget parseParameters(Map<String, String> parameters);
}

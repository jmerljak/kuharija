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

	/**
	 * Parses URL parameters and returns proper view.
	 * 
	 * @param parameters URL parameters
	 * @return proper view as a widget
	 */
	public abstract Widget parseParameters(Map<String, String> parameters);

	/**
	 * Gets name of the screen.
	 * 
	 * @return screen name
	 */
	public abstract String getScreenName();

	/**
	 * Gets name of the <em>parent</em> presenter. Needed for breadcrumbs building.
	 * 
	 * @return parent presenter name
	 */
	public abstract String getParentName();
}

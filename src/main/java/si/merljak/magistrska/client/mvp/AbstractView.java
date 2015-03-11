package si.merljak.magistrska.client.mvp;

import java.util.ArrayList;
import java.util.List;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.client.i18n.CommonMessages;

import com.google.common.base.Joiner;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

/**
 * This is the abstract class that serves as a base for all views.
 * It defines some common constants and convenience methods.
 * 
 * @author Jakob Merljak
 * 
 */
public abstract class AbstractView extends Composite {

	// string constants, messages & formatters
	protected static final int HEADING_SIZE = 1;
	protected static final CommonConstants constants = Kuharija.constants;
	protected static final CommonMessages messages = Kuharija.messages;

	// folders for static content
	public static final String IMG_BASE_FOLDER = GWT.getHostPageBaseURL() + "img/";
	public static final String INGREDIENT_IMG_FOLDER = GWT.getHostPageBaseURL() + "img/ingredient/";
	public static final String RECIPE_IMG_FOLDER = GWT.getHostPageBaseURL() + "img/recipe/";
	public static final String RECIPE_THUMB_IMG_FOLDER = GWT.getHostPageBaseURL() + "img/recipe/thumbs/";
	public static final String RECIPE_IMG_FALLBACK = "noimage.png";
	public static final String UTENSIL_IMG_FOLDER = GWT.getHostPageBaseURL() + "img/utensil/";

	/**
	 * Formats time/duration from number of minutes.
	 * 
	 * @param minutes duration in minutes
	 * @return time string, <em>e.g. '1 h 23 min', '3 h' or '45 min'</em>, or empty string if parameter is {@code null}
	 */
	protected static String timeFromMinutes(Integer minutes) {
		List<String> timeStrings = new ArrayList<String>();
		if (minutes != null) {
			int hours = minutes / 60;
			minutes = minutes % 60;
			if (hours > 0) {
				timeStrings.add(Integer.toString(hours));
				timeStrings.add(constants.timeHour());
			}
			if (minutes > 0) {
				timeStrings.add(Integer.toString(minutes));
				timeStrings.add(constants.timeMinute());
			}
		} else {
			timeStrings.add("-");
		}
		return Joiner.on(" ").join(timeStrings);
	}
}

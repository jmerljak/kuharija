package si.merljak.magistrska.client.mvp;

import java.util.Comparator;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.client.i18n.CommonMessages;
import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.enumeration.Season;
import si.merljak.magistrska.common.enumeration.Unit;

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
	protected static final String INGREDIENT_IMG_FOLDER = GWT.getHostPageBaseURL() + "img/ingredient/";
	protected static final String RECIPE_IMG_FOLDER = GWT.getHostPageBaseURL() + "img/recipe/";
	protected static final String RECIPE_THUMB_IMG_FOLDER = GWT.getHostPageBaseURL() + "img/recipe/";
	protected static final String RECIPE_IMG_FALLBACK = "../noimage.png";
	protected static final String UTENSIL_IMG_FOLDER = GWT.getHostPageBaseURL() + "img/utensil/";

	/** Returns localized name of category. */
	protected static String localizeEnum(Category category) {
		return constants.categoryMap().get(category.name());
	}

	/** Returns localized name of difficulty. */
	protected static String localizeEnum(Difficulty difficulty) {
		return constants.difficultyMap().get(difficulty.name());
	}

	/** Gets localized name of language. */
	protected static String localizeEnum(Language language) {
		return constants.languageMap().get(language.name());
	}

	/** Gets localized name of season. */
	protected static String localizeEnum(Season season) {
		return constants.seasonMap().get(season.name());
	}

	/** Gets localized name of unit. */
	protected static String localizeEnum(Unit unit) {
		return constants.unitMap().get(unit.name());
	}

	/**
	 * Formats time/duration from number of minutes.
	 * 
	 * @param minutes duration in minutes
	 * @return time string, <em>e.g. '1 h 23 min', '3 h' or '45 min'</em>, or empty string if parameter is {@code null}
	 */
	protected static String timeFromMinutes(Integer minutes) {
		String timeString = "";
		if (minutes != null) {
			int hours = minutes / 60;
			minutes = minutes % 60;
			if (hours > 0) {
				timeString += Integer.toString(hours) + " h ";
			}
			if (minutes > 0) {
				timeString += Integer.toString(minutes) + " min";
			}
		}
		return timeString;
	}

	/** Hides view and all of its widgets. */
	public void hide() {
		setVisible(false);
	}

	/** Locale sensitive string comparator */
	protected class LocaleSensitiveComparator implements Comparator<String> {
		public native int compare(String source, String target) /*-{
	    	return source.localeCompare(target);
		}-*/;
	}
}

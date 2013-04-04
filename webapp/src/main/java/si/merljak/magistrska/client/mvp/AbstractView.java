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

public abstract class AbstractView extends Composite {

	// common constants & formatters
	protected static final CommonConstants constants = Kuharija.constants;
	protected static final CommonMessages messages = Kuharija.messages;

	protected static final String INGREDIENT_IMG_FOLDER = GWT.getHostPageBaseURL() + "img/ingredient/";
	protected static final String RECIPE_IMG_FOLDER = GWT.getHostPageBaseURL() + "img/recipe/";

	protected static String localizeEnum(Category category) {
		return constants.categoryMap().get(category.name());
	}

	protected static String localizeEnum(Difficulty difficulty) {
		return constants.difficultyMap().get(difficulty.name());
	}

	protected static String localizeEnum(Language language) {
		return constants.languageMap().get(language.name());
	}

	protected static String localizeEnum(Season season) {
		return constants.seasonMap().get(season.name());
	}

	protected static String localizeEnum(Unit unit) {
		return constants.unitMap().get(unit.name());
	}

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

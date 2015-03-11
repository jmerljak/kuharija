package si.merljak.magistrska.client.utils;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.enumeration.Season;
import si.merljak.magistrska.common.enumeration.Unit;

/**
 * Utility class for enumerator localization and more.
 * 
 * @author Jakob Merljak
 *
 */
public final class EnumUtils {

	/** Returns localized name of category. */
	public static String localizeEnum(Category category) {
		return Kuharija.constants.categoryMap().get(category.name());
	}

	/** Returns localized name of difficulty. */
	public static String localizeEnum(Difficulty difficulty) {
		return Kuharija.constants.difficultyMap().get(difficulty.name());
	}

	/** Gets localized name of language. */
	public static String localizeEnum(Language language) {
		return Kuharija.constants.languageMap().get(language.name());
	}

	/** Gets localized name of season. */
	public static String localizeEnum(Season season) {
		return Kuharija.constants.seasonMap().get(season.name());
	}

	/** Gets localized name of unit. */
	public static String localizeEnum(Unit unit) {
		return Kuharija.constants.unitMap().get(unit.name());
	}

}

package si.merljak.magistrska.client.mvp;

import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.enumeration.Season;
import si.merljak.magistrska.common.enumeration.Unit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractView extends Composite {

	// common constants & formatters
	protected static final CommonConstants constants = GWT.create(CommonConstants.class);

	protected String localizeEnum(Category category) {
		return constants.categoryMap().get(category.name());
	}

	protected String localizeEnum(Difficulty difficulty) {
		return constants.difficultyMap().get(difficulty.name());
	}

	protected String localizeEnum(Language language) {
		return constants.languageMap().get(language.name());
	}

	protected String localizeEnum(Season season) {
		return constants.seasonMap().get(season.name());
	}

	protected String localizeEnum(Unit unit) {
		return constants.unitMap().get(unit.name());
	}

	public void hide() {
		setVisible(false);
	}
}

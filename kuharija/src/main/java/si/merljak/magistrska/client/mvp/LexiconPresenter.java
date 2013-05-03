package si.merljak.magistrska.client.mvp;

import java.util.Map;

import si.merljak.magistrska.common.enumeration.Language;

import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

public class LexiconPresenter extends AbstractPresenter {

	// screen name
	public static final String SCREEN_NAME = "lexicon";

	public LexiconPresenter(Language language) {
		super(language);
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		return new SimplePanel();
	}

	@Override
	public String getScreenName() {
		return SCREEN_NAME;
	}

	@Override
	public String getParentName() {
		return HomePresenter.SCREEN_NAME;
	}
}

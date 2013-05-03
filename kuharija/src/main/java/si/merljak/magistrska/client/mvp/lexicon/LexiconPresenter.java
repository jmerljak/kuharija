package si.merljak.magistrska.client.mvp.lexicon;

import java.util.Map;

import si.merljak.magistrska.client.mvp.AbstractPresenter;
import si.merljak.magistrska.client.mvp.home.HomePresenter;
import si.merljak.magistrska.common.enumeration.Language;

import com.google.gwt.user.client.ui.Widget;

/**
 * Lexicon index presenter.
 * 
 * @author Jakob Merljak
 * 
 */
public class LexiconPresenter extends AbstractPresenter {

	// screen name
	public static final String SCREEN_NAME = "lexicon";

	// view
	private LexiconView view = new LexiconView();

	public LexiconPresenter(Language language) {
		super(language);
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		return view.asWidget();
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

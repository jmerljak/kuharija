package si.merljak.magistrska.client.mvp.utensil;

import java.util.Map;

import si.merljak.magistrska.client.mvp.AbstractPresenter;
import si.merljak.magistrska.client.mvp.LexiconPresenter;
import si.merljak.magistrska.common.enumeration.Language;

import com.google.gwt.user.client.ui.Widget;

/**
 * Presenter for utensils index and details views.
 * 
 * @author Jakob Merljak
 * 
 */
public class UtensilIndexPresenter extends AbstractPresenter {

	// screen and parameter name
	public static final String SCREEN_NAME = "utensils";

	// views
	private final UtensilIndexView indexView = new UtensilIndexView();

	public UtensilIndexPresenter(Language language) {
		super(language);
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		return indexView.asWidget();
	}

	@Override
	public String getScreenName() {
		return SCREEN_NAME;
	}

	@Override
	public String getParentName() {
		return LexiconPresenter.SCREEN_NAME;
	}
}

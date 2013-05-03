package si.merljak.magistrska.client.mvp.error;

import java.util.Map;

import si.merljak.magistrska.client.mvp.AbstractPresenter;
import si.merljak.magistrska.client.mvp.home.HomePresenter;
import si.merljak.magistrska.common.enumeration.Language;

import com.google.gwt.user.client.ui.Widget;

/**
 * 404 page presenter.
 * 
 * @author Jakob Merljak
 * 
 */
public class NotFoundPresenter extends AbstractPresenter {

	// screen name
	public static final String SCREEN_NAME = "404";

	// view
	private final NotFoundView view = new NotFoundView();

	public NotFoundPresenter(Language language) {
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

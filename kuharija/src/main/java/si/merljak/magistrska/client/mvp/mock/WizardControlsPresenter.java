package si.merljak.magistrska.client.mvp.mock;

import java.util.Map;

import si.merljak.magistrska.client.mvp.AbstractPresenter;
import si.merljak.magistrska.client.mvp.home.HomePresenter;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.mock.WOzServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 * Wizard controls presenter.
 * 
 * @author Jakob Merljak
 * 
 */
public class WizardControlsPresenter extends AbstractPresenter {

	// screen name
	public static final String SCREEN_NAME = "wizard";

	// remote service
	private final WOzServiceAsync wozService;

	// view
	private WizardControlsView view = new WizardControlsView();

	public WizardControlsPresenter(Language language, WOzServiceAsync wozService) {
		super(language);
		this.wozService = wozService;
		view.setPresenter(this);
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		return view.asWidget();
	}

	public void setAction(String action) {
		wozService.setAction(action, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void nothing) {
				// do nothing
			}

			@Override
			public void onFailure(Throwable caught) {
				// ignore
			}
		});
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

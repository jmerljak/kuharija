package si.merljak.magistrska.client.mvp.mock;

import java.util.List;
import java.util.Map;

import si.merljak.magistrska.client.event.ActionEvent;
import si.merljak.magistrska.client.event.LoginEvent;
import si.merljak.magistrska.client.event.LoginEventHandler;
import si.merljak.magistrska.client.mvp.AbstractPresenter;
import si.merljak.magistrska.client.mvp.home.HomePresenter;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.mock.WOzService;
import si.merljak.magistrska.common.rpc.mock.WOzServiceAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 * Wizard controls presenter.
 * 
 * @author Jakob Merljak
 * 
 */
public class WizardControlsPresenter extends AbstractPresenter implements LoginEventHandler {

	// screen name
	public static final String SCREEN_NAME = "wizard";

	// remote service
	private final WOzServiceAsync wozService = GWT.create(WOzService.class);

	// event bus
	private final EventBus eventBus;

	// view
	private WizardControlsView view;

	private final Timer timer;

	public WizardControlsPresenter(Language language, EventBus eventBus) {
		super(language);
		this.eventBus = eventBus;

		view = new WizardControlsView();
		view.setPresenter(this);

		timer = new Timer() {
			@Override
			public void run() {
				getActions();
			}
		};

		eventBus.addHandler(LoginEvent.TYPE, this);
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		return view.asWidget();
	}

	private void getActions() {
		wozService.getActions(new AsyncCallback<List<String>>() {
			@Override
			public void onSuccess(List<String> actions) {
				if (actions != null && !actions.isEmpty()) {
					eventBus.fireEvent(new ActionEvent(actions));
					view.displayActions(actions);
				}
			}

			@Override
			public void onFailure(Throwable caught) {
				// do nothing
			}
		});
	}

	public void setAction(String action) {
		wozService.setAction(action, new AsyncCallback<Void>() {
			@Override
			public void onSuccess(Void nothing) {
				// do nothing
			}

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
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

	@Override
	public void onLogin(LoginEvent event) {
		if (event.getUser() != null) {
			timer.scheduleRepeating(2500);
		} else {
			timer.cancel();
		}
	}
}

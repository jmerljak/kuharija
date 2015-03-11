package si.merljak.magistrska.client.mvp.utensil;

import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.mvp.AbstractPresenter;
import si.merljak.magistrska.common.dto.UtensilDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.UtensilServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 * Presenter for utensil details view.
 * 
 * @author Jakob Merljak
 * 
 */
public class UtensilPresenter extends AbstractPresenter {

	// screen and parameter name
	public static final String SCREEN_NAME = "utensil";
	private static final String PARAMETER_UTENSIL = "name";

	// remote service
	private final UtensilServiceAsync utensilService;

	// views
	private final UtensilView detailsView = new UtensilView();

	public UtensilPresenter(Language language, UtensilServiceAsync utensilService) {
		super(language);
		this.utensilService = utensilService;
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		detailsView.setVisible(false);
		if (parameters.containsKey(PARAMETER_UTENSIL)) {
			getUtensil(parameters.get(PARAMETER_UTENSIL));
		} else {
			detailsView.displayUtensil(null);
			detailsView.setVisible(true);
		}
		return detailsView.asWidget();
	}

	/** Gets utensil by name. */
	private void getUtensil(String name) {
		utensilService.getUtensil(name, new AsyncCallback<UtensilDto>() {
			@Override
			public void onSuccess(UtensilDto utensil) {
				detailsView.displayUtensil(utensil);
				detailsView.setVisible(true);
			}
	
			@Override
			public void onFailure(Throwable caught) {
				Kuharija.handleException(caught);
			}
		});
	}

	/**
	 * Builds proper anchor URL for utensil.
	 * 
	 * @param name utensil's name
	 * @return anchor URL
	 */
	public static String buildUtensilUrl(String name) {
		return "#" + SCREEN_NAME +
			   "&" + PARAMETER_UTENSIL + "=" + name.toLowerCase();
	}

	@Override
	public String getScreenName() {
		return SCREEN_NAME;
	}

	@Override
	public String getParentName() {
		return UtensilIndexPresenter.SCREEN_NAME;
	}
}

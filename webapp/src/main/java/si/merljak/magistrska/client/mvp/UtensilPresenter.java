package si.merljak.magistrska.client.mvp;

import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.common.dto.UtensilDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.IngredientServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 * Presenter for utensils index and details views.
 * 
 * @author Jakob Merljak
 * 
 */
public class UtensilPresenter extends AbstractPresenter {

	// screen and parameter name
	public static final String SCREEN_NAME = "utensils";
	private static final String PARAMETER_UTENSIL = "name";

	// remote service
	private IngredientServiceAsync ingredientService;

	// views
	private UtensilIndexView indexView = new UtensilIndexView();
	private UtensilDetailsView detailsView = new UtensilDetailsView();

	public UtensilPresenter(Language language, IngredientServiceAsync ingredientService) {
		super(language);
		this.ingredientService = ingredientService;
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		if (parameters.containsKey(PARAMETER_UTENSIL)) {
			getUtensil(parameters.get(PARAMETER_UTENSIL));
			return detailsView.asWidget();
		} else {
			return indexView.asWidget();
		}
	}

	/** Gets utensil by name. */
	private void getUtensil(String name) {
		detailsView.setVisible(false);
		ingredientService.getUtensil(name, new AsyncCallback<UtensilDto>() {
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
}

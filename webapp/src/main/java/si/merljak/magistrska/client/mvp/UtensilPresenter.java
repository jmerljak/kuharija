package si.merljak.magistrska.client.mvp;

import java.util.Map;

import si.merljak.magistrska.common.dto.UtensilDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.IngredientServiceAsync;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class UtensilPresenter extends AbstractPresenter {

	// screen and parameter name
	public static final String SCREEN_NAME = "utensils";
	public static final String PARAMETER_UTENSIL = "name";

	// remote service
	private IngredientServiceAsync ingredientService;

	// view
    private UtensilView utensilView = new UtensilView();

	public UtensilPresenter(Language language, IngredientServiceAsync ingredientService) {
		super(language);
		this.ingredientService = ingredientService;
	}
	
	@Override
	protected boolean isPresenterForScreen(String screenName) {
		return SCREEN_NAME.equalsIgnoreCase(screenName);
	}

	@Override
	protected void parseParameters(String screenName, Map<String, String> parameters) {
		if (parameters.containsKey(PARAMETER_UTENSIL)) {
			getUtensil(parameters.get(PARAMETER_UTENSIL));
		} else {
			utensilView.displayUtensilsIndex();
		}
	}

	private void getUtensil(final String name) {
		ingredientService.getUtensil(name, new AsyncCallback<UtensilDto>() {
			@Override
			public void onSuccess(UtensilDto utensil) {
				utensilView.displayUtensil(utensil, name);
			}
	
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	@Override
	protected void hideView() {
		utensilView.hide();
	}

	/**
	 * Builds proper history token for utensil.
	 * @param name utensil's name
	 * @return history token
	 */
	public static String buildUtensilUrl(String name) {
		return "#" + SCREEN_NAME +
			   "&" + PARAMETER_UTENSIL + "=" + name.toLowerCase();
	}
}

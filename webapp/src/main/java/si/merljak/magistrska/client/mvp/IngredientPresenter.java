package si.merljak.magistrska.client.mvp;

import java.util.Map;

import si.merljak.magistrska.common.dto.IngredientDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.IngredientServiceAsync;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class IngredientPresenter extends AbstractPresenter {

	// screen and parameter name
	public static final String SCREEN_NAME = "ingredients";
	public static final String PARAMETER_INGREDIENT = "name";

	// remote service
	private IngredientServiceAsync ingredientService;

	// view
    private IngredientView ingredientView = new IngredientView();

	public IngredientPresenter(Language language, IngredientServiceAsync ingredientService) {
		super(language);
		this.ingredientService = ingredientService;
	}
	
	@Override
	protected boolean isPresenterForScreen(String screenName) {
		return SCREEN_NAME.equalsIgnoreCase(screenName);
	}

	@Override
	protected void parseParameters(String screenName, Map<String, String> parameters) {
		if (parameters.containsKey(PARAMETER_INGREDIENT)) {
			getIngredient(parameters.get(PARAMETER_INGREDIENT));
		} else {
			ingredientView.displayIngredientsIndex();
		}
	}

	private void getIngredient(final String name) {
		ingredientService.getIngredient(name, new AsyncCallback<IngredientDto>() {
			@Override
			public void onSuccess(IngredientDto ingredient) {
				ingredientView.displayIngredient(ingredient, name);
			}
	
			@Override
			public void onFailure(Throwable caught) {
				Window.alert(caught.getMessage());
			}
		});
	}

	@Override
	protected void hideView() {
		ingredientView.hide();
	}

	/**
	 * Builds proper anchor URL for ingredient.
	 * @param name ingredient's name
	 * @return anchor URL
	 */
	public static String buildIngredientUrl(String name) {
		return "#" + SCREEN_NAME +
			   "&" + PARAMETER_INGREDIENT + "=" + name.toLowerCase();
	}
}

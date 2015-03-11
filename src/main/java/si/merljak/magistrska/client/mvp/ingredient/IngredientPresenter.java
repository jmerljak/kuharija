package si.merljak.magistrska.client.mvp.ingredient;

import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.mvp.AbstractPresenter;
import si.merljak.magistrska.common.dto.IngredientDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.IngredientServiceAsync;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;

/**
 * Presenter for ingredient details view.
 * 
 * @author Jakob Merljak
 * 
 */
public class IngredientPresenter extends AbstractPresenter {

	// screen and parameter name
	public static final String SCREEN_NAME = "ingredient";
	private static final String PARAMETER_INGREDIENT = "name";

	// remote service
	private final IngredientServiceAsync ingredientService;

	// view
	private final IngredientView detailsView = new IngredientView();

	public IngredientPresenter(Language language, IngredientServiceAsync ingredientService) {
		super(language);
		this.ingredientService = ingredientService;
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		detailsView.setVisible(false);
		if (parameters.containsKey(PARAMETER_INGREDIENT)) {
			getIngredient(parameters.get(PARAMETER_INGREDIENT));
		} else {
			detailsView.displayIngredient(null);
			detailsView.setVisible(true);
		}
		return detailsView.asWidget();
	}

	/** Gets ingredient by name. */
	private void getIngredient(String name) {
		ingredientService.getIngredient(name, new AsyncCallback<IngredientDto>() {
			@Override
			public void onSuccess(IngredientDto ingredient) {
				detailsView.displayIngredient(ingredient);
				detailsView.setVisible(true);
			}

			@Override
			public void onFailure(Throwable caught) {
				Kuharija.handleException(caught);
			}
		});
	}

	/**
	 * Builds proper anchor URL for ingredient.
	 * 
	 * @param name ingredient's name
	 * @return anchor URL
	 */
	public static String buildIngredientUrl(String name) {
		return "#" + SCREEN_NAME +
			   "&" + PARAMETER_INGREDIENT + "=" + name.toLowerCase();
	}

	@Override
	public String getScreenName() {
		return SCREEN_NAME;
	}

	@Override
	public String getParentName() {
		return IngredientIndexPresenter.SCREEN_NAME;
	}
}

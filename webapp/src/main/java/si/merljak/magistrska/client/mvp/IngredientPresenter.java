package si.merljak.magistrska.client.mvp;

import java.util.Map;

import si.merljak.magistrska.client.KuharijaEntry;
import si.merljak.magistrska.common.dto.IngredientDto;
import si.merljak.magistrska.common.enumeration.Language;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class IngredientPresenter extends AbstractPresenter {

    private IngredientView ingredientView = new IngredientView();

	public IngredientPresenter(Language language) {
		super(language);
	}
	
	@Override
	protected boolean isPresenterForScreen(String screenName) {
		return screenName != null && screenName.equalsIgnoreCase("ingredients");
	}

	@Override
	protected void parseParameters(String screenName, Map<String, String> parameters) {
		if (parameters.containsKey("name")) {
			getIngredient(parameters.get("name").toUpperCase());
		} else {
			ingredientView.displayIngredientsIndex();
		}
	}

	private void getIngredient(final String name) {
		KuharijaEntry.ingredientService.getIngredient(name, new AsyncCallback<IngredientDto>() {
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

}

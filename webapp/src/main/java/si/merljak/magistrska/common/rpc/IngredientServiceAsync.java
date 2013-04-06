package si.merljak.magistrska.common.rpc;

import si.merljak.magistrska.common.dto.IngredientDto;
import si.merljak.magistrska.common.dto.UtensilDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IngredientServiceAsync {
	void getIngredient(String name, AsyncCallback<IngredientDto> callback);

	void getUtensil(String name, AsyncCallback<UtensilDto> callback);
}

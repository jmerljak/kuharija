package si.merljak.magistrska.client.rpc;

import si.merljak.magistrska.common.dto.IngredientDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IngredientServiceAsync {
	void getIngredient(String name, AsyncCallback<IngredientDto> callback);
}

package si.merljak.magistrska.common.rpc;

import si.merljak.magistrska.common.dto.IngredientDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Jakob Merljak
 */
public interface IngredientServiceAsync {
	void getIngredient(String name, AsyncCallback<IngredientDto> callback);
}

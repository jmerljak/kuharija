package si.merljak.magistrska.client.rpc;

import si.merljak.magistrska.common.dto.IngredientDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ingredient")
public interface IngredientService extends RemoteService {
	/**
	 * Gets single ingredient details.
	 * 
	 * @param name ingredient name
	 * @return single ingredient DTO or <em>null</em> if ingredient not found.
	 */
	IngredientDto getIngredient(String name);
}

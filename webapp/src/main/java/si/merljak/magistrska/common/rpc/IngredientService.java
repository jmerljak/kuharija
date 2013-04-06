package si.merljak.magistrska.common.rpc;

import si.merljak.magistrska.common.dto.IngredientDto;
import si.merljak.magistrska.common.dto.UtensilDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("ingredient")
public interface IngredientService extends RemoteService {

	/**
	 * Gets single ingredient details.
	 * @param name ingredient's name
	 * @return single ingredient DTO or <em>null</em> if ingredient not found
	 */
	IngredientDto getIngredient(String name);

	/**
	 * Gets single utensil details.
	 * @param name utensil's name
	 * @return single ingredient DTO or <em>null</em> if ingredient not found
	 */
	UtensilDto getUtensil(String name);
}

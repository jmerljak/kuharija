package si.merljak.magistrska.common.rpc;

import si.merljak.magistrska.common.dto.UtensilDto;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * @author Jakob Merljak
 */
@RemoteServiceRelativePath("utensil")
public interface UtensilService extends RemoteService {

	/**
	 * Gets single utensil details.
	 * @param name utensil's name
	 * @return single utensil DTO or <em>null</em> if utensil not found
	 */
	UtensilDto getUtensil(String name);
}

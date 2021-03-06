package si.merljak.magistrska.common.rpc;

import si.merljak.magistrska.common.dto.UtensilDto;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * @author Jakob Merljak
 */
public interface UtensilServiceAsync {
	void getUtensil(String name, AsyncCallback<UtensilDto> callback);
}

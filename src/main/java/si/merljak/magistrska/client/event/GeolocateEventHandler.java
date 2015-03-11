package si.merljak.magistrska.client.event;

import com.google.gwt.event.shared.EventHandler;

/**
 * 
 * @author Jakob Merljak
 *
 */
public interface GeolocateEventHandler extends EventHandler {
	/**
	 * Registers user geolocation.
	 * 
	 * @param event geolocate event
	 */
	void onGeolocate(GeolocateEvent event);
}

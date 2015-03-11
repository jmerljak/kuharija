package si.merljak.magistrska.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.geolocation.client.Position.Coordinates;

/**
 * Geolocate event.
 * 
 * @author Jakob Merljak
 * 
 */
public class GeolocateEvent extends GwtEvent<GeolocateEventHandler> {

	public static Type<GeolocateEventHandler> TYPE = new Type<GeolocateEventHandler>();

	private Coordinates coordinates;

	/**
	 * Geolocate event.
	 * 
	 * @param coordinates user geolocation
	 */
	public GeolocateEvent(Coordinates coordinates) {
		super();
		this.coordinates = coordinates;
	}

	@Override
	public Type<GeolocateEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(GeolocateEventHandler handler) {
		handler.onGeolocate(this);
	}

	public Coordinates getCoordinates() {
		return coordinates;
	}
}

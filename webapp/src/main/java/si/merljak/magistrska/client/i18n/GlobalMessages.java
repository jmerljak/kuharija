package si.merljak.magistrska.client.i18n;

import com.google.gwt.i18n.client.Constants;

public interface GlobalMessages extends Constants {
	@DefaultStringValue("Unfortunatelly, your browser does not support HTML5 audio tag.")
	String htmlAudioNotSupported();

	@DefaultStringValue("Unfortunatelly, your browser does not support HTML5 video tag.")
	String htmlVideoNotSupported();
}

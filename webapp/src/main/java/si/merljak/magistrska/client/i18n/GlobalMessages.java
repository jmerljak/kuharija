package si.merljak.magistrska.client.i18n;

import com.google.gwt.i18n.client.Messages;

public interface GlobalMessages extends Messages {
	@DefaultMessage("Unfortunatelly, your browser does not support HTML5 audio tag.")
	String htmlAudioNotSupported();

	@DefaultMessage("Unfortunatelly, your browser does not support HTML5 video tag.")
	String htmlVideoNotSupported();
}

package si.merljak.magistrska.client.i18n;

import com.google.gwt.i18n.client.Messages;

public interface UrlConstants extends Messages {

	@DefaultMessage("http://en.wikipedia.org/w/index.php?search={0}")
	String localWikipediaSearchUrl(String query);
	
	// TODO localize internal urls?
}

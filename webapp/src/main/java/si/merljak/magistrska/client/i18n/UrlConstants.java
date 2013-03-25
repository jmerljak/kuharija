package si.merljak.magistrska.client.i18n;

import com.google.gwt.i18n.client.Constants;

public interface UrlConstants extends Constants {

	@DefaultStringValue("http://en.wikipedia.org/w/index.php?search=")
	String localWikipediaSearchUrl();
	
	// TODO localize internal urls?
}

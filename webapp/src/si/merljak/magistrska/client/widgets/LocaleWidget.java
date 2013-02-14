package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.enumeration.Language;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.i18n.client.LocaleInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.ListBox;

public class LocaleWidget extends ListBox {

	private String currentLocale;

	public LocaleWidget() {
		currentLocale = LocaleInfo.getCurrentLocale().getLocaleName();
		if (currentLocale.equals("default")) {
			currentLocale = "sl_SI";
		}

		for (String localeName : LocaleInfo.getAvailableLocaleNames()) {
			if (!localeName.equals("default")) {
				String nativeName = LocaleInfo.getLocaleNativeDisplayName(localeName);
				addItem(nativeName, localeName);
				if (localeName.equals(currentLocale)) {
					setSelectedIndex(getItemCount() - 1);
				}
			}
		}

		addChangeHandler(new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				String localeName = getValue(getSelectedIndex());
				UrlBuilder builder = Location.createUrlBuilder().setParameter("locale", localeName);
				Window.Location.replace(builder.buildString());
			}
		});
	}

	public Language getCurrentLanguage() {
		return Language.valueOf(currentLocale);
	}
}

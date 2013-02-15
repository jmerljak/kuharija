package si.merljak.magistrska.client.i18n;

import com.google.gwt.i18n.client.Constants;

public interface GlobalFormatters extends Constants {
	@DefaultStringValue("MM/dd/yyyy")
	String dateFormat();

	@DefaultStringValue("MM/dd/yyyy HH:mm:ss")
	String timestampFormat();

	@DefaultStringValue("#,##0.00")
	String decimalNumberFormat();

	@DefaultStringValue("#,##0.##")
	String numberFormat();
}

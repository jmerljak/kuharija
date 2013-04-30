package si.merljak.magistrska.client.i18n;

import com.google.gwt.i18n.client.Constants;

public interface Formatters extends Constants {
	@DefaultStringValue("MM/dd/yyyy")
	String dateFormat();

	@DefaultStringValue("MM/dd/yyyy hh:mm a")
	String timestampFormat();

	@DefaultStringValue("#,##0.00")
	String decimalNumberFormat();

	@DefaultStringValue("#,##0.##")
	String numberFormat();
}

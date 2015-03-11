package si.merljak.magistrska.client.utils;

import java.util.Comparator;

/**
 * Locale sensitive string comparator.
 * 
 * @author Jakob Merljak
 * 
 */
public class LocaleSensitiveComparator implements Comparator<String> {
	public native int compare(String source, String target) /*-{
		return source.localeCompare(target);
	}-*/;
}
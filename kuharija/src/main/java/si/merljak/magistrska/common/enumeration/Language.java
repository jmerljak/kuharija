package si.merljak.magistrska.common.enumeration;

import java.util.HashMap;
import java.util.Map;

public enum Language {
	SI, EN;

	private static Map<String, Language> enumMap = new HashMap<String, Language>();

	static {
		enumMap.put("sl_SI", SI);
		enumMap.put("en", EN);
		enumMap.put("en_GB", EN);
		enumMap.put("en_US", EN);
	}

	public static Language getEnum(String locale) {
		return enumMap.get(locale);
	}
}

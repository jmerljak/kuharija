package si.merljak.magistrska.common.dto;

import java.io.Serializable;

import si.merljak.magistrska.common.enumeration.Language;

public class SubtitleDto implements Serializable {

	private static final long serialVersionUID = 7637546509808846048L;

	private Language language;
	private String url;

	SubtitleDto() {}

	public SubtitleDto(Language language, String url) {
		this.language = language;
		this.url = url;
	}

	public Language getLanguage() {
		return language;
	}

	public String getUrl() {
		return url;
	}

}

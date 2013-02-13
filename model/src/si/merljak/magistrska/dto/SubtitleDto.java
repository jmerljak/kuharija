package si.merljak.magistrska.dto;

import java.io.Serializable;

import si.merljak.magistrska.enumeration.Language;

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

package si.merljak.magistrska.common.dto;

import java.io.Serializable;

import si.merljak.magistrska.common.enumeration.Language;

public class AudioDto implements Serializable {

	private static final long serialVersionUID = -2676648300958057389L;

	private Language language;
	private String url;

	AudioDto() {}

	public AudioDto(Language language, String url) {
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

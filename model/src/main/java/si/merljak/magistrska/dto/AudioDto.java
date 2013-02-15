package si.merljak.magistrska.dto;

import java.io.Serializable;

import si.merljak.magistrska.enumeration.Language;

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

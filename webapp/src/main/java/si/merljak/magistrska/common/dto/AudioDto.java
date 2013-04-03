package si.merljak.magistrska.common.dto;

import java.io.Serializable;
import java.util.List;

import com.mysema.query.annotations.QueryProjection;

import si.merljak.magistrska.common.enumeration.Language;

public class AudioDto implements Serializable {

	private static final long serialVersionUID = 6162966631238503867L;

	private Language language;
	private List<String> urls;

	AudioDto() {}

	@QueryProjection
	public AudioDto(Language language, List<String> urls) {
		this.language = language;
		this.urls = urls;
	}

	public Language getLanguage() {
		return language;
	}

	public List<String> getUrls() {
		return urls;
	}
}

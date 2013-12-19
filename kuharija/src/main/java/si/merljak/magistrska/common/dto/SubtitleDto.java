package si.merljak.magistrska.common.dto;

import java.io.Serializable;

import com.mysema.query.annotations.QueryProjection;

import si.merljak.magistrska.common.enumeration.Language;

/**
 * @author Jakob Merljak
 */
public class SubtitleDto implements Serializable {

	private static final long serialVersionUID = 7637546509808846048L;

	private Language language;
	private String url;

	SubtitleDto() {}

	@QueryProjection
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

package si.merljak.magistrska.common.dto;

import java.io.Serializable;

import com.mysema.query.annotations.QueryProjection;

import si.merljak.magistrska.common.enumeration.Language;

public class TextDto implements Serializable {

	private static final long serialVersionUID = -6696710834850758287L;

	private Language language;
	private String content;
	private String metadata;

	TextDto() {}

	@QueryProjection
	public TextDto(Language language, String content, String metadata) {
		this.language = language;
		this.content = content;
		this.metadata = metadata;
	}

	public Language getLanguage() {
		return language;
	}

	public String getContent() {
		return content;
	}

	public String getMetadata() {
		return metadata;
	}

}

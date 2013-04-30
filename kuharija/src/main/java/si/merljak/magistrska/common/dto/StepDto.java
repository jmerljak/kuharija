package si.merljak.magistrska.common.dto;

import java.io.Serializable;

import com.mysema.query.annotations.QueryProjection;

import si.merljak.magistrska.common.enumeration.Language;

public class StepDto implements Serializable {

	private static final long serialVersionUID = -5718046515778391341L;

	private Language language;
	private int page;
	private String content;
	private String imageUrl;

	StepDto() {}

	@QueryProjection
	public StepDto(Language language, int page, String content, String imageUrl) {
		this.language = language;
		this.page = page;
		this.content = content;
		this.imageUrl = imageUrl;
	}

	public Language getLanguage() {
		return language;
	}

	public int getPage() {
		return page;
	}

	public String getContent() {
		return content;
	}

	public String getImageUrl() {
		return imageUrl;
	}
}

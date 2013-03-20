package si.merljak.magistrska.common.dto;

import java.io.Serializable;

import si.merljak.magistrska.common.enumeration.AppendixType;
import si.merljak.magistrska.common.enumeration.Language;

public class AppendixDto implements Serializable {

	private static final long serialVersionUID = 4201466622978374724L;

	private AppendixType type;
	private Language language;
	private String content;

	AppendixDto() {}

	public AppendixDto(AppendixType type, Language language, String content) {
		this.type = type;
		this.language = language;
		this.content = content;
	}

	public AppendixType getType() {
		return type;
	}

	public Language getLanguage() {
		return language;
	}

	public String getContent() {
		return content;
	}

}

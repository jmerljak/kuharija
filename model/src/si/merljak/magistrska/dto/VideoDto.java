package si.merljak.magistrska.dto;

import java.io.Serializable;
import java.util.List;

import si.merljak.magistrska.enumeration.Language;

public class VideoDto implements Serializable {

	private static final long serialVersionUID = 26269998661464851L;

	private Language language;
	private String url;
	private List<SubtitleDto> subtitles;

	VideoDto() {}

	public VideoDto(Language language, String url, List<SubtitleDto> subtitles) {
		this.language = language;
		this.url = url;
		this.subtitles = subtitles;
	}

	public Language getLanguage() {
		return language;
	}

	public String getUrl() {
		return url;
	}

	public List<SubtitleDto> getSubtitles() {
		return subtitles;
	}

}

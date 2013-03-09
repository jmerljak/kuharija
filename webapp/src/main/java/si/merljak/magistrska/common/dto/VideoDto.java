package si.merljak.magistrska.common.dto;

import java.io.Serializable;
import java.util.List;

import si.merljak.magistrska.common.enumeration.Language;

public class VideoDto implements Serializable {

	private static final long serialVersionUID = 1055624611304299737L;

	private Language language;
	private List<String> urls;
	private String posterUrl;
	private List<SubtitleDto> subtitles;

	VideoDto() {}

	public VideoDto(Language language, List<String> urls, String posterUrl, List<SubtitleDto> subtitles) {
		this.language = language;
		this.urls = urls;
		this.posterUrl = posterUrl;
		this.subtitles = subtitles;
	}

	public Language getLanguage() {
		return language;
	}

	public List<String> getUrls() {
		return urls;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public List<SubtitleDto> getSubtitles() {
		return subtitles;
	}

}

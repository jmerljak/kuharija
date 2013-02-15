package si.merljak.magistrska.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import si.merljak.magistrska.enumeration.Difficulty;

public class TechniqueDto implements Serializable {

	private static final long serialVersionUID = 8774960896820340914L;

	private String title;
	private String imageUrl;
	private Difficulty difficulty;
	private String metaData;

	private List<ToolDto> tools = new ArrayList<ToolDto>();
	private List<TextDto> texts = new ArrayList<TextDto>();
	private List<AudioDto> audios = new ArrayList<AudioDto>();
	private List<VideoDto> videos = new ArrayList<VideoDto>();

	TechniqueDto() {}

	public TechniqueDto(String title, String imageUrl, Difficulty difficulty, String metaData) {
		this.title = title;
		this.imageUrl = imageUrl;
		this.difficulty = difficulty;
		this.metaData = metaData;
	}

	public String getTitle() {
		return title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public String getMetaData() {
		return metaData;
	}

	public void addTool(ToolDto toolDto) {
		tools.add(toolDto);
	}

	public List<ToolDto> getTools() {
		return tools;
	}

	public void addText(TextDto textDto) {
		texts.add(textDto);
	}

	public List<TextDto> getTexts() {
		return texts;
	}

	public void addAudio(AudioDto audioDto) {
		audios.add(audioDto);
	}

	public List<AudioDto> getAudios() {
		return audios;
	}

	public void addVideo(VideoDto videoDto) {
		videos.add(videoDto);
	}

	public List<VideoDto> getVideos() {
		return videos;
	}

}

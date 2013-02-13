package si.merljak.magistrska.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import si.merljak.magistrska.enumeration.Difficulty;

@Entity
public class Technique implements Serializable {

	private static final long serialVersionUID = -7582271413855061062L;

	@Id
	private long id;

	@NotNull
	private String title;

	private String imageUrl;

	@Enumerated(EnumType.STRING)
	private Difficulty difficulty;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "technique")
	private List<TechniqueTool> tools;

	@OneToMany(fetch=FetchType.LAZY, mappedBy = "technique")
	private List<TechniqueText> texts;

	@OneToMany(fetch=FetchType.LAZY, mappedBy = "technique")
	private List<TechniqueAudio> audios;

	@OneToMany(fetch=FetchType.LAZY, mappedBy = "technique")
	private List<TechniqueVideo> videos;

	private String metaData;

	Technique(long id, String title, String imageUrl, Difficulty difficulty,
			List<TechniqueTool> tools, List<TechniqueText> texts,
			List<TechniqueAudio> audios, List<TechniqueVideo> videos,
			String metaData) {
		this.id = id;
		this.title = title;
		this.imageUrl = imageUrl;
		this.difficulty = difficulty;
		this.tools = tools;
		this.texts = texts;
		this.audios = audios;
		this.videos = videos;
		this.metaData = metaData;
	}

	public long getId() {
		return id;
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

	public List<TechniqueTool> getTools() {
		return tools;
	}

	public List<TechniqueText> getTexts() {
		return texts;
	}

	public List<TechniqueAudio> getAudios() {
		return audios;
	}

	public List<TechniqueVideo> getVideos() {
		return videos;
	}

	public String getMetaData() {
		return metaData;
	}
}

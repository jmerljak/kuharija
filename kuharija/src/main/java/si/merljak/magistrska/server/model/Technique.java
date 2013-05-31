package si.merljak.magistrska.server.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import si.merljak.magistrska.common.enumeration.Difficulty;

@Entity
public class Technique implements Serializable {

	private static final long serialVersionUID = -7582271413855061062L;

	@Id
	@GeneratedValue
	private long id;

	private String imageUrl;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Difficulty difficulty;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "technique")
	private Set<TechniqueUtensil> utensils;

	@OneToMany(fetch=FetchType.LAZY, mappedBy = "technique")
	private Set<TechniqueText> texts;

	@OneToMany(fetch=FetchType.LAZY, mappedBy = "technique")
	private Set<TechniqueAudio> audios;

	@OneToMany(fetch=FetchType.LAZY, mappedBy = "technique")
	private Set<TechniqueVideo> videos;

	private String metaData;

	protected Technique() {}

	public Technique(long id, String imageUrl, Difficulty difficulty, String metaData) {
		this.imageUrl = imageUrl;
		this.difficulty = difficulty;
		this.metaData = metaData;
	}

	public long getId() {
		return id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public Set<TechniqueUtensil> getUtensils() {
		return utensils;
	}

	public Set<TechniqueText> getTexts() {
		return texts;
	}

	public Set<TechniqueAudio> getAudios() {
		return audios;
	}

	public Set<TechniqueVideo> getVideos() {
		return videos;
	}

	public String getMetaData() {
		return metaData;
	}
}

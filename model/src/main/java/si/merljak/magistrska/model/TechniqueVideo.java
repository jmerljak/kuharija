package si.merljak.magistrska.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import si.merljak.magistrska.enumeration.Language;

@Entity
@Table(name="technique_video")
public class TechniqueVideo implements Serializable {

	private static final long serialVersionUID = -5682080495739973438L;

	@Id
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Technique technique;

	@Enumerated(EnumType.STRING)
	private Language language;

	@NotNull
	private String url;

	@OneToMany(fetch = FetchType.EAGER, mappedBy="video")
	private List<TechniqueVideoSubtitle> subtitles;

	TechniqueVideo(long id, Technique technique, Language language, String url, List<TechniqueVideoSubtitle> subtitles) {
		this.id = id;
		this.technique = technique;
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

	public List<TechniqueVideoSubtitle> getSubtitles() {
		return subtitles;
	}
}

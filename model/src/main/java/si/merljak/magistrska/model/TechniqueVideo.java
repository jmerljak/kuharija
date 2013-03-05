package si.merljak.magistrska.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import si.merljak.magistrska.enumeration.Language;

@Entity
@Table(name="TECHNIQUE_VIDEO")
public class TechniqueVideo implements Serializable {

	private static final long serialVersionUID = -5682080495739973438L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Technique technique;

	@Enumerated(EnumType.STRING)
	private Language language;

	@Column(nullable = false)
	private String url;

	@OneToMany(fetch = FetchType.EAGER, mappedBy="video")
	private List<TechniqueVideoSubtitle> subtitles;

	protected TechniqueVideo() {}

	public TechniqueVideo(Technique technique, Language language, String url, List<TechniqueVideoSubtitle> subtitles) {
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

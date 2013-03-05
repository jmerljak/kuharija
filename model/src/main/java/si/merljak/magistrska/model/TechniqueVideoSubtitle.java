package si.merljak.magistrska.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import si.merljak.magistrska.enumeration.Language;

@Entity
@Table(name="TECHNIQUE_VIDEO_SUBTITLE")
public class TechniqueVideoSubtitle implements Serializable {

	private static final long serialVersionUID = -8942376618126769192L;

	@Id
	@GeneratedValue
	long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private TechniqueVideo video;

	@Enumerated(EnumType.STRING)
	private Language language;

	@NotNull
	private String url;

	protected TechniqueVideoSubtitle() {}

	public TechniqueVideoSubtitle(TechniqueVideo video, Language language, String url) {
		this.video = video;
		this.language = language;
		this.url = url;
	}

	public Language getLanguage() {
		return language;
	}

	public String getUrl() {
		return url;
	}
}

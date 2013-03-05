package si.merljak.magistrska.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import si.merljak.magistrska.enumeration.Language;

@Entity
@Table(name="TECHNIQUE_AUDIO")
public class TechniqueAudio implements Serializable {

	private static final long serialVersionUID = -7364839033724996916L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Technique technique;

	@Enumerated(EnumType.STRING)
	private Language language;

	@Column(nullable = false)
	private String url;

	protected TechniqueAudio() {}

	public TechniqueAudio(Technique technique, Language language, String url) {
		this.technique = technique;
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

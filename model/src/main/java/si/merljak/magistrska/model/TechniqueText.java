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
@Table(name="TECHNIQUE_TEXT")
public class TechniqueText implements Serializable {

	private static final long serialVersionUID = 4638433047831556247L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Technique technique;

	@Enumerated(EnumType.STRING)
	private Language language;

	@NotNull
	private String content;

	private String metadata;

	protected TechniqueText() {}

	public TechniqueText(Technique technique, Language language, String content, String metadata) {
		this.technique = technique;
		this.language = language;
		this.content = content;
		this.metadata = metadata;
	}

	public Language getLanguage() {
		return language;
	}

	public String getContent() {
		return content;
	}

	public String getMetadata() {
		return metadata;
	}
}

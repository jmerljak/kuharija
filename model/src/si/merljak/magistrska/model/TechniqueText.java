package si.merljak.magistrska.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import si.merljak.magistrska.enumeration.Language;

@Entity
@Table(name="technique_text")
public class TechniqueText implements Serializable {

	private static final long serialVersionUID = 4638433047831556247L;

	@Id
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Technique technique;

	@Enumerated(EnumType.STRING)
	private Language language;

	@NotNull
	private String content;

	private String metadata;

	TechniqueText(long id, Technique technique, Language language, String content, String metadata) {
		this.id = id;
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

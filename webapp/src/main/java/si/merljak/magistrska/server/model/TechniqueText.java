package si.merljak.magistrska.server.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import si.merljak.magistrska.common.enumeration.Language;

@Entity
@DiscriminatorValue("T")
public class TechniqueText extends Text implements Serializable {

	private static final long serialVersionUID = 4638433047831556247L;

	@ManyToOne(fetch = FetchType.LAZY)
	private Technique technique;

	protected TechniqueText() {}

	public TechniqueText(Technique technique, Language language, String content, String metadata) {
		this.technique = technique;
		this.language = language;
		this.content = content;
		this.metadata = metadata;
	}
}

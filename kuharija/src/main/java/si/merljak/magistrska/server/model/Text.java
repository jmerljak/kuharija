package si.merljak.magistrska.server.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import si.merljak.magistrska.common.enumeration.Language;

/**
 * @author Jakob Merljak
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Text {

	@Id
	@GeneratedValue
	protected long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	protected Language language;

	@NotNull
	@Size(max = 5000)
	protected String content;

	protected String metadata;

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

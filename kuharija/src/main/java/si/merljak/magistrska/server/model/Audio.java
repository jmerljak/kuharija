package si.merljak.magistrska.server.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotNull;

import si.merljak.magistrska.common.enumeration.Language;

/**
 * @author Jakob Merljak
 */
@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Audio {

	@Id
	@GeneratedValue
	protected long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	protected Language language;

	@NotNull
	protected String urls;

	public Language getLanguage() {
		return language;
	}

	public String getUrls() {
		return urls;
	}
}

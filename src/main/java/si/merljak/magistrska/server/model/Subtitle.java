package si.merljak.magistrska.server.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import si.merljak.magistrska.common.enumeration.Language;

/**
 * @author Jakob Merljak
 */
@Entity
public class Subtitle {

	@Id
	@GeneratedValue
	long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Language language;

	@NotNull
	private String url;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Video video;

	public Language getLanguage() {
		return language;
	}

	public String getUrl() {
		return url;
	}
}

package si.merljak.magistrska.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import si.merljak.magistrska.common.enumeration.Language;

/**
 * @author Jakob Merljak
 */
@Entity
@Table(name="technique_details")
public class TechniqueDetails extends Details implements Serializable {

	private static final long serialVersionUID = -6082944107450713488L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Technique technique;

	protected TechniqueDetails() {}

	public TechniqueDetails(Technique technique, Language language, String heading, String subHeading) {
		this.technique = technique;
		this.language = language;
		this.heading = heading;
		this.subHeading = subHeading;
	}
}

package si.merljak.magistrska.server.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import si.merljak.magistrska.common.enumeration.Language;

@Entity
@DiscriminatorValue("T")
public class TechniqueAudio extends Audio implements Serializable {

	private static final long serialVersionUID = -1356813581811535317L;

	@ManyToOne(fetch = FetchType.LAZY)
	private Technique technique;

	protected TechniqueAudio() {}

	public TechniqueAudio(Technique technique, Language language, String urls) {
		this.technique = technique;
		this.language = language;
		this.urls = urls;
	}
}

package si.merljak.magistrska.server.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import si.merljak.magistrska.common.enumeration.Language;

/**
 * @author Jakob Merljak
 */
@Entity
@DiscriminatorValue("T")
public class TechniqueVideo extends Video implements Serializable {

	private static final long serialVersionUID = -5682080495739973438L;

	@ManyToOne(fetch = FetchType.LAZY)
	private Technique technique;

	protected TechniqueVideo() {}

	public TechniqueVideo(Technique technique, Language language, String urls, Set<Subtitle> subtitles) {
		this.technique = technique;
		this.language = language;
		this.urls = urls;
		this.subtitles = subtitles;
	}
}

package si.merljak.magistrska.server.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import si.merljak.magistrska.common.enumeration.Language;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="dtype", discriminatorType=DiscriminatorType.STRING, length=1)
public abstract class Video {

	@Id
	@GeneratedValue
	protected long id;

	@NotNull
	@Enumerated(EnumType.STRING)
	protected Language language;

	@NotNull
	protected String urls;

	protected String posterUrl;

	@OneToMany(fetch = FetchType.EAGER, mappedBy="video")
	protected Set<Subtitle> subtitles;

	@Column(insertable=false, updatable=false)
	protected String dtype;

	public Language getLanguage() {
		return language;
	}

	public String getUrls() {
		return urls;
	}

	public String getPosterUrl() {
		return posterUrl;
	}

	public Set<Subtitle> getSubtitles() {
		return subtitles;
	}
}

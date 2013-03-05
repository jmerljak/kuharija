package si.merljak.magistrska.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import si.merljak.magistrska.enumeration.Language;

@Entity
@Table(name="RECIPE_VIDEO_SUBTITLE")
public class RecipeVideoSubtitle implements Serializable {

	private static final long serialVersionUID = 5296639775204100994L;

	@Id
	@GeneratedValue
	long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private RecipeVideo video;

	@Enumerated(EnumType.STRING)
	private Language language;

	@Column(nullable = false)
	private String url;

	protected RecipeVideoSubtitle() {}

	public RecipeVideoSubtitle(RecipeVideo video, Language language, String url) {
		this.video = video;
		this.language = language;
		this.url = url;
	}

	public Language getLanguage() {
		return language;
	}

	public String getUrl() {
		return url;
	}
}

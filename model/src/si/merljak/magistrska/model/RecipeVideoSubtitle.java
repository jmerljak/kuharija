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
@Table(name="recipe_video_subtitle")
public class RecipeVideoSubtitle implements Serializable {

	private static final long serialVersionUID = 5296639775204100994L;

	@Id
	long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private RecipeVideo video;

	@Enumerated(EnumType.STRING)
	private Language language;

	@NotNull
	private String url;

	RecipeVideoSubtitle(long id, RecipeVideo video, Language language, String url) {
		this.id = id;
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

package si.merljak.magistrska.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import si.merljak.magistrska.enumeration.Language;

@Entity
@Table(name="recipe_video")
public class RecipeVideo implements Serializable {

	private static final long serialVersionUID = 649457916245256724L;

	@Id
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Recipe recipe;

	@Enumerated(EnumType.STRING)
	private Language language;

	@NotNull
	private String url;

	@OneToMany(fetch = FetchType.EAGER, mappedBy="video")
	private List<RecipeVideoSubtitle> subtitles;

	RecipeVideo(long id, Recipe recipe, Language language, String url, List<RecipeVideoSubtitle> subtitles) {
		this.id = id;
		this.recipe = recipe;
		this.language = language;
		this.url = url;
		this.subtitles = subtitles;
	}

	public Language getLanguage() {
		return language;
	}

	public String getUrl() {
		return url;
	}

	public List<RecipeVideoSubtitle> getSubtitles() {
		return subtitles;
	}
}

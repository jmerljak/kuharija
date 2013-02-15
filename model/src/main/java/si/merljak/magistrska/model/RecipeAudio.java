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
@Table(name="recipe_audio")
public class RecipeAudio implements Serializable {

	private static final long serialVersionUID = 5061283027841562213L;

	@Id
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Recipe recipe;

	@Enumerated(EnumType.STRING)
	private Language language;

	@NotNull
	private String url;

	RecipeAudio(long id, Recipe recipe, Language language, String url) {
		this.id = id;
		this.recipe = recipe;
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

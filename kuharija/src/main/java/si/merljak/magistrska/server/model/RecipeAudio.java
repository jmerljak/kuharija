package si.merljak.magistrska.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import si.merljak.magistrska.common.enumeration.Language;

/**
 * @author Jakob Merljak
 */
@Entity
@Table(name="recipe_audio")
public class RecipeAudio extends Audio implements Serializable {

	private static final long serialVersionUID = -3297681827683104477L;

	@ManyToOne(fetch = FetchType.LAZY)
	private Recipe recipe;

	protected RecipeAudio() {}

	public RecipeAudio(Recipe recipe, Language language, String urls) {
		this.recipe = recipe;
		this.language = language;
		this.urls = urls;
	}
}

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
@Table(name="recipe_text")
public class RecipeText implements Serializable {

	private static final long serialVersionUID = 6418198442104147678L;

	@Id
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Recipe recipe;

	@Enumerated(EnumType.STRING)
	private Language language;

	@NotNull
	private String content;

	private String metadata;

	RecipeText(long id, Recipe recipe, Language language, String content, String metadata) {
		this.id = id;
		this.recipe = recipe;
		this.language = language;
		this.content = content;
		this.metadata = metadata;
	}

	public Language getLanguage() {
		return language;
	}

	public String getContent() {
		return content;
	}

	public String getMetadata() {
		return metadata;
	}
}

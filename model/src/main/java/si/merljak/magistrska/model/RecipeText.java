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
@Table(name="RECIPE_TEXT")
public class RecipeText implements Serializable {

	private static final long serialVersionUID = 6418198442104147678L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Recipe recipe;

	@Enumerated(EnumType.STRING)
	private Language language;

	@Column(nullable = false)
	private String content;

	private String metadata;

	protected RecipeText() {}

	public RecipeText( Recipe recipe, Language language, String content, String metadata) {
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

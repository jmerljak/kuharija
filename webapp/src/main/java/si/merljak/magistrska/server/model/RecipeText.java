package si.merljak.magistrska.server.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import si.merljak.magistrska.common.enumeration.Language;

@Entity
@DiscriminatorValue("R")
public class RecipeText extends Text implements Serializable {

	private static final long serialVersionUID = 6418198442104147678L;

	@ManyToOne(fetch = FetchType.LAZY)
	private Recipe recipe;

	protected RecipeText() {}

	public RecipeText( Recipe recipe, Language language, String content, String metadata) {
		this.recipe = recipe;
		this.language = language;
		this.content = content;
		this.metadata = metadata;
	}
}

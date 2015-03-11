package si.merljak.magistrska.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import si.merljak.magistrska.common.enumeration.Language;

/**
 * @author Jakob Merljak
 */
@Entity
@Table(name="recipe_details")
public class RecipeDetails extends Details implements Serializable {

	private static final long serialVersionUID = -1055512463307910110L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Recipe recipe;

	protected RecipeDetails() {}

	public RecipeDetails( Recipe recipe, Language language, String heading, String subHeading) {
		this.recipe = recipe;
		this.language = language;
		this.heading = heading;
		this.subHeading = subHeading;
	}
}

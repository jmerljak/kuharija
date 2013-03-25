package si.merljak.magistrska.server.model;

import java.io.Serializable;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import si.merljak.magistrska.common.enumeration.Language;

@Entity
@DiscriminatorValue("R")
@Table(name="recipe_details")
public class RecipeDetails extends Details implements Serializable {

	private static final long serialVersionUID = -1055512463307910110L;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	private Recipe recipe;

	protected RecipeDetails() {}

	public RecipeDetails( Recipe recipe, Language language, String heading, String subHeading, String timeNeeded) {
		this.recipe = recipe;
		this.language = language;
		this.heading = heading;
		this.subHeading = subHeading;
		this.timeNeeded = timeNeeded;
	}
}

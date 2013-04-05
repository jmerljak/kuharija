package si.merljak.magistrska.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="recipe_urensil")
public class RecipeUtensil implements Serializable {

	private static final long serialVersionUID = 3035885493363533907L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Recipe recipe;

	@ManyToOne(fetch = FetchType.EAGER)
	private Utensil utensil;

	private Integer quantity;

	protected RecipeUtensil() {}

	public RecipeUtensil(Recipe recipe, Utensil utensil, Integer quantity) {
		this.recipe = recipe;
		this.utensil = utensil;
		this.quantity = quantity;
	}

	public Utensil getTool() {
		return utensil;
	}

	public Integer getQuantity() {
		return quantity;
	}
}

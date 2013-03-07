package si.merljak.magistrska.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import si.merljak.magistrska.common.enumeration.Unit;

@Entity
@Table(name="recipe_ingredient")
@IdClass(RecipeIngredientId.class)
public class RecipeIngredient implements Serializable {

	private static final long serialVersionUID = -7310557037747164374L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	private Recipe recipe;

	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	private Ingredient ingredient;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Unit unit;

	private Double amount;

	protected RecipeIngredient() {}

	public RecipeIngredient(Recipe recipe, Ingredient ingredient, Unit unit, Double amount) {
		this.recipe = recipe;
		this.ingredient = ingredient;
		this.unit = unit;
		this.amount = amount;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public Unit getUnit() {
		return unit;
	}

	public Double getAmount() {
		return amount;
	}
	
}

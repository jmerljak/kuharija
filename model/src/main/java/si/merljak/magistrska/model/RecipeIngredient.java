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

import si.merljak.magistrska.enumeration.Unit;

@Entity
@Table(name="recipe_ingredient")
public class RecipeIngredient implements Serializable {

	private static final long serialVersionUID = -7310557037747164374L;

	@Id
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Recipe recipe;

	@ManyToOne(fetch = FetchType.EAGER)
	private Ingredient ingredient;

	@Enumerated(EnumType.STRING)
	private Unit unit;

	@NotNull
	private double amount;

	RecipeIngredient(long id, Recipe recipe, Ingredient ingredient, Unit unit, double amount) {
		this.id = id;
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

	public double getAmount() {
		return amount;
	}
	
}

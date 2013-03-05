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

import si.merljak.magistrska.enumeration.Unit;

@Entity
@Table(name="RECIPE_INGREDIENT")
public class RecipeIngredient implements Serializable {

	private static final long serialVersionUID = -7310557037747164374L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Recipe recipe;

	@ManyToOne(fetch = FetchType.EAGER)
	private Ingredient ingredient;

	@Enumerated(EnumType.STRING)
	private Unit unit;

	@Column(nullable = false)
	private double amount;

	protected RecipeIngredient() {}

	public RecipeIngredient(Recipe recipe, Ingredient ingredient, Unit unit, double amount) {
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

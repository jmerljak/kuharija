package si.merljak.magistrska.common.dto;

import java.io.Serializable;

import si.merljak.magistrska.common.enumeration.Unit;

public class RecipeIngredientDto implements Serializable {

	private static final long serialVersionUID = -1928823939332422508L;

	private long ingredientId;
	private String name;
	private String imageUrl;
	private Unit unit;
	private Double amount;

	RecipeIngredientDto() {}

	public RecipeIngredientDto(long ingredientId, String name, String imageUrl, Unit unit, Double amount) {
		this.ingredientId = ingredientId;
		this.name = name;
		this.imageUrl = imageUrl;
		this.unit = unit;
		this.amount = amount;
	}

	public long getIngredientId() {
		return ingredientId;
	}

	public String getName() {
		return name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public Unit getUnit() {
		return unit;
	}

	public Double getAmount() {
		return amount;
	}
}

package si.merljak.magistrska.dto;

import java.io.Serializable;

import si.merljak.magistrska.enumeration.Unit;

public class IngredientDto implements Serializable {

	private static final long serialVersionUID = -1928823939332422508L;

	private long ingredientId;
	private String name;
	private String imageUrl;
	private Unit unit;
	private double amount;

	IngredientDto() {}

	public IngredientDto(long ingredientId, String name, String imageUrl, Unit unit, double amount) {
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

	public double getAmount() {
		return amount;
	}
}

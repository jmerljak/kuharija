package si.merljak.magistrska.common.dto;

import java.io.Serializable;

public class IngredientDto implements Serializable {

	private static final long serialVersionUID = 7825971095599332939L;

	private String name;
	private String imageUrl;

	IngredientDto() {}

	public IngredientDto(String name, String imageUrl) {
		this.name = name;
		this.imageUrl = imageUrl;
	}

	public String getName() {
		return name;
	}

	public String getImageUrl() {
		return imageUrl;
	}
}

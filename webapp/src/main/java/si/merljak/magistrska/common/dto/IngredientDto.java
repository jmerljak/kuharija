package si.merljak.magistrska.common.dto;

import java.io.Serializable;

import com.mysema.query.annotations.QueryProjection;

public class IngredientDto implements Serializable {

	private static final long serialVersionUID = 7825971095599332939L;

	private String name;
	private String imageUrl;

	IngredientDto() {}

	@QueryProjection
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

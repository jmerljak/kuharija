package si.merljak.magistrska.common.dto;

import java.io.Serializable;

import com.mysema.query.annotations.QueryProjection;

public class UtensilDto implements Serializable {

	private static final long serialVersionUID = -872342731145957913L;

	private String name;
	private String imageUrl;
	private Integer quantity;

	UtensilDto() {}

	@QueryProjection
	public UtensilDto(String name, String imageUrl) {
		this.name = name;
		this.imageUrl = imageUrl;
	}

	@QueryProjection
	public UtensilDto(String name, String imageUrl, Integer quantity) {
		this.name = name;
		this.imageUrl = imageUrl;
		this.quantity = quantity;
	}

	public String getName() {
		return name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public Integer getQuantity() {
		return quantity;
	}
}

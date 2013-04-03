package si.merljak.magistrska.common.dto;

import java.io.Serializable;

import com.mysema.query.annotations.QueryProjection;

public class ToolDto implements Serializable {

	private static final long serialVersionUID = -872342731145957913L;

	private String title;
	private String imageUrl;
	private Integer quantity;

	ToolDto() {}

	@QueryProjection
	public ToolDto(String title, String imageUrl) {
		this.title = title;
		this.imageUrl = imageUrl;
	}

	@QueryProjection
	public ToolDto(String title, String imageUrl, Integer quantity) {
		this.title = title;
		this.imageUrl = imageUrl;
		this.quantity = quantity;
	}

	public String getTitle() {
		return title;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public Integer getQuantity() {
		return quantity;
	}
}

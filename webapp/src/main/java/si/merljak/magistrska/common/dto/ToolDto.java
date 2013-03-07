package si.merljak.magistrska.common.dto;

import java.io.Serializable;

public class ToolDto implements Serializable {

	private static final long serialVersionUID = -872342731145957913L;

	private String title;
	private String imageUrl;
	private int quantity;

	ToolDto() {}

	public ToolDto(String title, String imageUrl, int quantity) {
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

	public int getQuantity() {
		return quantity;
	}
}

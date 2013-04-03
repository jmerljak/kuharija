package si.merljak.magistrska.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Ingredient implements Serializable {

	private static final long serialVersionUID = 6010168011125353169L;

	@Id
	@NotNull
	private String name;

	private String imageUrl;

	protected Ingredient() {}

	public Ingredient(String name, String imageUrl) {
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

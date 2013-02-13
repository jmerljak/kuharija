package si.merljak.magistrska.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Ingredient implements Serializable {

	private static final long serialVersionUID = 6010168011125353169L;

	@Id
	private long id;

	@NotNull
	private String name;

	private String imageUrl;

	Ingredient(long id, String name, String imageUrl) {
		this.id = id;
		this.name = name;
		this.imageUrl = imageUrl;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getImageUrl() {
		return imageUrl;
	}
}

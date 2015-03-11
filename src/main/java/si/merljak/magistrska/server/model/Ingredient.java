package si.merljak.magistrska.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 * @author Jakob Merljak
 */
@Entity
public class Ingredient implements Serializable {

	private static final long serialVersionUID = 6010168011125353169L;

	@Id
	@NotNull
	private String name;

	private String imageUrl;

	@ManyToOne
	private Ingredient parent;

	protected Ingredient() {}

	public Ingredient(String name, String imageUrl, Ingredient parent) {
		this.name = name;
		this.imageUrl = imageUrl;
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public Ingredient getParent() {
		return parent;
	}
}

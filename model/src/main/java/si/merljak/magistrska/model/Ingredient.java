package si.merljak.magistrska.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="INGREDIENT")
public class Ingredient implements Serializable {

	private static final long serialVersionUID = 6010168011125353169L;

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private String name;

	private String imageUrl;

	protected Ingredient() {}

	public Ingredient(String name, String imageUrl) {
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

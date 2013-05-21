package si.merljak.magistrska.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Utensil implements Serializable {

	private static final long serialVersionUID = -8350467055363888698L;

	@Id
	@NotNull
	private String name;

	private String imageUrl;

	@ManyToOne
	private Utensil parent;

	protected Utensil() {}

	public Utensil(String name, String imageUrl, Utensil parent) {
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

	public Utensil getParent() {
		return parent;
	}
}

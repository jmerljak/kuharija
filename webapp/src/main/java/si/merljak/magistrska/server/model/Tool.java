package si.merljak.magistrska.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Tool implements Serializable {

	private static final long serialVersionUID = -8350467055363888698L;

	@Id
	@NotNull
	private String name;

	private String imageUrl;

	protected Tool() {}

	public Tool(String name, String imageUrl) {
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

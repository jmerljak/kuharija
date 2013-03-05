package si.merljak.magistrska.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TOOL")
public class Tool implements Serializable {

	private static final long serialVersionUID = -8350467055363888698L;

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private String name;

	private String imageUrl;

	protected Tool() {}

	public Tool(String name, String imageUrl) {
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

package si.merljak.magistrska.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author Jakob Merljak
 */
@Entity
@Table(name="technique_utensil")
public class TechniqueUtensil implements Serializable {

	private static final long serialVersionUID = -6450812763794194567L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Technique technique;

	@ManyToOne(fetch = FetchType.EAGER)
	private Utensil utensil;

	private Integer quantity;

	protected TechniqueUtensil() {}

	public TechniqueUtensil(Technique technique, Utensil utensil, Integer quantity) {
		this.technique = technique;
		this.utensil = utensil;
		this.quantity = quantity;
	}

	public Utensil getTool() {
		return utensil;
	}

	public Integer getQuantity() {
		return quantity;
	}
}

package si.merljak.magistrska.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TECHNIQUE_TOOL")
public class TechniqueTool implements Serializable {

	private static final long serialVersionUID = -6450812763794194567L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Technique technique;

	@ManyToOne(fetch = FetchType.EAGER)
	private Tool tool;

	@Column(nullable = false)
	private int quantity;

	protected TechniqueTool() {}

	public TechniqueTool(Technique technique, Tool tool, int quantity) {
		this.technique = technique;
		this.tool = tool;
		this.quantity = quantity;
	}

	public Tool getTool() {
		return tool;
	}

	public int getQuantity() {
		return quantity;
	}
}

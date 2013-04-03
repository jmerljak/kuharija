package si.merljak.magistrska.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="technique_tool")
public class TechniqueTool implements Serializable {

	private static final long serialVersionUID = -6450812763794194567L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Technique technique;

	@ManyToOne(fetch = FetchType.EAGER)
	private Tool tool;

	private Integer quantity;

	protected TechniqueTool() {}

	public TechniqueTool(Technique technique, Tool tool, Integer quantity) {
		this.technique = technique;
		this.tool = tool;
		this.quantity = quantity;
	}

	public Tool getTool() {
		return tool;
	}

	public Integer getQuantity() {
		return quantity;
	}
}

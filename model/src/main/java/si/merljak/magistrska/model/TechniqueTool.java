package si.merljak.magistrska.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="technique_tool")
public class TechniqueTool implements Serializable {

	private static final long serialVersionUID = -6450812763794194567L;

	@Id
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Technique technique;

	@ManyToOne(fetch = FetchType.EAGER)
	private Tool tool;

	@NotNull
	private int quantity;

	TechniqueTool(long id, Technique technique, Tool tool, int quantity) {
		this.id = id;
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

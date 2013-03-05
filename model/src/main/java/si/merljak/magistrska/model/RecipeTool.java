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
@Table(name="RECIPE_TOOL")
public class RecipeTool implements Serializable {

	private static final long serialVersionUID = 3035885493363533907L;

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Recipe recipe;

	@ManyToOne(fetch = FetchType.EAGER)
	private Tool tool;

	@Column(nullable = false)
	private int quantity;

	protected RecipeTool() {}

	public RecipeTool(Recipe recipe, Tool tool, int quantity) {
		this.recipe = recipe;
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

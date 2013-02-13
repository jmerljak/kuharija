package si.merljak.magistrska.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="recipe_tool")
public class RecipeTool implements Serializable {

	private static final long serialVersionUID = 3035885493363533907L;

	@Id
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	private Recipe recipe;

	@ManyToOne(fetch = FetchType.EAGER)
	private Tool tool;

	@NotNull
	private int quantity;

	RecipeTool(long id, Recipe recipe, Tool tool, int quantity) {
		this.id = id;
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

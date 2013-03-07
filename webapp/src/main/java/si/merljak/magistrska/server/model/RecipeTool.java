package si.merljak.magistrska.server.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="recipe_tool")
@IdClass(RecipeToolId.class)
public class RecipeTool implements Serializable {

	private static final long serialVersionUID = 3035885493363533907L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	private Recipe recipe;

	@Id
	@ManyToOne(fetch = FetchType.EAGER)
	private Tool tool;

	@NotNull
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

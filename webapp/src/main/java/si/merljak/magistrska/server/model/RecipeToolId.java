package si.merljak.magistrska.server.model;

import java.io.Serializable;

public class RecipeToolId implements Serializable {

	private static final long serialVersionUID = 3545527345033880286L;

	public Recipe recipe;
	public Tool tool;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecipeToolId other = (RecipeToolId) o;
        return recipe.equals(other.recipe) && tool.equals(other.tool);
    }
}
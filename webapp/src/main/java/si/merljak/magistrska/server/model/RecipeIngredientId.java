package si.merljak.magistrska.server.model;

import java.io.Serializable;

public class RecipeIngredientId implements Serializable {

	private static final long serialVersionUID = 8685519973302492602L;

	public Recipe recipe;
	public Ingredient ingredient;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecipeIngredientId other = (RecipeIngredientId) o;
        return recipe.equals(other.recipe) && ingredient.equals(other.ingredient);
    }
}
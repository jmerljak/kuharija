package si.merljak.magistrska.common.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jakob Merljak
 */
public class RecipeListDto implements Serializable {

	private static final long serialVersionUID = -4689515931972660496L;

	private List<RecipeDto> recipes;
	private long allCount;

	RecipeListDto() {}

	public RecipeListDto(List<RecipeDto> recipes, long allCount) {
		this.recipes = recipes;
		this.allCount = allCount;
	}

	public List<RecipeDto> getRecipes() {
		return recipes;
	}

	public long getAllCount() {
		return allCount;
	}
}

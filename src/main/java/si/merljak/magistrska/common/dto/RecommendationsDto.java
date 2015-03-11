package si.merljak.magistrska.common.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import si.merljak.magistrska.common.enumeration.RecommendationType;

/**
 * @author Jakob Merljak
 */
public class RecommendationsDto implements Serializable {

	private static final long serialVersionUID = 3166258673861297035L;

	private Map<RecommendationType, List<RecipeDto>> recommendations = new HashMap<RecommendationType, List<RecipeDto>>();

	public Map<RecommendationType, List<RecipeDto>> getRecommendations() {
		return recommendations;
	}

	public void addRecommendations(RecommendationType type, List<RecipeDto> recipes) {
		// only add non empty lists
		if (recipes != null && !recipes.isEmpty()) {
			recommendations.put(type, recipes);
		}
	}
}

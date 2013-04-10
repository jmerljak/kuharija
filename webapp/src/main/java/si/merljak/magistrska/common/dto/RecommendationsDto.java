package si.merljak.magistrska.common.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import si.merljak.magistrska.common.enumeration.RecommendationType;

public class RecommendationsDto implements Serializable {

	private static final long serialVersionUID = 3166258673861297035L;

	private Map<RecommendationType, RecipeDto> recommendation = new HashMap<RecommendationType, RecipeDto>();

	public Map<RecommendationType, RecipeDto> getRecommendation() {
		return recommendation;
	}

	public void addRecommendation(RecommendationType type, RecipeDto recipe) {
		recommendation.put(type, recipe);
	}
}

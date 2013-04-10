package si.merljak.magistrska.server;

import static si.merljak.magistrska.server.model.QRecipe.recipe;
import static si.merljak.magistrska.server.model.QRecipeDetails.recipeDetails;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.merljak.magistrska.common.dto.QRecipeDto;
import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.RecommendationsDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.enumeration.RecommendationType;
import si.merljak.magistrska.common.enumeration.Season;
import si.merljak.magistrska.common.rpc.RecommendationService;
import si.merljak.magistrska.server.personalization.PersonalizationService;
import si.merljak.magistrska.server.personalization.PersonalizationServiceMock;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mysema.query.jpa.impl.JPAQuery;

public class RecommendationServiceImpl extends RemoteServiceServlet implements RecommendationService {

	private static final long serialVersionUID = 1636894033893317781L;

	private static final Logger log = LoggerFactory.getLogger(RecommendationServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	private PersonalizationService personalizationService = new PersonalizationServiceMock();

	@Override
	public RecommendationsDto recommendRecipes(String username, Double latitude, Double longitude, Language language) {
		log.debug("getting recommendations for user " + username + "at location: " + latitude + ", " + longitude);

		RecommendationsDto recommendations = new RecommendationsDto();

		// recipes from ingredients in user refrigerator
		List<String> ingredientsFromFridge = personalizationService.getIngredientsFromFridge(username);
		if (ingredientsFromFridge != null) {
			RecipeDto recipeFromIngredients = null;
			// TODO
			recommendations.addRecommendation(RecommendationType.INGREDIENTS_FROM_FRIDGE, recipeFromIngredients);
		}

		// recipes for time of day
		String localTime = personalizationService.getLocalTime(username, latitude, longitude);
		if (localTime != null) {
			RecipeDto recipeForTimeOfDay = new JPAQuery(em)
											.from(recipe)
											.innerJoin(recipe.details, recipeDetails)
											.where(recipeDetails.language.eq(language))
											.where(recipe.metadata.like("%" + localTime + "%"))
											.uniqueResult(new QRecipeDto(recipe.id, recipeDetails.heading, 
													recipe.imageUrl, recipe.difficulty, recipe.timeOverall));

			recommendations.addRecommendation(RecommendationType.LOCAL_TIME, recipeForTimeOfDay);
		}

		// recipes for season
		Season localSeason = personalizationService.getLocalSeason(username, latitude);
		if (localSeason != null) {
			RecipeDto recipeForSeason = new JPAQuery(em)
											.from(recipe)
											.innerJoin(recipe.details, recipeDetails)
											.where(recipeDetails.language.eq(language))
											.where(recipe.seasons.any().eq(localSeason))
											.uniqueResult(new QRecipeDto(recipe.id, recipeDetails.heading, 
													recipe.imageUrl, recipe.difficulty, recipe.timeOverall));
			
			recommendations.addRecommendation(RecommendationType.LOCAL_SEASON, recipeForSeason);
		}

		// local specialties
		String country = personalizationService.getCounty(username, latitude, longitude);
		if (country != null) {
			// TODO
			recommendations.addRecommendation(RecommendationType.LOCAL_SPECIALTIES, null);
		}

		// user preferences
		// TODO

		// featured recipes
		// TODO
		
		return recommendations;
	}

	@Override
	public List<RecipeDto> recommendSimmilarRecipes(long recipeId, String username) {
		// TODO Auto-generated method stub
		return null;
	}
}

package si.merljak.magistrska.server;

import static si.merljak.magistrska.server.model.QIngredient.ingredient;
import static si.merljak.magistrska.server.model.QRecipe.recipe;
import static si.merljak.magistrska.server.model.QRecipeDetails.recipeDetails;
import static si.merljak.magistrska.server.model.QRecipeIngredient.recipeIngredient;

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
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.expr.NumberExpression;

public class RecommendationServiceImpl extends RemoteServiceServlet implements RecommendationService {

	private static final long serialVersionUID = 1636894033893317781L;
	private static final long LIMIT_PER_RECOMMENDATION_TYPE = 2;

	private static final Logger log = LoggerFactory.getLogger(RecommendationServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	private PersonalizationService personalizationService;

	@Override
	public RecommendationsDto recommendRecipes(String username, Double latitude, Double longitude, Language language) {
		log.debug("getting recommendations for user " + username + "at location: " + latitude + ", " + longitude);
		
		if (personalizationService == null) {
			personalizationService = new PersonalizationServiceMock(em);
		}

		RecommendationsDto recommendations = new RecommendationsDto();

		// recipes from ingredients in user refrigerator
		List<String> ingredientsFromFridge = personalizationService.getIngredientsFromFridge(username);
		if (ingredientsFromFridge != null && !ingredientsFromFridge.isEmpty()) {
			JPASubQuery recipesWithMissingIngredient = new JPASubQuery()
											.from(recipe)
											.innerJoin(recipe.ingredients, recipeIngredient)
											.innerJoin(recipeIngredient.ingredient, ingredient)
											.where(ingredient.name.notIn(ingredientsFromFridge))
											.distinct();
			
			List<RecipeDto> recipesFromIngredients = new JPAQuery(em)
											.from(recipe)
											.innerJoin(recipe.details, recipeDetails)
											.where(recipeDetails.language.eq(language))
											.where(recipe.id.notIn(recipesWithMissingIngredient.list(recipe.id)))
											.orderBy(NumberExpression.random().asc()) // randomize
											.limit(LIMIT_PER_RECOMMENDATION_TYPE) // limit to few results
											.list(new QRecipeDto(recipe.id, recipeDetails.heading, 
													recipe.imageUrl, recipe.difficulty, recipe.timeOverall));

			recommendations.addRecommendations(RecommendationType.INGREDIENTS_FROM_FRIDGE, recipesFromIngredients);
		}

		// recipes for time of day
		String localTime = personalizationService.getLocalTime(username, latitude, longitude);
		if (localTime != null && !localTime.isEmpty()) {
			List<RecipeDto> recipesForTimeOfDay = new JPAQuery(em)
											.from(recipe)
											.innerJoin(recipe.details, recipeDetails)
											.where(recipeDetails.language.eq(language))
											.where(recipe.metadata.contains("timeofday=" + localTime))
											.orderBy(NumberExpression.random().asc()) // randomize
											.limit(LIMIT_PER_RECOMMENDATION_TYPE) // limit to few results
											.list(new QRecipeDto(recipe.id, recipeDetails.heading, 
													recipe.imageUrl, recipe.difficulty, recipe.timeOverall));

			recommendations.addRecommendations(RecommendationType.LOCAL_TIME, recipesForTimeOfDay);
		}

		// recipes for season
		Season localSeason = personalizationService.getLocalSeason(username, latitude, longitude);
		if (localSeason != null) {
			List<RecipeDto> recipesForSeason = new JPAQuery(em)
											.from(recipe)
											.innerJoin(recipe.details, recipeDetails)
											.where(recipeDetails.language.eq(language))
											.where(recipe.seasons.any().eq(localSeason))
											.orderBy(NumberExpression.random().asc()) // randomize
											.limit(LIMIT_PER_RECOMMENDATION_TYPE) // limit to few results
											.list(new QRecipeDto(recipe.id, recipeDetails.heading, 
													recipe.imageUrl, recipe.difficulty, recipe.timeOverall));
			
			recommendations.addRecommendations(RecommendationType.LOCAL_SEASON, recipesForSeason);
		}

		// local specialties
		String country = personalizationService.getCountry(username, latitude, longitude);
		if (country != null && !country.isEmpty()) {
			List<RecipeDto> localRecipes = new JPAQuery(em)
											.from(recipe)
											.innerJoin(recipe.details, recipeDetails)
											.where(recipeDetails.language.eq(language))
											.where(recipe.metadata.contains("origin=" + country))
											.orderBy(NumberExpression.random().asc()) // randomize
											.limit(LIMIT_PER_RECOMMENDATION_TYPE) // limit to few results
											.list(new QRecipeDto(recipe.id, recipeDetails.heading, 
													recipe.imageUrl, recipe.difficulty, recipe.timeOverall));

			recommendations.addRecommendations(RecommendationType.LOCAL_SPECIALTY, localRecipes);
		}

		// user preferences
		String searchString = personalizationService.recommendRecipe(username);
		if (searchString != null && !searchString.isEmpty()) {
			List<RecipeDto> recommendedRecipes = new JPAQuery(em)
											.from(recipe)
											.innerJoin(recipe.details, recipeDetails)
											.where(recipeDetails.language.eq(language))
											.where(recipe.metadata.contains(searchString))
											.orderBy(NumberExpression.random().asc()) // randomize
											.limit(LIMIT_PER_RECOMMENDATION_TYPE) // limit to few results
											.list(new QRecipeDto(recipe.id, recipeDetails.heading, 
													recipe.imageUrl, recipe.difficulty, recipe.timeOverall));

			recommendations.addRecommendations(RecommendationType.USER_PREFERENCES, recommendedRecipes);
		}

		// featured recipes
		List<RecipeDto> featuredRecipes = new JPAQuery(em)
									.from(recipe)
									.innerJoin(recipe.details, recipeDetails)
									.where(recipeDetails.language.eq(language))
									.where(recipe.metadata.contains("featured"))
									.orderBy(NumberExpression.random().asc()) // randomize
									.limit(LIMIT_PER_RECOMMENDATION_TYPE) // limit to few results
									.list(new QRecipeDto(recipe.id, recipeDetails.heading, 
											recipe.imageUrl, recipe.difficulty, recipe.timeOverall));

		recommendations.addRecommendations(RecommendationType.FEATURED, featuredRecipes);

		return recommendations;
	}

	@Override
	public List<RecipeDto> recommendSimmilarRecipes(long recipeId, String username) {
		// TODO Auto-generated method stub
		return null;
	}
}

package si.merljak.magistrska.server;

import static si.merljak.magistrska.server.model.QIngredient.ingredient;
import static si.merljak.magistrska.server.model.QRecipe.recipe;
import static si.merljak.magistrska.server.model.QRecipeDetails.recipeDetails;
import static si.merljak.magistrska.server.model.QRecipeIngredient.recipeIngredient;
import static si.merljak.magistrska.server.model.QRecipeText.recipeText;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.merljak.magistrska.client.rpc.SearchService;
import si.merljak.magistrska.common.SearchParameters;
import si.merljak.magistrska.common.dto.QRecipeBasicDto;
import si.merljak.magistrska.common.dto.RecipeBasicDto;
import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.enumeration.Season;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.jpa.impl.JPASubQuery;

public class SearchServiceImpl extends RemoteServiceServlet implements SearchService {

	private static final long serialVersionUID = 6742097745368981008L;

	private static final Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<RecipeBasicDto> search(SearchParameters searchParameters) {
		log.info("searching for: " + searchParameters.getSearchString());

		// parameters
		int page = searchParameters.getPage();
		int pageSize = searchParameters.getPageSize();
		String searchString = searchParameters.getSearchString();
		Difficulty difficulty = searchParameters.getDifficulty();
		Category category = searchParameters.getCategory();
		Season season = searchParameters.getSeason();
		Set<String> ingredients = searchParameters.getIngredients();
		Language language = searchParameters.getLanguage();

		// build query
		JPASubQuery subquery = new JPASubQuery().from(recipe);
		subquery.innerJoin(recipe.details, recipeDetails);
		subquery.innerJoin(recipe.texts, recipeText);
		subquery.where(recipeDetails.heading.like("%"+searchString+"%")
				.or(recipeDetails.subHeading.like("%"+searchString+"%"))
				.or(recipeText.content.like("%"+searchString+"%")));

		if (difficulty != null) {
			subquery.where(recipe.difficulty.eq(difficulty));
		}

		if (season != null) {
			subquery.where(recipe.seasons.contains(season)
					.or(recipe.seasons.contains(Season.ALLYEAR)));
		}

		if (category != null) {
			subquery.where(recipe.categories.contains(category));
		}

		if (ingredients != null && !ingredients.isEmpty()) {
			subquery.innerJoin(recipe.ingredients, recipeIngredient)
				 .innerJoin(recipeIngredient.ingredient, ingredient)
				 .with(ingredient.name.in(ingredients));
		}

		subquery.limit(pageSize)
				.offset((page -1) * pageSize)
				.distinct()
				.orderBy(recipe.id.asc());

		JPAQuery query = new JPAQuery(em).from(recipe);
		query.innerJoin(recipe.details, recipeDetails);
		query.where(recipeDetails.language.eq(language));
		query.where(recipe.id.in(subquery.list(recipe.id)));

		return query.list(new QRecipeBasicDto(recipe.id, recipeDetails.heading, recipe.imageUrl, recipe.difficulty, recipeDetails.timeNeeded));
	}

}

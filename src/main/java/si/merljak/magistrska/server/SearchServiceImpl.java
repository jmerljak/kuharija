package si.merljak.magistrska.server;

import static si.merljak.magistrska.server.model.QRecipe.recipe;
import static si.merljak.magistrska.server.model.QRecipeDetails.recipeDetails;
import static si.merljak.magistrska.server.model.QRecipeText.recipeText;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.merljak.magistrska.common.SearchParameters;
import si.merljak.magistrska.common.dto.QRecipeDto;
import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.RecipeListDto;
import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.enumeration.RecipeSortKey;
import si.merljak.magistrska.common.enumeration.Season;
import si.merljak.magistrska.common.rpc.SearchService;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mysema.query.BooleanBuilder;
import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * @author Jakob Merljak
 */
public class SearchServiceImpl extends RemoteServiceServlet implements SearchService {

	private static final long serialVersionUID = 6742097745368981008L;

	private static final Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);

	private final Splitter splitter = Splitter.on(CharMatcher.JAVA_LETTER.negate()).omitEmptyStrings();
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public RecipeListDto search(SearchParameters searchParameters) {
		log.debug("searching for: " + searchParameters.getSearchString());

		// parameters
		long page = searchParameters.getPage();
		long pageSize = searchParameters.getPageSize();
		String searchString = searchParameters.getSearchString();
		Set<Difficulty> difficulties = searchParameters.getDifficulties();
		Set<Category> categories = searchParameters.getCategories();
		Set<Season> seasons = searchParameters.getSeasons();
		Set<String> ingredients = searchParameters.getIngredients();
		Set<String> utensils = searchParameters.getUtensils();
		Language language = searchParameters.getLanguage();
		RecipeSortKey sortKey = searchParameters.getSortKey();

		// build query
		JPASubQuery subquery = new JPASubQuery().from(recipe);

		if (searchString != null) {
			BooleanBuilder basicFilter = new BooleanBuilder();
			for (String split : splitter.split(searchString)) {
				split = split.toLowerCase();
				if (split.length() < 3) {
					// skip very short words
					continue;
				} else {
					split = "%" + split + "%";
				}

				// match ALL words (anywhere)
				basicFilter.and(recipeDetails.heading.lower().like(split)
							   .or(recipeDetails.subHeading.lower().like(split))
							   .or(recipeText.content.lower().like(split))
							   .or(recipe.metadata.lower().like(split)));
			}

			subquery.innerJoin(recipe.details, recipeDetails);
			subquery.innerJoin(recipe.texts, recipeText);
			subquery.where(basicFilter);
		}

		if (!difficulties.isEmpty()) {
			subquery.where(recipe.difficulty.in(difficulties));
		}

		if (!seasons.isEmpty()) {
			BooleanBuilder seasonsFilter = new BooleanBuilder();
			for (Season season : seasons) {
				// searching for recipes for ANY of listed seasons
				seasonsFilter.or(recipe.seasons.any().eq(season));
			}
			// include those with season not specified?
//			seasonsFilter.or(recipe.seasons.any().eq(Season.ALLYEAR));

			subquery.where(seasonsFilter);
		}

		if (!categories.isEmpty()) {
			BooleanBuilder categoriesFilter = new BooleanBuilder();
			for (Category category : categories) {
				// searching for recipes in ANY of listed categories
				categoriesFilter.or(recipe.categories.any().eq(category));
			}
			subquery.where(categoriesFilter);
		}

		if (!ingredients.isEmpty()) {
			BooleanBuilder ingredientsFilter = new BooleanBuilder();
			for (String ingredientName : ingredients) {
				// searching for recipes with ALL listed ingredients
				ingredientsFilter.and(
						recipe.ingredients.any().ingredient.name.eq(ingredientName)
						.or(
						recipe.ingredients.any().ingredient.parent.name.eq(ingredientName))
						);
			}
			subquery.where(ingredientsFilter);
		}

		if (!utensils.isEmpty()) {
			BooleanBuilder utensilsFilter = new BooleanBuilder();
			for (String utensilName : utensils) {
				// searching for recipes with ALL listed utensils
				utensilsFilter.and(
						recipe.utensils.any().utensil.name.eq(utensilName)
						.or(
						recipe.utensils.any().utensil.parent.name.eq(utensilName))
						);
			}
			subquery.where(utensilsFilter);
		}

		// main query
		JPAQuery query = new JPAQuery(em).from(recipe);
		query.innerJoin(recipe.details, recipeDetails)
			 .where(recipeDetails.language.eq(language))
			 .where(recipe.id.in(subquery.list(recipe.id)))
			 .distinct();

		// count all results regardless of paging and sorting
		long allCount = query.count();

		// paging
		query.limit(pageSize)
		 	 .offset((page -1) * pageSize);

		// sorting
		switch (sortKey) {
		case ID:
			query.orderBy(recipe.id.asc());
			break;
		case TITLE:
			query.orderBy(recipeDetails.heading.asc());
			break;
		case TIMEOVERALL:
			query.orderBy(recipe.timeOverall.asc(), recipeDetails.heading.asc());
			break;
		case DIFFICULTY:
			query.orderBy(recipe.difficulty.asc(), recipeDetails.heading.asc());
			break;
		}

		List<RecipeDto> recipes = query.list(new QRecipeDto(recipe.id, recipeDetails.heading, recipe.imageUrl, recipe.difficulty, recipe.timeOverall));
		return new RecipeListDto(recipes, allCount);
	}

}

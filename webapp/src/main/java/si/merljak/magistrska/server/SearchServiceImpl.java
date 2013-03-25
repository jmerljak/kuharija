package si.merljak.magistrska.server;

import static si.merljak.magistrska.server.model.QIngredient.ingredient;
import static si.merljak.magistrska.server.model.QRecipe.recipe;
import static si.merljak.magistrska.server.model.QRecipeIngredient.recipeIngredient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.merljak.magistrska.client.rpc.SearchService;
import si.merljak.magistrska.common.SearchParameters;
import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.enumeration.Season;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mysema.query.jpa.impl.JPAQuery;

public class SearchServiceImpl extends RemoteServiceServlet implements SearchService {

	private static final long serialVersionUID = 6742097745368981008L;

	private static final Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<String> basicSearch(String searchString, int page, int pageSize) {
		log.info("searchinge for: " + searchString + ", page: " + page + ", pageSize: " + pageSize);
		// TODO Auto-generated method stub
		return new ArrayList<String>(Arrays.asList("polde", "jure", "miha"));
		
	}

	@Override
	public List<Long> search(SearchParameters searchParameters) {
		log.info("searchinge for parameters: " + searchParameters.toString());

		JPAQuery query = new JPAQuery(em).from(recipe);

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
		if (difficulty != null) {
			query.where(recipe.difficulty.eq(difficulty));
		}

		if (season != null) {
			query.where(recipe.seasons.contains(season)
					.or(recipe.seasons.contains(Season.ALLYEAR)));
		}

		if (category != null) {
			query.where(recipe.categories.contains(category));
		}

		if (ingredients != null && !ingredients.isEmpty()) {
			query.innerJoin(recipe.ingredients, recipeIngredient)
				 .innerJoin(recipeIngredient.ingredient, ingredient)
				 .with(ingredient.name.in(ingredients));
		}

		List<Long> list = query.limit(pageSize)
								 .offset((page -1) * pageSize)
								 .distinct()
								 .orderBy(recipe.id.asc())
								 .list(recipe.id);

		return list;
	}

}

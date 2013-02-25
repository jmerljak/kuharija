package si.merljak.magistrska.server;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.merljak.magistrska.client.rpc.RecipeService;
import si.merljak.magistrska.dto.CommentDto;
import si.merljak.magistrska.dto.IngredientDto;
import si.merljak.magistrska.dto.RecipeDto;
import si.merljak.magistrska.dto.TextDto;
import si.merljak.magistrska.dto.ToolDto;
import si.merljak.magistrska.enumeration.Difficulty;
import si.merljak.magistrska.enumeration.Language;
import si.merljak.magistrska.enumeration.Unit;
import si.merljak.magistrska.model.Recipe;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class RecipeServiceImpl extends RemoteServiceServlet implements RecipeService {

	private static final long serialVersionUID = 8165315606051808675L;

	private static final Logger log = LoggerFactory.getLogger(RecipeServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public RecipeDto getRecipe(long recipeId, Language language) {
		log.debug("executing getRecipe");

		try {
			Recipe recipeEntity = em.find(Recipe.class, recipeId);
			log.info(recipeEntity + "");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		
		RecipeDto recipe = new RecipeDto("Okusna jed", "Jakob", null, Difficulty.EASY, "15min", 4, null);
		recipe.addText(new TextDto(Language.sl_SI, "Vzemi kuharsko knjigo,  odpri na strani 54 in kuhaj!", null));
		recipe.addText(new TextDto(Language.sl_SI, "Vzemi kuharsko knjigo,  odpri na strani 54, dobro preberi, še enkrat pomešaj in kuhaj!", null));
		recipe.addText(new TextDto(Language.en_US, "Open cook bo5ok on page 54, then cook!", null));
		recipe.addComment(new CommentDto("prijazen uporabnik", new Date(), "Zelo okusno!"));
		recipe.addComment(new CommentDto("nesramen uporabnik", new Date(), "Ogabno!"));
		recipe.addIngredient(new IngredientDto(1, "cheese", null, Unit.G, 250.00));
		recipe.addIngredient(new IngredientDto(2, "water", null, Unit.L, 1.25));
		recipe.addIngredient(new IngredientDto(3, "salt", null, Unit.PINCH, 1));
		recipe.addIngredient(new IngredientDto(4, "sugar cube", null, Unit.PIECE, 4));
		recipe.addTool(new ToolDto("spoon", null, 2));
		recipe.addTool(new ToolDto("knife", null, 1));
		recipe.addTool(new ToolDto("cooking pot", null, 1));
		return recipe;
	}

}

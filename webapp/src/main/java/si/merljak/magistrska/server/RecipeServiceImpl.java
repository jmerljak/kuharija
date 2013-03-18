package si.merljak.magistrska.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.merljak.magistrska.client.rpc.RecipeService;
import si.merljak.magistrska.common.dto.AudioDto;
import si.merljak.magistrska.common.dto.CommentDto;
import si.merljak.magistrska.common.dto.IngredientDto;
import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.dto.SubtitleDto;
import si.merljak.magistrska.common.dto.TextDto;
import si.merljak.magistrska.common.dto.ToolDto;
import si.merljak.magistrska.common.dto.VideoDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.server.model.Audio;
import si.merljak.magistrska.server.model.Comment;
import si.merljak.magistrska.server.model.Ingredient;
import si.merljak.magistrska.server.model.Recipe;
import si.merljak.magistrska.server.model.RecipeIngredient;
import si.merljak.magistrska.server.model.RecipeTool;
import si.merljak.magistrska.server.model.Subtitle;
import si.merljak.magistrska.server.model.Text;
import si.merljak.magistrska.server.model.Tool;
import si.merljak.magistrska.server.model.Video;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class RecipeServiceImpl extends RemoteServiceServlet implements RecipeService {

	private static final long serialVersionUID = 8165315606051808675L;

	private static final Logger log = LoggerFactory.getLogger(RecipeServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public RecipeDto getRecipe(long recipeId, Language language) {
		log.info("executing getRecipe for id: " + recipeId + " and language: " + language);

		try {
			Recipe recipeEntity = em.find(Recipe.class, recipeId);
			if (recipeEntity == null) {
				return null;
			}
			
			RecipeDto recipe = new RecipeDto(recipeEntity.getTitle(), recipeEntity.getAuthor(), recipeEntity.getImageUrl(), 
											 recipeEntity.getDifficulty(), recipeEntity.getPreparationTime(), 
											 recipeEntity.getNumberOfMeals(), recipeEntity.getMetaData());

			for (RecipeIngredient recipeIngredient : recipeEntity.getIngredients()) {
				Ingredient ingredient = recipeIngredient.getIngredient();
				recipe.addIngredient(new IngredientDto(ingredient.getId(), ingredient.getName(), ingredient.getImageUrl(), recipeIngredient.getUnit(), recipeIngredient.getAmount()));
			}

			for (RecipeTool recipeTool : recipeEntity.getTools()) {
				Tool tool = recipeTool.getTool();
				recipe.addTool(new ToolDto(tool.getName(), tool.getImageUrl(), recipeTool.getQuantity()));
			}

			TypedQuery<Text> query = em.createQuery("SELECT t FROM Text t " +
													"WHERE t.recipe = :recipe " +
													"AND t.language = :language", Text.class);
			query.setParameter("recipe", recipeEntity);
			query.setParameter("language", language);
			for (Text text : query.getResultList()) {
				recipe.addText(new TextDto(text.getLanguage(), text.getContent(), text.getMetadata()));
			}

			for (Audio audio : recipeEntity.getAudios()) {
				List<String> audioUrls = new ArrayList<String>();
				audioUrls.addAll(Arrays.asList(audio.getUrls().split(";")));
				recipe.addAudio(new AudioDto(audio.getLanguage(), audioUrls));
			}

			for (Video video : recipeEntity.getVideos()) {
				List<SubtitleDto> subtitles = new ArrayList<SubtitleDto>();
				for (Subtitle subtitle : video.getSubtitles()) {
					subtitles.add(new SubtitleDto(subtitle.getLanguage(), subtitle.getUrl()));
				}

				List<String> videoUrls = new ArrayList<String>();
				videoUrls.addAll(Arrays.asList(video.getUrls().split(";")));
				recipe.addVideo(new VideoDto(video.getLanguage(), videoUrls, video.getPosterUrl(), subtitles));
			}

			for (Comment comment : recipeEntity.getComments()) {
				String user = comment.getUser() != null ? comment.getUser().getName() : "anonymous";
				recipe.addComment(new CommentDto(user, comment.getDate(), comment.getContent()));
			}

			return recipe;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
}

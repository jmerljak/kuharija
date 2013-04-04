package si.merljak.magistrska.server;

import static si.merljak.magistrska.server.model.QAppendix.appendix;
import static si.merljak.magistrska.server.model.QIngredient.ingredient;
import static si.merljak.magistrska.server.model.QRecipe.recipe;
import static si.merljak.magistrska.server.model.QRecipeDetails.recipeDetails;
import static si.merljak.magistrska.server.model.QRecipeIngredient.recipeIngredient;
import static si.merljak.magistrska.server.model.QRecipeText.recipeText;
import static si.merljak.magistrska.server.model.QRecipeTool.recipeTool;
import static si.merljak.magistrska.server.model.QTool.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.merljak.magistrska.common.dto.AppendixDto;
import si.merljak.magistrska.common.dto.AudioDto;
import si.merljak.magistrska.common.dto.CommentDto;
import si.merljak.magistrska.common.dto.QAppendixDto;
import si.merljak.magistrska.common.dto.QRecipeDetailsDto;
import si.merljak.magistrska.common.dto.QRecipeIngredientDto;
import si.merljak.magistrska.common.dto.QTextDto;
import si.merljak.magistrska.common.dto.QToolDto;
import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.dto.RecipeIngredientDto;
import si.merljak.magistrska.common.dto.SubtitleDto;
import si.merljak.magistrska.common.dto.TextDto;
import si.merljak.magistrska.common.dto.ToolDto;
import si.merljak.magistrska.common.dto.VideoDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.RecipeService;
import si.merljak.magistrska.server.model.Audio;
import si.merljak.magistrska.server.model.Comment;
import si.merljak.magistrska.server.model.Recipe;
import si.merljak.magistrska.server.model.Subtitle;
import si.merljak.magistrska.server.model.Video;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mysema.query.jpa.impl.JPAQuery;

public class RecipeServiceImpl extends RemoteServiceServlet implements RecipeService {

	private static final long serialVersionUID = 8165315606051808675L;

	private static final Logger log = LoggerFactory.getLogger(RecipeServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public RecipeDetailsDto getRecipeDetails(long recipeId, Language language) {
		log.debug("executing getRecipe for id: " + recipeId + " and language: " + language);

		Recipe recipeEntity = em.find(Recipe.class, recipeId);
		if (recipeEntity == null) {
			return null;
		}

		RecipeDetailsDto recipeDto = new JPAQuery(em).from(recipe)
												.where(recipe.eq(recipeEntity))
												.innerJoin(recipe.details, recipeDetails)
												.with(recipeDetails.language.eq(language))
												.uniqueResult(new QRecipeDetailsDto(recipe.id, recipeDetails.heading, 
													recipeDetails.subHeading, recipe.author, recipe.imageUrl, recipe.difficulty, 
													recipe.timePreparation, recipe.timeCooking, recipe.timeOverall,
													recipe.numberOfMeals, recipe.mealUnit));

		if (recipeDto == null) {
			// TODO try show recipe in the default language?
			return null;
		}

		// ingredients
		List<RecipeIngredientDto> ingredients = new JPAQuery(em).from(recipeIngredient)
							.where(recipeIngredient.recipe.eq(recipeEntity))
							.innerJoin(recipeIngredient.ingredient, ingredient)
							.orderBy(recipeIngredient.id.asc())
							.list(new QRecipeIngredientDto(ingredient.name, ingredient.imageUrl, recipeIngredient.unit, recipeIngredient.amount));
		recipeDto.setIngredients(ingredients);

		// tools
		List<ToolDto> tools = new JPAQuery(em).from(recipeTool)
							.where(recipeTool.recipe.eq(recipeEntity))
							.innerJoin(recipeTool.tool, tool)
							.orderBy(recipeTool.id.asc())
							.list(new QToolDto(tool.name, tool.imageUrl, recipeTool.quantity));
		recipeDto.setTools(tools);

		// texts
		List<TextDto> texts = new JPAQuery(em).from(recipeText)
							.where(recipeText.recipe.eq(recipeEntity), recipeText.language.eq(language))
							.list(new QTextDto(recipeText.language, recipeText.content, recipeText.metadata));
		recipeDto.setTexts(texts);

		// appendencies
		List<AppendixDto> appendencies = new JPAQuery(em).from(appendix)
						.where(appendix.recipe.eq(recipeEntity), appendix.language.eq(language))
						.list(new QAppendixDto(appendix.type, appendix.language, appendix.content));
		recipeDto.setAppendencies(appendencies);

		// audio
		for (Audio audio : recipeEntity.getAudios()) {
			List<String> audioUrls = new ArrayList<String>();
			audioUrls.addAll(Arrays.asList(audio.getUrls().split(";")));
			recipeDto.addAudio(new AudioDto(audio.getLanguage(), audioUrls));
		}

		// video
		for (Video video : recipeEntity.getVideos()) {
			List<SubtitleDto> subtitles = new ArrayList<SubtitleDto>();
			for (Subtitle subtitle : video.getSubtitles()) {
				subtitles.add(new SubtitleDto(subtitle.getLanguage(), subtitle.getUrl()));
			}

			List<String> videoUrls = new ArrayList<String>();
			videoUrls.addAll(Arrays.asList(video.getUrls().split(";")));
			recipeDto.addVideo(new VideoDto(video.getLanguage(), videoUrls, video.getPosterUrl(), subtitles));
		}

		// comments
		for (Comment comment : recipeEntity.getComments()) {
			String user = comment.getUser() != null ? comment.getUser().getName() : "anonymous";
			recipeDto.addComment(new CommentDto(user, comment.getDate(), comment.getContent()));
		}

		return recipeDto;
	}

	@Override
	public List<RecipeDetailsDto> getRecipes(Set<Long> recipeIdList, Language language) {
		log.debug("executing getRecipes for ids: " + recipeIdList.toString() + " and language: " + language);

		List<RecipeDetailsDto> list = new JPAQuery(em).from(recipe)
								.where(recipe.id.in(recipeIdList))
								.innerJoin(recipe.details, recipeDetails)
								.with(recipeDetails.language.eq(language))
								.list(new QRecipeDetailsDto(recipe.id, recipeDetails.heading, 
									recipeDetails.subHeading, recipe.author, recipe.imageUrl, recipe.difficulty, 
									recipe.timePreparation, recipe.timeCooking, recipe.timeOverall,
									recipe.numberOfMeals, recipe.mealUnit));
		
		for (RecipeDetailsDto r : list) {
			r.setIngredients(new JPAQuery(em).from(recipeIngredient)
								.where(recipeIngredient.recipe.id.eq(r.getId()))
								.innerJoin(recipeIngredient.ingredient, ingredient)
								.orderBy(recipeIngredient.id.asc())
								.list(new QRecipeIngredientDto(ingredient.name, ingredient.imageUrl, recipeIngredient.unit, recipeIngredient.amount)));
		}

		return list;
	}
}

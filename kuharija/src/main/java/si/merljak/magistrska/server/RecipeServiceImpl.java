package si.merljak.magistrska.server;

import static si.merljak.magistrska.server.model.QAppendix.appendix;
import static si.merljak.magistrska.server.model.QIngredient.ingredient;
import static si.merljak.magistrska.server.model.QProcedureStep.procedureStep;
import static si.merljak.magistrska.server.model.QRecipe.recipe;
import static si.merljak.magistrska.server.model.QRecipeDetails.recipeDetails;
import static si.merljak.magistrska.server.model.QRecipeIngredient.recipeIngredient;
import static si.merljak.magistrska.server.model.QRecipeText.recipeText;
import static si.merljak.magistrska.server.model.QRecipeUtensil.recipeUtensil;
import static si.merljak.magistrska.server.model.QUser.user;
import static si.merljak.magistrska.server.model.QUtensil.utensil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.merljak.magistrska.common.dto.AppendixDto;
import si.merljak.magistrska.common.dto.AudioDto;
import si.merljak.magistrska.common.dto.CommentDto;
import si.merljak.magistrska.common.dto.IngredientDto;
import si.merljak.magistrska.common.dto.QAppendixDto;
import si.merljak.magistrska.common.dto.QIngredientDto;
import si.merljak.magistrska.common.dto.QRecipeDetailsDto;
import si.merljak.magistrska.common.dto.QStepDto;
import si.merljak.magistrska.common.dto.QTextDto;
import si.merljak.magistrska.common.dto.QUtensilDto;
import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.dto.SubtitleDto;
import si.merljak.magistrska.common.dto.TextDto;
import si.merljak.magistrska.common.dto.UtensilDto;
import si.merljak.magistrska.common.dto.VideoDto;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.rpc.RecipeService;
import si.merljak.magistrska.server.model.Audio;
import si.merljak.magistrska.server.model.Comment;
import si.merljak.magistrska.server.model.Recipe;
import si.merljak.magistrska.server.model.Subtitle;
import si.merljak.magistrska.server.model.User;
import si.merljak.magistrska.server.model.Video;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * @author Jakob Merljak
 */
public class RecipeServiceImpl extends RemoteServiceServlet implements RecipeService {

	private static final long serialVersionUID = 8165315606051808675L;

	private static final Logger log = LoggerFactory.getLogger(RecipeServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction transaction;

	@Override
	public RecipeDetailsDto getRecipeDetails(long recipeId, Language language, String username) {
		log.debug("executing getRecipe for id: " + recipeId + " and language: " + language);

		Recipe recipeEntity = em.find(Recipe.class, recipeId);
		if (recipeEntity == null) {
			return null;
		}

		RecipeDetailsDto recipeDto = new JPAQuery(em).from(recipe)
												.where(recipe.eq(recipeEntity))
												.innerJoin(recipe.details, recipeDetails)
												.on(recipeDetails.language.eq(language))
												.uniqueResult(new QRecipeDetailsDto(recipe.id, recipeDetails.heading, 
													recipeDetails.subHeading, recipe.author, recipe.imageUrl, recipe.difficulty, 
													recipe.timePreparation, recipe.timeCooking, recipe.timeOverall,
													recipe.numberOfMeals, recipe.mealUnit, recipe.reviewStars));

		if (recipeDto == null) {
			// TODO try show recipe in the default language?
			return null;
		}

		// ingredients
		List<IngredientDto> ingredients = new JPAQuery(em).from(recipeIngredient)
							.where(recipeIngredient.recipe.eq(recipeEntity))
							.innerJoin(recipeIngredient.ingredient, ingredient)
							.orderBy(recipeIngredient.id.asc())
							.list(new QIngredientDto(ingredient.name, ingredient.imageUrl, recipeIngredient.unit, recipeIngredient.amount));
		recipeDto.setIngredients(ingredients);

		// utensils
		List<UtensilDto> utensils = new JPAQuery(em).from(recipeUtensil)
							.where(recipeUtensil.recipe.eq(recipeEntity))
							.innerJoin(recipeUtensil.utensil, utensil)
							.orderBy(recipeUtensil.id.asc())
							.list(new QUtensilDto(utensil.name, utensil.imageUrl, recipeUtensil.quantity));
		recipeDto.setUtensils(utensils);

		// texts
		List<TextDto> texts = new JPAQuery(em).from(recipeText)
							.where(recipeText.recipe.eq(recipeEntity), recipeText.language.eq(language))
							.list(new QTextDto(recipeText.language, recipeText.content, recipeText.metadata));
		recipeDto.setTexts(texts);

		// appendices
		List<AppendixDto> appendices = new JPAQuery(em).from(appendix)
						.where(appendix.recipe.eq(recipeEntity), appendix.language.eq(language))
						.list(new QAppendixDto(appendix.type, appendix.language, appendix.content));
		recipeDto.setAppendices(appendices);

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

		// steps
		recipeDto.setSteps(new JPAQuery(em).from(procedureStep)
								.where(procedureStep.recipe.id.eq(recipeId), procedureStep.language.eq(language))
								.orderBy(procedureStep.page.asc())
								.list(new QStepDto(procedureStep.language, procedureStep.page, procedureStep.content, procedureStep.imageUrl)));

		// comments
		for (Comment comment : recipeEntity.getComments()) {
			// TODO sort by date
			String user = comment.getUser() != null ? comment.getUser().getName() : "anonymous";
			recipeDto.addComment(new CommentDto(user, comment.getDate(), comment.getContent()));
		}

		// categories & seasons
		recipeDto.addCategories(recipeEntity.getCategories());
		recipeDto.addSeasons(recipeEntity.getSeasons());

		if (username != null) {
			recipeDto.setBookmarked(new JPAQuery(em).from(user)
								.where(user.bookmarks.any().eq(recipeEntity))
								.exists());
		}

		return recipeDto;
	}

	@Override
	public List<RecipeDetailsDto> getRecipes(Set<Long> recipeIdList, Language language) {
		log.debug("executing getRecipes for ids: " + recipeIdList.toString() + " and language: " + language);

		List<RecipeDetailsDto> list = new JPAQuery(em).from(recipe)
								.where(recipe.id.in(recipeIdList))
								.innerJoin(recipe.details, recipeDetails)
								.on(recipeDetails.language.eq(language))
								.list(new QRecipeDetailsDto(recipe.id, recipeDetails.heading, 
									recipeDetails.subHeading, recipe.author, recipe.imageUrl, recipe.difficulty, 
									recipe.timePreparation, recipe.timeCooking, recipe.timeOverall,
									recipe.numberOfMeals, recipe.mealUnit, recipe.reviewStars));
		
		for (RecipeDetailsDto recipeDto : list) {
			Recipe uniqueResult = new JPAQuery(em).from(recipe)
								.where(recipe.id.eq(recipeDto.getId())).uniqueResult(recipe);
			recipeDto.addSeasons(uniqueResult.getSeasons());
			recipeDto.addCategories(uniqueResult.getCategories());
			
//			recipeDto.setSeasons(new JPAQuery(em).from(recipe)
//					.where(recipe.id.eq(recipeDto.getId()))
//					.uniqueResult(recipe.seasons));
//
//			recipeDto.setCategories(new JPAQuery(em).from(recipe)
//								.where(recipe.id.eq(recipeDto.getId()))
//								.uniqueResult(recipe.categories));

			recipeDto.setIngredients(new JPAQuery(em).from(recipeIngredient)
								.where(recipeIngredient.recipe.id.eq(recipeDto.getId()))
								.innerJoin(recipeIngredient.ingredient, ingredient)
								.orderBy(recipeIngredient.id.asc())
								.list(new QIngredientDto(ingredient.name, ingredient.imageUrl, recipeIngredient.unit, recipeIngredient.amount)));
		}

		return list;
	}

	@Override
	public void bookmarkRecipe(String username, long recipeId, boolean add) {
		log.debug("user " + username + " bookmarking recipe " + recipeId);

		try {
			transaction.begin();
			Recipe recipeEntity = em.find(Recipe.class, recipeId);
			User userEntity = em.find(User.class, username);
			userEntity.addBookmarks(recipeEntity, add);
			em.persist(userEntity);
			transaction.commit();
		} catch (Exception e) {
			log.error("Could not " + (add ? "add" : "remove") + " bookmark!", e);
			throw new RuntimeException("Could not " + (add ? "add" : "remove") + " bookmark!");
		}
	}

	@Override
	public void commentRecipe(String username, long recipeId, String content) {
		log.debug("user " + username + " adding comment for recipe " + recipeId);

		try {
			Recipe recipeEntity = em.find(Recipe.class, recipeId);
			User userEntity = em.find(User.class, username);

			transaction.begin();
			em.persist(new Comment(userEntity, recipeEntity, content));
			transaction.commit();
		} catch (Exception e) {
			log.error("Could not save comment!", e);
			throw new RuntimeException("Could not save comment!");
		}
	}
}

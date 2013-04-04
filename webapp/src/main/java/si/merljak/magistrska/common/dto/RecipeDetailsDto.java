package si.merljak.magistrska.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.MealUnit;

import com.mysema.query.annotations.QueryProjection;

public class RecipeDetailsDto implements Serializable {

	private static final long serialVersionUID = 3663171145467465776L;

	private long id;
	private String heading;
	private String subHeading;
	private String author;
	private String imageUrl;
	private Difficulty difficulty;
	private String timePreparation;
	private String timeCooking;
	private String timeOverall;
	private int numberOfMeals;
	private MealUnit mealUnit;

	private List<RecipeIngredientDto> ingredients = new ArrayList<RecipeIngredientDto>();

	private List<ToolDto> tools = new ArrayList<ToolDto>();
	private List<TextDto> texts = new ArrayList<TextDto>();
	private List<AudioDto> audios = new ArrayList<AudioDto>();
	private List<VideoDto> videos = new ArrayList<VideoDto>();
	private List<CommentDto> comments = new ArrayList<CommentDto>();
	private List<AppendixDto> appendencies = new ArrayList<AppendixDto>();
	private List<StepDto> steps = new ArrayList<StepDto>();

	RecipeDetailsDto() {}

	@QueryProjection
	public RecipeDetailsDto(long id, String heading, String subHeading, String author, String imageUrl, Difficulty difficulty, 
			String timePreparation, String timeCooking, String timeOverall, int numberOfMeals, MealUnit mealUnit) {
		this.id = id;
		this.heading = heading;
		this.subHeading = subHeading;
		this.author = author;
		this.imageUrl = imageUrl;
		this.difficulty = difficulty;
		this.timePreparation = timePreparation;
		this.timeCooking = timeCooking;
		this.timeOverall = timeOverall;
		this.numberOfMeals = numberOfMeals;
		this.mealUnit = mealUnit;
	}

	public long getId() {
		return id;
	}

	public String getHeading() {
		return heading;
	}

	public String getSubHeading() {
		return subHeading;
	}

	public String getAuthor() {
		return author;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public int getNumberOfMeals() {
		return numberOfMeals;
	}

	public MealUnit getMealUnit() {
		return mealUnit;
	}

	public String getTimePreparation() {
		return timePreparation;
	}

	public String getTimeCooking() {
		return timeCooking;
	}

	public String getTimeOverall() {
		return timeOverall;
	}

	public void addIngredient(RecipeIngredientDto recipeIngredientDto) {
		ingredients.add(recipeIngredientDto);
	}

	public List<RecipeIngredientDto> getIngredients() {
		return ingredients;
	}

	public void addTool(ToolDto toolDto) {
		tools.add(toolDto);
	}

	public List<ToolDto> getTools() {
		return tools;
	}

	public void addText(TextDto textDto) {
		texts.add(textDto);
	}

	public List<TextDto> getTexts() {
		return texts;
	}

	public void setTexts(List<TextDto> texts) {
		this.texts = texts;
	}

	public void addAudio(AudioDto audioDto) {
		audios.add(audioDto);
	}

	public List<AudioDto> getAudios() {
		return audios;
	}

	public void addVideo(VideoDto videoDto) {
		videos.add(videoDto);
	}

	public List<VideoDto> getVideos() {
		return videos;
	}

	public void addComment(CommentDto commentDto) {
		comments.add(commentDto);
	}

	public List<CommentDto> getComments() {
		return comments;
	}

	public List<AppendixDto> getAppendencies() {
		return appendencies;
	}

	public void setAppendencies(List<AppendixDto> appendencies) {
		this.appendencies = appendencies;
	}

	public void addStep(StepDto step) {
		steps.add(step);
	}

	public List<StepDto> getSteps() {
		return steps;
	}

	public void setIngredients(List<RecipeIngredientDto> ingredients) {
		this.ingredients = ingredients;
	}

	public void setTools(List<ToolDto> tools) {
		this.tools = tools;
	}

	public void setVideos(List<VideoDto> videos) {
		this.videos = videos;
	}

}

package si.merljak.magistrska.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import si.merljak.magistrska.common.enumeration.Difficulty;

public class RecipeDto implements Serializable {

	private static final long serialVersionUID = 3663171145467465776L;

	private String title;
	private String author;
	private String imageUrl;
	private Difficulty difficulty;
	private String preparationTime;
	private int numberOfMeals;
	private String metaData;

	private List<RecipeIngredientDto> ingredients = new ArrayList<RecipeIngredientDto>();
	private List<ToolDto> tools = new ArrayList<ToolDto>();
	private List<TextDto> texts = new ArrayList<TextDto>();
	private List<AudioDto> audios = new ArrayList<AudioDto>();
	private List<VideoDto> videos = new ArrayList<VideoDto>();
	private List<CommentDto> comments = new ArrayList<CommentDto>();
	private List<AppendixDto> appendices = new ArrayList<AppendixDto>();
	private List<StepDto> steps = new ArrayList<StepDto>();

	RecipeDto() {}

	public RecipeDto(String title, String author, String imageUrl, Difficulty difficulty, 
			String preparationTime, int numberOfMeals, String metaData) {
		this.title = title;
		this.author = author;
		this.imageUrl = imageUrl;
		this.difficulty = difficulty;
		this.preparationTime = preparationTime;
		this.numberOfMeals = numberOfMeals;
		this.metaData = metaData;
	}

	public String getTitle() {
		return title;
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

	public String getPreparationTime() {
		return preparationTime;
	}

	public int getNumberOfMeals() {
		return numberOfMeals;
	}

	public String getMetaData() {
		return metaData;
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

	public void addAppendix(AppendixDto appendix) {
		appendices.add(appendix);
	}

	public List<AppendixDto> getAppendices() {
		return appendices;
	}

	public void addStep(StepDto step) {
		steps.add(step);
	}

	public List<StepDto> getSteps() {
		return steps;
	}

}

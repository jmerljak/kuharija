package si.merljak.magistrska.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import si.merljak.magistrska.enumeration.Difficulty;

@Entity
public class Recipe implements Serializable {

	private static final long serialVersionUID = 3131171680171379875L;

	@Id
	private long id;

	@NotNull
	private String title;

	@NotNull
	private String author;

	private String imageUrl;

	@Enumerated(EnumType.STRING)
	private Difficulty difficulty;

	@NotNull
	private String preparationTime;

	@NotNull
	private int numberOfMeals;

	@OneToMany(fetch=FetchType.LAZY, mappedBy = "recipe")
	private List<RecipeIngredient> ingredients;

    @OneToMany(fetch=FetchType.LAZY, mappedBy = "recipe")
	private List<RecipeTool> tools;

	@OneToMany(fetch=FetchType.LAZY)
	private List<Technique> techniques;

	@OneToMany(fetch=FetchType.LAZY, mappedBy = "recipe")
	private List<RecipeText> texts;

	@OneToMany(fetch=FetchType.LAZY, mappedBy = "recipe")
	private List<RecipeAudio> audios;

	@OneToMany(fetch=FetchType.LAZY, mappedBy = "recipe")
	private List<RecipeVideo> videos;

	@OneToMany(fetch=FetchType.LAZY)
	private List<Comment> comments;

	private String metaData;

	Recipe(long id, String title, String author, String imageUrl,
			Difficulty difficulty, String preparationTime, int numberOfMeals,
			List<RecipeIngredient> ingredients, List<RecipeTool> tools,
			List<Technique> techniques, List<RecipeText> texts,
			List<RecipeAudio> audioRecordings, List<RecipeVideo> videos,
			List<Comment> comments, String metaData) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.imageUrl = imageUrl;
		this.difficulty = difficulty;
		this.preparationTime = preparationTime;
		this.numberOfMeals = numberOfMeals;
		this.ingredients = ingredients;
		this.tools = tools;
		this.techniques = techniques;
		this.texts = texts;
		this.audios = audioRecordings;
		this.videos = videos;
		this.comments = comments;
		this.metaData = metaData;
	}

	public long getId() {
		return id;
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

	public List<RecipeIngredient> getIngredients() {
		return ingredients;
	}

	public List<RecipeTool> getTools() {
		return tools;
	}

	public List<Technique> getTechniques() {
		return techniques;
	}

	public List<RecipeText> getTexts() {
		return texts;
	}

	public List<RecipeAudio> getAudios() {
		return audios;
	}

	public List<RecipeVideo> getVideos() {
		return videos;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public String getMetaData() {
		return metaData;
	}
}

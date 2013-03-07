package si.merljak.magistrska.server.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import si.merljak.magistrska.common.enumeration.Difficulty;

@Entity
public class Recipe implements Serializable {

	private static final long serialVersionUID = -3699796639905108296L;

	@Id
	@GeneratedValue
	private long id;

	@NotNull
	private String title;

	@NotNull
	@Size(max = 50)
	private String author;

	private String imageUrl;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Difficulty difficulty;

	@NotNull
	private String preparationTime;

	@NotNull
	private int numberOfMeals;

	@OneToMany(fetch=FetchType.EAGER, mappedBy = "recipe")
	private Set<RecipeIngredient> ingredients;

    @OneToMany(fetch=FetchType.EAGER, mappedBy = "recipe")
	private Set<RecipeTool> tools;

	@OneToMany
	private Set<Technique> techniques;

	@OneToMany(fetch=FetchType.EAGER, mappedBy = "recipe")
	private Set<RecipeText> texts;

	@OneToMany(fetch=FetchType.EAGER, mappedBy = "recipe")
	private Set<RecipeAudio> audios;

	@OneToMany(fetch=FetchType.EAGER, mappedBy = "recipe")
	private Set<RecipeVideo> videos;

	@OneToMany(fetch=FetchType.EAGER, mappedBy = "recipe")
	private Set<Comment> comments;

	private String metaData;

	protected Recipe() {}

	public Recipe(String title, String author, String imageUrl,
			Difficulty difficulty, String preparationTime, int numberOfMeals, String metaData) {
		this.title = title;
		this.author = author;
		this.imageUrl = imageUrl;
		this.difficulty = difficulty;
		this.preparationTime = preparationTime;
		this.numberOfMeals = numberOfMeals;
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

	public Set<RecipeIngredient> getIngredients() {
		return ingredients;
	}

	public Set<RecipeTool> getTools() {
		return tools;
	}

	public Set<Technique> getTechniques() {
		return techniques;
	}

	public Set<RecipeText> getTexts() {
		return texts;
	}

	public Set<RecipeAudio> getAudios() {
		return audios;
	}

	public Set<RecipeVideo> getVideos() {
		return videos;
	}

	public Set<Comment> getComments() {
		return comments;
	}

	public String getMetaData() {
		return metaData;
	}
}

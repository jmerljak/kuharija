package si.merljak.magistrska.server.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.MealUnit;
import si.merljak.magistrska.common.enumeration.Season;

@Entity
public class Recipe implements Serializable {

	private static final long serialVersionUID = -3699796639905108296L;

	@Id
	@GeneratedValue
	private long id;

	@NotNull
	@Size(max = 50)
	private String author;

	private String imageUrl;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Difficulty difficulty;

	@NotNull
	private int numberOfMeals;

	@NotNull
	@Enumerated(EnumType.STRING)
	private MealUnit mealUnit;

	private Integer timePreparation;

	private Integer timeCooking;

	private Integer timeOverall;

	private String metadata;

	@OneToMany(mappedBy = "recipe")
	private Set<RecipeDetails> details;

	@OneToMany(mappedBy = "recipe")
	private Set<RecipeIngredient> ingredients;

    @OneToMany(mappedBy = "recipe")
	private Set<RecipeUtensil> utensils;

	@ManyToMany
	private Set<Technique> techniques;

	@OneToMany(mappedBy = "recipe")
	private Set<RecipeText> texts;

	@OneToMany(mappedBy = "recipe")
	private Set<ProcedureStep> steps;

	@OneToMany(mappedBy = "recipe")
	private Set<Appendix> appendices;

	@OneToMany(fetch=FetchType.EAGER, mappedBy = "recipe")
	private Set<RecipeAudio> audios;

	@OneToMany(fetch=FetchType.EAGER, mappedBy = "recipe")
	private Set<RecipeVideo> videos;

	@OneToMany(fetch=FetchType.EAGER, mappedBy = "recipe")
	private Set<Comment> comments;

	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Category.class)
	@Column(name="category")
	private Set<Category> categories;

	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Season.class)
	@Column(name="season")
	private Set<Season> seasons;

	protected Recipe() {}

	public Recipe(String author, String imageUrl,
			Difficulty difficulty, int numberOfMeals) {
		this.author = author;
		this.imageUrl = imageUrl;
		this.difficulty = difficulty;
		this.numberOfMeals = numberOfMeals;
	}

	public long getId() {
		return id;
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

	public Integer getTimePreparation() {
		return timePreparation;
	}

	public Integer getTimeCooking() {
		return timeCooking;
	}

	public Integer getTimeOverall() {
		return timeOverall;
	}

	public String getMetadata() {
		return metadata;
	}

	public Set<RecipeDetails> getDetails() {
		return details;
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
}

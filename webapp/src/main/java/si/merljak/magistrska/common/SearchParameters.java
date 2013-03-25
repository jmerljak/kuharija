package si.merljak.magistrska.common;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.enumeration.Season;

public class SearchParameters implements Serializable {

	private static final int PAGE_SIZE = 15;
	private static final long serialVersionUID = 3967849192762799943L;

	private String searchString;
	private int page = 1;
	private int pageSize = PAGE_SIZE;
	private Category category;
	private Season season;
	private Difficulty difficulty;
	private Set<String> ingredients = new HashSet<String>();
	private Language language;

	SearchParameters() {}

	public SearchParameters(String searchString, Language language) {
		this.searchString = searchString;
		this.language = language;
	}

	public SearchParameters(String searchString, Language language, int pageSize) {
		this.searchString = searchString;
		this.language = language;
		setPageSize(pageSize);
	}

	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page > 0 ? page : 1;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize > 0 ? pageSize : PAGE_SIZE;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Season getSeason() {
		return season;
	}

	public void setSeason(Season season) {
		this.season = season;
	}

	public Difficulty getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(Difficulty difficulty) {
		this.difficulty = difficulty;
	}

	public Set<String> getIngredients() {
		return ingredients;
	}

	public void addIngredient(String ingredient) {
		this.ingredients.add(ingredient);
	}

	public Language getLanguage() {
		return language;
	}
}

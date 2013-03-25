package si.merljak.magistrska.common;

import java.io.Serializable;
import java.util.List;

import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.enumeration.Season;

public class SearchParameters implements Serializable {

	private static final long serialVersionUID = 3967849192762799943L;

	private String searchString;
	private int page = 1;
	private int pageSize = 15;
	private Category category;
	private Season season;
	private Difficulty difficulty;
	private List<String> ingredients;
	private Language language;

	SearchParameters() {}

	public SearchParameters(String searchString, Language language) {
		this.searchString = searchString;
		this.language = language;
	}

	public SearchParameters(String searchString, Language language, int pageSize) {
		this.searchString = searchString;
		this.language = language;
		this.pageSize = pageSize;
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
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
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

	public List<String> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}

	public Language getLanguage() {
		return language;
	}
}

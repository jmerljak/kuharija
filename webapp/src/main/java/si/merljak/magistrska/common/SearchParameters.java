package si.merljak.magistrska.common;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.enumeration.RecipeSortKey;
import si.merljak.magistrska.common.enumeration.Season;

public class SearchParameters implements Serializable {

	private static final long serialVersionUID = 2847174317378294785L;

	public static final long DEFAULT_PAGE_SIZE = 15L;
	public static final RecipeSortKey DEFAULT_SORT_KEY = RecipeSortKey.ID;

	private String searchString;
	private long page = 1;
	private long pageSize = DEFAULT_PAGE_SIZE;
	private Set<Difficulty> difficulties = new HashSet<Difficulty>();
	private Set<Category> categories = new HashSet<Category>();
	private Set<Season> seasons = new HashSet<Season>();
	private Set<String> ingredients = new HashSet<String>();
	private String utensil;
	private Language language;
	private RecipeSortKey sortKey = DEFAULT_SORT_KEY;

	SearchParameters() {}

	public SearchParameters(String searchString, Language language) {
		this.searchString = searchString;
		this.language = language;
	}

	public SearchParameters(String searchString, Language language, long pageSize) {
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

	public long getPage() {
		return page;
	}

	public void setPage(long page) {
		this.page = page > 0 ? page : 1;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
	}

	public Set<Difficulty> getDifficulties() {
		return difficulties;
	}

	public void addDifficulty(Difficulty difficulty) {
		difficulties.add(difficulty);
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void addCategory(Category category) {
		categories.add(category);
	}

	public Set<Season> getSeasons() {
		return seasons;
	}

	public void addSeason(Season season) {
		seasons.add(season);
	}

	public Set<String> getIngredients() {
		return ingredients;
	}

	public void addIngredient(String ingredient) {
		ingredients.add(ingredient);
	}

	public String getUtensil() {
		return utensil;
	}

	public void setUtensil(String utensil) {
		this.utensil = utensil;
	}

	public Language getLanguage() {
		return language;
	}

	public RecipeSortKey getSortKey() {
		return sortKey;
	}

	public void setSortKey(RecipeSortKey sortKey) {
		this.sortKey = sortKey != null ? sortKey : DEFAULT_SORT_KEY;
	}
}

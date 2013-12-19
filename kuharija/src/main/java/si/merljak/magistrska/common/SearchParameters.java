package si.merljak.magistrska.common;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import si.merljak.magistrska.common.enumeration.Category;
import si.merljak.magistrska.common.enumeration.Difficulty;
import si.merljak.magistrska.common.enumeration.Language;
import si.merljak.magistrska.common.enumeration.RecipeSortKey;
import si.merljak.magistrska.common.enumeration.Season;

/**
 * Parameters for advanced search.
 * 
 * @author Jakob Merljak
 */
public class SearchParameters implements Serializable {

	private static final long serialVersionUID = 2817702799360224889L;

	public static final int DEFAULT_PAGE_SIZE = 8;
	public static final RecipeSortKey DEFAULT_SORT_KEY = RecipeSortKey.ID;

	private int page = 1;
	private int pageSize = DEFAULT_PAGE_SIZE;
	private String searchString;
	private Set<Difficulty> difficulties = new HashSet<Difficulty>(0);
	private Set<Category> categories = new HashSet<Category>(0);
	private Set<Season> seasons = new HashSet<Season>(0);
	private Set<String> ingredients = new HashSet<String>(0);
	private Set<String> utensils = new HashSet<String>(0);
	private Language language;
	private RecipeSortKey sortKey = DEFAULT_SORT_KEY;

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
		this.pageSize = pageSize > 0 ? pageSize : DEFAULT_PAGE_SIZE;
	}

	public Set<Difficulty> getDifficulties() {
		return difficulties;
	}

	public boolean addDifficulty(Difficulty difficulty) {
		return difficulties.add(difficulty);
	}

	public boolean removeDifficulty(Difficulty difficulty) {
		return difficulties.remove(difficulty);
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public boolean addCategory(Category category) {
		return categories.add(category);
	}

	public boolean removeCategory(Category category) {
		return categories.remove(category);
	}

	public Set<Season> getSeasons() {
		return seasons;
	}

	public boolean addSeason(Season season) {
		return seasons.add(season);
	}

	public boolean removeSeason(Season season) {
		return seasons.remove(season);
	}

	public Set<String> getIngredients() {
		return ingredients;
	}

	public boolean addIngredient(String ingredient) {
		return ingredients.add(ingredient);
	}

	public boolean removeIngredient(String ingredient) {
		return ingredients.remove(ingredient);
	}

	public Set<String> getUtensils() {
		return utensils;
	}

	public boolean addUtensil(String utensil) {
		return this.utensils.add(utensil);
	}

	public boolean removeUtensil(String utensil) {
		return this.utensils.remove(utensil);
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

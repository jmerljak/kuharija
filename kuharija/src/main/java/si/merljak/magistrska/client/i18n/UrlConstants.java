package si.merljak.magistrska.client.i18n;

import java.util.Map;

import com.google.gwt.i18n.client.Constants;

/**
 * 
 * @author Jakob Merljak
 *
 */
public interface UrlConstants extends Constants {

	@DefaultStringMapValue({
		"404", "Error",
		"home", "Home",
		"compare", "Recipe comparison",
		"ingredient", "Ingredient",
		"ingredients", "Ingredients",
		"lexicon", "Lexicon",
		"login", "Login",
		"recipe", "Recipe",
		"recipes", "Recipes",
		"search", "Search",
		"utensil", "Utensil",
		"utensils", "Utensils"})
	Map<String, String> screenNameMap();
}

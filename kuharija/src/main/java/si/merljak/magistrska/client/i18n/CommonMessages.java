package si.merljak.magistrska.client.i18n;

import com.google.gwt.i18n.client.Messages;

/** 
 * Common interface messages.
 * 
 * @author Jakob Merljak
 *
 */
public interface CommonMessages extends Messages {
	@DefaultMessage("Hello!")
	String hello();

	@DefaultMessage("Hello, {0}!")
	String helloUser(String name);

	@DefaultMessage("Oops!")
	String oops();

	@DefaultMessage("Page not found! Go to <a href=\"#\">home page</a> or try <a href=\"#search\">search</a>.")
	String pageNotFound();

	@DefaultMessage("There is some kind of problem. We apologize for inconvenience. Please, try again.")
	String unknownError();

	@DefaultMessage(" of {0}")
	String ofPages(Number allPages);

	@DefaultMessage("Ingredient not found. See <a href=\"#ingredients\">index of ingredients</a>.")
	String ingredientNotFoundTry();

	@DefaultMessage("Read more about {0} on <a href=\"http://en.wikipedia.org/w/index.php?search={1}\" target=\"_blank\">Wikipedia</a>")
	String ingredientReadMoreOnWikipedia(String name, String query);
	
	@DefaultMessage("Utensil not found. See <a href=\"#utensils\">index of utensils</a>.")
	String utensilNotFoundTry();

	@DefaultMessage("Read more about {0} on <a href=\"http://en.wikipedia.org/w/index.php?search={1}\" target=\"_blank\">Wikipedia</a>")
	String utensilReadMoreOnWikipedia(String name, String query);
	
	@DefaultMessage("Browse by category or <a href=\"#search\">search</a> by a keyword.")
	String browseOrSearchRecipes();

	@DefaultMessage("Find <a href=\"{1}\">recipes with {0}</a>")
	String searchByIngredient(String name, String href);

	@DefaultMessage("Find <a href=\"{1}\">recipes with {0}</a>")
	String searchByUtensil(String name, String href);

	@DefaultMessage("Recipe not found or has no content in english. Select some other language or try <a href=\"#search\">search</a>.")
	String recipeNotFoundTry();
	
	@DefaultMessage("There is no audio recordings for the recipe.")
	String recipeNoAudio();

	@DefaultMessage("There is no video recordings for the recipe.")
	String recipeNoVideo();

	@DefaultMessage("Unfortunatelly, your browser does not support HTML5 audio tag. We recommend <a href=\"http://browser-update.org/update.html\">upgrading your browser</a>.")
	String htmlAudioNotSupported();

	@DefaultMessage("Unfortunatelly, your browser does not support HTML5 video tag. We recommend <a href=\"http://browser-update.org/update.html\">upgrading your browser</a>.")
	String htmlVideoNotSupported();

	@DefaultMessage("{0}, posted {1}")
	String commentBy(String user, String date);
}

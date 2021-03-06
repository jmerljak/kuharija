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

	@DefaultMessage("Learn about cooking utensils, ingredients and more.")
	String lexiconIntro();

	@DefaultMessage("Ingredient not found. See <a href=\"#ingredients\">index of ingredients</a>.")
	String ingredientNotFoundTry();

	@DefaultMessage("Read more on Wikipedia")
	String ingredientReadMoreOnWikipedia();
	
	@DefaultMessage("Utensil not found. See <a href=\"#utensils\">index of utensils</a>.")
	String utensilNotFoundTry();

	@DefaultMessage("Read more on Wikipedia")
	String utensilReadMoreOnWikipedia();
	
	@DefaultMessage("Browse all recipes")
	String browseAllRecipes();
	
	@DefaultMessage("Login for recommendations")
	String loginForRecommendations();

	@DefaultMessage("Did you search for ...")
	String didYouSearchFor();

	@DefaultMessage("Find recipes with {0}")
	String searchByIngredient(String name);

	@DefaultMessage("Find recipes with {0}")
	String searchByUtensil(String name);

	@DefaultMessage("Recipe not found or has no content in english. Select some other language or try <a href=\"#search\">search</a>.")
	String recipeNotFoundTry();
	
	@DefaultMessage("There are no audio recordings for the recipe.")
	String recipeNoAudio();

	@DefaultMessage("There are no video recordings for the recipe.")
	String recipeNoVideo();

	@DefaultMessage("This recipe has not been reviewed yet.")
	String recipeNoReview();

	@DefaultMessage("{0} of 5 stars")
	String recipeReviewOutOfFive(int stars);

	@DefaultMessage("Procedure step number {0}.")
	String recipeStepNumber(int stepNumber);

	@DefaultMessage("Unfortunatelly, your browser does not support HTML5 audio tag. We recommend <a href=\"http://browser-update.org/update.html\">upgrading your browser</a>.")
	String htmlAudioNotSupported();

	@DefaultMessage("Unfortunatelly, your browser does not support HTML5 video tag. We recommend <a href=\"http://browser-update.org/update.html\">upgrading your browser</a>.")
	String htmlVideoNotSupported();
	
	@DefaultMessage(" of {0}")
	String ofPages(Number allPages);

	@DefaultMessage("{0}, posted {1}")
	String commentBy(String user, String date);

	@DefaultMessage("Did you know?")
	String tipDidYouKnow();

	@DefaultMessage("After setting search parameters, press the search button to refresh results.")
	String tipSearch();

	@DefaultMessage("You can move between steps using gestures or voice.")
	String tipRecipeGestures();

	@DefaultMessage("http://en.wikipedia.org/w/index.php?search={0}")
	String wikipediaSearchUrl(String query);
}

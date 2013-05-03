package si.merljak.magistrska.client.i18n;

import java.util.Map;

import com.google.gwt.i18n.client.Constants;

/** 
 * Common interface constants.
 * 
 * @author Jakob Merljak
 *
 */
public interface CommonConstants extends Constants {

	@DefaultStringValue("Cooking masterpieces")
	String appTitle();

	@DefaultStringValue("Home")
	String home();

	@DefaultStringValue("Page")
	String page();

	@DefaultStringValue("Remove")
	String remove();
	
	@DefaultStringValue("Previous page")
	String pagePrevious();
	
	@DefaultStringValue("Next page")
	String pageNext();
	
	@DefaultStringValue("First page")
	String pageFirst();
	
	@DefaultStringValue("Last page")
	String pageLast();

	@DefaultStringValue("Recipe")
	String recipe();

	@DefaultStringValue("Recipe comparison")
	String recipeComparison();

	@DefaultStringValue("Author")
	String recipeAuthor();

	@DefaultStringValue("Tools")
	String tools();

	@DefaultStringValue("Lexicon")
	String lexicon();

	@DefaultStringValue("Ingredients")
	String ingredients();

	@DefaultStringValue("Index of ingredients")
	String ingredientsIndex();

	@DefaultStringValue("Utensils")
	String utensils();

	@DefaultStringValue("Index of food preparation utensils")
	String utensilsIndex();

	@DefaultStringValue("Search")
	String search();

	@DefaultStringValue("Search string")
	String searchQuery();

	@DefaultStringValue("Advanced search")
	String searchFilters();

	@DefaultStringValue("Show")
	String searchFiltersShow();

	@DefaultStringValue("Clear and hide")
	String searchFiltersClear();

	@DefaultStringValue("No results")
	String searchNoResults();

	@DefaultStringValue("Search and add an ingredient")
	String searchAndAddIngredient();

	@DefaultStringValue("Search and add an utensil")
	String searchAndAddUtensil();
	
	@DefaultStringValue("Sort by")
	String sortBy();

	@DefaultStringValue("Preparation time")
	String timePreparation();

	@DefaultStringValue("Cooking time")
	String timeCooking();

	@DefaultStringValue("Time overall")
	String timeOverall();
	
	@DefaultStringValue("h")
	String timeHour();

	@DefaultStringValue("min")
	String timeMinute();

	@DefaultStringValue("Number of meals")
	String numberOfMeals();

	@DefaultStringValue("Use metric units")
	String metricUnits();

	@DefaultStringValue("Comments")
	String comments();

	@DefaultStringValue("Difficulty")
	String difficulty();

	@DefaultStringValue("Seasons")
	String seasons();

	@DefaultStringValue("Categories")
	String categories();

	@DefaultStringValue("Basic description")
	String tabBasic();

	@DefaultStringValue("Detailed description")
	String tabDetails();

	@DefaultStringValue("Video")
	String tabVideo();
	
	@DefaultStringValue("Audio")
	String tabAudio();

	@DefaultStringValue("Download audio")
	String downloadAudio();

	@DefaultStringValue("Download video")
	String downloadVideo();

	@DefaultStringValue("Download subtitle")
	String downloadSubtitle();

	// login
	@DefaultStringValue("Log in")
	String login();

	@DefaultStringValue("Log out")
	String logout();

	@DefaultStringValue("Username")
	String username();

	@DefaultStringValue("Password")
	String password();
	
	@DefaultStringValue("You are now logged in.")
	String loginSuccess();
	
	@DefaultStringValue("You are now logged out.")
	String logoutSuccess();


	// maps - enumerator constants

	@DefaultStringMapValue({
    	"INCORRECT_USERNAME_PASSWORD", "Incorrect username or password",
    	"SESSION_EXPIRED", "Session expired. Please, log in again",
    	"USERNAME_ALREADY_EXISTS", "Username already exists"})
    Map<String, String> loginErrorMap();

	@DefaultStringMapValue({
    	"A_EASY", "Easy",
    	"B_MODERATE", "Moderate",
    	"C_ADVANCED", "Advanced"})
    Map<String, String> difficultyMap();
	
	@DefaultStringMapValue({
		"SI", "Slovenščina",
		"EN", "English"})
	Map<String, String> languageMap();
	
	@DefaultStringMapValue({
		"G", "g",
		"KG", "kg",
		"OZ", "oz",
		"LB", "lb",
		"L", "l",
		"DL", "dl",
		"ML", "ml",
		"PT", "pt",
		"CUP", "cup",
		"TABLESPOON", "table spoon",
		"TEASPOON", "tea spoon",
		"C", "°C",
		"F", "°F",
		"PIECE", "x",
		"PAIR", "pair of",
		"PINCH", "pinch",
		"SOME", ""})
	Map<String, String> unitMap();
	
	@DefaultStringMapValue({
		"DIDYOUKNOW", "Did you know?",
		"HEALTHINFO", "Healty info",
		"IMPORTANT", "Important",
		"TIP", "Useful tip",
		"VARIATION", "Variation"})
	Map<String, String> appendixMap();
	
	@DefaultStringMapValue({
		"STARTER", "Starter",
		"SNACK", "Snack",
		"MAIN", "Main course",
		"DESSERT", "Desert",
		"SALAD", "Salad",
		"SIDE", "Side dish",
		"MEAT", "Meat",
		"SEAFOOD", "Seafood",
		"VEGETERIAN", "Vegeterian",
		"SPECIAL", "Special"})
	Map<String, String> categoryMap();
	
	@DefaultStringMapValue({
		"ALLYEAR", "All year",
		"SPRING", "Spring",
		"SUMMER", "summer",
		"AUTUMN", "Autumn",
		"WINTER", "Winter"})
	Map<String, String> seasonMap();

	@DefaultStringMapValue({
		"ID", "Default",
		"TITLE", "Title",
		"TIMEOVERALL", "Time overall",
		"DIFFICULTY", "Difficulty"})
	Map<String, String> sortKeyMap();
}

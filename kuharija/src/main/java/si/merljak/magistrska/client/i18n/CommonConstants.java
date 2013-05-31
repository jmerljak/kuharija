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

	@DefaultStringValue("Culinary Masterpieces")
	String appTitle();

	@DefaultStringValue("Home")
	String home();

	@DefaultStringValue("Language")
	String language();

	@DefaultStringValue("You are here")
	String youAreHere();

	@DefaultStringValue("Recommendations for you")
	String recommendations1();

	@DefaultStringValue("Have you tried ...")
	String recommendations2();

	@DefaultStringValue("Remove")
	String remove();

	@DefaultStringValue("Page")
	String page();
	
	@DefaultStringValue("Enter page number (only numbers)")
	String pageInput();
	
	@DefaultStringValue("Previous page")
	String pagePrevious();
	
	@DefaultStringValue("Next page")
	String pageNext();
	
	@DefaultStringValue("To beginning")
	String pageFirst();
	
	@DefaultStringValue("To end")
	String pageLast();

	@DefaultStringValue("Recipe")
	String recipe();

	@DefaultStringValue("Recipes")
	String recipes();

	@DefaultStringValue("Compare selected recipes")
	String recipeCompare();

	@DefaultStringValue("Select for comparison")
	String recipeComparisonSelect();

	@DefaultStringValue("Recipe comparison")
	String recipeComparison();

	@DefaultStringValue("Recipes by difficulty")
	String recipesByDifficulty();

	@DefaultStringValue("Procedure")
	String recipeProcedure();

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

	@DefaultStringValue("Search results")
	String searchResults();

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

	@DefaultStringValue("Increase number of meals")
	String numberOfMealsIncrease();

	@DefaultStringValue("Decrease number of meals")
	String numberOfMealsDecrease();
	
	@DefaultStringValue("Enter number of meals (only numbers)")
	String numberOfMealsInput();

	@DefaultStringValue("Use metric units")
	String metricUnits();

	@DefaultStringValue("Check to display metric units")
	String metricUnitsTooltip();

	@DefaultStringValue("Appendices")
	String appendices();

	@DefaultStringValue("Show appendices")
	String appendicesShow();

	@DefaultStringValue("Comments")
	String comments();

	@DefaultStringValue("There are no comments")
	String noComments();

	@DefaultStringValue("Difficulty")
	String difficulty();

	@DefaultStringValue("Seasons")
	String seasons();

	@DefaultStringValue("Categories")
	String categories();
	
	@DefaultStringValue("Previous step")
	String stepPrevious();
	
	@DefaultStringValue("Next step")
	String stepNext();

	@DefaultStringValue("Text")
	String tabBasic();

	@DefaultStringValue("Step by step")
	String tabSteps();

	@DefaultStringValue("Video")
	String tabVideo();
	
	@DefaultStringValue("Audio")
	String tabAudio();

	@DefaultStringValue("Download audio: ")
	String downloadAudio();

	@DefaultStringValue("Download video: ")
	String downloadVideo();

	@DefaultStringValue("Download subtitle: ")
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
	
	@DefaultStringValue("You are already logged in.")
	String loginSuccess();
	
	@DefaultStringValue("You are now logged in. In a few moments you will be redirected to previous page.")
	String loginSuccessAndRedirect();
	
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
		"TIMES", "x",
		"PAIR", "pair of",
		"PINCH", "pinch",
		"SOME", ""})
	Map<String, String> unitMap();

	@DefaultStringMapValue({
		"PERSON", "Persons",
		"PIECE", "Pieces",
		"SERVING_PLATE", "Serving plates",
		"BAKING_TRAY", "Baking trays"
	})
	Map<String, String> mealUnitMap();
	
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
		"ALLYEAR", "Throughout the year",
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

	@DefaultStringMapValue({
		"INGREDIENTS_FROM_FRIDGE", "From ingredients in your refrigerator",
		"USER_PREFERENCES", "Based on your preferences",
		"LOCAL_SEASON", "Seasonal",
		"LOCAL_TIME", "For the time of day",
		"LOCAL_SPECIALTY", "Local specialty",
		"FEATURED", "Featured"})
	Map<String, String> recommendationMap();
}

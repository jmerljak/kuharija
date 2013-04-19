package si.merljak.magistrska.client.i18n;

import java.util.Map;

import com.google.gwt.i18n.client.Constants;

public interface CommonConstants extends Constants {

	@DefaultStringValue("Cooking masterpieces")
	String appTitle();

	@DefaultStringValue("Recipe")
	String recipe();

	@DefaultStringValue("Recipe comparison")
	String recipeComparison();

	@DefaultStringValue("Author")
	String recipeAuthor();

	@DefaultStringValue("Tools")
	String tools();

	@DefaultStringValue("Search")
	String search();

	@DefaultStringValue("No results")
	String searchNoResults();

	@DefaultStringValue("Preparation time")
	String timePreparation();

	@DefaultStringValue("Cooking time")
	String timeCooking();

	@DefaultStringValue("Cooking time")
	String timeOverall();
	
	@DefaultStringValue("Number of meals")
	String numberOfMeals();

	@DefaultStringValue("Comments")
	String comments();

	@DefaultStringValue("Difficulty")
	String difficulty();

	@DefaultStringValue("Basic description")
	String tabBasic();

	@DefaultStringValue("Detailed description")
	String tabDetails();

	@DefaultStringValue("Video")
	String tabVideo();
	
	@DefaultStringValue("Audio")
	String tabAudio();

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
		"MAIN", "Main course",
		"DESERT", "Desert",
		"MEAT", "Meat",
		"VEGETERIAN", "Vegeterian"})
	Map<String, String> categoryMap();
	
	@DefaultStringMapValue({
		"ALLYEAR", "All year",
		"SPRING", "Spring",
		"SUMMER", "summer",
		"AUTUMN", "Autumn",
		"WINTER", "Winter"})
	Map<String, String> seasonMap();
}

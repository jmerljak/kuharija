package si.merljak.magistrska.client.i18n;

import java.util.Map;

import com.google.gwt.i18n.client.Constants;

public interface CommonConstants extends Constants {

	@DefaultStringValue("Recipe")
	String recipe();

	@DefaultStringValue("Recipe comparison")
	String recipeComparison();

	@DefaultStringValue("Author")
	String recipeAuthor();

	@DefaultStringValue("Ingredients")
	String ingredients();

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


	// maps - enumerator constants

	@DefaultStringMapValue({
    	"EASY", "Easy",
    	"MODERATE", "Moderate",
    	"ADVANCED", "Advanced"})
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
		"SPRING_EARLY", "Early spring",
		"SPRING_LATE", "Late spring",
		"SUMMER", "summer",
		"SUMMER_EARLY", "Early summer",
		"SUMMER_LATE", "Late summer",
		"AUTUMN", "Autumn",
		"AUTUMN_EARLY", "Early autumn",
		"AUTUMN_LATE", "Late autumn",
		"WINTER", "Winter",
		"WINTER_EARLY", "Early winter",
		"WINTER_LATE", "Late winter"})
	Map<String, String> seasonMap();
}

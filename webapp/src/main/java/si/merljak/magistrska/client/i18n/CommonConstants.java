package si.merljak.magistrska.client.i18n;

import java.util.Map;

import com.google.gwt.i18n.client.Constants;

public interface CommonConstants extends Constants {

	@DefaultStringValue("Recipe")
	String recipe();

	@DefaultStringValue("Author")
	String recipeAuthor();

	@DefaultStringValue("Ingredients")
	String ingredients();

	@DefaultStringValue("Tools")
	String tools();

	@DefaultStringValue("Search")
	String search();

	@DefaultStringValue("Preparation time")
	String preparationTime();
	
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
		"PINCH", "pinch"})
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
	
	@DefaultStringMapValue({
		"SPOON", "spoon",
		"FORK", "fork",
		"KNIFE", "knife",
		"KNIFE_SMALL", "knife (small)",
		"KNIFE_LARGE", "knife (large)",
		"KNIFE_WHEEL", "wheel knife",
		"COOKING_POT", "cooking pot",
		"BAKING_TRAY", "baking tray",
		"BAKING_TRAY_SMALL", "baking tray (small)",
		"BAKING_TRAY_LARGE", "baking tray (large)",
		"OVEN", "oven",
		"SQUEEZER", "squeezer",
		"BLENDER", "blender",
		"CUP", "cup",
		"ROLLING_PIN", "rolling pin",
		"PASTRY_BRUSH", "pastry brush",
		"TOOTHPICKS", "toothpicks",
		"PLATE", "plate",
		"PLATE_SERVING", "serving plate",
		"PLANK", "plank"})
	Map<String, String> toolsMap();
}

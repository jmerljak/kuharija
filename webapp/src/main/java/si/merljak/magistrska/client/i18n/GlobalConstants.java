package si.merljak.magistrska.client.i18n;

import java.util.Map;

import com.google.gwt.i18n.client.Constants;

public interface GlobalConstants extends Constants {

	@DefaultStringValue("Recipe")
	String recipe();

	@DefaultStringValue("Author")
	String recipeAuthor();

	@DefaultStringValue("Ingredients")
	String ingredients();

	@DefaultStringValue("Tools")
	String tools();

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
		"SUGAR", "sugar",
		"FLOUR", "flour",
		"SALT", "salt",
		"PEPPER", "peper",
		"OIL", "oil",
		"BUTTER", "butter",
		"EGGS", "eggs",
		"ONION", "onion",
		"VINEGAR", "vinegar",
		"MARGARINE", "margarine",
		"MILK", "milk",
		"OREGANO", "oregano",
		"BASIL", "basil",
		"GARLIC", "garlic",
		"VANILLA", "vanilla",
		"CINNAMON", "cinnamon",
		"CHEESE", "cheese",
		"PEAS", "peas",
		"CORN", "corn",
		"BEANS", "beans",
		"TUNA", "tuna",
		"MUSHROOMS", "mushrooms",
		"TOMATO", "tomato",
		"TOMATO_SAUCE", "tomato sauce",
		"BREAD", "bread",
		"BREAD_CRUMBS", "bread crumbs",
		"BAKING_POWDER", "baking powder",
		"SUGAR_POWDER", "sugar powder",
		"LEMON", "lemon",
		"LEMON_JUICE", "lemon juice",
		"ORANGE", "orange",
		"ORANGE_JUICE", "orange juice",
		"CLOVES", "cloves",
		"NUTMEG", "nutmeg",
		"CHIVES", "chives",
		"CELERY", "celery",
		"PARSLEY", "parsley",
		"SAGE", "sage",
		"CUCURBITA", "cucurbita",
		"KAKI", "kaki",
		"CREAM", "cream",
		"FRANKFURTERS", "frankfurters",
		"PUFF_PASTRY", "puff pastry",
		"MELON", "melon",
		"WATERMELON", "watermelon",
		"RICE", "rice",
		"PASTA", "pasta",
		"SALMON", "salmon",
		"COD", "cod",
		"WATER", "water",
		"SUGAR_CUBE", "sugar cube"})
	Map<String, String> ingredientMap();
	
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

package si.merljak.magistrska.client.i18n;

import java.util.Map;

import com.google.gwt.i18n.client.Constants;

public interface IngredientsConstants extends Constants {

	@DefaultStringValue("Ingredients")
	String ingredients();

	@DefaultStringValue("Index of ingredients")
	String ingredientsIndex();
	
	// maps - enumerator constants

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
		"SUGAR_DESC", "Sugar is the generalised name for a class of chemically-related sweet-flavored substances, most of which are used as food. They are carbohydrates, composed of carbon, hydrogen and oxygen. There are various types of sugar derived from different sources.",
		"FLOUR_DESC", "flour",
		"SALT_DESC", "salt",
		"PEPPER_DESC", "peper",
		"OIL_DESC", "oil",
		"BUTTER_DESC", "butter",
		"EGGS_DESC", "eggs",
		"ONION_DESC", "onion",
		"VINEGAR_DESC", "vinegar",
		"MARGARINE_DESC", "margarine",
		"MILK_DESC", "milk",
		"OREGANO_DESC", "oregano",
		"BASIL_DESC", "basil",
		"GARLIC_DESC", "garlic",
		"VANILLA_DESC", "vanilla",
		"CINNAMON_DESC", "cinnamon",
		"CHEESE_DESC", "cheese",
		"PEAS_DESC", "peas",
		"CORN_DESC", "corn",
		"BEANS_DESC", "beans",
		"TUNA_DESC", "tuna",
		"MUSHROOMS_DESC", "mushrooms",
		"TOMATO_DESC", "tomato",
		"TOMATO_SAUCE_DESC", "tomato sauce",
		"BREAD_DESC", "bread",
		"BREAD_CRUMBS_DESC", "bread crumbs",
		"BAKING_POWDER_DESC", "baking powder",
		"SUGAR_POWDER_DESC", "sugar powder",
		"LEMON_DESC", "lemon",
		"LEMON_JUICE_DESC", "lemon juice",
		"ORANGE_DESC", "orange",
		"ORANGE_JUICE_DESC", "orange juice",
		"CLOVES_DESC", "cloves",
		"NUTMEG_DESC", "nutmeg",
		"CHIVES_DESC", "chives",
		"CELERY_DESC", "celery",
		"PARSLEY_DESC", "parsley",
		"SAGE_DESC", "sage",
		"CUCURBITA_DESC", "cucurbita",
		"KAKI_DESC", "kaki",
		"CREAM_DESC", "cream",
		"FRANKFURTERS_DESC", "frankfurters",
		"PUFF_PASTRY_DESC", "puff pastry",
		"MELON_DESC", "melon",
		"WATERMELON_DESC", "watermelon",
		"RICE_DESC", "rice",
		"PASTA_DESC", "pasta",
		"SALMON_DESC", "salmon",
		"COD_DESC", "cod",
		"WATER_DESC", "water",
		"SUGAR_CUBE_DESC", "sugar cube"})
	Map<String, String> ingredientDescriptionMap();
}

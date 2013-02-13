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
		"sl_SI", "Slovenščina",
		"en_US", "English",
		"de_DE", "Deutsch"})
	Map<String, String> languageMap();
	
	@DefaultStringMapValue({
		"G", "g",
		"L", "l",
		"C", "°C",
		"K", "K",
		"PIECE", "x",
		"PINCH", "pinch"})
	Map<String, String> unitMap();
}

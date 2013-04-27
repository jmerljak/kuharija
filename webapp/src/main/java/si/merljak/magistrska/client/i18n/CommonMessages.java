package si.merljak.magistrska.client.i18n;

import com.google.gwt.i18n.client.Messages;

public interface CommonMessages extends Messages {
	@DefaultMessage("Hello!")
	String hello();

	@DefaultMessage("Hello, {0}!")
	String helloUser(String name);

	@DefaultMessage("Oops!")
	String oops();

	@DefaultMessage("Ingredient not found. See <a href=\"#ingredients\">index of ingredients</a>.")
	String ingredientNotFoundTry();

	@DefaultMessage("Read more about {0} on Wikipedia")
	String ingredientReadMoreOnWikipedia(String name);
	
	@DefaultMessage("Utensil not found. See <a href=\"#utensils\">index of utensils</a>.")
	String utensilNotFoundTry();

	@DefaultMessage("Read more about {0} on Wikipedia")
	String utensilReadMoreOnWikipedia(String name);

	@DefaultMessage("Find recipes with {0}")
	String searchByIngredient(String name);

	@DefaultMessage("Find recipes with {0}")
	String searchByUtensil(String name);

	@DefaultMessage("Unfortunatelly, your browser does not support HTML5 audio tag. We recommend <a href=\"http://browser-update.org/update.html\">upgrading your browser</a>.")
	String htmlAudioNotSupported();

	@DefaultMessage("Unfortunatelly, your browser does not support HTML5 video tag. We recommend <a href=\"http://browser-update.org/update.html\">upgrading your browser</a>.")
	String htmlVideoNotSupported();
}

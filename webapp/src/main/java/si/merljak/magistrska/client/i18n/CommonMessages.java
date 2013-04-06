package si.merljak.magistrska.client.i18n;

import com.google.gwt.i18n.client.Messages;

public interface CommonMessages extends Messages {
	@DefaultMessage("Oops!")
	String oops();

	@DefaultMessage("Ingredient not found. Try <a href=\"#search&q={0}\">search</a> or see <a href=\"#ingredients\">index of ingredients</a>.")
	String ingredientNotFoundTry(String query);

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

	@DefaultMessage("Unfortunatelly, your browser does not support HTML5 audio tag.")
	String htmlAudioNotSupported();

	@DefaultMessage("Unfortunatelly, your browser does not support HTML5 video tag.")
	String htmlVideoNotSupported();
}

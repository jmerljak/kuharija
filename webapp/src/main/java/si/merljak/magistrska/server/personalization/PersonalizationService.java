package si.merljak.magistrska.server.personalization;

import java.util.List;

import si.merljak.magistrska.common.enumeration.Season;

public interface PersonalizationService {

	/** 
	 * Returns list of ingredients from user's smart internet-connected refrigerator.
	 * 
	 * @param username
	 * @return list of ingredient names or {@code null} if user not found or connection could not be established
	 */
	List<String> getIngredientsFromFridge(String username);

	/**
	 * Determines local season based on current user coordinates (if available) or user profile.
	 * 
	 * @param username
	 * @param latitude
	 * @return local season or {@code null} if neither username nor latitude specified
	 */
	Season getLocalSeason(String username, Double latitude);

	/**
	 * Determines local time of day based on current user coordinates (if available) or timezone from user profile.
	 * 
	 * @param username
	 * @param latitude
	 * @param longitude
	 * @return local time of day string or {@code null} if neither username nor coordinates specified
	 */
	String getLocalTime(String username, Double latitude, Double longitude);

	/**
	 * Determines origin country based on current user coordinates (if available) or user profile settings.
	 * 
	 * @param username
	 * @param latitude
	 * @param longitude
	 * @return coutry code string or {@code null} if neither username nor coordinates specified
	 */
	String getCounty(String username, Double latitude, Double longitude);

	/**
	 * Recommends recipe based on user profile (habits, preferences ...)
	 * 
	 * @param username
	 * @return recommendation search string or {@code null} if user profile information could not be found
	 */
	String recommendRecipe(String username);


	// other methods for building, maintaining and extracting user profile
	// ...
}

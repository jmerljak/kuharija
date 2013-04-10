package si.merljak.magistrska.server.personalization;

import java.io.IOException;
import java.util.List;

import org.geonames.WebService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.merljak.magistrska.common.enumeration.Season;

/**
 * This is a mock implementation of personalization service.
 * Real personalization system would build and maintain user profile,
 * extract real user preferences and habits, detect circumstances, 
 * connect to user's smart kitchen devices etc.
 * 
 * @author Jakob Merljak
 *
 */
public class PersonalizationServiceMock implements PersonalizationService {

	private static final Logger log = LoggerFactory.getLogger(PersonalizationServiceMock.class);

	@Override
	public List<String> getIngredientsFromFridge(String username) {
		if (username == null) {
			return null;
		}

		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String recommendRecipe(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Season getLocalSeason(String username, Double latitude) {
		boolean southernHemisphere = false;
		if (latitude != null) {
			southernHemisphere = latitude.doubleValue() < 0.0;
		} else if (username != null) {
			// TODO read from database
		} else {
			return null;
		}

		DateTime date = new DateTime();
		if (southernHemisphere) {
			// very dumb way to calculate season on southern hemisphere :)
			date.plusMonths(6);
		}

		int monthOfYear = date.getMonthOfYear();
		log.info("month: " + monthOfYear);
		switch(monthOfYear) {
			case 12:
			case 1:
			case 2:
				return Season.WINTER;
			case 3:
			case 4:
			case 5:
				return Season.SPRING;
			case 6:
			case 7:
			case 8:
				return Season.SUMMER;
			case 9:
			case 10:
			case 11:
				return Season.AUTUMN;
			default:
				return null;
		}
	}

	@Override
	public String getLocalTime(String username, Double latitude, Double longitude) {
		String timezoneID = null;
		if (latitude != null && longitude != null) {
			try {
				timezoneID = WebService.timezone(latitude, longitude).getTimezoneId();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if (username != null) {
			// TODO read from database
			timezoneID = "CET";
		} else {
			return null;
		}

		// TODO Auto-generated method stub
		DateTime date = new DateTime(DateTimeZone.forID(timezoneID));
		return "midday";
	}

	@Override
	public String getCounty(String username, Double latitude, Double longitude) {
		log.debug("location of the user: " + latitude + ", " + longitude);
	
		try {
			return WebService.countryCode(latitude, longitude);
		} catch (IOException e) {
			return null;
		}
	}

}

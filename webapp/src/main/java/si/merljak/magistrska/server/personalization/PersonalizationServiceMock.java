package si.merljak.magistrska.server.personalization;

import static si.merljak.magistrska.server.model.QUser.user;
import static si.merljak.magistrska.server.model.mock.QFridge.fridge;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;

import org.geonames.WebService;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.merljak.magistrska.common.enumeration.Season;

import com.mysema.query.jpa.impl.JPAQuery;


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
	
	private EntityManager em;

	public PersonalizationServiceMock(EntityManager entityManager) {
		this.em = entityManager;
	}

	@Override
	public List<String> getIngredientsFromFridge(String username) {
		if (username == null) {
			return null;
		}

		return new JPAQuery(em)
					.from(fridge)
					.where(fridge.user.username.eq(username))
					.list(fridge.ingredient.name);
	}

	@Override
	public String recommendRecipe(String username) {
		if (username == null) {
			return null;
		}

		String preferences = new JPAQuery(em)
							.from(user)
							.where(user.username.eq(username))
							.uniqueResult(user.preferences);

		return getProperty(preferences, "recommended");
	}

	@Override
	public Season getLocalSeason(String username, Double latitude, Double longitude) {
		boolean southernHemisphere = false;
		if (latitude != null) {
			southernHemisphere = latitude.doubleValue() < 0.0;
		} else if (username != null) {
			southernHemisphere = new JPAQuery(em)
								.from(user)
								.where(user.username.eq(username)
								  .and(user.preferences.contains("hemisphere=southern")))
								.exists();
		} else {
			return null;
		}

		DateTime date = new DateTime();
		if (southernHemisphere) {
			// not very smart way to calculate seasons in the southern hemisphere :)
			// just add half a year
			date = date.plusMonths(6);
		}

		// dumb way to determine season :)
		switch(date.getMonthOfYear()) {
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
		log.debug("getting local time for user " + user + " at " + latitude + ", " + longitude);

		try {
			String timezoneID = null;
			if (latitude != null && longitude != null) {
				timezoneID = WebService.timezone(latitude, longitude).getTimezoneId();
			} else if (username != null) {
				String preferences = new JPAQuery(em)
									.from(user)
									.where(user.username.eq(username))
									.uniqueResult(user.preferences);

				timezoneID = getProperty(preferences, "timezone");
			} else {
				return null;
			}

			DateTime date = new DateTime(DateTimeZone.forID(timezoneID));
			int hourOfDay = date.getHourOfDay();
			if (hourOfDay < 6 || hourOfDay > 22) {
				return "night";
			} else if (hourOfDay < 11) {
				return "morning";
			} else if (hourOfDay < 18) {
				return "midday";
			} else {
				return "evening";
			}
		} catch (Exception e) {
			log.error("Could not get local time.", e);
			return null;
		}
	}

	@Override
	public String getCountry(String username, Double latitude, Double longitude) {
		log.debug("getting country code for user " + user + " at " + latitude + ", " + longitude);
	
		try {
			if (latitude != null && longitude != null) {
				return WebService.countryCode(latitude.doubleValue(), longitude.doubleValue());
			} else if (username != null) {
				String preferences = new JPAQuery(em)
									.from(user)
									.where(user.username.eq(username))
									.uniqueResult(user.preferences);
				
				return getProperty(preferences, "country");
			} else {
				return null;
			}
		} catch (IOException e) {
			log.error("Could not get country code.", e);
			return null;
		}
	}

	/** 
	 * Gets property value from <em>key1=value1;key2=value2;...</em> encoded string
	 * 
	 * @param properties encoded string
	 * @param propertyName name of the property
	 * @return property value or {@code null} if property not found
	 */
	private static String getProperty(String properties, String propertyName) {
		for (String property : properties.split(";")) {
			String[] param = property.split("=");
			if (param.length == 2 && param[0].equalsIgnoreCase(propertyName)) {
				return param[1];
			}
		}
		return null;
	}
}

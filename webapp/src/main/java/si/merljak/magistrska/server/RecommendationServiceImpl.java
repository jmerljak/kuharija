package si.merljak.magistrska.server;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.geonames.WebService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.merljak.magistrska.common.dto.RecipeDto;
import si.merljak.magistrska.common.rpc.RecommendationService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class RecommendationServiceImpl extends RemoteServiceServlet implements RecommendationService {

	private static final long serialVersionUID = 1636894033893317781L;

	private static final Logger log = LoggerFactory.getLogger(RecommendationServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<RecipeDto> recommendRecipesForUser() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RecipeDto> recommendSimmilarRecipes(long recipeId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String geolocate(double latitude, double longitude) {
		log.debug("location of the user: " + latitude + ", " + longitude);
	
		try {
			return WebService.countryCode(latitude, longitude);
		} catch (IOException e) {
			return "";
		}
	}
}

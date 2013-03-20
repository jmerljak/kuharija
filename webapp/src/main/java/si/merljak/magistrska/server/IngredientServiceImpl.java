package si.merljak.magistrska.server;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.merljak.magistrska.client.rpc.IngredientService;
import si.merljak.magistrska.common.dto.IngredientDto;
import si.merljak.magistrska.server.model.Ingredient;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class IngredientServiceImpl extends RemoteServiceServlet implements IngredientService {

	private static final long serialVersionUID = -6076926785869869872L;

	private static final Logger log = LoggerFactory.getLogger(IngredientServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	public IngredientDto getIngredient(String name) {
		log.info("executing getIngredient: " + name);

		try {
			TypedQuery<Ingredient> query = em.createQuery("SELECT i FROM Ingredient i WHERE i.name = :name", Ingredient.class);
			query.setParameter("name", name);
			Ingredient recipeEntity = query.getSingleResult();
			return new IngredientDto(recipeEntity.getName(), recipeEntity.getImageUrl());
		} catch (NoResultException e) {
			return null;
		}
	}

}

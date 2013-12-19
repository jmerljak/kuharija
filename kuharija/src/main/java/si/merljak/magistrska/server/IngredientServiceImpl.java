package si.merljak.magistrska.server;

import static si.merljak.magistrska.server.model.QIngredient.ingredient;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.merljak.magistrska.common.dto.IngredientDto;
import si.merljak.magistrska.common.dto.QIngredientDto;
import si.merljak.magistrska.common.rpc.IngredientService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mysema.query.jpa.impl.JPAQuery;

/**
 * @author Jakob Merljak
 */
public class IngredientServiceImpl extends RemoteServiceServlet implements IngredientService {

	private static final long serialVersionUID = -1157324345144797122L;

	private static final Logger log = LoggerFactory.getLogger(IngredientServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	public IngredientDto getIngredient(String name) {
		log.debug("executing getIngredient: " + name);

		return new JPAQuery(em)
					.from(ingredient)
					.where(ingredient.name.equalsIgnoreCase(name))
					.uniqueResult(new QIngredientDto(ingredient.name, ingredient.imageUrl));
	}

}

package si.merljak.magistrska.server;

import static si.merljak.magistrska.server.model.QUtensil.utensil;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.merljak.magistrska.common.dto.QUtensilDto;
import si.merljak.magistrska.common.dto.UtensilDto;
import si.merljak.magistrska.common.rpc.UtensilService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mysema.query.jpa.impl.JPAQuery;

public class UtensilServiceImpl extends RemoteServiceServlet implements UtensilService {

	private static final long serialVersionUID = -6657779731960840808L;

	private static final Logger log = LoggerFactory.getLogger(UtensilServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Override
	public UtensilDto getUtensil(String name) {
		log.debug("executing getUtensil: " + name);

		return new JPAQuery(em)
					.from(utensil)
					.where(utensil.name.equalsIgnoreCase(name))
					.uniqueResult(new QUtensilDto(utensil.name, utensil.imageUrl));
	}

}

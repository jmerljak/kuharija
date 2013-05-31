package si.merljak.magistrska.server.mock;

import static si.merljak.magistrska.server.model.mock.QWOzAction.wOzAction;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.merljak.magistrska.common.rpc.mock.WOzService;
import si.merljak.magistrska.server.model.mock.WOzAction;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mysema.query.jpa.impl.JPADeleteClause;
import com.mysema.query.jpa.impl.JPAQuery;

public class WOzServiceImpl extends RemoteServiceServlet implements WOzService {

	private static final long serialVersionUID = -1972686679692469943L;

	private static final Logger log = LoggerFactory.getLogger(WOzServiceImpl.class);

	@PersistenceContext
	private EntityManager em;

	@Resource
	private UserTransaction transaction;

	@Override
	public List<String> getActions() {
		log.debug("Getting commands from a wizard ...");

		List<String> actionList = null;
		try {
			transaction.begin();
			actionList = new JPAQuery(em).from(wOzAction).orderBy(wOzAction.id.asc()).list(wOzAction.action);
			// delete actions
			new JPADeleteClause(em, wOzAction).execute();
			transaction.commit();
		} catch (Exception e) {
			log.error("Could not get action!", e);
			// do not throw exception
		}

		return actionList;
	}

	@Override
	public void setAction(String action) {
		log.debug("Saving command from a wizard ...");

		try {
			transaction.begin();
//			TODO override old actions?
//			new JPADeleteClause(em, wOzAction).execute();
			// insert new action
			em.persist(new WOzAction(action));
			transaction.commit();
		} catch (Exception e) {
			log.error("Could not insert action!", e);
			throw new RuntimeException("Could not insert action!");
		}
	}
}

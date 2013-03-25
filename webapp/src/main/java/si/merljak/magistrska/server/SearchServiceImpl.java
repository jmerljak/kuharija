package si.merljak.magistrska.server;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import si.merljak.magistrska.client.rpc.SearchService;
import si.merljak.magistrska.common.SearchParameters;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class SearchServiceImpl extends RemoteServiceServlet implements SearchService {

	private static final long serialVersionUID = 6742097745368981008L;

	private static final Logger log = LoggerFactory.getLogger(SearchServiceImpl.class);
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<String> basicSearch(String searchString, int page, int pageSize) {
		log.info("searchinge for: " + searchString + ", page: " + page + ", pageSize: " + pageSize);
		// TODO Auto-generated method stub
		return new ArrayList<String>(Arrays.asList("polde", "jure", "miha"));
		
	}

	@Override
	public List<String> search(SearchParameters searchParameters) {
		// TODO Auto-generated method stub
		return null;
	}

}

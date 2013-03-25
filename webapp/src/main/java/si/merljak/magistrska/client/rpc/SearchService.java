package si.merljak.magistrska.client.rpc;

import java.util.List;

import si.merljak.magistrska.common.SearchParameters;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("search")
public interface SearchService extends RemoteService {
	List<String> basicSearch(String searchString, int page, int pageSize);

	List<String> search(SearchParameters searchParameters);
}

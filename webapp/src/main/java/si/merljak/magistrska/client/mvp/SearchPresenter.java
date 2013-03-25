package si.merljak.magistrska.client.mvp;

import java.util.List;
import java.util.Map;

import si.merljak.magistrska.client.KuharijaEntry;
import si.merljak.magistrska.common.enumeration.Language;

import com.google.gwt.user.client.rpc.AsyncCallback;

public class SearchPresenter extends AbstractPresenter {

    private SearchView searchView = new SearchView();

	public SearchPresenter(Language language) {
		super(language);
	}

	@Override
	protected boolean isPresenterForScreen(String screenName) {
		return screenName != null && screenName.equalsIgnoreCase("search");
	}

	@Override
	protected void parseParameters(String screenName, Map<String, String> parameters) {
		if (parameters.containsKey("q")) {
			try {
				search(parameters.get("q"));
			} catch (Exception e) {

			}
		}
	}

	private void search(String searchString) {
		KuharijaEntry.searchService.basicSearch(searchString, 1, 15, new AsyncCallback<List<String>>() {
			@Override
			public void onSuccess(List<String> results) {
				searchView.displaySearchResults(results);
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	@Override
	protected void hideView() {
		searchView.hide();
	}

}

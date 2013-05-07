package si.merljak.magistrska.client.mvp.recipe;

import java.util.Map;

import si.merljak.magistrska.client.mvp.AbstractPresenter;
import si.merljak.magistrska.client.mvp.home.HomePresenter;
import si.merljak.magistrska.common.enumeration.Language;

import com.google.gwt.user.client.ui.Widget;

/**
 * Presenter for recipe index.
 * 
 * @author Jakob Merljak
 * 
 */
public class RecipeIndexPresenter extends AbstractPresenter {

	// screen and parameters name
	public static final String SCREEN_NAME = "recipes";

	// view
	private final RecipeIndexView view = new RecipeIndexView();

	public RecipeIndexPresenter(Language language) {
		super(language);
	}

	@Override
	public Widget parseParameters(Map<String, String> parameters) {
		return view.asWidget();
	}

	@Override
	public String getScreenName() {
		return SCREEN_NAME;
	}

	@Override
	public String getParentName() {
		return HomePresenter.SCREEN_NAME;
	}

}

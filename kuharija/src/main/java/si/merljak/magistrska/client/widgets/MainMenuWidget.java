package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.client.mvp.home.HomePresenter;
import si.merljak.magistrska.client.mvp.ingredient.IngredientIndexPresenter;
import si.merljak.magistrska.client.mvp.search.SearchPresenter;
import si.merljak.magistrska.client.mvp.utensil.UtensilIndexPresenter;

import com.github.gwtbootstrap.client.ui.NavHeader;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.NavTabs;
import com.github.gwtbootstrap.client.ui.ResponsiveNavbar;
import com.github.gwtbootstrap.client.ui.constants.NavbarPosition;
import com.google.gwt.user.client.ui.Composite;

public class MainMenuWidget extends Composite {

	// i18n
	private final CommonConstants constants = Kuharija.constants;

	public MainMenuWidget() {
		NavTabs navbarlist = new NavTabs();
		navbarlist.add(new NavLink(constants.home(), "#" + HomePresenter.SCREEN_NAME));
		navbarlist.add(new NavLink(constants.search(), "#" + SearchPresenter.SCREEN_NAME));
		navbarlist.add(new NavLink(constants.ingredients(), "#" + IngredientIndexPresenter.SCREEN_NAME));
		navbarlist.add(new NavLink(constants.utensils(), "#" + UtensilIndexPresenter.SCREEN_NAME));

		ResponsiveNavbar navbar = new ResponsiveNavbar();
		navbar.setPosition(NavbarPosition.STATIC_TOP);
		navbar.add(new NavHeader(constants.appTitle()));
		navbar.add(navbarlist);
		initWidget(navbar);
	}
}

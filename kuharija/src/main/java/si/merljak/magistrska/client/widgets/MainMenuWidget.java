package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.event.LoginEvent;
import si.merljak.magistrska.client.event.LoginEventHandler;
import si.merljak.magistrska.client.handler.LogoutHandler;
import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.client.mvp.home.HomePresenter;
import si.merljak.magistrska.client.mvp.ingredient.IngredientIndexPresenter;
import si.merljak.magistrska.client.mvp.login.LoginPresenter;
import si.merljak.magistrska.client.mvp.search.SearchPresenter;
import si.merljak.magistrska.client.mvp.utensil.UtensilIndexPresenter;
import si.merljak.magistrska.common.dto.UserDto;

import com.github.gwtbootstrap.client.ui.Nav;
import com.github.gwtbootstrap.client.ui.NavCollapse;
import com.github.gwtbootstrap.client.ui.NavHeader;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.ResponsiveNavbar;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.github.gwtbootstrap.client.ui.constants.Alignment;
import com.github.gwtbootstrap.client.ui.constants.Device;
import com.github.gwtbootstrap.client.ui.constants.NavbarPosition;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Composite;

/**
 * Main menu.
 * 
 * @author Jakob Merljak
 *
 */
public class MainMenuWidget extends Composite implements LoginEventHandler, SearchWidget.SearchHandler {

	// i18n
	private final CommonConstants constants = Kuharija.constants;

	// widgets
	private final Nav mainNav = new Nav();
	private final Nav navRight = new Nav();
	private final NavLink loginLink = new NavLink(constants.login(), LoginPresenter.getLoginUrl());
	private final NavLink logoutButton = new NavLink(constants.logout(), LoginPresenter.getLoginUrl());
	private final SearchWidget searchWidget = new SearchWidget(this);

	// handler
	private final LogoutHandler handler;

	public MainMenuWidget(LogoutHandler logoutHandler, EventBus eventBus) {
		this.handler = logoutHandler;
		eventBus.addHandler(LoginEvent.TYPE, this);

		// header
		NavHeader header = new NavHeader(constants.appTitle());
		header.setHideOn(Device.DESKTOP);

		// main navigation
		mainNav.add(new NavLink(constants.home(), "#" + HomePresenter.SCREEN_NAME));
		mainNav.add(new NavLink(constants.ingredients(), "#" + IngredientIndexPresenter.SCREEN_NAME));
		mainNav.add(new NavLink(constants.utensils(), "#" + UtensilIndexPresenter.SCREEN_NAME));

		// right buttons (login)
		navRight.setAlignment(Alignment.RIGHT);
		navRight.add(new ListItem(searchWidget));
		navRight.add(loginLink);

		logoutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (handler != null) {
					handler.logout();
				}
			}
		});

		// collapse navigation
		NavCollapse navCollapse = new NavCollapse();
		navCollapse.add(mainNav);
		navCollapse.add(navRight);

		// responsive navbar
		ResponsiveNavbar navbar = new ResponsiveNavbar();
		navbar.setInverse(true);
		navbar.setPosition(NavbarPosition.TOP);
		navbar.add(header);
		navbar.add(navCollapse);
		initWidget(navbar);
	}

	@Override
	public void onLogin(LoginEvent event) {
		UserDto user = event.getUser();
		if (user != null) {
			logoutButton.setText(constants.logout() + " (" + user.getName() + ")");
			navRight.remove(loginLink);
			navRight.add(logoutButton);
		} else {
			navRight.remove(logoutButton);
			navRight.add(loginLink);
		}
	}

	@Override
	public void doSearch() {
		SearchPresenter.doSearch(searchWidget.getText());
		searchWidget.clear();
	}
}

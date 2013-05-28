package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.event.LoginEvent;
import si.merljak.magistrska.client.event.LoginEventHandler;
import si.merljak.magistrska.client.handler.LogoutHandler;
import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.client.i18n.CommonMessages;
import si.merljak.magistrska.client.mvp.home.HomePresenter;
import si.merljak.magistrska.client.mvp.lexicon.LexiconPresenter;
import si.merljak.magistrska.client.mvp.login.LoginPresenter;
import si.merljak.magistrska.client.mvp.recipe.RecipeIndexPresenter;
import si.merljak.magistrska.common.dto.UserDto;

import com.github.gwtbootstrap.client.ui.Nav;
import com.github.gwtbootstrap.client.ui.NavCollapse;
import com.github.gwtbootstrap.client.ui.NavHeader;
import com.github.gwtbootstrap.client.ui.NavLink;
import com.github.gwtbootstrap.client.ui.NavText;
import com.github.gwtbootstrap.client.ui.ResponsiveNavbar;
import com.github.gwtbootstrap.client.ui.constants.Alignment;
import com.github.gwtbootstrap.client.ui.constants.Device;
import com.github.gwtbootstrap.client.ui.constants.NavbarPosition;
import com.google.gwt.aria.client.Roles;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.Composite;

public class MainMenuWidget extends Composite implements LoginEventHandler {

	// i18n
	private final CommonConstants constants = Kuharija.constants;
	private final CommonMessages messages = Kuharija.messages;

	// widgets
	private final Nav navRight = new Nav();
	private final NavLink loginLink = new NavLink(constants.login(), LoginPresenter.getLoginUrl());
	private final NavLink logoutButton = new NavLink(constants.logout(), LoginPresenter.getLoginUrl());
	private final SearchWidget searchWidget = new SearchWidget();

	// handler
	private final LogoutHandler handler;

	public MainMenuWidget(LogoutHandler logoutHandler, EventBus eventBus) {
		this.handler = logoutHandler;
		eventBus.addHandler(LoginEvent.TYPE, this);

		// header
		NavHeader header = new NavHeader(constants.appTitle());
		header.setHideOn(Device.DESKTOP);

		// main navigation
		Nav mainNav = new Nav();
		mainNav.add(new NavLink(constants.home(), "#" + HomePresenter.SCREEN_NAME));
		mainNav.add(new NavLink(constants.recipes(), "#" + RecipeIndexPresenter.SCREEN_NAME));
		mainNav.add(new NavLink(constants.lexicon(), "#" + LexiconPresenter.SCREEN_NAME));

		// right buttons (login)
		navRight.setAlignment(Alignment.RIGHT);
		navRight.add(searchWidget);
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
		navRight.clear();
		navRight.add(searchWidget);
		if (user != null) {
			final NavText userLabel = new NavText(messages.helloUser(user.getName()));
			Roles.getStatusRole().set(userLabel.getElement());
			navRight.add(userLabel);
			navRight.add(logoutButton);
		} else {
			navRight.add(loginLink);
		}
	}
}

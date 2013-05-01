package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.handler.LoginEventHandler;
import si.merljak.magistrska.client.handler.LogoutHandler;
import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.client.i18n.CommonMessages;
import si.merljak.magistrska.client.mvp.login.LoginPresenter;
import si.merljak.magistrska.common.dto.UserDto;

import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.base.InlineLabel;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.google.gwt.aria.client.Roles;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;

/**
 * Widget displaying hello to user and login link or logout button.
 * 
 * @author Jakob Merljak
 * 
 */
public class UserWidget extends Composite implements LoginEventHandler {

	// i18n
	private final CommonConstants constants = Kuharija.constants;
	private final CommonMessages messages = Kuharija.messages;

	// widgets
	private final InlineLabel userLabel = new InlineLabel(messages.hello());
	private final SimplePanel loginLogoutHolder = new SimplePanel();
	private final Anchor loginLink = new Anchor(constants.login(), LoginPresenter.getLoginUrl());
	private final Button logoutButton = new Button(constants.logout());

	// handler
	private LogoutHandler handler;

	public UserWidget(LogoutHandler logoutHandler) {
		this.handler = logoutHandler;

		FlowPanel main = new FlowPanel();
		main.add(userLabel);
		main.add(loginLogoutHolder);
		initWidget(main);

		loginLogoutHolder.setWidget(loginLink);
		logoutButton.setStyleName(Constants.BTN);
		logoutButton.addStyleDependentName("link");
		logoutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (handler != null) {
					handler.logout();
				}
			}
		});

		// ARIA role: status
		Roles.getStatusRole().set(userLabel.getElement());
	}

	/**
	 * Displays hello to user and logout button. If user is not logged in,
	 * generic hello and login link are displayed instead.
	 * 
	 * @param user DTO (nullable)
	 */
	@Override
	public void onUserLogin(UserDto user) {
		if (user != null) {
			userLabel.setText(messages.helloUser(user.getName()));
			loginLogoutHolder.setWidget(logoutButton);
		} else {
			userLabel.setText(messages.hello());
			loginLogoutHolder.setWidget(loginLink);
		}
	}
}

package si.merljak.magistrska.client.mvp.login;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.handler.LoginHandler;
import si.merljak.magistrska.client.handler.LogoutHandler;
import si.merljak.magistrska.client.mvp.AbstractView;
import si.merljak.magistrska.common.enumeration.LoginError;

import com.github.gwtbootstrap.client.ui.Alert;
import com.github.gwtbootstrap.client.ui.Form;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.google.gwt.aria.client.Id;
import com.google.gwt.aria.client.Roles;
import com.google.gwt.aria.client.TextboxRole;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

/**
 * Simple view with login form.
 * 
 * @author Jakob Merljak
 * 
 */
public class LoginView extends AbstractView implements LoginPresenter.LoginView {

	// widgets
	private final Alert alertPlaceholder = new Alert();
	private final TextBox usernameBox = new TextBox();
	private final PasswordTextBox passwordBox = new PasswordTextBox();
	private final Button loginButton = new Button(constants.login());
	private final Button logoutButton = new Button(constants.logout());

	// handlers
	private LoginHandler loginHandler;
	private LogoutHandler logoutHandler;

	public LoginView() {
		// alert placeholder
		alertPlaceholder.setClose(false);
		alertPlaceholder.setVisible(false);

		// labels
		Label usernameLabel = new Label(constants.username());
		Label passwordLabel = new Label(constants.password());
		usernameLabel.getElement().setId("usernameLabel");
		passwordLabel.getElement().setId("passwordLabel");

		// username input box
		usernameBox.setTitle(constants.username());
		usernameBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER && loginHandler != null) {
					loginHandler.login(usernameBox.getText(), passwordBox.getText());
				}
			}
		});

		// password input box
		passwordBox.setTitle(constants.password());
		passwordBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER && loginHandler != null) {
					loginHandler.login(usernameBox.getText(), passwordBox.getText());
				}
			}
		});

		// login button
		loginButton.setStyleName(Constants.BTN);
		loginButton.addStyleDependentName("success");
		loginButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (loginHandler != null) {
					loginHandler.login(usernameBox.getText(), passwordBox.getText());
				}
			}
		});

		// logout button
		logoutButton.setStyleName("btn-link");
		logoutButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (logoutHandler != null) {
					logoutHandler.logout();
				}
			}
		});

		// form
		Form loginForm = new Form();
		loginForm.getElement().setId("loginForm");
		loginForm.add(usernameLabel);
		loginForm.add(usernameBox);
		loginForm.add(passwordLabel);
		loginForm.add(passwordBox);
		loginForm.add(loginButton);

		// layout
		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, constants.login()));
		main.add(alertPlaceholder);
		main.add(loginForm);
		initWidget(main);

		// ARIA roles
		Roles.getAlertRole().set(alertPlaceholder.getElement());
		Roles.getFormRole().set(loginForm.getElement());
		TextboxRole textboxRole = Roles.getTextboxRole();
		textboxRole.setAriaLabelledbyProperty(usernameBox.getElement(), Id.of(usernameLabel.getElement()));
		textboxRole.setAriaLabelledbyProperty(passwordBox.getElement(), Id.of(passwordLabel.getElement()));
	}

	@Override
	public void setLoginHandler(LoginHandler loginHandler) {
		this.loginHandler = loginHandler;
	}

	@Override
	public void setLogoutHandler(LogoutHandler logoutHandler) {
		this.logoutHandler = logoutHandler;
	}

	@Override
	public void showError(LoginError error) {
		passwordBox.setValue("");
		alertPlaceholder.remove(logoutButton);
		alertPlaceholder.setType(AlertType.ERROR);
		alertPlaceholder.setText(constants.loginErrorMap().get(error.name()));
		alertPlaceholder.setVisible(true);
		usernameBox.setEnabled(true);
		passwordBox.setEnabled(true);
		loginButton.setEnabled(true);
	}

	@Override
	public void showLoginSuccess(boolean withTimer) {
		usernameBox.setValue("");
		passwordBox.setValue("");
		alertPlaceholder.add(logoutButton);
		alertPlaceholder.setType(AlertType.SUCCESS);
		alertPlaceholder.setText(withTimer ? constants.loginSuccessAndRedirect() : constants.loginSuccess());
		alertPlaceholder.setVisible(true);
		usernameBox.setEnabled(false);
		passwordBox.setEnabled(false);
		loginButton.setEnabled(false);
	}

	@Override
	public void showLogoutSuccess() {
		usernameBox.setValue("");
		passwordBox.setValue("");
		alertPlaceholder.remove(logoutButton);
		alertPlaceholder.setType(AlertType.INFO);
		alertPlaceholder.setText(constants.logoutSuccess());
		alertPlaceholder.setVisible(true);
		usernameBox.setEnabled(true);
		passwordBox.setEnabled(true);
		loginButton.setEnabled(true);
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(constants.login());
		return super.asWidget();
	}
}

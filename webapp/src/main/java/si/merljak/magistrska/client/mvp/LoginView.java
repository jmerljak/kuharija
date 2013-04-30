package si.merljak.magistrska.client.mvp;

import si.merljak.magistrska.client.handler.LoginHandler;
import si.merljak.magistrska.common.enumeration.LoginError;

import com.github.gwtbootstrap.client.ui.Alert;
import com.github.gwtbootstrap.client.ui.Form;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.constants.AlertType;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.google.gwt.aria.client.Roles;
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

	private LoginHandler handler;

	public LoginView() {
		// alert placeholder with ARIA alert role
		alertPlaceholder.setClose(false);
		alertPlaceholder.setVisible(false);
		Roles.getAlertRole().set(alertPlaceholder.getElement());

		// username input box
		usernameBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER && handler != null) {
					handler.login(usernameBox.getText(), passwordBox.getText());
				}
			}
		});

		// password input box
		passwordBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER && handler != null) {
					handler.login(usernameBox.getText(), passwordBox.getText());
				}
			}
		});

		// login button
		loginButton.setStyleName(Constants.BTN);
		loginButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (handler != null) {
					handler.login(usernameBox.getText(), passwordBox.getText());
				}
			}
		});

		// form
		Form loginForm = new Form();
		loginForm.getElement().setId("loginForm");
		loginForm.add(new Label(constants.username()));
		loginForm.add(usernameBox);
		loginForm.add(new Label(constants.password()));
		loginForm.add(passwordBox);
		loginForm.add(loginButton);

		// layout
		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, constants.login()));
		main.add(alertPlaceholder);
		main.add(loginForm);
		initWidget(main);
	}

	@Override
	public void setLoginHandler(LoginHandler loginHandler) {
		this.handler = loginHandler;
	}

	@Override
	public void clear() {
		usernameBox.setValue("");
		passwordBox.setValue("");
		alertPlaceholder.setVisible(false);
	}

	@Override
	public void showError(LoginError error) {
		passwordBox.setValue("");
		alertPlaceholder.setType(AlertType.ERROR);
		alertPlaceholder.setText(constants.loginErrorMap().get(error.name()));
		alertPlaceholder.setVisible(true);
	}

	@Override
	public void showLoginSuccess() {
		usernameBox.setValue("");
		passwordBox.setValue("");
		alertPlaceholder.setType(AlertType.SUCCESS);
		alertPlaceholder.setText(constants.loginSuccess());
		alertPlaceholder.setVisible(true);
	}

	@Override
	public void showLogoutSuccess() {
		usernameBox.setValue("");
		passwordBox.setValue("");
		alertPlaceholder.setType(AlertType.INFO);
		alertPlaceholder.setText(constants.logoutSuccess());
		alertPlaceholder.setVisible(true);

	}
}

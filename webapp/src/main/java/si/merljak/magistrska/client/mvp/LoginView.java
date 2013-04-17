package si.merljak.magistrska.client.mvp;

import si.merljak.magistrska.common.enumeration.LoginError;

import com.github.gwtbootstrap.client.ui.Form;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Paragraph;
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
public class LoginView extends AbstractView implements LoginPresenter.View {

	// widgets
	private final Paragraph alertPlaceholder = new Paragraph();
	private final TextBox usernameBox = new TextBox();
	private final PasswordTextBox passwordBox = new PasswordTextBox();
	private final Button loginButton = new Button(constants.login());

	private LoginPresenter presenter;

	public LoginView() {
		alertPlaceholder.setStyleName("alertPlaceholder");
		alertPlaceholder.setVisible(false);

		// username input box
		usernameBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER && presenter != null) {
					presenter.login(usernameBox.getText(), passwordBox.getText());
				}
			}
		});

		// password input box
		passwordBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER && presenter != null) {
					presenter.login(usernameBox.getText(), passwordBox.getText());
				}
			}
		});

		// login button
		loginButton.setStyleName("btn");
		loginButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.login(usernameBox.getText(), passwordBox.getText());
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

		// main layout
		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, constants.login()));
		main.add(alertPlaceholder);
		main.add(loginForm);
		initWidget(main);

		// ARIA role
		Roles.getAlertRole().set(alertPlaceholder.getElement());
	}

	@Override
	public void displayLoginForm(LoginError error) {
		usernameBox.setValue(""); // leave entered username?
		passwordBox.setValue("");

		if (error != null) {
			alertPlaceholder.setText(constants.loginErrorMap().get(error.name()));
			alertPlaceholder.setVisible(true);
		} else {
			alertPlaceholder.setVisible(false);
		}
	}

	@Override
	public void setPresenter(LoginPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void displayRegisterForm(LoginError error) {
		// TODO register not yet implemented
	}
}

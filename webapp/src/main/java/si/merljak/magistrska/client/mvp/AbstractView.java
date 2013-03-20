package si.merljak.magistrska.client.mvp;

import com.google.gwt.user.client.ui.Composite;

public abstract class AbstractView extends Composite {
	public void hide() {
		setVisible(false);
	}
}

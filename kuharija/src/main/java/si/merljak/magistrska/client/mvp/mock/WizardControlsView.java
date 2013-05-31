package si.merljak.magistrska.client.mvp.mock;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.mvp.AbstractView;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.client.ui.resources.ButtonSize;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Wizard controls view.
 * 
 * @author Jakob Merljak
 * 
 */
public class WizardControlsView extends AbstractView {

	// presenter
	private WizardControlsPresenter presenter;

	public WizardControlsView() {
		Button buttonPrevious = new Button("", IconType.STEP_BACKWARD);
		buttonPrevious.setSize(ButtonSize.LARGE);
		buttonPrevious.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.setAction("back");
			}
		});

		Button buttonPause = new Button("", IconType.PAUSE);
		buttonPause.setType(ButtonType.WARNING);
		buttonPause.setSize(ButtonSize.LARGE);
		buttonPause.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.setAction("pause");
			}
		});

		Button buttonPlay = new Button("", IconType.PLAY);
		buttonPlay.setType(ButtonType.SUCCESS);
		buttonPlay.setSize(ButtonSize.LARGE);
		buttonPlay.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.setAction("play");
			}
		});

		Button buttonNext = new Button("", IconType.STEP_FORWARD);
		buttonNext.setSize(ButtonSize.LARGE);
		buttonNext.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				presenter.setAction("forward");
			}
		});


		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, "WOz"));
		main.add(buttonPrevious);
		main.add(new InlineLabel(" "));
		main.add(buttonPause);
		main.add(new InlineLabel(" "));
		main.add(buttonPlay);
		main.add(new InlineLabel(" "));
		main.add(buttonNext);
		initWidget(main);
	}

	void setPresenter(WizardControlsPresenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle("WOz");
		return super.asWidget();
	}
}

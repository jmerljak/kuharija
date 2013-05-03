package si.merljak.magistrska.client.mvp.lexicon;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.mvp.AbstractView;
import si.merljak.magistrska.client.mvp.ingredient.IngredientIndexPresenter;
import si.merljak.magistrska.client.mvp.utensil.UtensilIndexPresenter;

import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Lexicon index view.
 * 
 * @author Jakob Merljak
 * 
 */
public class LexiconView extends AbstractView {

	public LexiconView() {
		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, constants.lexicon()));

		Anchor ingredientsLink = new Anchor(constants.ingredients(), "#" + IngredientIndexPresenter.SCREEN_NAME);
		ingredientsLink.setStyleName(Constants.BTN);
		main.add(ingredientsLink);

		Anchor utensilsLink = new Anchor(constants.utensils(), "#" + UtensilIndexPresenter.SCREEN_NAME);
		utensilsLink.setStyleName(Constants.BTN);
		main.add(utensilsLink);

		initWidget(main);
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(constants.lexicon());
		return super.asWidget();
	}
}

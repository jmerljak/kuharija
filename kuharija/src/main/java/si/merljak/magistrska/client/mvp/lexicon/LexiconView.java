package si.merljak.magistrska.client.mvp.lexicon;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.mvp.AbstractView;
import si.merljak.magistrska.client.mvp.ingredient.IngredientIndexPresenter;
import si.merljak.magistrska.client.mvp.utensil.UtensilIndexPresenter;

import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.github.gwtbootstrap.client.ui.base.UnorderedList;
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
		Anchor ingredientsLink = new Anchor(constants.ingredients(), "#" + IngredientIndexPresenter.SCREEN_NAME);
		Anchor utensilsLink = new Anchor(constants.utensils(), "#" + UtensilIndexPresenter.SCREEN_NAME);

		UnorderedList categories = new UnorderedList();
		categories.add(new ListItem(ingredientsLink));
		categories.add(new ListItem(utensilsLink));

		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, constants.lexicon()));
		main.add(categories);
		initWidget(main);
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(constants.lexicon());
		return super.asWidget();
	}
}

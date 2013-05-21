package si.merljak.magistrska.client.mvp.lexicon;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.mvp.AbstractView;
import si.merljak.magistrska.client.mvp.ingredient.IngredientIndexPresenter;
import si.merljak.magistrska.client.mvp.utensil.UtensilIndexPresenter;

import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.SimplePanel;
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
		SimplePanel ingredientsLinkHolder = new SimplePanel();
		ingredientsLinkHolder.setWidget(ingredientsLink);
		ingredientsLinkHolder.setStylePrimaryName("imageLink");
		ingredientsLinkHolder.addStyleDependentName("ingredients");
		ingredientsLinkHolder.addStyleName("img-rounded");

		Anchor utensilsLink = new Anchor(constants.utensils(), "#" + UtensilIndexPresenter.SCREEN_NAME);
		SimplePanel utensilsLinkHolder = new SimplePanel();
		utensilsLinkHolder.setWidget(utensilsLink);
		utensilsLinkHolder.setStylePrimaryName("imageLink");
		utensilsLinkHolder.addStyleDependentName("utensils");
		utensilsLinkHolder.addStyleName("img-rounded");

		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, constants.lexicon()));
		main.add(new Paragraph(messages.lexiconIntro()));
		main.add(ingredientsLinkHolder);
		main.add(utensilsLinkHolder);
		initWidget(main);
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(constants.lexicon());
		return super.asWidget();
	}
}

package si.merljak.magistrska.client.mvp.lexicon;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.mvp.AbstractView;
import si.merljak.magistrska.client.mvp.ingredient.IngredientIndexPresenter;
import si.merljak.magistrska.client.mvp.utensil.UtensilIndexPresenter;

import com.github.gwtbootstrap.client.ui.Column;
import com.github.gwtbootstrap.client.ui.FluidRow;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.constants.ImageType;
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
		Column ingredientsLinkHolder = new Column(6);
		ingredientsLinkHolder.add(ingredientsLink);
		ingredientsLinkHolder.addStyleName("imageLink");
		ingredientsLinkHolder.addStyleName("imageLink-ingredients");
		ingredientsLinkHolder.addStyleName(ImageType.ROUNDED.get());

		Anchor utensilsLink = new Anchor(constants.utensils(), "#" + UtensilIndexPresenter.SCREEN_NAME);
		Column utensilsLinkHolder = new Column(6);
		utensilsLinkHolder.add(utensilsLink);
		utensilsLinkHolder.addStyleName("imageLink");
		utensilsLinkHolder.addStyleName("imageLink-utensils");
		utensilsLinkHolder.addStyleName(ImageType.ROUNDED.get());
		
		FluidRow container = new FluidRow();
		container.add(ingredientsLinkHolder);
		container.add(utensilsLinkHolder);

		FlowPanel main = new FlowPanel();
		main.add(new Heading(HEADING_SIZE, constants.lexicon()));
		main.add(new Paragraph(messages.lexiconIntro()));
		main.add(container);
		initWidget(main);
	}

	@Override
	public Widget asWidget() {
		Kuharija.setWindowTitle(constants.lexicon());
		return super.asWidget();
	}
}

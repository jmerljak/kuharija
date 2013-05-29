package si.merljak.magistrska.client.mvp.utensil;

import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.UtensilsConstants;
import si.merljak.magistrska.client.mvp.AbstractView;
import si.merljak.magistrska.client.mvp.search.SearchPresenter;
import si.merljak.magistrska.common.dto.UtensilDto;

import com.github.gwtbootstrap.client.ui.Button;
import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.constants.ButtonType;
import com.github.gwtbootstrap.client.ui.constants.ImageType;
import com.google.gwt.aria.client.Roles;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Simple view that displays single utensil details.
 * 
 * @author Jakob Merljak
 * 
 */
public class UtensilView extends AbstractView {

	// i18n
	private final UtensilsConstants utensilsConstants = Kuharija.utensilsConstants;
	private final Map<String, String> utensilMap = utensilsConstants.utensilsMap();
	private final Map<String, String> utensilsDescriptionMap = utensilsConstants.utensilsDescriptionMap();

	// widgets
	private final Heading heading = new Heading(HEADING_SIZE);
	private final FlowPanel content = new FlowPanel();
	private final Paragraph message404 = new Paragraph(messages.utensilNotFoundTry());

	public UtensilView() {
		FlowPanel main = new FlowPanel();
		main.add(heading);
		main.add(content);
		initWidget(main);

		Roles.getArticleRole().set(main.getElement());
	}

	/**
	 * Displays utensil details or shows appropriate message if utensil not found.
	 * 
	 * @param utensil DTO or {@code null} if utensil not found
	 */
	public void displayUtensil(UtensilDto utensil) {
		content.clear();
		if (utensil == null) {
			// no utensil found
			heading.setText(messages.oops());
			content.add(message404);
			Kuharija.setWindowTitle(null);
		} else {
			String utensilName = utensil.getName();
			String localizedName = utensilMap.get(utensilName);
			String localizedDescription = utensilsDescriptionMap.get(utensilName + "_DESC");

			// heading and title
			heading.setText(localizedName);
			Kuharija.setWindowTitle(localizedName);

			// image
			String imageUrl = utensil.getImageUrl();
			if (imageUrl != null) {
				Image img = new Image(UTENSIL_IMG_FOLDER + imageUrl);
				img.setAltText(localizedName);
				img.setType(ImageType.ROUNDED);
				content.add(img);
			}

			// links
			Button linkRecipesByUtensil = new Button();
			linkRecipesByUtensil.setText(messages.searchByUtensil(localizedName.toLowerCase()));
			linkRecipesByUtensil.setHref(SearchPresenter.buildSearchByUtensilUrl(utensilName));
			linkRecipesByUtensil.setType(ButtonType.INFO);

			Button linkWikipedia = new Button();
			linkWikipedia.setText(messages.utensilReadMoreOnWikipedia());
			linkWikipedia.setHref(messages.wikipediaSearchUrl(URL.encodeQueryString(localizedName)));
			linkWikipedia.setTarget("_blank");

			content.add(new Paragraph(localizedDescription));
			content.add(linkRecipesByUtensil);
			content.add(linkWikipedia);
		}
	}
}

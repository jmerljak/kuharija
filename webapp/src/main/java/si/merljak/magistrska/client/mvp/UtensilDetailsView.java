package si.merljak.magistrska.client.mvp;

import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.UrlConstants;
import si.merljak.magistrska.client.i18n.UtensilsConstants;
import si.merljak.magistrska.common.dto.UtensilDto;

import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.FlowPanel;

/** 
 * Simple view that displays single utensil details.
 * 
 * @author Jakob Merljak
 *
 */
public class UtensilDetailsView extends AbstractView {

	// i18n
	private final UtensilsConstants utensilsConstants = Kuharija.utensilsConstants;
	private final UrlConstants urlConstants = Kuharija.urlConstants;
	private final Map<String, String> utensilMap = utensilsConstants.utensilsMap();
	private final Map<String, String> utensilsDescriptionMap = utensilsConstants.utensilsDescriptionMap();

	// widgets
	private final Heading heading = new Heading(HEADING_SIZE);
	private final FlowPanel content = new FlowPanel();

	public UtensilDetailsView () {
		FlowPanel main = new FlowPanel();
		main.add(heading);
		main.add(content);
		initWidget(main);
	}

	/** 
	 * Displays utensil details or shows appropriate message if utensil not found.
	 * @param utensil DTO or {@code null} if utensil not found
	 */
	public void displayUtensil(UtensilDto utensil) {
		content.clear();
		if (utensil == null) {
			// no utensil found
			heading.setText(messages.oops());
			content.add(new Paragraph(messages.utensilNotFoundTry()));
			Kuharija.setWindowTitle(null);
		} else {
			String utensilName = utensil.getName();
			String localizedName = utensilMap.get(utensilName);
			String localizedDescription = utensilsDescriptionMap.get(utensilName + "_DESC");

			// heading and title
			heading.setText(localizedName);
			Kuharija.setWindowTitle(localizedName);

			// TODO check for null URL
			Image img = new Image(UTENSIL_IMG_FOLDER + utensil.getImageUrl());
			img.setAltText(localizedName);

			content.add(img);
			content.add(new Paragraph(localizedDescription));
			content.add(new Anchor(messages.searchByUtensil(localizedName.toLowerCase()), SearchPresenter.buildSearchByUtensilUrl(utensilName)));
			content.add(new Anchor(messages.utensilReadMoreOnWikipedia(localizedName), urlConstants.localWikipediaSearchUrl() + localizedName, "_blank"));
		}
	}
}

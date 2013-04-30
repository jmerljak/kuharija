package si.merljak.magistrska.client.widgets;

import java.util.List;
import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.client.i18n.UtensilsConstants;
import si.merljak.magistrska.client.mvp.utensil.UtensilPresenter;
import si.merljak.magistrska.common.dto.UtensilDto;

import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.github.gwtbootstrap.client.ui.base.UnorderedList;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Widget displaying utensils for recipe or technique.
 * 
 * @author Jakob Merljak
 * 
 */
public class UtensilsWidget extends Composite {

	// i18n
	private final CommonConstants constants = Kuharija.constants;
	private final UtensilsConstants utensilsConstants = Kuharija.utensilsConstants;
	private final Map<String, String> utensilsMap = utensilsConstants.utensilsMap();

	// widgets
	private final Heading heading = new Heading(2, constants.utensils());
	private final UnorderedList utensilsList = new UnorderedList();

	public UtensilsWidget() {
		FlowPanel panel = new FlowPanel();
		panel.add(heading);
		panel.add(utensilsList);
		initWidget(panel);
	}

	/**
	 * Clears old data and displays utensil list.
	 * 
	 * @param utensils list of utensils
	 */
	public void update(List<UtensilDto> utensils) {
		utensilsList.clear();
		for (UtensilDto utensil : utensils) {
			ListItem listItem = new ListItem();
			utensilsList.add(listItem);

			// quantity
			Integer quantity = utensil.getQuantity();
			if (quantity != null) {
				listItem.getElement().setInnerText(quantity.toString() + " x ");
			}

			// name
			String name = utensil.getName();
			String localizedName = utensilsMap.get(name).toLowerCase();
			Anchor utensilLink = new Anchor(localizedName, UtensilPresenter.buildUtensilUrl(name));
			listItem.add(utensilLink);
		}

		// hide if empty
		setVisible(!utensils.isEmpty());
	}
}

package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.common.dto.AppendixDto;

import com.github.gwtbootstrap.client.ui.Heading;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Simple widget for displaying tips, health info, important notes etc.
 * 
 * @author Jakob Merljak
 * 
 */
public class AppendixWidget extends FlowPanel {

	// i18n
	private final CommonConstants constants = Kuharija.constants;

	/**
	 * Simple widget for displaying tips, health info, important notes etc.
	 * 
	 * @param appendix DTO
	 */
	public AppendixWidget(AppendixDto appendix) {
		String type = appendix.getType().name();

		setStyleName("appendix");
		addStyleDependentName(type.toLowerCase());
		add(new Heading(3, constants.appendixMap().get(type)));
		add(new Paragraph(appendix.getContent()));
	}
}

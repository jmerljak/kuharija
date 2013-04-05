package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.common.dto.AppendixDto;

import com.google.gwt.user.client.ui.Label;

/**
 * Simple widget for displaying tips, health info, important notes etc.
 * 
 * @author Jakob Merljak
 *
 */
public class AppendixWidget extends Label {

	public AppendixWidget(AppendixDto appendix) {
		setStyleName("appendix");
		addStyleDependentName(appendix.getType().name().toLowerCase());
		setText(appendix.getContent());
	}
}

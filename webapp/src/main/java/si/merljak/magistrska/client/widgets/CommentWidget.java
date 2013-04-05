package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.common.dto.CommentDto;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

/**
 * Simple widget for displaying tips, health info, important notes etc.
 * 
 * @author Jakob Merljak
 *
 */
public class CommentWidget extends FlowPanel {

	private static final DateTimeFormat timestampFormat = Kuharija.timestampFormat;

	public CommentWidget(CommentDto comment) {
		setStyleName("comment");
		add(new Label(comment.getUser()));
		add(new Label(timestampFormat.format(comment.getDate())));
		add(new Label(comment.getContent()));
	}
}

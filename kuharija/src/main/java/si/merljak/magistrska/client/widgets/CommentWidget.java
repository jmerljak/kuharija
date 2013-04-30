package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.CommonMessages;
import si.merljak.magistrska.common.dto.CommentDto;

import com.github.gwtbootstrap.client.ui.Blockquote;
import com.google.gwt.i18n.client.DateTimeFormat;

/**
 * Simple widget for displaying user comments.
 * 
 * @author Jakob Merljak
 * 
 */
public class CommentWidget extends Blockquote {

	// i18n
	private final CommonMessages messages = Kuharija.messages;
	private final DateTimeFormat dateFormat = Kuharija.dateFormat;

	/**
	 * Simple widget for displaying user comments.
	 * 
	 * @param comment DTO
	 */
	public CommentWidget(CommentDto comment) {
		String date = dateFormat.format(comment.getDate());

		setStyleName("comment");
		setText(comment.getContent());
		setSource(messages.commentBy(comment.getUser(), date));
	}
}

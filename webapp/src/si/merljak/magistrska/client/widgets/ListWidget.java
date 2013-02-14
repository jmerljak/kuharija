package si.merljak.magistrska.client.widgets;

import java.util.List;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

public class ListWidget extends Composite {

	public ListWidget(List<Widget> listItems) {
		String html = new String("<ul>");
		for (Widget listItem : listItems) {
			html += "<li>" + listItem + "</li>";
		}
		html += "</ul>";

		initWidget(new HTML(html));
	}

}

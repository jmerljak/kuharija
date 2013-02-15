package si.merljak.magistrska.client.widgets;

import com.google.gwt.user.client.ui.HTML;

public class GlyphIconWidget extends HTML {

	public GlyphIconWidget(String cssClass) {
		getElement().setInnerHTML("<i class=\"" + cssClass + "\"></i>");
	}

}

package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.RecipeEntry;
import si.merljak.magistrska.client.i18n.GlobalConstants;

import com.github.gwtbootstrap.client.ui.NavTabs;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;

public class TabsWidget extends Composite {
	private static final GlobalConstants constants = RecipeEntry.getConstants();

	private Anchor tabBasic = new Anchor();
	private Anchor tabDetails = new Anchor();
	private Anchor tabVideo = new Anchor();
	private Anchor tabAudio = new Anchor();

	public TabsWidget() {

		tabBasic.setHref("#basic");
		tabBasic.setHTML(new GlyphIconWidget("icon-list").getHTML() + " " + constants.tabBasic());
		tabBasic.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				tabBasic.getParent().addStyleName("active");
				tabDetails.getParent().removeStyleName("active");
				tabVideo.getParent().removeStyleName("active");
				tabAudio.getParent().removeStyleName("active");
				RootPanel.get("textBasic").setVisible(true);
			}
		});

		tabDetails.setHref("#details");
		tabDetails.setHTML(new GlyphIconWidget("icon-list-alt").getHTML() + " " + constants.tabDetails());
		tabDetails.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				tabBasic.getParent().removeStyleName("active");
				tabDetails.getParent().setStyleName("active");
				tabVideo.getParent().removeStyleName("active");
				tabAudio.getParent().removeStyleName("active");
				RootPanel.get("textBasic").setVisible(false);
			}
		});

		tabVideo.setHref("#video");
		tabVideo.setHTML(new GlyphIconWidget("icon-film").getHTML() + " " + constants.tabVideo());
		tabVideo.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				tabBasic.getParent().removeStyleName("active");
				tabDetails.getParent().removeStyleName("active");
				tabVideo.getParent().setStyleName("active");
				tabAudio.getParent().removeStyleName("active");
			}
		});

		tabAudio.setHref("#audio");
		tabAudio.setHTML(new GlyphIconWidget("icon-music").getHTML() + " " + constants.tabAudio());
		tabAudio.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				tabBasic.getParent().removeStyleName("active");
				tabDetails.getParent().removeStyleName("active");
				tabVideo.getParent().removeStyleName("active");
				tabAudio.getParent().setStyleName("active");
			}
		});
		
		NavTabs navTabs = new NavTabs();
		navTabs.add(new ListItem(tabBasic));
		navTabs.add(new ListItem(tabDetails));
		navTabs.add(new ListItem(tabVideo));
		navTabs.add(new ListItem(tabAudio));

		initWidget(navTabs);
		
		// set active widget
		tabBasic.getParent().addStyleName("active");
	}

}

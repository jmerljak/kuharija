package si.merljak.magistrska.client.widgets;

import java.util.HashMap;
import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.CommonConstants;

import com.github.gwtbootstrap.client.ui.NavTabs;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.google.gwt.aria.client.Roles;
import com.google.gwt.aria.client.SelectedValue;
import com.google.gwt.aria.client.TabRole;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;

public class TabsWidget extends Composite {

	// constants & roles
	private static final CommonConstants constants = Kuharija.constants;
	private static final TabRole TAB_ROLE = Roles.getTabRole();

	// panels
	private static final RootPanel PANEL_BASIC = RootPanel.get("basic");
	private static final RootPanel PANEL_STEPS = RootPanel.get("steps");
	private static final RootPanel PANEL_VIDEO = RootPanel.get("video");
	private static final RootPanel PANEL_AUDIO = RootPanel.get("audio");

	private Map <String, ListItem> tabMap = new HashMap<String, ListItem>();
	private static Map <String, RootPanel> panelMap = new HashMap<String, RootPanel>();

	public TabsWidget() {
		// anchors
		Anchor anchorBasic = new Anchor(" " + constants.tabBasic(), "#basic");
		anchorBasic.getElement().insertFirst(getGlyphIcon("icon-list"));

		Anchor anchorSteps = new Anchor(" " + constants.tabDetails(), "#steps");
		anchorSteps.getElement().insertFirst(getGlyphIcon("icon-list-alt"));

		Anchor anchorVideo = new Anchor(" " + constants.tabVideo(), "#video");
		anchorVideo.getElement().insertFirst(getGlyphIcon("icon-film"));

		Anchor anchorAudio = new Anchor(" " + constants.tabAudio(), "#audio");
		anchorAudio.getElement().insertFirst(getGlyphIcon("icon-music"));

		// tabs
		ListItem tabBasic = new ListItem(anchorBasic);
		ListItem tabSteps = new ListItem(anchorSteps);
		ListItem tabVideo = new ListItem(anchorVideo);
		ListItem tabAudio = new ListItem(anchorAudio);

		// ARIA roles
		TAB_ROLE.set(tabBasic.getElement());
		TAB_ROLE.set(tabSteps.getElement());
		TAB_ROLE.set(tabVideo.getElement());
		TAB_ROLE.set(tabAudio.getElement());

		// tab map
		tabMap.put("basic", tabBasic);
		tabMap.put("steps", tabSteps);
		tabMap.put("video", tabVideo);
		tabMap.put("audio", tabAudio);

		// tabs panel
		NavTabs navTabs = new NavTabs();
		navTabs.add(tabBasic);
		navTabs.add(tabSteps);
		navTabs.add(tabVideo);
		navTabs.add(tabAudio);
		initWidget(navTabs);
	}

	/** 
	 * Creates bootstrap glyph icon element.
	 * @param cssClass glyph's class name
	 * @return the created element
	 */
	private Element getGlyphIcon(String cssClass) {
		Element glyphIcon = DOM.createElement("i");
	    glyphIcon.setAttribute("class", cssClass);
		return glyphIcon;
	}

	/** 
	 * Sets tab as selected/active and displays associated panel. It also sets appropriate ARIA states.
	 * @param tabName tab name
	 */
	public void setActiveTab(String tabName) {
		for (String key : tabMap.keySet()) {
			ListItem tab = tabMap.get(key);
			boolean isActive = tabName != null && tabName.equalsIgnoreCase(key);
			tab.setStyleName("active", isActive);
			TAB_ROLE.setAriaSelectedState(tab.getElement(), SelectedValue.of(isActive));
		}

		for (String key : panelMap.keySet()) {
			boolean isActive = tabName != null && tabName.equalsIgnoreCase(key);
			panelMap.get(key).setVisible(isActive);
		}
	}

	static {
		// panel map
		panelMap.put("basic", PANEL_BASIC);
		panelMap.put("steps", PANEL_STEPS);
		panelMap.put("video", PANEL_VIDEO);
		panelMap.put("audio", PANEL_AUDIO);
	}
}

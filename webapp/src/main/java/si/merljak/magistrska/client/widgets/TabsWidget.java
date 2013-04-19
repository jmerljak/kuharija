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
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;

public class TabsWidget extends Composite {

	public interface TabChangeHandler {
		void onTabChange(int tab);
	}

	// constants & roles
	public static final int TAB_BASIC = 1;
	public static final int TAB_STEPS = 2;
	public static final int TAB_VIDEO = 3;
	public static final int TAB_AUDIO = 4;

	private static final CommonConstants constants = Kuharija.constants;
	private static final TabRole TAB_ROLE = Roles.getTabRole();

	private Map <Integer, ListItem> tabMap = new HashMap<Integer, ListItem>();
	private TabChangeHandler handler;

	public TabsWidget(TabChangeHandler tabChangeHandler) {
		this.handler = tabChangeHandler;
		
		// anchors
		Anchor anchorBasic = new Anchor(" " + constants.tabBasic(), "#basic");
		anchorBasic.getElement().insertFirst(getGlyphIcon("icon-list"));
		anchorBasic.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setActiveTab(TAB_BASIC);
				if (handler != null) {
					handler.onTabChange(TAB_BASIC);
				}
			}
		});

		Anchor anchorSteps = new Anchor(" " + constants.tabDetails(), "#steps");
		anchorSteps.getElement().insertFirst(getGlyphIcon("icon-list-alt"));
		anchorSteps.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setActiveTab(TAB_STEPS);
				if (handler != null) {
					handler.onTabChange(TAB_STEPS);
				}
			}
		});

		Anchor anchorVideo = new Anchor(" " + constants.tabVideo(), "#video");
		anchorVideo.getElement().insertFirst(getGlyphIcon("icon-film"));
		anchorVideo.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setActiveTab(TAB_VIDEO);
				if (handler != null) {
					handler.onTabChange(TAB_VIDEO);
				}
			}
		});

		Anchor anchorAudio = new Anchor(" " + constants.tabAudio(), "#audio");
		anchorAudio.getElement().insertFirst(getGlyphIcon("icon-music"));
		anchorAudio.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setActiveTab(TAB_AUDIO);
				if (handler != null) {
					handler.onTabChange(TAB_AUDIO);
				}
			}
		});

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
		tabMap.put(TAB_BASIC, tabBasic);
		tabMap.put(TAB_STEPS, tabSteps);
		tabMap.put(TAB_VIDEO, tabVideo);
		tabMap.put(TAB_AUDIO, tabAudio);

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
	 * Sets tab as selected/active. It also sets appropriate ARIA states.
	 * 
	 * @param tabName tab name
	 */
	public void setActiveTab(int selectedTab) {
		for (Integer tabId : tabMap.keySet()) {
			boolean isActive = tabId.intValue() == selectedTab;
			ListItem tab = tabMap.get(tabId);
			tab.setStyleName("active", isActive);
			TAB_ROLE.setAriaSelectedState(tab.getElement(), SelectedValue.of(isActive));
		}
	}
}

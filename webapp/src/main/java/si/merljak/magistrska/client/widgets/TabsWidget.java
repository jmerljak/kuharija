package si.merljak.magistrska.client.widgets;

import java.util.HashMap;
import java.util.Map;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.CommonConstants;

import com.github.gwtbootstrap.client.ui.NavTabs;
import com.github.gwtbootstrap.client.ui.base.IconAnchor;
import com.github.gwtbootstrap.client.ui.base.ListItem;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.google.gwt.aria.client.Roles;
import com.google.gwt.aria.client.SelectedValue;
import com.google.gwt.aria.client.TabRole;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
		IconAnchor anchorBasic = new IconAnchor();
		anchorBasic.setHref("#basic");
		anchorBasic.setIcon(IconType.LIST);
		anchorBasic.setText(" " + constants.tabBasic());
		anchorBasic.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setActiveTab(TAB_BASIC);
				if (handler != null) {
					handler.onTabChange(TAB_BASIC);
				}
			}
		});

		IconAnchor anchorSteps = new IconAnchor();
		anchorSteps.setHref("#steps");
		anchorSteps.setIcon(IconType.LIST_ALT);
		anchorSteps.setText(" " + constants.tabDetails());
		anchorSteps.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setActiveTab(TAB_STEPS);
				if (handler != null) {
					handler.onTabChange(TAB_STEPS);
				}
			}
		});

		IconAnchor anchorVideo = new IconAnchor();
		anchorVideo.setHref("#video");
		anchorVideo.setIcon(IconType.FILM);
		anchorVideo.setText(" " + constants.tabVideo());
		anchorVideo.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setActiveTab(TAB_VIDEO);
				if (handler != null) {
					handler.onTabChange(TAB_VIDEO);
				}
			}
		});

		IconAnchor anchorAudio = new IconAnchor();
		anchorAudio.setHref("#audio");
		anchorAudio.setIcon(IconType.MUSIC);
		anchorAudio.setText(" " + constants.tabAudio());
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

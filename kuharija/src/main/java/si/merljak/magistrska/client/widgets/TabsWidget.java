package si.merljak.magistrska.client.widgets;

import java.util.List;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.handler.PagingHandler;
import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.client.i18n.CommonMessages;
import si.merljak.magistrska.client.mvp.AbstractView;
import si.merljak.magistrska.common.dto.AudioDto;
import si.merljak.magistrska.common.dto.RecipeDetailsDto;
import si.merljak.magistrska.common.dto.StepDto;
import si.merljak.magistrska.common.dto.TextDto;
import si.merljak.magistrska.common.dto.VideoDto;

import com.github.gwtbootstrap.client.ui.Image;
import com.github.gwtbootstrap.client.ui.Paragraph;
import com.github.gwtbootstrap.client.ui.Tab;
import com.github.gwtbootstrap.client.ui.TabPanel;
import com.github.gwtbootstrap.client.ui.constants.Constants;
import com.github.gwtbootstrap.client.ui.constants.IconType;
import com.github.gwtbootstrap.client.ui.constants.ImageType;
import com.github.gwtbootstrap.client.ui.resources.Bootstrap.Tabs;
import com.google.gwt.aria.client.Id;
import com.google.gwt.aria.client.PresentationRole;
import com.google.gwt.aria.client.Roles;
import com.google.gwt.aria.client.SelectedValue;
import com.google.gwt.aria.client.TabRole;
import com.google.gwt.aria.client.TablistRole;
import com.google.gwt.aria.client.TabpanelRole;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.media.client.Audio;
import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;

/**
 * Widget with tabbed panels.
 * 
 * @author Jakob Merljak
 * 
 */
public class TabsWidget extends Composite {

	// constants
	public static final int TAB_BASIC = 0;
	public static final int TAB_STEPS = 1;
	public static final int TAB_VIDEO = 2;
	public static final int TAB_AUDIO = 3;

	// i18n
	private final CommonConstants constants = Kuharija.constants;
	private final CommonMessages messages = Kuharija.messages;

	// ARIA roles
	private final TabRole roleTab = Roles.getTabRole();
	private final TablistRole roleTablist = Roles.getTablistRole();
	private final TabpanelRole roleTabpanel = Roles.getTabpanelRole();
	private final PresentationRole rolePresentation = Roles.getPresentationRole();

	// widgets
	private final TabPanel tabsWidget = new TabPanel(Tabs.ABOVE);
	private final FlowPanel panelBasic = new FlowPanel();
	private final FlowPanel panelSteps = new FlowPanel();
	private final FlowPanel stepPanel = new FlowPanel();
	private final FlowPanel panelAudio = new FlowPanel();
	private final FlowPanel panelVideo = new FlowPanel();

	private final Element tabBasicElement;
	private final Element tabStepsElement;
	private final Element tabVideoElement;
	private final Element tabAudioElement;

	// paging handler
	private SimplePagingWidget simplePagingWidget;
	private PagingHandler pagingHandler;

	public TabsWidget() {
		// tabs
		Tab tabBasic = new Tab();
		tabBasic.setIcon(IconType.ALIGN_LEFT);
		tabBasic.setHeading(constants.tabBasic());
		tabBasic.add(panelBasic);
		tabBasic.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setAriaSelected(TAB_BASIC);
			}
		});

		Tab tabSteps = new Tab();
		tabSteps.setIcon(IconType.HAND_RIGHT);
		tabSteps.setHeading(constants.tabSteps());
		tabSteps.add(panelSteps);
		tabSteps.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setAriaSelected(TAB_STEPS);
			}
		});

		Tab tabVideo = new Tab();
		tabVideo.setIcon(IconType.FACETIME_VIDEO);
		tabVideo.setHeading(constants.tabVideo());
		tabVideo.add(panelVideo);
		tabVideo.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setAriaSelected(TAB_VIDEO);
			}
		});

		Tab tabAudio = new Tab();
		tabAudio.setIcon(IconType.MUSIC);
		tabAudio.setHeading(constants.tabAudio());
		tabAudio.add(panelAudio);
		tabAudio.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				setAriaSelected(TAB_AUDIO);
			}
		});

		// elements & IDs
		Element tabBasicListElement = tabBasic.asWidget().getElement();
		Element tabStepsListElement = tabSteps.asWidget().getElement();
		Element tabVideoListElement = tabVideo.asWidget().getElement();
		Element tabAudioListElement = tabAudio.asWidget().getElement();

		tabBasicElement = tabBasicListElement.getFirstChildElement();
		tabStepsElement = tabStepsListElement.getFirstChildElement();
		tabVideoElement = tabVideoListElement.getFirstChildElement();
		tabAudioElement = tabAudioListElement.getFirstChildElement();
		tabBasicElement.setId("tabBasic");
		tabStepsElement.setId("tabSteps");
		tabVideoElement.setId("tabVideo");
		tabAudioElement.setId("tabAudio");

		Element panelBasicElement = panelBasic.getElement();
		Element panelStepsElement = panelSteps.getElement();
		Element panelVideoElement = panelVideo.getElement();
		Element panelAudioElement = panelAudio.getElement();
		panelBasicElement.setId("panelBasic");
		panelStepsElement.setId("panelSteps");
		panelVideoElement.setId("panelVideo");
		panelAudioElement.setId("panelAudio");

		// ARIA roles
		// TODO should be added by gwtbootstrap widget
		roleTablist.set(tabsWidget.getElement().getFirstChildElement());

		roleTab.set(tabBasicElement);
		roleTab.set(tabStepsElement);
		roleTab.set(tabVideoElement);
		roleTab.set(tabAudioElement);
		roleTab.setAriaControlsProperty(tabBasicElement, Id.of(panelBasicElement));
		roleTab.setAriaControlsProperty(tabStepsElement, Id.of(panelStepsElement));
		roleTab.setAriaControlsProperty(tabVideoElement, Id.of(panelVideoElement));
		roleTab.setAriaControlsProperty(tabAudioElement, Id.of(panelAudioElement));

		rolePresentation.set(tabBasicListElement);
		rolePresentation.set(tabStepsListElement);
		rolePresentation.set(tabVideoListElement);
		rolePresentation.set(tabAudioListElement);

		roleTabpanel.set(panelBasicElement);
		roleTabpanel.set(panelStepsElement);
		roleTabpanel.set(panelVideoElement);
		roleTabpanel.set(panelAudioElement);
		roleTabpanel.setAriaLabelledbyProperty(panelBasicElement, Id.of(tabBasicElement));
		roleTabpanel.setAriaLabelledbyProperty(panelStepsElement, Id.of(tabStepsElement));
		roleTabpanel.setAriaLabelledbyProperty(panelVideoElement, Id.of(tabVideoElement));
		roleTabpanel.setAriaLabelledbyProperty(panelAudioElement, Id.of(tabAudioElement));

		// init widget
		tabsWidget.add(tabBasic);
		tabsWidget.add(tabSteps);
		tabsWidget.add(tabVideo);
		tabsWidget.add(tabAudio);
		initWidget(tabsWidget);
	}

	public void clearAll() {
		panelBasic.clear();
		panelSteps.clear();
		panelAudio.clear();
		panelVideo.clear();
	}

	public void displayRecipe(RecipeDetailsDto recipe, String view) {
		clearAll();
		if (recipe == null) {
			return;
		}

		// texts
		for (TextDto text : recipe.getTexts()) {
			HTML html = new HTML(text.getContent());
			panelBasic.add(html);
			NodeList<Element> elementsByTagName = html.getElement().getElementsByTagName("p");
			if (elementsByTagName.getLength() > 0) {
				// select first
				elementsByTagName.getItem(0).setClassName(Constants.LEAD);
			}
		}

		// steps
		final List<StepDto> steps = recipe.getSteps();
		pagingHandler = new PagingHandler() {
			@Override
			public void changePage(long page) {
				showStep(steps.get((int) page));
				NodeList<Element> elementsByTagName = panelBasic.getWidget(0).getElement().getElementsByTagName("p");
				for (int i=0; i < elementsByTagName.getLength(); i++) {
					elementsByTagName.getItem(i).removeClassName(Constants.LEAD);
				}
				elementsByTagName.getItem((int) page).setClassName(Constants.LEAD);
			}
		};
		simplePagingWidget = new SimplePagingWidget(pagingHandler);

		if (!steps.isEmpty()) {
			showStep(steps.get(0));
			panelSteps.add(stepPanel);
			panelSteps.add(simplePagingWidget);
			simplePagingWidget.setPage(0, steps.size());
		} else if (view != null && view.equalsIgnoreCase("steps")) {
			// no steps, show basic view
			view = null;
		}

		// video
		List<VideoDto> videos = recipe.getVideos();
		if (videos.isEmpty()) {
			panelVideo.add(new Paragraph(messages.recipeNoVideo()));
			if (view != null && view.equalsIgnoreCase("video")) {
				// no video, show basic view
				view = null;
			}
		} else if (!Video.isSupported()) {
			panelVideo.add(new Paragraph(messages.htmlVideoNotSupported()));
		}

		for (VideoDto videoDto : videos) {
			panelVideo.add(new VideoWidget(videoDto));
		}

		// audio
		List<AudioDto> audios = recipe.getAudios();
		if (audios.isEmpty()) {
			panelAudio.add(new Paragraph(messages.recipeNoAudio()));
			if (view != null && view.equalsIgnoreCase("audio")) {
				// no audio, show basic view
				view = null;
			}
		} else if (!Audio.isSupported()) {
			panelAudio.add(new Paragraph(messages.htmlAudioNotSupported()));
		}

		for (AudioDto audioDto : audios) {
			panelAudio.add(new AudioWidget(audioDto));
		}

		// set selected tab
		if (view == null || view.equalsIgnoreCase("basic")) {
			tabsWidget.selectTab(TAB_BASIC);
			setAriaSelected(TAB_BASIC);
		} else if (view.equalsIgnoreCase("steps")) {
			tabsWidget.selectTab(TAB_STEPS);
			setAriaSelected(TAB_STEPS);
		} else if (view.equalsIgnoreCase("video")) {
			tabsWidget.selectTab(TAB_VIDEO);
			setAriaSelected(TAB_VIDEO);
		} else if (view.equalsIgnoreCase("audio")) {
			tabsWidget.selectTab(TAB_AUDIO);
			setAriaSelected(TAB_AUDIO);
		}
	}

	/**
	 * Sets ARIA selected state to tabs.
	 * 
	 * @param selectedTab
	 */
	private void setAriaSelected(int selectedTab) {
		roleTab.setAriaSelectedState(tabBasicElement, selectedTab == TAB_BASIC ? SelectedValue.TRUE : SelectedValue.FALSE);
		roleTab.setAriaSelectedState(tabStepsElement, selectedTab == TAB_STEPS ? SelectedValue.TRUE : SelectedValue.FALSE);
		roleTab.setAriaSelectedState(tabVideoElement, selectedTab == TAB_VIDEO ? SelectedValue.TRUE : SelectedValue.FALSE);
		roleTab.setAriaSelectedState(tabAudioElement, selectedTab == TAB_AUDIO ? SelectedValue.TRUE : SelectedValue.FALSE);
	}

	/**
	 * Displays steps.
	 * 
	 * @param step DTO
	 */
	private void showStep(StepDto step) {
		stepPanel.clear();

		int page = step.getPage();
		String imageUrl = step.getImageUrl();
		if (imageUrl != null) {
			Image img = new Image(AbstractView.RECIPE_IMG_FOLDER + imageUrl);
			img.setAltText(messages.recipeStepNumber(page));
			img.setType(ImageType.ROUNDED);
			stepPanel.add(img);
		}

		Paragraph stepContent = new Paragraph(page + ". " + step.getContent());
		stepContent.setStyleName("stepContent");
		stepPanel.add(stepContent);
	}

	public void performActions(List<String> actions) {
		if (!this.isAttached()) {
			Window.alert("new actions ignored!");
			return;
		}

		try {
			for (String action : actions) {
				switch (tabsWidget.getSelectedTab()) {
				case TAB_BASIC:
				case TAB_STEPS:
					if (simplePagingWidget != null && pagingHandler != null) {
						int page = simplePagingWidget.getPage();
						int newPage = page;
						if (action.equalsIgnoreCase("forward") && simplePagingWidget.hasNext()) {
							newPage = page + 1;
						} else if (action.equalsIgnoreCase("back") && simplePagingWidget.hasPrevious()) {
							newPage = page - 1;
						} else {
							return;
						}

						simplePagingWidget.setPage(newPage, simplePagingWidget.getAllCount());
						pagingHandler.changePage(newPage);
						if (panelBasic.getWidgetCount() > 0) {
							NodeList<Element> elementsByTagName = panelBasic.getWidget(0).getElement().getElementsByTagName("p");
							elementsByTagName.getItem(page).removeClassName(Constants.LEAD);
							elementsByTagName.getItem(newPage).setClassName(Constants.LEAD);
						}
					}
					break;
				case TAB_AUDIO:
					Widget audioWidget = panelAudio.getWidget(0);
					if (audioWidget instanceof AudioWidget && action.equalsIgnoreCase("play")) {
						((AudioWidget) audioWidget).play();
					} else if (audioWidget instanceof AudioWidget && action.equalsIgnoreCase("pause")) {
						((AudioWidget) audioWidget).pause();
					}
					break;
				case TAB_VIDEO:
					Widget videoWidget = panelVideo.getWidget(0);
					if (videoWidget instanceof VideoWidget && action.equalsIgnoreCase("play")) {
						((VideoWidget) videoWidget).play();
					} else if (videoWidget instanceof VideoWidget && action.equalsIgnoreCase("pause")) {
						((VideoWidget) videoWidget).pause();
					}
					break;
				}
			}
		} catch (Exception e) {
			// ignore exceptions
		}
	}
}

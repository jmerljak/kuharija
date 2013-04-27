package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.common.dto.SubtitleDto;
import si.merljak.magistrska.common.dto.VideoDto;

import com.github.gwtbootstrap.client.ui.base.InlineLabel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.dom.client.VideoElement;
import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Video widget with player (if HTML5 video supported) and links for file / subtitle download.
 * 
 * @author Jakob Merljak
 * 
 */
public class VideoWidget extends Composite {

	// i18n
	private static final CommonConstants constants = Kuharija.constants;

	// constants
	private static final String OGG = "ogg";
	private static final String MP4 = "mp4";
	private static final String WEBM = "webm";
	private static final String VIDEO_FOLDER = GWT.getHostPageBaseURL() + "video/";
	private static final String SUBTITLE_FOLDER = GWT.getHostPageBaseURL() + "video/";

	// widgets
	private Video videoWidget;

	/**
	 * Video widget with player (if HTML5 video supported) and links for file / subtitle download.
	 * 
	 * @param videoDto video DTO
	 */
	public VideoWidget(VideoDto videoDto) {
		FlowPanel main = new FlowPanel();
		FlowPanel videoLinks = new FlowPanel();
		FlowPanel subtitleLinks = new FlowPanel();
		videoLinks.add(new InlineLabel(constants.downloadVideo()));
		subtitleLinks.add(new InlineLabel(constants.downloadSubtitle()));

		if (Video.isSupported()) {
			videoWidget = Video.createIfSupported();
			videoWidget.setAutoplay(false);
			videoWidget.setControls(true);
			videoWidget.setPoster(VIDEO_FOLDER + videoDto.getPosterUrl());
			videoWidget.setPreload(MediaElement.PRELOAD_METADATA);
			videoWidget.setWidth("100%");
			main.add(videoWidget);
		} else {
			// add a flash player?
		}

		// add all available sources (webm, mp4, ...)
		for (String srcUrl : videoDto.getUrls()) {
			Anchor downloadLink = new Anchor("", VIDEO_FOLDER + srcUrl);
			// start download immediately (no need for right click → save as)
			downloadLink.getElement().setAttribute("download", srcUrl);

			String lowerCaseName = srcUrl.toLowerCase();
			if (lowerCaseName.endsWith(MP4)) {
				downloadLink.setText(MP4);
				if (videoWidget != null) {
					videoWidget.addSource(VIDEO_FOLDER + srcUrl, VideoElement.TYPE_MP4);
				}
			} else if (lowerCaseName.endsWith(WEBM)) {
				downloadLink.setText(WEBM);
				if (videoWidget != null) {
					videoWidget.addSource(VIDEO_FOLDER + srcUrl, VideoElement.TYPE_WEBM);
				}
			} else if (lowerCaseName.endsWith(OGG)) {
				downloadLink.setText(OGG);
				if (videoWidget != null) {
					videoWidget.addSource(VIDEO_FOLDER + srcUrl, VideoElement.TYPE_OGG);
				}
			} else {
				downloadLink.setText(srcUrl);
				if (videoWidget != null) {
					videoWidget.addSource(VIDEO_FOLDER + srcUrl);
				}
			}

			videoLinks.add(downloadLink);
			videoLinks.add(new InlineLabel(" | "));
		}

		// subtitles
		for (SubtitleDto subtitle : videoDto.getSubtitles()) {
			String srcUrl = subtitle.getUrl();
			String language = subtitle.getLanguage().name();
			String localizedLanguage = constants.languageMap().get(language);

			Anchor downloadLink = new Anchor(localizedLanguage, SUBTITLE_FOLDER + srcUrl);
			// start download immediately (no need for right click → save as)
			downloadLink.getElement().setAttribute("download", srcUrl);
			subtitleLinks.add(downloadLink);
			subtitleLinks.add(new InlineLabel(" | "));

			if (videoWidget != null) {
				// subtitles/tracks are not yet supported in GWT, add manually
				Element trackElement = DOM.createElement("track");
				trackElement.setAttribute("kind", "subtitle");
				trackElement.setAttribute("srclang", language);
				trackElement.setAttribute("label", localizedLanguage);
				trackElement.setAttribute("src", SUBTITLE_FOLDER + srcUrl);
				DOM.appendChild(videoWidget.getElement(), trackElement);
			}
		}

		// remove last delimiter or label if no links
		videoLinks.remove(videoLinks.getWidgetCount() - 1);
		if (videoLinks.getWidgetCount() > 0) {
			main.add(videoLinks);
		}

		// remove last delimiter or label if no links
		subtitleLinks.remove(subtitleLinks.getWidgetCount() - 1);
		if (subtitleLinks.getWidgetCount() > 0) {
			main.add(subtitleLinks);
		}

		initWidget(main);
		setStyleName("videoWidget");
	}

	public void pause() {
		if (videoWidget != null) {
			videoWidget.pause();
		}
	}
}

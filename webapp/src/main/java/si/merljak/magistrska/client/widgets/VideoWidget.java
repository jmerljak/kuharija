package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.KuharijaEntry;
import si.merljak.magistrska.client.i18n.GlobalConstants;
import si.merljak.magistrska.client.i18n.GlobalMessages;
import si.merljak.magistrska.common.dto.SubtitleDto;
import si.merljak.magistrska.common.dto.VideoDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.VideoElement;
import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class VideoWidget extends Composite {

	private static final GlobalConstants constants = KuharijaEntry.getConstants();
	private static final GlobalMessages messages = KuharijaEntry.getMessages();

	private Video videoWidget;

	public VideoWidget(VideoDto videoDto) {
		if (Video.isSupported()) {
			videoWidget = Video.createIfSupported();
			videoWidget.setControls(true);
			videoWidget.setPoster(GWT.getHostPageBaseURL() + "video/" + videoDto.getPosterUrl());
			videoWidget.setWidth("100%");

			// subtitles are not yet supported by GWT, add plain HTML
			String trackHTML = "";
			for (SubtitleDto subtitle : videoDto.getSubtitles()) {
				trackHTML = trackHTML.concat("<track src='" + GWT.getHostPageBaseURL() + "video/" + subtitle.getUrl() + "' kind='subtitle' srclang='" + subtitle.getLanguage().name() + "' label='" + constants.languageMap().get(subtitle.getLanguage().name()) + "' />");
			}
			videoWidget.getVideoElement().setInnerHTML(trackHTML);

			// add all available sources (webm, mp4, ...)
			for (String srcUrl : videoDto.getUrls()) {
				String fileExt = srcUrl.substring(srcUrl.length() - 3, srcUrl.length());
				String videoType = fileExt.equalsIgnoreCase("mp4") ? VideoElement.TYPE_MP4 : VideoElement.TYPE_WEBM;
				videoWidget.addSource(GWT.getHostPageBaseURL() + "video/" + srcUrl, videoType);
			}

			initWidget(videoWidget);
		} else {
			initWidget(new Label(messages.htmlVideoNotSupported()));
			// TODO add links for download
			// <p>Your browser does not support video; download the <a href="video.webm">WebM</a>, <a href="video.mp4">mp4</a> or <a href="video.ogg">Ogg</a> video for off-line viewing.</p>
			// remind to upgrade browser
		}
	}

	public void pause() {
		videoWidget.pause();
	}
}

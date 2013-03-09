package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.KuharijaEntry;
import si.merljak.magistrska.client.i18n.GlobalMessages;
import si.merljak.magistrska.common.dto.VideoDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class VideoWidget extends Composite {

	private static final GlobalMessages messages = KuharijaEntry.getMessages();

	private Video videoWidget;

	public VideoWidget(VideoDto videoDto) {
		if (Video.isSupported()) {
			videoWidget = Video.createIfSupported();
			videoWidget.setControls(true);
			videoWidget.setPoster(videoDto.getPosterUrl());
			videoWidget.setWidth("100%");

			// add all available sources (webm, mp4, ...)
			for (String srcUrl : videoDto.getUrls()) {
				videoWidget.addSource(GWT.getHostPageBaseURL() + "video/" + srcUrl);
			}

			initWidget(videoWidget);
		} else {
			initWidget(new Label(messages.htmlVideoNotSupported()));
		}
	}

	public void pause() {
		videoWidget.pause();
	}
}

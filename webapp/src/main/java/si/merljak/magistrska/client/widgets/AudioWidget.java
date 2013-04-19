package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.CommonMessages;
import si.merljak.magistrska.common.dto.AudioDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AudioElement;
import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class AudioWidget extends Composite {

	private final CommonMessages messages = Kuharija.messages;

	private final String AUDIO_FOLDER = GWT.getHostPageBaseURL() + "audio/";

	private Audio audioWidget;

	public AudioWidget(AudioDto audioDto) {
		if (Audio.isSupported()) {
			audioWidget = Audio.createIfSupported();
			audioWidget.setControls(true);
			audioWidget.setPreload(MediaElement.PRELOAD_METADATA);

			// add all available sources (mp3, ogg, ...)
			for (String srcUrl : audioDto.getUrls()) {
				String fileExt = srcUrl.substring(srcUrl.length() - 3, srcUrl.length());
				if (fileExt.equalsIgnoreCase("mp3")) {
					audioWidget.addSource(AUDIO_FOLDER + srcUrl, AudioElement.TYPE_MP3);
				} else if (fileExt.equalsIgnoreCase("ogg")) {
					audioWidget.addSource(AUDIO_FOLDER + srcUrl, AudioElement.TYPE_OGG);
				} else if (fileExt.equalsIgnoreCase("wav")) {
					audioWidget.addSource(AUDIO_FOLDER + srcUrl, AudioElement.TYPE_WAV);
				} else {
					audioWidget.addSource(AUDIO_FOLDER + srcUrl);
				}
			}

			initWidget(audioWidget);
		} else {
			FlowPanel fallbackPanel = new FlowPanel();
			fallbackPanel.add(new Label(messages.htmlAudioNotSupported()));
			// TODO add links for download 
			// always?
			for (String srcUrl : audioDto.getUrls()) {
				fallbackPanel.add(new Anchor(srcUrl, AUDIO_FOLDER + srcUrl));
			}
			// remind to upgrade browser
			initWidget(fallbackPanel);
		}
	}

	public void pause() {
		if (audioWidget != null) {
			audioWidget.pause();
		}
	}
}

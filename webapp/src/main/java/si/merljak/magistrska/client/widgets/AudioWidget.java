package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.CommonMessages;
import si.merljak.magistrska.common.dto.AudioDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AudioElement;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class AudioWidget extends Composite {

	private static final CommonMessages messages = Kuharija.messages;

	protected static final String AUDIO_FOLDER = GWT.getHostPageBaseURL() + "audio/";

	private Audio audioWidget;

	public AudioWidget(AudioDto audioDto) {
		if (Audio.isSupported()) {
			audioWidget = Audio.createIfSupported();
			audioWidget.setControls(true);

			// add all available sources (mp3, ogg, ...)
			for (String srcUrl : audioDto.getUrls()) {
				String fileExt = srcUrl.substring(srcUrl.length() - 3, srcUrl.length());
				String audioType = fileExt.equalsIgnoreCase("mp3") ? AudioElement.TYPE_MP3 : AudioElement.TYPE_OGG;
				audioWidget.addSource(AUDIO_FOLDER + srcUrl, audioType);
			}

			initWidget(audioWidget);
		} else {
			initWidget(new Label(messages.htmlAudioNotSupported()));
			// TODO add links for download
			// remind to upgrade browser
		}
	}

	public void pause() {
		audioWidget.pause();
	}
}

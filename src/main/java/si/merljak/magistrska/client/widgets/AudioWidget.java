package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.Kuharija;
import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.common.dto.AudioDto;

import com.github.gwtbootstrap.client.ui.base.InlineLabel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.AudioElement;
import com.google.gwt.dom.client.MediaElement;
import com.google.gwt.media.client.Audio;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;

/**
 * Audio widget with player (if HTML5 audio supported) and links for file download.
 * 
 * @author Jakob Merljak
 * 
 */
public class AudioWidget extends Composite {

	// i18n
	private final CommonConstants constants = Kuharija.constants;

	// constants
	private static final String MP3 = "mp3";
	private static final String OGG = "ogg";
	private static final String WAV = "wav";
	private static final String AUDIO_FOLDER = GWT.getHostPageBaseURL() + "audio/";

	// widgets
	private Audio audioWidget;

	/**
	 * Audio widget with player (if HTML5 audio supported) and links for file download.
	 * 
	 * @param audioDto audio DTO
	 */
	public AudioWidget(AudioDto audioDto) {
		FlowPanel main = new FlowPanel();
		FlowPanel links = new FlowPanel();
		links.add(new InlineLabel(constants.downloadAudio()));

		if (Audio.isSupported()) {
			audioWidget = Audio.createIfSupported();
			audioWidget.setAutoplay(false);
			audioWidget.setControls(true);
			audioWidget.setPreload(MediaElement.PRELOAD_METADATA);
			main.add(audioWidget);
		} else {
			// add a flash player?
		}

		// add all available sources (mp3, ogg, ...)
		for (String srcUrl : audioDto.getUrls()) {
			Anchor downloadLink = new Anchor("", AUDIO_FOLDER + srcUrl);
			// start download immediately (no need for right click → save as)
			downloadLink.getElement().setAttribute("download", srcUrl);

			String lowerCaseName = srcUrl.toLowerCase();
			if (lowerCaseName.endsWith(MP3)) {
				downloadLink.setText(MP3);
				if (audioWidget != null) {
					audioWidget.addSource(AUDIO_FOLDER + srcUrl, AudioElement.TYPE_MP3);
				}
			} else if (lowerCaseName.endsWith(OGG)) {
				downloadLink.setText(OGG);
				if (audioWidget != null) {
					audioWidget.addSource(AUDIO_FOLDER + srcUrl, AudioElement.TYPE_OGG);
				}
			} else if (lowerCaseName.endsWith(WAV)) {
				downloadLink.setText(WAV);
				if (audioWidget != null) {
					audioWidget.addSource(AUDIO_FOLDER + srcUrl, AudioElement.TYPE_WAV);
				}
			} else {
				downloadLink.setText(srcUrl);
				if (audioWidget != null) {
					audioWidget.addSource(AUDIO_FOLDER + srcUrl);
				}
			}

			links.add(downloadLink);
			links.add(new InlineLabel(" | "));
		}

		// remove last delimiter or label if no links
		links.remove(links.getWidgetCount() - 1);
		if (links.getWidgetCount() > 0) {
			main.add(links);
		}

		initWidget(main);
		setStyleName("audioWidget");
	}

	public void play() {
		if (audioWidget != null) {
			audioWidget.play();
		}
	}

	public void pause() {
		if (audioWidget != null) {
			audioWidget.pause();
		}
	}
}

package si.merljak.magistrska.client.widgets;

import si.merljak.magistrska.client.KuharijaEntry;
import si.merljak.magistrska.client.i18n.CommonConstants;
import si.merljak.magistrska.client.i18n.CommonMessages;
import si.merljak.magistrska.common.dto.SubtitleDto;
import si.merljak.magistrska.common.dto.VideoDto;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.VideoElement;
import com.google.gwt.media.client.Video;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

public class VideoWidget extends Composite {

	private static final CommonConstants constants = KuharijaEntry.constants;
	private static final CommonMessages messages = KuharijaEntry.messages;

	private Video videoWidget;

	public VideoWidget(VideoDto videoDto) {
		if (Video.isSupported()) {
			videoWidget = Video.createIfSupported();
			videoWidget.setControls(true);
			videoWidget.setPoster(GWT.getHostPageBaseURL() + "video/" + videoDto.getPosterUrl());
			videoWidget.setWidth("100%");

			// add all available sources (webm, mp4, ...)
			for (String srcUrl : videoDto.getUrls()) {
				String fileExt = srcUrl.substring(srcUrl.length() - 3, srcUrl.length());
				String videoType = fileExt.equalsIgnoreCase("mp4") ? VideoElement.TYPE_MP4 : VideoElement.TYPE_WEBM;
				videoWidget.addSource(GWT.getHostPageBaseURL() + "video/" + srcUrl, videoType);
			}

			// subtitles
			for (SubtitleDto subtitle : videoDto.getSubtitles()) {
				String language = subtitle.getLanguage().name();

				// subtitles/tracks are not yet supported in GWT, add manually
				Element trackElement = DOM.createElement("track");
			    trackElement.setAttribute("kind", "subtitle");
				trackElement.setAttribute("srclang", language);
			    trackElement.setAttribute("label", constants.languageMap().get(language));
			    trackElement.setAttribute("src", GWT.getHostPageBaseURL() + "video/" + subtitle.getUrl());
			    DOM.appendChild(videoWidget.getElement(), trackElement);
			}

			initWidget(videoWidget);
		} else {
			initWidget(new Label(messages.htmlVideoNotSupported()));
			// TODO add links for download
			// <p>Your browser does not support video; download the <a href="video.webm">WebM</a>, <a href="video.mp4">mp4</a> or <a href="video.ogg">Ogg</a> video for off-line viewing.</p>
			// remind to upgrade browser
			
//			String htlm = "<script>jwplayer(\"video\").setup({playlist: [{image: \"" + GWT.getHostPageBaseURL() + "img/glyphicons-halflings.png\", file: \"" + GWT.getHostPageBaseURL() + "video/Shaun.mp4\",  captions: [ { file: \"" + GWT.getHostPageBaseURL() + "video/Shaun.vtt\", label: \"English\" } ] }] });</script>";
//			initWidget(new HTML(htlm));
//			KuharijaEntry.addScript("/jwplayer/jwplayer.js ");
		}
	}

	public void pause() {
		videoWidget.pause();
	}
}

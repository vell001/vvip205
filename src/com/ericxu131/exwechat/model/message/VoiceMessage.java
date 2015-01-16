package com.ericxu131.exwechat.model.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author eric
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class VoiceMessage extends Message {

    public VoiceMessage() {
        super();
        msgType = MessageType.VOICE;
    }
    @XmlElement(name = "MediaId")
    private String mediaId;
    @XmlElement(name = "Format")
    private String format;
    @XmlElement(name = "Recognition")
    private String recognition;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getRecognition() {
        return recognition;
    }

    public void setRecognition(String recognition) {
        this.recognition = recognition;
    }

    @Override
    public String toString() {
        return "VoiceMessage{" + "mediaId=" + mediaId + ", format=" + format + ", recognition=" + recognition + '}';
    }

}

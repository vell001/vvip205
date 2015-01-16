package com.ericxu131.exwechat.model.message;

import com.ericxu131.exwechat.model.event.SimpleEvent;
import javax.xml.bind.annotation.XmlEnumValue;

/**
 *
 * @author eric
 */
public enum MessageType {

    @XmlEnumValue("text")
    TEXT(TextMessage.class),
    @XmlEnumValue("event")
    EVENT(SimpleEvent.class),
    @XmlEnumValue("voice")
    VOICE(VoiceMessage.class),
    @XmlEnumValue("news")
    NEWS(VoiceMessage.class);

    private MessageType(Class messageClass) {
        this.messageClass = messageClass;
    }

    private final Class messageClass;

    public Class getMessageClass() {
        return messageClass;
    }
}

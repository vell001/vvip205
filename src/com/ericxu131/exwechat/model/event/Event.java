package com.ericxu131.exwechat.model.event;

import com.ericxu131.exwechat.model.message.Message;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 *
 * @author eric
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Event extends Message {

    @XmlElement(name = "Event")
    private EventType event;

    public EventType getEvent() {
        return event;
    }

    public void setEvent(EventType event) {
        this.event = event;
    }

}

package com.ericxu131.exwechat.model.event;

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
public class ClickEvent extends Event {

    @XmlElement(name = "EventKey")
    private String eventKey;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    @Override
    public String toString() {
        return "ClickEvent{" + "eventKey=" + eventKey + '}';
    }

}

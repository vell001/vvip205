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
public class ScanEvent extends Event {

    @XmlElement(name = "EventKey")
    private String eventKey;

    @XmlElement(name = "Ticket")
    private String ticket;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    @Override
    public String toString() {
        return "ScanEvent{" + "eventKey=" + eventKey + ", ticket=" + ticket + '}';
    }

}

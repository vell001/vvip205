package com.ericxu131.exwechat.model.message;

import com.ericxu131.exwechat.utils.AdapterCDATA;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author eric
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class TextMessage extends Message {

    public TextMessage() {
        super();
        msgType = MessageType.TEXT;
    }

    @XmlElement(name = "Content")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "TextMessage{" + "content=" + content + '}';
    }

}

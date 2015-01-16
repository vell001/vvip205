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
@XmlRootElement(name = "item")
@XmlAccessorType(XmlAccessType.FIELD)
public class NewsMessageItem {

    @XmlElement(name = "Title")
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    private String title;
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    @XmlElement(name = "Description")
    private String description;
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    @XmlElement(name = "PicUrl")
    private String picUrl;
    @XmlJavaTypeAdapter(AdapterCDATA.class)
    @XmlElement(name = "Url")
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}

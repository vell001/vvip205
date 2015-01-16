package com.ericxu131.exwechat.model.message;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author eric
 */
@XmlRootElement(name = "xml")
@XmlAccessorType(XmlAccessType.FIELD)
public class NewsMessage extends Message {

    @XmlElement(name = "ArticleCount")
    private Integer articleCount;

    @XmlElementWrapper(name = "Articles")
    @XmlElement(name = "item")
    private List<NewsMessageItem> items;

    public NewsMessage() {
        super();
        msgType = MessageType.NEWS;
    }

    public List<NewsMessageItem> getItems() {
        if (items == null) {
            items = new ArrayList<NewsMessageItem>();
        }
        return items;
    }

    public void setItems(List<NewsMessageItem> items) {
        this.items = items;
    }

    public Integer getArticleCount() {
        return articleCount;
    }

    public void setArticleCount(Integer articleCount) {
        this.articleCount = articleCount;
    }

    public void addItem(NewsMessageItem item) {
        if (articleCount == null) {
            articleCount = 0;
        }
        articleCount++;
        getItems().add(item);
    }
}

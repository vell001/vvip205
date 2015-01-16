package com.ericxu131.exwechat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author eric
 */
public class WechatQRCode extends WechatMessageResult {

    @Expose
    private String ticket;
    @Expose
    @SerializedName("expire_seconds")
    private Integer expireSeconds;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(Integer expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

}

package com.ericxu131.exwechat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author eric
 */
public class WechatUser extends WechatMessageResult {

    @Expose
    private boolean subscribe;
    @Expose
    private String openid, nickname, language, city, province, country, headimgurl;
    @Expose
    private WechatUserSex sex;
    @Expose
    @SerializedName("subscribe_time")
    private Long subscribeTime;

    public boolean isSubscribe() {
        return subscribe;
    }

    public void setSubscribe(boolean subscribe) {
        this.subscribe = subscribe;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public WechatUserSex getSex() {
        return sex;
    }

    public void setSex(WechatUserSex sex) {
        this.sex = sex;
    }

    public Long getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Long subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    @Override
    public String toString() {
        return "WechatUser{" + "subscribe=" + subscribe + ", openid=" + openid + ", nickname=" + nickname + ", language=" + language + ", city=" + city + ", province=" + province + ", country=" + country + ", headimgurl=" + headimgurl + ", sex=" + sex + ", subscribeTime=" + subscribeTime + ", errorCode=" + getErrorCode() + ", errorMessage=" + getErrorMessage() + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (this.openid != null ? this.openid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final WechatUser other = (WechatUser) obj;
        if ((this.openid == null) ? (other.openid != null) : !this.openid.equals(other.openid)) {
            return false;
        }
        return true;
    }

}

package com.ericxu131.exwechat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 *
 * @author eric
 */
public class WechatMessageResult {

    @Expose
    @SerializedName("errcode")
    private String errorCode;
    @Expose
    @SerializedName("errmsg")
    private String errorMessage;

    public boolean isSuccess() {
        return getErrorCode() != null && "0".equals(getErrorCode());
    }

    /**
     * success value '0'
     *
     * @return
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     *
     * @param errorCode
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * success value 'ok'
     *
     * @return
     */
    public String getErrorMessage() {
        return errorMessage;
    }

    /**
     *
     * @param errorMessage
     */
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "WechatMessageResult{" + "errorCode=" + errorCode + ", errorMessage=" + errorMessage + '}';
    }

}

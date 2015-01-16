package com.ericxu131.exwechat;

/**
 *
 * @author eric
 */
public class WechatAccessException extends RuntimeException {

    private String errcode;
    private String errmsg;

    public WechatAccessException(String errcode, String errmsg) {
        super("errcode:" + errcode + " errmsg:" + errmsg);
        this.errcode = errcode;
        this.errmsg = errmsg;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

}

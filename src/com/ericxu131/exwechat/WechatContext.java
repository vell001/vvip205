package com.ericxu131.exwechat;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author eric
 */
public class WechatContext {

    private static final ThreadLocal<WechatContext> threadLocal = new ThreadLocal<WechatContext>();

    public static WechatContext getCurrentInstance() {
        if (threadLocal.get() == null) {
            threadLocal.set(new WechatContext());
        }
        return threadLocal.get();
    }

    private HttpServletRequest request;

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

}

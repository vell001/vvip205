package com.ericxu131.exwechat;

/**
 *
 * @author eric
 */
public enum QRCodeCreateActionName {

    QR_SCENE("QR_SCENE"), QR_LIMIT_SCENE("QR_LIMIT_SCENE");

    private final String value;

    private QRCodeCreateActionName(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}

package com.ericxu131.exwechat.model;

import javax.xml.bind.annotation.XmlEnumValue;

/**
 *
 * @author eric
 */
public enum WechatUserSex {

    @XmlEnumValue("0")
    UNKNOWN,
    @XmlEnumValue("1")
    MALE,
    @XmlEnumValue("2")
    FEMALE;
}

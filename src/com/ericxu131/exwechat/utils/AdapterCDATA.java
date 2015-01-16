package com.ericxu131.exwechat.utils;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 *
 * @author eric
 */
public class AdapterCDATA extends XmlAdapter<String, String> {

    @Override
    public String marshal(String arg0) throws Exception {
        return "<![CDATA[" + arg0 + "]]>";
    }

    @Override
    public String unmarshal(String arg0) throws Exception {
        return arg0;
    }

}

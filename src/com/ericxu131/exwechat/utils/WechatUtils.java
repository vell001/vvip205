package com.ericxu131.exwechat.utils;

import com.ericxu131.exwechat.WechatAccessException;
import com.ericxu131.exwechat.model.AccessToken;
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author eric
 */
public class WechatUtils {

    private static final Logger logger = LoggerFactory.getLogger(WechatUtils.class);

    public static String sign(String timestamp, String nonce, String token) {
        if (timestamp == null || nonce == null || token == null) {
            throw new IllegalArgumentException("Argument cannot be null.");
        }
        List<String> params = new ArrayList<String>();
        params.add(timestamp);
        params.add(nonce);
        params.add(token);
        logger.debug("before sort {}", StringUtils.join(params, ","));
        Collections.sort(params);
        logger.debug("end sort {}", StringUtils.join(params, ","));
        String result = DigestUtils.sha1Hex(StringUtils.join(params, ""));
        logger.debug("sha1hex {}", result);
        return result;
    }

    public static AccessToken getAccessToken(String appid, String secret) {
        if (appid == null || secret == null) {
            throw new IllegalArgumentException("appid or secret was null.");
        }
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource cosmsservice = client.resource("https://api.weixin.qq.com/cgi-bin/token");
        ClientResponse clientResponse = cosmsservice
                .queryParam("grant_type", "client_credential")
                .queryParam("appid", appid)
                .queryParam("secret", secret)
                .get(ClientResponse.class);

        if (clientResponse.getStatus() != 200) {
            throw new IllegalStateException("status error:" + clientResponse.getStatus());
        } else {
            Map result = new Gson().fromJson(clientResponse.getEntity(String.class), Map.class);

            if (result.get("errcode") != null) {
                throw new WechatAccessException(result.get("errcode") + "", result.get("errmsg") + "");
            } else {
                return new AccessToken((String) result.get("access_token"), ((Double) result.get("expires_in")).longValue());
            }

        }
    }
}

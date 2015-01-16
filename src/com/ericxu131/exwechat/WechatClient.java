package com.ericxu131.exwechat;

import com.ericxu131.exwechat.model.AccessToken;
import com.ericxu131.exwechat.model.WechatMessageResult;
import com.ericxu131.exwechat.model.WechatQRCode;
import com.ericxu131.exwechat.model.WechatUser;
import com.ericxu131.exwechat.utils.WechatUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import static com.google.gson.stream.JsonToken.BOOLEAN;
import static com.google.gson.stream.JsonToken.NULL;
import static com.google.gson.stream.JsonToken.NUMBER;
import com.google.gson.stream.JsonWriter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author eric
 */
public class WechatClient {

    private static final Logger logger = LoggerFactory.getLogger(WechatClient.class);
    private final String appid, secret;

    private static Map<String, AccessToken> tokenMap = new HashMap<String, AccessToken>();
    private final Gson gson;

    public WechatClient(String appid, String secret) {
        this.appid = appid;
        this.secret = secret;
        gson = new GsonBuilder()
                .registerTypeAdapter(Boolean.class, booleanAsIntAdapter)
                .registerTypeAdapter(boolean.class, booleanAsIntAdapter)
                .create();
    }

    private synchronized AccessToken getAccessToken() {
        AccessToken accessToken = tokenMap.get(appid);
        Long now = new Date().getTime();
        if (accessToken == null || now > accessToken.getExpiresTimestemp()) {
            accessToken = WechatUtils.getAccessToken(appid, secret);
        }
        return accessToken;
    }

    public WechatMessageResult send(String message, String toUser) {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource webResource = client.resource("https://api.weixin.qq.com/cgi-bin/message/custom/send");
        ClientResponse clientResponse = webResource
                .queryParam("access_token", getAccessToken().getToken())
                .post(ClientResponse.class, new Gson().toJson(new MassageBuilder("text").toUser(toUser).textContent(message).bulid()));

        if (clientResponse.getStatus() != 200) {
            throw new IllegalStateException("status error:" + clientResponse.getStatus());
        } else {
            return new Gson().fromJson(clientResponse.getEntity(String.class), WechatMessageResult.class);
        }
    }

    public WechatQRCode qrCodeCreate(Integer expireSeconds, QRCodeCreateActionName actionName, int sceneId) {
        if (QRCodeCreateActionName.QR_SCENE == actionName) {
            if (expireSeconds == null || expireSeconds < 1 || expireSeconds > 1800) {
                throw new IllegalArgumentException("expireSeconds must between 1 to 1800");
            }
        }

        if (sceneId < 1 || sceneId > 100000) {
            throw new IllegalArgumentException("sceneId must between 1 to 100000");
        }

        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource webResource = client.resource("https://api.weixin.qq.com/cgi-bin/qrcode/create");
        ClientResponse clientResponse;
        Map data = new QRCodeBuilder(actionName).expireSeconds(expireSeconds).sceneId(sceneId).bulid();
        logger.debug("QRCode Post:{}", data.toString());
        clientResponse = webResource
                .queryParam("access_token", getAccessToken().getToken())
                .post(ClientResponse.class, new Gson().toJson(data));
        if (clientResponse.getStatus() != 200) {
            throw new IllegalStateException("status error:" + clientResponse.getStatus());
        } else {
            return gson.fromJson(clientResponse.getEntity(String.class), WechatQRCode.class);
        }
        //{"ticket":"gQG28DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL0FuWC1DNmZuVEhvMVp4NDNMRnNRAAIEesLvUQMECAcAAA==","expire_seconds":1800}
        //{"errcode":40013,"errmsg":"invalid appid"}
    }

    public WechatUser getUserInfo(String openid) {
        ClientConfig config = new DefaultClientConfig();
        Client client = Client.create(config);
        WebResource webResource = client.resource("https://api.weixin.qq.com/cgi-bin/user/info");
        ClientResponse clientResponse = webResource
                .queryParam("access_token", getAccessToken().getToken())
                .queryParam("openid", openid)
                .get(ClientResponse.class);
        if (clientResponse.getStatus() != 200) {
            throw new IllegalStateException("status error:" + clientResponse.getStatus());
        } else {
            return gson.fromJson(clientResponse.getEntity(String.class), WechatUser.class);
        }
    }

    private static class QRCodeBuilder {

        private final Map qrCode = new HashMap();

        public QRCodeBuilder(QRCodeCreateActionName actionName) {
            qrCode.put("action_name", actionName.getValue());
        }

        private QRCodeBuilder expireSeconds(Integer expireSeconds) {
            if (expireSeconds != null) {
                qrCode.put("expire_seconds", expireSeconds);
            }
            return this;
        }

        private QRCodeBuilder sceneId(int sceneId) {
            Map actionInfo = (Map) qrCode.get("action_info");
            if (actionInfo == null) {
                actionInfo = new HashMap();
                qrCode.put("action_info", actionInfo);
            }
            Map scene = (Map) actionInfo.get("scene");
            if (scene == null) {
                scene = new HashMap();
                actionInfo.put("scene", scene);
            }
            scene.put("scene_id", sceneId);
            return this;
        }

        private Map bulid() {
            return qrCode;
        }

    }

    public static class MassageBuilder {

        private final Map message = new HashMap();

        public MassageBuilder(String type) {
            message.put("msgtype", type);
        }

        public MassageBuilder toUser(String user) {
            message.put("touser", user);
            return this;
        }

        public MassageBuilder textContent(String content) {
            Map text = (Map) message.get("text");
            if (text == null) {
                text = new HashMap();
                message.put("text", text);
            }
            text.put("content", content);
            return this;
        }

        public Map bulid() {
            logger.debug("build message {}", message);
            return message;
        }

    }
    private static final TypeAdapter<Boolean> booleanAsIntAdapter = new TypeAdapter<Boolean>() {
        @Override
        public void write(JsonWriter out, Boolean value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value);
            }
        }

        @Override
        public Boolean read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case BOOLEAN:
                    return in.nextBoolean();
                case NULL:
                    in.nextNull();
                    return null;
                case NUMBER:
                    return in.nextInt() != 0;
                case STRING:
                    return Boolean.parseBoolean(in.nextString());
                default:
                    throw new IllegalStateException("Expected BOOLEAN or NUMBER but was " + peek);
            }
        }
    };

}

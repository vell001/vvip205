package com.ericxu131.exwechat.web;

import com.ericxu131.exwechat.WechatContext;
import com.ericxu131.exwechat.model.event.SimpleEvent;
import com.ericxu131.exwechat.model.message.Message;
import com.ericxu131.exwechat.model.message.NewsMessage;
import com.ericxu131.exwechat.model.message.SimpleMessage;
import com.ericxu131.exwechat.model.message.TextMessage;
import com.ericxu131.exwechat.utils.JAXBUtils;
import com.ericxu131.exwechat.utils.WechatUtils;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author eric
 */
public abstract class WechatServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(WechatServlet.class);

    private static final String PARAM_SIGNATURE = "signature",
            PARAM_TIMESTAMP = "timestamp",
            PARAM_NONCE = "nonce",
            PARAM_ECHOSTR = "echostr";

    protected abstract String getToken();

    protected abstract Message onMessage(Message message);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        if (StringUtils.isEmpty(getToken())) {
            throw new IllegalStateException("Token cannot be empty.");
        }
        String signature = request.getParameter(PARAM_SIGNATURE);
        String timestamp = request.getParameter(PARAM_TIMESTAMP);
        String nonce = request.getParameter(PARAM_NONCE);
        String echostr = request.getParameter(PARAM_ECHOSTR);
        //logger.debug("signature:{},timestamp:{},nonce:{},echostr:{}", signature, timestamp, nonce, echostr);
        if (signature == null || timestamp == null || nonce == null || echostr == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Signature and timestamp and nonce and echostr cannot be null.");
            return;
        }

        if (signature.equals(WechatUtils.sign(timestamp, nonce, getToken()))) {
            PrintWriter writer = response.getWriter();
            try {
                writer.print(echostr);
            } finally {
                writer.close();
            }
        } else {

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        WechatContext.getCurrentInstance().setRequest(request);
        String xml;
        try {
            StringWriter stringWriter = new StringWriter();
            IOUtils.copy(request.getInputStream(), stringWriter, "UTF-8");
            xml = stringWriter.toString();
        } catch (IOException ex) {
            throw new IllegalStateException(ex);
        }

        logger.debug("request xml {}", xml);
        Message message = parserMessage(xml);
        if (message != null) {
            logger.debug("request message {}", message.toString());
            Message responseMessage = onMessage(message);
            if (responseMessage != null) {
                logger.debug("response message {}", responseMessage.toString());
                PrintWriter writer = response.getWriter();
                try {
                    logger.debug("response Message class {}", responseMessage.getClass());
                    String responseXML = JAXBUtils.objectToXml(responseMessage, responseMessage.getClass());
                    logger.debug("response xml {}", responseXML);
                    writer.print(responseXML);
                } finally {
                    writer.close();
                }
            }
        }
    }

    private Message parserMessage(String xml) {
        if (StringUtils.isEmpty(xml)) {
            return null;
        }
        SimpleMessage message = JAXBUtils.parserString(xml, SimpleMessage.class);
        if (message.getMsgType() == null || message.getMsgType().getMessageClass() == null) {
            return null;
        }
        Message m = JAXBUtils.parserString(xml, message.getMsgType().getMessageClass());

        if (m instanceof SimpleEvent) {
            SimpleEvent smimpleEvent = (SimpleEvent) m;
            if (smimpleEvent.getEvent() == null || smimpleEvent.getEvent().getEventClass() == null) {
                return null;
            }
            return JAXBUtils.parserString(xml, smimpleEvent.getEvent().getEventClass());
        } else {
            return m;
        }
    }

    protected TextMessage replyTextMessage(Message message) {
        TextMessage responseTextMessage = new TextMessage();
        responseTextMessage.setFromUserName(message.getToUserName());
        responseTextMessage.setToUserName(message.getFromUserName());
        return responseTextMessage;
    }

    protected NewsMessage replyNewsMessage(Message message) {
        NewsMessage responseTextMessage = new NewsMessage();
        responseTextMessage.setFromUserName(message.getToUserName());
        responseTextMessage.setToUserName(message.getFromUserName());
        return responseTextMessage;
    }
}

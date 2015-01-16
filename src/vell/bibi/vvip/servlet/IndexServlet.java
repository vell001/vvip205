package vell.bibi.vvip.servlet;

import javax.servlet.annotation.WebServlet;

import vell.bibi.vvip.service.TextMessageService;

import com.ericxu131.exwechat.model.message.Message;
import com.ericxu131.exwechat.model.message.TextMessage;
import com.ericxu131.exwechat.web.WechatServlet;

/**
 *
 * @author eric
 */
@WebServlet(name = "IndexServlet", urlPatterns = {"/index"})
public class IndexServlet extends WechatServlet {
	private static final long serialVersionUID = -8587556346815082569L;

	@Override
    protected String getToken() {
        return "vell001";
    }

    @Override
    protected synchronized Message onMessage(Message message) {
        //接收文本信息
        if (message instanceof TextMessage) {
            TextMessage textMessage = (TextMessage) message;
          //创建回复的信息
            TextMessage responseMessage = replyTextMessage(message);
            /*if ("hi".equals(textMessage.getContent())) {
                
                //创建一个Client来获取用户信息，这里要填写appid和secret
                //WechatClient wechatClient = new WechatClient("appid", "secret");
                //获取用户信息
                //WechatUser wechatUser = wechatClient.getUserInfo(message.getFromUserName());
                responseMessage.setContent(String.format("Hi:%s", message.getFromUserName()));
                
            } else {
            	responseMessage.setContent("welcome to vell001\n");
            }*/
            TextMessageService.getInstance().service(textMessage, responseMessage);
            return responseMessage;
        }
        //接收自定义菜单点击事件
/*        if (message instanceof ClickEvent) {
            ClickEvent clickEvent = (ClickEvent) message;
            if ("V1001_XXX".equals(clickEvent.getEventKey())) {
                //处理逻辑写在这里
            }
        }*/
        return null;
    }

}
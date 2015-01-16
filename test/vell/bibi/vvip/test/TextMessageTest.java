package vell.bibi.vvip.test;

import vell.bibi.vvip.service.TextMessageService;

import com.ericxu131.exwechat.model.message.TextMessage;

public class TextMessageTest {
	public static void main(String[] args) {
		TextMessageService mTextMessageService = TextMessageService.getInstance();
		
		TextMessage req = new TextMessage();
		TextMessage resp = new TextMessage();
		req.setFromUserName("oIgpnt7eMdOY6sjT6DoXyfZooMnM");
		req.setContent("ls -a");
		
		mTextMessageService.service(req, resp);
		
		System.out.println(resp.getContent());
	}
}

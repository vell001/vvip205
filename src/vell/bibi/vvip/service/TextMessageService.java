package vell.bibi.vvip.service;

import vell.bibi.vvip.dao.RecordDAO;
import vell.bibi.vvip.dao.UserDAO;
import vell.bibi.vvip.model.User;
import vell.bibi.vvip.util.StrUtil;

import com.ericxu131.exwechat.model.message.TextMessage;

public class TextMessageService {
	private UserDAO mUserDAO = UserDAO.getInstance();
	private RecordDAO mRecordDAO = RecordDAO.getInstance();
	private ParseService mParseService = ParseService.getInstance();
	private StringService mStringService = StringService.getInstance();
	private UserService mUserService = UserService.getInstance();
	private RecordService mRecordService = RecordService.getInstance();
	
	private TextMessageService(){}
	private static TextMessageService service = null;
	public static TextMessageService getInstance() {
		if(service == null)
			service = new TextMessageService();
		return service;
	}
	
	public void service(TextMessage req, TextMessage resp){
		User fromUser = mUserDAO.getUserByWechatId(req.getFromUserName());
		String[] cmds = null;
		if(!StrUtil.isEmpty(req.getContent()))
			cmds = mParseService.parse(req.getContent());
		
		if(null == cmds || cmds.length == 0){ // commands is null
			resp.setContent(mStringService.getBindHelpStr());
			return;
		} else { //  Global command area
			if(StrUtil.equals(cmds[0], "hi")) { // hi 
				resp.setContent("hello, welcome to vell001\n" +
						"your wechatId is " + req.getFromUserName());
				return;
			}
			if(StrUtil.equals(cmds[0], "help")) { // help
				resp.setContent(mStringService.getHelpStr());
				return;
			}
		}
		
		if(fromUser == null) { // Anonymous user command area
			if(StrUtil.equals(cmds[0], "bind")) { // bind command
				mUserService.bindWechat(cmds, req, resp);
				return;
			} // bind command
			
			// other command
			resp.setContent(mStringService.getBindHelpStr());
			return;
		} else { // Ordinary user command area
			if(fromUser.getStatus() == User.FORZEN) { // frozen user
				resp.setContent("你的账号已被冻结，请联系管理员\n贺磊: 18392605732");
				return;
			}
			
			if(fromUser.getStatus() >= User.NORMAL) { // normal user command area
				if(StrUtil.equals(cmds[0], "1") 
						|| (StrUtil.equals(cmds[0], "ls") && cmds.length < 2)
						|| StrUtil.equals(cmds[0], "余额")
						|| StrUtil.equals(cmds[0], "ye")) { // Query balance
					mUserService.getBalance(cmds, fromUser, resp);
					return;
				}
				if(StrUtil.equals(cmds[0], "2")
						|| StrUtil.equals(cmds[0], "h")){ // Query historical bills
					mRecordService.getRecords(cmds, fromUser, resp);
					return;
				}
				if((StrUtil.equals(cmds[0], "info")
						|| StrUtil.equals(cmds[0], "i")
						|| StrUtil.equals(cmds[0], "3"))
							&& cmds.length < 2) { // View the user information
					mUserService.getUserInfo(fromUser, resp);
					return;
				}
				if(StrUtil.equals(cmds[0], "unbind")) { // unbind wechat
					mUserService.unbindWechat(cmds, fromUser, resp);
					return;
				}
				
				if(fromUser.getStatus() >= User.MANAGER) { // manager user command area
					if(StrUtil.equals(cmds[0], "ls")
							&& cmds.length >= 2){ // The balance of information query all users
						mUserService.getGlobalBalance(cmds, resp);
						return;
					}
					if((StrUtil.equals(cmds[0], "info")
							|| StrUtil.equals(cmds[0], "i")
							|| StrUtil.equals(cmds[0], "3"))
								&& cmds.length >= 2) { // View the user information
						mUserService.getUserInfo(cmds, resp);
						return;
					}
					if(StrUtil.equals(cmds[0], "4")
							|| StrUtil.equals(cmds[0], "addrecord")
							|| StrUtil.equals(cmds[0], "ar")){
						mRecordService.addRecord(cmds, fromUser, resp);
						return;
					}
					if(StrUtil.equals(cmds[0], "adduser")
							|| StrUtil.equals(cmds[0], "au")){
						mUserService.addUser(cmds, fromUser, resp);
						return;
					}
					if(StrUtil.equals(cmds[0], "freeze")){
						mUserService.freezeUser(cmds, fromUser, resp);
						return;
					}
					if(StrUtil.equals(cmds[0], "unfreeze")){
						mUserService.unfreezeUser(cmds, fromUser, resp);
						return;
					}
					
					if(StrUtil.equals(cmds[0], "adminhelp")
							||StrUtil.equals(cmds[0], "ah")){
						resp.setContent(mStringService.getAdminHelpStr());
						return;
					}
					
					if(fromUser.getStatus() >= User.SUPERMANAGER) { // super manager user command area
						if(StrUtil.equals(cmds[0], "addsu")){
							mUserService.addSuperUser(cmds, fromUser, resp);
							return;
						}
						if(StrUtil.equals(cmds[0], "deletesu")){
							mUserService.deleteSuperUser(cmds, fromUser, resp);
							return;
						}
						if(StrUtil.equals(cmds[0], "lssu")){
							mUserService.getSuperUser(cmds, resp);
							return;
						}
					} // super manager
				} // manager
			} // normal
		}
		
		// other command
		resp.setContent("未识别命令\n" + mStringService.getHelpStr());
		return;
	}
}

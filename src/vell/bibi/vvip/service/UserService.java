package vell.bibi.vvip.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import vell.bibi.vvip.dao.UserDAO;
import vell.bibi.vvip.model.User;
import vell.bibi.vvip.util.StrUtil;

import com.ericxu131.exwechat.model.message.TextMessage;

public class UserService {
	private UserDAO mUserDAO = UserDAO.getInstance();
	private ParseService mParseService = ParseService.getInstance();
	private StringService mStringService = StringService.getInstance();
	private UserService(){}
	private static UserService service = null;
	public static UserService getInstance() {
		if(service == null)
			service = new UserService();
		return service;
	}
	
	public void getBalance(String[] cmds, User user, TextMessage resp) {
		resp.setContent("你当前余额: " + user.getBalance() + "元");
	}
	
	public void bindWechat(String[] cmds, TextMessage req, TextMessage resp){
		if(cmds.length < 2) {
			resp.setContent("请输入账户id！！！\n" + mStringService.getBindHelpStr());
			return;
		}
		
		User fromUser = mUserDAO.getUserById(Integer.parseInt(cmds[1])); // get exist user
		if(fromUser == null) { // not find user;
			resp.setContent("账号未找到！！！\n" + mStringService.getBindHelpStr());
			return;
		}
		
		User tempUser = new User();
		tempUser.setId(fromUser.getId());
		for(int i=2; i<cmds.length; i++){
			mParseService.parseUserInfo(cmds[i], tempUser);
		}
		
		if(tempUser.equals(fromUser)) { 
			if(StrUtil.isEmpty(fromUser.getWechatId())){
				fromUser.setWechatId(req.getFromUserName()); // bind
				mUserDAO.saveUser(fromUser);
				resp.setContent("账号绑定成功，欢迎使用205vip系统\n" + mStringService.getHelpStr());
				return;
			} else {
				resp.setContent("账号以被绑定，请先在已绑定账号上解绑！！！\n" + mStringService.getUnBindHelpStr());
				return;
			}
		} else {
			resp.setContent("账号信息不匹配！！！\n" + mStringService.getBindHelpStr());
			return;
		}
	}
	
	public void getUserInfo(User user, TextMessage resp) {
		resp.setContent(user.toString());
	}
	
	public void unbindWechat(String[] cmds, User fromUser, TextMessage resp){
		if(cmds.length < 2) {
			resp.setContent("请输入账户id！！！\n" + mStringService.getBindHelpStr());
			return;
		}
		
		if(fromUser.getId() == Integer.parseInt(cmds[1])) {
			fromUser.setWechatId("");
			mUserDAO.saveUser(fromUser);
			resp.setContent("解绑成功，感谢使用205vip系统！");
		} else {
			resp.setContent("解绑失败，请确认你的账户id！");
		}
	}
	
	public void getGlobalBalance(String[] cmds, TextMessage resp){
		List<User> users = mUserDAO.getALLUsers();
		if(StrUtil.equals(cmds[1], "-a")) {
			String str = "";
			for(User user : users) {
				str += user.getName() + ": " + user.getBalance() + "\n";
			}
			if(StrUtil.isEmpty(str)) str = "系统还没有任何用户";
			resp.setContent(str);
		} else if(StrUtil.equals(cmds[1], "-A")) {
			double globalBalance = 0;
			for(User user : users) {
				globalBalance += user.getBalance();
			}
			resp.setContent("系统总余额： " + String.valueOf(globalBalance) + "元");
		} else {
			resp.setContent("未识别命令\n" + mStringService.getHelpStr());
		}
	}
	
	public void getUserInfo(String[] cmds, TextMessage resp) {
		List<User> users = findUsersByStr(cmds[1]);
		if(users == null || users.size() == 0) {
			resp.setContent("没有找到相应账户");
		} else {
			String str = "";
			for(User user : users) {
				str += user.toString() + "\n";
			}
			resp.setContent(str);
		}
	}
	
	private List<User> findUsersByStr(String info) {
		List<User> users = null;
		if(!StrUtil.isNumber(info)) { // info is name
			users = mUserDAO.getUsersByName(info);
		} else {
			if(info.length() == 12) { // info is studentId
				users = mUserDAO.getUsersByStudentId(info);
			} else if(info.length() == 4){ // Cell phone number after four
				users = new ArrayList<User>();
				List<User> tempUsers = mUserDAO.getALLUsers();
				for(User user : tempUsers) {
					if(!StrUtil.isEmpty(user.getPhone()) && StrUtil.equals(user.getPhone().substring(7), info)) {
						users.add(user);
					}
				}
			}
		}
		
		return users;
	}
	
	public void addUser(String[] cmds, User operUser, TextMessage resp) {
		User user = new User();
		boolean flag = false;
		for(int i=1; (cmds.length<=4 && i<cmds.length) || (cmds.length>4 && i < 4); i++) {
			flag = mParseService.parseUserInfo(cmds[i], user);
		}
		if(!flag) { 
			resp.setContent("名字里必须出现字符，学号为12位数字，手机号为11位数字。三个信息至少需要填写一个！");
		} else {
			user.setInfo("账户由"+operUser.getName()+"创建！时间：" + new Date());
			mUserDAO.saveUser(user);
			resp.setContent("用户添加成功！\n" + user.toString());
		}
	}
	
	public void freezeUser(String[] cmds, User operUser, TextMessage resp) {
		if(cmds.length < 2) {
			resp.setContent("请输入需要冻结的账户id！");
		} else {
			User user = mUserDAO.getUserById(Integer.parseInt(cmds[1]));
			if(user == null) {
				resp.setContent("账户不存在，请确认账户id！");
				return;
			}
			if(user.getStatus() >= operUser.getStatus()) {
				resp.setContent("你没有权限冻结此用户！");
				return;
			}
			if(user.getStatus() < User.NORMAL) {
				resp.setContent("账户已经被冻结，无需重复操作！");
				return;
			}
			user.setStatus(User.FORZEN);
			user.setInfo("账户已被"+operUser.getName()+"冻结！时间：" + new Date()
					+ (cmds.length >= 3 ? ("\n备注：" + cmds[2]) : ""));
			mUserDAO.saveUser(user);
			resp.setContent("账户冻结成功！\n" + user.toString());
		}
	}
	
	public void unfreezeUser(String[] cmds, User operUser, TextMessage resp) {
		if(cmds.length < 2) {
			resp.setContent("请输入需要恢复的账户id！");
		} else {
			User user = mUserDAO.getUserById(Integer.parseInt(cmds[1]));
			if(user == null) {
				resp.setContent("账户不存在，请确认账户id！");
				return;
			}
			if(user.getStatus() >= operUser.getStatus()) {
				resp.setContent("你没有权限恢复此用户！");
				return;
			}
			if(user.getStatus() >= User.NORMAL) {
				resp.setContent("账户已经在正常使用中，无需重复操作！");
				return;
			}
			
			user.setStatus(User.NORMAL);
			user.setInfo("账户被"+operUser.getName()+"恢复使用！时间：" + new Date()
					+ (cmds.length >= 3 ? ("\n备注：" + cmds[2]) : ""));
			mUserDAO.saveUser(user);
			resp.setContent("账户恢复成功！\n" + user.toString());
		}
	}
	
	public void addSuperUser(String[] cmds, User operUser, TextMessage resp) {
		if(cmds.length < 2) {
			resp.setContent("请输入需要设置管理员的账户id！");
		} else {
			User user = mUserDAO.getUserById(Integer.parseInt(cmds[1]));
			if(user == null) {
				resp.setContent("账户不存在，请确认账户id！");
				return;
			}
			if(user.getStatus() >= operUser.getStatus()) {
				resp.setContent("你没有权限设置此用户！");
				return;
			}
			if(user.getStatus() >= User.MANAGER) {
				resp.setContent("账户已经是管理员，无需重复操作！");
				return;
			}
			
			user.setStatus(User.MANAGER);
			user.setInfo("账户被"+operUser.getName()+"设置为管理员！时间：" + new Date()
					+ (cmds.length >= 3 ? ("\n备注：" + cmds[2]) : ""));
			mUserDAO.saveUser(user);
			resp.setContent("账户设置成功！\n" + user.toString());
		}
	}
	
	public void deleteSuperUser(String[] cmds, User operUser, TextMessage resp) {
		if(cmds.length < 2) {
			resp.setContent("请输入需要取消管理员的账户id！");
		} else {
			User user = mUserDAO.getUserById(Integer.parseInt(cmds[1]));
			if(user == null) {
				resp.setContent("账户不存在，请确认账户id！");
				return;
			}
			if(user.getStatus() >= operUser.getStatus()) {
				resp.setContent("你没有权限设置此用户！");
				return;
			}
			if(user.getStatus() < User.MANAGER) {
				resp.setContent("账户已经不是管理员，无需重复操作！");
				return;
			}
			
			user.setStatus(User.NORMAL);
			user.setInfo("账户被"+operUser.getName()+"取消管理员！时间：" + new Date()
					+ (cmds.length >= 3 ? ("\n备注：" + cmds[2]) : ""));
			mUserDAO.saveUser(user);
			resp.setContent("账户设置成功！\n" + user.toString());
		}
	}
	
	public void getSuperUser(String[] cmds, TextMessage resp) {
		List<User> users = mUserDAO.getUsersGreater(User.MANAGER);
		String str = "";
		for(User user : users) {
			str += user.toString() + "\n";
		}
		if(StrUtil.isEmpty(str)) str = "暂时没有管理员！";
		resp.setContent(str);
	}
}

package vell.bibi.vvip.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import vell.bibi.vvip.model.User;
import vell.bibi.vvip.util.StrUtil;

public class ParseService {
	private ParseService(){}
	private static ParseService service = null;
	public static ParseService getInstance() {
		if(service == null)
			service = new ParseService();
		return service;
	}
	
	public String[] parse(String cmd){
		String regEx=" +";  
		Pattern pat = Pattern.compile(regEx);  
		return pat.split(cmd.trim());
	}
	
	/**
	 * @param info
	 * @param user
	 * @return
	 */
	public boolean parseUserInfo(String userInfo, User user){
		if(null == user || StrUtil.isEmpty(userInfo)) return false;
		String info = userInfo.trim();
		
		if(StrUtil.isNumber(info)){
			if(info.length() == 11) { // phone number
				user.setPhone(info);
			} else if(info.length() == 12) {// student id
				user.setStudentId(info);
			} else 
				return false;
		} else { // name
			user.setName(info);
		}
		return true;
	}
	
	public Date parseDate(String dateStr) {
		if(StrUtil.isEmpty(dateStr)) return null;
		SimpleDateFormat DateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = null;
		try {
			date = DateFormat.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return date;
	}
	
	public static void main(String[] args) {
		User user = new User();
		user.setName("vell001");
		System.out.println(user);
		
		ParseService.getInstance().parseUserInfo("", user);
		System.out.println(user);
	}
}

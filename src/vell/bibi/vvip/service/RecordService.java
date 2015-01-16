package vell.bibi.vvip.service;

import java.util.Date;
import java.util.List;

import vell.bibi.vvip.dao.RecordDAO;
import vell.bibi.vvip.dao.UserDAO;
import vell.bibi.vvip.model.Record;
import vell.bibi.vvip.model.User;
import vell.bibi.vvip.util.StrUtil;

import com.ericxu131.exwechat.model.message.TextMessage;

public class RecordService {
	private ParseService mParseService = ParseService.getInstance();
	private RecordDAO mRecordDAO = RecordDAO.getInstance();
	private UserDAO mUserDAO = UserDAO.getInstance();
	private RecordService(){}
	private static RecordService service = null;
	public static RecordService getInstance() {
		if(service == null)
			service = new RecordService();
		return service;
	}
	
	public void getRecords(String[] cmds, User user, TextMessage resp) {
		Date startDate = (cmds.length >= 2 ? mParseService.parseDate(cmds[1]) : null);
		Date endDate = (cmds.length >= 3 ? mParseService.parseDate(cmds[2]) : null);
		
		List<Record> records = null;
		
		if(startDate != null){
			if(endDate != null) {
				records = mRecordDAO.getRecordsByDate(startDate, endDate);
			} else { // endDate is null
				records = mRecordDAO.getRecordsByDate(startDate);
			}
		} else { // endDate is null
			records = mRecordDAO.getRecords();
		}
		
		String respStr = "";
		for(Record record : records) {
			if(record.getCustomer().equals(user))
				respStr += record.toString() + "\n";
		}
		if(StrUtil.isEmpty(respStr)) respStr = "你暂无消费记录";
		resp.setContent(respStr);
	}
	
	public void addRecord(String[] cmds, User operUser, TextMessage resp){
		if(cmds.length < 3) {
			resp.setContent("记录信息不完整，请输入完整！");
		} else if(!StrUtil.isNumber(cmds[1])) {
			resp.setContent("账户id必须是数字！");
		} else { // add record
			User user = mUserDAO.getUserById(Integer.parseInt(cmds[1]));
			if(user == null) {
				resp.setContent("账户没有找见，请确认账户id是否正确！");
				return;
			}
			Record record = new Record();
			
			double value = 0.0;
			
			try{
				value = Double.parseDouble(cmds[2]);
			} catch (NumberFormatException e) {
				resp.setContent("金额输入有误，无法识别金额数！");
				return;
			}
			// add record
			record.setCustomer(user);
			record.setOperator(operUser);
			if(cmds.length >= 4) record.setInfo(cmds[3]);
			record.setValue(value);
			mRecordDAO.saveRecord(record);
			
			// update user's balance
			user.setBalance(user.getBalance() + value);
			mUserDAO.saveUser(user);
			resp.setContent("记录添加成功！\n" + record.toString());
		}
	}
}

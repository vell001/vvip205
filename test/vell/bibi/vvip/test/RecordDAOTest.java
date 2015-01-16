package vell.bibi.vvip.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import vell.bibi.vvip.dao.RecordDAO;
import vell.bibi.vvip.model.Record;

public class RecordDAOTest {
	public static void main(String[] args) throws ParseException {
//		Record r1 = new Record();
//		r1.setCustomer(UserDAO.getInstance().getUserById(0));
//		r1.setOperator(UserDAO.getInstance().getUserById(1));
//		r1.setValue(2);
		
		
		RecordDAO dao = RecordDAO.getInstance();
//		dao.saveRecord(r1);
		SimpleDateFormat sdf = Record.RecordDateFormat;
		String strDate = "2014-09-28 16:11:56";
		Date date=sdf.parse(strDate);
		System.out.println(date.toString());
		List<Record> list = dao.getRecordsByDate(date);
		
		for(Record r:list){
			System.out.println(r);
		}
	}

}

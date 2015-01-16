package vell.bibi.vvip.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Session;

import vell.bibi.vvip.model.Record;
import vell.bibi.vvip.model.User;
import vell.bibi.vvip.util.HibernateUtil;

public class RecordDAO {
	private RecordDAO(){}
	private static RecordDAO recordDAO = null;
	public static RecordDAO getInstance() {
		if(recordDAO == null)
			recordDAO = new RecordDAO();
		return recordDAO;
	}
	
	public Record getRecordById(int id){
		Session session = HibernateUtil.getSessionFactory().openSession();
		Record record = (Record) session.get(Record.class, id);
		session.close();
		return record;
	}
	
	public List<Record> getRecords(){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Record> recordList = (List<Record>) session.createQuery("from Record record order by record.date").list();
		session.close();
		return recordList;
	}
	
	public List<Record> getRecordsByCustomer(User customer){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Record> recordList = (List<Record>) session.createQuery("from Record record where record.customer.id = :customerId")
				.setString("customerId", String.valueOf(customer.getId())).list();
		session.close();
		return recordList;
	}
	
	public List<Record> getRecordsByDate(Date startDate){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Record> recordList = (List<Record>) session.createQuery("from Record record where record.date >= :startDate order by record.date")
				.setDate("startDate", startDate).list();
		session.close();
		return recordList;
	}
	
	public List<Record> getRecordsByDate(Date startDate, Date endDate){
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<Record> recordList = (List<Record>) session.createQuery("from Record record where record.date >= :startDate and record.date <= :endDate order by record.date")
				.setDate("startDate", startDate)
				.setDate("endDate", endDate).list();
		session.close();
		return recordList;
	}
	
	public void saveRecord(Record record){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		session.saveOrUpdate(record);
		
		session.getTransaction().commit();
		session.close();
	}
}

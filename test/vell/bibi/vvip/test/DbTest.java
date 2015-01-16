package vell.bibi.vvip.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import vell.bibi.vvip.model.Record;
import vell.bibi.vvip.model.User;
import vell.bibi.vvip.util.HibernateUtil;

public class DbTest {

	public static void main(String[] args) {
		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session s = sf.openSession();
		/*User u = new User();
		u.setName("vell001");
		u.setPhone("12345678901");
		u.setStatus(User.SUPERMANAGER);
		s.saveOrUpdate(u);*/
		
		User user = new User();
		Record r = new Record();
		r.setCustomer(user);
		r.setOperator(user);
		
		s.saveOrUpdate(r);
		
		user.setName("vell002");
		
		s.beginTransaction();
		s.saveOrUpdate(user);
		s.getTransaction().commit();
		
		s.close();
		sf.close();
	}

}

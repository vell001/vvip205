package vell.bibi.vvip.dao;

import java.util.List;

import org.hibernate.Session;

import vell.bibi.vvip.model.User;
import vell.bibi.vvip.util.HibernateUtil;

public class UserDAO {
	private UserDAO(){}
	private static UserDAO userDAO = null;
	public static UserDAO getInstance() {
		if(userDAO == null)
			userDAO = new UserDAO();
		return userDAO;
	}
	
	public List<User> getALLUsers() {
		return getUsersGreater(0);
	}
	
	/**
	 * @param status
	 * @return
	 * 返回大于等于status的所有user
	 */
	public List<User> getUsersGreater(int status) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<User> userList = (List<User>) session.createQuery("from User user where user.status >= :status order by user.id")
				.setString("status", String.valueOf(status)).list();
		session.close();
		return userList;
	}
	
	public List<User> getUsersLess(int status) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<User> userList = (List<User>) session.createQuery("from User user where user.status <= :status order by user.id")
				.setString("status", String.valueOf(status)).list();
		session.close();
		return userList;
	}
	
	public User getUserById(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		User user = (User) session.get(User.class, id);
		session.close();
		return user;
	}
	
	public List<User> getUsersByName(String name) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<User> userList = (List<User>) session.createQuery("from User user where user.name = :name")
				.setString("name", name).list();
		session.close();
		return userList;
	}
	
	public List<User> getUsersByPhone(String phone) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<User> userList = (List<User>) session.createQuery("from User user where user.phone = :phone")
				.setString("phone", phone).list();
		session.close();
		return userList;
	}

	public List<User> getUsersByStudentId(String studentId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		List<User> userList = (List<User>) session.createQuery("from User user where user.studentId = :studentId")
				.setString("studentId", studentId).list();
		session.close();
		return userList;
	}
	

	public User getUserByWechatId(String wechatId) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		User user = null;
		List<User> userList = (List<User>) session.createQuery("from User user where user.wechatId = :wechatId")
				.setString("wechatId", wechatId).list();
		session.close();
		if(userList != null && !userList.isEmpty())
			user = userList.get(0);
		return user;
	}
	
	public void saveUser(User user){
		Session session = HibernateUtil.getSessionFactory().openSession();
		session.beginTransaction();
		
		session.saveOrUpdate(user);
		
		session.getTransaction().commit();
		session.close();
	}
}

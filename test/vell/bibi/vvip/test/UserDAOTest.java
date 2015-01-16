package vell.bibi.vvip.test;

import java.util.List;

import vell.bibi.vvip.dao.UserDAO;
import vell.bibi.vvip.model.User;

public class UserDAOTest {
	public static void main(String[] args) {
		UserDAO dao = UserDAO.getInstance();
		List<User> list = dao.getUsersLess(2);
		
		for(User u:list){
			System.out.println(u.getName());
		}
	
	}

}

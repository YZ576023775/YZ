package com.abc.daoimpl.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abc.dao.UserDAO;
import com.abc.entity.User;

public class UserDaoImplTest {

	@Test
	public void testFind() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserDAO userDAO=(UserDAO)ac.getBean("userdaoimpl");
		User user = new User();
		user.setId(1);
		user.setUsername("yz");
		user.setUserpwd("123");
		System.out.println(userDAO.find(user));
	}

/*	@Test
	public void testSave() {
		UserDAO userDAO = new UserDaoImpl();
		User user = new User();
	
		user.setUsername("yq");
		user.setUserpwd("123");
		System.out.println(userDAO.save(user));
	}*/
	@Test
	public void testSave() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
				UserDAO userDAO=(UserDAO)ac.getBean("userdaoimpl");
		
		User user = new User();
	
		user.setUsername("yq");
		user.setUserpwd("123");
		System.out.println(userDAO.save(user));
	}
	

}

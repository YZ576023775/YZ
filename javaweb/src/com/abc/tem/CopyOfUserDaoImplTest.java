package com.abc.tem;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abc.dao.UserDAO;
import com.abc.entity.User;

public class CopyOfUserDaoImplTest {

	@Test
	public void testGetJdbcTemplate() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetJdbcTemplate() {
		fail("Not yet implemented");
	}

	@Test
	public void testFind() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserDAO userDAO=(UserDAO)ac.getBean("userdaoimpl1");
		User user = new User();
		user.setUsername("yz");
		user.setUserpwd("123");
		System.out.println(userDAO.find(user));
	}

	@Test
	public void testSave() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserDAO userDAO=(UserDAO)ac.getBean("userdaoimpl1");
		User user = new User();
		user.setUsername("yz");
		user.setUserpwd("123");
		System.out.println(userDAO.save(user));
		
	}
  @Test
  public void testFindByNameString(){
	  ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		UserDAO userDAO=(UserDAO)ac.getBean("userdaoimpl1");
		System.out.println(userDAO.findByName("yz"));
  }
	
}

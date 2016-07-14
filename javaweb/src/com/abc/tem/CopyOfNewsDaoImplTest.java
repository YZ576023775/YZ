package com.abc.tem;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.abc.dao.NewsDAO;
import com.abc.entity.News;

public class CopyOfNewsDaoImplTest {


	@Test
	public void testFind() {
		News news = null;
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		NewsDAO newsDAO = (NewsDAO)ac.getBean("newsdaoimpl");
		news = newsDAO.find(5);
		System.out.println(news);
	}

	@Test
	public void testFindAll() {
		List<News> list=null;
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		NewsDAO newsDAO = (NewsDAO)ac.getBean("newsdaoimpl");
		list = newsDAO.findAll();
		System.out.println(list);
		}

	@Test
	public void testFindAllInt() {
		List<News> list=null;
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		NewsDAO newsDAO = (NewsDAO)ac.getBean("newsdaoimpl");
		list = newsDAO.findAll(1);
		System.out.println(list);
	}

	@Test
	public void testFindAllIntString() {
		List<News> list=null;
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		NewsDAO newsDAO = (NewsDAO)ac.getBean("newsdaoimpl");
		list = newsDAO.findAll(1, "r");
		System.out.println(list);
	}
	

	@Test
	public void testSave() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		NewsDAO newsDAO = (NewsDAO)ac.getBean("newsdaoimpl");
		News news = new News();
		news.setTitle("adsadf");
		news.setnews_desc("sadasf");
		System.out.println(newsDAO.save(news));
	}

	@Test
	public void testGetCount() {
		int count=0;
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		NewsDAO newsDAO = (NewsDAO)ac.getBean("newsdaoimpl");
		count = newsDAO.getCount();
		System.out.println(count);
		}

	@Test
	public void testGetCountString() {
		int count= 0;
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		NewsDAO newsDAO = (NewsDAO)ac.getBean("newsdaoimpl");
		count = newsDAO.getCount("r");
		System.out.println(count);
	}

	@Test
	public void testDelete() {
		int count = 0;
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		NewsDAO newsDAO = (NewsDAO)ac.getBean("newsdaoimpl");
		count = newsDAO.delete(5);
		System.out.println(count);
	}

	@Test
	public void testUpdate() {
		 int count =0;
		ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
		NewsDAO newsDAO = (NewsDAO)ac.getBean("newsdaoimpl");
		count = newsDAO.update(7, "title", "news_desc");
		System.out.println(count);
	}

}

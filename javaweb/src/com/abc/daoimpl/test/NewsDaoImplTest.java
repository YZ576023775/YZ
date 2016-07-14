package com.abc.daoimpl.test;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.abc.dao.NewsDAO;
import com.abc.daoimpl.NewsDaoImpl;
import com.abc.entity.News;

public class NewsDaoImplTest {

	@Test
	public void testFind() {
		NewsDAO newsDAO = new NewsDaoImpl();
		System.out.println(newsDAO.find(1));
	}

	@Test
	public void testFindAll() {
		List<News> list=null;
		NewsDAO newsDAO = new NewsDaoImpl();
		list = newsDAO.findAll();
//		System.out.println(list);
		Iterator<News> iterator =list.iterator();
		while(iterator.hasNext()){
			System.out.println(iterator.next());
			
		}
		
	}
	@Test
	public void testFindAllInt() {
		List<News> list=null;
		NewsDAO newsDAO = new NewsDaoImpl();
		list = newsDAO.findAll(0);
		for (News news : list) {
			System.out.println(news);
		}	
		}

	@Test
	public void testSave() {
		News news = new News();
		news.setTitle("ttt2");
		news.setnews_desc("ddd2");
		NewsDAO newsDAO = new NewsDaoImpl();
		newsDAO.save(news);
	}
	@Test
	public void testGetCount() {
		NewsDAO newsDAO = new NewsDaoImpl();
		System.out.println(newsDAO.getCount());
	}
	@Test
	public void testGetCountString(){
		NewsDAO newsDAO = new NewsDaoImpl();
		System.out.println(newsDAO.getCount("r"));
		
	}
	@Test
	public void testFindAllIntString() {
		NewsDAO newsDAO = new NewsDaoImpl();
		System.out.println(newsDAO.findAll(1, "r"));
	}
	@Test
	public void testDeleteInt(){
		NewsDAO newsDAO = new NewsDaoImpl();
		System.out.println(newsDAO.delete(2));
	}
	@Test
	public void testUpdate() {
		NewsDAO newsDAO = new NewsDaoImpl();
		System.out.println(newsDAO.update(4, "title", "newc"));
	}
}
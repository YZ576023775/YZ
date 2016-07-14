package com.yz.searchservlet;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class MongoDBJDBCTest {

	@Test
	public void testSelectAll() {
		MongoDBJDBC mongoDBJDBC = new MongoDBJDBC();
		mongoDBJDBC.selectAll();
	}
	@Test
	public void testSelectByKeywords() {
		MongoDBJDBC mongoDBJDBC = new MongoDBJDBC();
		System.out.println(mongoDBJDBC.selectByKeywords("徐佳莹2","", 2));
	}
	@Test
	public void testSelectBySet() {
		MongoDBJDBC mongoDBJDBC = new MongoDBJDBC();
		System.out.println(mongoDBJDBC.selectBySet("徐佳莹2", ""));
	}
	@Test
	public void testSelectBySessionId() {
		MongoDBJDBC mongoDBJDBC = new MongoDBJDBC();
		System.out.println(mongoDBJDBC.selectBySessionId("1111"));
	}
	@Test
	public void testInsert() {
		MongoDBJDBC mongoDBJDBC = new MongoDBJDBC();
		for (int i = 2; i < 3; i++) {
			Set<String> set = new HashSet<String>();
			set.add("林俊杰"+i);
			set.add("周杰伦"+i);
			set.add("徐佳莹"+i);
			mongoDBJDBC.insert(""+i, set);
			
		}
	}
	@Test
	public void testUpdate() {
		MongoDBJDBC mongoDBJDBC = new MongoDBJDBC();
		Set<String> set = new HashSet<String>();
		set.add("林俊杰2");
		set.add("周杰伦2");
		set.add("徐佳莹2");
		mongoDBJDBC.update("2222", set);
	}
	@Test
	public void testDeleteAll() {
		MongoDBJDBC mongoDBJDBC = new MongoDBJDBC();
		mongoDBJDBC.deleteAll();
	}

}

package com.abc.tem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import com.abc.dao.NewsDAO;
import com.abc.dbutils.Dbutils;
import com.abc.entity.News;
import com.sun.xml.internal.ws.wsdl.writer.document.Types;

public class CopyOfNewsDaoImpl implements NewsDAO {
	Connection connection;
	JdbcTemplate jdbcTemplate;
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public News find(int id) {
		
		String sql="select * from news where id="+id;
		News news = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<News>(News.class));
		//News news = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<News>(News.class));
		

		
		return news;
	}

	public List<News> findAll() {
		String sql = "select * from news order by id desc";
		List<News> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<News>(News.class));
		
		return list;
	}
	public List<News> findAll(int currentIndex) {
		String sql = "select * from news order by id desc limit "+currentIndex*10+",10";
		List<News> list = new ArrayList<News>();
		list = jdbcTemplate.query(sql,new BeanPropertyRowMapper<News>(News.class));
	
		return list;
	}
	public List<News> findAll(int currentIndex , String keywords) {
		String sql = "select * from news where news_desc like '%"+keywords+"%' order by id desc limit "+currentIndex*10+",10";
		List<News> list = new ArrayList<News>();
		List<Map<String,Object>> listmap = jdbcTemplate.queryForList(sql);
		for(Map<String,Object> map : listmap){
			News news = new News();
			news.setId((Integer)map.get("id"));
			news.setTitle((String)map.get("title"));
			news.setnews_desc((String)map.get("news_desc"));
			list.add(news);
		}
		
		System.out.println(list);
		return list;
	}

	

	public boolean save(News news) {
		int count=0;
		String sql = "insert into news(title,news_desc) values (?,?)";
		count = jdbcTemplate.update(sql,news.getTitle(),news.getnews_desc());
		if(count !=0){
			System.out.println("保存成功！");
			return true;
		}else{
			System.out.println("保存失败！");
			return false;
		}
		
	}

	public int getCount() {
		int count=0;
		String sql = "select count(*) count from news";
		count = jdbcTemplate.queryForInt(sql);
		
		return count;
	}

	public int getCount(String keywords) {
		int count=0;
		String sql = "select count(*) count from news where news_desc like '%"+keywords+"%'";
			count = jdbcTemplate.queryForInt(sql);
			
		return count;
	}

	public int delete(int id) {
		int count=0;
		String sql = "delete from news where id =?";
		count = jdbcTemplate.update(sql, id);
		return count;
	}

	public int update(int id,String title,String news_desc) {
		int count=0;
		String sql = "UPDATE news SET title=?,news_desc=? WHERE id =?";
		count = jdbcTemplate.update(sql, title,news_desc,id);
		return count;
	}

}

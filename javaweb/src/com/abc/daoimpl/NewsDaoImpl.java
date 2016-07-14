package com.abc.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.abc.dao.NewsDAO;
import com.abc.dbutils.Dbutils;
import com.abc.entity.News;

public class NewsDaoImpl implements NewsDAO {
	Connection connection;
	PreparedStatement ps;
	ResultSet rs;
	public News find(int id) {
		News news = new News();
		String sql = "select * from news where id=?";
		try {
			connection = Dbutils.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			rs= ps.executeQuery();
			while (rs.next()) {
				news.setId(rs.getInt("id"));
				news.setTitle(rs.getString("tittle"));
				news.setnews_desc(rs.getString("news_desc"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Dbutils.close(connection, ps, rs);
		}
		
		return news;
	}

	public List<News> findAll() {
		String sql = "select * from news order by id desc";
		List<News> list = new ArrayList<News>();
		try {
			connection = Dbutils.getConnection();
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News();
				news.setId(rs.getInt("id"));
				news.setTitle(rs.getString("tittle"));
				news.setnews_desc(rs.getString("news_desc"));
				list.add(news);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Dbutils.close(connection, ps, rs);

		}
		return list;
	}
	public List<News> findAll(int currentIndex) {
		String sql = "select * from news order by id desc limit "+currentIndex*10+",10";
		List<News> list = new ArrayList<News>();
		try {
			connection = Dbutils.getConnection();
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News();
				news.setId(rs.getInt("id"));
				news.setTitle(rs.getString("tittle"));
				news.setnews_desc(rs.getString("news_desc"));
				list.add(news);
			}
			System.out.println(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Dbutils.close(connection, ps, rs);
			
		}
		return list;
	}
	public List<News> findAll(int currentIndex , String keywords) {
		String sql = "select * from news where news_desc like '%"+keywords+"%' order by id desc limit "+currentIndex*10+",10";
		List<News> list = new ArrayList<News>();
		try {
			connection = Dbutils.getConnection();
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				News news = new News();
				news.setId(rs.getInt("id"));
				news.setTitle(rs.getString("tittle"));
				news.setnews_desc(rs.getString("news_desc"));
				list.add(news);
			}
			System.out.println(list);
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Dbutils.close(connection, ps, rs);
			
		}
		return list;
	}

	

	public boolean save(News news) {
		int count=0;
		String sql = "insert into news(tittle,news_desc) values (?,?)";
		try {
			connection = Dbutils.getConnection();
			ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, news.getTitle());
			ps.setString(2, news.getnews_desc());
			count = ps.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		try {
			connection = Dbutils.getConnection();
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				count=rs.getInt("count");
				System.out.println("getCount()+++++++++++"+count);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Dbutils.close(connection, ps, rs);
			
		}
		return count;
	}

	public int getCount(String keywords) {
		int count=0;
		String sql = "select count(*) count from news where news_desc like '%"+keywords+"%'";
		try {
			connection = Dbutils.getConnection();
			ps = connection.prepareStatement(sql);
			rs = ps.executeQuery();
			while (rs.next()) {
				count=rs.getInt("count");
				System.out.println("getCount(String keywords)+++++++++++"+count);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Dbutils.close(connection, ps, rs);
			
		}
		return count;
	}

	public int delete(int id) {
		int count=0;
		String sql = "delete from news where id =?";
		try {
			connection = Dbutils.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setInt(1, id);
			count = ps.executeUpdate();
			if(count!=0){
				System.out.println("删除成功！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Dbutils.close(connection, ps, rs);
			
		}
		return count;
	}

	public int update(int id,String title,String news_desc) {
		int count=0;
		String sql = "UPDATE news SET tittle=?,news_desc=? WHERE id =?";
		try {
			connection = Dbutils.getConnection();
			ps = connection.prepareStatement(sql);
			ps.setString(1, title);
			ps.setString(2, news_desc);
			ps.setInt(3, id);
			count = ps.executeUpdate();
			if(count!=0){
				System.out.println("更新成功！");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			Dbutils.close(connection, ps, rs);
			
		}
		return count;
	}

}

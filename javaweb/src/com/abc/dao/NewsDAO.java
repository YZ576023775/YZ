package com.abc.dao;

import java.util.List;

import com.abc.entity.News;

public interface NewsDAO {
	public News find(int id);
	public List<News> findAll();
	public boolean save(News news);
	public int getCount();
	public int getCount(String keywords);
	public List<News> findAll(int currentIndex);
	public List<News> findAll(int currentIndex , String keywords);
	public int delete(int id);
	public int update(int id,String title,String news_desc);
}

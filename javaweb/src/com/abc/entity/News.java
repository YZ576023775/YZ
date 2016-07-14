package com.abc.entity;

public class News {
	int id;
	String title;//���ű���
	String news_desc;//��������
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getnews_desc() {
		return news_desc;
	}
	public void setnews_desc(String news_desc) {
		this.news_desc = news_desc;
	}
	@Override
	public String toString() {
		return "News [news_desc=" + news_desc + ", id=" + id + ", title=" + title + "]";
	}

}

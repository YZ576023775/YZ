package com.abc.entity;

public class User {
	private int id;
	private String username;
	private String userpwd;


	

	// 给三个全局变量定义get set访问器
	public int getId() {
		return id;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", userpwd="
				+ userpwd + "]";
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserpwd() {
		return userpwd;
	}

	public void setUserpwd(String userpwd) {
		this.userpwd = userpwd;
	}

}

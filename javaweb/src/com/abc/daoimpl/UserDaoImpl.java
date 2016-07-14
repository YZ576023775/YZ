package com.abc.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;



import com.abc.dao.UserDAO;
import com.abc.dbutils.Dbutils;
import com.abc.entity.User;
public class UserDaoImpl implements UserDAO {
	private DataSource dataSource;
	private String username;
	private String userpwd;
	


	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
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

	
	private PreparedStatement ps;
	private ResultSet resultSet;
	public boolean find(User user) {
		Connection connection = null;
		try {
		
			connection = dataSource.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//connection = Dbutils.getConnection();
		String sql = "select * from user  where username=? and userpwd =?" ;
		boolean flag=false;
		try {

			ps = connection.prepareStatement(sql);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getUserpwd());
			resultSet = ps.executeQuery();
			while (resultSet.next()) {
				if (resultSet.getString("username").equals(user.getUsername())
						&& resultSet.getString("userpwd").equals(user.getUserpwd())) {
					flag =  true;
				} else {
					flag = false;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Dbutils.close(connection, ps, resultSet);
		}
		System.out.println("查找结果："+flag);
		return flag;

	}

	public boolean save(User user) {
		String sql = "insert into user(username,userpwd) values (?,?)";
		//Connection connection = Dbutils.getConnection();
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		int count=0;
		try {
			ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, user.getUsername());
			ps.setString(2, user.getUserpwd());
			count = ps.executeUpdate();
		
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			Dbutils.close(connection, ps, null);
		}
		if(count!=0){
			System.out.println("保存成功！");
			return true;
		}else{
			System.out.println("保存失败！");
		    return false;
		}
	}

	public boolean findByName(String username) {
		// TODO Auto-generated method stub
		return false;
	}

}

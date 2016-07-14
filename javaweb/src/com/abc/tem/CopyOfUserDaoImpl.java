package com.abc.tem;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;

import com.abc.dao.UserDAO;
import com.abc.entity.User;
public class CopyOfUserDaoImpl implements UserDAO {

	private JdbcTemplate jdbcTemplate;
	
	
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	
	public boolean find(User user) {
		List<Map<String,Object>> list = jdbcTemplate.queryForList("select * from user where username='"+user.getUsername()+"' and userpwd ='"+user.getUserpwd()+"'");
		boolean flag=false;
		if(list.size()>0){
			flag=true;
			System.out.println(list);
		}else{
			flag=false;
		}
		
		return flag;
	}

	public boolean save(User user) {
		boolean flag=false;
		int count = jdbcTemplate.update("insert into user(username,userpwd) values('"+user.getUsername()+"','"+user.getUserpwd()+"')");
		if(count!=0){
			flag=true;
		}else{
			flag=false;
		}
		return flag;
	}

	public boolean findByName(String username) {
	boolean flag = false;
	List<Map<String,Object>> list = jdbcTemplate.queryForList("select * from user where username='"+username+"'");
	if(list.size()>0){
		flag=true;
	}else{
		flag=false;
	}
		
		return flag;
	}
	
	



	
}

package com.abc.dao;

import com.abc.entity.User;

public interface UserDAO {
	public boolean find(User user);
	public boolean save(User user);
	public boolean findByName(String username);
}

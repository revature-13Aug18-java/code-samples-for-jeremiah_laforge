package com.revature.dao;

import java.util.List;

import com.revature.models.RUser;
import com.revature.models.Recipe;

public interface UserDAO {

	public List<RUser> getAllUsers();
	public List<RUser> getAllChefs();
	public List<RUser> getAllNonChefs();
	public RUser getUserByUserName(String userName);
	public int createUser(RUser user);
	public void deleteUser(RUser user);
	public RUser updateUser(RUser user);
	public RUser getUserByUserId(int userId);
	
	
}

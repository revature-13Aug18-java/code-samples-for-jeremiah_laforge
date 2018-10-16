package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.revature.dao.UserDAO;
import com.revature.dao.UserDAOImpl;
import com.revature.models.RUser;

// TODO: Auto-generated Javadoc
/**
 * The Class UserService which acts as the medium between 
 * the controller and the DAO layer of this application.
 */
@Service
public class UserService {
	
	/** ud provides access to DAO */
	private static UserDAO ud = new UserDAOImpl();
	
	/**
	 * Instantiates a new user service.
	 */
	public UserService() {
		super();
	}
	
	/**
	 * Gets the all users.
	 *
	 * @return List of all users
	 */
	public List<RUser> getAllUsers(){
		return ud.getAllUsers();
	}
	
	/**
	 * Returns all users with critical information removed for front end use. 
	 * Prevents access to sensitive info
	 *
	 * @return List of users
	 */
	public List<RUser> getAllUsersExternal(){
		List<RUser> users = ud.getAllUsers();
		List<RUser> sanatizedUsers = new ArrayList<>();
		
		for(RUser u: users) {
			u.setEmail(" ");
			u.setPswd(" ");
			u.setName(" ");
			sanatizedUsers.add(u);
			}
		
		return users;
	}
	
	/**
	 * Gets the all chefs.
	 *
	 * @return all users flagged as chef
	 */
	public List<RUser> getAllChefs(){
		List<RUser> users = ud.getAllChefs();
		List<RUser> sanatizedUsers = new ArrayList<>();
		
		for(RUser u: users) {
			u.setEmail(" ");
			u.setPswd(" ");
			u.setName(" ");
			sanatizedUsers.add(u);
			}
		
		return users;
	}
	
	/**
	 * Gets the all default users.
	 *
	 * @return the all default users
	 */
	public List<RUser> getAllDefaultUsers(){
		List<RUser> users = ud.getAllNonChefs();
		List<RUser> sanatizedUsers = new ArrayList<>();
		
		for(RUser u: users) {
			u.setEmail(" ");
			u.setPswd(" ");
			u.setName(" ");
			sanatizedUsers.add(u);
			}
		
		return users;
	}
	
	/**
	 * Creates the user.
	 *
	 * @param user the user to be put into DB
	 * @return the newly created user
	 */
	public RUser createUser(RUser user) {
		int userId = ud.createUser(user);
		if (userId > 0) {
			return ud.getUserByUserId(userId);
		} else {
			return null;
		}
	}
	
	/**
	 * Gets the user by username.
	 *
	 * @param username the username
	 * @return the user associated with this username
	 */
	public RUser getUserByUserName(String username) {
		return ud.getUserByUserName(username);
	}
	
	/**
	 * Updates User.
	 *
	 * @param user the user
	 * @return the r user
	 */
	public RUser updateUser(RUser user) {
		return ud.updateUser(user);
	}
	
	/**
	 * Deletes user.
	 *
	 * @param user to be deleted
	 */
	public void deleteUser(RUser user) {
		ud.deleteUser(user);
	}
	
	/**
	 * Authenticates that the log in information matches a user in the 
	 * DB
	 *
	 * @param userName for user trying to log in
	 * @param pswd 
	 * @return the user object if log in is successful, otherwise null
	 */
	public RUser authenticateUser(String userName, String pswd) {
		RUser user = ud.getUserByUserName(userName);
		if(user.getPswd().equals(pswd)) {
			return user;
		}
		
		return null;
	}
	
	/**
	 * Gets the user by id.
	 *
	 * @param id 
	 * @return the user associated with the given id
	 */
	public RUser getUserById(int id) {
		return ud.getUserByUserId(id);
	}
}


package com.revature.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.models.RUser;
import com.revature.services.UserService;

/**
 * This class is our controller that handles all request concerning User request and functions.
 * It is a part of the MVC architecture implementation allowing the servlet mapping to be abstracted
 * away from the programmer.
 * @author jeremiah
 *
 */
@RestController
@CrossOrigin
public class UserController {

	/** The log. */
	private static Logger log = Logger.getRootLogger();
	
	/** The user name set. */
	private static Set<String> userNameSet = new HashSet<>();
	
	/** The user serv. */
	private static UserService userServ = new UserService();

	/**
	 * Instantiates a new user controller.
	 */
	public UserController() {
		super();
	}
	/**
	 * This method is for use within the controller only, and provides
	 * an easy, but not scalable, way to ensure usernames are unique.
	 */
	private static void loadUsers() {
		for (RUser u : userServ.getAllUsers()) {
			userNameSet.add(u.getuName());
		}
	}

	/**
	 * This controller method returns all users in the database as a JSON object to
	 * be handled by the view in our component.ts
	 * 
	 * @return array of JSON objects(as a string) containing user objects
	 */
	@RequestMapping(method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value="/getUsers")
	public static List<RUser> getAllUsers() {
		
		return userServ.getAllUsersExternal();
	}
	
	/**
	 * This controller returns to the frontend a user whose username matches the 
	 * path variable passed in the url .
	 *
	 * @param username the username
	 * @return RUser object matching the username
	 */
	@RequestMapping(method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value="/getUsers/{username}")
	public static RUser getUserByUsername(@PathVariable("username") String username) {
		
		return userServ.getUserByUserName(username);
	}
	
	/**
	 * This controller method returns all usernames in the database as a JSON object to
	 * be handled by the view in our component.ts
	 * it is intended to be used to provide a more reactive way to confirm the
	 * availability of a username when a user is creating a profile.
	 * 
	 * @return array containing all usernames in database
	 */
	@RequestMapping(method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value="/getUsernames")
	public static List<RUser> getUsernames() {
		
		return userServ.getAllUsersExternal();
	}
	
	/**
	 * To get all Users with Chef status simply call this method with 
	 * getChefs appended to the url. 
	 * 
	 * @return array of JSON objects(as a string) containing chef user objects
	 */
	@RequestMapping(method=RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE, value="/getChefs")
	public static List<RUser> getAllChefs() {
		
		return userServ.getAllChefs();
	}

	/**
	 * This method will return all default users in the database.
	 * 
	 * @return array of JSON objects(as a string) containing default user objects
	 */
	@RequestMapping(method=RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, value="/getDefaultUsers")
	public static List<RUser> getAllDefaultUsers() {
		
		return userServ.getAllDefaultUsers();
	}

	/**
	 * This method will create a new user in the database using parameters passed to it in
	 * a body of a post request.
	 *
	 * @param name the name
	 * @param userName the user name
	 * @param email the email
	 * @param pswd the pswd
	 * @return a message detailing the results of the create action
	 */
	@RequestMapping(method=RequestMethod.POST, value="/newUser")
	public static String createUser(@RequestParam("name") String name, @RequestParam("userName") String userName,
			@RequestParam("email") String email, @RequestParam("pswd") String pswd) {
		
		loadUsers();
		int setSize = userNameSet.size();
		userNameSet.add(userName);
		if (setSize == userNameSet.size()) {
			return "User Name is already taken; please choose a different User Name.";
		}
		RUser user = new RUser();
		user.setEmail(email);
		user.setIsChef(0);
		user.setName(name);
		user.setuName(userName);
		user.setPswd(pswd);
		
		return userServ.createUser(user).getuName();

	}

	/**
	 * This method will update the user attached to the userId passed in the 
	 * request body using the other parameters passed. 
	 *
	 * @param id the id
	 * @param name the name
	 * @param userName the user name
	 * @param email the email
	 * @param pswd the pswd
	 * @return the updated user object
	 */
	@RequestMapping(method=RequestMethod.POST, value="/updateUser")
	public static RUser updateProfile(@RequestParam("userId") String id, @RequestParam("name") String name, @RequestParam("userName") String userName,
			@RequestParam("email") String email, @RequestParam("pswd") String pswd) {
		/*
		 * Current logic assumes front end will not return any empty values. If user
		 * does not input a field, front end should return user's original profile info
		 * in the request body
		 */
		
		RUser user = userServ.getUserById(Integer.parseInt(id));
		if (user == null) {
			log.info("UserController:updateProfile: User does not exist, or database lookup failed");
			return null;
		}
		user.setEmail(email);
		user.setName(name);
		user.setPswd(pswd);
		return userServ.updateUser(user);
	}
	
	/**
	 * This method will delete the user associated with the username passed to it
	 * in a request body.
	 *
	 * @param userName the user name
	 * @return boolean indicating status of the deletion
	 */
	@RequestMapping(method=RequestMethod.POST, value="/deleteUser")
	public static boolean deleteUser(@RequestParam("userName") String userName) {
		RUser user = userServ.getUserByUserName(userName);
		if(user == null) {
			log.info("User does not exist, or a databse error occured.");
			return false;
		}
		userServ.deleteUser(user);
		return true;
	}
}

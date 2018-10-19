package com.revature.eval.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.revature.models.RUser;
import com.revature.models.Recipe;
import com.revature.services.RecipeService;
import com.revature.services.UserService;

// TODO: Auto-generated Javadoc
/**
 * The Class ServiceTest which is used to be sure a fresh database is set up correctly, and that
 * the DAO methods are functioning properly
 */
public class ServiceTest {

	/** The user serv. */
	private static UserService userServ = new UserService();
	
	/** The recipe serv. */
	private static RecipeService recipeServ = new RecipeService();
	
	/** The log. */
	private static Logger log = Logger.getRootLogger();

	/** The expected exception. */
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	/**
	 * Test get users.
	 */
	/*
	 * Hardcoded test case will only work with newly created database
	 */
	@Test
	public void testGetUsers() {
		List<RUser> expResult = new ArrayList<>();
		/*
		 * will have to manually ensure that the database is created with default values matching these
		 */
		expResult.add(new RUser(100, "theHand", "megan wallaby", "hand@aol.com", "notsecure", 0));
		expResult.add(new RUser(101, "theHost", "cecil", "intern@aol.com", "science", 1));
		expResult.add(new RUser(102, "theScientist", "carlos", "hair@aol.com", "radio", 0));
		expResult.add(new RUser(103, "theMayor", "dana", "realDana@aol.com", "nottherealdana", 1));

		List<RUser> returnedResult = userServ.getAllUsers();
		
		for (int i = 0; i < expResult.size(); i++) {
			assertTrue(returnedResult.get(i).equals(expResult.get(i)));
		}

	}
	
	/**
	 * Test get default users.
	 */
	@Test
	public void testGetDefaultUsers() {
		List<RUser> expResult = new ArrayList<>();
		/*
		 * will have to manually ensure that the passed id's will match these expected objects
		 */
		expResult.add(new RUser(100, "theHand", "megan wallaby", "hand@aol.com", "notsecure", 0));
		expResult.add(new RUser(102, "theScientist", "carlos", "hair@aol.com", "radio", 0));

		List<RUser> returnedResult = userServ.getAllDefaultUsers();
		
		for (int i = 0; i < expResult.size(); i++) {
			assertTrue(returnedResult.get(i).equals(expResult.get(i)));
		}

	}
	
	/**
	 * Test get chef users.
	 */
	@Test
	public void testGetChefUsers() {
		List<RUser> expResult = new ArrayList<>();
		List<RUser> returnedResult = userServ.getAllChefs();
		
		expResult.add(new RUser(101, "theHost", "cecil", "intern@aol.com", "science", 1));
		expResult.add(new RUser(103, "theMayor", "dana", "realDana@aol.com", "nottherealdana", 1));
		
		for (int i = 0; i < expResult.size(); i++) {
			assertTrue(returnedResult.get(i).equals(expResult.get(i)));
		}

	}
	
	/**
	 * Test get user by user name.
	 */
	@Test
	public void testGetUserByUserName() {
		
		RUser user = new RUser("theHost", "cecil", "intern@aol.com", "science", 1);
		
		assertEquals(user,  userServ.getUserByUserName("theHost")); 
		
	}

	/**
	 * Test create user.
	 */
	@Test
	public void testCreateUser() {

		RUser user = new RUser("theFarmer", "John Peters", "farmer@aol.com", "agriculture", 1);
		userServ.createUser(user);
		
		RUser returned = userServ.getUserByUserName("theFarmer");
		
		assertEquals(user, returned);
		
	}

	/**
	 * Test update user.
	 */
	@Test
	public void testUpdateUser() {
		
		RUser user = userServ.getUserByUserName("theHost");
		user.setName("cecil baldwin");
		userServ.updateUser(user);
		
		RUser updated = userServ.getUserByUserName("theHost");
		
		assertEquals(user, updated);
	}

	/**
	 * Test delete U ser.
	 */
	@Test
	public void testDeleteUSer() {
		RUser user = new RUser(100, "theHand", "megan wallaby", "hand@aol.com", "notsecure", 0);
		userServ.deleteUser(user);
		
		assertNull(userServ.getUserByUserName("theHand"));

	}

	/**
	 * Test get recipes by user.
	 */
	@Test
	public void testGetRecipesByUser() {
		List<Recipe> returnedResult = recipeServ.getAllRecipesByUser(new RUser(100, "theHand", "megan wallaby", "hand@aol.com", "notsecure", 0));
		List<Recipe> expResult = new ArrayList<Recipe>();

		expResult.add(new Recipe(1, "waffles", new RUser(100, "theHand", "megan wallaby", "hand@aol.com", "notsecure", 0)));
		expResult.add(new Recipe(2, "pancakes", new RUser(100, "theHand", "megan wallaby", "hand@aol.com", "notsecure", 0)));
		expResult.add(new Recipe(3, "frenchtoast", new RUser(100, "theHand", "megan wallaby", "hand@aol.com", "notsecure", 0)));
		expResult.add(new Recipe(4, "syrup", new RUser(100, "theHand", "megan wallaby", "hand@aol.com", "notsecure", 0)));

		for (int i = 0; i < expResult.size(); i++) {
			assertTrue(returnedResult.get(i).equals(expResult.get(i)));
		}
	}

	/**
	 * Test get all recipe by user id.
	 */
	@Test
	public void testGetAllRecipeByUserId() {
		List<Recipe> returnedResult = recipeServ.getAllRecipesByUserId(100);
		List<Recipe> expResult = new ArrayList<Recipe>();

		expResult.add(new Recipe(1, "waffles", new RUser(100, "theHand", "megan wallaby", "hand@aol.com", "notsecure", 0)));
		expResult.add(new Recipe(2, "pancakes", new RUser(100, "theHand", "megan wallaby", "hand@aol.com", "notsecure", 0)));
		expResult.add(new Recipe(3, "frenchtoast", new RUser(100, "theHand", "megan wallaby", "hand@aol.com", "notsecure", 0)));
		expResult.add(new Recipe(4, "syrup", new RUser(100, "theHand", "megan wallaby", "hand@aol.com", "notsecure", 0)));

		for (int i = 0; i < expResult.size(); i++) {
			assertTrue(returnedResult.get(i).equals(expResult.get(i)));
		}
	}

	/**
	 * Test update recipe.
	 */
	@Test
	public void testUpdateRecipe() {
		Recipe og = new Recipe(3, "frenchtoast", new RUser(100, "theHand", "megan wallaby", "hand@aol.com", "notsecure", 0));
		og.setrecipeJSON("crepes");
		recipeServ.updateRecipe(og);
		assertEquals(og, recipeServ.getRecipeByRecipeId(3));

	}

	/**
	 * Test create recipe.
	 *
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws SQLException the SQL exception
	 */
	@Test
	public void testCreateRecipe() throws IOException, SQLException {
		
		Recipe nrecipe = new Recipe(25, "pizza", new RUser(100, "theHand", "megan wallaby", "hand@aol.com", "notsecure", 0));
		recipeServ.saveRecipe(nrecipe);
		assertEquals(nrecipe, recipeServ.getRecipeByRecipeId(25));
	}

	/**
	 * Test delete recipe.
	 */
	@Test
	public void testDeleteRecipe() {

		Recipe dMe = recipeServ.getRecipeByRecipeId(25);
		recipeServ.deleteRecipe(dMe);
		assertNull(recipeServ.getRecipeByRecipeId(25));
		
	}
}
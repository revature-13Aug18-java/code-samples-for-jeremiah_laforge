package com.revature.dao;

import java.util.List;

import com.revature.models.RUser;
import com.revature.models.Recipe;

public interface RecipeDao {
	
	public int saveRecipe(Recipe recipe);
	public Recipe mergeRecipe(Recipe recipe);
	public void updateRecipe(Recipe recipe);
	public void persistRecipe(Recipe recipe);
	public Recipe getRecipeByRecipeId(int recipeId);
	public Recipe loadRecipe(int recipeId);
	public List<Recipe> getAllRecipes();
	public List<Recipe> getAllRecipesByUser(RUser user);
	public void deleteRecipe(Recipe recipe);
	public List<Recipe> getAllRecipesByUserId(int userId);

}

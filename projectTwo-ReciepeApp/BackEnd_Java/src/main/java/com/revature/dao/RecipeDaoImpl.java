package com.revature.dao;

import java.util.List;


import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.models.RUser;
import com.revature.models.Recipe;
import com.revature.util.HibernateUtil;

public class RecipeDaoImpl implements RecipeDao{
	private static Logger log = Logger.getRootLogger();

	@Override
	public int saveRecipe(Recipe recipe) {
		Session s = HibernateUtil.getSession();
		Transaction tx = s.beginTransaction();
		int recipePK = (int) s.save(recipe);
		tx.commit();
		s.close();
		return recipePK;
	}


	public Recipe mergeRecipe(Recipe recipe) {
		Session s = HibernateUtil.getSession();
		Transaction tx = s.beginTransaction();
		Recipe mergedRecipe = (Recipe) s.merge("RECIPE", recipe);
		tx.commit();
		return mergedRecipe;
	}

	@Override
	public void updateRecipe(Recipe recipe) {
		Session s = HibernateUtil.getSession();
		Transaction tx = s.beginTransaction();
		s.update(recipe);
		tx.commit();
		s.close();
	}

	@Override
	public void persistRecipe(Recipe recipe) {
		Session s = HibernateUtil.getSession();
		Transaction tx = s.beginTransaction();
		s.persist(recipe);
		tx.commit();
		s.close();
	}

	@Override
	public Recipe getRecipeByRecipeId(int recipeId) {
		Session s = HibernateUtil.getSession();
		Recipe recipe = (Recipe) s.get(Recipe.class, recipeId);
		s.close();
		return recipe;
	}

	@Override
	public Recipe loadRecipe(int recipeId) {
		Session s = HibernateUtil.getSession();
		Recipe recipe = (Recipe) s.load(Recipe.class, recipeId);
		s.close();
		return recipe;
		
	}
	
	@Override
	public List<Recipe> getAllRecipesByUser(RUser user) {
		Session s = HibernateUtil.getSession();
		String hql = "FROM Recipe r  WHERE r.user.userId = :id";
		Query query = s.createQuery(hql);
		query.setParameter("id", user.getUserId());
		List<Recipe> recipes = query.list();
		s.close();
		return recipes;
	}

	@Override
	public List<Recipe> getAllRecipes() {
		Session s = HibernateUtil.getSession();
		List<Recipe> recipes = s.createQuery("from Recipe").list();
		s.close();
		return recipes;
	}
	
	@Override
	public void deleteRecipe(Recipe recipe) {
		Session s = HibernateUtil.getSession();
		Transaction tx = s.beginTransaction();
		s.delete(recipe);
		tx.commit();
		s.close();
	}


	@Override
	public List<Recipe> getAllRecipesByUserId(int userId) {
		Session s = HibernateUtil.getSession();
//		Criteria cr = s.createCriteria(Recipe.class);
		String hql = "FROM Recipe r  WHERE r.user.userId = :id";
		Query query = s.createQuery(hql);
		query.setParameter("id", userId);
//		cr.add(Restrictions.eq("user", user.getUserId()));
		List<Recipe> recipes = query.list();
		s.close();
		return recipes;
	}
}

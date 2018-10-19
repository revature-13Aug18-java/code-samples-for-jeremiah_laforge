package com.revature.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.revature.models.RUser;
import com.revature.util.HibernateUtil;

/**
 * The Class UserDAOImpl which provides the implimentation of CRUD access to our database
 */
public class UserDAOImpl implements UserDAO {

	/** The log. */
	private static Logger log = Logger.getRootLogger();

	
	@Override
	public List<RUser> getAllUsers() {
		Session s = HibernateUtil.getSession();
		List<RUser> users = s.createQuery("from RUser").list();
		s.close();
		return users;
	}


	@Override
	public List<RUser> getAllChefs() {
		Session s = HibernateUtil.getSession();
		Criteria cr = s.createCriteria(RUser.class);
		cr.add(Restrictions.eq("isChef", 1));
		List<RUser> users = cr.list();
		s.close();
		return users;
	}


	@Override
	public List<RUser> getAllNonChefs() {
		Session s = HibernateUtil.getSession();
		Criteria cr = s.createCriteria(RUser.class);
		cr.add(Restrictions.eq("isChef", 0));
		List<RUser> users = cr.list();
		s.close();
		return users;
	}


	@Override
	public RUser getUserByUserName(String userName) {
		Session s = HibernateUtil.getSession();
		Criteria cr = s.createCriteria(RUser.class);
		cr.add(Restrictions.eq("uName", userName));
		if (!cr.list().isEmpty()) {
			RUser user = (RUser) cr.list().get(0);
			s.close();
			return user;
		}
		s.close();
		return null;
	}
	
	
	@Override
	public RUser getUserByUserId(int userId) {
		Session s = HibernateUtil.getSession();
		Criteria cr = s.createCriteria(RUser.class);
		cr.add(Restrictions.eq("userId", userId));
		if (!cr.list().isEmpty()) {
			RUser user = (RUser) cr.list().get(0);
			s.close();
			return user;
		}
		s.close();
		return null;
	}


	@Override
	public int createUser(RUser user) {
		Session s = HibernateUtil.getSession();
		Transaction tx = s.beginTransaction();
		int custId = (Integer) s.save(user);
		tx.commit();
		s.close();
		return custId;
	}


	@Override
	public void deleteUser(RUser user) {
		Session s = HibernateUtil.getSession();
		Transaction tx = s.beginTransaction();
		s.delete(user);
		tx.commit();
		s.close();

	}


	@Override
	public RUser updateUser(RUser user) {
		Session s = HibernateUtil.getSession();
		Transaction tx = s.beginTransaction();
		RUser rUser = (RUser) s.merge(user);
		if (rUser != null) {
			s.close();
			return rUser;
		}
		tx.commit();
		s.close();
		return null;
	}

}

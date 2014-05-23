package com.ibus.mediabeaver.core.data;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.CriteriaSpecification;

import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.Persistable;
import com.ibus.mediabeaver.core.entity.RegExSelector;

public abstract class Repository 
{
	public static List<MediaConfig>  getAllMediaConfig() 
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		
		Criteria criteria = s.createCriteria(MediaConfig.class)
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			
		@SuppressWarnings("unchecked")
		List<MediaConfig> results = criteria.list();
		
		return results;
	}

	
	public static List<RegExSelector>  getAllMovieRegEx() 
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		
		Criteria criteria = s.createCriteria(RegExSelector.class)
				.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
			
		@SuppressWarnings("unchecked")
		List<RegExSelector> results = criteria.list();
		
		return results;
	}

	
	public static <T extends Persistable> String updateEntity(T obj)
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.update(obj);
		return obj.getId();
	}
	
	public static <T extends Persistable> String mergeEntity(T obj)
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.merge(obj);
		return obj.getId();
	}
	
	
	public static <T extends Persistable> String saveEntity(T item) 
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(item);
		return item.getId();
	}
	
	
	public static <T extends Persistable> T getEntity(Class<T> cls, String id) 
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		@SuppressWarnings("unchecked")
		T c = (T)s.get(cls, id);
		return c;
	}
	
	public static <T> T getInTransaction(QueryTransactable<T> transactable)
	{
		T result;
		Transaction tx = null; 
		try
		{
			Session s = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = s.beginTransaction();

			result = transactable.run();
			
			tx.commit();
		}
		catch(RuntimeException e)
		{
			tx.rollback();
			throw e;
		}	
		
		return result;
	}

	
}



















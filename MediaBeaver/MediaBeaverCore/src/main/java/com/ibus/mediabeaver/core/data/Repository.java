package com.ibus.mediabeaver.core.data;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.ibus.mediabeaver.core.entity.Persistable;
import com.ibus.mediabeaver.core.entity.MediaTransformConfig;
import com.ibus.mediabeaver.core.entity.MovieRegEx;

public class Repository 
{
	public static List<MediaTransformConfig>  getAllMediaTransformConfig() 
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		
		Query query = s.createQuery("from Media_Transform_Config");
		List<MediaTransformConfig> results = (List<MediaTransformConfig>)query.list();
		
		return results;
	}

	
	public static List<MovieRegEx>  getAllMovieRegEx() 
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		
		Query query = s.createQuery("from Movie_Reg_Ex");
		List<MovieRegEx> results = (List<MovieRegEx>)query.list();
		
		return results;
	}

	
	public static <T extends Persistable> int updateEntity(T obj)
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.update(obj);
		return obj.getId();
	}
	
	
	public static <T extends Persistable> int addEntity(T item) 
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(item);
		return item.getId();
	}
	
	
	public static <T extends Persistable> T getEntity(Class cls, int id) 
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

	
	/*public static <T> T getInTransaction(Transactable<T> transactable)
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
	}	*/
	
	
}



















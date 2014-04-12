package com.ibus.mediaBeaverCore.data;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.ibus.mediaBeaverCore.entity.Entity;
import com.ibus.mediaBeaverCore.entity.MediaTransformConfig;
import com.ibus.mediaBeaverCore.entity.MovieRegEx;

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

	
	public static <T extends Entity> int updateEntity(T obj)
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.update(obj);
		return obj.getId();
	}
	
	
	public static <T extends Entity> int addEntity(T item) 
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(item);
		return item.getId();
	}
	
	
	public static <T extends Entity> T getEntity(Class cls, int id) 
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		@SuppressWarnings("unchecked")
		T c = (T)s.get(cls, id);
		return c;
	}
	
	
}



















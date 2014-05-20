package com.ibus.mediabeaver.core.test;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibus.mediabeaver.core.data.HibernateUtil;
import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.ConfigVariable;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.core.entity.RegExVariable;
import com.ibus.mediabeaver.core.entity.TransformAction;
import com.ibus.mediabeaver.core.util.TestHelper;

public class RepositoryTests
{
	@BeforeClass
	public static void initialiseClass()
	{
	}

	@Before
	public void beforeTest()
	{
		// start with a fresh schema
		HibernateUtil.createSchema();
	}

	public void StartTransaction()
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.beginTransaction();
	}

	public void EndTransaction()
	{
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.getTransaction().commit();
	}
	
	
	@Test
	public void addMediaconfigTest()
	{
		StartTransaction();
		
		MediaConfig c = TestHelper.getMediaConfigFullGraph();
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(c);
		String id = c.getId();
		
		EndTransaction();
		
		
		StartTransaction();
		
		MediaConfig c2 = Repository.getEntity(MediaConfig.class, id);
		TestHelper.mediaConfigsFullGraphEqual(c, c2);
		
		//assert(true);
		
		EndTransaction();
		
		
		
	}
	
	
	

}

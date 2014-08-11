package com.ibus.mediabeaver.core.test;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.ibus.mediabeaver.core.data.HibernateUtil;
import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.MediaConfig;


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
	
	
	/*@Test
	public void refferenceTest()
	{
		StartTransaction();
		
		MediaConfig c = TestHelper.getMediaConfigFullGraph();
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(c);
		String id = c.getId();
		
		EndTransaction();
		
		
		StartTransaction();
		
		MediaConfig c2 = Repository.getEntity(MediaConfig.class, id);
		
		RegExSelector res = c2.getRegExSelectors().iterator().next();
		res.getVariables().iterator().next().getConfigVariable().setValue("Changed xxx");
		
		boolean success = false;
		for(ConfigVariable cv : c2.getConfigVariables())
		{
			if(cv.getValue().equals("Changed xxx"))
				success = true;
		}
		
		assert(success);
		
		EndTransaction();
	}
	*/
	
	
	
	
	/*@Test
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
	
	
	@Test
	public void updateMediaconfigTest()
	{
		//add mc
		StartTransaction();
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		MediaConfig c = TestHelper.getMediaConfigFullGraph();
		s.save(c);
		
		EndTransaction();
		
		
		//change c in another transaction and update
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		c.setDescription("adf");
		s.update(c);
		String id = c.getId();
		
		EndTransaction();
		
		
		//check update
		StartTransaction();
		
		MediaConfig c2 = Repository.getEntity(MediaConfig.class, id);
		TestHelper.mediaConfigsFullGraphEqual(c, c2);
		assert(c2.getDescription().equals("adf"));
		
		EndTransaction();
		
		
		
	}
	*/
	
	
	

}













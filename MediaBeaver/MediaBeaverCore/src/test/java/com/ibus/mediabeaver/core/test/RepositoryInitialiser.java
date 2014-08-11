package com.ibus.mediabeaver.core.test;

import javax.validation.constraints.AssertTrue;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.ibus.mediabeaver.core.data.DataInitialiser;
import com.ibus.mediabeaver.core.data.HibernateUtil;
import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.MediaConfig;

public class RepositoryInitialiser
{
	@BeforeClass
	public static void initialiseClass()
	{
		HibernateUtil.createSchema();
	}

	@Before
	public void beforeTest()
	{
	}

	
	

	@Test
	public void initialiseDatabase()
	{
		Transaction tx = null; 
		try
		{
			Session s = HibernateUtil.getSessionFactory().getCurrentSession();
			tx = s.beginTransaction();

			DataInitialiser.addDefaultConfigs();
			
			tx.commit();
		}
		catch(RuntimeException e)
		{
			tx.rollback();
			assert(false);
		}

		assert(true);
	}
	
	
	
	
	
	

}













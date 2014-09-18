package com.ibus.mediabeaver.core.integrationtest;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibus.mediabeaver.core.data.DataInitialiser;
import com.ibus.mediabeaver.core.data.HibernateUtil;

public class RepositoryInitialiserIT
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













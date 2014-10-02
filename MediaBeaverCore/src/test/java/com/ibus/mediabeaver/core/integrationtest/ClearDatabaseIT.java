package com.ibus.mediabeaver.core.integrationtest;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibus.mediabeaver.core.data.DataInitialiser;
import com.ibus.mediabeaver.core.data.HibernateUtil;

public class ClearDatabaseIT
{


	@Test
	public void clearDatabase()
	{
		
		HibernateUtil.createSchema();
		assert(true);
	}
	
	
	
	
	
	

}













package com.ibus.mediabeaver.core.integrationtest;

import org.junit.Test;

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













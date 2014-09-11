package com.ibus.mediabeaver.core.test;

import static org.junit.Assert.*;

import org.hibernate.Session;
import org.junit.Test;

import com.ibus.mediabeaver.core.data.HibernateUtil;
import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.Configuration;

public class ConfigurationTests
{
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
	public void hasVideoExtensionTest()
	{
		StartTransaction();
		
		Configuration config = Repository.getFirstEntity(Configuration.class);
		boolean hasExtension = config.isVideoExtension("avi");
		assertTrue(hasExtension);
		
		hasExtension = config.isVideoExtension("xxx");
		assertTrue(!hasExtension);
		
		EndTransaction();
	}
}

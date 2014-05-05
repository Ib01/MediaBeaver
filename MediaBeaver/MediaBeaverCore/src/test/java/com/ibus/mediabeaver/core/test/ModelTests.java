package com.ibus.mediabeaver.core.test;

import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibus.mediabeaver.core.data.HibernateUtil;
import com.ibus.mediabeaver.core.entity.ConfigVariable;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.core.entity.RegExVariable;
import com.ibus.mediabeaver.core.entity.TransformAction;
import com.ibus.mediabeaver.core.util.TestHelper;

public class ModelTests
{

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
	public void mediaConfigCascadeDeleteTest()
	{
		StartTransaction();
		
		/*Add MediaConfig */
		MediaConfig c1 = TestHelper.getMediaConfig();
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(c1);
		String id = c1.getId();
		
		EndTransaction();
		
		
		/*delete MediaConfig*/
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		MediaConfig c2 = (MediaConfig) s.get(MediaConfig.class, id);
		RegExSelector res = c2.getRegExSelectors().iterator().next();
		String resId = res.getId();
		s.delete(c2);
		
		EndTransaction();
		
		
		/*check that RegExSelector has been deleted also */
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		RegExSelector res2 = (RegExSelector) s.get(RegExSelector.class, resId);
		
		assertTrue(res2 == null);
		
		EndTransaction();
		
	}
	
	
	@Test
	public void configVariableCascadeDeleteTest()
	{
		StartTransaction();
		
		/*Add MediaConfig */
		MediaConfig c1 = TestHelper.getMediaConfig();
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(c1);
		String id = c1.getId();
		
		EndTransaction();
		
		
		/*remove all config vars*/
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		MediaConfig c2 = (MediaConfig) s.get(MediaConfig.class, id);
		c2.getConfigVariables().clear();
		
		RegExSelector res1 = c2.getRegExSelectors().iterator().next();
		RegExVariable rev1 = res1.getVariables().iterator().next();
		rev1.setConfigVariable(null);
		
		s.save(c2);
		
		EndTransaction();
		
		
		/*check that RegExVariable still exists and has null for configVariable*/
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		MediaConfig c3 = (MediaConfig) s.get(MediaConfig.class, id);
		
		RegExSelector res = c3.getRegExSelectors().iterator().next();
		RegExVariable rev = res.getVariables().iterator().next();
		
		assertTrue(rev.getConfigVariable() == null);
		
		EndTransaction();
		
	}
	
	
	
	@Test
	public void addMediaconfigTest()
	{
		StartTransaction();
		
		MediaConfig c1 = TestHelper.getMediaConfig();
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(c1);
		String id = c1.getId();
		
		EndTransaction();
		
		
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		MediaConfig c2 = (MediaConfig) s.get(MediaConfig.class, id);
		
		TestHelper.mediaConfigsEqual(c1, c2);
		
		EndTransaction();
		
	}
	
	@Test
	public void updateMediaconfigTest()
	{
		/*save a config*/
		StartTransaction();
		
		MediaConfig c1 = TestHelper.getMediaConfig();
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(c1);
		String id = c1.getId();
		
		EndTransaction();
		
		
		/*update config*/
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		MediaConfig c2 = (MediaConfig) s.get(MediaConfig.class, id);
		c2.setDescription("changed description");
		s.save(c2);
		
		EndTransaction();
		
		
		/*validate updated config*/
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		MediaConfig c3 = (MediaConfig) s.get(MediaConfig.class, id);
		
		TestHelper.mediaConfigsEqual(c2, c3);
		
		EndTransaction();
		
		
	}

}

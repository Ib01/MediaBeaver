package com.ibus.mediabeaver.core.integrationtest;

import org.hibernate.Session;
import org.junit.Before;

import com.ibus.mediabeaver.core.data.HibernateUtil;

public class ModelIT
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
	
	
	
	/*
	@Test
	public void mediaConfigCascadeDeleteTest()
	{
		StartTransaction();
		
		Add MediaConfig 
		MediaConfig c1 = TestHelper.getMediaConfigFullGraph();
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(c1);
		String id = c1.getId();
		
		EndTransaction();
		
		
		delete MediaConfig
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		MediaConfig c2 = (MediaConfig) s.get(MediaConfig.class, id);
		
		//String osfmId = c2.getOpenSubtitlesFieldMaps().iterator().next().getId();
		String vcId = c2.getConfigVariables().iterator().next().getId();
		RegExSelector res = c2.getRegExSelectors().iterator().next();
		String resId = res.getId();
		String revId = res.getVariables().iterator().next().getId();
		
		s.delete(c2);
		
		EndTransaction();
		
		
		check that RegExSelector has been deleted also 
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		
		//OpenSubtitlesFieldMap a = (OpenSubtitlesFieldMap) s.get(OpenSubtitlesFieldMap.class, osfmId);
		ConfigVariable b = (ConfigVariable) s.get(ConfigVariable.class, vcId);
		RegExSelector c = (RegExSelector) s.get(RegExSelector.class, resId);
		RegExVariableSetter d = (RegExVariableSetter) s.get(RegExVariableSetter.class, revId);
		MediaConfig e = (MediaConfig) s.get(MediaConfig.class, id);
		
		//assertTrue(a == null);
		assertTrue(b == null);
		assertTrue(c == null);
		assertTrue(d == null);
		assertTrue(e == null);
		
		EndTransaction();
		
	}
	
	
	@Test
	public void configVariableCascadeDeleteTest()
	{
		StartTransaction();
		
		Add MediaConfig 
		MediaConfig c1 = TestHelper.getMediaConfigFullGraph();
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(c1);
		String id = c1.getId();
		
		EndTransaction();
		
		
		remove all config vars
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		MediaConfig c2 = (MediaConfig) s.get(MediaConfig.class, id);
		
		ConfigVariable cv1 = c2.getConfigVariables().iterator().next();
		RegExVariableSetter rev1 = cv1.getRegExVariables().iterator().next();
		cv1.removeRegExVariable(rev1);
		
		s.save(c2);
		
		EndTransaction();
		
		
		check that RegExVariable still exists and has null for configVariable
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		MediaConfig c3 = (MediaConfig) s.get(MediaConfig.class, id);
		
		RegExSelector res = c3.getRegExSelectors().iterator().next();
		RegExVariableSetter rev = res.getVariables().iterator().next();
		
		assertTrue(rev.getConfigVariable() == null);
		assertTrue(c3.getConfigVariables().size() == 1);
		
		EndTransaction();
		
	}
	
	@Test
	public void configVariableCascadeUpdate1Test()
	{
		StartTransaction();
		
		Add MediaConfig 
		MediaConfig c1 = TestHelper.getMediaConfigFullGraph();
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(c1);
		String id = c1.getId();
		
		EndTransaction();
		
		
		remove all config vars
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		MediaConfig c2 = (MediaConfig) s.get(MediaConfig.class, id);
		
		ConfigVariable cv1 = c2.getConfigVariables().iterator().next();
		RegExVariableSetter rev1 = cv1.getRegExVariables().iterator().next();
		rev1.setReplaceExpression("configVariableCascadeUpdateTest");
		
		//UPDATE THROUGH ConfigVariable
		s.save(cv1);
		
		EndTransaction();
		
		
		check that RegExVariable still exists and has null for configVariable
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		MediaConfig c3 = (MediaConfig) s.get(MediaConfig.class, id);
		
		ConfigVariable cv2 = c2.getConfigVariables().iterator().next();
		RegExVariableSetter rev3 = cv1.getRegExVariables().iterator().next();
		
		assertTrue(rev3.getReplaceExpression().equals("configVariableCascadeUpdateTest"));
		
		EndTransaction();
		
	}
	
	@Test
	public void configVariableCascadeUpdate2Test()
	{
		StartTransaction();
		
		Add MediaConfig 
		MediaConfig c1 = TestHelper.getMediaConfigFullGraph();
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(c1);
		String id = c1.getId();
		
		EndTransaction();
		
		
		remove all config vars
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		MediaConfig c2 = (MediaConfig) s.get(MediaConfig.class, id);
		
		ConfigVariable cv1 = c2.getConfigVariables().iterator().next();
		RegExVariableSetter rev1 = cv1.getRegExVariables().iterator().next();
		rev1.setReplaceExpression("configVariableCascadeUpdateTest");
		
		//UPDATE THROUGH MediaConfig
		s.save(c2);
		
		EndTransaction();
		
		
		check that RegExVariable still exists and has null for configVariable
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		MediaConfig c3 = (MediaConfig) s.get(MediaConfig.class, id);
		
		ConfigVariable cv2 = c2.getConfigVariables().iterator().next();
		RegExVariableSetter rev3 = cv1.getRegExVariables().iterator().next();
		
		assertTrue(rev3.getReplaceExpression().equals("configVariableCascadeUpdateTest"));
		
		EndTransaction();
		
	}*/
	
	
	
	/*@Test
	public void regenerateSchema()
	{
		assertTrue(true);
	}*/
	
	
	/*@Test
	public void addMediaconfigTest()
	{
		StartTransaction();
		
		MediaConfig c1 = TestHelper.getMediaConfigFullGraph();
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(c1);
		String id = c1.getId();
		
		EndTransaction();
		
		
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		MediaConfig c2 = (MediaConfig) s.get(MediaConfig.class, id);
		
		TestHelper.mediaConfigsFullGraphEqual(c1, c2);
		
		EndTransaction();
		
	}*/
	
	/*@Test
	public void updateMediaconfigTest()
	{
		save a config
		StartTransaction();
		
		MediaConfig c1 = TestHelper.getMediaConfigFullGraph();
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(c1);
		String id = c1.getId();
		
		EndTransaction();
		
		
		update config
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		MediaConfig c2 = (MediaConfig) s.get(MediaConfig.class, id);
		c2.setDescription("changed description");
		s.save(c2);
		
		EndTransaction();
		
		
		validate updated config
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		MediaConfig c3 = (MediaConfig) s.get(MediaConfig.class, id);
		
		TestHelper.mediaConfigsFullGraphEqual(c2, c3);
		
		EndTransaction();
		
		
	}*/

}

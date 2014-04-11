package com.ibus.mediaBeaverServer.test;


import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibus.mediaBeaverServer.data.HibernateRequestInterceptor;
import com.ibus.mediaBeaverServer.data.HibernateUtil;
import com.ibus.mediaBeaverServer.data.Repository;
import com.ibus.mediaBeaverServer.entity.MediaTransformConfig;
import com.ibus.mediaBeaverServer.entity.MovieRegEx;
import com.ibus.mediaBeaverServer.entity.RenamingService;



public class RepositoryTest 
{
	@BeforeClass 
	public static void initialiseClass()
	{
	}
	
	@Before
	public void InitialiseTest()
	{
		//start with a fresh schema
		HibernateUtil.createSchema();
	}
	
	
	
	@Test
	public void addMediaTransformConfigTest() throws Exception
	{
		//add entity
		int cfgId = addMediaTransformConfig();
		
		//check it was addeed
		HibernateRequestInterceptor inteceptor = new HibernateRequestInterceptor();
		inteceptor.preHandle(null, null, null);
		
		MediaTransformConfig cfg = Repository.getEntity(MediaTransformConfig.class, cfgId);
		assertTrue(cfg != null);
		
		inteceptor.postHandle(null, null, null, null);
	}
	
	@Test
	public void getAllMediaTransformConfigTest() throws Exception
	{
		//add entity
		addMediaTransformConfig();
		
		//check we can get it with get all method 
		HibernateRequestInterceptor inteceptor = new HibernateRequestInterceptor();
		inteceptor.preHandle(null, null, null);
		
		List<MediaTransformConfig> configs = Repository.getAllMediaTransformConfig();
		assertTrue(configs.size() > 0);
		validateMediaTransformConfig(configs.get(0));
		
		inteceptor.postHandle(null, null, null, null);
	}
	
	
	@Test
	public void addMovieRegExTest() throws Exception
	{
		//add entity
		int rexId = addMovieRegEx();
		
		//check entity was added
		HibernateRequestInterceptor inteceptor = new HibernateRequestInterceptor();
		inteceptor.preHandle(null, null, null);
		
		MovieRegEx rex = Repository.getEntity(MovieRegEx.class, rexId);
		assertTrue(rex != null);		
		validateMovieRegEx(rex);
		
		inteceptor.postHandle(null, null, null, null);
	}
	
	/*test case handled above
	 * @Test
	public void getMovieRegExTest() throws Exception{}*/
	
	
	@Test
	public void updateMovieRegExTest() throws Exception
	{
		//add entity
		int id = addMovieRegEx();
		
		//get the item out and update it
		HibernateRequestInterceptor inteceptor = new HibernateRequestInterceptor();
		inteceptor.preHandle(null, null, null);
		
		MovieRegEx rex = Repository.getEntity(MovieRegEx.class, id);
		rex.setExpression("updateMovieRegExTest");
		Repository.updateEntity(rex);
		
		inteceptor.postHandle(null, null, null, null);
		
		
		//get the item out and check its updated value
		inteceptor = new HibernateRequestInterceptor();
		inteceptor.preHandle(null, null, null);
		
		MovieRegEx rex2 = Repository.getEntity(MovieRegEx.class, id);
		assertTrue(rex2.getExpression().equals("updateMovieRegExTest"));
		
		inteceptor.postHandle(null, null, null, null);
	}
	
	
	
	@Test
	public void addMovieRegExThroughParentTest() throws Exception
	{
		//add entity and child
		int cfgId = addMediaTransformConfig();
	
		//check that child was added as well as parent
		HibernateRequestInterceptor inteceptor = new HibernateRequestInterceptor();
		inteceptor.preHandle(null, null, null);
		
		MediaTransformConfig config = Repository.getEntity(MediaTransformConfig.class, cfgId);
		assertTrue(config.getSelectExpressions().size() > 0);
		
		inteceptor.postHandle(null, null, null, null);
	}
	

	@Test
	public void removeMovieRegExThroughParentTest() throws Exception
	{
		//add config
		int cfgId = addMediaTransformConfig();
	
		//remove entity form config
		HibernateRequestInterceptor inteceptor = new HibernateRequestInterceptor();
		inteceptor.preHandle(null, null, null);
		
		MediaTransformConfig config = Repository.getEntity(MediaTransformConfig.class, cfgId);
		int rexId = config.getSelectExpressions().get(0).getId();
		config.removeSelectExpression(config.getSelectExpressions().get(0));
		Repository.updateEntity(config);
		
		inteceptor.postHandle(null, null, null, null);
		
		
		//check that regex is removed from config and is not in db 
		inteceptor = new HibernateRequestInterceptor();
		inteceptor.preHandle(null, null, null);
		
		config = Repository.getEntity(MediaTransformConfig.class,cfgId);
		assertTrue(config.getSelectExpressions().size() == 0);
		MovieRegEx rex2 = Repository.getEntity(MovieRegEx.class, rexId);
		assertTrue(rex2 == null);
		
		inteceptor.postHandle(null, null, null, null);
	}
	
	
	@Test
	public void updateMovieRegExThroughParentTest() throws Exception
	{
		//add config
		int cfgId = addMediaTransformConfig();
	
		//update entity through parent
		HibernateRequestInterceptor inteceptor = new HibernateRequestInterceptor();
		inteceptor.preHandle(null, null, null);
		
		MediaTransformConfig config = Repository.getEntity(MediaTransformConfig.class, cfgId);
		MovieRegEx rex = config.getSelectExpressions().get(0);
		rex.setExpression("updateMovieRegExThroughParentTest");
		Repository.updateEntity(config);
		
		inteceptor.postHandle(null, null, null, null);
		
		//check that rex has changed
		inteceptor = new HibernateRequestInterceptor();
		inteceptor.preHandle(null, null, null);
		
		config = Repository.getEntity(MediaTransformConfig.class, cfgId);
		rex = config.getSelectExpressions().get(0);
		
		assertTrue(rex.getExpression().equals("updateMovieRegExThroughParentTest"));
		
		inteceptor.postHandle(null, null, null, null);
		
	}
	
	
	
	
	public int addMovieRegEx() throws Exception
	{
		HibernateRequestInterceptor inteceptor = new HibernateRequestInterceptor();
		inteceptor.preHandle(null, null, null);
		int cfgId = Repository.addEntity(getMovieRegEx());
		inteceptor.postHandle(null, null, null, null);
		
		return cfgId;
	}
	
	public MovieRegEx getMovieRegEx()
	{
		MovieRegEx re = new MovieRegEx();
		re.setExpression("1");
		re.getNameParser().setAssembledItem("2");
		re.getNameParser().setRecursiveRegEx("3");
		re.getNameParser().setRemoveCharacters("4");
		re.getYearParser().setAssembledItem("5");
		re.getYearParser().setRecursiveRegEx("6");
		re.getYearParser().setRemoveCharacters("7");
		re.setTestFileName("8");
		re.setGeneratedName("9");
		re.setGeneratedYear("10");
		
		return re;
	}
	
	
	public void validateMovieRegEx(MovieRegEx re )
	{
		assertTrue(re.getExpression().equals("1"));
		assertTrue(re.getNameParser().getAssembledItem().equals("2"));
		assertTrue(re.getNameParser().getRecursiveRegEx().equals("3"));
		assertTrue(re.getNameParser().getRemoveCharacters().equals("4"));
		assertTrue(re.getYearParser().getAssembledItem().equals("5"));
		assertTrue(re.getYearParser().getRecursiveRegEx().equals("6"));
		assertTrue(re.getYearParser().getRemoveCharacters().equals("7"));
		assertTrue(re.getTestFileName().equals("8"));
		assertTrue(re.getGeneratedName().equals("9"));
		assertTrue(re.getGeneratedYear().equals("10"));
		
	}
		
	public int addMediaTransformConfig() throws Exception
	{
		HibernateRequestInterceptor inteceptor = new HibernateRequestInterceptor();
		inteceptor.preHandle(null, null, null);
		int cfgId = Repository.addEntity(getMediaTransformConfig());
		inteceptor.postHandle(null, null, null, null);
				
		return cfgId;
	}
	
	public MediaTransformConfig getMediaTransformConfig()
	{
		MediaTransformConfig it = new MediaTransformConfig();
		it.setName("ello");
		it.setProcessOrder(1);
		it.addSelectExpression(getMovieRegEx());
		it.setSelectAllContent(true);
		it.setTargetDirectory("ad\\adf\\adf");
		it.setRenamingService(RenamingService.TMDB);
		
		return it;
	}
	
	
	public void validateMediaTransformConfig(MediaTransformConfig config)
	{
		assertTrue(config.getName().equals("ello"));
		assertTrue(config.getProcessOrder() == 1);
		assertTrue(config.getSelectExpressions().size() > 0);
		assertTrue(config.selectAllContent() == true);
		assertTrue(config.getRenamingService() == RenamingService.TMDB);
		
		
		for(MovieRegEx it : config.getSelectExpressions())
		{
			validateMovieRegEx(it);	
		}
		
	}
	
	
}

















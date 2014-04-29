package com.ibus.mediabeaver.core.test;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.ibus.mediabeaver.core.data.HibernateUtil;
import com.ibus.mediabeaver.core.data.Repository;
import com.ibus.mediabeaver.core.entity.ConfigVariable;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.MediaTransformConfig;
import com.ibus.mediabeaver.core.entity.MovieRegEx;
import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.core.entity.RegExVariable;
import com.ibus.mediabeaver.core.entity.RenamingService;
import com.ibus.mediabeaver.core.entity.TransformAction;

public class RepositoryTest
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
	
	
	//Tests //////////////////////////////////////////////////////

	/*@Test
	public void addMediaTransformConfigTest() throws Exception
	{
		// add entity
		int cfgId = addMediaTransformConfig();

		StartTransaction();

		MediaTransformConfig cfg = Repository.getEntity(
				MediaTransformConfig.class, cfgId);
		assertTrue(cfg != null);

		EndTransaction();
	}

	@Test
	public void getAllMediaTransformConfigTest() throws Exception
	{
		// add entity
		addMediaTransformConfig();

		StartTransaction();

		List<MediaTransformConfig> configs = Repository
				.getAllMediaTransformConfig();
		assertTrue(configs.size() > 0);
		validateMediaTransformConfig(configs.get(0));

		EndTransaction();
	}

	@Test
	public void addMovieRegExTest() throws Exception
	{
		// add entity
		int rexId = addMovieRegEx();

		StartTransaction();

		MovieRegEx rex = Repository.getEntity(MovieRegEx.class, rexId);
		assertTrue(rex != null);
		validateMovieRegEx(rex);

		EndTransaction();
	}

	
	 * test case handled above
	 * 
	 * @Test public void getMovieRegExTest() throws Exception{}
	 

	@Test
	public void updateMovieRegExTest() throws Exception
	{
		// add entity
		int id = addMovieRegEx();

		// get the item out and update it
		StartTransaction();

		MovieRegEx rex = Repository.getEntity(MovieRegEx.class, id);
		rex.setExpression("updateMovieRegExTest");
		Repository.updateEntity(rex);

		EndTransaction();

		// get the item out and check its updated value
		StartTransaction();

		MovieRegEx rex2 = Repository.getEntity(MovieRegEx.class, id);
		assertTrue(rex2.getExpression().equals("updateMovieRegExTest"));

		EndTransaction();
	}

	@Test
	public void addMovieRegExThroughParentTest() throws Exception
	{
		// add entity and child
		int cfgId = addMediaTransformConfig();

		// check that child was added as well as parent
		StartTransaction();

		MediaTransformConfig config = Repository.getEntity(
				MediaTransformConfig.class, cfgId);
		assertTrue(config.getSelectExpressions().size() > 0);

		EndTransaction();
	}

	@Test
	public void removeMovieRegExThroughParentTest() throws Exception
	{
		// add config
		int cfgId = addMediaTransformConfig();

		// remove entity form config
		StartTransaction();

		MediaTransformConfig config = Repository.getEntity(
				MediaTransformConfig.class, cfgId);
		int rexId = config.getSelectExpressions().get(0).getId();
		config.removeSelectExpression(config.getSelectExpressions().get(0));
		Repository.updateEntity(config);

		EndTransaction();

		// check that regex is removed from config and is not in db
		StartTransaction();

		config = Repository.getEntity(MediaTransformConfig.class, cfgId);
		assertTrue(config.getSelectExpressions().size() == 0);
		MovieRegEx rex2 = Repository.getEntity(MovieRegEx.class, rexId);
		assertTrue(rex2 == null);

		EndTransaction();
	}

	@Test
	public void updateMovieRegExThroughParentTest() throws Exception
	{
		// add config
		int cfgId = addMediaTransformConfig();

		// update entity through parent
		StartTransaction();

		MediaTransformConfig config = Repository.getEntity(
				MediaTransformConfig.class, cfgId);
		MovieRegEx rex = config.getSelectExpressions().get(0);
		rex.setExpression("updateMovieRegExThroughParentTest");
		Repository.updateEntity(config);

		EndTransaction();

		// check that rex has changed
		StartTransaction();

		config = Repository.getEntity(MediaTransformConfig.class, cfgId);
		rex = config.getSelectExpressions().get(0);

		assertTrue(rex.getExpression().equals(
				"updateMovieRegExThroughParentTest"));

		EndTransaction();

	}
*/
	
	@Test
	public void addMediaconfigTest()
	{
		StartTransaction();
		
		MediaConfig c = getMediaConfig();
		
		Session s = HibernateUtil.getSessionFactory().getCurrentSession();
		s.save(c);
		String id = c.getId();
		
		EndTransaction();
		
		
		StartTransaction();
		
		s = HibernateUtil.getSessionFactory().getCurrentSession();
		c = (MediaConfig) s.get(MediaConfig.class, id);
		assertConfigValid(c);
		
		EndTransaction();
		
	}
	
	
	
	
	//Utilities //////////////////////////////////////////////////////
	
	
	
	private MediaConfig getMediaConfig()
	{
		String movieNameVar = "MovieName";
		String movieYearVar = "MovieYear";
		String movieExtensionVar = "movieExtension";

		//create variables
		RegExVariable nameVar = new RegExVariable();
		nameVar.setVariableName(movieNameVar);
		nameVar.setGroupAssembly("{1}");
		nameVar.setReplaceExpression("[\\.-]+");
		nameVar.setReplaceWithCharacter(" ");

		RegExVariable yearVar = new RegExVariable();
		yearVar.setVariableName(movieYearVar);
		yearVar.setGroupAssembly("{2}");

		RegExVariable extensionVar = new RegExVariable();
		extensionVar.setVariableName(movieExtensionVar);
		extensionVar.setGroupAssembly("{3}");
		
		//create selector and add variables to it
		RegExSelector sel = new RegExSelector();
		sel.setExpression("(.+)[\\(\\[\\{]([0-9]{4})[\\)\\]\\}].+\\.([a-zA-Z]+)");
		sel.addRegExVariable(nameVar);
		sel.addRegExVariable(yearVar);
		sel.addRegExVariable(extensionVar);
		
		//add media config and add selector to it
		MediaConfig config = new MediaConfig();
		config.setAction(TransformAction.Move);
		config.setDescription("Move Movie files");
		config.setSourceDirectory("D:\\MediabeaverTests\\Source");
		
		config.addConfigVariable(new ConfigVariable(movieNameVar));
		config.addConfigVariable(new ConfigVariable(movieYearVar));
		config.addConfigVariable(new ConfigVariable(movieExtensionVar));
		
		config.setDestinationRoot("D:\\MediabeaverTests\\Destination\\Movies");
		config.setRelativeDestinationPath(String.format("{%s} ({%s})\\{%s} ({%s}).{%s}", movieNameVar, movieYearVar, movieNameVar, movieYearVar, movieExtensionVar));
		config.addRegExSelector(sel);

		return config;
	}
	
	
	private void assertConfigValid(MediaConfig config)
	{
		String movieNameVar = "MovieName";
		String movieYearVar = "MovieYear";
		String movieExtensionVar = "movieExtension";
		
		assertTrue(config.getAction().equals(TransformAction.Move));
		assertTrue(config.getDescription().equals("Move Movie files"));
		assertTrue(config.getSourceDirectory().equals("D:\\MediabeaverTests\\Source"));
		assertTrue(containsConfigVariable(config.getConfigVariables(), new ConfigVariable(movieNameVar)));
		assertTrue(containsConfigVariable(config.getConfigVariables(), new ConfigVariable(movieYearVar)));
		assertTrue(containsConfigVariable(config.getConfigVariables(), new ConfigVariable(movieExtensionVar)));
		assertTrue(config.getDestinationRoot().equals("D:\\MediabeaverTests\\Destination\\Movies"));
		assertTrue(config.getRelativeDestinationPath().equals(
				String.format("{%s} ({%s})\\{%s} ({%s}).{%s}", movieNameVar, movieYearVar, movieNameVar, movieYearVar, movieExtensionVar)));

		//create variables
		RegExVariable nameVar = new RegExVariable();
		nameVar.setVariableName(movieNameVar);
		nameVar.setGroupAssembly("{1}");
		nameVar.setReplaceExpression("[\\.-]+");
		nameVar.setReplaceWithCharacter(" ");
		
		RegExVariable yearVar = new RegExVariable();
		yearVar.setVariableName(movieYearVar);
		yearVar.setGroupAssembly("{2}");

		RegExVariable extensionVar = new RegExVariable();
		extensionVar.setVariableName(movieExtensionVar);
		extensionVar.setGroupAssembly("{3}");
		
		
		RegExSelector res = config.getRegExSelectors().iterator().next();
		assertTrue(res.getExpression().equals("(.+)[\\(\\[\\{]([0-9]{4})[\\)\\]\\}].+\\.([a-zA-Z]+)"));
		
		containsRegExVariable(res.getVariables(), nameVar);
		containsRegExVariable(res.getVariables(), yearVar);
		containsRegExVariable(res.getVariables(), extensionVar);
		
	}
	
	
	
	public boolean containsRegExVariable(Set<RegExVariable> variables, RegExVariable var)
	{
		for (RegExVariable rv : variables)
		{
			if (
					nullablesAreEqual(rv.getVariableName(), var.getVariableName()) && 
					nullablesAreEqual(rv.getGroupAssembly(), var.getGroupAssembly()) && 
					nullablesAreEqual(rv.getRreplaceExpression(), var.getRreplaceExpression()) &&
					nullablesAreEqual(rv.getReplaceWithCharacter(), var.getReplaceWithCharacter())) 
			{
				return true;
			}
		}
		
		return false;
	
	}
	
	
	
	public boolean containsConfigVariable(Set<ConfigVariable> configVariables, ConfigVariable var)
	{
		for (ConfigVariable cv : configVariables)
		{
			if (
					nullablesAreEqual(cv.getName(), var.getName()) && 
					nullablesAreEqual(cv.getValue(), var.getValue()) && 
					cv.isRequired() == var.isRequired())
			{
				return true;
			}
		}
		
		return false;
	}
	
	
	private boolean nullablesAreEqual(Object obj1, Object obj2)
	{
		if(obj1 != null)
		{
			if(obj2 != null)
			{
				if(!obj1.equals(obj2))
						return false;
			}
			else
				return false;		
		}
		else
		{
			if(obj2 != null)
				return false;
		}
		
		return true;
	}
	
	
	
	
	/*

	public int addMovieRegEx() throws Exception
	{
		StartTransaction();
		int cfgId = Repository.addEntity(getMovieRegEx());
		EndTransaction();

		return cfgId;
	}

	public MovieRegEx getMovieRegEx()
	{
		MovieRegEx re = new MovieRegEx();
		re.setExpression("1");
		re.getNameParser().setAssembledItem("2");
		re.getNameParser().setCleaningRegEx("3");
		re.getNameParser().setRemoveCharacters("4");
		re.getYearParser().setAssembledItem("5");
		re.getYearParser().setCleaningRegEx("6");
		re.getYearParser().setRemoveCharacters("7");
		re.setTestFileName("8");
		re.setGeneratedName("9");
		re.setGeneratedYear("10");

		return re;
	}

	public void validateMovieRegEx(MovieRegEx re)
	{
		assertTrue(re.getExpression().equals("1"));
		assertTrue(re.getNameParser().getAssembledItem().equals("2"));
		assertTrue(re.getNameParser().getCleaningRegEx().equals("3"));
		assertTrue(re.getNameParser().getRemoveCharacters().equals("4"));
		assertTrue(re.getYearParser().getAssembledItem().equals("5"));
		assertTrue(re.getYearParser().getCleaningRegEx().equals("6"));
		assertTrue(re.getYearParser().getRemoveCharacters().equals("7"));
		assertTrue(re.getTestFileName().equals("8"));
		assertTrue(re.getGeneratedName().equals("9"));
		assertTrue(re.getGeneratedYear().equals("10"));

	}

	public int addMediaTransformConfig() throws Exception
	{
		StartTransaction();
		int cfgId = Repository.addEntity(getMediaTransformConfig());
		EndTransaction();

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

		for (MovieRegEx it : config.getSelectExpressions())
		{
			validateMovieRegEx(it);
		}

	}
*/
}

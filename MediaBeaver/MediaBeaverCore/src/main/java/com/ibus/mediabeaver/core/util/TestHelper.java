package com.ibus.mediabeaver.core.util;

import static org.junit.Assert.assertTrue;

import com.ibus.mediabeaver.core.entity.ConfigVariable;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.PersistentObject;
import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.core.entity.RegExVariable;
import com.ibus.mediabeaver.core.entity.TransformAction;

public class TestHelper
{
	public static RegExSelector getRegExSelector(ConfigVariable cf)
	{
		RegExSelector sel = new RegExSelector();
		sel.setExpression("(.+)[\\(\\[\\{]([0-9]{4})[\\)\\]\\}].+\\.([a-zA-Z]+)");
		sel.setDescription("description");
		sel.addRegExVariable(getRegExVariable(cf));
		
		return sel;
	}
	
	public static RegExVariable getRegExVariable(ConfigVariable cf)
	{
		RegExVariable var = new RegExVariable();
		var.setGroupAssembly("{1}");
		var.setReplaceExpression("[\\.-]+");
		var.setReplaceWithCharacter(" ");
		var.setConfigVariable(cf);
		
		return var;
	}
	
	public static ConfigVariable getConfigVariable()
	{
		ConfigVariable var = new ConfigVariable();
		var.setName("name");
		var.setRequired(true);
		var.setValue("value");
		
		return var;
	}
	
	public static MediaConfig getMediaConfig()
	{
		//add MediaConfig and add selector to it
		MediaConfig config = new MediaConfig();
		config.setAction(TransformAction.Move);
		config.setDescription("Move Movie files");
		config.setSourceDirectory("D:\\MediabeaverTests\\Source");
		config.setDestinationRoot("D:\\MediabeaverTests\\Destination\\Movies");
		config.setRelativeDestinationPath("C:\\destination\\path");
		config.setExtensionsSelector("extensionsSelector");
		config.setSelectAllEmptyFolders(true);
		config.setSelectAllFiles(true);
		config.setSelectAllFolders(true);
		config.setUseOpenSubtitlesThumbprintService(true);
		
		ConfigVariable cf = getConfigVariable();
		
		config.addConfigVariable(cf);
		config.addRegExSelector(getRegExSelector(cf));
		
		//config.setOpenSubtitlesFieldMaps(openSubtitlesFieldMaps);
		
		return config;
	}
	
	
	
	public static void mediaConfigsEqual(MediaConfig m1, MediaConfig m2)
	{
		persistentObjectsEqual(m1, m2);
		
		assertTrue(m2.getAction().equals(m2.getAction()));
		assertTrue(m2.getDescription().equals(m2.getDescription()));
		assertTrue(m2.getSourceDirectory().equals(m2.getSourceDirectory()));
		assertTrue(m2.getDestinationRoot().equals(m2.getDestinationRoot()));
		assertTrue(m2.getRelativeDestinationPath().equals(m2.getRelativeDestinationPath()));
		assertTrue(m2.getExtensionsSelector().equals(m2.getExtensionsSelector()));
		assertTrue(m2.isSelectAllEmptyFolders() == m2.isSelectAllEmptyFolders());
		assertTrue(m2.isSelectAllFiles() == m2.isSelectAllFiles());
		assertTrue(m2.isSelectAllFolders() == m2.isSelectAllFolders());
		assertTrue(m2.isUseOpenSubtitlesThumbprintService() == m2.isUseOpenSubtitlesThumbprintService());
		
		RegExSelector[] sel1 = (RegExSelector[]) m1.getRegExSelectors().toArray(new RegExSelector[0]);
		RegExSelector[] sel2 = (RegExSelector[]) m2.getRegExSelectors().toArray(new RegExSelector[0]);
		
		for(int i=0; i < sel1.length; i++)
		{
			regExSelectorsEqual(sel1[i], sel2[i]);
		}
		
		ConfigVariable[] cf1 = (ConfigVariable[]) m1.getConfigVariables().toArray(new ConfigVariable[0]);
		ConfigVariable[] cf2 = (ConfigVariable[]) m2.getConfigVariables().toArray(new ConfigVariable[0]);
		
		for(int i=0; i < cf1.length; i++)
		{
			configVariablesEqual(cf1[i], cf2[i]);
		}
		
		
	}
	
	
	public static void configVariablesEqual(ConfigVariable c1, ConfigVariable c2)
	{
		persistentObjectsEqual(c1, c2);
		
		assertTrue(c1.getName().equals(c2.getName()));
		assertTrue(c1.getValue().equals(c2.getValue()));
	}
	
	
	
	public static void persistentObjectsEqual(PersistentObject sel1, PersistentObject sel2)
	{
		assertTrue(sel1.getId().equals(sel2.getId()));
		//assertTrue(sel1.getLastUpdate().equals(sel2.getLastUpdate()));
		assertTrue(sel1.getVersion() == sel2.getVersion());
	}
	
	public static void regExSelectorsEqual(RegExSelector sel1, RegExSelector sel2)
	{	
		persistentObjectsEqual(sel1, sel2);
		
		assertTrue(sel1.getExpression().equals(sel2.getExpression()));
		assertTrue(sel1.getDescription().equals(sel2.getDescription()));
		
		assertTrue(sel1.getVariables().size() == sel2.getVariables().size());
		
		RegExVariable[] vars1 = (RegExVariable[]) sel1.getVariables().toArray(new RegExVariable[0]);
		RegExVariable[] vars2 = (RegExVariable[]) sel2.getVariables().toArray(new RegExVariable[0]);
		
		for(int i=0; i < vars1.length; i++)
		{
			regExVariablesEqual(vars1[i], vars2[i]);
		}
	}
	
	
	public static void regExVariablesEqual(RegExVariable var1, RegExVariable var2)
	{
		assertTrue(var1.getConfigVariable().equals(var2.getConfigVariable()));
		assertTrue(var1.getGroupAssembly().equals(var2.getGroupAssembly()));
		assertTrue(var1.getRreplaceExpression().equals(var2.getRreplaceExpression()));
		assertTrue(var1.getReplaceWithCharacter().equals(var2.getReplaceWithCharacter()));
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
}












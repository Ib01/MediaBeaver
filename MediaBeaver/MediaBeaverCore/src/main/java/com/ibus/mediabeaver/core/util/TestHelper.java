package com.ibus.mediabeaver.core.util;

import static org.junit.Assert.assertTrue;

import com.ibus.mediabeaver.core.entity.ConfigVariable;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.PersistentObject;
import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.core.entity.RegExVariableSetter;
import com.ibus.mediabeaver.core.entity.TransformAction;

public class TestHelper
{
	public static RegExSelector getRegExSelector()
	{
		return getRegExSelector(null);
	}
	
	public static RegExSelector getRegExSelector(RegExVariableSetter var)
	{
		RegExSelector sel = new RegExSelector();
		sel.setExpression("(.+)[\\(\\[\\{]([0-9]{4})[\\)\\]\\}].*\\.([a-zA-Z]+)");
		sel.setDescription("description");
		
		if(var != null)
			sel.addRegExVariable(var);
		
		return sel;
	}
	
	public static RegExVariableSetter getRegExVariable()
	{
		return getRegExVariable1(null);
	}
	
	public static RegExVariableSetter getRegExVariable1(ConfigVariable cf)
	{
		RegExVariableSetter var = new RegExVariableSetter();
		var.setGroupAssembly("{1}");
		var.setReplaceExpression("[\\.-]+");
		var.setReplaceWithCharacter(" ");
		
		if(cf != null)
			var.setConfigVariable(cf);
		
		return var;
	}
	
	public static ConfigVariable getConfigVariable1()
	{
		return getConfigVariable1(null);
	}
	
	public static ConfigVariable getConfigVariable1(RegExVariableSetter rev)
	{
		ConfigVariable var = new ConfigVariable();
		var.setName("name");
		var.setRequired(true);
		var.setValue("value");
		
		if(var != null)
			var.addRegExVariable(rev);
		
		return var;
	}
	
	public static ConfigVariable getConfigVariable2()
	{
		return getConfigVariable2(null);
	}
	
	public static ConfigVariable getConfigVariable2(RegExVariableSetter rev)
	{
		ConfigVariable var = new ConfigVariable();
		var.setName("year");
		var.setRequired(true);
		var.setValue("1969");
		
		if(var != null)
			var.addRegExVariable(rev);
		
		return var;
	}
	
	public static MediaConfig getMediaConfigFullGraph()
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
		
		ConfigVariable cf = getConfigVariable1();
		ConfigVariable cf2 = getConfigVariable2();
		
		config.addConfigVariable(cf);
		config.addConfigVariable(cf2);
		
		RegExVariableSetter rev = getRegExVariable1(cf);
		RegExSelector res = getRegExSelector(rev);
		
		config.addRegExSelector(res);
		
		//config.setOpenSubtitlesFieldMaps(openSubtitlesFieldMaps);
		
		return config;
	}
	
	
	
	public static void mediaConfigsFullGraphEqual(MediaConfig m1, MediaConfig m2)
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
			configVariablesEqual(cf1[i], cf2[i],"mediaConfigsFullGraphEqual");
		}
		
		
	}
	
	
	public static void configVariablesEqual(ConfigVariable c1, ConfigVariable c2, String caller)
	{
		persistentObjectsEqual(c1, c2);
		
		assertTrue(c1.getName().equals(c2.getName()));
		assertTrue(c1.getValue().equals(c2.getValue()));
		
		if(caller != "regExVariablesEqual")
		{
			if(c1.getRegExVariables().size() > 0 && c2.getRegExVariables().size() > 0)
				regExVariablesEqual(c1.getRegExVariables().iterator().next(),c2.getRegExVariables().iterator().next(), "configVariablesEqual");
		}
	}
	
	
	
	public static void persistentObjectsEqual(PersistentObject sel1, PersistentObject sel2)
	{
		assertTrue(sel1.getId().equals(sel2.getId()));
		//assertTrue(sel1.getLastUpdate().equals(sel2.getLastUpdate()));
		//assertTrue(sel1.getVersion() == sel2.getVersion());
	}
	
	public static void regExSelectorsEqual(RegExSelector sel1, RegExSelector sel2)
	{	
		persistentObjectsEqual(sel1, sel2);
		
		assertTrue(sel1.getExpression().equals(sel2.getExpression()));
		assertTrue(sel1.getDescription().equals(sel2.getDescription()));
		
		assertTrue(sel1.getVariables().size() == sel2.getVariables().size());
		
		RegExVariableSetter[] vars1 = (RegExVariableSetter[]) sel1.getVariables().toArray(new RegExVariableSetter[0]);
		RegExVariableSetter[] vars2 = (RegExVariableSetter[]) sel2.getVariables().toArray(new RegExVariableSetter[0]);
		
		for(int i=0; i < vars1.length; i++)
		{
			regExVariablesEqual(vars1[i], vars2[i], "regExSelectorsEqual");
		}
	}
	
	
	public static void regExVariablesEqual(RegExVariableSetter var1, RegExVariableSetter var2, String caller)
	{
		assertTrue(var1.getConfigVariable().equals(var2.getConfigVariable()));
		assertTrue(var1.getGroupAssembly().equals(var2.getGroupAssembly()));
		assertTrue(var1.getReplaceExpression().equals(var2.getReplaceExpression()));
		assertTrue(var1.getReplaceWithCharacter().equals(var2.getReplaceWithCharacter()));
		
		if(caller != "configVariablesEqual")
			configVariablesEqual(var1.getConfigVariable(), var2.getConfigVariable(), "regExVariablesEqual");
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












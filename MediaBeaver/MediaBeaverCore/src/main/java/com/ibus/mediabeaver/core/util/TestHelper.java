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
	public static MediaConfig getMediaConfigFullGraph()
	{
		//add MediaConfig and add selector to it
		MediaConfig config = new MediaConfig();
		config.setAction(TransformAction.Move);
		config.setDescription("Move Movie files");
		config.setSourceDirectory("D:\\MediabeaverTests\\Source");
		config.setDestinationRoot("D:\\MediabeaverTests\\Destination\\Movies");
		config.setRelativeDestinationPath("C:\\destination\\path");
		
		ConfigVariable cf = getConfigVariable1();
		ConfigVariable cf2 = getConfigVariable2();
		
		config.addConfigVariable(cf);
		config.addConfigVariable(cf2);
		
		RegExVariableSetter rev = getRegExVariable1(cf.getName());
		RegExSelector res = getRegExSelector(rev);
		
		config.addRegExSelector(res);
		
		//config.setOpenSubtitlesFieldMaps(openSubtitlesFieldMaps);
		
		return config;
	}
	
	public static ConfigVariable getConfigVariable1()
	{
		ConfigVariable var = new ConfigVariable();
		var.setName("name");
		var.setValue("Iron Man");
		
		return var;
	}
	
	public static ConfigVariable getConfigVariable2()
	{
		ConfigVariable var = new ConfigVariable();
		var.setName("year");
		var.setValue("1969");
				
		return var;
	}
	
	public static RegExVariableSetter getRegExVariable1(String variableName)
	{
		RegExVariableSetter var = new RegExVariableSetter();
		var.setGroupAssembly("{1}");
		var.setReplaceExpression("[\\.-]+");
		var.setReplaceWithCharacter(" ");
		var.setVariableName(variableName);
		
		return var;
	}
	
	
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
	
	
	
	
	
	
	
	
	
	
	public static void mediaConfigsFullGraphEqual(MediaConfig m1, MediaConfig m2)
	{
		persistentObjectsEqual(m1, m2);
		
		assertTrue(m2.getAction().equals(m2.getAction()));
		assertTrue(m2.getDescription().equals(m2.getDescription()));
		assertTrue(m2.getSourceDirectory().equals(m2.getSourceDirectory()));
		assertTrue(m2.getDestinationRoot().equals(m2.getDestinationRoot()));
		assertTrue(m2.getRelativeDestinationPath().equals(m2.getRelativeDestinationPath()));
		
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
		assertTrue(var1.getGroupAssembly().equals(var2.getGroupAssembly()));
		assertTrue(var1.getReplaceExpression().equals(var2.getReplaceExpression()));
		assertTrue(var1.getReplaceWithCharacter().equals(var2.getReplaceWithCharacter()));
		assertTrue(var1.getVariableName().equals(var2.getVariableName()));
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












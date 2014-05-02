package com.ibus.mediabeaver.server.test;

import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;

import com.ibus.mediabeaver.core.entity.ConfigVariable;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.core.entity.RegExVariable;
import com.ibus.mediabeaver.core.entity.TransformAction;
import com.ibus.mediabeaver.server.viewmodel.RegExSelectorViewModel;
import com.ibus.mediabeaver.server.viewmodel.RegExVariableViewModel;

import static org.junit.Assert.*;

public class ModelMapperTests
{
	public class regExVariableMap extends PropertyMap<RegExVariable, RegExVariableViewModel> 
	{
		  protected void configure() {
		    map().setReplaceExpression(source.getRreplaceExpression());
		  }
	}
	
	@Test
	public void mapRegExVariableTest()
	{
		RegExVariable v = getRegExVariable();
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new regExVariableMap());
		
		//modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		
		RegExVariableViewModel vm = modelMapper.map(v, RegExVariableViewModel.class);
		
		assertTrue(true);
		
	}
	
	@Test
	public void mapRegExSelector()
	{
		RegExSelector s = getRegExSelector();
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new regExVariableMap());
		
		//modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		
		RegExSelectorViewModel vm = modelMapper.map(s , RegExSelectorViewModel.class);
		RegExSelector sel = modelMapper.map(vm , RegExSelector.class);
		
		
		assertTrue(true);
		
	}
	
	
	private RegExSelector getRegExSelector()
	{
		RegExSelector sel = new RegExSelector();
		sel.setExpression("(.+)[\\(\\[\\{]([0-9]{4})[\\)\\]\\}].+\\.([a-zA-Z]+)");
		sel.setDescription("description");
		sel.addRegExVariable(getRegExVariable());
		
		return sel;
	}
	
	/*private boolean regExSelectorValid(RegExSelector sel1, RegExSelector sel2)
	{
		assertTrue(sel1.getExpression().equals(sel2.getExpression()));
		assertTrue(sel1.getDescription().equals(sel2.getDescription()));
		
		//sel1.getVariables().toArray().
		//regExVariablesEqual()
	}*/
	
	private RegExVariable getRegExVariable()
	{
		RegExVariable var = new RegExVariable();
		var.setVariableName("MovieName");
		var.setGroupAssembly("{1}");
		var.setReplaceExpression("[\\.-]+");
		var.setReplaceWithCharacter(" ");
		
		return var;
	}
	
	private void regExVariablesEqual(RegExVariable var1, RegExVariable var2)
	{
		assertTrue(var1.getVariableName().equals(var2.getVariableName()));
		assertTrue(var1.getGroupAssembly().equals(var2.getGroupAssembly()));
		assertTrue(var1.getRreplaceExpression().equals(var2.getRreplaceExpression()));
		assertTrue(var1.getReplaceWithCharacter().equals(var2.getReplaceWithCharacter()));
	}
	
	private ConfigVariable getConfigVariable()
	{
		ConfigVariable var = new ConfigVariable();
		var.setName("name");
		var.setRequired(true);
		var.setValue("value");
		
		return var;
	}
	
	/*private boolean configVariableValid()
	{
		
	}*/
	
	private MediaConfig getMediaConfig()
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
		
		config.addRegExSelector(getRegExSelector());
		config.addConfigVariable(getConfigVariable());
		//config.setOpenSubtitlesFieldMaps(openSubtitlesFieldMaps);
		
		return config;
	}
	
	
	/*private boolean mediaConfigValid()
	{
		
	}
	*/
	
	/*private MediaConfig getMediaConfig()
	{
		String movieNameVar = "MovieName";
		String movieYearVar = "MovieYear";
		String movieExtensionVar = "movieExtension";

		//create RegExVariables
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
		
		//create RegExSelector and add variables to it
		RegExSelector sel = new RegExSelector();
		sel.setExpression("(.+)[\\(\\[\\{]([0-9]{4})[\\)\\]\\}].+\\.([a-zA-Z]+)");
		sel.addRegExVariable(nameVar);
		sel.addRegExVariable(yearVar);
		sel.addRegExVariable(extensionVar);
		
		//add MediaConfig and add selector to it
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
	}*/
}

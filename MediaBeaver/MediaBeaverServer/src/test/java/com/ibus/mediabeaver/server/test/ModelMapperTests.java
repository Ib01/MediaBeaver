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
	public void mapRegExVariable()
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
	
	private RegExVariable getRegExVariable()
	{
		RegExVariable var = new RegExVariable();
		var.setVariableName("MovieName");
		var.setGroupAssembly("{1}");
		//var.setReplaceExpression("[\\.-]+");
		var.setReplaceExpression("bla");
		var.setReplaceWithCharacter(" ");
		
		return var;
	}
	
	
	
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
}

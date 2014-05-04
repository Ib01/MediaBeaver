package com.ibus.mediabeaver.server.test;

import org.junit.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;

import com.ibus.mediabeaver.core.entity.ConfigVariable;
import com.ibus.mediabeaver.core.entity.MediaConfig;
import com.ibus.mediabeaver.core.entity.PersistentObject;
import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.core.entity.RegExVariable;
import com.ibus.mediabeaver.core.entity.TransformAction;
import com.ibus.mediabeaver.core.util.TestHelper;
import com.ibus.mediabeaver.server.viewmodel.MediaConfigViewModel;
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
		RegExVariable v = TestHelper.getRegExVariable();
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new regExVariableMap());
		//modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
				
		RegExVariableViewModel vm = modelMapper.map(v, RegExVariableViewModel.class);
		
		assertTrue(true);
		
	}
	
	@Test
	public void mapRegExSelector()
	{
		RegExSelector sel1 = TestHelper.getRegExSelector();
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new regExVariableMap());
		
		RegExSelectorViewModel vm = modelMapper.map(sel1 , RegExSelectorViewModel.class);
		RegExSelector sel2 = modelMapper.map(vm , RegExSelector.class);
		
		TestHelper.regExSelectorsEqual(sel1, sel2);
		
		assertTrue(true);
		
	}
	
	@Test
	public void mapMediaConfig()
	{
		MediaConfig cfg1 = TestHelper.getMediaConfig();
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.addMappings(new regExVariableMap());
		
		MediaConfigViewModel vm = modelMapper.map(cfg1 , MediaConfigViewModel.class);
		MediaConfig cfg2 = modelMapper.map(vm , MediaConfig.class);
		
		TestHelper.mediaConfigsEqual(cfg1, cfg2);
		
		assertTrue(true);
		
	}
	
	
	
	
	// utilities ////////////////////////////////////////////////////////////////////
	
	
	
	

	
	
}

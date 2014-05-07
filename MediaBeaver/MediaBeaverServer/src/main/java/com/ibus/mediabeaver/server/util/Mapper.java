package com.ibus.mediabeaver.server.util;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.util.AutoPopulatingList;

import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.core.entity.RegExVariable;
import com.ibus.mediabeaver.server.viewmodel.RegExSelectorViewModel;
import com.ibus.mediabeaver.server.viewmodel.RegExVariableViewModel;

public class Mapper
{
	public class regExVariableMap extends PropertyMap<RegExVariable, RegExVariableViewModel> 
	{
		  protected void configure() 
		  {
			  map().setReplaceExpression(source.getReplaceExpression());
		  }
	}
	/*public class regExSelectorMap extends PropertyMap<RegExSelector, RegExSelectorViewModel> 
	{
		  protected void configure() 
		  {
			  AutoPopulatingList<RegExVariableViewModel> l = new AutoPopulatingList<RegExVariableViewModel>(RegExVariableViewModel.class);
			  //l.addAll(source.getVariables());
			  
			  map().setVariables(l);
		  }
	}*/
	
	
	ModelMapper modelMapper;
	
	public Mapper()
	{
		modelMapper = new ModelMapper();
		modelMapper.addMappings(new Mapper.regExVariableMap());
		//modelMapper.addMappings(new Mapper.regExSelectorMap());
		
		//modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	
	public ModelMapper getMapper()
	{
		return modelMapper;
	}
}

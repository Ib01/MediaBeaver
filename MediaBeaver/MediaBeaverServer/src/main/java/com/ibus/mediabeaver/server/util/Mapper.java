package com.ibus.mediabeaver.server.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
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
	
	ModelMapper modelMapper;
	
	public Mapper()
	{
		modelMapper = new ModelMapper();
		modelMapper.addMappings(new Mapper.regExVariableMap());
		
		//modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}
	
	
	public ModelMapper getMapper()
	{
		return modelMapper;
	}
}

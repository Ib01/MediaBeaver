package com.ibus.mediabeaver.server.util;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.Provider;
import org.springframework.util.AutoPopulatingList;

import com.ibus.mediabeaver.core.entity.RegExSelector;
import com.ibus.mediabeaver.core.entity.RegExVariableSetter;
import com.ibus.mediabeaver.server.viewmodel.RegExSelectorViewModel;
import com.ibus.mediabeaver.server.viewmodel.RegExVariableViewModel;

public abstract class Mapper
{
	public class regExVariableMap extends PropertyMap<RegExVariableSetter, RegExVariableViewModel>
	{
		protected void configure()
		{
			map().setReplaceExpression(source.getReplaceExpression());
		}
	}


	public static ModelMapper getMapper()
	{
		ModelMapper modelMapper = new ModelMapper();
		//seems like it works without this works now???
		//modelMapper.addMappings(new Mapper.regExVariableMap());
		
		return modelMapper;
	}
}

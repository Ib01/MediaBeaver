package com.ibus.mediabeaver.server.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

public abstract class Mapper
{
	/*public class regExVariableMap extends PropertyMap<RegExPathTokenSetter, RegExPathTokenSetterViewModel>
	{
		protected void configure()
		{
			map().setReplaceExpression(source.getReplaceExpression());
		}
	}*/


	public static ModelMapper getMapper()
	{
		ModelMapper modelMapper = new ModelMapper();
		//seems like it works without this works now???
		//modelMapper.addMappings(new Mapper.regExVariableMap());
		
		return modelMapper;
	}
}

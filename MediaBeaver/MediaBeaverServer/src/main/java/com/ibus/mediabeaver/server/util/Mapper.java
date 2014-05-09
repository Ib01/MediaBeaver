package com.ibus.mediabeaver.server.util;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.Provider;
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

	public class regExSelectorMap extends PropertyMap<RegExSelector, RegExSelectorViewModel>
	{
		protected void configure()
		{
			
			
			map().setVariablesWithSet(source.getVariables());
		}
	}

	/*Provider<AutoPopulatingList<RegExVariableViewModel>> delegatingProvider = new Provider<AutoPopulatingList<RegExVariableViewModel>>()
	{
		public AutoPopulatingList<RegExVariableViewModel> get(
				ProvisionRequest<AutoPopulatingList<RegExVariableViewModel>> request)
		{
			return new AutoPopulatingList<RegExVariableViewModel>(
					RegExVariableViewModel.class);
		}

	};*/

	ModelMapper modelMapper;

	public Mapper()
	{
		modelMapper = new ModelMapper();
		modelMapper.addMappings(new Mapper.regExVariableMap());

		// modelMapper.getConfiguration().setProvider(delegatingProvider);

		// modelMapper.getConfiguration().setProvider(provider)

		// modelMapper.addMappings(new Mapper.regExSelectorMap());

		// modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
	}

	public ModelMapper getMapper()
	{
		return modelMapper;
	}
}

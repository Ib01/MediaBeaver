package com.ibus.mediabeaver.server.viewmodel;

import org.springframework.util.AutoPopulatingList;

public class RegExVariableAutoPopulatingList extends AutoPopulatingList<RegExPathTokenSetterViewModel>
{
	private static final long serialVersionUID = 1L;
	
	public RegExVariableAutoPopulatingList()
	{
		super(RegExPathTokenSetterViewModel.class);
	}
}
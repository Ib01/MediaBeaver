package com.ibus.mediabeaver.server.viewmodel;

import org.springframework.util.AutoPopulatingList;

public class RegExVariableAutoPopulatingList extends AutoPopulatingList<RegExVariableSetterViewModel>
{
	private static final long serialVersionUID = 1L;
	
	public RegExVariableAutoPopulatingList()
	{
		super(RegExVariableSetterViewModel.class);
	}
}
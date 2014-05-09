package com.ibus.mediabeaver.server.viewmodel;

import org.springframework.util.AutoPopulatingList;

public class RegExVariableAutoPopulatingList extends AutoPopulatingList<RegExVariableViewModel>
{
	private static final long serialVersionUID = 1L;
	
	public RegExVariableAutoPopulatingList()
	{
		super(RegExVariableViewModel.class);
	}
}
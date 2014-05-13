package com.ibus.mediabeaver.server.controller;

import java.text.ParseException;
import java.util.Locale;

import org.springframework.format.Formatter;

import com.ibus.mediabeaver.server.viewmodel.ConfigVariableViewModel;

public class ConfigVariableViewModelFormatter implements Formatter<ConfigVariableViewModel>
{

	public String print(ConfigVariableViewModel arg0, Locale arg1)
	{
		// TODO Auto-generated method stub
		return "";
	}

	public ConfigVariableViewModel parse(String arg0, Locale arg1)
			throws ParseException
	{
		return new ConfigVariableViewModel();
	}

}

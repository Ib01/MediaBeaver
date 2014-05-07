package com.ibus.mediabeaver.server.viewmodel;

import java.util.HashSet;
import java.util.Set;

import com.ibus.mediabeaver.core.entity.RegExVariable;

public class ConfigVariableViewModel extends ViewModel
{
	private String name;

	private String value;

	private boolean required = false;
	
	private Set<RegExVariable> regExVariables = new HashSet<RegExVariable>();
	
	public ConfigVariableViewModel(){}
	
	public ConfigVariableViewModel(String name)
	{
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

	public boolean isRequired()
	{
		return required;
	}

	public void setRequired(boolean required)
	{
		this.required = required;
	}

	public Set<RegExVariable> getRegExVariables()
	{
		return regExVariables;
	}

	public void setRegExVariables(Set<RegExVariable> regExVariables)
	{
		this.regExVariables = regExVariables;
	}
}

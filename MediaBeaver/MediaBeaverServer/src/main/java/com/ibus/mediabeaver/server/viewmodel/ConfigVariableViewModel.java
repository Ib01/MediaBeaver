package com.ibus.mediabeaver.server.viewmodel;

import java.util.HashSet;
import java.util.Set;

import com.ibus.mediabeaver.core.entity.RegExVariable;

public class ConfigVariableViewModel extends ViewModel
{

	private static final long serialVersionUID = 1L;
	
	private String name;

	private String value;

	private boolean required = false;
	
	public ConfigVariableViewModel(){}
	
	public ConfigVariableViewModel(String name)
	{
		this.name = name;
	}

	public ConfigVariableViewModel(String name, String value)
	{
		this.name = name;
		this.value= value;
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

	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* 
	 * we nees this crapola so selects will work with Model Editors. when you reference this type in a select list it 
	 * it determines whether to select an option based on whether it is equal with the option!!
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConfigVariableViewModel other = (ConfigVariableViewModel) obj;
		if (name == null)
		{
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
}

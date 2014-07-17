package com.ibus.mediabeaver.server.viewmodel;

import java.util.HashSet;
import java.util.Set;

import com.ibus.mediabeaver.core.entity.IdGenerator;
import com.ibus.mediabeaver.core.entity.RegExVariableSetter;

public class ConfigVariableViewModel extends ViewModel
{
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String value;
	
	public ConfigVariableViewModel()
	{
		setId(IdGenerator.createId());
	}
	
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

	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* 
	 * equals required when setting a selects to have an object value 
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
		ViewModel other = (ViewModel) obj;
		if (id == null)
		{
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}

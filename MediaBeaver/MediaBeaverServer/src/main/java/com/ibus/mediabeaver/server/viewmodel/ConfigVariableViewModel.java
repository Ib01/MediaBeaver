package com.ibus.mediabeaver.server.viewmodel;

import javax.persistence.Column;

public class ConfigVariableViewModel extends ViewModel
{
	private String name;

	private String value;

	private boolean required = false;

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
}

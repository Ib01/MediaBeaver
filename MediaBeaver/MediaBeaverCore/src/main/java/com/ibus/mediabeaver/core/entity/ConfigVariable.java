package com.ibus.mediabeaver.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

@Entity(name = "Config_Variable")
public class ConfigVariable extends PersistentObject
{
	private static final long serialVersionUID = 1L;
	
	@Column
	private String name;
		
	@Column
	private String value;

	@Column
	private boolean required = false;
	
	@ManyToOne(fetch = FetchType.EAGER)
	private MediaConfig parentConfig;
	
	public ConfigVariable(){}
	
	public ConfigVariable(String name)
	{
		this.name = name;
	}
	
	public ConfigVariable(String name, String value)
	{
		this.name = name;
		this.value =value; 
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


	public MediaConfig getParentConfig() {
		return parentConfig;
	}

	public void setParentConfig(MediaConfig parentConfig) {
		this.parentConfig = parentConfig;
	}
}











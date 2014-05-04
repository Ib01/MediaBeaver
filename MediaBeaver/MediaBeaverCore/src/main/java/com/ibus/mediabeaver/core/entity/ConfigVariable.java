package com.ibus.mediabeaver.core.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	
	@JsonIgnore
	@Column
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
	@Cascade({CascadeType.ALL})
	private Set<RegExVariable> regExVariables = new HashSet<RegExVariable>();
	
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

	public Set<RegExVariable> getRegExVariables()
	{
		return regExVariables;
	}

	public void setRegExVariables(Set<RegExVariable> regExVariables)
	{
		this.regExVariables = regExVariables;
	}


/*	public MediaConfig getParentConfig() {
		return parentConfig;
	}

	public void setParentConfig(MediaConfig parentConfig) {
		this.parentConfig = parentConfig;
	}*/
}











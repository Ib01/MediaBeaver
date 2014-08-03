package com.ibus.mediabeaver.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity(name = "Config_Variable")
public class ConfigVariable extends PersistentObject
{
	private static final long serialVersionUID = 1L;
	
	@Column
	private String name;
		
	@Column
	private String value;

	/*@Column
	private boolean required = false;*/
	
	/*@Column
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "configVariable")
	@Cascade({CascadeType.SAVE_UPDATE})
	private Set<RegExVariableSetter> regExVariables = new HashSet<RegExVariableSetter>();*/
	
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

	/*public boolean isRequired()
	{
		return required;
	}

	public void setRequired(boolean required)
	{
		this.required = required;
	}*/

	/*public Set<RegExVariableSetter> getRegExVariables()
	{
		return regExVariables;
	}

	public void setRegExVariables(Set<RegExVariableSetter> regExVariables)
	{
		this.regExVariables = regExVariables;
	}
	
	public boolean addRegExVariable(RegExVariableSetter var)
	{
		if (var != null && !regExVariables.contains(var))
		{
			regExVariables.add(var);
			var.setConfigVariable(this);
			
			return true;
		}
		
		return false;
	}
	
	public boolean removeRegExVariable(RegExVariableSetter var)
	{
		if(var != null && regExVariables.contains(var))
		{
			regExVariables.remove(var);
			var.setConfigVariable(null);
			return true;
		}
		
		return false;
	}*/

	

}











package com.ibus.mediabeaver.core.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.validator.constraints.NotEmpty;

@Entity(name = "RegEx_Selector")
public class RegExSelector extends PersistentObject
{
	private static final long serialVersionUID = 1L;

	@Column
	private String description;
	
	@Column
	@NotEmpty(message = "This field cannot be left empty")
	private String expression;

	@Column
	@OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
	@Cascade({CascadeType.ALL})
	private Set<RegExVariableSetter> variableSetters = new HashSet<RegExVariableSetter>();
	
	@Column
	private FileSystemType fileSystemTarget;
	
	/*
	@JsonIgnore
	@ManyToOne(fetch = FetchType.EAGER)
	private MediaConfig parentConfig;*/

	public String getExpression()
	{
		return expression;
	}

	public void setExpression(String expression)
	{
		this.expression = expression;
	}
	
	//TODO RENAME
	public Set<RegExVariableSetter> getVariableSetters()
	{
		return variableSetters;
	}

	//TODO RENAME
	public void setVariableSetters(Set<RegExVariableSetter> variables)
	{
		this.variableSetters = variables;
	}
	
	public void addRegExVariable(RegExVariableSetter var)
	{
		getVariableSetters().add(var);
	}

	

	/*public void addRegExVariable(RegExVariable var)
	{
		getVariables().add(var);
		var.setRegExSelector(this);
	}

	public void removeRegExVariable(RegExVariable var)
	{
		getVariables().remove(var);
		var.setRegExSelector(null);
	}
*/
/*	public MediaConfig getParentConfig()
	{
		return parentConfig;
	}

	public void setParentConfig(MediaConfig parentConfig)
	{
		this.parentConfig = parentConfig;
	}*/

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	

}

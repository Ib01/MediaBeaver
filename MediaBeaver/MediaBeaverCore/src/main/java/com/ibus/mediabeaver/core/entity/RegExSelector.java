package com.ibus.mediabeaver.core.entity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.NotEmpty;

@Entity(name = "RegEx_Selector")
public class RegExSelector extends PersistentObject
{
	private static final long serialVersionUID = 1L;

	@NotEmpty(message = "This field cannot be left empty")
	@Column
	private String expression;

	@Column
	private Set<RegExVariable> variables = new HashSet<RegExVariable>();

	@ManyToOne(fetch = FetchType.EAGER)
	private MediaConfig parentConfig;

	public String getExpression()
	{
		return expression;
	}

	public void setExpression(String expression)
	{
		this.expression = expression;
	}

	public Set<RegExVariable> getRegExVariables()
	{
		return variables;
	}

	public void addRegExVariable(RegExVariable var)
	{
		variables.add(var);
		var.setRegExSelector(this);
	}

	public void removeRegExVariable(RegExVariable var)
	{
		variables.remove(var);
		var.setRegExSelector(null);
	}

	public MediaConfig getParentConfig()
	{
		return parentConfig;
	}

	public void setParentConfig(MediaConfig parentConfig)
	{
		this.parentConfig = parentConfig;
	}

}

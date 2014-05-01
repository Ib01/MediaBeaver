package com.ibus.mediabeaver.server.viewmodel;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;

import com.ibus.mediabeaver.core.entity.RegExVariable;

public class RegExSelectorViewModel extends ViewModel
{
	private String description;

	@NotEmpty(message = "This field cannot be left empty")
	private String expression;

	private Set<RegExVariable> variables = new HashSet<RegExVariable>();

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getExpression()
	{
		return expression;
	}

	public void setExpression(String expression)
	{
		this.expression = expression;
	}

	public Set<RegExVariable> getVariables()
	{
		return variables;
	}

	public void setVariables(Set<RegExVariable> variables)
	{
		this.variables = variables;
	}
}

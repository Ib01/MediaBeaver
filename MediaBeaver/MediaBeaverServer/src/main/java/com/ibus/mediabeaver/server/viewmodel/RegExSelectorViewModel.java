package com.ibus.mediabeaver.server.viewmodel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.util.AutoPopulatingList;

import com.ibus.mediabeaver.core.entity.RegExVariable;

public class RegExSelectorViewModel extends ViewModel
{
	private String description;

	@NotEmpty(message = "This field cannot be left empty")
	private String expression;

	private Set<RegExVariableViewModel> variables = new HashSet<RegExVariableViewModel>();
	
	private RegExVariableViewModel toAddVariable = new RegExVariableViewModel();
	
	private String testFileName;
	
	
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
	
	public Set<RegExVariableViewModel> getVariables()
	{
		return variables;
	}

	public void setVariables(Set<RegExVariableViewModel> variables)
	{
		this.variables = variables;
	}

	public RegExVariableViewModel getToAddVariable()
	{
		return toAddVariable;
	}

	public void setToAddVariable(RegExVariableViewModel toAddVariable)
	{
		this.toAddVariable = toAddVariable;
	}

	public String getTestFileName()
	{
		return testFileName;
	}

	public void setTestFileName(String testFileName)
	{
		this.testFileName = testFileName;
	}

	
	
}
















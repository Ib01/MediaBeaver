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

	//private Set<RegExVariableViewModel> variables = new HashSet<RegExVariableViewModel>();
	
	//TODO DELETE THIS
	private AutoPopulatingList<RegExVariableViewModel> variables = new AutoPopulatingList<RegExVariableViewModel>(RegExVariableViewModel.class);
	/*public AutoPopulatingList getTester()
	{
		return tester;
	}
	public void setTester(AutoPopulatingList tester)
	{
		this.tester = tester;
	}*/

	
	
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

/*	public Set<RegExVariableViewModel> getVariables()
	{
		return variables;
	}

	public void setVariables(Set<RegExVariableViewModel> variables)
	{
		this.variables = variables;
	}*/

	public AutoPopulatingList<RegExVariableViewModel> getVariables()
	{
		return variables;
	}

	public void setVariables(AutoPopulatingList<RegExVariableViewModel> variables)
	{
		this.variables = variables;
	}
	
	
	public void setVariablesWithSet(Set<RegExVariableViewModel> variables)
	{
		this.variables = new AutoPopulatingList<RegExVariableViewModel>(new ArrayList<RegExVariableViewModel>(variables), RegExVariableViewModel.class);
	}
	
	public HashSet<RegExVariableViewModel> getVariablesAsSet()
	{
		return new HashSet<RegExVariableViewModel>(
				Arrays.asList(variables.toArray(new RegExVariableViewModel[]{})));
	}
}















